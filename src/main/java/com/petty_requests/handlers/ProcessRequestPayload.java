package com.petty_requests.handlers;

import com.petty_requests.main.Validable;

import lombok.Data;

public @Data class ProcessRequestPayload implements Validable {
	private int status;
	private String requestId;
	
	public boolean isValid() {
		return !requestId.isEmpty() && status > -1;
	}

}
