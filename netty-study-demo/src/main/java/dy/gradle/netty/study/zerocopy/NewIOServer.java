package dy.gradle.netty.study.zerocopy;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * 实用NIO的零拷贝
 *
 * @author HuangDongYang<huangdy @ pvc123.com>
 * @date 2019/4/29
 */
public class NewIOServer {

    public static void main(String[] args) throws IOException {
        InetSocketAddress address = new InetSocketAddress(8899);

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocket.setReuseAddress(true);
        serverSocket.bind(address);

        ByteBuffer byteBuffer = ByteBuffer.allocate(4096);

        while (true) {
            SocketChannel socketChannel = serverSocketChannel.accept();
            socketChannel.configureBlocking(true);

            int total = 0;
            int readCount = 0;

            System.out.println("----------- read remote binary-data -----------");
            while (-1 != readCount) {
                try {

                    readCount = socketChannel.read(byteBuffer);
                    total += readCount;
                    //System.out.println(readCount);
                } catch (Exception ex) {
                    //ex.printStackTrace();
                    System.out.println("客户端已经关闭连接:-1");
                    socketChannel.close();
                    break;
                }

                byteBuffer.rewind();
            }
            System.out.println("本次读取数据字节数:" + total);
            System.out.println("----------- finish -----------");
        }
    }
}
