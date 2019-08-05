package dy.springboot;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import dy.springboot.demo1.MyBean;
import dy.springboot.demo1.dao.mapper.StudentMapper;
import dy.springboot.demo1.model.Student;
import dy.springboot.demo1.properties.DyProperties;
import dy.springboot.demo1.service.BService;
import dy.springboot.demo1.service.SayService;
import dy.springboot.demo1.util.SpringIocUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;


/**
 * 类的描述
 *
 * @author HuangDongYang<huangdy @ pvc123.com>
 * @date 2019/4/16
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = dy.springboot.demo1.BootApplication.class)
public class AopTest {

    @Autowired
    private SayService sayService;
    @Autowired
    private BService bService;

    @Autowired
    private MyBean myBean;

    @Autowired
    private DyProperties dyProperties;

    @Autowired
    private StudentMapper studentMapper;

    public AopTest() {
        System.out.println("-----------------------------------");
        System.out.println("-                                 -");
        System.out.println("-          AopTests               -");
        System.out.println("-                                 -");
        System.out.println("-----------------------------------");
    }

    @Test
    public void test1() {
        System.out.println(">>>>>>>>test1");
        bService.sayB();
        Assert.assertNotNull(bService);
    }

    @Test
    public void test2() {
        System.out.println(">>>>>>>>>>test2");
    }

    @Test
    public void test3() {
        System.out.println("dy.name: " + dyProperties.getName());
        System.out.println("dy.telephone: " + dyProperties.getTelephone());
        System.out.println("dy.map: " + dyProperties.getMap());
        System.out.println("dy.listGame: " + dyProperties.getListGame());
        System.out.println("dy.game: " + dyProperties.getGame().toString());
    }

    @Test
    public void test4() {
        System.out.println(">>>>>>test4");
    }

}
