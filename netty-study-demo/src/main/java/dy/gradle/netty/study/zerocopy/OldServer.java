package dy.gradle.netty.study.zerocopy;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 获取客户端传递过来的文件流，丢弃
 * 普通IO，没有零拷贝
 *
 * @author HuangDongYang<huangdy @ pvc123.com>
 * @date 2019/4/29
 */
public class OldServer {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8899);

        while (true) {
            // 等待 客户端连接(accept 阻塞)
            Socket clientSocket = serverSocket.accept();

            DataInputStream dataInputStream = new DataInputStream(clientSocket.getInputStream());

            try {

                byte[] bytes = new byte[4096];

                System.out.println("----------- read remote binary-data -----------");
                while (-1 != dataInputStream.read(bytes)) {
                    //System.out.println(new String(bytes));
                }
                System.out.println("----------- finish -----------");

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
