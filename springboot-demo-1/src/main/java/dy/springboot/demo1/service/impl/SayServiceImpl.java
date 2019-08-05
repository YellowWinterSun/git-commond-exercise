package dy.springboot.demo1.service.impl;

import dy.springboot.demo1.service.BService;
import dy.springboot.demo1.service.SayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 类的描述
 *
 * @author HuangDongYang<huangdy @ pvc123.com>
 * @date 2019/4/16
 */
@Service
public class SayServiceImpl implements SayService {

    @Autowired
    private BService bService;

    @Override
    public String saySth(String something) {
        if (null == something) {
            throw new RuntimeException("nothing you want to say");
        }

        System.out.println("I say : " + something);
        return something;
    }

    @Override
    public String sayBye() {

        System.out.println("I say bye");
        return "bye";
    }
}
