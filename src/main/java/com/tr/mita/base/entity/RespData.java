package com.tr.mita.base.entity;

import java.util.HashMap;

import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
public class RespData {
	
	private Rtsts rtsts;
	
	private Object rtdata;
	
	public RespData() {
		this.rtsts = new Rtsts("000000", "");
		this.rtdata = new HashMap<String, Object>();
	}

	public RespData(Rtsts rtsts) {
		this.rtsts = rtsts;
		this.rtdata = new HashMap<String, Object>();
	}
	
	public Rtsts getRtsts() {
		return rtsts;
	}

	public void setRtsts(Rtsts rtsts) {
		this.rtsts = rtsts;
	}

	public Object getRtdata() {
		return rtdata;
	}

	public void setRtdata(Object rtdata) {
		this.rtdata = rtdata;
	}

	@Override
	public String toString() {
		return "RespData{" +
				"rtsts=" + rtsts +
				", rtdata=" + rtdata +
				'}';
	}
}

