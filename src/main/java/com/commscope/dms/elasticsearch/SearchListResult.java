/**
 *
 * Copyright (c) 2015 Airvana LP. All rights reserved.
 * 
 */

package com.commscope.dms.elasticsearch;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SearchListResult implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private List<String> result =  new ArrayList<String>() ;
	
	
	private int totalResultsSize;

	public List<String> getResult() {
		return result;
	}

	public void setResult(List<String> result) {
		this.result = result;
	}

	public int getTotalResultsSize() {
		return totalResultsSize;
	}

	public void setTotalResultsSize(int totalResultsSize) {
		this.totalResultsSize = totalResultsSize;
	}
	
	
	
}
