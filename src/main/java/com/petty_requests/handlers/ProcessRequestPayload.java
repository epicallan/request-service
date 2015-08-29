package com.petty_requests.handlers;

import com.petty_requests.Validable;

import lombok.Data;

public @Data class ProcessRequestPayload implements Validable {
	private int status;
	private String requestId;
	private String adminId;
	
	public boolean isValid() {
		return !adminId.isEmpty() && status > -1;
	}

}
