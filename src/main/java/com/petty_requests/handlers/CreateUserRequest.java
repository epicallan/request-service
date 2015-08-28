package com.petty_requests.handlers;
import spark.Request;
import com.petty_requests.models.Model;

public class CreateUserRequest extends AbstractRequestHandler <NewUserRequestPayload> {
	
	public CreateUserRequest(Model model) {
		super(model);
		
	}
	
	protected String processRequest(Request request) {
		NewUserRequestPayload payload =processPayload(request);
		if (processPayload(request).isValid()) {
			result = model.createUserRequest(payload);
		}
		return dataToJson(result);
	}

}
