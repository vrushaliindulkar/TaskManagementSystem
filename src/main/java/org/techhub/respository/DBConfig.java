package org.techhub.respository;

import java.io.*;
import java.sql.*;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.techhub.client.TaskManagementSystemApp;



public class DBConfig {
	
	private static Logger Logger=org.apache.log4j.Logger.getLogger(DBConfig.class);

	protected static Connection conn;
	protected static PreparedStatement stmt;
	protected static ResultSet rs;
	private static DBConfig db;
	protected static CallableStatement cstmt;

	private DBConfig() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			File f = new File("");
			String path = f.getAbsolutePath();
			FileInputStream inputStream = new FileInputStream(path + "\\src\\main\\resources\\dbconfig.properties");
			Properties p = new Properties();
			p.load(inputStream);
			String driverClassName = p.getProperty("driver");
			String username = p.getProperty("username");
			String password = p.getProperty("password");
			String url = p.getProperty("url");
			conn = DriverManager.getConnection(url, username, password);
			
			if(conn!=null)
			{
				Logger.info("Database Connected Successfully");
			}
			else
			{
				Logger.info("Connection Fail");
			}

		} catch (Exception ex) {
			Logger.error("Database connection failed"+ex);

		}
	}

	public static DBConfig getInstance() {
		if (db == null) {
			db = new DBConfig();
		}
		return db;
	}

	public static Connection getConnection() {
		return conn;
	}

	public static PreparedStatement getStatement() {
		return stmt;
	}

	public static ResultSet getResult() {
		return rs;
	}

	public static CallableStatement getCallStatement() {
		return cstmt;
	}
}
