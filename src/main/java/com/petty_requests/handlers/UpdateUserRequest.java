package com.petty_requests.handlers;

import spark.Request;

import com.petty_requests.models.Model;
import com.petty_requests.models.ProcessedRequest;

public class UpdateUserRequest extends AbstractRequestHandler {

	public UpdateUserRequest(Model model) {
		super(model);
	}

	@Override
	protected String processRequest(Request request) {
		String requestId = request.params("request_id");
		String adminId = request.params("admin_id");
		ProcessedRequest processedRequest = model.editRequest(requestId, adminId);
		return dataToJson(processedRequest);
	}

}
