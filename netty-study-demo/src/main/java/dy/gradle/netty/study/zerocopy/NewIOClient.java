package dy.gradle.netty.study.zerocopy;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * NIO零拷贝客户端
 *
 * @author HuangDongYang<huangdy @ pvc123.com>
 * @date 2019/4/29
 */
public class NewIOClient {

    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("127.0.0.1", 8899));
        socketChannel.configureBlocking(true);

        FileChannel fileChannel = new FileInputStream("movie.mp4").getChannel();
        System.out.println(fileChannel.size());

        long startTime = System.currentTimeMillis();
        /*
        transferTo 将channel中的数据，转化(写入）到目标writeableChannel中
        这里内部实现采用了“零拷贝”

        ps : transferTo方法在windows中，如果文件太大（大于8m左右），就会传递不完整，所以要分段传递

         */
        long total = 0;
        long transferCount = 0;
        long position = 0;

        while ((transferCount = fileChannel.transferTo(position, fileChannel.size(), socketChannel)) > 0) {
            total += transferCount;
            position += transferCount;
        }


        long endTime = System.currentTimeMillis();
        System.out.println("发送的总字节数: " + total);
        System.out.println("发送耗时：" + (endTime - startTime) + " ms");

        fileChannel.close();
    }
}
