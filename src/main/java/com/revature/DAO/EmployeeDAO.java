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
import com.revature.Model.EmployeeModel;

public class EmployeeDAO {
	static final String DB_ADRESS = "jdbc:postgresql://database-1.c1v26i6avxc8.us-east-2.rds.amazonaws.com:5432/postgres";
	static final String DB_USERNAME= "postgres";
	static final String DB_PASSWORD = "memememe";
	
	private static Connection connection = null;
	private static Statement statement = null;
	private static PreparedStatement preparedStmt = null;
	private static ResultSet result = null;
	
	private static Logger logger = LoggerFactory.getLogger(EmployeeDAO.class);

	public static void connect(){
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(DB_ADRESS, DB_USERNAME, DB_PASSWORD);
		}
		catch (Exception e) {
			logger.error(e + " ; occured");
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
			logger.error(e + " ; occured");
		}
	}
	
	public static ArrayList<EmployeeModel> findAll() {
		connect();
		String query = "select * from requests.employee";
		ArrayList<EmployeeModel> employeeList = new ArrayList<EmployeeModel>();
		try {
			statement = connection.createStatement();
			result = statement.executeQuery(query);
			logger.info("Before findAll while loop");
			while (result.next()) {
				EmployeeModel temp = new EmployeeModel();
				temp.setEmployeeId(result.getInt(1));
				logger.info(temp.getEmployeeId() + " gotten from data base");
				temp.setEmployeeName(result.getString(2));
				logger.info(temp.getEmployeeName() + " gotten from data base");
				temp.setPassword(result.getString(4));
				logger.info(temp.getPassword() + " gotten from data base");
				temp.setPosition(result.getString(3));
				logger.info(temp.getPosition() + " gotten from data base");
				employeeList.add(temp);
			}
			
		}catch (Exception e) {
			logger.error(e + " ; occured");
		}
		closeResource();
		return employeeList;

	}
	
	public static EmployeeModel findById(int id) {
		connect();
		String query = "SELECT * FROM requests.employee WHERE \"idEmployee\"=?";
		
		EmployeeModel employee = new EmployeeModel();
		try {
			preparedStmt = connection.prepareStatement(query);
			preparedStmt.setInt(1,id);
			result = preparedStmt.executeQuery();
			logger.info("Before findAll while loop");
			while (result.next()) {
				EmployeeModel temp = new EmployeeModel();
				temp.setEmployeeId(result.getInt("idemployee"));
				logger.info(temp.getEmployeeId() + " gotten from data base");
				temp.setEmployeeName(result.getString("name"));
				logger.info(temp.getEmployeeName() + " gotten from data base");
				temp.setPassword(result.getString("password"));
				logger.info(temp.getPassword() + " gotten from data base");
				temp.setPosition(result.getString("position"));
				logger.info(temp.getPosition() + " gotten from data base");
				employee = temp;
			}
			
		}catch (Exception e) {
			logger.error(e + " ; occured");
		}
		closeResource();
		return employee;
	}
}
