package dy.springboot.demo1.service.impl;

import dy.springboot.demo1.model.User;
import dy.springboot.demo1.service.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 类的描述
 *
 * @author HuangDongYang<huangdy @ pvc123.com>
 * @date 2019/7/21
 */
@Service
public class UserServiceImpl implements UserService {

    private List<User> systemUser;

    public UserServiceImpl() {
        systemUser = new ArrayList<>();

        System.out.println("--------- 系统用户：");
        for (int i = 1; i <= 10; i++) {
            User user = new User(String.valueOf(i), "A" + i);
            System.out.println(user);
            systemUser.add(user);
        }
        System.out.println("------------------------");
    }

    @Override
    public User getUser(String userId) {
        int id;

        try {
            id = Integer.parseInt(userId);
        } catch (Exception e) {
            return null;
        }

        if (id > 0 && id <= systemUser.size()) {
            return systemUser.get(id - 1);
        }

        return null;
    }
}
