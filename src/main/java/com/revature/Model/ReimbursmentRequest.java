package com.revature.Model;

public class ReimbursmentRequest {
	
	private EmployeeModel employee;
	private int employeeId;
	private String description;
	private float amount;
	private int requestId;
	private String status;
	public ReimbursmentRequest() {
		super();
		employee = null;
		employeeId = -1;
		description = null;
		amount = -1;
		requestId = -1;
		status = null;
	}
	public ReimbursmentRequest(int employeeId, String description, float amount, int requestId, String status) {
		super();
		this.employeeId = employeeId;
		this.description = description;
		this.amount = amount;
		this.requestId = requestId;
		this.status = status;
	}
	public int getEmployeeId() {
		return employeeId;
	}
	public String getDescription() {
		return description;
	}
	public float getAmount() {
		return amount;
	}
	public int getRequestId() {
		return requestId;
	}
	public String getStatus() {
		return status;
	}
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public EmployeeModel getEmployee() {
		return employee;
	}
	public void setEmployee(EmployeeModel employee) {
		this.employee = employee;
	}	
}
