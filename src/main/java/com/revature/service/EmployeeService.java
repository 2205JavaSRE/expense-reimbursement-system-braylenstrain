package com.revature.service;

import java.sql.SQLException;

import com.revature.dao.EmployeeDao;
import com.revature.models.Employee;

public class EmployeeService {
	
	public static Employee employee;
	
	private EmployeeService() {}
	
	public static Employee getEmployeeFromDB(String username, String password) {
		employee = null;
		
		try {
			employee = EmployeeDao.selectEmployeeByUserPass(username, password);
		} catch (SQLException e) {
			System.out.println("Username/password did not match employee in DB.");
		}
		
		return employee;
	}
	
}
