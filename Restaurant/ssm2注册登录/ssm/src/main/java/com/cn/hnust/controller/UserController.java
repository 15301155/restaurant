
package com.cn.hnust.controller;  
      
import javax.annotation.Resource;  
import javax.servlet.http.HttpServletRequest;     

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;  
import org.springframework.ui.Model;  
import org.springframework.web.bind.annotation.RequestMapping;  
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.cn.hnust.been.User;
import com.cn.hnust.servic.IUserService;


      
  
     //输入地址：localhost:8080/项目名称/user/showUser?id=1 
    @Controller  
    @RequestMapping("/user")  
    public class UserController {  
    	private static Logger logger = Logger.getLogger(UserController.class); 
        @Resource  
        private IUserService userService;
              
    	@RequestMapping("/showUser")
    	public String toIndex(HttpServletRequest request,Model model){
    		
            logger.debug("ws---0011--------"); 
    		int userId = Integer.parseInt(request.getParameter("id"));
    		User user = this.userService.getUserById(userId);
    		model.addAttribute("user", user);
    		return "showUser";
    	} 
        
         @RequestMapping("/login")  
        public String toIndex(){
        	    System.out.println("wangshuo....show_template");
            logger.debug("wangshuo---0011--------");              
            return "login";  
        }
          
   
        
    /*    @RequestMapping(value="/showUser",method=RequestMethod.GET)  
        public String toIndex2(@RequestParam() Integer userId ,Model model){  
            //int userId = Integer.parseInt(request.getParameter("id")); 
        	logger.debug("wangshuo-----------");
            User user = this.userService.getUserById(userId);  
            model.addAttribute("user", user);  
            return "showUser";  
        }*/ 
   
    }  
