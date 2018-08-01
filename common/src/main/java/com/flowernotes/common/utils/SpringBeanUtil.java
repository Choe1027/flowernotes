package com.flowernotes.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

/** spring-bean手动获取工具类 */
@Component
public class SpringBeanUtil implements ApplicationContextAware {
	
	 // Spring应用上下文环境
    private static ApplicationContext applicationContext;
 
    /**
     * 实现ApplicationContextAware接口的回调方法，设置上下文环境
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    	SpringBeanUtil.applicationContext = applicationContext;
    }
 
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
 
    @SuppressWarnings("unchecked")
	public static <T> T getBean(String beanId) throws BeansException {
        return (T) applicationContext.getBean(beanId);
    }

    public static <T> T getBean(Class<T> requiredType) throws BeansException {
        return applicationContext.getBean(requiredType);
    }
    /** 依据类型获取所有子类(key为spring的id，value为对象实例) */
    public static <T> Map<String, T> getBeanOfType(Class<T> requiredType) throws BeansException {
        return applicationContext.getBeansOfType(requiredType);
    }
}
