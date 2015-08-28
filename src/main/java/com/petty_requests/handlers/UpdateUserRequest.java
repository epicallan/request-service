package com.petty_requests.handlers;

import spark.Request;

import com.petty_requests.models.Model;
import com.petty_requests.models.ProcessedRequest;

public class UpdateUserRequest extends AbstractRequestHandler<ProcessRequestPayload> {

	public UpdateUserRequest(Model model) {
		super(model);
	}

	@Override
	protected String processRequest(Request request) {
		ProcessRequestPayload payload = processPayload(request);
		ProcessedRequest processedRequest = null;
		if(payload.isValid()){
			processedRequest = model.editRequest(payload);
		}
		return dataToJson(processedRequest);
	}

}
