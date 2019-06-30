package dy.gradle.netty.study.nio;

import java.nio.ByteBuffer;

/**
 * 介绍Buffer中 获取只读内部类 .asReadOnlyBuffer()
 *
 * @author HuangDongYang<huangdy @ pvc123.com>
 * @date 2019/4/22
 */
public class NioTest3 {

    public static void main(String[] args) {
        ByteBuffer buf = ByteBuffer.allocate(512);

        buf.put((byte)1);
        buf.put((byte)2);

        ByteBuffer rBuf = buf.asReadOnlyBuffer();

        rBuf.flip();

        for (int i = 0; i < rBuf.limit(); i++) {
            System.out.println(rBuf.get());
        }

        buf.put((byte)3);

        rBuf = buf.asReadOnlyBuffer();
        rBuf.put((byte)4);
        rBuf.flip();

        for (int i = 0; i < rBuf.limit(); i++) {
            System.out.println(rBuf.get());
        }
    }

    public static void good () {
        System.out.println("good trick");
    }
}
