package com.flowernotes.common.utils;


import com.flowernotes.common.excutor.Filter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

@SuppressWarnings("rawtypes")
public class ObjectUtil {
	
	/***
	 * 将对象序列化后进行base64处理
	 * @param obj	对象
	 * @return	base64的序列化对象数据
	 */
	public static String toBase64(Object obj) {
		return StringUtil.toBase64(toByte(obj));
	}
	
	/**
	 * 将对象进行序列化
	 * @param obj	对象
	 * @return	对象序列化后的数据
	 */
	public static byte[] toByte(Object obj) {
		try {
			ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
			ObjectOutputStream objOut = new ObjectOutputStream(byteOut);
			objOut.writeObject(obj);
			return byteOut.toByteArray();
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	/**
	 * 将base64的序列化数据转换为对象
	 * @param base64	经过base64的序列化对象数据
	 * @return	原对象
	 */
	public static <T> T toObject(String base64) {
		return toObject(StringUtil.base64ToByte(base64));
	}
	
	/**
	 * 将序列化数据转换为对象
	 * @param bts	序列化后的对象数据
	 * @return	原对象
	 */
	@SuppressWarnings("unchecked")
	public static <T> T toObject(byte[] bts) {
		try {
			ByteArrayInputStream byteIn = new ByteArrayInputStream(bts);
			ObjectInputStream objIn = new ObjectInputStream(byteIn);
			return (T)objIn.readObject();
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	/***
	 * 依据class的名称获取对应class
	 * @param classAllName	类的全称(如: java.lang.String)
	 * @return	返回依据类名映射的class对象
	 */
 	public static Class getClassByName(String classAllName) {
		try {
			return Class.forName(classAllName);
		} catch (ClassNotFoundException e) {
			throw new IllegalArgumentException(e);
		}
	}
 	
 	/** 依据类型创建对象 */
 	public static <T> T createObject(Class<T> clazz) {
 		try {
 			T obj = clazz.newInstance();
 			return obj;
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
 	}
 	
 	/**
 	 * 测试一个对象里不为空参数与另一个对象是否匹配
 	 * @param baseBean	基础对象(全量数据)
 	 * @param params	参数对象(部分数据)
 	 * @return	是否匹配(true:匹配)
 	 */
 	public static boolean isInner(Object baseBean, Object params) {
 		try {
 			Class<?> clazz = baseBean.getClass();
 			Map<String, Object> paramMap = getNotNullFields(params);
 			Set<String> keys = paramMap.keySet();
 			for(String key : keys) {
 				Object val = paramMap.get(key);
 				Field f = clazz.getDeclaredField(key);
 				f.setAccessible(true);
 				Object baseVal = f.get(baseBean);
 				f.setAccessible(false);
 				// 如果是基础类型
 				if(isNotStructure(val)) {
 					if(!val.equals(baseVal)) {
 						return false;
 					}
 				} 
 				// 非基础类型，迭代调用
 				else if(!isInner(baseVal, val)) {
 					return false;
 				}
 			}
 			return true;
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
 	}

	/**
	 * 初始化对象
	 * @param clazz		创建的对象的类型
	 * @param attrMap	初始对象的属性值
	 * @return	创建的对象
	 */
	public static <T> T initObject(Class<T> clazz, Map<String, Object> attrMap) {
		try{
			T obj = clazz.newInstance();
			if(attrMap != null) {
				// 移除所有的常量赋值
				for(Class tempClass=clazz ; !tempClass.equals(Object.class); tempClass = tempClass.getSuperclass()) {
					Field[] fs = tempClass.getDeclaredFields();
					for(Field f : fs) {
						f.setAccessible(true);
						if(Modifier.isFinal(f.getModifiers())) {
							if(attrMap.remove(f.getName()) != null) {
								System.out.println("移除常量赋值：" + f.getName());
							}
						}
						f.setAccessible(false);
					}
				}
				// 开始赋值
				for(String attrName : attrMap.keySet()) {
					Object val = attrMap.get(attrName);
					if(isNotStructure(val)) {
						setAttribute(obj, attrName, val);
					} else {
						Object temp = getAttributeValue(obj, attrName);
						if(temp == null) {
							setAttribute(obj, attrName, val);
						} else {
							insertObj(temp, val);
						}
					}
				}
			}
			return obj;
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	/** 
	 * 给对象的属性赋值 
	 * @param obj	对象
	 * @param attrName	对象的属性名
	 * @param value	对象的属性值
	 */
	public static void setAttribute(Object obj, String attrName, Object value) {
		try{
			Class clazz = obj.getClass();
			while(!clazz.equals(Object.class)) {
				try {
					Field f = clazz.getDeclaredField(attrName);
					
					if (f == null) {
						continue;
					}
					f.setAccessible(true);
					if (value == null || f.getType().isAssignableFrom(value.getClass())) {
						f.set(obj, value);
					} else {
						f.set(obj, parseToObject(value, f.getType()));
					}
					f.setAccessible(false);
					
					return;
				} catch (NoSuchFieldException e) {
					clazz = clazz.getSuperclass();
				}
			}
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	/**
	 * 从对象中取值
	 * @param obj	对象
	 * @param attrName	要取值的属性名
	 * @return	值
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getAttributeValue(Object obj, String attrName) {
		try {
			Class clazz = obj.getClass();
			while(!clazz.equals(Object.class)) {
				try {
					Field f = clazz.getDeclaredField(attrName);
					f.setAccessible(true);
					Object value = f.get(obj);
					f.setAccessible(false);
					return (T) value;
				} catch (NoSuchFieldException e) {
					clazz = clazz.getSuperclass();
				}
			}
			
			return null; 
			
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	/**
	 * 获取对象中的所有属性
	 * @param bean	对象
	 * @return	属性和值(Map[属性名, 属性值])
	 */
	public static Map<String, Object> getAttributes(Object bean) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			for(Class clazz=bean.getClass(); !clazz.equals(Object.class); clazz=clazz.getSuperclass()) {
				Field[] fs = clazz.getDeclaredFields();
				for(Field f : fs) {
					// 子类最大，父类值不覆盖子类
					if(map.containsKey(f.getName())) {
						continue;
					}
					f.setAccessible(true);
					Object value = f.get(bean);
					f.setAccessible(false);
					map.put(f.getName(), value);
				}
			}
			map.remove("serialVersionUID");
			return map;
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	/**
	 * 获取所有属性对象
	 * @param aclass	类class
	 * @return	属性对象集合
	 */
	@SuppressWarnings("unchecked")
	public static List<Field> getFields(Class<?> aclass,Filter<Field>... filters) {
		List<Field> fList = new ArrayList<Field>();
		try {
			for (Class clazz = aclass; !clazz.equals(Object.class); clazz = clazz.getSuperclass()) {
				Field[] fs = clazz.getDeclaredFields();
				for (Field f : fs) {
					if (!filters[0].doFilter(f)) {
						continue;
					}
					// 子类最大，父类值不覆盖子类
					else if (fList.contains(f)) {
						continue;
					}
					fList.add(f);
				}
			}
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
		
		return fList;
	}
	
	/**
	 * 依据属性名获取该类中对应名字的属性
	 * (未包含此属性则返回null)
	 * @param aclass	类class
	 * @param name		属性名
	 * @return	属性对象集合
	 */
	public static Field getField(Class<?> aclass, String name) {
		try {
			for (Class clazz = aclass; !clazz.equals(Object.class); clazz = clazz.getSuperclass()) {
				Field[] fs = clazz.getDeclaredFields();
				for (Field f : fs) {
					if(name.equals(f.getName())) {
						return f;
					}
				}
			}
			return null;
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	/**
	 * 获取类的所有属性与属性的类型
	 * @param clazz	类
	 * @return	该类的所有属性名与属性类型(包含父类属性)
	 */
	public static Map<String, Class> getFieldNames(Class clazz) {
		try {
			Map<String, Class> attrMap = new HashMap<String, Class>();
			for(; !clazz.equals(Object.class); clazz=clazz.getSuperclass()) {
				Field[] fs = clazz.getDeclaredFields();
				for(Field f : fs) {
					attrMap.put(f.getName(), f.getType());
				}
			}
			attrMap.remove("serialVersionUID");
			return attrMap;
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	/**
	 * 获取对象中的非空属性(属性如果是对象，则只会在同一个map中新增，不会出现map嵌套情况)
	 * @param bean	对象
	 * @param hasInitValue	是否过滤掉初始值(true:过滤掉)
	 * @param filters	拦截器(拦截不想返回的属性)
	 * @return	非空属性和值(Map[属性名, 属性值])
	 */
	@SafeVarargs
	public static Map<String, Object> getNotNullFields(Object bean, boolean hasInitValue, Filter<Field>... filters) {
		try {
			if(hasInitValue) {
				cleanInitValue(bean);
			}
			Map<String, Object> map = new HashMap<String, Object>();
			for(Class clazz=bean.getClass(); !clazz.equals(Object.class); clazz=clazz.getSuperclass()) {
				Field[] fs = clazz.getDeclaredFields();
				for(Field f : fs) {
					// 子类最大，父类值不覆盖子类
					if(map.containsKey(f.getName())) {
						continue;
					}
					boolean isFilter = false;
					if(filters!=null && filters.length>0) {
						for(Filter<Field> filter : filters) {
							isFilter |= filter.doFilter(f);
						}
					}
					if(isFilter) {
						continue;
					}
					f.setAccessible(true);
					Object value = f.get(bean);
					f.setAccessible(false);
					if(value != null) {
						map.put(f.getName(), value);
					}
				}
			}
			map.remove("serialVersionUID");
			return map;
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	/**
	 * 获取对象中的非空属性(属性如果是对象，则只会在同一个map中新增，不会出现map嵌套情况)
	 * (会清空初始值)
	 * @param bean	对象
	 * @param filters	拦截器(拦截不想返回的属性)
	 * @return	非空属性和值(Map[属性名, 属性值])
	 */
	@SafeVarargs
	public static Map<String, Object> getNotNullFields(Object bean, Filter<Field>... filters) {
		return getNotNullFields(bean, true, filters);
	}
	
	/**
     * 获取对象中的非空属性(属性如果是对象，则会嵌套map)
     * @param bean  对象
     * @return  非空属性和值(Map[属性名, 属性值])
     */
    public static Map<String, Object> getNotNullFieldsForStructure(Object bean) {
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            for(Class clazz=bean.getClass(); !clazz.equals(Object.class); clazz=clazz.getSuperclass()) {
                Field[] fs = clazz.getDeclaredFields();
                for(Field f : fs) {
                    // 子类最大，父类值不覆盖子类
                    if(map.containsKey(f.getName())) {
                        continue;
                    }
                    f.setAccessible(true);
                    Object value = f.get(bean);
                    f.setAccessible(false);
                    if(value != null) {
                        if(!isNotStructure(value)) {
                            map.put(f.getName(), getNotNullFieldsForStructure(value));
                        } else {
                            map.put(f.getName(), value);
                        }
                    }
                }
            }
            map.remove("serialVersionUID");
            return map;
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
    
	/***
	 * 依据类，获取该类的泛型class
	 * @param clazz	类对象
	 * @return	泛型类型
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Object> Class<T> getGeneric(Class clazz) {
		try {
			Type type = clazz;
			if(type instanceof ParameterizedType) {
				Type[] params = ((ParameterizedType) type).getActualTypeArguments();
				return (Class<T>) params[0];
			}
			Type genType = clazz.getGenericSuperclass();
			if (!(genType instanceof ParameterizedType)) {
				return (Class<T>) Object.class;
			}
			Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
			return (Class<T>) params[0];
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	/***
	 * 获取一个类的注解
	 * @param aclass	类的class
	 * @return	注解集合
	 */
	public static List<Annotation> getClassAnnotation(Class<?> aclass) {
		List<Annotation> aList = new ArrayList<Annotation>();
		try {
			for (Class clazz = aclass; !clazz.equals(Object.class); clazz = clazz.getSuperclass()) {
				Annotation[] classAnnotations = clazz.getDeclaredAnnotations();
				for (Annotation f : classAnnotations) {
					// 子类最大，父类值不覆盖子类
					if (aList.contains(f)) {
						continue;
					}
					
					aList.add(f);
				}
			}
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
		
		return aList;
	}
	
	/**
	 * 将byte字节转换成对象
	 * @param bts	字节数据
	 * @return	对象
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Object> T parseByteForObj(byte[] bts) {
		ByteArrayInputStream input = new ByteArrayInputStream(bts);
		ObjectInputStream objectInput = null;
		try {
			objectInput = new ObjectInputStream(input);
			return (T) objectInput.readObject();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if(objectInput != null) {
					objectInput.close();
				}
				if(input != null) {
					input.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 将对象转换为byte数据
	 * @param obj	对象
	 * @return	byte数据
	 */
	public static byte[] parseObjForByte(Object obj) {
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		ObjectOutputStream objOut = null;
		try {
			objOut = new ObjectOutputStream(byteOut);
			objOut.writeObject(obj);
			return byteOut.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if(objOut != null) {
					objOut.close();
				}
				if(byteOut != null) {
					byteOut.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}
	}
	
	/***
	 * 转换类型
	 * @param value	字符串的值
	 * @param type	要转换的类型
	 * @return	转换后的值
	 */
	@SuppressWarnings("unchecked")
	public static <T> T parseToObject(Object value, Class<T> type) {
		Object result = null;
		if(value==null || type==String.class) {
			result = value==null?null:value.toString();
		}
		else if(type==Character.class || type==char.class) {
			char[] chars = value.toString().toCharArray();
			result = chars.length>0?chars.length>1?chars:chars[0]:Character.MIN_VALUE;
		}
		else if(type==Boolean.class || type==boolean.class) {
			result = Boolean.parseBoolean(value.toString());
		}
		// 处理boolean值转换
		else if(type==Double.class || type==double.class) {
			result = value.toString().equalsIgnoreCase("true")?true:value.toString().equalsIgnoreCase("false")?false:value;
		}
		else if(type==Long.class || type==long.class) {
			result = Long.parseLong(value.toString());
		}
		else if(type==Integer.class || type==int.class) {
			result = Integer.parseInt(value.toString());
		}
		else if(type==Double.class || type==double.class) {
			result = Double.parseDouble(value.toString());
		}
		else if(type==Float.class || type==float.class) {
			result = Float.parseFloat(value.toString());
		}
		else if(type==Byte.class || type==byte.class) {
			result = Byte.parseByte(value.toString());
		}
		else if(type==Short.class || type==short.class) {
			result = Short.parseShort(value.toString());
		}
		return (T) result;
	}
	
	/***
	 * 是否非结构体(是否不能再进行解析 true:不能解析)
	 * @param value	要验证数据
	 * @return	是否非结构体 (true:不能解析，如基本数据类型)
	 */
	private static boolean isNotStructure(Object value) {

		if(!isBaseClass(value)) {
			if(value instanceof Collection) {
	            return true;
	        } else if(value instanceof Map) {
	            return true;
	        } else if(value instanceof Date) {
	        	return true;
	        } else if(value.getClass().isArray()) {
	            return true;
	        } 
			return false;
		}
		return true;
	}
	
	/***
     * 校验是否是九种基础类型(即：非用户定义的类型)
     * @param value 字符串的值	要校验的值
     * @return  是否是基础类型(true:已经是基础类型了)
     */
    public static boolean isBaseClass(Object value) {
        if(value==null) {
            return true;
        } else if(value instanceof Long) {
            return true;
        } else if(value instanceof Integer) {
            return true;
        } else if(value instanceof Double) {
            return true;
        } else if(value instanceof Float) {
            return true;
        } else if(value instanceof Byte) {
            return true;
        } else if(value instanceof Boolean) {
            return true;
        } else if(value instanceof Short) {
            return true;
        } else if(value instanceof Character) {
            return true;
        } else if(value instanceof String) {
            return true;
        } 
        return false;
    }
	
	/***
	 * 克隆有序列化的对象
	 * @param <T>	要返回的数据类型
	 * @param bean	所有继承过BaseBean的对象
	 * @return	克隆后的对象
	 */
	public static <T>T CloneObject(Class<T> clazz, Object bean) {
		try{
			Map<String, Object> attrMap = getAttributes(bean);
			return initObject(clazz, attrMap);
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	/***
	 * 克隆有序列化的对象
	 * @param <T>	要返回的数据类型
	 * @param bean	要克隆的对象
	 * @return	克隆后的对象
	 */
	@SuppressWarnings("unchecked")
	public static <T>T CloneObject(T bean) {
		try {
			Map<String, Object> attrMap = getAttributes(bean);
			return (T) initObject(bean.getClass(), attrMap);
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	/**
	 * 将新数据的非空属性值插入到基本数据中
	 * @param baseData	基本数据
	 * @param newData	新数据
	 */
	@SuppressWarnings("unchecked")
	public static void insertObj(Object baseData, Object newData) {
		try {
			if(baseData==null || newData==null) {
				return;
			}
			// 清空初始值
			Map<String, Object> attrList = getNotNullFields(newData);
			Set<String> keys = attrList.keySet();
			if(keys!=null && keys.size()>0) {
				for(String key : keys) {
					if(!key.equals("serialVersionUID")) {
						if(isBaseClass(attrList.get(key))) {
							setAttribute(baseData, key, attrList.get(key));
						}
						// 处理数组集合
						else if(attrList.get(key).getClass().isArray()) {
							setAttribute(baseData, key, attrList.get(key));
						}
						// 处理list集合
						else if(attrList.get(key) instanceof Collection) {
							Object baseVal = getAttributeValue(baseData, key);
							if(baseVal == null) {
								baseVal = attrList.get(key).getClass().newInstance();
								setAttribute(baseData, key, baseVal);
							}
							((Collection)baseVal).clear();
							((Collection)baseVal).addAll((Collection)attrList.get(key));
						}
						// 处理map集合
						else if (attrList.get(key) instanceof Map) {
							Map<String, Object> map=(Map<String, Object>) attrList.get(key);
							Object baseMap = getAttributeValue(baseData, key);
							if(baseMap == null) {
								Class baseMapType = attrList.get(key).getClass();
								if(Map.class.isAssignableFrom(baseMapType)) {
									baseMap = baseMapType.newInstance();
									setAttribute(baseData, key, baseMap);
								}
							}
							Iterator<Entry<String, Object>> iterator = map.entrySet().iterator();
							if(baseMap instanceof Map) {
								while(iterator.hasNext()){
									Entry<String, Object> once = iterator.next();
									// 基本数据类型不再处理
									if(isBaseClass(once.getValue())) {
										((Map) baseMap).put(once.getKey(), once.getValue());
									} 
									// 结构体继续处理
									else {
										Object baseValue = ((Map) baseMap).get(once.getKey());
										if(baseValue == null) {
											baseValue = once.getValue().getClass().newInstance();
											((Map) baseMap).put(once.getKey(), baseValue);
										}
										insertObj(baseValue, once.getValue());
									}
								}
							}
						}
						// 处理其他class
						else {
							Object baseValue = getAttributeValue(baseData, key);
							Object newValue = attrList.get(key);
							if(baseValue == null) {
								try {
									baseValue = newValue.getClass().newInstance();
									insertObj(baseValue, newValue);
								} catch (Exception e) {
									baseValue = newValue;
								}
								setAttribute(baseData, key, baseValue);
							} else {
								insertObj(baseValue, newValue);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	/** 清空对象中所有属性的初始值 */
	public static <T>void cleanInitValue(T bean) {
		if(bean == null) {
			return;
		}
		try {
			Class<?> clazz = bean.getClass();
			Object obj = clazz.newInstance();
			for(; !clazz.equals(Object.class); clazz=clazz.getSuperclass()) {
				Field[] fs = clazz.getDeclaredFields();
				for(Field f : fs) {
					if(Modifier.isFinal(f.getModifiers())) {
						continue;
					}
					f.setAccessible(true);
					Object initValue = f.get(obj);
					Object oldValue = f.get(bean);
					if(initValue!=null && initValue.equals(oldValue)) {
						f.set(bean, null);
					}
					f.setAccessible(false);
				}
			}
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}
}
