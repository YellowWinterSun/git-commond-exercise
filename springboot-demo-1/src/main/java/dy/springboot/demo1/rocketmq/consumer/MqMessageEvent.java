package dy.springboot.demo1.rocketmq.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.context.ApplicationEvent;

import java.util.List;

/**
 * 监听rocketMQ消费消息的spring event
 *
 * @author HuangDongYang<huangdy @ pvc123.com>
 * @date 2019/8/7
 */
public class MqMessageEvent extends ApplicationEvent {
    private DefaultMQPushConsumer consumer;
    private List<MessageExt> msgs;

    public MqMessageEvent(DefaultMQPushConsumer consumer, List<MessageExt> msgs) {
        super(msgs);
        this.consumer = consumer;
        this.msgs = msgs;
    }

    public DefaultMQPushConsumer getConsumer() {
        return consumer;
    }

    public void setConsumer(DefaultMQPushConsumer consumer) {
        this.consumer = consumer;
    }

    public List<MessageExt> getMsgs() {
        return msgs;
    }

    public void setMsgs(List<MessageExt> msgs) {
        this.msgs = msgs;
    }
}
