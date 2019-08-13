package dy.springboot.demo1;

import org.apache.ibatis.io.Resources;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * 类的描述
 *
 * @author HuangDongYang<huangdy @ pvc123.com>
 * @date 2019/8/13
 */
public class Main {

    public static void main(String[] args) throws Exception {
        ArrayBlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<Integer>(10);
        blockingQueue.take();
        blockingQueue.add(1);
        blockingQueue.put(1);
    }
}
