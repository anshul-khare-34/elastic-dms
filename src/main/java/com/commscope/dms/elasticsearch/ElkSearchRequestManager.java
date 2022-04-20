/**
 *
 * Copyright (c) 2015 Airvana LP. All rights reserved.
 * 
 */

package com.commscope.dms.elasticsearch;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Remote;
	
import com.commscope.dms.elasticsearch.dto.DeviceFactoryData;
import com.commscope.dms.elasticsearch.dto.LocationInfo;
import com.commscope.dms.elasticsearch.dto.ParameterData;
import com.commscope.dms.elasticsearch.dto.VenueInfo;
import com.commscope.dms.elasticsearch.enums.DeviceAliasType;
import com.commscope.dms.elasticsearch.exception.ElkSearchException;

/**
 * 
 *
 */
@Remote 
public interface ElkSearchRequestManager {
	
	public boolean saveParamData(String deviceId, List<ParameterData> params) throws ElkSearchException;
	
	public int deleteParamData(String deviceId, List<ParameterData> params) throws ElkSearchException;
	
	public boolean deleteandsaveParamData(String deviceId, List<ParameterData> deleteParams, List<ParameterData> params) throws ElkSearchException;
	
	boolean updateDeviceAlias(String deviceId, Map<DeviceAliasType, String> aliasInfo) throws ElkSearchException;

	boolean updateDeviceStatus(String deviceId, int deviceState) throws ElkSearchException;

	boolean updateDeviceFields(String deviceId, Map<String,String> deviceFields) throws ElkSearchException;

	void createDeviceFactoryData(String deviceId, DeviceFactoryData deviceInfo);
	
	void createRuDevice(String deviceId, DeviceFactoryData deviceInfo);
	
	public SearchListResult getSearchListByMap(String indexName,  FilterCriteria filterCriteria) throws ElkSearchException;
	
	public SearchListResult getSearchListByCriteria(String indexName, FilterCriteria filterCriteria) throws ElkSearchException;
	
	public List<Map<String, Object>> searchDevice(String searchValue) throws ElkSearchException;

	public void createUpdataDeleteDeviceVenueData(String deviceId, VenueInfo venueInfo);
	
	public void createUpdataDeleteDeviceLocationInfoData(String deviceId, LocationInfo locationInfo);

	public void createUpdataDeleteDeviceData(String deviceId, HashMap<String, String> genericData);

	public List<String> executeSelectNativeQuery(String nativeQuery, Map<String, Object> paramMap);
	
	public int deleteDeviceData(String deviceId) throws ElkSearchException;
	
}
