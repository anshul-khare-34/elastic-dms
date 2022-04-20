package com.commscope.dms.elasticsearch;

import java.util.List;

import javax.ejb.Local;

import com.commscope.dms.elasticsearch.exception.ElkSearchException;

@Local
public interface ElasticDeviceListService {
	public SearchListResult getSearchListByMap(String indexName, FilterCriteria filterCriteria) throws ElkSearchException;
	public SearchListResult getSearchListByCriteria(String indexName, FilterCriteria filterCriteria) throws ElkSearchException;
	public void addUpdateDoc(String indexName, String doc, String json);
	public List<String> getAllFailedDevices();
}
