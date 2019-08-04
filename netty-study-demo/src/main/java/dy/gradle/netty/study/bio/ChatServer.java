package dy.gradle.netty.study.bio;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * BIO 实现聊天功能Server
 *
 * @author HuangDongYang<huangdy @ pvc123.com>
 * @date 2019/7/25
 */
public class ChatServer {

    public static AtomicInteger onlineNum = new AtomicInteger(0);

    static List<PrintWriter> allOut = new ArrayList<>();;
    static Map<String, PrintWriter> clientPrintWriterMap = new ConcurrentHashMap<>();

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(5000);
        System.out.println("Server running success");

        while (true) {
            System.out.println(" Waiting for a connection");
            Socket socket = serverSocket.accept();
            System.out.println("connected:" + socket.getInetAddress() + ":" + socket.getPort());
            System.out.println("当前聊天室人数:" + onlineNum.incrementAndGet());

            // 启动一个线程负责该客户端（BIO的特点，一个线程负责一个客户端连接
            new Thread(new ClientHandler(socket)).start();
        }
    }

    private static class ClientHandler implements Runnable {
        private Socket socket;  // client socket
        private String host;    // client的host

        public ClientHandler(Socket socket) {
            this.socket = socket;
            host = "" + socket.getPort();
        }

        public void run() {
            PrintWriter pw = null;
             try {
                 InputStream in = socket.getInputStream();
                 InputStreamReader isr = new InputStreamReader(in, "utf-8");
                 BufferedReader br = new BufferedReader(isr);

                 OutputStream out = socket.getOutputStream();
                 OutputStreamWriter osw = new OutputStreamWriter(out, "utf-8");

                 pw = new PrintWriter(osw, true);
                 synchronized (allOut) {
                     allOut.add(pw);
                 }
                 clientPrintWriterMap.put(host, pw);
                 // 广播当前客户端加入聊天室
                 sendMessage("加入聊天室");

                 String msg = null;
                 while ((msg = br.readLine()) != null) {
                     sendMessage(msg);
                 }
             } catch (Exception e) {

             } finally {
                 synchronized (allOut) {
                     allOut.remove(pw);
                     sendMessage(host + "离开聊天室");
                     System.out.println("当前在线人数：" + onlineNum.decrementAndGet());

                     try {
                         socket.close();
                     } catch (IOException ex) {
                         ex.printStackTrace();
                     }
                 }
             }
        }

        private void sendMessage(String msg) {
            synchronized (allOut) {
                PrintWriter myPw = clientPrintWriterMap.get(host);
                myPw.println("[我]:" + msg);

                for (PrintWriter o : allOut) {
                    if (o != myPw) {
                        o.println("[" + host + "]" + ":" + msg);
                    }
                }
            }
        }
    }
}
