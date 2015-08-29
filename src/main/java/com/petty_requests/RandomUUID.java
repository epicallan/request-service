package com.petty_requests;

import java.util.UUID;

public class RandomUUID {

	
	public static String GenerateUUID(){
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}
	
}
