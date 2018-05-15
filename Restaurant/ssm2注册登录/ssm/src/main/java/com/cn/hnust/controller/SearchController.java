package com.cn.hnust.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cn.hnust.been.Comment;
import com.cn.hnust.been.Person;
import com.cn.hnust.been.Search;
import com.cn.hnust.dao.CommentMapper;
import com.cn.hnust.dao.PersonMapper;
import com.cn.hnust.dao.SearchMapper;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cn.hnust.been.Person;
import com.cn.hnust.servic.IPersonService;
import com.lava.core.utils.ResponseUtils;


@Controller  
@RequestMapping("/index")  
public class SearchController {
	private static Logger logger = Logger.getLogger(SearchController.class); 
	@Resource
	private SearchMapper searchmapper;
	@Resource
	private CommentMapper commentmapper;
	
	
	@RequestMapping(value="/single",method=RequestMethod.POST)
	private String search(java.lang.String search,HttpServletResponse response,Model model) throws IOException{	
		logger.debug(search+"1234563633333333333333333333333333333333333333333333333333333333333333");  
		List<Comment> commentEnty=new ArrayList<Comment>();
		Search searchEnty =searchmapper.selectbyname(search);
	    commentEnty=(List<Comment>) commentmapper.selectcommentByname(search);
		//Search searchEnty =null;
	    logger.debug(commentEnty+"9999999999999999999999999999999999999999999999999999999999999999999999");   
		logger.debug(searchEnty+"9999999999999999999999999999999999999999999999999999999999999999999999");   
		if(null == searchEnty){
			 response.setCharacterEncoding("utf-8");
			 PrintWriter out = response.getWriter();
			 out.print("<script>alert('Search failed, please try again.'); window.location='http://localhost:8080/ssm/pages/index.jsp' </script>");
			 out.flush();
			 out.close();
			return "index";
		}
		model.addAttribute("restaurant_name",searchEnty.getname());
		model.addAttribute("information",searchEnty.getinformation());
		model.addAttribute("imageId",searchEnty.getimageid());
		model.addAttribute("location",searchEnty.getlocation());
		model.addAttribute("avercost",searchEnty.getavercost());
		model.addAttribute("tel",searchEnty.gettel());
		if(null != commentEnty)
		{
			for(int i = 0;i < commentEnty.size(); i ++){
				model.addAttribute("restaurant_comment"+i,commentEnty.get(i).getname());
				model.addAttribute("comment"+i,commentEnty.get(i).getcomment());
				model.addAttribute("username"+i,commentEnty.get(i).getusername());
				}
		}
        return "single";
		
		
		
	}
	

}
