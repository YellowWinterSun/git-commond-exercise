package com.dy.demo0.biz.service.impl;

import com.dy.demo0.biz.service.DemoService;
import org.springframework.stereotype.Service;

/**
 * 类的描述
 *
 * @author HuangDongYang<huangdy @ pvc123.com>
 * @date 2019/7/9
 */
@Service
public class DemoService2Impl implements DemoService {
    @Override
    public String hello() {
        return "inDemoService2Impl";
    }

    @Override
    public String getStudentNameById(String id) {
        return null;
    }
}
