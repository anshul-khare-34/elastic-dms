package com.commscope.dms.elasticsearch.dto;

import java.io.Serializable;

import com.commscope.dms.elasticsearch.enums.CRUDType;

public class VenueInfo implements Serializable {

	private static final long serialVersionUID = -2712379150975120020L;

	private String deviceId;

	private String venueName;
	
	private String buildName;
	
	private String flrName;
	
	private CRUDType operationType;
	
	public CRUDType getOperationType() {
		return operationType;
	}

	public void setOperationType(CRUDType operationType) {
		this.operationType = operationType;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getVenueName() {
		return venueName;
	}

	public void setVenueName(String venueName) {
		this.venueName = venueName;
	}

	public String getBuildName() {
		return buildName;
	}

	public void setBuildName(String buildName) {
		this.buildName = buildName;
	}

	public String getFlrName() {
		return flrName;
	}

	public void setFlrName(String flrName) {
		this.flrName = flrName;
	}

}
