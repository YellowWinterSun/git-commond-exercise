package dy.springboot.demo1.controller;

import dy.springboot.demo1.model.User;
import dy.springboot.demo1.service.UserService;
import dy.springboot.demo1.util.JwtTokenUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 类的描述
 *
 * @author HuangDongYang<huangdy @ pvc123.com>
 * @date 2019/7/21
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping("/doLogin")
    public String login(HttpServletResponse response, String userId) {
        if (StringUtils.isBlank(userId)) {
            return "登陆失败";
        }

        User user = userService.getUser(userId);
        if (null == user) {
            return "登陆失败";
        }

        String token = JwtTokenUtil.createToken(user.getUserId(), user.getUserName());
        Cookie cookie = new Cookie("TOKEN", token);
        cookie.setPath("/");
        response.addCookie(cookie);

        return "token is : " + token;
    }

    @RequestMapping("/validLogin")
    public String validLogin(HttpServletRequest request) {
        return request.getAttribute("loginingUserId") + "已经登陆了";
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (null == cookies || cookies.length <= 0) {
            return "注销成功";
        }

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("TOKEN")) {
                cookie.setValue(null);
                cookie.setMaxAge(0);
                cookie.setPath("/");
                response.addCookie(cookie);
                break;
            }
        }

        return "注销成功";
    }
}
