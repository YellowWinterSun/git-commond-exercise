package dy.gradle.netty.study.netty.ep1;

import java.security.SecureRandom;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * 学习原子更新
 * since jdk1.5
 * {@link java.util.concurrent.atomic.AtomicIntegerFieldUpdater}
 *
 * @author HuangDongYang<huangdy @ pvc123.com>
 * @date 2019/5/23
 */
public class AtomIntegerFieldUpdateDemo {

    final static int THREAD_NUM = 10000;

    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("----------- start testWithAtomic() --------------");
                try {
                    AtomIntegerFieldUpdateDemo.testWithAtmoic();
                } catch (InterruptedException e) {
                    System.out.println("---------- Error: " + e.getCause().getMessage());
                }
            }
        });

        t1.start();
        t1.join();

        System.out.println("\n=========== 结束testWithAtomic ===============");
        System.out.println("\n ------------------ start testWithoutAtomic() ----------------------");


        testWithoutAtmoic();

        System.out.println(" ################## End of this demo ################# ");
    }

    /**
     * 使用了AtomicIntegerFieldUpdater类控制int变量原子更新的方法
     */
    public static void testWithAtmoic () throws InterruptedException {
        AtomicIntegerFieldUpdater<DemoBean> atom = AtomicIntegerFieldUpdater.newUpdater(DemoBean.class, "inte");
        DemoBean bean = new DemoBean();

        CountDownLatch latch = new CountDownLatch(THREAD_NUM);

        final boolean[] isOk = {false};

        for (int i = 0; i < THREAD_NUM; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        SecureRandom secureRandom = new SecureRandom();

                        Thread.sleep(secureRandom.nextInt(10) * 10L /* 0~9随机数 乘以100ms */);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if (atom.getAndIncrement(bean) == THREAD_NUM-1) {
                        isOk[0] = true;
                    }

                    latch.countDown();
                }
            }).start();
        }

        latch.await();

        System.out.println("高并发结果:" + isOk[0] + "\n"
                + "inte: " + bean.inte);
    }

    /**
     * 无使用原子更新，无法解决高并发场景下，原子问题。
     * 最终结果isOk大概率是false，inte的结果大概率小于THREAD_NUM
     *
     * 出现并发的原因主要是“原子性"没有满足
     * 高并发下，bean.inet++(非原子性)操作，可能会在某一时刻，2个线程都拿到inet=1，然后对1进行++，得到2.
     * 这样本来2个线程的结果inet应该是加2次，变为3 。但是这里却只得到2
     *
     * 因此可以通过jdk1.5并发编程提供的工具类实现int的原子操作。或者自己实现原子性（如使用锁）
     */
    public static void testWithoutAtmoic() throws InterruptedException {

        DemoBean bean = new DemoBean();

        CountDownLatch latch = new CountDownLatch(THREAD_NUM);

        final boolean[] isOk = {false};

        for (int i = 0; i < THREAD_NUM; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        SecureRandom secureRandom = new SecureRandom();

                        Thread.sleep(secureRandom.nextInt(10) * 10L /* 0~9随机数 乘以100ms */);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    bean.inte++;
                    if (bean.inte == THREAD_NUM) {
                        isOk[0] = true;
                    }

                    latch.countDown();
                }
            }).start();
        }

        latch.await();

        System.out.println("高并发结果:" + isOk[0] + "\n"
                + "inte: " + bean.inte);
    }
}

class DemoBean {
    volatile int inte;

    public DemoBean() {
        this.inte = 0;
    }
}
