package dy.springboot.demo1;

import dy.springboot.demo1.properties.YmlPropertiesFactory;
import dy.springboot.demo1.service.SayService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <h1>SpringBoot2.x 的框架DEMO</h1>
 *
 * <p> 主要用于学习和整合技术 </p>
 *
 * <div>
 *     <p>概要</p>
 *     <ul>
 *         <li>整合Redis</li>
 *         <li>整合了JWT实现token单点登陆，不需要session共享等问题</li>
 *         <li>有个AOP的小例子</li>
 *         <li>整合了一个webapp目录用于前端开发</li>
 *         <li>整合了一个可用的redis分布式可重入防死锁</li>
 *         <li>整合Spring Session，解决session共享问题</li>
 *         <li>整合MyBatis以及其逆向工程文件，后续使用了mybatis-plus，对原本项目没有太多影响</li>
 *     </ul>
 * </div>
 *  <hr/>
 * <div>
 *     <p> 1、整合Redis </p>
 *     <p>
 *
 *     </p>
 * </div>
 *
 *
 *
 * @author HuangDongYang<huangdy @ pvc123.com>
 * @date 2019/4/16
 */
@SpringBootApplication
@ImportResource(locations = {"classpath:mybean.xml"})
@PropertySource(value = {"classpath:dy.yml"}, ignoreResourceNotFound = true, factory = YmlPropertiesFactory.class)
@MapperScan("dy.springboot.demo1.dao.mapper")
@Controller
@RequestMapping("/")
public class BootApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootApplication.class, args);
    }



    @Autowired
    private SayService sayService;

    @RequestMapping("/")
    public String toIndex() {
        return "index";
    }

    @RequestMapping("/index")
    public String toIndex_() {
        return "index";
    }

    @RequestMapping("/hello")
    @ResponseBody
    public String hello(String sth) {
        if (null == sth) {
            return "null";
        }

        String returenStr = sayService.saySth(sth);
        return returenStr;
    }
}
