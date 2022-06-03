package com.revature.controller;

import com.revature.models.Employee;
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

	public static void checkForSession(Context ctx) {
		Employee e = ctx.sessionAttribute("employee");
		if (e == null) {
			ctx.redirect("/login", 401);
		}
	}
	
	//TODO forbidden method for when nonfinance managers visit finance manager only pages
	
	public static void logout(Context ctx) {
		ctx.consumeSessionAttribute("employee");
		ctx.result("Logout successful.");
	}
	
}
