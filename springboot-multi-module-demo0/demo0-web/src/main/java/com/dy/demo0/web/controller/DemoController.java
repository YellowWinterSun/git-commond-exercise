package com.dy.demo0.web.controller;

import com.dy.demo0.biz.service.DemoService;
import com.dy.demo0.dao.mapper.StudentMapper;
import com.dy.demo0.dao.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 类的描述
 *
 * @author HuangDongYang<huangdy @ pvc123.com>
 * @date 2019/6/28
 */
@RestController
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    private DemoService demoService;

    @GetMapping("/hello")
    public String hello() {
        return demoService.hello();
    }

    @GetMapping("/getStudent")
    public String getStudent() {
        return demoService.getStudentNameById("1");
    }
}
