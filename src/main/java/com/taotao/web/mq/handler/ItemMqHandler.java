package com.taotao.web.mq.handler;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.common.service.RedisService;

public class ItemMqHandler {

	private static final ObjectMapper MAPPER = new ObjectMapper();
	
	private final static String REDIS_KEY = "TAOTAO.WEB.ITEM.DETAIL";
	@Autowired
	private RedisService redisService;
	
	
	public void execute(String msg){
		try{
			JsonNode jsonNode = MAPPER.readTree(msg);
			long itemId = jsonNode.get("itemId").asLong();
			String key = REDIS_KEY + itemId;
			this.redisService.del(key);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
