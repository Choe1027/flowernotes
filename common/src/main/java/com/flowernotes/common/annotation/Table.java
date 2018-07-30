package com.flowernotes.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 标记数据库表 */
@Target({ElementType.TYPE})    
@Retention(RetentionPolicy.RUNTIME)    
@Documented 
public @interface Table {
	
	/** 对应数据库表的表名 */
	String name() default "";
}
