package com.taotao.web.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.taotao.common.service.ApiService;
import com.taotao.common.service.RedisService;
import com.taotao.manage.pojo.ItemDesc;
import com.taotao.manage.pojo.ItemParamItem;
import com.taotao.web.pojo.Item;

@Service
public class ItemService {

	@Autowired
	private ApiService apiService;
	
	private final static ObjectMapper MAPPER  = new ObjectMapper();
	
	@Value("${MANANGE.TAOTAO.COM}")
	private String MANANGE_TAOTAO_COM;
	
	@Autowired
	private RedisService redisService;
	
	private final static Integer REDIS_TIME = 60 * 60 * 24;
	
	private final static String REDIS_KEY = "TAOTAO.WEB.ITEM.DETAIL";
	
	public Item queryItemById(Long itemId) {
		
		try{
			String key = REDIS_KEY + itemId;
			String cacheData = this.redisService.get(key);
			if(StringUtils.isNotEmpty(cacheData)){
				return MAPPER.readValue(cacheData, Item.class);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		try{
			String url = MANANGE_TAOTAO_COM + "/rest/api/item/"+itemId;
			String jsonData = this.apiService.doGet(url);
			
			try{
				this.redisService.set(REDIS_KEY+itemId, jsonData, REDIS_TIME);
			}catch(Exception e){
				e.printStackTrace();
			}
			return MAPPER.readValue(jsonData, Item.class);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}


	public ItemDesc queryDescByItemId(Long itemId) {
		try{
			String url = MANANGE_TAOTAO_COM + "/rest/api/item/desc/" + itemId;
			String jsonData = this.apiService.doGet(url);
			return MAPPER.readValue(jsonData, ItemDesc.class);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}


	public String queryItemParamByItemId(Long itemId) {
		try{
			String url = MANANGE_TAOTAO_COM + "/rest/api/item/param/item/query/" + itemId;
			String jsonData = this.apiService.doGet(url);
			if(StringUtils.isNotBlank(jsonData)){
				ItemParamItem itemParamItem = MAPPER.readValue(jsonData, ItemParamItem.class);
				String paramStr = itemParamItem.getParamData();
				
				ArrayNode nodes = (ArrayNode) MAPPER.readTree(paramStr);
				StringBuilder sb = new StringBuilder();
				sb.append("<table class='Patble' border='0' cellpadding='0' cellspacing='1' width='100%'><tbody>");
				for(JsonNode node : nodes){
					sb.append("<tr><th class 'tdTitle colspan='2'>");
					sb.append(node.get("group").asText());
					sb.append("</th></tr><tr></tr>");
				    ArrayNode paramNodes = (ArrayNode) node.get("params");
				    for(JsonNode paramNode : paramNodes){
				    	    sb.append("<tr>");
				    	    sb.append("<td class='tdTitle>'"+paramNode.get("k").asText()+"</td>");
				    	    sb.append("<td>"+paramNode.get("v").asText()+"</td>");
				    	    sb.append("</tr>");
				    }
				}
				sb.append("</tbody></table>");
				return sb.toString();
			}
		}catch(Exception e){
		   e.printStackTrace();	
		}
		return null;
	}

}
