package com.revature.controller;

import java.sql.SQLException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.revature.dao.ReimbursementsLogDao;
import com.revature.models.Employee;
import com.revature.models.ReimbursementRequest;
import com.revature.service.EmployeeService;

import io.javalin.http.Context;
import io.javalin.http.HttpCode;

public class EmployeeController {

	private EmployeeController() {}
	
	public static void authenticateEmployee(Context ctx) {
		String username = ctx.formParam("username");
		String password = ctx.formParam("password");
		
		Employee e = EmployeeService.getEmployeeFromDB(username, password);
		
		if (e != null) {
			ctx.sessionAttribute("employee", e);
			ctx.result("You are logged in. Welcome " + e.getFirstName() + " " + e.getLastName() + "!");
		} else {
			ctx.result("Incorrect username/password.");
			ctx.status(HttpCode.UNAUTHORIZED);
		}
	}
	
	public static void logout(Context ctx) {
		ctx.consumeSessionAttribute("employee");
		ctx.result("Logout successful.");
	}

	public static void checkForSession(Context ctx) {
		Employee e = ctx.sessionAttribute("employee");
		if (e == null || EmployeeService.employee == null) {
			ctx.redirect("/login", 401); //Unauthorized
		}
	}
	
	public static void requestReimbursement(Context ctx) {
		try {
			EmployeeService.submitRequest(ctx.bodyAsClass(ReimbursementRequest.class));
			ctx.result("Request submitted.");
			ctx.status(HttpCode.CREATED);
		} catch (SQLException e) {
			ctx.result("Invalid format for request. Double check your input and try again.\n e.g. {\"category\": \"FOOD\", \"amount\":32.05}\nAlso make sure amount > 0.");
			ctx.status(HttpCode.BAD_REQUEST);
		} catch (InvalidFormatException e) {
			ctx.result("Invalid input.\n-Options for category are [OTHER, TRAVEL, LODGING, FOOD]\n-Make sure amount is a number");
			ctx.status(HttpCode.BAD_REQUEST);
		} catch (JsonParseException e) {
			ctx.result("Make sure your amount starts with a digit and not .");
			ctx.status(HttpCode.BAD_REQUEST);
		}
		
	}

	public static void pendingRequests(Context ctx) {
		if(EmployeeService.employee.isFinanceManager())
			ctx.result(EmployeeService.displayPendingRequests());
		else {
			ctx.result("You are not allowed to view this page. Finance Manager access only.");
			ctx.status(HttpCode.FORBIDDEN);
		}
	}

	public static void approveOrDenyRequest(Context ctx) {
		// TODO Auto-generated method stub
		
	}
	
}
