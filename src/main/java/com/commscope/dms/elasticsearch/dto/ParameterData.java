package com.commscope.dms.elasticsearch.dto;

import java.io.Serializable;


public class ParameterData implements Serializable {
	private static final long serialVersionUID = -4405623848845361375L;
	
	private String deviceId;
	
	private String indexes;
	
	private String paramName;
	
	private  String moName;
	
	private String value;
	
	private String service;
	
	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getIndexes() {
		return indexes;
	}

	public void setIndexes(String indexes) {
		this.indexes = indexes;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getMoName() {
		return moName;
	}

	public void setMoName(String moName) {
		this.moName = moName;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}



	

	
}
