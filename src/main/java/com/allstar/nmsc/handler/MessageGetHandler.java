
package com.allstar.nmsc.handler;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.HeaderMap;
import io.undertow.util.HttpString;

import com.alibaba.fastjson.JSONObject;
import com.allstar.nmsc.scylla.dao.MessageDao;
import com.allstar.nmsc.scylla.repository.MessageEntity;

public class MessageGetHandler implements HttpHandler {
	
	@Override
	public void handleRequest(HttpServerExchange exchange) throws Exception {

		exchange.getResponseHeaders().add(new HttpString("Content-Type"), "application/json");
		// get request headers
		HeaderMap map = exchange.getRequestHeaders();
		String userId = map.getFirst("userId");
		String messagekey = map.getFirst("messagekey");

		System.out.println("----->> userId from request:" + userId);
		
		MessageEntity entity = new MessageDao().test(messagekey);
		
		JSONObject json = new JSONObject();
		json.put("respcode", "1");
		json.put("msg", "OK");

		JSONObject response = new JSONObject();
		if(entity!=null){
			response.put("sessionKey", entity.getSession_key());
			response.put("messageId", entity.getMessage_id());
			response.put("messageIndex", entity.getMessage_index());
			response.put("messageStatus", entity.getMessage_status());
			response.put("senderId", entity.getSender_id());
			response.put("receiverId", entity.getReceiver_id());
			response.put("message", entity.getMessage_content());
		}
		json.put("response", response);
		
		exchange.getResponseSender().send(json.toJSONString());
	}
}
