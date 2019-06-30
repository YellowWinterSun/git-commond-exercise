package dy.gradle.netty.study.nio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * NIO学习demo
 * 实现一个简单的NIO，聊天程序
 *
 * @author HuangDongYang<huangdy @ pvc123.com>
 * @date 2019/4/25
 */
public class NioTest5 {

    /**
     * NIO服务端
     * 一个线程同时处理多个客户端
     */
    protected static class NioServer {

        private static Map<SocketChannel, String> clientMap = new HashMap<>();

        public static void main(String[] args) throws IOException {

            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);

            ServerSocket serverSocket = serverSocketChannel.socket();
            serverSocket.bind(new InetSocketAddress(8899));

            /**
             * 注册 channel 到 selector
              */
            Selector selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            while (true) {
                try {
                    // 这个方法返回的int，表示他所监听到channel的key事件数量
                    System.out.println("轮询selector: " + selector.select());

                    Set<SelectionKey> selectionKeys = selector.selectedKeys();

                    selectionKeys.forEach(selectionKey -> {
                        try {

                            final SocketChannel client;

                            /**
                             * 判断当前获取到的selectionKey是什么
                             */
                            if (selectionKey.isAcceptable()) {
                                // 客户端发起连接到当前服务器
                                ServerSocketChannel channel = (ServerSocketChannel) selectionKey.channel();
                                client = channel.accept();
                                client.configureBlocking(false);
                                client.register(selector, SelectionKey.OP_READ);

                                String clientUuid = UUID.randomUUID().toString();
                                clientMap.put(client, clientUuid);

                                System.out.println("与客户端建立连接: " + client);
                                System.out.println("client number: " + clientMap.size());
                            }
                            else if (selectionKey.isReadable()) {
                                // channel有数据可读

                                try {
                                    // 获取客户端传来的数据
                                    ByteBuffer buf = ByteBuffer.allocate(512);
                                    client = (SocketChannel) selectionKey.channel();
                                    client.read(buf);

                                    buf.flip();
                                    String receivedMsg = String.valueOf(Charset.forName("utf-8").decode(buf).array());
                                    System.out.println("客户端传递过来的数据：" + receivedMsg);

                                    for (SocketChannel value : clientMap.keySet()) {
                                        ByteBuffer writeBuf = ByteBuffer.allocate(1024);

                                        writeBuf.put(("[" + client.getRemoteAddress() + "] : " + receivedMsg).getBytes());
                                        writeBuf.flip();
                                        System.out.println("发送" + value);
                                        value.write(writeBuf);
                                    }
                                } catch (IOException e) {
                                    System.out.println("客户端连接强制关闭:" + e.getMessage());
                                    // e.printStackTrace();
                                    clientMap.remove(selectionKey.channel());
                                    selectionKey.cancel();
                                    System.out.println("client number: " + clientMap.size());
                                }
                            }


                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    });

                    selectionKeys.clear();

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    protected static class NioClient {

        public static void main(String[] args) {
            try {

                SocketChannel socketChannel = SocketChannel.open();
                socketChannel.configureBlocking(false);

                Selector selector = Selector.open();

                // channel -register-> selector
                socketChannel.register(selector, SelectionKey.OP_CONNECT);

                socketChannel.connect(new InetSocketAddress("127.0.0.1", 8899));

                while (true) {
                    selector.select();

                    Set<SelectionKey> selectionKeys = selector.selectedKeys();

                    selectionKeys.forEach(selectionKey -> {
                        if (selectionKey.isConnectable()) {
                            // 已经建立连接事件
                            SocketChannel client = (SocketChannel) selectionKey.channel();

                            if (client.isConnectionPending()) {
                                // 连接正在处于进行状态
                                try {
                                    client.finishConnect();
                                    client.register(selector, SelectionKey.OP_READ);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }



                                ByteBuffer writeBuf = ByteBuffer.allocate(1024);

                                writeBuf.put(((LocalDateTime.now()) + " 连接成功").getBytes());
                                writeBuf.flip();

                                try {
                                    client.write(writeBuf);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        while (true) {
                                            try {
                                                writeBuf.clear();
                                                InputStreamReader input = new InputStreamReader(System.in);
                                                BufferedReader br = new BufferedReader(input);

                                                String sendMessage = br.readLine();

                                                writeBuf.put(sendMessage.getBytes());
                                                writeBuf.flip();
                                                client.write(writeBuf);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                }).start();

                                /*
                                ExecutorService executorService = Executors.newSingleThreadExecutor(Executors.defaultThreadFactory());
                                executorService.submit(() -> {
                                    while (true) {
                                        try {
                                            writeBuf.clear();
                                            InputStreamReader input = new InputStreamReader(System.in);
                                            BufferedReader br = new BufferedReader(input);

                                            String sendMessage = br.readLine();

                                            writeBuf.put(sendMessage.getBytes());
                                            writeBuf.flip();
                                            client.write(writeBuf);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                                */
                            }
                        }
                        else if (selectionKey.isReadable()) {
                            // 读取服务端的数据
                            SocketChannel client = (SocketChannel) selectionKey.channel();

                            ByteBuffer buf = ByteBuffer.allocate(1024);

                            try {
                                client.read(buf);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            buf.flip();
                            String receivedMsg = String.valueOf(Charset.forName("utf-8").decode(buf).array());
                            System.out.println(receivedMsg);

                        }

                    });

                    selectionKeys.clear();
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}



