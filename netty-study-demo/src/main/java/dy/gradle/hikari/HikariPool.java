package dy.gradle.hikari;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 类的描述
 *
 * @author HuangDongYang<huangdy @ pvc123.com>
 * @date 2019/8/5
 */
public class HikariPool {

    private final String DB_URL = "jdbc:mysql://127.0.0.1:3306/dorm?useUnicode=true&characterEncoding=utf8&useAffectedRows=true";
    private final String DB_NAME = "dorm";
    private final int DB_MAX_CONN = 3;
    private final String DB_USERNAME = "root";
    private final String DB_PASSWORD = "123456";

    private HikariDataSource dataSource;


    /* double-check */
    private static volatile HikariPool instance = null;

    private HikariPool() {}

    public static HikariPool getInstance() {
        if (null == instance) {
            synchronized (HikariPool.class) {
                if (null == instance) {
                    instance = new HikariPool();
                }
            }
        }
        return instance;
    }

    public void start() throws Exception {
        HikariConfig config = new HikariConfig();

        config.setDriverClassName("com.mysql.jdbc.Driver");
        config.setJdbcUrl(DB_URL);
        config.setUsername(DB_USERNAME);
        config.setPassword(DB_PASSWORD);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.setMaximumPoolSize(DB_MAX_CONN);

        dataSource = new HikariDataSource(config);
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public boolean stop() throws SQLException {
        dataSource.close();
        return true;
    }
}
