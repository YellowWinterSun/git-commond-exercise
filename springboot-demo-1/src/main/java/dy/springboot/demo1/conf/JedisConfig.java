package dy.springboot.demo1.conf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurerSupport;

/**
 * Jedis配置
 *
 * @author HuangDongYang<huangdy @ pvc123.com>
 * Create on 2018/10/29 21:55
 */
//@Configuration
public class JedisConfig extends CachingConfigurerSupport {

    private Logger logger = LoggerFactory.getLogger(JedisConfig.class);

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.timeout}")
    private int timeout;

    @Value("${spring.redis.jedis.pool.max-active}")
    private int maxActive;

    @Value("${spring.redis.jedis.pool.max-idle}")
    private int maxIdle;

    @Value("${spring.redis.jedis.pool.min-idle}")
    private int minIdle;

    @Value("${spring.redis.jedis.pool.max-wait}")
    private long maxWaitMillis;

    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.database}")
    private int dbIndex;


//    @Bean
//    public JedisPool redisPoolFactory(){
//        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
//        jedisPoolConfig.setMaxIdle(maxIdle);
//        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
//        jedisPoolConfig.setMaxTotal(maxActive);
//        jedisPoolConfig.setMinIdle(minIdle);
//
//        JedisPool jedisPool = null;
//        if (StringUtils.isBlank(password)){
//            jedisPool = new JedisPool(jedisPoolConfig,host,port,timeout, null, dbIndex);
//        }
//        else {
//            jedisPool = new JedisPool(jedisPoolConfig,host,port,timeout,password, dbIndex);
//        }
//
//        logger.info("JedisPool注入成功！");
//        logger.info("redis地址：" + host + ":" + port);
//
//        //测试ping
//        Jedis jedis = null;
//        try {
//            jedis = jedisPool.getResource();
//        } catch (Exception e){
//            e.printStackTrace();
//            logger.info(">>> Redis没有服务");
//        }
//        if (null == jedis){
//            logger.info(">>> 获取Jedis失败");
//        }
//        else {
//            try {
//                logger.info("ping:" + jedis.ping());
//            } catch (Exception e) {
//                e.printStackTrace();
//            } finally {
//                if (null != jedis) {
//                    jedis.close();
//                }
//            }
//        }
//        return  jedisPool;
//    }


}
