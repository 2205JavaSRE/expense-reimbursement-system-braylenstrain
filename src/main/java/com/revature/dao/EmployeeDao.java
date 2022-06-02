package com.revature.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.models.Employee;
import com.revature.util.ConnectionFactory;

public class EmployeeDao {
	
	private EmployeeDao() {}

	public static Employee selectEmployeeByUserPass(String username, String password) throws SQLException {
		String sqlSelect = "SELECT * FROM employees WHERE username = ? AND password = ?";
		
		try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sqlSelect)) {
			ps.setString(1, username);
			ps.setString(2, password);
			
			//Put results  into ResultSet and grab first/only result
			ResultSet rs = ps.executeQuery();
			rs.next();
		
			return new Employee(rs.getInt("id"), rs.getString("first_name"), rs.getString("last_name"), rs.getBoolean("finance_manager"));
		}
	}
	
}
