package com.flowernotes.core.cache;

/** Redis配置信息 */
//@Component
public class ConfigBean {
	private String address;
	private String flag;
	private Integer port;
	private Integer maxActive;
	private Integer maxIdle;
	private String maxWait;
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
