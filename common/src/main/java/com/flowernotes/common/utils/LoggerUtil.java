package com.flowernotes.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

/** 日志 */
public class LoggerUtil {
	/** 项目名 */
	private static String projectName;
     
	static {
		URL url = LoggerUtil.class.getResource("/");
		if(url != null) {
			String str = url.getFile();
			File f = new File(str);
			if (str.endsWith("bin/")) {
				projectName = f.getParentFile().getName();
			}
			else if (str.endsWith("classes/")) {
				projectName = f.getParentFile().getParentFile().getName();
			} else {
				projectName = f.getName();
			}
		} else {
			String tempStr = System.getProperty("java.class.path");
			if(tempStr == null) {
				projectName = "notknow";
			} else {
				tempStr = tempStr.replace("\\", "/");
				projectName = tempStr.substring(tempStr.lastIndexOf("/")+1, tempStr.lastIndexOf("."));
			}
		}
	}
	
	/** 获取当前项目的名称 */
	public static String getProjectName() {
		return projectName;
	}
	
	private Class<?> clazz;
	public LoggerUtil(Class<?> clazz) {
		this.clazz = clazz;
	}
	
	public LoggerUtil() {
		Class<?> clazz = null;
		try {
			String className = Thread.currentThread().getStackTrace()[2].getClassName();
			clazz = Thread.currentThread().getContextClassLoader().loadClass(className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		this.clazz = clazz;
	}

	/** 保存日志 */
	private static void saveLog() {
		//		LogBean lb = new LogBean(projectName, type, msg);
		//		try {
		//			RmiProxy.getLogService().add(lb);
		//		} catch (Exception e) {
		//			LoggerFactory.getLogger(LoggerUtil.class).error("项目名["+projectName+"]日志写入异常", e.getMessage());
		//		}
	}
	
	/** 获取log对象 */
	public Logger getLogger() {
		return LoggerFactory.getLogger(clazz);
	}
	/** 获取log对象 */
	public static Logger getLogger(Class<?> clazz) {
		return LoggerFactory.getLogger(clazz);
	}

	public static void debug(Class<?> clazz, String str) {
		str = str == null ? "" : str;
		LoggerFactory.getLogger(clazz).debug("项目名：[" + projectName + "]" + str);
		saveLog();
	}

	public static void debug(Class<?> clazz, String str, Object obj) {
		str = str == null ? "" : str;
		LoggerFactory.getLogger(clazz).debug("项目名：[" + projectName + "]" + str, obj);
		saveLog();
	}

	public static void debug(Class<?> clazz, String str, Object[] objs) {
		str = str == null ? "" : str;
		LoggerFactory.getLogger(clazz).debug("项目名：[" + projectName + "]" + str, objs);
		saveLog();
	}

	public static void debug(Class<?> clazz, Throwable e) {
		LoggerFactory.getLogger(clazz).debug("项目名：[" + projectName + "]", e);
	}

	public static void info(Class<?> clazz, String str) {
		str = str == null ? "" : str;
		LoggerFactory.getLogger(clazz).info("项目名：[" + projectName + "]" + str);
		saveLog();
	}

	public static void info(Class<?> clazz, String str, Object obj) {
		str = str == null ? "" : str;
		LoggerFactory.getLogger(clazz).info("项目名：[" + projectName + "]" + str, obj);
		saveLog();
	}

	public static void info(Class<?> clazz, String str, Object[] objs) {
		str = str == null ? "" : str;
		LoggerFactory.getLogger(clazz).info("项目名：[" + projectName + "]" + str, objs);
		saveLog();
	}

	public static void error(Class<?> clazz, String str) {
		str = str == null ? "" : str;
		LoggerFactory.getLogger(clazz).error("项目名：[" + projectName + "]" + str);
		saveLog();
	}

	public static void error(Class<?> clazz, String str, Object obj) {
		str = str == null ? "" : str;
		LoggerFactory.getLogger(clazz).error("项目名：[" + projectName + "]" + str, obj);
		saveLog();
	}

	public static void error(Class<?> clazz, String str, Object[] objs) {
		str = str == null ? "" : str;
		LoggerFactory.getLogger(clazz).error("项目名：[" + projectName + "]" + str, objs);
		saveLog();
	}

	public static void error(Class<?> clazz, String str, Throwable e) {
		str = str == null ? "" : str;
		LoggerFactory.getLogger(clazz).error("项目名：[" + projectName + "]" + str, e);
		saveLog();
	}

	public static void error(Class<?> clazz, Throwable e) {
		error(clazz, "", e);
	}
	
	public void debug(String str) {
		str = str == null ? "" : str;
		LoggerFactory.getLogger(clazz).debug("项目名：[" + projectName + "]" + str);
		saveLog();
	}

	public void debug(String str, Object obj) {
		str = str == null ? "" : str;
		LoggerFactory.getLogger(clazz).debug("项目名：[" + projectName + "]" + str, obj);
		saveLog();
	}

	public void debug(String str, Object[] objs) {
		str = str == null ? "" : str;
		LoggerFactory.getLogger(clazz).debug("项目名：[" + projectName + "]" + str, objs);
		saveLog();
	}

	public void debug(Throwable e) {
		LoggerFactory.getLogger(clazz).debug("项目名：[" + projectName + "]", e);
	}

	public void info(String str) {
		str = str == null ? "" : str;
		LoggerFactory.getLogger(clazz).info("项目名：[" + projectName + "]" + str);
		saveLog();
	}

	public void info(String str, Object obj) {
		str = str == null ? "" : str;
		LoggerFactory.getLogger(clazz).info("项目名：[" + projectName + "]" + str, obj);
		saveLog();
	}

	public void info(String str, Object[] objs) {
		str = str == null ? "" : str;
		LoggerFactory.getLogger(clazz).info("项目名：[" + projectName + "]" + str, objs);
		saveLog();
	}

	public void error(String str) {
		str = str == null ? "" : str;
		LoggerFactory.getLogger(clazz).error("项目名：[" + projectName + "]" + str);
		saveLog();
	}

	public void error(String str, Object obj) {
		str = str == null ? "" : str;
		LoggerFactory.getLogger(clazz).error("项目名：[" + projectName + "]" + str, obj);
		saveLog();
	}

	public void error(String str, Object[] objs) {
		str = str == null ? "" : str;
		LoggerFactory.getLogger(clazz).error("项目名：[" + projectName + "]" + str, objs);
		saveLog();
	}

	public void error(String str, Throwable e) {
		str = str == null ? "" : str;
		LoggerFactory.getLogger(clazz).error("项目名：[" + projectName + "]" + str, e);
		saveLog();
	}

	public void error(Throwable e) {
		error(clazz, "", e);
	}

	/**
	 * 记录系统启动日志
	 */
	public static void log(String logContent){
		File file;
		if (System.getProperty("os.name").contains("Windows")){
			file =  new File("F:/service.log");
		}else {
			file =  new File("/fs/work/service.log");
		}
		if (!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try{
			FileOutputStream fo = new FileOutputStream(file,true);
			byte[] data = logContent.getBytes("UTF-8");
			fo.write(data);
			fo.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
