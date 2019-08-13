package dy.springboot.demo1.rocketmq.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 测试消费者1
 *
 * @author HuangDongYang<huangdy @ pvc123.com>
 * @date 2019/8/7
 */
//@Component
public class TestConsumer1 extends AbstractMqConsumer {

    @Value("${dy.rocketmq.consumer.consumerGroup}")
    private String consumerGroup;

    @Value("${dy.rocketmq.namesrvAddr}")
    private String namesrvAddr;

    @Override
    void start0() {
        try {
            consumer = new DefaultMQPushConsumer(consumerGroup);
            consumer.setNamesrvAddr(namesrvAddr);
            consumer.setMessageModel(MessageModel.CLUSTERING);
            consumer.setConsumeMessageBatchMaxSize(1);
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
            consumer.subscribe("TopicTest", "*");
        } catch (MQClientException e) {
            e.printStackTrace();
        }

    }
}
