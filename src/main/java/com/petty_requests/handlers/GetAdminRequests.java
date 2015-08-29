package com.petty_requests.handlers;

import spark.Request;

import com.petty_requests.models.Model;

public class GetAdminRequests extends AbstractRequestHandler<EmptyPayload>{

	public GetAdminRequests(Model model) {
		super(model);
		
	}

	@Override
	protected String processRequest(Request request) {
		String adminId = request.params("user_id");
		result = model.getAdminUserRequests(adminId);
		return dataToJson(result);
	}
}
