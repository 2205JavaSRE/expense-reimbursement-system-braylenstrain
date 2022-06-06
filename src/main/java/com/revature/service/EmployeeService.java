package com.revature.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.revature.dao.EmployeeDao;
import com.revature.dao.ReimbursementsLogDao;
import com.revature.enums.ReimbursementStatus;
import com.revature.models.Employee;
import com.revature.models.ReimbursementRequest;
import com.revature.util.StatusAlreadyUpdatedException;

public class EmployeeService {
	
	public static Employee employee;
	
	private EmployeeService() {}
	
	public static Employee getEmployeeFromDB(String username, String password) {
		employee = null; //If someone trys to log in while someone else is logged in, this will erase(effictively log out) the current user
		
		try {
			employee = EmployeeDao.selectEmployeeByUserPass(username, password);
		} catch (SQLException ex) {
			System.out.println("Username/password did not match employee in DB.");
		}
		
		return employee;
	}
	
	public static void submitRequest(ReimbursementRequest r) throws SQLException, InvalidFormatException, JsonParseException {
		String details = "Submitted request";
		ReimbursementsLogDao.insertLog(r, details);
	}

	//Insures correct decimal format was inputted my employee for their amount double. Rounds down because it's assuming employee accidentally put in an extra digit after the 2nd decimal place.
	public static double correctAmountFormat(ReimbursementRequest r) {
		return BigDecimal.valueOf(r.getAmount()).setScale(2, RoundingMode.DOWN).doubleValue();
	}

	public static String displayPendingRequests() {
		String s = "ERROR";
		try {
			s = ReimbursementsLogDao.selectPendingRequests().toString();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return s;
	}

	public static void approveOrDenyRequest(int id, ReimbursementStatus status) throws SQLException, StatusAlreadyUpdatedException {
		ReimbursementRequest r = ReimbursementsLogDao.selectLogById(id);
		if (r.getStatus() == ReimbursementStatus.PENDING) {
			ReimbursementsLogDao.updateReimbursementStatus(id, status);
			ReimbursementRequest newR = new ReimbursementRequest(id, employee, r.getTimestamp(), r.getCategory(), status, r.getAmount());
			ReimbursementsLogDao.insertLog(newR, newR.getDetails());
		} else {
			throw new StatusAlreadyUpdatedException("That request has already been approved/denied.");
		}
	}

	public static String viewAllHistory() {
		String s = "";
		try {
			s = ReimbursementsLogDao.selectAllLogs().toString();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return s;
	}
	
	public static String viewEmployeeHistory(int id) {
		String s = "";
		try {
			s = ReimbursementsLogDao.selectLogsByEmployee(id).toString();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return s;
	}
	
}
