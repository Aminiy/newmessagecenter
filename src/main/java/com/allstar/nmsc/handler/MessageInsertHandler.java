package com.allstar.nmsc.handler;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.HeaderMap;
import io.undertow.util.HttpString;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.util.Assert;

import com.alibaba.fastjson.JSONObject;
import com.allstar.nmsc.scylla.dao.MessageDao;
import com.allstar.nmsc.scylla.repository.MessageEntity;

public class MessageInsertHandler implements HttpHandler {

	@Override
	public void handleRequest(HttpServerExchange exchange) {
		try {
			exchange.getResponseHeaders().add(new HttpString("Content-Type"), "application/json");
			HeaderMap map = exchange.getRequestHeaders();
			
			String messageId = map.getFirst("messageId");
			String from = map.getFirst("from");
			String to = map.getFirst("to");
			String groupId = map.getFirst("groupId");
			String message = map.getFirst("message");
			
			Assert.notNull(messageId, "messageId must be not null.");
			Assert.notNull(from, "sender user Id must be not null.");
			Assert.notNull(to, "receiver user Id must be not null.");
			Assert.notNull(groupId, "groupId must be not null.");
			Assert.notNull(message, "message must be not null.");
			
			long fromId = Long.valueOf(from);
			long toId = Long.valueOf(to);
			String sessionKey;
			
			if(fromId > toId)
				sessionKey= fromId + "" + toId;
			else
				sessionKey= toId + "" + fromId;
			
			long maxIndex = new MessageDao().getMaxIndex(sessionKey);
			long messIndex = maxIndex + 1;
			System.out.println("----->>MaxIndex from DB is:" + maxIndex);
			
			Map<String, String> map_ext = new HashMap<String, String>();
			map_ext.put("name", "vincent.ma");
			map_ext.put("age", "30");
			map_ext.put("like", "nice girl");
			
			MessageEntity entity = new MessageEntity();
			entity.setSession_key(sessionKey);
			entity.setMessage_id(messageId);
			entity.setMessage_index(messIndex);
			entity.setMessage_status(0);
			entity.setMessage_content(message);// ByteBuffer.wrap(message.getBytes())
			entity.setMessage_time(new Date(System.currentTimeMillis()));
			entity.setSender_id(Long.valueOf(from));
			entity.setReceiver_id(Long.valueOf(to));
			entity.setGroup_sender(Long.valueOf(groupId));
			entity.setDelflag_max(0);
			entity.setDelflag_min(0);
			entity.setMsg_ext(map_ext);
			new MessageDao().insertMessage(entity);
			
			// send response
			JSONObject resp = new JSONObject();
			JSONObject response = new JSONObject();
			resp.put("messageIndex", messIndex);
			response.put("respcode", 1);
			response.put("msg", "OK");
			response.put("resp", resp);
			exchange.getResponseSender().send(response.toJSONString());
		
		} catch (Exception e) {
			e.printStackTrace();
			
			JSONObject response = new JSONObject();
			response.put("respcode", 2);
			response.put("msg", e.getMessage());

			exchange.getResponseSender().send(response.toJSONString());
		}
	}
}
