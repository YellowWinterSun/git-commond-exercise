package dy.springboot.demo1.rocketmq.dto;

import java.io.Serializable;

/**
 * 模拟MQ消息的DTO。
 * 这里模拟一个新闻报道，标题内容作者，以及新闻id
 *
 * @author HuangDongYang<huangdy @ pvc123.com>
 * @date 2019/8/7
 */
public class TestMqMessageDto implements Serializable {

    private String title;

    private String body;

    private String author;

    private Integer id;

    public TestMqMessageDto() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
