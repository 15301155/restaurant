package com.lava.common.utils;

import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringUtil implements ApplicationContextAware {

	/**
	 * 当前IOC
	 */
	private static ApplicationContext applicationContext;

	/**
	 * 设置当前上下文环境，此方法由spring自动装配
	 */
	@Override
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		applicationContext = arg0;
	}

	/**
	 * 从当前IOC获取bean
	 * 
	 * @param id
	 *            bean的id
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
