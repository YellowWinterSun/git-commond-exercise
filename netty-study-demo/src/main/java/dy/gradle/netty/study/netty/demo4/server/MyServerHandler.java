package dy.gradle.netty.study.netty.demo4.server;

import dy.gradle.netty.study.netty.demo4.MyProtocol;
import dy.gradle.netty.study.netty.demo4.MyProtocolService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * 服务器用的入站处理器，用于消费MyProtocol
 *
 * @author HuangDongYang<huangdy @ pvc123.com>
 * @date 2019/8/4
 */
public class MyServerHandler extends SimpleChannelInboundHandler<MyProtocol> {

    private MyProtocolService service = ServerServiceImpl.getInstance();

    // 保存一个个Channel对象
    public static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyProtocol msg) throws Exception {
        service.process(ctx, msg);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();

        // 告诉其他客户端，我加入了这个群聊
        System.out.println(("[服务器] : " + channel.remoteAddress() + " 加入\n"));

        channelGroup.add(channel);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();

        // 离开群聊
        System.out.println(("[服务器] : " +
                channel.remoteAddress() + " 离开\n"));

        // Netty会自动把这个断开的channel移除，下列代码无用
        // channelGroup.remove(channel);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println(channel.remoteAddress() + " 上线了");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println(channel.remoteAddress() + " 下线了");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
