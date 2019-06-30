package dy.gradle.netty.study.zerocopy;

import java.io.*;
import java.net.Socket;

/**
 * 普通的Socket IO客户端，没有使用零拷贝
 *
 * @author HuangDongYang<huangdy @ pvc123.com>
 * @date 2019/4/29
 */
public class OldClient {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 8899);

        InputStream inputStream = new FileInputStream("movie.mp4");

        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

        long startTime = System.currentTimeMillis();

        int total = 0;
        int readCount = 0;
        byte[] bytes = new byte[4096];
        while ((readCount = inputStream.read(bytes)) >= 0) {
            total += readCount;
            dataOutputStream.write(bytes);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("发送总字节数：" + total);
        System.out.println("发送耗时：" + (endTime - startTime) + " ms");

        dataOutputStream.close();
        socket.close();
        inputStream.close();
    }
}
