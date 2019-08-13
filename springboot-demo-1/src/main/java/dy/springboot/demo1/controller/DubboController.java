package dy.springboot.demo1.controller;

import com.dy.learn.dubbo.demo.provider.api.service.DubboLService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 类的描述
 *
 * @author HuangDongYang<huangdy @ pvc123.com>
 * @date 2019/8/11
 */
//@RestController
//@RequestMapping("/dubbo")
public class DubboController {

    //@Reference
    DubboLService dubboLService;

    @RequestMapping("/consumer")
    public String consumer() {
        return dubboLService.sayHi("我是springboot-demo-1");
    }
}
