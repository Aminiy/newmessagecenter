
package com.allstar.nmsc.handler;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.HeaderMap;
import io.undertow.util.HttpString;

import org.springframework.util.Assert;

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
			String from = map.getFirst("from");
			String to = map.getFirst("to");
			String messageIndex = map.getFirst("messageIndex");
			Assert.notNull(from, "from must be not null.");
			Assert.notNull(to, "to must be not null.");
			Assert.notNull(messageIndex, "messageIndex must be not null.");
			
			String sessionKey;
			long fromId = Long.valueOf(from);
			long toId = Long.valueOf(to);
			
			if(fromId > toId)
				sessionKey= fromId + "" + toId;
			else
				sessionKey= toId + "" + fromId;
			
			MessageEntity entity = new MessageDao().findMessageByMsgIndex(sessionKey, Long.valueOf(messageIndex));// 100001231100001441
			
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
				
//				ByteBuffer buffer = entity.getMessage_content();
//				byte[] b = new byte[buffer.capacity()];
//				buffer.get(b, 0, b.length);
//				System.out.println("----string---" + new String (b));
				response.put("message", entity.getMessage_content());
			}
			
			json.put("response", response);
			exchange.getResponseSender().send(json.toJSONString());
		} catch (Exception e) {
			e.printStackTrace();
			try {
				Response res = new Response(ResponseCode.ERROR);
				exchange.getResponseSender().send(res.toString());
				
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
}
