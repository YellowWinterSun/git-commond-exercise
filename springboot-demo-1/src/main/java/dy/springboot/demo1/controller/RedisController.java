package dy.springboot.demo1.controller;

import dy.springboot.demo1.exception.AppException;
import dy.springboot.demo1.model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 类的描述
 *
 * @author HuangDongYang<huangdy @ pvc123.com>
 * @date 2019/7/21
 */
@RestController
@RequestMapping("/redis")
public class RedisController {
//
//    @Resource
//    private RedisTemplate<String, Object> redisTemplate;
//
//    @Autowired
//    private StringRedisTemplate stringRedisTemplate;
//
//    @RequestMapping("/set")
//    public String set(String key, String value) {
//        stringRedisTemplate.opsForValue().set(key, value);
//        return "ok";
//    }
//
//    @RequestMapping("/get")
//    public String get(String key) {
//        if (StringUtils.isEmpty(key)) {
//            throw new AppException("没有传入key");
//        }
//        return stringRedisTemplate.opsForValue().get(key);
//    }
//
//    @RequestMapping("/testObj")
//    public String testObj() {
//        User user = new User("test1", "testRedis" + System.currentTimeMillis());
//        redisTemplate.opsForValue().set("testObj", user, 30L, TimeUnit.SECONDS);
//
//        try {
//            Thread.sleep(1000L);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        Object value = redisTemplate.opsForValue().get("testObj");
//        if (null == value) {
//            throw new AppException("存入Obj失败");
//        }
//
//        return "刚才存入的对象：" + ((User) value).toString();
//    }


}
