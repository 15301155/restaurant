package com.cn.hnust.service.impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.cn.hnust.been.Search;
import com.cn.hnust.controller.LoginController;
import com.cn.hnust.controller.SearchController;
import com.cn.hnust.dao.PersonMapper;
import com.cn.hnust.dao.SearchMapper;
import com.cn.hnust.servic.IPersonService;
import com.cn.hnust.servic.ISearchService;

@Service("searchService")
public class SearchServiceImpl implements ISearchService{
	
	@Resource	
	private SearchMapper searchMapper;
	private static Logger logger = Logger.getLogger(SearchController.class); 


	public String Search(String search) {
		logger.debug(search+"1234563633333333333333333333333333333333333333333333333333333333333333");
		Search searchEnty = searchMapper.selectbyname(search);
		 
		if(null == searchEnty){
			return "’À∫≈ªÚ√‹¬Î¥ÌŒÛ£°";
		}
		
		
		return "µ«¬Ω≥…π¶";
	}

}
