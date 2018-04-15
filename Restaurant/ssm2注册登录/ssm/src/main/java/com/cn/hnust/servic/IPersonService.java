package com.cn.hnust.servic;

import com.cn.hnust.been.Person;

public interface IPersonService {
	public String registerPerson(Person person);
	
	public String loginPerson(Person person);

	public boolean activatEmail(Person person);

	
}
