package com.petty_requests.handlers;

import com.petty_requests.Validable;

import lombok.Data;

public @Data class NewUserRequestPayload implements Validable {
	private String userId;
	private String reason;
	private String organisationId;
	private float requestAmount;
 
	public boolean isValid() {
		return userId != null ;
	}


}
