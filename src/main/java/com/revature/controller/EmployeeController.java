package com.revature.controller;

import java.sql.SQLException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.revature.enums.ReimbursementStatus;
import com.revature.models.Employee;
import com.revature.models.ReimbursementRequest;
import com.revature.service.EmployeeService;
import com.revature.util.StatusAlreadyUpdatedException;

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
		} catch (SQLException ex) {
			ctx.result("Invalid format for request. Double check your input and try again.\n e.g. {\"category\": \"FOOD\", \"amount\":32.05}\nAlso make sure amount > 0.");
			ctx.status(HttpCode.BAD_REQUEST);
		} catch (InvalidFormatException ex) {
			ctx.result("Invalid input.\n-Options for category are [OTHER, TRAVEL, LODGING, FOOD]\n-Make sure amount is a number");
			ctx.status(HttpCode.BAD_REQUEST);
		} catch (JsonParseException ex) {
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
		if(EmployeeService.employee.isFinanceManager()) {
			try {
				int id = Integer.parseInt(ctx.formParam("id"));
				ReimbursementStatus status = ReimbursementStatus.valueOf(ctx.formParam("approve/deny"));
				if (status != ReimbursementStatus.PENDING) {
					EmployeeService.approveOrDenyRequest(id, status);
					ctx.result("Status change completed.");
				} else {
					ctx.result("Please change approve/deny to APPROVED or DENIED.");
					ctx.status(HttpCode.BAD_REQUEST);
				}
			} catch (StatusAlreadyUpdatedException ex) {
				ctx.result("This request has already been approved/denied.");
				ctx.status(HttpCode.BAD_REQUEST);
			} catch (SQLException ex) {
				ctx.result("That log ID doesn't exist.");
				ctx.status(HttpCode.BAD_REQUEST);
			} catch (NumberFormatException ex) {
				ctx.result("Invalid id input.");
				ctx.status(HttpCode.BAD_REQUEST);
			} catch (IllegalArgumentException ex) {
				ctx.result("Invalid approve/deny input. (APPROVED, DENIED)");
				ctx.status(HttpCode.BAD_REQUEST);
			}
		} else {
			ctx.result("You are not allowed to view this page. Finance Manager access only.");
			ctx.status(HttpCode.FORBIDDEN);
		}
	}
	
	public static void viewAllHistory(Context ctx) {
		if (EmployeeService.employee.isFinanceManager()) {
			ctx.result(EmployeeService.viewAllHistory());
		} else {
			int id = EmployeeService.employee.getId();
			ctx.redirect("/history/" + id, 307); //Temporary Redirect
		}
	}
	
	public static void viewEmployeeHistory(Context ctx) {
		try {
			int id = Integer.parseInt(ctx.pathParam("id"));
			if (!EmployeeService.employee.isFinanceManager() && EmployeeService.employee.getId() != id) {
				ctx.result("You are not allowed to view this page. You can only view your own history.\n"
						+ "Please change URL to /history or /history/{yourownidnumber}");
				ctx.status(HttpCode.FORBIDDEN);
			} else {
				String s = EmployeeService.viewEmployeeHistory(id);
				if (s.length() == 0) {
					ctx.result("No logs found for employee ID " + id + ".");
				} else {
					ctx.result(s);
				}
			}
		} catch (NumberFormatException ex) {
			ctx.result("Invalid Path Parameter. Make sure to request /history/{id} e.g. /history/5");
			ctx.status(HttpCode.BAD_REQUEST);
		}
		
	}
	
}
