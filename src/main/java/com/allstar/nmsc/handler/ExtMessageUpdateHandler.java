package com.allstar.nmsc.handler;

import java.util.HashMap;

import org.springframework.util.Assert;

import com.allstar.nmsc.model.Response;
import com.allstar.nmsc.model.ResponseCode;
import com.allstar.nmsc.scylla.dao.MessageDao;
import com.networknt.body.BodyHandler;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;

/**
 * update msg_ext column, msg_ext = value
 * 
 * @author aminiy
 *
 */
public class ExtMessageUpdateHandler implements HttpHandler{

	@SuppressWarnings("unchecked")
	@Override
	public void handleRequest(HttpServerExchange exchange) throws Exception {
		try {
			HashMap<String, String>  bodyMap = (HashMap<String, String>) exchange.getAttachment(BodyHandler.REQUEST_BODY);
			String to = bodyMap.get("to");
			String from = bodyMap.get("from");
			String extMap = bodyMap.get("extMap");// {'key1':'value1','key2':'value2'}
			String messageIndex = bodyMap.get("messageIndex");
			
			Assert.notNull(from, "from must be not null.");
			Assert.notNull(to, "to must be not null.");
			Assert.notNull(extMap, "extMap must be not null.");
			Assert.notNull(messageIndex, "messageIndex must be not null.");
			
			String sessionKey;
			long fromId = Long.valueOf(from);
			long toId = Long.valueOf(to);
			
			if(fromId > toId)
				sessionKey= fromId + "" + toId;
			else
				sessionKey= toId + "" + fromId;
			
			new MessageDao().ExtMessageUpdate(sessionKey, messageIndex, extMap);
			
			Response resp = new Response(ResponseCode.OK);
			exchange.getResponseSender().send(resp.toString());
		} catch (Exception e) {
			e.printStackTrace();
			
			Response resp = new Response(ResponseCode.ERROR);
			exchange.getResponseSender().send(resp.toString());
		}
		exchange.endExchange();
	}

}
