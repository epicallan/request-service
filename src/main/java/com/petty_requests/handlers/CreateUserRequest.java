package com.petty_requests.handlers;

import java.io.IOException;
import java.util.HashMap;

import spark.Request;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petty_requests.models.Model;

public class CreateUserRequest extends AbstractRequestHandler {
	NewUserRequestPayload payload;

	public CreateUserRequest(Model model) {
		super(model);
		
	}

	private NewUserRequestPayload processPayload(Request request) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			payload = objectMapper.readValue(request.body(),
					NewUserRequestPayload.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return payload;
	}
	
	
	protected String processRequest(Request request) {
		if (processPayload(request).isValid()) {
			result = model.createUserRequest(payload);
		}
		return dataToJson(result);
	}

	
	


}
