package dy.gradle.netty.study.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 实用Nio完成文件拷贝功能
 *
 * @author HuangDongYang<huangdy @ pvc123.com>
 * @date 2019/4/22
 */
public class NioTest2 {

    public static void main(String[] args) throws Exception {
        FileInputStream fis = new FileInputStream("NioTest2.txt");
        FileChannel channel = fis.getChannel();

        System.out.println("channel size: " + channel.size());
        System.out.println("channel position: " + channel.position());

        ByteBuffer buf = ByteBuffer.allocate(10);

        long totalReadSize = 0;
        while (totalReadSize < channel.size()) {
            System.out.println("read length: " + channel.read(buf));

            buf.flip();

            while (buf.hasRemaining()) {
                System.out.println((char) buf.get());
            }

            totalReadSize += buf.limit();

            buf.clear();

        }


        fis.close();
    }
}
