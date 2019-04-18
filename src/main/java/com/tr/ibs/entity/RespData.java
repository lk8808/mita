package com.tr.ibs.entity;

import java.util.HashMap;
import java.util.Map;

import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
public class RespData {
	
	private Rtsts rtsts;
	
	private Map<String, Object> rtdata;
	
	public RespData() {
		rtdata = new HashMap<String, Object>();
	}

	public RespData(Rtsts rtsts) {
		rtdata = new HashMap<String, Object>();
		this.rtsts = rtsts;
	}
	
	public Rtsts getRtsts() {
		return rtsts;
	}

	public void setRtsts(Rtsts rtsts) {
		this.rtsts = rtsts;
	}

	public Map<String, Object> getRtdata() {
		return rtdata;
	}

	public void setRtdata(Map<String, Object> rtdata) {
		this.rtdata = rtdata;
	}
	
	public void setRtdata(String key, Object value) {
		rtdata.put(key, value);
	}
}

