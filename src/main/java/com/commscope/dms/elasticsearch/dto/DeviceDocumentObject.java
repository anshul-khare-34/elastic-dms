package com.commscope.dms.elasticsearch.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class DeviceDocumentObject extends DeviceFactoryData implements Serializable {

	private static final long serialVersionUID = -8135725624094436756L;

	private ElkPersistenceObj deviceData = new ElkPersistenceObj();

	private Map<String, String> docFields = new HashMap<>();

	public ElkPersistenceObj getDeviceData() {
		return deviceData;
	}

	public void setDeviceData(ElkPersistenceObj deviceData) {
		this.deviceData = deviceData;
	}

	public Map<String, String> getDocFields() {
		return docFields;
	}

	public void setDocFields(Map<String, String> docFields) {
		this.docFields = docFields;
	}

}
