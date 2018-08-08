package com.flowernotes.api;

import com.flowernotes.core.cache.ConfigBean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@ComponentScan(basePackages = "com.flowernotes")
@EnableRedisHttpSession
@EnableTransactionManagement
public class ApiApplication {
    @Value("${cache.jedis.address}")
    private String address;
    @Value("${cache.jedis.flag}_")
    private String flag;
    @Value("${cache.jedis.port}")
    private Integer port;
    @Value("${cache.jedis.maxActive}")
    private Integer maxActive;
    @Value("${cache.jedis.maxIdle}")
    private Integer maxIdle;
    @Value("${cache.jedis.maxWait}")
    private String maxWait;
    @Value("${cache.jedis.pwd}")
    private String pwd;

    @Bean
    public ConfigBean configBean(){
        ConfigBean configBean = new ConfigBean();
        configBean.setAddress(address);
        configBean.setFlag(flag);
        configBean.setPort(port);
        configBean.setMaxActive(maxActive);
        configBean.setMaxIdle(maxIdle);
        configBean.setMaxWait(maxWait);
        configBean.setPwd(pwd);
        return configBean;
    }
    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }
}
