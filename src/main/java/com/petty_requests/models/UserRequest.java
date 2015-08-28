package com.petty_requests.models;

import lombok.Data;

public @Data class UserRequest{
	private String userId;
	private String requestId;
	private String reason;
	private String modDate;
	private String createdOn;
	private float requestAmount;
	private int approvalCount;
	private int status;
	private int organisationId;

	
}
