package dy.springboot.demo1.rocketmq.producer;

import org.apache.rocketmq.client.exception.MQClientException;

/**
 * MQ生产者接口
 *
 * @author HuangDongYang<huangdy @ pvc123.com>
 * @date 2019/8/7
 */
public interface MqProducer {

    void start() throws MQClientException;

    void shutdown();
}
