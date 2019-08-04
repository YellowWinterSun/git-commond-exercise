package dy.gradle.netty.study.netty.demo4.client;

import dy.gradle.netty.study.netty.demo2.MyChatClientInitializer;
import dy.gradle.netty.study.netty.demo4.MyProtocol;
import dy.gradle.netty.study.netty.demo4.MyProtocolDecoder;
import dy.gradle.netty.study.netty.demo4.MyProtocolEncode;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * DEMO4 Netty-client
 *
 * 基于自定义协议的Netty通讯
 *
 * 客户端功能：根据屏幕提示操作，发送命令到服务器，服务器响应操作并返回结果
 *
 * @author HuangDongYang<huangdy @ pvc123.com>
 * @date 2019/8/4
 */
public class NettyClient {

    public static void main(String[] args) throws Exception{
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(
                                    new MyProtocolEncode(),
                                    new MyProtocolDecoder(),
                                    new MyClientHandler()
                            );
                        }
                    });

            Channel channel = bootstrap.connect(
                    "localhost",
                    8899
            ).sync().channel();

            System.out.println("---------------------");
            System.out.println("- 1: 获取系统时间 ");
            System.out.println("- 2: 获取在线人数 ");
            System.out.println("- exit: 退出 ");
            System.out.println("---------------------");

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                System.out.print("请输入序号:");
                String input = br.readLine().trim();
                if ("exit".equals(input)) {
                    System.out.println("++++++++开始关闭channel+++++++++");
                    channel.close();
                    break;
                }

                MyProtocol myProtocol = null;
                switch (input) {
                    case "1":
                        myProtocol = new MyProtocol();
                        myProtocol.setType(MyProtocol.TYPE_GET_SERVER_TIME);
                        channel.writeAndFlush(myProtocol);
                        break;

                    case "2":
                        myProtocol = new MyProtocol();
                        myProtocol.setType(MyProtocol.TYPE_GET_ONLINE_NUM);
                        channel.writeAndFlush(myProtocol);
                        break;

                    default:
                        System.out.println("不存在该命令");
                }

                Thread.sleep(3000L);
            }

        } finally {
            eventLoopGroup.shutdownGracefully();
        }
    }
}
