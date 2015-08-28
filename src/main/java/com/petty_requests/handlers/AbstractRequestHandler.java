package com.petty_requests.handlers;


import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;

import spark.Request;
import spark.Response;
import spark.Route;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.petty_requests.models.Model;

public abstract class AbstractRequestHandler implements Route{

	protected Request request;
	protected Model model;
	Integer FAILED = 400;
	Integer SUCCESS = 200;
	Object result;
	HashMap<String,Object> responseMap;

	public AbstractRequestHandler(Model model) {
		this.model = model;
	}

	@Override
	public Object handle(Request request, Response response) throws Exception {
		String data =  processRequest(request);
		Integer status;
		status =!data.isEmpty()?SUCCESS:FAILED;
		response.status(status);
		return data;
	}
	
	public static String dataToJson(Object data) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            StringWriter sw = new StringWriter();
            mapper.writeValue(sw, data);
            return sw.toString();
        } catch (IOException e){
            throw new RuntimeException("IOException from a StringWriter?");
        }
    }
	
	protected String processRequest(Request request){
		return null;
		}


}
