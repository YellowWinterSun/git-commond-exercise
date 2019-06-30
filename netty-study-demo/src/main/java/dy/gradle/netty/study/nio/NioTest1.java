package dy.gradle.netty.study.nio;

import java.nio.IntBuffer;
import java.security.SecureRandom;
import java.util.Random;

/**
 * 简单讲解Buffer
 *
 * @author HuangDongYang<huangdy @ pvc123.com>
 * @date 2019/4/21
 */
public class NioTest1 {

    public static void main(String[] args) {
        IntBuffer buf = IntBuffer.allocate(10);

        for (int i = 0; i < 5; i++) {
            int randomNumber = new SecureRandom().nextInt(20);
            buf.put(randomNumber);
        }

        buf.flip();

        while (buf.hasRemaining()) {
            System.out.println(buf.get());
        }
    }
}
