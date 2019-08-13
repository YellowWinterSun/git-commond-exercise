package dy.springboot.demo1.rocketmq.producer;

import com.alibaba.fastjson.JSONObject;
import dy.springboot.demo1.rocketmq.dto.TestMqMessageDto;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;

/**
 * 生产者
 *
 * 利用SpringBoot的特性，首先将其注解Component，让Spring容器接管这个实例
 *
 * 利用PostConstruct来让实例化后的Bean进行后置处理
 *
 * @author HuangDongYang<huangdy @ pvc123.com>
 * @date 2019/8/7
 */
//@Component
public class TestProducer1 extends AbstractMqProducer{

    @Value("${dy.rocketmq.producer.producerGroup}")
    private String producerGroup;

    @Value("${dy.rocketmq.namesrvAddr}")
    private String namesrvAddr;

    @Value("${dy.rocketmq.producer.instanceName}")
    private String instanceName;

    @Override
    public void start() throws MQClientException {
        if (null == producer) {
            producer = new DefaultMQProducer(producerGroup);
            producer.setNamesrvAddr(namesrvAddr);
            producer.setInstanceName(instanceName);
        }
        producer.start();
        System.out.println(namesrvAddr);
        System.err.println("rocketmq producer is starting...");
    }

    public boolean send(String topic, String tag, String key, TestMqMessageDto msg) {
        try {
            Message message = new Message(
                    topic, tag, key,
                    JSONObject.toJSONString(msg).getBytes("utf-8")
            );

            SendResult sendResult = producer.send(message);
            System.err.println("消息生产结果：" + sendResult);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
