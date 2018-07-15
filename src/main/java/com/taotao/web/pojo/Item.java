package com.taotao.web.pojo;

import org.apache.commons.lang3.StringUtils;

public class Item extends com.taotao.manage.pojo.Item {

	public String[] getImages(){
		return StringUtils.split(this.getImage(),",");
	}
}
