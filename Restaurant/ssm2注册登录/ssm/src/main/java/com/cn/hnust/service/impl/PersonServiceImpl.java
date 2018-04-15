package com.cn.hnust.service.impl;

import java.security.NoSuchAlgorithmException;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.cn.hnust.been.Person;
import com.cn.hnust.controller.LoginController;
import com.cn.hnust.dao.PersonMapper;
import com.cn.hnust.servic.IPersonService;
import com.lava.core.utils.MailUtils;

@Service("personService")
public class PersonServiceImpl implements IPersonService {

@Resource	
private PersonMapper personMapper;
private static Logger logger = Logger.getLogger(LoginController.class); 

	public String registerPerson(Person person) {
 	
		if(null ==person.getUsername() || null ==person.getPassword() || null ==person.getMail()){		 
			 return "请输入";
		 }
		
		if(null != personMapper.selectByUsername(person.getUsername())){
			return "用户名被使用";
		}
		if(null != personMapper.selectByMail(person.getMail())){
			return "邮箱被注册";
		}
					
			try {
			person=	MailUtils.sendMail(person);
			} catch (AddressException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(this.personMapper.insert(person) == -1){
				return "注册失败";
			}else{
				return "注册成功,请去邮箱查收验证";
			}
		
		
	}

	public String loginPerson(Person person) {
		Person personEnty = personMapper.selectByPwdAndEmail(person);
		if(null == personEnty){
			return "账号或密码错误！";
		}
		
		if(personEnty.getState() ==0){
			return "账号未激活";
		}
		
		
		return "登陆成功";
	}
	public boolean activatEmail(Person person) {
			
		Person personEnty = personMapper.selectByCodeAndEmail(person);
		if(null !=personEnty && personEnty.getState()==0 ){
		   personEnty.setState(1);	   
		   if(-1 != personMapper.updateStateByCode(personEnty)){
			   
			   logger.debug("ws--activatEmail-------");
			   return true;
		   }else {
			   logger.debug("ws--activatEmail----updateStateByCode---");
		   }
		   
		}		 
		return false;
	}

}
