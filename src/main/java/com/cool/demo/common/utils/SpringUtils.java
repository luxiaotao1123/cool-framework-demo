package com.cool.demo.common.utils;

import com.core.common.BaseRes;
import com.core.exception.CoolException;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringUtils implements ApplicationContextAware {

	private static ApplicationContext application;
	
	private SpringUtils() {}

	/**
	 * 初始化容器对象
	 * @param context
	 */
	public static void init(ApplicationContext context) {
		SpringUtils.application = context;
	}
	
	private static ApplicationContext getContext() {
		if(application==null) {
			throw new CoolException(BaseRes.ERROR);
		}
		return application;
	}
	
	public static <T>T getBean(Class<T> prototype) {
		return getContext().getBean(prototype);
	}
	
	public static Object getBean(String name) {
		return getContext().getBean(name);
	}

	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		SpringUtils.application = context;
	}
	
}
