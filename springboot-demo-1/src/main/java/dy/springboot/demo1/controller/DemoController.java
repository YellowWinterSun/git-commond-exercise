package dy.springboot.demo1.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import dy.springboot.demo1.dao.mapper.StudentMapper;
import dy.springboot.demo1.model.Student;
import dy.springboot.demo1.properties.DyProperties;
import dy.springboot.demo1.vo.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.convert.EntityWriter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类的描述
 *
 * @author HuangDongYang<huangdy @ pvc123.com>
 * @date 2019/7/3
 */
@RestController
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    private DyProperties dyProperties;

    @Autowired
    private StudentMapper studentMapper;

    @RequestMapping("/test1")
    public String test1(String name) {
        System.out.println("DemoController.test1");

        System.out.println(dyProperties.getName());

        return (null != name)?name : "none";
    }

    @RequestMapping("/test2")
    public String test2(String name) {
        System.out.println("DemoController.test2");

        return (null != name)?name : "none";
    }

    @RequestMapping("/testMapper")
    public Msg testMapper() {
        studentMapper.countByExample(null);

        System.out.println(studentMapper.selectCountAll());

        return Msg.success().add("list", studentMapper.selectByExample(null));
    }

    @RequestMapping("/testMybatisPlus")
    public Msg testMyBatisPlus() {

        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sex", "男");
        queryWrapper.select("studentid", "name", "sex");

        List list = studentMapper.selectList(queryWrapper);

        return Msg.success().add("list", list);
    }


    /**
     * 下面/first /sessions是用于测试Spring Session有没有起作用的
     */
    @RequestMapping(value = "/first", method = RequestMethod.GET)
    public Map<String, Object> firstResp (HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        request.getSession().setAttribute("request Url", request.getRequestURL());
        map.put("request Url", request.getRequestURL());
        return map;
    }

    @RequestMapping(value = "/sessions", method = RequestMethod.GET)
    public Object sessions (HttpServletRequest request, HttpSession session){
        Map<String, Object> map = new HashMap<>();
        map.put("sessionId", request.getSession().getId());
        map.put("message", request.getSession().getAttribute("request Url"));
        map.put("HttpSession", session.getId());
        return map;
    }
}
