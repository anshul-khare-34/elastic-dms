package com.commscope.dms.elasticsearch.dto;

import java.io.Serializable;
import java.util.Map;

import com.commscope.dms.elasticsearch.enums.DeviceAliasType;



public class DeviceAliasInfo implements Serializable {
	
	private static final long serialVersionUID = 6138138542473020816L;

	private String deviceId;
	
	private  Map<DeviceAliasType, String> aliasInfo;

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public Map<DeviceAliasType, String> getAliasInfo() {
		return aliasInfo;
	}

	public void setAliasInfo(Map<DeviceAliasType, String> aliasInfo) {
		this.aliasInfo = aliasInfo;
	}

	
	
}
