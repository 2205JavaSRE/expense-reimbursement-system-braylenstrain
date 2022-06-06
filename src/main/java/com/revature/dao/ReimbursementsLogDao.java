package com.revature.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.enums.ReimbursementCategory;
import com.revature.enums.ReimbursementStatus;
import com.revature.models.ReimbursementRequest;
import com.revature.service.EmployeeService;
import com.revature.util.ConnectionFactory;

public class ReimbursementsLogDao {

	private ReimbursementsLogDao() {}
	
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
	
	public static ReimbursementRequest selectLogById(int id) throws SQLException {
		String sqlSelect = "SELECT * FROM reimbursements_log WHERE id = ?";
		
		try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sqlSelect)) {
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			rs.next();
			
			return new ReimbursementRequest(rs.getInt(1), EmployeeService.employee, rs.getTimestamp(2), ReimbursementCategory.valueOf(rs.getString(7)), ReimbursementStatus.valueOf(rs.getString("reimbursement_status")), rs.getDouble(8));
		}
	}
	
	public static StringBuilder selectAllLogs() throws SQLException {
		StringBuilder allLogs = new StringBuilder();
		String sqlSelect = "SELECT * FROM reimbursements_log ORDER BY id";
		
		try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sqlSelect)) {
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				allLogs.append(String.format("%s | %s | %s | %s %s %s | %s $%.2f %s%n", rs.getInt(1), rs.getTimestamp(2), rs.getString(3), rs.getInt(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getDouble(8), rs.getString(9)));
			}
		}
		return allLogs;
	}
	
	public static StringBuilder selectLogsByEmployee(int employeeID) throws SQLException {
		StringBuilder logsByEmployee = new StringBuilder();
		
		String sqlSelect = "SELECT * FROM reimbursements_log WHERE employee_id = ? ORDER BY id";
		
		try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sqlSelect)) {
			ps.setInt(1, employeeID);
			
			//Put results  into ResultSet and grab first/only result
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				logsByEmployee.append(String.format("%s | %s | %s | %s %s %s | %s $%.2f %s%n", rs.getInt(1), rs.getTimestamp(2), rs.getString(3), rs.getInt(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getDouble(8), rs.getString(9)));
			}
			
			return logsByEmployee;
		}
	}
	
	public static StringBuilder selectPendingRequests() throws SQLException {
		StringBuilder pendingRequests = new StringBuilder();
		
		String sqlSelect = "SELECT * FROM reimbursements_log WHERE reimbursement_status = 'PENDING'";
		
		try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sqlSelect)) {
			
			//Put results  into ResultSet and grab first/only result
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				pendingRequests.append(String.format("(%s) | %s | Employee ID and name: %s %s %s | Request Info: %s $%.2f%n",
						rs.getInt("id"), rs.getTimestamp("time_of_action"),
						rs.getInt("employee_id"), rs.getString("employee_first_name"), rs.getString("employee_last_name"),
						rs.getString("reimbursement_category"), rs.getDouble("amount")));
			}
			
			return pendingRequests;
		}
	}

	public static void updateReimbursementStatus(int id, ReimbursementStatus status) throws SQLException {
		String sqlUpdate = "UPDATE reimbursements_log SET reimbursement_status = ?::status WHERE id = ?";
		
		try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sqlUpdate)) {
			ps.setInt(2, id);
			ps.setString(1, status.name());
			
			ps.execute();
		}
	}
	
}
