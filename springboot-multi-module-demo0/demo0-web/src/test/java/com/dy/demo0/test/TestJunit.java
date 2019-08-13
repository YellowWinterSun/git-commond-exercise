package com.dy.demo0.test;

import com.dy.demo0.dao.mapper.StudentMapper;
import com.dy.demo0.dao.model.Student;
import com.dy.demo0.web.SpringBootWebApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * 类的描述
 *
 * @author HuangDongYang<huangdy @ pvc123.com>
 * @date 2019/7/9
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootWebApplication.class)
public class TestJunit {

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private PlatformTransactionManager txManager;

    @Test
    @Transactional
    @Rollback(value = false)
    public void test() throws Exception{
        System.out.println(LocalDateTime.now() + "=======> test1() thread start");

        Thread.sleep(10*1000L);

        Student student = studentMapper.selectByPrimaryKey("10");
        String oldIdCard = student.getIdentitycard();
        String newIdCard = String.valueOf(Integer.parseInt(oldIdCard) + 1);

        System.out.println("读出来student：" + student);

        //Thread.sleep(20*1000L);
        System.out.println(LocalDateTime.now() + "开始更新数据库");
        System.out.println(LocalDateTime.now() + ", 更新结果:"  + studentMapper.updateTest(student.getStudentid(), "test1", newIdCard, oldIdCard));
        System.out.println(LocalDateTime.now() + "更新数据库完毕");
        //Thread.sleep(15*1000L);

        System.out.println(LocalDateTime.now() + "更新后student：" + studentMapper.selectByPrimaryKey("10"));

        System.out.println(LocalDateTime.now() + "=======> test1() thread destory");
    }

    @Test
    @Transactional
    @Rollback(value = false)
    public void test2() throws Exception{
        System.out.println(LocalDateTime.now() + "=======> test2() thread start");

        Thread.sleep(25*1000L);

        Student student = studentMapper.selectByPrimaryKey("10");
        String oldIdCard = student.getIdentitycard();
        String newIdCard = String.valueOf(Integer.parseInt(oldIdCard) + 1);

        System.out.println("读出来student：" + student);

        //Thread.sleep(25*1000L);
        System.out.println(LocalDateTime.now() + "开始更新数据库");
        System.out.println(LocalDateTime.now() + ", 更新结果:" + studentMapper.updateTest(student.getStudentid(), "test2", newIdCard, oldIdCard));
        System.out.println(LocalDateTime.now() + "更新数据库完毕");

        System.out.println(LocalDateTime.now() + "更新后student：" + studentMapper.selectByPrimaryKey("10"));
        //Thread.sleep(5*1000L);

        System.out.println(LocalDateTime.now() + "=======> test2() thread destory");
    }
}
