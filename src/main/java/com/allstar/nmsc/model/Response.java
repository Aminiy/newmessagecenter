package com.allstar.nmsc.model;

import org.springframework.util.Assert;

import com.alibaba.fastjson.JSONObject;

public class Response {
	
	JSONObject resp = new JSONObject();
	public Response(ResponseCode code)
	{
		resp.put("respcode", code.getIndex());
		resp.put("msg", code.getName());
	}
	
	public Response()
	{
		resp.put("respcode", ResponseCode.OK.getIndex());
		resp.put("msg", ResponseCode.OK.getName());
	}
	
	public void put(String key, Object value) throws Exception
	{
		Assert.isNull(key, "key must be not null.");
		if(key.equalsIgnoreCase("respcode") || key.equalsIgnoreCase("msg"))
			throw new Exception("key value must not be respcode or msg.");
		
		resp.put(key, value);
	}
	
	public String toString()
	{
		return resp.toJSONString();
	}
}
