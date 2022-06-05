package com.revature.models;

import java.sql.Timestamp;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.revature.enums.ReimbursementCategory;
import com.revature.enums.ReimbursementStatus;
import com.revature.service.EmployeeService;

public class ReimbursementRequest {
	private final int id;
	private final Employee employee;
	private final Timestamp timestamp;
	private final ReimbursementCategory category;
	private ReimbursementStatus status;
	private final double amount;
	private String details;

	@JsonCreator
	//Constructor for when an employee makes a request
	public ReimbursementRequest(@JsonProperty("category") ReimbursementCategory category,
			@JsonProperty("amount") double amount) {
		this.id = -1;
		this.employee = EmployeeService.employee;
		this.timestamp = null;
		this.category = category;
		this.status = ReimbursementStatus.PENDING;
		this.amount = amount;
	}

	//Constructor for when finance manager approves/denies a request
	public ReimbursementRequest(int id, Employee employee, Timestamp timestamp, ReimbursementCategory category,
			ReimbursementStatus status, double amount, String details) {
		this.id = id;
		this.employee = employee;
		this.timestamp = timestamp;
		this.category = category;
		this.status = status;
		this.amount = amount;
		this.details = details;
	}

	public int getId() {
		return id;
	}

	public Employee getEmployee() {
		return employee;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public ReimbursementCategory getCategory() {
		return category;
	}

	public ReimbursementStatus getStatus() {
		return status;
	}

	public void setStatus(ReimbursementStatus status) {
		this.status = status;
	}

	public double getAmount() {
		return amount;
	}
 
	public String getDetails() {
		return details;
	}
	
	public void setDetails(String details) {
		this.details = details;
	}

	@Override
	public int hashCode() {
		return Objects.hash(amount, category, details, employee, id, status, timestamp);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof ReimbursementRequest))
			return false;
		ReimbursementRequest other = (ReimbursementRequest) obj;
		return Double.doubleToLongBits(amount) == Double.doubleToLongBits(other.amount) && category == other.category
				&& Objects.equals(details, other.details) && Objects.equals(employee, other.employee) && id == other.id
				&& status == other.status && Objects.equals(timestamp, other.timestamp);
	}

	@Override
	public String toString() {
		return "ReimbursementRequest [id=" + id + ", employee=" + employee + ", timestamp=" + timestamp + ", category="
				+ category + ", status=" + status + ", amount=" + amount + ", details=" + details + "]";
	}
	
}
