package dy.gradle.netty.study.nio;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * NIO三大关键之一：Selector的学习
 * 实现单线程NIO服务器，可以处理多个IO客户但
 *
 * @author HuangDongYang<huangdy @ pvc123.com>
 * @date 2019/4/25
 */
public class NioTest4 {

    public static void main(String[] args) throws Exception {

        /**
         * 该Demo，主线程处理5个端口
         */
        int[] ports = new int[5];
        for (int i = 0; i < ports.length; i++) {
            ports[i] = 5000 + i;
        }

        Selector selector = Selector.open();

        for (int i = 0; i < ports.length; i++) {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            // 非阻塞
            serverSocketChannel.configureBlocking(false);
            // 返回与当前channel关联的serverSocket
            ServerSocket serverSocket = serverSocketChannel.socket();
            InetSocketAddress address = new InetSocketAddress(ports[i]);
            // 绑定
            serverSocket.bind(address);

            /**
             * 实现注册 channel 和 selector 的关联关系
             * SelectionKey 监听事件
             * 如 SelectionKey.OP_ACCEPT 注册当前 channel 的响应事件。当客户端与这个channel对应的socket建立连接时
             */
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            System.out.println("监听端口：" + ports[i]);
        }

        while (true) {
            int numbers = selector.select();
            System.out.println("numbers: " + numbers);

            Set<SelectionKey> selectionKeySet = selector.selectedKeys();
            System.out.println("selectionKeySetSize: " + selectionKeySet.size());

            // 获取迭代器
            Iterator<SelectionKey> iter = selectionKeySet.iterator();

            while (iter.hasNext()) {
                SelectionKey nowKey = iter.next();

                if (nowKey.isAcceptable()) {
                    ServerSocketChannel channel = (ServerSocketChannel) nowKey.channel();
                    // 等待客户端发来连接请求，并连接成功
                    SocketChannel socketChannel = channel.accept();
                    socketChannel.configureBlocking(false);

                    socketChannel.register(selector, SelectionKey.OP_READ);

                    System.out.println("客户端连接成功：" + socketChannel.getLocalAddress());
                }
                else if (nowKey.isReadable()) {
                    SocketChannel socketChannel = (SocketChannel) nowKey.channel();

                    int byteRead = 0;

                    ByteBuffer byteBuffer = ByteBuffer.allocate(512);
                    int read = 0;
                    while ((read = socketChannel.read(byteBuffer)) > 0) {
                        byteRead += read;

                        byteBuffer.flip();
                        socketChannel.write(byteBuffer);
                        byteBuffer.clear();
                    }

                    System.out.println("读取: " + byteRead + " ,来自： " + socketChannel);
                }


                /**
                 * 必须移除当前监听事件,表示当前事件已经处理完，不再监听这个事件
                 */
                iter.remove();
                System.out.println("after-remove selectionKeySetSize: " + selectionKeySet.size());
            }
        }

    }



}
