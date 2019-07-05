package com.dy.demo0.biz.service.impl;

import com.dy.demo0.biz.service.DemoService;
import com.dy.demo0.dao.mapper.StudentMapper;
import com.dy.demo0.dao.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 类的描述
 *
 * @author HuangDongYang<huangdy @ pvc123.com>
 * @date 2019/6/28
 */
@Service
public class DemoServiceImpl implements DemoService {

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public String hello() {
        System.out.println("in DemoServiceImpl hello() ");
        return "demoService say hello ";
    }

    @Override
    public String getStudentNameById(String id) {
        Student student = studentMapper.selectByPrimaryKey(id);

        if (null == student)
            throw new RuntimeException("no name");

        return student.toString();
    }


}
