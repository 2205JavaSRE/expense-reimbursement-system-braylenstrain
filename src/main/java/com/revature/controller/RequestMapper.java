package com.revature.controller;

import io.javalin.Javalin;

public class RequestMapper {
	
	
	public void configureRoutes(Javalin app) {
		
		//Check for session id 
		app.before(ctx -> {
			if (!ctx.path().equals("/login")) {
				EmployeeController.checkForSession(ctx);
			}
		});
		
		app.post("/login", ctx -> {
			EmployeeController.authenticateEmployee(ctx);
		});
		
		app.get("/logout", ctx -> {
			EmployeeController.logout(ctx);
		});
		
		
	}
}
