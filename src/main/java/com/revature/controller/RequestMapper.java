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
		
		app.get("/login", ctx -> {
			ctx.result("You need to log in before viewing any other pages.");
		});
		
		app.post("/login", EmployeeController::authenticateEmployee);
		
		app.get("/logout", EmployeeController::logout);
		
		app.post("/reimbursements/request", EmployeeController::requestReimbursement);
		
		app.get("/reimbursements/pending", EmployeeController::pendingRequests);
		
		app.post("/reimbursements/pending", EmployeeController::approveOrDenyRequest);
		
		app.get("/history", EmployeeController::viewAllHistory);
		
		app.get("/history/{id}", EmployeeController::viewEmployeeHistory);
	}
}
