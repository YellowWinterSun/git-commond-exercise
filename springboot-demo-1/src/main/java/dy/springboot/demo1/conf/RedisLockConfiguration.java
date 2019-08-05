package dy.springboot.demo1.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.integration.redis.util.RedisLockRegistry;

/**
 * 已实现的redis分布式锁
 *
 * @author HuangDongYang<huangdy @ pvc123.com>
 * @date 2019/7/3
 */
//@Configuration
public class RedisLockConfiguration {

    //@Bean
    public RedisLockRegistry redisLockRegistry(RedisConnectionFactory redisConnectionFactory) {
        // registryKey 是分布式锁在redis中的前缀。可以根据具体项目决定
        return new RedisLockRegistry(redisConnectionFactory, "redis-lock-test");
    }
}
