package com.petty_requests.handlers;

import java.io.IOException;



import spark.Request;
import spark.Response;
import spark.Route;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.petty_requests.Validable;
import com.petty_requests.models.Model;

public abstract class AbstractRequestHandler <V extends Validable> implements Route {

	protected Request request;
	protected Model model;
	Integer FAILED = 400;
	Integer SUCCESS = 200;
	Object result;
	//Class<V> type;
	
	public AbstractRequestHandler(Model model) {
		this.model = model;

	}

	@Override
	public Object handle(Request request, Response response) throws Exception {
		String data = processRequest(request);
		Integer status;
		status = !data.isEmpty() || data == null ? SUCCESS : FAILED;
		response.status(status);
		return data;
	}
	
	public V processPayload(Request request,Class<V> type) {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		V payload = null;
		try {
			System.out.println(request.body());
			payload = objectMapper.readValue(request.body(),type);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return payload;
	}
	
	public static String dataToJson(Object data) {
		String json = null;
		try {
			Gson gson = new Gson();
			json = gson.toJson(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}

	abstract String processRequest(Request request);

}
