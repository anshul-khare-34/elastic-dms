package com.commscope.dms.elasticsearch.dto;

import java.io.Serializable;

import com.commscope.dms.elasticsearch.enums.CRUDType;

public class LocationInfo implements Serializable {

	private static final long serialVersionUID = -2712379150975120020L;

	private String deviceId;

	private String state;
	
	private String county;
	
	private String fips;
	
	private String zip;
	
	private String region;
	
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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getFips() {
		return fips;
	}

	public void setFips(String fips) {
		this.fips = fips;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}
}
