package dy.gradle.netty.study.netty.demo4.client;

import dy.gradle.netty.study.netty.demo4.MyProtocol;
import dy.gradle.netty.study.netty.demo4.MyProtocolService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 响应服务器消息
 *
 * @author HuangDongYang<huangdy @ pvc123.com>
 * @date 2019/8/4
 */
public class MyClientHandler extends SimpleChannelInboundHandler<MyProtocol> {

    private MyProtocolService service = ClientServiceImpl.getInstance();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyProtocol msg) throws Exception {
        service.process(ctx, msg);
    }

}
