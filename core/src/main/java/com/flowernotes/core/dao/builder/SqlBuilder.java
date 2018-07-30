package com.flowernotes.core.dao.builder;


import com.flowernotes.common.annotation.Ignore;
import com.flowernotes.common.annotation.Table;
import com.flowernotes.common.exception.CommonException;
import com.flowernotes.common.excutor.Filter;
import com.flowernotes.common.utils.LoggerUtil;
import com.flowernotes.common.utils.ObjectUtil;
import com.flowernotes.common.utils.StringUtil;
import com.flowernotes.core.constant.Context;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 通过注解生成sql
 */
public class SqlBuilder {

    /**
     * 由传入的对象生成insert sql语句
     *
     * @param object 参数对象
     * @return sql    数据库语句
     * @throws Exception
     */
    public static String buildInsertSql(Object object) throws Exception {
        if (null == object) {
            throw new RuntimeException("补充insert语句时发现传递参数为null");
        }

        Map<String, Object> fieldMap = new HashMap<String, Object>();
        Map<String, Object> tempMap = ObjectUtil.getNotNullFields(object, new Filter<Field>() {
            @Override
            public boolean doFilter(Field obj) {
                Ignore ig = obj.getAnnotation(Ignore.class);
                if (ig == null) {
                    return false;
                }
                // 替换key
                else if (!StringUtil.isEmpty(ig.field())) {
                    fieldMap.put(ig.field(), ObjectUtil.getAttributeValue(object, obj.getName()));
                    return true;
                }
                return true;
            }
        });
        fieldMap.putAll(tempMap);

        List<Annotation> list = ObjectUtil.getClassAnnotation(object.getClass());
        Class<?> clazz = object.getClass();
        String tableName = clazz.getSimpleName();
        for (Annotation an : list) {
            if (an instanceof Table) {
                tableName = ((Table) an).name();
            }
        }
        tableName = "`" + tableName + "`";

        StringBuffer tableSql = new StringBuffer();
        StringBuffer valueSql = new StringBuffer();

        tableSql.append("insert into ").append(tableName).append("(");
        valueSql.append("values(");

        boolean allFieldNull = true;
        // 根据字段注解和属性值联合生成sql语句
        for (String fieldName : fieldMap.keySet()) {
            Object value = fieldMap.get(fieldName);
            // 由于要根据字段对象值是否为空来判断是否将字段加入到sql语句中，因此DTO对象的属性不能是简单类型，反而必须是封装类型
            if (!ObjectUtil.isBaseClass(value)) {
                continue;
            }
            allFieldNull = false;
            Class<?> type = ObjectUtil.getField(clazz, fieldName).getType();
            String jdbcType = parseTypeToJDBCType(type);
            String str = jdbcType != null ? ",jdbcType=" + jdbcType : "";
            tableSql.append(fieldName).append(",");
            valueSql.append("#{").append(fieldName).append(str)
                    .append("},");
        }
        if (allFieldNull) {
            throw new RuntimeException("参数中所有基本类型的属性都为null");
        }
        tableSql.delete(tableSql.lastIndexOf(","), tableSql.lastIndexOf(",") + 1);
        valueSql.delete(valueSql.lastIndexOf(","), valueSql.lastIndexOf(",") + 1);
        tableSql.append(") ").append(valueSql).append(")");
        LoggerUtil.info(SqlBuilder.class, "插入语句：" + tableSql.toString());
        return tableSql.toString();
    }

    /**
     * 将基本类型转换为jdbcType
     *
     * @param type
     * @return
     */
    private static String parseTypeToJDBCType(Class<?> type) {
        if (type.isAssignableFrom(Integer.class)) {
            return "INTEGER";
        }
        if (type.isAssignableFrom(Long.class)) {
            return "INTEGER";
        }
        if (type.isAssignableFrom(Double.class)) {
            return "DOUBLE";
        }
        if (type.isAssignableFrom(Float.class)) {
            return "DOUBLE";
        }
        if (type.isAssignableFrom(String.class)) {
            return "VARCHAR";
        }
        return null;
    }

    /**
     * 由传入的对象生成update sql语句
     *
     * @param object 参数对象
     * @return sql    数据库语句
     * @throws Exception
     */
    public static String buildUpdateSql(Object object) throws Exception {
        if (null == object) {
            throw new RuntimeException("补充update语句时发现传递参数为null");
        }
        Map<String, Object> fieldMap = new HashMap<String, Object>();
        Map<String, Object> tempMap = ObjectUtil.getNotNullFields(object, new Filter<Field>() {
            @Override
            public boolean doFilter(Field obj) {
                Ignore ig = obj.getAnnotation(Ignore.class);
                if (ig == null) {
                    return false;
                }
                // 替换key
                else if (!StringUtil.isEmpty(ig.field())) {
                    fieldMap.put(ig.field(), ObjectUtil.getAttributeValue(object, obj.getName()));
                    return true;
                }
                return true;
            }
        });
        fieldMap.putAll(tempMap);

        List<Annotation> list = ObjectUtil.getClassAnnotation(object.getClass());
        Class<?> clazz = object.getClass();
        String tableName = clazz.getSimpleName();
        for (Annotation an : list) {
            if (an instanceof Table) {
                tableName = ((Table) an).name();
            }
        }
        tableName = "`" + tableName + "`";

        StringBuffer tableSql = new StringBuffer();
        StringBuffer whereSql = new StringBuffer(" where ");

        tableSql.append("update ").append(tableName).append(" set ");

        // 根据字段注解和属性值联合生成sql语句
        for (String fieldName : fieldMap.keySet()) {
            Object value = fieldMap.get(fieldName);
            // 由于要根据字段对象值是否为空来判断是否将字段加入到sql语句中，因此DTO对象的属性不能是简单类型，反而必须是封装类型
            if (!ObjectUtil.isBaseClass(value)) {
                continue;
            }
            if (fieldName.equals("id")) {
                continue;
            }
            Class<?> type = ObjectUtil.getField(clazz, fieldName).getType();
            String jdbcType = parseTypeToJDBCType(type);
            String str = jdbcType != null ? ",jdbcType=" + jdbcType : "";
            tableSql.append(fieldName)
                    .append("=#{")
                    .append(fieldName).append(str)
                    .append("},");
        }
        if (ObjectUtil.getAttributeValue(object, "id") == null) {
            throw new CommonException("修改操作中，id不能为空");
        }
        tableSql.delete(tableSql.lastIndexOf(","), tableSql.lastIndexOf(",") + 1);
        whereSql.append(" id=#{id,jdbcType=INTEGER} ");
        tableSql.append(whereSql);
        LoggerUtil.info(SqlBuilder.class, "更新语句：" + tableSql.toString());
        return tableSql.toString();
    }

    /**
     * 由传入的对象生成delete sql语句
     *
     * @param object 参数对象
     * @return sql    数据库语句
     * @throws Exception
     */
    public static String buildDeleteSql(Object object) throws Exception {
        if (null == object) {
            throw new RuntimeException("补充delete语句时发现传递参数为null");
        }
        Map<String, Object> fieldMap = new HashMap<String, Object>();
        Map<String, Object> tempMap = ObjectUtil.getNotNullFields(object, new Filter<Field>() {
            @Override
            public boolean doFilter(Field obj) {
                Ignore ig = obj.getAnnotation(Ignore.class);
                if (ig == null) {
                    return false;
                }
                // 替换key
                else if (!StringUtil.isEmpty(ig.field())) {
                    fieldMap.put(ig.field(), ObjectUtil.getAttributeValue(object, obj.getName()));
                    return true;
                }
                return true;
            }
        });
        fieldMap.putAll(tempMap);
        List<Annotation> list = ObjectUtil.getClassAnnotation(object.getClass());
        Class<?> clazz = object.getClass();
        String tableName = clazz.getSimpleName();
        for (Annotation an : list) {
            if (an instanceof Table) {
                tableName = ((Table) an).name();
            }
        }
        tableName = "`" + tableName + "`";

        StringBuffer sql = new StringBuffer();
        sql.append("delete from ").append(tableName);

        StringBuffer whereSql = new StringBuffer();
        whereSql.append(" where 1=1 ");
        boolean allFieldNull = true;
        for (String fieldName : fieldMap.keySet()) {
            Object value = fieldMap.get(fieldName);
            // 由于要根据字段对象值是否为空来判断是否将字段加入到sql语句中，因此DTO对象的属性不能是简单类型，反而必须是封装类型
            if (!ObjectUtil.isBaseClass(value)) {
                continue;
            }
            allFieldNull = false;
            Class<?> type = ObjectUtil.getField(clazz, fieldName).getType();
            String jdbcType = parseTypeToJDBCType(type);
            String str = jdbcType != null ? ",jdbcType=" + jdbcType : "";
            whereSql.append(" and ").append(fieldName).append("=#{")
                    .append(fieldName).append(str)
                    .append("}");
        }
        if (allFieldNull) {
            throw new RuntimeException("参数中所有基本类型的属性都为null，不允许删除全表数据");
        }
        sql.append(whereSql);
        LoggerUtil.info(SqlBuilder.class, "删除语句：" + sql.toString());
        return sql.toString();
    }

    /**
     * 由传入的对象生成query sql语句
     *
     * @param object
     * @return sql
     * @throws Exception
     */
    public static String buildSelectSql(Object object) throws Exception {

        boolean is_desc = false;
        Object val = null;
        Long start_time = null;
        Long end_time = null;

        if (object instanceof Map) {
            Map<String, Object> map = (Map<String, Object>) object;
            if (map.containsKey("arg0") && map.get("arg0") != null) {
                val = map.get("arg0");
            }

            if (map.containsKey("arg1") && map.get("arg1") != null) {
                start_time = Long.valueOf(map.get("arg1").toString());
            }

            if (map.containsKey("arg2") && map.get("arg2") != null) {
                end_time = Long.valueOf(map.get("arg2").toString());
            }

            if (map.containsKey("arg3") && map.get("arg3") != null && map.get("arg3").toString().equals("true")) {
                is_desc = true;
            }

        } else {
            val = object;
        }

        Map<String, Object> fieldMap = new HashMap<String, Object>();
        final Object o = val;
        if (val != null) {
            Map<String, Object> tempMap = ObjectUtil.getNotNullFields(val, new Filter<Field>() {
                @Override
                public boolean doFilter(Field obj) {
                    Ignore ig = obj.getAnnotation(Ignore.class);
                    if (ig == null) {
                        return false;
                    }
                    // 替换key
                    else if (!StringUtil.isEmpty(ig.field())) {
                        fieldMap.put(ig.field(), ObjectUtil.getAttributeValue(o, obj.getName()));
                        return true;
                    }
                    return true;
                }
            });
            fieldMap.putAll(tempMap);
        }

        Class<?> clazz = val.getClass();
        String tableName = clazz.getSimpleName();
        Table[] an = clazz.getDeclaredAnnotationsByType(Table.class);
        if (an != null && an.length >= 1) {
            tableName = an[0].name();
        }
        tableName = "`" + tableName + "`";

        StringBuffer selectSql = new StringBuffer();
        selectSql.append("select * from ").append(tableName);

        StringBuffer whereSql = new StringBuffer();
        whereSql.append("\r\n where 1=1 \r\n");
        for (String fieldName : fieldMap.keySet()) {
            Object value = fieldMap.get(fieldName);
            // 由于要根据字段对象值是否为空来判断是否将字段加入到sql语句中，因此DTO对象的属性不能是简单类型，反而必须是封装类型
            if (!ObjectUtil.isBaseClass(value)) {
                continue;
            }
            Class<?> type = ObjectUtil.getField(clazz, fieldName).getType();
            String jdbcType = parseTypeToJDBCType(type);
            String str = jdbcType != null ? ",jdbcType=" + jdbcType : "";
            whereSql.append(" and ").append(fieldName)
                    .append("=#{")
                    .append(fieldName).append(str)
                    .append("}");
        }
        if (start_time != null) {
            whereSql.append(" and ").append(Context.field_Create_time)
                    .append(" > ")
                    .append(start_time);
        }

        if (end_time != null) {
            whereSql.append(" and ").append(Context.field_Create_time)
                    .append(" < ")
                    .append(end_time);
        }

        if (is_desc) {
            whereSql.append(" ORDER BY create_time DESC");
        }
        selectSql.append(whereSql);
        LoggerUtil.info(SqlBuilder.class, "select查询语句：" + selectSql.toString());
        return selectSql.toString();
    }

    public static String buildGetSql(Object object) throws Exception {
        if (null == object) {
            throw new RuntimeException("补充get语句时发现传递参数为null");
        }

        String sql = buildSelectSql(object);
        if (sql != null) {
            sql = sql + " limit 1";
        }
        LoggerUtil.info(SqlBuilder.class, "get查询语句：" + sql);
        return sql;
    }
}
