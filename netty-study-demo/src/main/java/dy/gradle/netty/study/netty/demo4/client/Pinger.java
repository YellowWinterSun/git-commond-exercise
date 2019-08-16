package dy.gradle.netty.study.netty.demo4.client;

import dy.gradle.netty.study.netty.demo4.MyProtocol;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.ScheduledFuture;

import java.security.SecureRandom;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 客户端连接服务端后，会循环执行一个任务，随机等待几秒，然后ping一下server端，即发送一个心跳包
 *
 * @author HuangDongYang<huangdy @ pvc123.com>
 * @date 2019/8/5
 */
public class Pinger extends ChannelInboundHandlerAdapter {

    private SecureRandom random = new SecureRandom();

    private Channel channel;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        this.channel = ctx.channel();

        ping(ctx.channel());
    }

    private void ping(Channel channel) {
        int second = Math.max(2, random.nextInt(5));
        //System.out.println("next heart beat will send after " + second + "s.");
        ScheduledFuture<?> future = channel.eventLoop().schedule(new Runnable() {
            @Override
            public void run() {
                if (channel.isActive()) {
                    //System.out.println("sending heart beat to server...");
                    MyProtocol protocol = new MyProtocol();
                    protocol.setType(MyProtocol.HEART_BEAT);

                    channel.writeAndFlush(protocol);
                }
                else {
                    System.err.println("The connection had broken, cancel the task that will send a heart beat. ");
                    channel.closeFuture();
                    throw new RuntimeException("heart beat error");
                }
            }
        }, second, TimeUnit.SECONDS);

        future.addListener(new GenericFutureListener() {

            @Override
            public void operationComplete(Future future) throws Exception {
                if (future.isSuccess()) {
                    ping(channel);
                }
            }
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("Pinger channel close");
        cause.printStackTrace();
        ctx.close();
    }
}
