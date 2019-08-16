package dy.gradle.hikari;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.CountDownLatch;

/**
 * 类的描述
 *
 * @author HuangDongYang<huangdy @ pvc123.com>
 * @date 2019/8/5
 */
public class Main {

    public static void main(String[] args) throws Exception {
        HikariPool pool = HikariPool.getInstance();
        Connection connection = null;

        try {
            pool.start();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }

        CountDownLatch countDownLatch = new CountDownLatch(6);

        for (int i = 0; i < 6; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getId() + ":开始获取connection");

                    Connection conn = null;
                    try {
                        conn = pool.getConnection();

                        System.out.println(Thread.currentThread().getId() + "：获取conn成功");
                        Thread.sleep(3000L);
                        PreparedStatement pstmt = conn.prepareStatement("select name from student where studentId=?");
                        pstmt.setString(1, "10");
                        ResultSet rs = pstmt.executeQuery();
                        if (rs.next()) {
                            System.out.println(Thread.currentThread().getId() + ":" + rs.getString(1));
                        }
                        else {
                            System.err.println(Thread.currentThread().getId() + ":empty result");
                        }

                        pstmt.close();
                        rs.close();
                        System.out.println(Thread.currentThread().getId() + ":归还conn");

                    } catch (Exception e) {
                        System.err.println(Thread.currentThread().getId() + ":" + e.getMessage());
                    } finally {
                        if (null != conn) {
                            try {
                                conn.close();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                        countDownLatch.countDown();
                    }
                }
            }).start();
        }

        countDownLatch.await();
        System.out.println(">>>>>>>>>>>>关闭连接池");
        System.out.println(pool.stop());

    }
}
