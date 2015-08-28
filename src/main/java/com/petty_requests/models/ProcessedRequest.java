package com.petty_requests.models;

import com.petty_requests.main.Validable;

public class ProcessedRequest extends UserRequest implements Validable{
	private int maxApprovals;
	
	ProcessedRequest() {
		super();
	}

	public boolean isValid() {
		return (maxApprovals > -1);
	}

	public int getMaxApprovals() {
		return maxApprovals;
	}

	public void setMaxApprovals(int maxApprovals) {
		this.maxApprovals = maxApprovals;
	}

}
