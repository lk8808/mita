package com.tr.mita.comm.entity;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;

public class RequestLog {
	
	private String requestcode;
	
	private String type;
	
	private String ip;
	
	private String url;
	
	private String username;
	
	private String method;
	
	private String ext = "";
	
	public String getRequestcode() {
		return requestcode;
	}
	
	public void setRequestcode(String requestcode) {
		this.requestcode = requestcode;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}
	
	/**
	 * 获取记录日志
	 * @return 格式：[请求码][记录时间][记录时机][访问ip][url][请求用户名][请求方法][当前使用内存][扩展描述]
	 */
	public String getRequestLog() {
		MemoryMXBean memoryMBean = ManagementFactory.getMemoryMXBean();   
        MemoryUsage usage = memoryMBean.getHeapMemoryUsage(); 
        
		return "[" + requestcode + "][" + type + "][" + ip + "][" + url + "][" + username 
				+ "][" + method + "][" + usage.getUsed()/1024/1024 + "M][" + ext + "]";
	}
	
}
