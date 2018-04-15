package com.cn.hnust.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cn.hnust.been.User;
import com.cn.hnust.dao.UserMapper;
import com.cn.hnust.servic.IUserService;

@Service("userService")
public class UserServiceImpl implements IUserService {  
 @Resource
   private UserMapper userDao;

   public User getUserById(int userId) {  
       // TODO Auto-generated method stub  
       return this.userDao.selectByPrimaryKey(userId);  
}
   }