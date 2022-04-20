package com.commscope.dms.elasticsearch;

import javax.ejb.Local;

@Local
public interface ElasticServiceStatus {

	
	/**
	 * @return
	 * Check if elastic service is running
	 */
	public String isElasticServiceRunning() throws ElasticServiceStatusException;

}
