package com.flowernotes.core.utils;


import com.flowernotes.common.base.BaseBean;
import com.flowernotes.common.exception.BaseException;
import com.flowernotes.common.exception.CommonException;
import com.flowernotes.common.exception.Error;
import com.flowernotes.common.excutor.Tasker;
import com.flowernotes.common.utils.JsonUtil;
import com.flowernotes.common.utils.LoggerUtil;
import com.flowernotes.common.utils.ObjectUtil;
import com.flowernotes.common.utils.StringUtil;
import com.flowernotes.core.constant.Context;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;


public class ResponseUtil {
	
	/** 临时存储是否需要返回加密 */
	public static ThreadLocal<Boolean> hasAES = new ThreadLocal<Boolean>();
	

	/***
	 * 写出简单消息
	 * @param response	写出流对象
	 * @param text	要写的文本
	 */
	public static void out(HttpServletResponse response, String text) {
		try {
            response.setContentType("text/html");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
			// FIXME: 2018/8/1/001 用来行为记录
//            WebActionRecordIntercept.resultPool.set(text);
//            BackActionRecordIntercept.resultPool.set(text);
            out.println(text);
            out.flush();
            out.close();
            LoggerUtil.info(ResponseUtil.class, "\n返回消息：\n" + text + "\n----------------------------------------");
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	/**
	 * 向客户端返回消息
	 * @param response
	 * @param textMap
	 * @param exMap
	 */
	public static void out(HttpServletResponse response, Object textMap, Map<String, Object> exMap) {
		out(response, textMap, exMap, true);
	}

	/**
	 * 向客户端返回消息
	 * @param response
	 * @param textObj
	 * @param exMap
	 * @param needEncrypt
	 */
	public static void out(HttpServletResponse response, Object textObj, Map<String, Object> exMap, boolean needEncrypt) {
		String text = "";
		if(exMap!=null && !exMap.isEmpty()) {
			Map<String, Object> textMap = JsonUtil.objToMap(textObj);
			textMap.putAll(exMap);
			text = JsonUtil.objToJson(textMap);
		} else {
			text = JsonUtil.objToJson(textObj);
		}
		try {
            response.setContentType("text/html");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            String returnMsg = text;
            Boolean hasAes = hasAES.get();
            hasAes = hasAes==null?false:hasAes;
        	if(hasAes && needEncrypt) {
            	String token = (String) RequestPool.getSession().getAttribute(Context.session_token);
            	if(StringUtil.isEmpty(token)) {
            		outError(response, new CommonException(Error.biz_nottoken), false);
            	}
            	returnMsg = StringUtil.toAESForSplit(text, token);
            }
			// FIXME: 2018/8/1/001 用来行为记录
//        	WebActionRecordIntercept.resultPool.set(text);
//        	BackActionRecordIntercept.resultPool.set(text);
            out.println(returnMsg);
            out.flush();
            out.close();
            LoggerUtil.info(ResponseUtil.class, "\n返回消息：\n" + text + "\n----------------------------------------");
        } catch (Exception e) {
            e.printStackTrace();
            LoggerUtil.error(ResponseUtil.class, "\n返回消息出错\n", e);
        }
	}

	/**
	 * 向客户端返回消息
	 * @param response
	 * @param outObjTask
	 */
	public static void outSuccess(HttpServletResponse response, Tasker... outObjTask) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("state", 1);
        out(response, map, doTask(null, outObjTask));
	}



	/**
	 * 向客户端返回成功消息
	 * @param response
	 * @param obj
	 * @param outObjTask
	 */
	public static void outSuccess(HttpServletResponse response, Object obj, Tasker... outObjTask) {
		outSuccess(response, obj, true, outObjTask);
	}

	/**
	 * 向客户端返回成功消息
	 * @param response
	 * @param obj
	 * @param needEncrypt
	 * @param outObjTask
	 */
	@SuppressWarnings("unchecked")
	public static void outSuccess(HttpServletResponse response, Object obj, boolean needEncrypt, Tasker... outObjTask) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("state", 1);
		if(obj != null) {
			if(ObjectUtil.isBaseClass(obj)) {
				map.put("info", obj);
				out(response, map, doTask(obj, outObjTask), needEncrypt);
				return;
			} else {
				map.put("info", obj);
			}
		}
        out(response, map, doTask(obj, outObjTask), needEncrypt);
	}
	
	
	/**
	 * 向客户端返回成功消息
	 * @param response	写出流对象
	 * @param e	出错的异常
	 * @param outObjTask	扩展处理
	 * @
	 */
	public static void outError(HttpServletResponse response, Exception e, Tasker... outObjTask) {
		outError(response, e, true, outObjTask);
	}
	
	/**
	 * 向客户端返回error消息
	 * @param response	写出流对象
	 * @param e	出错的异常
	 * @param outObjTask	扩展处理
	 * @param needEncrypt	是否需要做加密处理
	 * @
	 */
	public static void outError(HttpServletResponse response, Exception e, boolean needEncrypt, Tasker... outObjTask) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("state", 0);
		if(e instanceof BaseException) {
			Error error = ((BaseException)e).getErrorCode();
			if (error!=null && error==Error.notKnow_error || error==Error.biz_error || error==Error.system_error) {
				map.put("errorMsg", e.getMessage());
			}
			else{
				map.put("errorMsg", error.getViewMsg());
			}
			map.put("errorCode", error.getCode());
		} else {
			map.put("errorMsg", e.getMessage());
		}
        out(response, map, doTask(null, outObjTask), needEncrypt);
	}
	
	/**
	 * 向客户端返回成功消息
	 * @param response	写出流对象
	 * @param errorMsg	提示错误
	 * @
	 */
	public static void outError(HttpServletResponse response, String errorMsg, Tasker... outObjTask) {
		outError(response, errorMsg, true, outObjTask);
	}
	
	/**
	 * 向客户端返回成功消息
	 * @param response	写出流对象
	 * @param errorMsg	提示错误
	 * @param outObjTask	扩展处理
	 * @param needEncrypt	是否需要做加密处理
	 * @
	 */
	public static void outError(HttpServletResponse response, String errorMsg, boolean needEncrypt, Tasker... outObjTask) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("state", 0);
		map.put("errorMsg", errorMsg);
        out(response, map, doTask(null, outObjTask), needEncrypt);
	}
	
	/**
	 * 执行扩展处理，返回附加数据
	 * @param outObjTask	处理类
	 * @return	附加数据
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private static Map<String, Object> doTask(Object outObj, Tasker... outObjTask) {
		if(outObjTask == null) {
			return null;
		}
		Map<String, Object> exMsg = new HashMap<String, Object>();
		for(Tasker task : outObjTask) {
			if(task == null) {
				continue;
			}
			try {
				exMsg.putAll((Map<String, Object>)task.doTask(outObj));
			} catch (Exception e) {
				e.printStackTrace();
				throw new CommonException(e);
			}
		}
		return exMsg;
	}
	

	/**
	 * 构造分页返回的执行器
	 * @param paramBean	参数对象
	 * @return	如果有分页数据，则构造分页执行器
	 */
	public static Tasker pageTask(BaseBean paramBean) throws Exception {
		final Object page = ObjectUtil.getAttributeValue(paramBean, "page");
		if(page != null) {
			return new Tasker() {
				@SuppressWarnings("unused")
				private static final long serialVersionUID = 1L;
				@Override
				public Object doTask(Object... objs) throws Exception {
					Map<String, Object> exMap = new HashMap<String, Object>();
					Map<String, Object> pageMap = JsonUtil.objToMap(page);
					System.out.println(pageMap);
					pageMap.remove("pageStr");
					pageMap.remove("mobileStr");
					System.out.println(pageMap);
					exMap.put("page", pageMap);
					if(objs!=null && objs.length>0 && objs[0]!=null && objs[0] instanceof BaseBean) {
						ObjectUtil.setAttribute(objs[0], "page", null);
					}
					return exMap;
				}
			};
		}
		return null;
	}
}
