package com.petty_requests.models;

import java.util.List;

import org.sql2o.Connection;

import com.petty_requests.handlers.NewUserRequestPayload;

public interface Model {

	boolean createUserRequest(NewUserRequestPayload userRequest);
	
	List<UserRequest> getAllUserRequests(String userId);
	
	ProcessedRequest editRequest(String requestId,String adminId);
	
	List<UserRequest> getAdminUserRequests(String admin);
}
