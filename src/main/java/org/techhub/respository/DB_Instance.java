package org.techhub.respository;
import java.sql.*;


public class DB_Instance {
	DBConfig config=DBConfig.getInstance();
	 protected Connection conn=config.getConnection();
	 protected PreparedStatement stmt=config.getStatement();
	 protected ResultSet rs=config.getResult();
	protected CallableStatement cstmt=config.getCallStatement();
}
