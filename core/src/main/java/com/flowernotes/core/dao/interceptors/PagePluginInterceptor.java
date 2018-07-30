package com.flowernotes.core.dao.interceptors;


import com.flowernotes.common.bean.Page;
import com.flowernotes.common.utils.LoggerUtil;
import com.flowernotes.common.utils.ObjectUtil;
import com.flowernotes.common.utils.StringUtil;
import com.flowernotes.core.dao.builder.SqlBuilder;

import org.apache.ibatis.builder.SqlSourceBuilder;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.ExecutorException;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.xml.bind.PropertyException;

@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class,Integer.class }) })
public class PagePluginInterceptor implements Interceptor {

	private static String dialect = "mysql";
	private static String pageSqlId = ".*istPage.*";

	@SuppressWarnings({ "rawtypes" })
	public Object intercept(Invocation ivk) throws Throwable {

		if (ivk.getTarget() instanceof RoutingStatementHandler) {
			RoutingStatementHandler statementHandler = (RoutingStatementHandler) ivk.getTarget();
			BaseStatementHandler delegate = (BaseStatementHandler) ObjectUtil.getAttributeValue(statementHandler, "delegate");
            ParameterHandler parameterHandler = delegate.getParameterHandler();
			MappedStatement mappedStatement = (MappedStatement) ObjectUtil.getAttributeValue(delegate, "mappedStatement");

			// 重新设定编码集
			// ((Connection)
			// ivk.getArgs()[0]).prepareStatement("set names 'utf8mb4'").executeQuery();

			// 补充创建时间(create_time)字段,只对add***,与insert*方法生效
			if (mappedStatement.getId().indexOf("add")>-1 || mappedStatement.getId().indexOf("insert")>-1) {
				Object parameterObject = delegate.getBoundSql().getParameterObject();
				Object createTime = ObjectUtil.getAttributeValue(parameterObject, "create_time");
				if (createTime == null) {
					ObjectUtil.setAttribute(parameterObject, "create_time", System.currentTimeMillis());
				}
			}



			BoundSql boundSql = delegate.getBoundSql();
			// 获取请求参数
			Object parameterObject = boundSql.getParameterObject();

			// MyBatis中sql的id
			String id = mappedStatement.getId();

			String sql = boundSql.getSql();




            // 自动补填语句,根据命名规则来生成sql语句
			{
				boolean isAuto = true;
				if (id.startsWith("add")) {
					sql = SqlBuilder.buildInsertSql(parameterObject);
				} else if (id.startsWith("update")) {
					sql = SqlBuilder.buildUpdateSql(parameterObject);
				} else if (id.startsWith("delete")) {
					sql = SqlBuilder.buildDeleteSql(parameterObject);
				} else if (id.startsWith("listPage")) {
					sql = SqlBuilder.buildSelectSql(parameterObject);
				} else if (id.startsWith("select")) {
					sql = SqlBuilder.buildSelectSql(parameterObject);
				} else if (id.startsWith("get")) {
					sql = SqlBuilder.buildGetSql(parameterObject);
				} else if (id.startsWith("listPageDesc")) {
                    sql = SqlBuilder.buildSelectSql(parameterObject);
                }else if (id.startsWith("selectDesc")){
					sql = SqlBuilder.buildSelectSql(parameterObject);
                } else {
					isAuto = false;
				}

                if (parameterObject instanceof Map){
                    Map<String,Object> map = (Map<String,Object>)parameterObject;
                    if (map.containsKey("arg0")){
                        parameterObject = map.get("arg0");
                    }
                }

				if(isAuto) {
					SqlSource sqlSource = buildSqlSource(mappedStatement.getConfiguration(), sql, parameterObject);
		            List<ParameterMapping> parameterMappings = sqlSource.getBoundSql(parameterObject).getParameterMappings();
					sql = sqlSource.getBoundSql(parameterObject).getSql();
		            ObjectUtil.setAttribute(boundSql, "sql", sql);
					ObjectUtil.setAttribute(boundSql, "parameterMappings", parameterMappings);
				}
			}

			// 修改传过来的参数
            ObjectUtil.setAttribute(parameterHandler,"parameterObject",parameterObject);

			// 分页istPageLoad
			if (id.matches(pageSqlId)) {
				if (parameterObject == null) {
					throw new NullPointerException("parameterObject尚未实例化！");
				}
				Connection connection = (Connection) ivk.getArgs()[0];
				String countSql = "select count(0) from (" + sql + ") as total";
				PreparedStatement countStmt = connection.prepareStatement(countSql);
				BoundSql countBS = new BoundSql(mappedStatement.getConfiguration(), countSql, boundSql.getParameterMappings(), parameterObject);
				setParameters(countStmt, mappedStatement, countBS, parameterObject);
				ResultSet rs = countStmt.executeQuery();
				Long count = 0L;
				if (rs.next()) {
					count = rs.getLong(1);
				}
				rs.close();
				countStmt.close();
				Page page = null;


				if (ObjectUtil.getAttributeValue(parameterObject, "page") instanceof Page) {
					page = (Page) ObjectUtil.getAttributeValue(parameterObject, "page");
					page.setTotal(count);
				} else {
//					if (parameterObject instanceof Map) {
//						page = new Page();
//						page.setEntityOrField(true);
//						page.setTotalResult(count);
//						((Map) parameterObject).put("page", page);
//					}
					Map<String, Class> pageField = ObjectUtil.getFieldNames(parameterObject.getClass());
					if (pageField.containsKey("page")) {
						page = (Page) ObjectUtil.getAttributeValue(parameterObject, "page");
						if (page == null) {
							page = new Page();
						}
						ObjectUtil.setAttribute(parameterObject, "page", page);
						page.setTotal(count);
					} else{
						
						// throw new
						// NoSuchFieldException(parameterObject.getClass().getName()+"不存  page 属性！");
						LoggerUtil.error(this.getClass(), "无page属性");
						return ivk.proceed();
					}
				}
				sql = generatePageSql(sql, page);

				//
				// LoggerUtil.info(this.getClass(),"==================================");
				// LoggerUtil.info(this.getClass(),pageSql);
				// LoggerUtil.info(this.getClass(),"==================================");
				//
				ObjectUtil.setAttribute(boundSql, "sql", sql);
			}
		}

		return ivk.proceed();
	}
	
	private SqlSource buildSqlSource(Configuration configuration, String originalSql, Object parementerObject) {
        SqlSourceBuilder builder = new SqlSourceBuilder(configuration);
        Map<String, Object> param = ObjectUtil.getNotNullFields(parementerObject);
        param.remove("page");
        return builder.parse(originalSql, parementerObject.getClass(), param);
    }

	@SuppressWarnings("unchecked")
	private void setParameters(PreparedStatement ps, MappedStatement mappedStatement, BoundSql boundSql, Object parameterObject) throws SQLException {
		ErrorContext.instance().activity("setting parameters").object(mappedStatement.getParameterMap().getId());
		List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
		if (parameterMappings != null) {
			Configuration configuration = mappedStatement.getConfiguration();
			TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
			MetaObject metaObject = parameterObject == null ? null : configuration.newMetaObject(parameterObject);
			for (int i = 0; i < parameterMappings.size(); i++) {
				ParameterMapping parameterMapping = parameterMappings.get(i);
				if (parameterMapping.getMode() != ParameterMode.OUT) {
					Object value;
					String propertyName = parameterMapping.getProperty();
					PropertyTokenizer prop = new PropertyTokenizer(propertyName);
					if (parameterObject == null) {
						value = null;
					} else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
						value = parameterObject;
					} else if (boundSql.hasAdditionalParameter(propertyName)) {
						value = boundSql.getAdditionalParameter(propertyName);
					} else if ( propertyName.startsWith(ForEachSqlNode.ITEM_PREFIX) && boundSql.hasAdditionalParameter(prop.getName())) {
						value = boundSql.getAdditionalParameter(prop.getName());
						if (value != null) {
							value = configuration.newMetaObject(value).getValue(propertyName.substring(prop.getName().length()));
						}
					} else {
						value = metaObject == null ? null : metaObject.getValue(propertyName);
					}
					@SuppressWarnings("rawtypes")
					TypeHandler typeHandler = parameterMapping.getTypeHandler();
					if (typeHandler == null) {
						throw new ExecutorException(
								"There was no TypeHandler found for parameter "
										+ propertyName + " of statement "
										+ mappedStatement.getId());
					}
					typeHandler.setParameter(ps, i + 1, value, parameterMapping.getJdbcType());
				}
			}
		}
	}

	private String generatePageSql(String sql, Page page) {
		if (page != null && !StringUtil.isEmpty(dialect)
				&& page.getShowCount() > 0) {
			StringBuffer pageSql = new StringBuffer();
			if ("oracle".equals(dialect)) {
				pageSql.append("select * from (select tmp_tb.*,ROWNUM row_id from (");
				pageSql.append(sql);
				pageSql.append(") as tmp_tb where ROWNUM<=");
				pageSql.append(page.getCurrentIndex() + page.getShowCount());
				pageSql.append(") where row_id>");
				pageSql.append(page.getCurrentIndex());
			} else if ("mysql".equalsIgnoreCase(dialect)) {
				pageSql.append("select * from (");
				pageSql.append(sql);
				pageSql.append(" limit ")
						.append((page.getCurrentPage()-1) * page.getShowCount()).append(",")
						.append(page.getShowCount());
				pageSql.append(") as tmp_tb ");
			}
			return pageSql.toString();
		} else {
			return sql;
		}
	}

	public Object plugin(Object arg0) {
		return Plugin.wrap(arg0, this);
	}

	public void setProperties(Properties p) {
		dialect = p.getProperty("dialect");
		if (StringUtil.isEmpty(dialect)) {
			try {
				throw new PropertyException("dialect property is not found!");
			} catch (PropertyException e) {
				e.printStackTrace();
			}
		}
		pageSqlId = p.getProperty("pageSqlId");
		if (StringUtil.isEmpty(pageSqlId)) {
			try {
				throw new PropertyException("pageSqlId property is not found!");
			} catch (PropertyException e) {
				e.printStackTrace();
			}
		}
	}
}
