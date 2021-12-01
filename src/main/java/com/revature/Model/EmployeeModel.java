package com.revature.Model;

public class EmployeeModel {

	private String employeeName;
	private int employeeId;
	private String position;
	private String password;
	
	public EmployeeModel() {
		super();
	}
	public int getEmployeeId() {
		return employeeId;
	}
	public EmployeeModel(String employeeName, int employeeId, String position, String password) {
		super();
		this.employeeName = employeeName;
		this.employeeId = employeeId;
		this.position = position;
		this.password = password;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public String getPosition() {
		return position;
	}
	public String getPassword() {
		return password;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
