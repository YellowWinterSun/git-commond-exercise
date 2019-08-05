package dy.springboot.demo1;

/**
 * 类的描述
 *
 * @author HuangDongYang<huangdy @ pvc123.com>
 * @date 2019/4/17
 */
public class MyBean {
    public String hello = "hello";

    public String hello() {
        return hello;
    }

    public String getHello() {
        return hello;
    }

    public void setHello(String hello) {
        this.hello = hello;
    }
}
