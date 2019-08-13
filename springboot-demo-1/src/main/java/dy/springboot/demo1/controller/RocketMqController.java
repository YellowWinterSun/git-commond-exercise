package dy.springboot.demo1.controller;

import dy.springboot.demo1.rocketmq.dto.TestMqMessageDto;
import dy.springboot.demo1.rocketmq.producer.TestProducer1;
import dy.springboot.demo1.vo.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试rocketmq用的controller
 *
 * @author HuangDongYang<huangdy @ pvc123.com>
 * @date 2019/8/7
 */
//@RestController
//@RequestMapping("/rocketmq")
public class RocketMqController {

    @Autowired
    private TestProducer1 testProducer1;

    @RequestMapping("/testSend")
    public Msg testSend() {
        TestMqMessageDto dto = new TestMqMessageDto();
        dto.setTitle("某程序员说Php是世界上最好的语言遭到围殴");
        dto.setAuthor("MebiusYong");
        dto.setBody("今日凌晨3点，某互联网公司php程序员深夜发朋友圈号称php是世界上最好的语言，随后被公司Java同事看到，两人发生口角，最终惹怒公司大批Java程序员，php程序员势单力薄遭到挨打");
        dto.setId(1);

        return testProducer1.send("TopicTest", "TagA", String.valueOf(dto.getId()), dto) ? Msg.success() : Msg.error();
    }
}
