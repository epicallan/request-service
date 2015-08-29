package com.petty_requests;

import spark.servlet.SparkApplication;

public class Bootstrap  implements SparkApplication {
	
	@Override
	public void init() {
		// TODO Auto-generated method stub
		RequestsService service = new RequestsService();
		service.routes();
		
	}
	
	
}
