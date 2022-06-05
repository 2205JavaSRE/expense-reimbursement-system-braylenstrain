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
		
		app.post("/login", EmployeeController::authenticateEmployee);
		
		app.get("/logout", EmployeeController::logout);
		
		app.post("/reimbursements/request", EmployeeController::requestReimbursement);
		
		app.get("/reimbursements/pending", EmployeeController::pendingRequests);
		
		app.post("/reimbursements/pending", EmployeeController::approveOrDenyRequest);
		
		app.get("/reimbursements/history", ctx -> {
			//TODO Non finance managers will get redirected with their own id
		});
		
		app.get("/reimbursements/history/{id}", ctx -> {
			//TODO if nfm, id must match current employee's id
		});
	}
}
