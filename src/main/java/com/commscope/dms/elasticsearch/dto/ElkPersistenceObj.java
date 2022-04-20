package com.commscope.dms.elasticsearch.dto;

import java.io.Serializable;
import java.util.List;

import com.commscope.dms.elasticsearch.enums.CRUDType;



public class ElkPersistenceObj implements Serializable {
	
	private static final long serialVersionUID = -2712379150975120020L;
	
	private String deviceId;
	
	private CRUDType operationType;

	private List<ParameterData> params;

	public List<ParameterData> getParams() {
		return params;
	}

	public void setParams(List<ParameterData> params) {
		this.params = params;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public CRUDType getOperationType() {
		return operationType;
	}

	public void setOperationType(CRUDType operationType) {
		this.operationType = operationType;
	}
	

	
}
