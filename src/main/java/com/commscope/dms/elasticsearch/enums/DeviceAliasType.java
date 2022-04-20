package com.commscope.dms.elasticsearch.enums;

public enum DeviceAliasType {

	ENODEBNAME("eNodeBName"), 
	FRIENDLYNAME("friendlyName"),
	GNBID("gNBId"), 
	GNBNAME("gNBName"),
	CLUSTER_NAME("clusterName"),
	IP_ADDRESS("ipaddress"), 
	CLUSTER_VIRTUAL_CELLID("clusterVirtualCellId"),
	X_0005B9_CELLNAME("X_0005B9_CellName", "Device.Services.FAPService.1.CellConfig.LTE.RAN.Common.X_0005B9_CellName"),
	CELL_IDENTITY("CellIdentity", "Device.Services.FAPService.1.CellConfig.LTE.RAN.Common.CellIdentity");

	
	private String aliasName;
	private String moName;
	
	private DeviceAliasType(String aliasName) {
		this.aliasName = aliasName;
	}
	
	DeviceAliasType(String aliasName, String moName) {
		this.aliasName = aliasName;
		this.moName = moName;
	}

	public String getMoName() {
		return moName;
	}

	public String getAliasName() {
		return aliasName;
	}

	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}

	public static DeviceAliasType getAliasType(String aliasName) {
		if (aliasName != null) {
			if(aliasName.contains("gNBName")){
				return DeviceAliasType.GNBNAME;
			}
			for (DeviceAliasType alias : DeviceAliasType.values()) {
				if (aliasName.equalsIgnoreCase(alias.getAliasName())) {
					return alias;
				}
			}
		}
		return null;

	}
	
	public static DeviceAliasType getAliasTypeByMoName(String moName) {
		if (moName != null) {
			for (DeviceAliasType alias : DeviceAliasType.values()) {
				if (moName.equalsIgnoreCase(alias.getMoName())) {
					return alias;
				}
			}
		}
		return null;
	}

}
