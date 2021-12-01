package com.revature.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.Controler.JavalinCentralControl;
import com.revature.Model.ReimbursmentRequest;

public class ReimbursmentDAO {
	static final String DB_ADRESS = "jdbc:postgresql://database-1.c1v26i6avxc8.us-east-2.rds.amazonaws.com:5432/postgres";
	static final String DB_USERNAME= "postgres";
	static final String DB_PASSWORD = "memememe";
	
	private static Connection connection = null;
	private static Statement statement = null;
	private static PreparedStatement preparedStmt = null;
	private static ResultSet result = null;
	
	private static Logger logger = LoggerFactory.getLogger(ReimbursmentDAO.class);
	
	public static void connect(){
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(DB_ADRESS, DB_USERNAME, DB_PASSWORD);
		}
		catch (Exception e) {
			logger.error(e + " ; occured with connect function");
		}
	}
	
	public static void closeResource() {
		
		try {
			if (result != null) { 
				result.close(); 
			} 
			if (preparedStmt != null) { 
				preparedStmt.close(); 
			} 
		    if (statement != null) { 
			    statement.close(); 
			} 
			if (connection != null) {
				connection.close(); 
			}
		}
		catch (Exception e) {
			logger.error(e + " ; occured with closeResource function");
		}
	}
	
	public static ArrayList<ReimbursmentRequest> findAll() {
		String query = "select * from requests.reimbursment_request";
		ArrayList<ReimbursmentRequest> employeeList = new ArrayList<ReimbursmentRequest>();
		try {
			connect();
			statement = connection.createStatement();
			result = statement.executeQuery(query);
			logger.info("Before findAll while loop");
			while (result.next()) {
				
				ReimbursmentRequest temp = new ReimbursmentRequest();
				
				temp.setEmployeeId(result.getInt("idEmployee"));
				logger.info(temp.getEmployeeId() + " gotten from data base");
				
				temp.setRequestId(result.getInt("idreimbursment_request"));
				logger.info(temp.getRequestId() + " gotten from data base");
				
				temp.setDescription(result.getString("description"));
				logger.info(temp.getDescription() + " gotten from data base");
				
				temp.setAmount(result.getFloat("amount_requested"));
				logger.info(temp.getAmount() + " gotten from data base");
				
				temp.setStatus(result.getString("status"));
				logger.info(temp.getStatus() + " gotten from data base");
				
				temp.setEmployee(EmployeeDAO.findById(temp.getEmployeeId()));
				
				employeeList.add(temp);
			}
			
		}catch (Exception e) {
			logger.error(e + " ; occured with findAll function");
		}
		closeResource();
		return employeeList;
	}

	public static void updateStatus(int requestId, ReimbursmentRequest reimbursmentRequest) {
		try {
			connect();
			String query = "UPDATE requests.reimbursment_request SET status = ? WHERE idreimbursment_request = ?"; 
			preparedStmt = connection.prepareStatement(query);
			preparedStmt.setString(1, reimbursmentRequest.getStatus());
			preparedStmt.setInt(2, requestId);

			preparedStmt.executeUpdate();
			closeResource();
		}
		catch(Exception e) {
			logger.error(e + " ; occured with updateStatus function");
		}
	}
	
	public static void insert(ReimbursmentRequest reimbursmentRequest) {
		try {
			connect();
			String query = "insert into requests.reimbursment_request (idEmployee, amount_requested,status,description) values (?,?,?,?)"; 
			preparedStmt = connection.prepareStatement(query);
			preparedStmt.setString(3, reimbursmentRequest.getStatus());
			preparedStmt.setInt(1, reimbursmentRequest.getEmployeeId());
			preparedStmt.setFloat(2, reimbursmentRequest.getAmount());
			preparedStmt.setString(4, reimbursmentRequest.getDescription());
			preparedStmt.executeUpdate();
			closeResource();
		}
		catch(Exception e) {
			logger.error(e + " ; occured with updateStatus function");
		}
	}
}
