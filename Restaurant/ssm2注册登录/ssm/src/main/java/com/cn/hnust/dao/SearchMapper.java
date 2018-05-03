package com.cn.hnust.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.cn.hnust.been.Search;;

public interface SearchMapper {

	Search selectbyname(String search);
		
}
