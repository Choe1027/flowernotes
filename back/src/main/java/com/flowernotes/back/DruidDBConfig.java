package com.flowernotes.back;

import com.alibaba.druid.pool.DruidDataSource;
import com.flowernotes.common.utils.LoggerUtil;
import com.flowernotes.core.dao.builder.ReVFS;
import com.flowernotes.core.dao.interceptors.PagePluginInterceptor;

import org.apache.ibatis.io.VFS;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = {"com.flowernotes.**.dao"}, sqlSessionFactoryRef = "druidSqlSessionFactory")
public class DruidDBConfig implements EnvironmentAware {
	
//	static final String PACKAGE = "com.fengshang.common.dao.impl,com.fengshang.biz.information.dao.impl";
	
    private String dbUrl;  
    private String username;  
      
    private String password;  
      
    private String typeAliasesPackage;  
    
    private String mapperLocations;  
    
    private RelaxedPropertyResolver propertyResolver;  
    
    @Override
	public void setEnvironment(Environment env) {
    	this.propertyResolver = new RelaxedPropertyResolver(env);
    	this.dbUrl = propertyResolver.getProperty("spring.druid.datasource.url");
    	this.username = propertyResolver.getProperty("spring.druid.datasource.username");
    	this.password = propertyResolver.getProperty("spring.druid.datasource.password");
    	this.mapperLocations = propertyResolver.getProperty("mybatis.mapperLocations");
    	this.typeAliasesPackage = propertyResolver.getProperty("mybatis.typeAliasesPackage");
	}
      
    @Bean(name="druidDataSource")    //声明其为Bean实例
    @Primary  //在同样的DataSource中，首先使用被标注的DataSource
    public DataSource dataSource(){  
        DruidDataSource datasource = new DruidDataSource();  
          
        datasource.setUrl(this.dbUrl);  
        datasource.setUsername(username);  
        datasource.setPassword(password);  
        datasource.setDriverClassName("com.mysql.jdbc.Driver");  
          
        //configuration  
        datasource.setInitialSize(5);  
        datasource.setMinIdle(5);  
        datasource.setMaxActive(20);  
        datasource.setMaxWait(60000);  
        datasource.setTimeBetweenEvictionRunsMillis(60000);  
        datasource.setMinEvictableIdleTimeMillis(300000);  
        datasource.setValidationQuery("SELECT 'x'");  
        datasource.setTestWhileIdle(true);  
        datasource.setTestOnBorrow(false);  
        datasource.setTestOnReturn(false);  
        datasource.setPoolPreparedStatements(false);  
        datasource.setMaxPoolPreparedStatementPerConnectionSize(20);  
        try {  
            datasource.setFilters("stat,wall,slf4j");  
        } catch (SQLException e) {  
        	LoggerUtil.error(this.getClass(), e);
        }  
        datasource.setConnectionProperties("druid.stat.mergeSql=true;druid.stat.slowSqlMillis=5000");  
        return datasource;  
    } 
    
    @Bean(name = "druidTransactionManager")
    @Primary
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }
    
    @Bean(name = "druidSqlSessionFactory")
//    @Primary
    public SqlSessionFactory masterSqlSessionFactory(@Qualifier("druidDataSource") DataSource masterDataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(masterDataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(mapperLocations));
        sessionFactory.setVfs(ReVFS.class);
        VFS.addImplClass(ReVFS.class);
        sessionFactory.setTypeAliasesPackage(typeAliasesPackage);
        
        PagePluginInterceptor pageHelper = new PagePluginInterceptor();
        Properties props = new Properties();  
        props.setProperty("dialect", "mysql");  
        props.setProperty("pageSqlId", ".*istPage*");  
        pageHelper.setProperties(props);
        
//        AutoMapperInterceptor mapperHelper = new AutoMapperInterceptor();
//        
//        ResultTypeInterceptor resultTypeInterceptor = new ResultTypeInterceptor();
//        sessionFactory.setPlugins(new Interceptor[]{pageHelper, mapperHelper, resultTypeInterceptor});
        
//        MybatisPlugin mybatisPlugin = new MybatisPlugin();
//        sessionFactory.setPlugins(new Interceptor[]{mybatisPlugin, resultTypeInterceptor});        
        sessionFactory.setPlugins(new Interceptor[]{pageHelper});        
        return sessionFactory.getObject();
    }

	public String getDbUrl() {
		return dbUrl;
	}

	public void setDbUrl(String dbUrl) {
		this.dbUrl = dbUrl;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
