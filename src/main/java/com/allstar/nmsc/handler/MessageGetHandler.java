
package com.allstar.nmsc.handler;

import java.nio.ByteBuffer;

import org.springframework.util.Assert;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.HeaderMap;
import io.undertow.util.HttpString;

import com.alibaba.fastjson.JSONObject;
import com.allstar.nmsc.model.Response;
import com.allstar.nmsc.model.ResponseCode;
import com.allstar.nmsc.scylla.dao.MessageDao;
import com.allstar.nmsc.scylla.repository.MessageEntity;

public class MessageGetHandler implements HttpHandler {
	
	@Override
	public void handleRequest(HttpServerExchange exchange) {
		try {
			exchange.getResponseHeaders().add(new HttpString("Content-Type"), "application/json");
			// get request headers
			HeaderMap map = exchange.getRequestHeaders();
			String sessionKey = map.getFirst("sessionKey");

			Assert.notNull(sessionKey, "sessionKey must be not null.");
			
			MessageEntity entity = new MessageDao().findMessageBySessionKey(sessionKey);// 100001231100001441
			
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
				
				ByteBuffer buffer = entity.getMessage_content();
				byte[] b = new byte[buffer.remaining()];
				response.put("message", buffer.get(b, 0, b.length));
			}
			
			json.put("response", response);
			exchange.getResponseSender().send(json.toJSONString());
		} catch (Exception e) {
			e.printStackTrace();
			
//			JSONObject response = new JSONObject();
//			response.put("respcode", 2);
//			response.put("msg", e.getMessage());

			
			try {
				Response res = new Response(ResponseCode.OK);
				res.put("abc", "vin.");
				
				exchange.getResponseSender().send(res.toString());
				
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
}
