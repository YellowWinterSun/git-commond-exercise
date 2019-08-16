package dy.gradle.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DB {
	private Connection conn;
	private ResultSet rs;
	private PreparedStatement pstmt;
	private DatabaseConn dbConn;

	public String queryNameById(String id)
	{
		dbConn = new DatabaseConn();
		String result = null;
		
		try{
			conn = dbConn.getConnection();
			pstmt = conn.prepareStatement("select name from student where studentId=?");
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				result = rs.getString(1);
			}
			
			pstmt.close();
			rs.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally{
			if(conn != null)
				DatabaseConn.closeConn(conn);
		}
		return result;
	}

	/* singletop */
	private enum SingleHelper {
		INSTANCE;

		private DB instance;

		SingleHelper() {
			this.instance = new DB();
		}

		DB getInstance() {
			return this.instance;
		}
	}

	public static DB getInstance() {
		return SingleHelper.INSTANCE.getInstance();
	}
	
}
