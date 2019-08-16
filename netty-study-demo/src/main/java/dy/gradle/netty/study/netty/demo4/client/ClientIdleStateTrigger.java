package dy.gradle.netty.study.netty.demo4.client;

import dy.gradle.netty.study.netty.demo4.MyProtocol;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * 用于捕获{@link io.netty.handler.timeout.IdleStateHandler} 事件
 *
 * @author HuangDongYang<huangdy @ pvc123.com>
 * @date 2019/8/5
 */
public class ClientIdleStateTrigger extends ChannelInboundHandlerAdapter {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleState state = ((IdleStateEvent) evt).state();
            if (state == IdleState.WRITER_IDLE) {
                System.out.println("trigger send heart beat");
                MyProtocol protocol = new MyProtocol();
                protocol.setType(MyProtocol.HEART_BEAT);
                ctx.channel().writeAndFlush(protocol);
            }
        }
        else {
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("与服务端断开连接");
    }
}
