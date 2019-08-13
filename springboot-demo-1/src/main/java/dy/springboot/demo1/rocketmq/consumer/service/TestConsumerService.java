package dy.springboot.demo1.rocketmq.consumer.service;

import dy.springboot.demo1.rocketmq.consumer.MqMessageEvent;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

/**
 * 用于监听MqMessageEvent的服务
 *
 * @author HuangDongYang<huangdy @ pvc123.com>
 * @date 2019/8/7
 */
//@Service
public class TestConsumerService {

    /**
     * 消费TopicTest下的TagA
     * @param event
     */
    @EventListener(condition = "#event.msgs[0].topic=='TopicTest' && #event.msgs[0].tags=='TagA'")
    public void testConsumer(MqMessageEvent event) {
        // 由于mq消费者设置了batch=1，所以每次都只会消费一条
        MessageExt msg = event.getMsgs().get(0);

        if (null != msg) {
            StringBuilder sb = new StringBuilder();
            sb.append("------------- 本次消费消息 ----------------");
            sb.append("\n");
            try {
                sb.append("** Key: " + msg.getKeys() + "\n");
                sb.append("** 内容:\n" + new String(msg.getBody(), "utf-8"));
                sb.append("\n");
                sb.append("** 完整内容：\n" + msg);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            sb.append("\n");
            sb.append("-------------------------------------------");
            sb.append("\n");

            System.out.println(sb.toString());
        }
    }
}
