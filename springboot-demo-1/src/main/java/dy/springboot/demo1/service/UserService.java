package dy.springboot.demo1.service;

import dy.springboot.demo1.model.User;

/**
 * 类的描述
 *
 * @author HuangDongYang<huangdy @ pvc123.com>
 * @date 2019/7/21
 */
public interface UserService {

    User getUser(String userId);
}
