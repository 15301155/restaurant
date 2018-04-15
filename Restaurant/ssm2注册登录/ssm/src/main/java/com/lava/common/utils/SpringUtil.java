package com.lava.common.utils;

import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringUtil implements ApplicationContextAware {

	/**
	 * ��ǰIOC
	 */
	private static ApplicationContext applicationContext;

	/**
	 * ���õ�ǰ�����Ļ������˷�����spring�Զ�װ��
	 */
	@Override
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		applicationContext = arg0;
	}

	/**
	 * �ӵ�ǰIOC��ȡbean
	 * 
	 * @param id
	 *            bean��id
	 * @return
	 */
	public static Object getBean(String id) {
		Object object = null;
		object = applicationContext.getBean(id);
		return object;
	}
	/**
	 * 
	 * @param <T>
	 * @param type
	 * @return
	 */
	public static <T>T getBean(Class<T> type) {
		return applicationContext.getBean(type);
	}
	/**
	 * 
	 * @param <T>
	 * @param type
	 * @return
	 */
	public static <T> Map<String, T> getBeansOfType(Class<T> type) {
		return applicationContext.getBeansOfType(type);
	}

}
