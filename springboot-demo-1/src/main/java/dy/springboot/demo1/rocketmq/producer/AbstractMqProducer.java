package dy.springboot.demo1.rocketmq.producer;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * 长连接producer抽象
 *
 * @author HuangDongYang<huangdy @ pvc123.com>
 * @date 2019/8/7
 */
public abstract class AbstractMqProducer implements MqProducer{
    protected DefaultMQProducer producer;

    @PostConstruct
    public abstract void start() throws MQClientException;

    @PreDestroy
    @Override
    public void shutdown() {
        System.err.println("AbstractMqProducer @PreDestroy调用");
        producer.shutdown();
    }
}
