package com.revature.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.models.ReimbursementRequest;
import com.revature.service.EmployeeService;
import com.revature.util.ConnectionFactory;

public class ReimbursementsLogDao {

	private ReimbursementsLogDao() {}
	
	//TODO test
	public static void insertLog(ReimbursementRequest r, String details) throws SQLException {
		String sqlInsert = "INSERT INTO reimbursements_log VALUES(DEFAULT, DEFAULT, ?, ?, ?, ?, ?::category, ?, ?::status)";
		
		try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sqlInsert)) {
			ps.setString(1, details);
			ps.setInt(2, r.getEmployee().getId());
			ps.setString(3, r.getEmployee().getFirstName());
			ps.setString(4, r.getEmployee().getLastName());
			ps.setString(5, r.getCategory().name());
			ps.setDouble(6, EmployeeService.correctAmountFormat(r));
			ps.setString(7, r.getStatus().name());
			
			ps.execute();
		}
	}
	
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
	
	public static StringBuilder selectPendingRequests() throws SQLException {
		StringBuilder pendingRequests = new StringBuilder();
		
		String sqlSelect = "SELECT * FROM reimbursements_log WHERE reimbursement_status = 'PENDING'";
		
		try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sqlSelect)) {
			
			//Put results  into ResultSet and grab first/only result
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				pendingRequests.append(String.format("(%s) | %s | Employee ID and name: %s %s %s, Request Info: %s $%.2f%n",
						rs.getInt("id"), rs.getTimestamp("time_of_action"),
						rs.getInt("employee_id"), rs.getString("employee_first_name"), rs.getString("employee_last_name"),
						rs.getString("reimbursement_category"), rs.getDouble("amount")));
			}
			
			return pendingRequests;
		}
	}
	
	//TODO Update existing reimbursement status method when fm approves/denies
}
