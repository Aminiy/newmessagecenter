package com.allstar.nmsc.handler;

import com.alibaba.fastjson.JSONObject;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.HttpString;

public class MessageInsertHandler implements HttpHandler {

	@Override
	public void handleRequest(HttpServerExchange exchange) throws Exception {

		exchange.getResponseHeaders().add(new HttpString("Content-Type"), "application/json");
		
		
		
		//
		JSONObject response = new JSONObject();
		response.put("msg", "OK");
		response.put("code", 1);
		
		exchange.getResponseSender().send(response.toJSONString());

	}
}
