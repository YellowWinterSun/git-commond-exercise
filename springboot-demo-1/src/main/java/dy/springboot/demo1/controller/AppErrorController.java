package dy.springboot.demo1.controller;

import dy.springboot.demo1.vo.Msg;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 类的描述
 *
 * @author HuangDongYang<huangdy @ pvc123.com>
 * @date 2019/7/29
 */
@Controller
public class AppErrorController implements ErrorController {

    @RequestMapping("/error")
    @ResponseBody
    public Msg error() {
        System.out.println("in errer()");
        return Msg.error("错误页面");
    }

    @Override
    public String getErrorPath() {
        System.out.println("in getErrorPath");
        return "/error";
    }
}
