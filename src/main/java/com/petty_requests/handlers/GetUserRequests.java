package com.petty_requests.handlers;

import spark.Request;

import com.petty_requests.models.Model;

public class GetUserRequests extends AbstractRequestHandler {

	public GetUserRequests(Model model) {
		super(model);
	}

	@Override
	protected String processRequest(Request request) {
		String userId = request.params("user_id");
		result = model.getAllUserRequests(userId);
		return dataToJson(result);
	}
	
}
