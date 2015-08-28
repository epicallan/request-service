package com.petty_requests.handlers;

import java.util.HashMap;

import spark.Request;
import spark.Response;
import spark.Route;
import com.google.gson.Gson;
import com.petty_requests.models.Model;

public abstract class AbstractRequestHandler implements Route {

	protected Request request;
	protected Model model;
	Integer FAILED = 400;
	Integer SUCCESS = 200;
	Object result;
	HashMap<String, Object> responseMap;

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
