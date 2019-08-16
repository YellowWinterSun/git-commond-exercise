package dy.gradle.netty.study.netty.demo4;

import java.util.HashMap;
import java.util.Map;

/**
 * 通讯用的协议
 *
 * @author HuangDongYang<huangdy @ pvc123.com>
 * @date 2019/8/4
 */
public class MyProtocol {

    public final static String TYPE_REQUEST = "request";
    public final static String TYPE_RESPONSE = "response";
    public final static String HEART_BEAT = "heart_beat";

    public final static String TYPE_GET_SERVER_TIME = "get_server_time";
    public final static String TYPE_GET_ONLINE_NUM = "get_online_num";

    private String type;
    private String name;
    private Map<String, Object> map;

    public MyProtocol() {
        map = new HashMap<>();
    }

    public MyProtocol add(String key, Object value) {
        this.map.put(key ,value);
        return this;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }
}
