package dy.springboot.demo1.rocketmq.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.*;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;

/**
 * MQ消费者抽象类
 *
 * 用于发布消息
 *
 * @author HuangDongYang<huangdy @ pvc123.com>
 * @date 2019/8/7
 */
public abstract class AbstractMqConsumer {

    protected DefaultMQPushConsumer consumer;
    // 是否允许顺序消费
    protected boolean isOrderConsumer = false;

    @Autowired
    private ApplicationEventPublisher publisher;

    /**
     * 初始化consumer，由开发者控制
     *
     * 例如：
     * try {
     *      consumer = new DefaultMQPushConsumer(consumerGroup);
     *      consumer.setNamesrvAddr(namesrvAddr);
     *      consumer.setMessageModel(MessageModel.CLUSTERING);
     *      consumer.setConsumeMessageBatchMaxSize(1);
     *      consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
     *      consumer.subscribe("TopicTest", "*");
     *  } catch (MQClientException e) {
     *      e.printStackTrace();
     *  }
     */
    abstract void start0();

    @PostConstruct
    private void start() {
        if (null == consumer) {
            start0();
        }

        if (isOrderConsumer) {
            consumer.registerMessageListener(new MessageListenerOrderly() {
                @Override
                public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext consumeOrderlyContext) {
                    try {
                        consumeOrderlyContext.setAutoCommit(true);
                        if (null == msgs || msgs.size() == 0) {
                            return ConsumeOrderlyStatus.SUCCESS;
                        }
                        publisher.publishEvent(new MqMessageEvent(consumer, msgs));
                    } catch (Exception e) {
                        e.printStackTrace();
                        return ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
                    }
                    return ConsumeOrderlyStatus.SUCCESS;
                }
            });
        }
        else {
            consumer.registerMessageListener(new MessageListenerConcurrently() {
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                    try {
                        if (null == msgs || msgs.size() == 0) {
                            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                        }
                        publisher.publishEvent(new MqMessageEvent(consumer, msgs));
                    } catch (Exception e) {
                        // 应该合理的去记录消费异常，如果这里不用try catch捕获消费逻辑，就会导致有些莫名的抛出异常而导致重复消费问题
                        // 此处只是demo，为了方便所以使用e.printStackTrace(); 实际项目请合理运用log日志等技术
                        e.printStackTrace();
                        return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                    }

                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
            });
        }

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Thread.sleep(10000L);

                    consumer.start();
                    System.err.println("rocketmq consumer server is starting...");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @PreDestroy
    public void shutdown() {
        consumer.shutdown();
    }
}
