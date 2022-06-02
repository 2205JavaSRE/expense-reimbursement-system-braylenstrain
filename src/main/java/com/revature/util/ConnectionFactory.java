package com.revature.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	
	//TODO change to env variables in ec2
	private static final String URL = System.getenv("db_url");
	private static final String USERNAME = System.getenv("db_username");
	private static final String PASSWORD = System.getenv("db_password");
	
	private ConnectionFactory() {}
	
	//Establishes a connection to the database referenced by URL
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL,USERNAME,PASSWORD);
	}

}