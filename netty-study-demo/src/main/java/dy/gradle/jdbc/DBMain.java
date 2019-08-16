package dy.gradle.jdbc;

/**
 * 类的描述
 *
 * @author HuangDongYang<huangdy @ pvc123.com>
 * @date 2019/8/5
 */
public class DBMain {

    public static void main(String[] args) {
        DB db = DB.getInstance();

        System.out.println(db.queryNameById("10"));
    }
}
