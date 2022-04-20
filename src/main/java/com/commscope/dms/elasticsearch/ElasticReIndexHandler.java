/**
 *
 * Copyright (c) 2015 Airvana LP. All rights reserved.
 * 
 */

package com.commscope.dms.elasticsearch;

import javax.ejb.Remote;

import com.commscope.dms.elasticsearch.exception.ElkSearchException;

/**
 * 
 *
 */
@Remote 
public interface ElasticReIndexHandler {
	
	public boolean inisiateReIndexing(String indexName, String operation, String macId) throws ElkSearchException;

}
