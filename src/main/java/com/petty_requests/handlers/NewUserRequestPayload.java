package com.petty_requests.handlers;

import lombok.Data;

public @Data class NewUserRequestPayload {
	private String userId;
	private String reason;
	private String organisationId;
	private float requestAmount;

	public boolean isValid() {
		return !userId.isEmpty() && !(requestAmount > -1);
	}


}
