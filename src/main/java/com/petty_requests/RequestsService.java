package com.petty_requests;

import com.petty_requests.SqlModel.SqlModel;
import com.petty_requests.handlers.CreateUserRequest;
import com.petty_requests.handlers.GetAdminRequests;
import com.petty_requests.handlers.GetUserRequests;
import com.petty_requests.handlers.UpdateUserRequest;
import com.petty_requests.models.Model;

import static spark.Spark.*;

public class RequestsService {
	Model model;
	public RequestsService(){
		model = new SqlModel();
	}
	
	public void  routes(){
		
		post("/user/requests/create",new CreateUserRequest(model));
		
		put("/user/requests/:request_id", new UpdateUserRequest(model)); 
		
		get("/user/requests/:user_id",new GetUserRequests(model)); 
		
		get("/admin/requests/:user_id",new GetAdminRequests(model)); 
	}

}
