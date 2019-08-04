package dy.gradle.netty.study.netty.demo4.server;

import dy.gradle.netty.study.netty.demo4.MyProtocolDecoder;
import dy.gradle.netty.study.netty.demo4.MyProtocolEncode;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * DEMO4 netty-server
 *
 * 基于自定义协议的服务器
 *
 * 收到客户端发来的命令，在服务端进行业务处理，处理完毕后响应结果
 *
 * @author HuangDongYang<huangdy @ pvc123.com>
 * @date 2019/8/4
 */
public class NettyServer {

    public static void main(String[] args) throws Exception {
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(boss, worker).channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<NioSocketChannel>() {

                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            ch.pipeline().addLast(
                                    new MyProtocolEncode(),
                                    new MyProtocolDecoder(),
                                    new MyServerHandler()
                            );
                        }
                    });

            ChannelFuture channelFuture = serverBootstrap.bind(8899).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }


    }
}
