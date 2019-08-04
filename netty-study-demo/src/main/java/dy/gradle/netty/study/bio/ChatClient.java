package dy.gradle.netty.study.bio;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * 类的描述
 *
 * @author HuangDongYang<huangdy @ pvc123.com>
 * @date 2019/7/25
 */
public class ChatClient {

    public static void main(String[] args) throws Exception {


        Socket socket = new Socket("localhost", 5000);
        System.out.println("connection is success, my port:" + socket.getLocalPort());

        OutputStream out = null;
        try {
            Scanner scanner = new Scanner(System.in);
            out = socket.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(out, "utf-8");
            PrintWriter pw = new PrintWriter(osw, true);

            // 启动一个线程专门负责读服务器回传的消息
            new Thread(new ServerHandler(socket)).start();

            // 当前线程负责写
            String msg = null;
            while (true) {
                msg = scanner.nextLine();
                if ("exit".equals(msg.trim())) {
                    break;
                }
                pw.println(msg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static class ServerHandler implements Runnable {

        private Socket socket;

        public ServerHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            InputStream in = null;
            try {
                in = socket.getInputStream();
                InputStreamReader isr = new InputStreamReader(in, "utf-8");
                BufferedReader br = new BufferedReader(isr);
                String msg = null;
                while ((msg = br.readLine()) != null) {
                    System.out.println(msg);
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
