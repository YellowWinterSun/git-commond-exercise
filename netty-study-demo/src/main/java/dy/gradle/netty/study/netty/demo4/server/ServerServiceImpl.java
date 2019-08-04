package dy.gradle.netty.study.netty.demo4.server;

import dy.gradle.netty.study.netty.demo4.MyProtocol;
import dy.gradle.netty.study.netty.demo4.MyProtocolService;
import io.netty.channel.ChannelHandlerContext;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * 服务端对MyProtocol协议的业务逻辑处理
 *
 * @author HuangDongYang<huangdy @ pvc123.com>
 * @date 2019/8/4
 */
public class ServerServiceImpl implements MyProtocolService {

    public ServerServiceImpl() {}

    @Override
    public void process(ChannelHandlerContext ctx, MyProtocol msg) {
        switch (msg.getType()) {
            case MyProtocol.TYPE_GET_SERVER_TIME:
                // 获取服务器系统时间
                processGetServerTime(ctx, msg);
                break;
            case MyProtocol.TYPE_GET_ONLINE_NUM:
                processGetOnlineNum(ctx, msg);
                break;
            default:
                break;
        }
    }

    private void processGetServerTime(ChannelHandlerContext ctx, MyProtocol msg) {
        // 组装消息
        MyProtocol response = new MyProtocol();
        response.setType(MyProtocol.TYPE_RESPONSE);
        response.setName("响应体");

        // 设置响应的类型，即响应结果
        response.add(MyProtocol.TYPE_RESPONSE, MyProtocol.TYPE_GET_SERVER_TIME)
                .add(MyProtocol.TYPE_GET_SERVER_TIME, (Long) System.currentTimeMillis());

        ctx.writeAndFlush(response);
    }

    private void processGetOnlineNum(ChannelHandlerContext ctx, MyProtocol msg) {
        // 组装消息
        MyProtocol response = new MyProtocol();
        response.setType(MyProtocol.TYPE_RESPONSE);
        response.setName("响应体");

        // 设置响应的类型，即响应结果
        response.add(MyProtocol.TYPE_RESPONSE, MyProtocol.TYPE_GET_ONLINE_NUM)
                .add(MyProtocol.TYPE_GET_ONLINE_NUM, (Integer) MyServerHandler.channelGroup.size());

        ctx.writeAndFlush(response);

    }

    /* single top */
    private enum SingleHelper {
        INSTANCE;

        private ServerServiceImpl instance; //单例

        SingleHelper() {
            instance = new ServerServiceImpl();
        }

        ServerServiceImpl getInstance() {
            return this.instance;
        }
    }

    public static ServerServiceImpl getInstance() {
        return SingleHelper.INSTANCE.getInstance();
    }
}
