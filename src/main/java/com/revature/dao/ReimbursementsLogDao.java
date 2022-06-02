package com.revature.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.util.ConnectionFactory;

public class ReimbursementsLogDao {

	private ReimbursementsLogDao() {};
	//TODO fix details
//	public static void insertLog(Account account, String logDetails) throws SQLException {
//		String sqlInsert = "INSERT INTO transactions_log VALUES(?, DEFAULT, ?)";
//		
//		try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sqlInsert)) {
//			ps.setInt(1, account.getAccountNumber());
//			ps.setString(2, logDetails);
//			
//			ps.execute();
//		}
//	}
	
	//TODO fix details, what output needs to be returned?
//	public static StringBuilder selectLogsByAccount(int accountNumber) throws SQLException {
//		StringBuilder logsByAccount = new StringBuilder();
//		
//		String sqlSelect = "SELECT * FROM transactions_log WHERE account_number = ?";
//		
//		try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sqlSelect)) {
//			ps.setInt(1, accountNumber);
//			
//			//Put results  into ResultSet and grab first/only result
//			ResultSet rs = ps.executeQuery();
//			while(rs.next()) {
//				logsByAccount.append(rs.getTimestamp("time_of_transaction") + " " + rs.getString("transaction_details") + "\n") ;
//			}
//			
//			return logsByAccount;
//		}
//	}
	//TODO fix details, what output needs to be returned?
//	public static StringBuilder selectAllLogs() throws SQLException {
//		StringBuilder logs = new StringBuilder();
//		
//		String sqlSelect = "SELECT * FROM transactions_log";
//		
//		try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sqlSelect)) {
//			
//			//Put results  into ResultSet and grab first/only result
//			ResultSet rs = ps.executeQuery();
//			while(rs.next()) {
//				logs.append(rs.getTimestamp("time_of_transaction") + " " + rs.getString("transaction_details") + "\n");
//			}
//			
//			return logs;
//		}
//	}
}
