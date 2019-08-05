package dy.springboot.demo1.service.impl;

import dy.springboot.demo1.service.BService;
import dy.springboot.demo1.service.SayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 类的描述
 *
 * @author HuangDongYang<huangdy @ pvc123.com>
 * @date 2019/6/30
 */
@Service
public class BServiceImpl implements BService {

    @Autowired
    private SayService sayService;

    @Override
    public String sayB() {
        System.out.println(sayService.saySth("B 调用了 SayService"));
        return "i am B";
    }
}
