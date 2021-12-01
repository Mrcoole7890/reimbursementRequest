package com.revature.Controler;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.revature.DAO.EmployeeDAO;
import com.revature.DAO.ReimbursmentDAO;
import com.revature.Model.EmployeeModel;
import com.revature.Model.ReimbursmentRequest;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;

public class JavalinCentralControl {
	
	private static Logger logger = LoggerFactory.getLogger(JavalinCentralControl.class);
	
    public static void main(String[] args) {
		Javalin app = Javalin.create(config -> {
			config.addStaticFiles("C:\\Users\\mrcoo\\OneDrive\\Desktop\\ReventureStuff\\ReimbursmentProgramP3\\src\\main\\resources\\static", Location.EXTERNAL);
		}).start(7000);
		
		app.get("/api/v1/employee/", ctx -> { 
			ArrayList<EmployeeModel> returnList = new ArrayList<EmployeeModel>();
			returnList = EmployeeDAO.findAll();
			Gson gson = new Gson();
			String myJSON = gson.toJson(returnList);
			logger.info(myJSON);
			ctx.json(myJSON);
		});
		
		app.get("/api/v1/reimbursmentRequest/", ctx -> { 
			ArrayList<ReimbursmentRequest> returnList = new ArrayList<ReimbursmentRequest>();
			returnList = ReimbursmentDAO.findAll();
			Gson gson = new Gson();
			String myJSON = gson.toJson(returnList);
			logger.info(myJSON);
			ctx.json(myJSON);
		});
		
		app.put("/api/v1/reimbursmentRequest/{requestId}",  ctx -> {
			ReimbursmentRequest reimbursmentRequest = new ReimbursmentRequest();
			logger.info(ctx.body() + " Update for requestID:" + ctx.pathParam("requestId"));
			JsonObject convertedObject = new Gson().fromJson(ctx.body(), JsonObject.class);
			reimbursmentRequest.setStatus(convertedObject.get("status").getAsString());
			ReimbursmentDAO.updateStatus(Integer.parseInt(ctx.pathParam("requestId")), reimbursmentRequest);
		});
		
		app.get("/api/v1/reimbursmentRequest/{employeeId}",  ctx -> { 
			ArrayList<ReimbursmentRequest> tempList = new ArrayList<ReimbursmentRequest>();
			ArrayList<ReimbursmentRequest> returnList = new ArrayList<ReimbursmentRequest>();
			tempList = ReimbursmentDAO.findAll();
			tempList.forEach((rr) -> {
				if(rr.getEmployeeId() == Integer.parseInt(ctx.pathParam("employeeId")))
					returnList.add(rr);
			});
			Gson gson = new Gson();
			String myJSON = gson.toJson(returnList);
			logger.info(myJSON);
			ctx.json(myJSON);
		});
		
		app.put("/api/v1/reimbursmentRequestSubmission", ctx -> {
			JsonObject convertedObject = new Gson().fromJson(ctx.body(), JsonObject.class);
			int employeeId = convertedObject.get("employeeId").getAsInt();
			float amount_requested = convertedObject.get("amount").getAsFloat();
			String request_description = convertedObject.get("description").getAsString();
			String request_status = "pending";
			logger.info("Request for employeeId: " + employeeId + " made <amount:"+ amount_requested +";description:"+ request_description +">");
			ReimbursmentRequest reimbursmentRequest = new ReimbursmentRequest();
			reimbursmentRequest.setAmount(amount_requested);
			reimbursmentRequest.setDescription(request_description);
			reimbursmentRequest.setEmployeeId(employeeId);
			reimbursmentRequest.setStatus(request_status);
			ReimbursmentDAO.insert(reimbursmentRequest);
		});
    }
}