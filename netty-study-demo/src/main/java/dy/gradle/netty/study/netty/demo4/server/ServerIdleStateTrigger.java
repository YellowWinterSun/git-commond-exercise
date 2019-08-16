package dy.gradle.netty.study.netty.demo4.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * 在规定事件内没有收到客户端的任何数据包，将主动断开该连接
 *
 * @author HuangDongYang<huangdy @ pvc123.com>
 * @date 2019/8/5
 */
public class ServerIdleStateTrigger extends ChannelInboundHandlerAdapter {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleState state = ((IdleStateEvent) evt).state();
            if (state == IdleState.READER_IDLE) {
                System.err.println(ctx.channel().remoteAddress() + " 读超时");
                ctx.channel().disconnect();
            }
        }else{
            super.userEventTriggered(ctx, evt);
        }
    }
}
