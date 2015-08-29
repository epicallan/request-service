package com.petty_requests.models;

import java.util.List;
import com.petty_requests.handlers.NewUserRequestPayload;
import com.petty_requests.handlers.ProcessRequestPayload;

public interface Model {

	boolean createUserRequest(NewUserRequestPayload userRequest);
	
	List<UserRequest> getAllUserRequests(String userId);
	
	ProcessedRequest editRequest(ProcessRequestPayload payload);
	
	List<UserRequest> getAdminUserRequests(String admin);
}
