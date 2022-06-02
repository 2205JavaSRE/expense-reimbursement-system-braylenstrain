package com.revature.models;

import java.util.Objects;

public class Employee {
	private int id;
	private String firstName;
	private String lastName;
	private boolean financeManager;
	
	public Employee(int id, String firstName, String lastName, boolean financeManager) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.financeManager = financeManager;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public boolean isFinanceManager() {
		return financeManager;
	}
	public void setFinanceManager(boolean financeManager) {
		this.financeManager = financeManager;
	}

	@Override
	public int hashCode() {
		return Objects.hash(financeManager, firstName, id, lastName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Employee))
			return false;
		Employee other = (Employee) obj;
		return financeManager == other.financeManager && Objects.equals(firstName, other.firstName) && id == other.id
				&& Objects.equals(lastName, other.lastName);
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", financeManager="
				+ financeManager + "]";
	}
	
}
