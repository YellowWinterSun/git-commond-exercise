package dy.gradle.netty.study.netty.demo4.client;

import dy.gradle.netty.study.netty.demo4.MyProtocol;
import dy.gradle.netty.study.netty.demo4.MyProtocolService;
import io.netty.channel.ChannelHandlerContext;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * 客户端消费MyProtocol服务实现
 *
 * @author HuangDongYang<huangdy @ pvc123.com>
 * @date 2019/8/4
 */
public class ClientServiceImpl implements MyProtocolService {


    public ClientServiceImpl() {
    }

    @Override
    public void process(ChannelHandlerContext ctx, MyProtocol msg) {
        switch (msg.getType()) {
            case MyProtocol.TYPE_RESPONSE:
                processResponse(ctx, msg);
                break;
            default:
                System.out.println("不是响应体");
                break;
        }
    }

    private void processResponse(ChannelHandlerContext ctx, MyProtocol msg) {
        String responseType = (String) msg.getMap().get(MyProtocol.TYPE_RESPONSE);
        if (null == responseType) {
            throw new RuntimeException("响应体消息类型为null");
        }

        try {
            Object result = msg.getMap().get(responseType);
            switch (responseType) {
                case MyProtocol.TYPE_GET_SERVER_TIME:
                    Instant instant = Instant.ofEpochMilli(Long.parseLong(result.toString()));
                    ZoneId zone = ZoneId.systemDefault();
                    System.out.println("服务器时间：\n" + LocalDateTime.ofInstant(instant, zone));

                    break;
                case MyProtocol.TYPE_GET_ONLINE_NUM:
                    System.out.println("服务器连接数:\n" +  result);

                    break;

                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    /* single top */
    private enum SingleHelper {
        INSTANCE;

        private ClientServiceImpl instance; //单例

        SingleHelper() {
            instance = new ClientServiceImpl();
        }

        ClientServiceImpl getInstance() {
            return this.instance;
        }
    }

    public static ClientServiceImpl getInstance() {
        return SingleHelper.INSTANCE.getInstance();
    }
}
