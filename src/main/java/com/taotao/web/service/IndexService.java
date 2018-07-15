package com.taotao.web.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.taotao.common.service.ApiService;

@Service
public class IndexService {

	@Autowired
	private ApiService apiService;
	
	@Value("${MANANGE.TAOTAO.COM}")
	private String MANANGE_TAOTAO_COM;
	
	@Value("${INDEX.AD1}")
	private String INDEX_AD1;
	
	@Value("${INDEX.AD2}")
	private String INDEX_AD2;
	
	
	private final static ObjectMapper MAPPER = new ObjectMapper();
	public String queryAd1() {
		try{
			String url = MANANGE_TAOTAO_COM+INDEX_AD1;
			String rpResult = this.apiService.doGet(url);
			JsonNode jsonNode = MAPPER.readTree(rpResult);
			
			ArrayNode rows = (ArrayNode) jsonNode.get("rows");
			
			List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
			
		    for(JsonNode row : rows){
		    	    Map<String,Object> map = new LinkedHashMap<String,Object>();
		    	    map.put("srcB", row.get("pic").asText());
		    	    map.put("height", 240);
		    	    map.put("alt", row.get("title").asText());
		    	    map.put("width", 670);
		    	    map.put("src", row.get("pic").asText());
		    	    map.put("widthB", 550);
		    	    map.put("href", row.get("url").asText());
		    	    map.put("heightB", 240);
		    	    result.add(map);
		    }
			return MAPPER.writeValueAsString(result);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	public String queryAd2() {
		try{
			String url = MANANGE_TAOTAO_COM+INDEX_AD2;
			String rpResult = this.apiService.doGet(url);
			JsonNode jsonNode = MAPPER.readTree(rpResult);
			
			ArrayNode rows = (ArrayNode) jsonNode.get("rows");
			
			List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
			
		    for(JsonNode row : rows){
		    	    Map<String,Object> map = new LinkedHashMap<String,Object>();
		    	    map.put("width", 310);
		    	    map.put("height", 70);
		    	    map.put("src", row.get("pic").asText());
		    	    map.put("href", row.get("url").asText());
		    	    map.put("alt", row.get("title").asText());
		    	    map.put("widthB", 210);
		    	    map.put("heightB", 70);
		    	    map.put("srcB", row.get("pic").asText());
		    	    result.add(map);
		    }
			return MAPPER.writeValueAsString(result);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

}
