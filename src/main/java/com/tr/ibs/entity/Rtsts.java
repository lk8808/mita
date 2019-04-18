package com.tr.ibs.entity;

public class Rtsts {

	private String code;
	
	private String msg;
	
	public Rtsts() {	
	}
	
	public Rtsts(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
