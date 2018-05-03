package com.cn.hnust.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
@RequestMapping("/login")  
public class LoginController {
	private static Logger logger = Logger.getLogger(LoginController.class); 
	@Resource
	private IPersonService personService;
	
	@RequestMapping("/person")
	@ResponseBody
	private Object register(Person person){
		 
	   logger.debug("ws--register-------"); 
	   person.setState(0);
	   return ResponseUtils.sendSuccess(this.personService.registerPerson(person));		 
	}
	
	@RequestMapping("/activatemail")
	private String activatemail(String actiCode,String email){
		 
	    logger.debug("ws-----activatemail----actiCode="+actiCode+" email="+email); 
	    Person person = new Person();
		person.setActiCode(actiCode);
		person.setMail(email);	
		boolean isAc = this.personService.activatEmail(person);		

		if(isAc){//激活成功，5秒跳转
			return "activateCode";
		}else{
			 return "activateCode";	
		}
		  	 
	}
	
	@RequestMapping("/index")	
	private String login(Person person,ModelMap modelMap){	
		 logger.debug("ws-----activatemail----person.getPassword();="+person.getPassword()+" email="+person.getMail());   
		String result = this.personService.loginPerson(person);
		if(result.equals("登陆成功")){
			 return "index";	
		}
		 modelMap.addAttribute("result",result);
		 logger.debug("ws-----login----result="+result); 
	   return "redirect:/user/login";		 
	}
	
	
	

}
