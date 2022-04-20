package com.commscope.dms.elasticsearch.dto;

import java.io.Serializable;

public class DeviceField implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String deviceId;

	private String name;

	private String value;
	
	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "DeviceField [deviceId=" + deviceId + ", name=" + name + ", value=" + value + "]";
	}

}
