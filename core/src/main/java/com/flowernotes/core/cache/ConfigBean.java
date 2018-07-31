package com.flowernotes.core.cache;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/** Redis配置信息 */
@Component
public class ConfigBean {
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
	
	public String getAddress() {
		return address;
	}
	public String getFlag() {
		return flag;
	}
	public Integer getPort() {
		return port;
	}
	public Integer getMaxActive() {
		return maxActive;
	}
	public Integer getMaxIdle() {
		return maxIdle;
	}
	public String getMaxWait() {
		return maxWait;
	}
	public String getPwd() {
		return pwd;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public void setPort(Integer port) {
		this.port = port;
	}
	public void setMaxActive(Integer maxActive) {
		this.maxActive = maxActive;
	}
	public void setMaxIdle(Integer maxIdle) {
		this.maxIdle = maxIdle;
	}
	public void setMaxWait(String maxWait) {
		this.maxWait = maxWait;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
}
