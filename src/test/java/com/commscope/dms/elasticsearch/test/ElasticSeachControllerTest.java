package com.commscope.dms.elasticsearch.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import com.commscope.dms.elasticsearch.FilterCondition;
import com.commscope.dms.elasticsearch.FilterCriteria;
import com.commscope.dms.elasticsearch.FilterExpression;
import com.commscope.dms.elasticsearch.FilterOperator;
import com.commscope.dms.elasticsearch.SearchListResult;
import com.commscope.dms.elasticsearch.util.JSONUtils;
import com.commscope.dms.elasticsearch.utils.ElkServiceConstants;

public class ElasticSeachControllerTest {
	private static final HttpClient client = HttpClient.newBuilder().version(Version.HTTP_2).build();
	private static final String serviceURL = "http://10.211.55.25:9966/controller/";

	public static void main(String[] args) throws InterruptedException, ExecutionException, IOException {
		String indexName = "device";
		String docName = "bglrszone-33218du1";
		ElasticSeachControllerTest demo = new ElasticSeachControllerTest();

		//demo.deleteIndex(indexName);
		// demo.createIndexWithMapping(indexName);
		demo.createDoc(indexName, docName+"11");
		demo.createDoc(indexName, docName+"12");
		demo.createDoc(indexName, docName+"13");
		demo.createDoc(indexName, docName+"14");
		demo.createDoc(indexName, docName+"15");
		demo.createDoc(indexName, docName+"16");
		demo.createDoc(indexName, docName+"17");
		demo.createDoc(indexName, docName+"18");
		demo.createDoc(indexName, docName+"19");
		demo.createDoc(indexName, docName+"10");
		// demo.updateDoc(indexName, docName);
		getSearchListByFilterCriteria(indexName);
		//getDevicesList("com.airvana.dms.devicelist.search.domainobjects.DeviceListOneCellDuEntity");

	}

	public static void getSearchListByMap(String indexName)
			throws InterruptedException, ExecutionException, IOException {
		FilterCriteria filterCriteria = new FilterCriteria();
		Map<String, String> map = new HashMap<String, String>();
		map.put("_id", "asr");
		filterCriteria.setFilterMap(map);
		String inputJson = JSONUtils.covertFromObjectToJson(filterCriteria);
		HttpRequest request = HttpRequest.newBuilder(URI.create(serviceURL + "getSearchListByMap/" + indexName))
				.header("Content-Type", "application/json").POST(HttpRequest.BodyPublishers.ofString(inputJson))
				.build();
		CompletableFuture<HttpResponse<String>> response = client.sendAsync(request, BodyHandlers.ofString());
		SearchListResult searchListResult = JSONUtils.covertFromJsonToObject(response.get().body(),
				SearchListResult.class);
		System.out.println(searchListResult);
	}

	public static SearchListResult getSearchListByFilterCriteria(String indexName)
			throws InterruptedException, ExecutionException, IOException {
		
		List<FilterExpression> expressionList = new ArrayList<FilterExpression>();
		
		FilterExpression expression1 = new FilterExpression(ElkServiceConstants.oui,"0005B9",
				com.commscope.dms.elasticsearch.FilterOperator.EQUALS,
				com.commscope.dms.elasticsearch.FilterCondition.AND,
				com.commscope.dms.elasticsearch.DataType.STRING);
		expressionList.add(expression1);
		expression1 = new FilterExpression("productClass","CS_ONECELL_DU",
						com.commscope.dms.elasticsearch.FilterOperator.EQUALS,
						com.commscope.dms.elasticsearch.FilterCondition.AND,
						com.commscope.dms.elasticsearch.DataType.STRING);
		expressionList.add(expression1);
		
		String name = "COmmscope1";
		FilterCriteria criteria = new FilterCriteria();
		criteria.setExpressions(expressionList);
		/*criteria.getExpressions().add(new FilterExpression(ElkServiceConstants.deviceId, name, FilterOperator.EQUALS,
				FilterCondition.OR, DataType.STRING));
		criteria.getExpressions().add(new FilterExpression(ElkServiceConstants.friendlyName, name,
				FilterOperator.EQUALS, FilterCondition.OR, DataType.STRING));
		criteria.getExpressions().add(new FilterExpression(ElkServiceConstants.gNBId, name, FilterOperator.EQUALS,
				FilterCondition.OR, DataType.STRING));
		criteria.getExpressions().add(new FilterExpression(ElkServiceConstants.gNBName, name, FilterOperator.EQUALS,
				FilterCondition.OR, DataType.STRING));
		criteria.getExpressions().add(new FilterExpression(ElkServiceConstants.eNodeBName, name, FilterOperator.EQUALS,
				FilterCondition.NONE, DataType.STRING));*/

		 System.out.println(getJsonFromCriteria(criteria));
		String inputJson = JSONUtils.covertFromObjectToJson(criteria);
		HttpRequest request = HttpRequest.newBuilder(URI.create(serviceURL + "getSearchListByCriteria/" + indexName))
				.header("Content-Type", "application/json").POST(HttpRequest.BodyPublishers.ofString(inputJson))
				.build();
		CompletableFuture<HttpResponse<String>> response = client.sendAsync(request, BodyHandlers.ofString());
		SearchListResult searchListResult = JSONUtils.covertFromJsonToObject(response.get().body(),
				SearchListResult.class);
		System.out.println(searchListResult);
		return searchListResult;
	}

	public void createIndexWithMapping(String indexName) throws InterruptedException, ExecutionException, IOException {
		String inputJson = readAllLines("devicelist_mapping");
		HttpRequest request = HttpRequest.newBuilder(URI.create(serviceURL + "createIndex/" + indexName))
				.header("Content-Type", "application/json").POST(HttpRequest.BodyPublishers.ofString(inputJson))
				.build();
		CompletableFuture<HttpResponse<String>> response = client.sendAsync(request,
				HttpResponse.BodyHandlers.ofString());
		System.out.println(response.get().body());
	}

	public void createDoc(String indexName, String docName)
			throws InterruptedException, ExecutionException, IOException {
		String inputJson = readAllLines("devicelistdata_add");
		HttpRequest request = HttpRequest.newBuilder(URI.create(serviceURL + "createDoc/" + indexName + "/" + docName))
				.header("Content-Type", "application/json").POST(HttpRequest.BodyPublishers.ofString(inputJson))
				.build();
		CompletableFuture<HttpResponse<String>> response = client.sendAsync(request,
				HttpResponse.BodyHandlers.ofString());
		System.out.println(response.get().body());
	}

	public void updateDoc(String indexName, String docName)
			throws InterruptedException, ExecutionException, IOException {
		String inputJson = readAllLines("devicelistdata_update");
		HttpRequest request = HttpRequest.newBuilder(URI.create(serviceURL + "updateDoc/" + indexName + "/" + docName))
				.header("Content-Type", "application/json").POST(HttpRequest.BodyPublishers.ofString(inputJson))
				.build();
		CompletableFuture<HttpResponse<String>> response = client.sendAsync(request,
				HttpResponse.BodyHandlers.ofString());
		System.out.println(response.get().body());
	}

	public void deleteIndex(String indexName) throws InterruptedException, ExecutionException, IOException {
		HttpRequest request = HttpRequest.newBuilder(URI.create(serviceURL + "deleteIndex/" + indexName)).DELETE()
				.build();
		CompletableFuture<HttpResponse<String>> response = client.sendAsync(request,
				HttpResponse.BodyHandlers.ofString());
		System.out.println(response.get().body());
	}

	private String readAllLines(String className) throws IOException {
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(className + ".txt");
		return readFromInputStream(inputStream);
	}

	private String readFromInputStream(InputStream inputStream) throws IOException {
		StringBuilder resultStringBuilder = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
			String line;
			while ((line = br.readLine()) != null) {
				resultStringBuilder.append(line).append("\n");
			}
		}
		return resultStringBuilder.toString();
	}

	/*public static DeviceListResult getDevicesList(String clazz) {
		com.commscope.dms.elasticsearch.FilterCriteria elkFiltrCriteria = new com.commscope.dms.elasticsearch.FilterCriteria();
		List<com.commscope.dms.elasticsearch.FilterExpression> expressionList = new ArrayList<com.commscope.dms.elasticsearch.FilterExpression>();

		elkFiltrCriteria.setExpressions(expressionList);
		
		try {
			DeviceListResult result = new DeviceListResult();
			result.setResult(new ArrayList<DeviceListEntity>());
			SearchListResult searchListResult = getSearchListByFilterCriteria("device");
			for (String jsonStr : searchListResult.getResult()) {
				 JSONObject object = new JSONObject(jsonStr);
				    String source = object.getJSONObject("_source").toString();
				DeviceListEntity entity = (DeviceListEntity) JSONUtils.covertFromJsonToObject(source,
						Class.forName(clazz));
				result.getResult().add(entity);
			}
			result.setTotalResultsSize(searchListResult.getTotalResultsSize());
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}*/
	
	public static String getJsonFromCriteria(FilterCriteria filterCriteria) {
		String entity =  "{";
		if(filterCriteria.getPageNum()!=null) {
			entity = entity + "\"from\": "+filterCriteria.getPageNum()+",\"size\":"+filterCriteria.getPageSize()+",";
		}
		
		int numberOfExp =0;
		String tmp ="";
		String match = "";
		String condition ="";
		for(FilterExpression expression:filterCriteria.getExpressions()) {
			FilterOperator operator = expression.getOperator();
			if(FilterOperator.CONTAINS.equals(operator) || FilterOperator.NOT_CONTAINS.equals(operator)) {
				match = "{\"wildcard\": {\""+expression.getColumnName()+"\" : \"*"+expression.getValue()+"*\"}}";
			} else if(FilterOperator.EQUALS.equals(operator) || FilterOperator.BOOLEAN.equals(operator) 
					|| FilterOperator.NOT_EQUALS.equals(operator)) {
				match = "{\"match\": {\""+expression.getColumnName()+".keyword\" : \""+expression.getValue()+"\"}}";
			} else if(FilterOperator.BETWEEN.equals(operator)) {
				match = "{\"range\": {\""+expression.getColumnName()+"\": { \"gte\":"+expression.getValue()+",\"lte\":"+expression.getToValue()+"}}}";
			} else if(FilterOperator.LESSER_THAN.equals(operator)) {
				match = "{\"range\": {\""+expression.getColumnName()+"\": { \"lt\":"+expression.getValue()+"}}}";
			} else if(FilterOperator.LESS_THAN_EQUAL.equals(operator)) {
				match = "{\"range\": {\""+expression.getColumnName()+"\": { \"lte\":"+expression.getValue()+"}}}";
			} else if(FilterOperator.GREATER_THAN.equals(operator)) {
				match = "{\"range\": {\""+expression.getColumnName()+"\": { \"gt\":"+expression.getValue()+"}}}";
			} else if(FilterOperator.GREATER_THAN_EQUAL.equals(operator)) {
				match = "{\"range\": {\""+expression.getColumnName()+"\": { \"gte\":"+expression.getValue()+"}}}";
			} 
			if(match.isEmpty()|| "_id".equalsIgnoreCase(expression.getColumnName())) {
				match = "{\"match\": {\""+expression.getColumnName()+"\" : \""+expression.getValue()+"\"}}";
			}
			
			if(FilterOperator.NOT_EQUALS.equals(operator) || FilterOperator.NOT_CONTAINS.equals(operator)) {
				match = "{\"bool\": { \"must_not\":"+match+"}}";
			}
			
			if(numberOfExp==0) {
				tmp = match;
			} else {
				tmp = "{\"bool\": { \""+condition+"\": ["+tmp+","+match+"]}}";
			}
			
			if(FilterCondition.OR.equals(expression.getFilterCondition())){
				condition ="should";
			} else {
				condition ="must";
			}
			numberOfExp ++;
		}
		
		entity = entity +"\"query\":";
		if(numberOfExp==1) {
			entity = entity +"{\"bool\": {\"must\": ["; 
			entity = entity+tmp+"]}}" ;
		} else if(numberOfExp==0){
			entity = "{" ;
		} else {
			entity = entity+tmp ;
		}
		entity = entity+"}" ; 
		return entity;
	}
}
