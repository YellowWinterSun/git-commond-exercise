package dy.gradle.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConn {
	public DatabaseConn(){}
	
	public Connection getConnection()
	{
		Connection conn = null;
		String driverClass = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://127.0.0.1:3306/dorm?useUnicode=true&characterEncoding=utf8&useAffectedRows=true";
		String user = "root";
		String password = "123456";
		
		try{
			Class.forName(driverClass);
			conn = DriverManager.getConnection(url, user, password);
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		return conn;
	}
	
	public static void closeConn(Connection conn)
	{
		if(conn != null)
		{
			try{
				conn.close();
			}catch(SQLException ex)
			{
				ex.printStackTrace();
			}
		}
	}
}
