package com.commscope.dms.elasticsearch.dto;

import java.io.Serializable;

public class ReIndexInfo implements Serializable {

	private static final long serialVersionUID = -2712379150975120020L;

	private Long opHistoryId;

	private Long opHistoryDetailsId;
	
	private String operation; // ALL, FAILED, MACID
	
	private String macId;
	

	public String getMacId() {
		return macId;
	}

	public void setMacId(String macId) {
		this.macId = macId;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public Long getOpHistoryId() {
		return opHistoryId;
	}

	public void setOpHistoryId(Long opHistoryId) {
		this.opHistoryId = opHistoryId;
	}

	public Long getOpHistoryDetailsId() {
		return opHistoryDetailsId;
	}

	public void setOpHistoryDetailsId(Long opHistoryDetailsId) {
		this.opHistoryDetailsId = opHistoryDetailsId;
	}
	
	
}
