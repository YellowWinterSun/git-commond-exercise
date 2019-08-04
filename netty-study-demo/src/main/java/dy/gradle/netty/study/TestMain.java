package dy.gradle.netty.study;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.util.ReferenceCountUtil;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 类的描述
 *
 * @author HuangDongYang<huangdy @ pvc123.com>
 * @date 2019/5/15
 */
public class TestMain {

    public static void main(String[] args) {

        ReentrantLock lock = new ReentrantLock();

        Condition conditionA = lock.newCondition();
        Condition conditionB = lock.newCondition();

        new Thread(new Runnable() {
            @Override
            public void run() {
                lock.lock();
                try {
                    System.out.println("C我要阻塞5s");
                    Thread.sleep(5000L);
                    System.out.println("C要conditionB.await()");
                    conditionB.await();
                    System.out.println("C被唤醒了");
                    System.out.println("C 处理完毕");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    conditionB.signalAll();
                    lock.unlock();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                lock.lock();
                try {
                    System.out.println("A我要阻塞10s");
                    Thread.sleep(10000L);
                    System.out.println("A我要释放调用以下condition");
                    conditionA.await();
                    System.out.println("A被唤醒了");
                    System.out.println("A去唤醒conditionB.notify()");
                    conditionB.signal();
                    System.out.println("A conditionB.await()");
                    conditionB.await();
                    System.out.println("A完成");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                lock.lock();
                try {
                    System.out.println("B我要阻塞5s");
                    Thread.sleep(5000L);
                    System.out.println("B要唤醒conditionA");
                    conditionA.signal();
                    System.out.println("B阻塞完了");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }).start();


    }


}

