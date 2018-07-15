package com.taotao.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.taotao.web.service.IndexService;

@Controller
public class IndexController {
  
	
	@Autowired
	private IndexService indexService;
	
	@RequestMapping(value="index")
	public ModelAndView toPage(){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("index");
		String ad1=this.indexService.queryAd1();
		String ad2=this.indexService.queryAd2();
		mv.addObject("ad1", ad1);
		mv.addObject("ad2", ad2);
		return mv;
	}
}
