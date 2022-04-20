package com.commscope.dms.elasticsearch.impl;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.commscope.dms.elasticsearch.DataType;
import com.commscope.dms.elasticsearch.FilterCondition;
import com.commscope.dms.elasticsearch.FilterCriteria;
import com.commscope.dms.elasticsearch.FilterExpression;
import com.commscope.dms.elasticsearch.FilterOperator;
import com.commscope.dms.elasticsearch.SearchListResult;
import com.commscope.dms.elasticsearch.util.ElkRestClient;
import com.commscope.dms.elasticsearch.utils.ElkServiceConstants;

@RestController
public class ElasticSeachController {
	private static final Log logger = LogFactory.getLog(ElasticSeachController.class);
	private static final String retry_on_conflict ="?retry_on_conflict=5&refresh=wait_for";
	private static final String url = ElkRestClient.getELKURL();
	private RestTemplate restTemplate = ElkRestClient.getRestTemplate();
	private static Log log = LogFactory.getLog(ElasticSeachController.class);
    @GetMapping(value="controller/health")
    public ResponseEntity<String> health() throws Exception{
		return new ResponseEntity<String>(HttpStatus.OK);
	}
    
	@PostMapping(value="controller/createIndex/{indexName}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> createIndexWithMapping(@PathVariable("indexName")String indexName, @RequestBody String jsonData) throws Exception{
		try {
			processPUTRequest(indexName, jsonData);
		} catch (Exception e) {
			updateSettingMapping(indexName, jsonData);
			throw e;
		}
		return new ResponseEntity<String>("createIndexWithMapping added successfully ",HttpStatus.OK);
	}

	private ResponseEntity<String> processPUTRequest(String uri, String jsonData) {
		ResponseEntity<String> response = null;
		if (jsonData != null) {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> requestEntity = new HttpEntity<String>(jsonData, headers);
			response = restTemplate.exchange(url+uri, HttpMethod.PUT, requestEntity, String.class);
			log.debug("Completed sending create index request json : " + jsonData + " , at URI : " + uri
					+ ", response :  " + response.getBody());
		}
		return response;
	}
	
	private void processDelete(String uri) {
			restTemplate.delete(url+uri);
			log.debug("deleteted : at URI : " + uri);
	}
    
	private void updateSettingMapping(String indexName, String jsonData)
			throws IOException, Exception {
		JSONObject object = new JSONObject(jsonData);
		JSONObject settings = null;
		try {
			settings = object.getJSONObject("settings");
		}catch (Exception e1) {}
		JSONObject mappings = null;
		try {
			mappings = object.getJSONObject("mappings");
		}catch (Exception e1) {}
		if(settings!=null) {
			ResponseEntity<String> response = processPUTRequest(indexName+"/_settings", settings.toString());
			System.out.println("_settings updated is :"+response.getStatusCode());
		}
		if(mappings!=null) {
			ResponseEntity<String> response = processPUTRequest(indexName+"/_mappings", mappings.toString());
			System.out.println("_mappings updated is :"+response.getStatusCode());
		}
	}
    @DeleteMapping(value = "controller/deleteIndex/{indexName}", produces = MediaType.TEXT_HTML_VALUE )
    public ResponseEntity<String> deleteIndex(@PathVariable("indexName") String indexName) throws KeyManagementException, NoSuchAlgorithmException, CertificateException, KeyStoreException, IOException {
		try {
			processDelete(url+indexName);
		} catch (Exception e) {
			throw e;
		} 
		return new ResponseEntity<String>("Index is deleted successfully:"+indexName, HttpStatus.OK);
	}
    
    
    @PostMapping(value="controller/createDoc/{indexName}/{docName}", consumes = MediaType.APPLICATION_JSON_VALUE, 
    		produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> createDoc(@PathVariable("indexName") String indexName, 
    		@PathVariable("docName") String docName, @RequestBody String jsonData) throws Exception{
		try {
			ResponseEntity<String> response = processPOSTRequest(indexName+"/_create/"+docName, jsonData);
			System.out.println("CreateDoc is :"+response.getStatusCode());
		} catch (HttpClientErrorException e) {
	    	logger.debug(e.getResponseBodyAsString());
			if(e.getResponseBodyAsString()!=null && 
					e.getResponseBodyAsString().contains("document already exists")) {
				updateDoc(indexName, docName, "{\"doc\":"+jsonData+"}");
			} else {
				throw e;
			}
		}
		return new ResponseEntity<String>("Document added successfully :"+docName,HttpStatus.OK);
	}
    
    private ResponseEntity<String> processPOSTRequest(String uri, String jsonData) {
		ResponseEntity<String> response = null;
		if (jsonData != null) {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> requestEntity = new HttpEntity<String>(jsonData, headers);
			response = restTemplate.postForEntity(url+uri,requestEntity, String.class);
			log.debug("Completed sending create index request json : " + jsonData + " , at URI : " + uri
					+ ", response :  " + response.getBody());
		}
		return response;
	}
    
    
   @PostMapping(value="controller/updateDoc/{indexName}/{docName}", consumes = MediaType.APPLICATION_JSON_VALUE, 
    		produces = MediaType.TEXT_HTML_VALUE)
	public ResponseEntity<String> updateDoc(@PathVariable("indexName") String indexName, 
    		@PathVariable("docName") String docName, @RequestBody String jsonData) throws Exception{
		try {
			logger.debug("getDocumentLock :"+docName);
			DocumentLock lock = getDocumentLock(docName);
			synchronized (lock) {
				ResponseEntity<String> response = processPOSTRequest(indexName+"/_update/"+docName+retry_on_conflict, jsonData);
				System.out.println("UpdateDoc is :"+response.getStatusCode());
			}
			
	    } catch (HttpClientErrorException e) {
	    	logger.debug("docName:"+docName+":"+e.getResponseBodyAsString());
			if(e.getResponseBodyAsString()!=null && 
					e.getResponseBodyAsString().contains("document missing")) {
				if(!jsonData.contains(".remove")) {
					createDoc(indexName, docName, "{\"deviceidentifier\":\""+docName+"\"}");
					updateDoc(indexName, docName, jsonData);
				}
			} if(jsonData.contains(".remove") && e.getResponseBodyAsString()!=null && 
					e.getResponseBodyAsString().contains("failed to execute script")) {
				
			} else {
				throw e;
			}
		} 
		return new ResponseEntity<String>("Document Updated successfully :"+docName,HttpStatus.OK);
	}
    
    @DeleteMapping(value = "controller/deleteDoc/{indexName}/{docName}", produces = MediaType.TEXT_HTML_VALUE )
	public ResponseEntity<String>  deleteDoc(@PathVariable("indexName") String indexName, 
    		@PathVariable("docName") String docName) throws Exception{
		try {
			processDelete(indexName+"/_doc/"+docName);
			System.out.println("deleted :"+indexName+"/_doc/"+docName);
		} catch (HttpClientErrorException e) {
			if(e.getResponseBodyAsString()!=null && 
					e.getResponseBodyAsString().contains("document missing")) {
			} else throw e;
		} 
		return new ResponseEntity<String>("Document Updated successfully :"+docName,HttpStatus.OK);
	}
    
    @PostMapping(value="controller/getSearchListByMap/{indexName}", consumes = MediaType.APPLICATION_JSON_VALUE, 
    		produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SearchListResult> getSearchListByMap(@PathVariable("indexName") String indexName, 
    		@RequestBody  FilterCriteria filterCriteria) 
    		throws KeyManagementException, NoSuchAlgorithmException, CertificateException, KeyStoreException, IOException {
    	String entity =  "{";
		if(filterCriteria.getPageNum()!=null) {
			entity = "\"from\": "+filterCriteria.getPageNum()+",\"size\":"+filterCriteria.getPageSize()+",";
		}
		
		if(filterCriteria.getFilterMap().size()!=0) {
			entity = entity +"\"query\": {\"bool\": { " + 
					"\"must\": ["; 
			boolean firstTime =true;
			Map<String, String> keyValue= filterCriteria.getFilterMap();
			for(String key: keyValue.keySet()) {
				if(!firstTime) {
					entity = entity+",";
				}
				entity = entity+"{\"match\": {\""+key+"\" : \""+keyValue.get(key)+"\"}}";
				firstTime = false;
			}
			
			entity = entity+"]}}" ;
		}
		entity = entity+"}" ;
		
		return getSearchResults(indexName, entity);
	}
    
    @PostMapping(value="controller/getSearchListByJSON/{indexName}", consumes = MediaType.APPLICATION_JSON_VALUE, 
    		produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SearchListResult> getSearchListByJSON(@PathVariable("indexName") String indexName, 
    		@RequestBody  String jsonData) 
    		throws KeyManagementException, NoSuchAlgorithmException, CertificateException, KeyStoreException, IOException {
		return getSearchResults(indexName, jsonData);
	}


	private ResponseEntity<SearchListResult> getSearchResults(String indexName, String query) throws NoSuchAlgorithmException,
			CertificateException, IOException, KeyStoreException, KeyManagementException {
		ResponseEntity<String> response = processPOSTRequest(indexName + "/_search", query);
		int code = response.getStatusCodeValue();
		String result = null;
		SearchListResult resultList = new SearchListResult();
		if (code >= 200 & code < 300) {
			result = response.getBody();
			logger.debug("result:" + result);
			JSONObject object = new JSONObject(result);
			JSONObject hits = object.getJSONObject("hits");
			logger.debug("hits:" + hits);
			JSONArray hits1 = hits.getJSONArray("hits");
			for (int i = 0; i < hits1.length(); i++) {
				JSONObject jb2 = hits1.getJSONObject(i);
				resultList.getResult().add(jb2.toString());
			}
		} else {
			String errorMessage = String.format("ElasticSearch reported an error while trying to run the query: %s",
					response.getStatusCode());
			throw new IOException(errorMessage);
		}
		
		// Find the count
		JSONObject countQuery = new JSONObject(query);
		countQuery = countQuery.getJSONObject("query");
		String queryOfQuery = "";
		if (countQuery != null) {
			queryOfQuery = "{\"query\":" + countQuery.toString() + "}";
		} else {
			queryOfQuery = "";
		}
		ResponseEntity<String> response1 = processPOSTRequest(indexName + "/_count", queryOfQuery);
		code = response1.getStatusCodeValue();
		result = null;
		if (code >= 200 & code < 300) {
			result = response1.getBody();
			logger.debug("result:" + result);
			JSONObject object = new JSONObject(result);
			Integer value = object.getInt("count");
			resultList.setTotalResultsSize(value);
			return new ResponseEntity<SearchListResult>(resultList, HttpStatus.OK);
		} else {
			String errorMessage = String.format("ElasticSearch reported an error while trying to run the query: %s",
					response1.getStatusCode());
			throw new IOException(errorMessage);
		}
	}
	
	 @PostMapping(value="controller/getSearchListByCriteria/{indexName}", consumes = MediaType.APPLICATION_JSON_VALUE, 
	    		produces = MediaType.APPLICATION_JSON_VALUE)
	    public ResponseEntity<SearchListResult> getSearchListByCriteria(@PathVariable("indexName") String indexName, 
	    		@RequestBody  FilterCriteria filterCriteria) 
	    		throws KeyManagementException, NoSuchAlgorithmException, CertificateException, KeyStoreException, IOException {
			return getSearchResults(indexName, getJsonFromCriteria(filterCriteria));
		}


	 public static void main(String str[]) {
		 ElasticSeachController c = new ElasticSeachController();
		 
		 FilterCriteria criteria = new FilterCriteria();
			criteria.getExpressions().add(new FilterExpression(ElkServiceConstants.deviceidentifier, "Commscope1",
			FilterOperator.EQUALS, FilterCondition.OR, DataType.STRING));
			criteria.getExpressions().add(new FilterExpression(ElkServiceConstants.friendlyName, "Commscope1",
			FilterOperator.EQUALS, FilterCondition.OR, DataType.STRING));
			criteria.getExpressions().add(new FilterExpression(ElkServiceConstants.gNBId, "Commscope1",
			FilterOperator.EQUALS, FilterCondition.OR, DataType.STRING));
			criteria.getExpressions().add(new FilterExpression(ElkServiceConstants.gNBName, "Commscope1",
			FilterOperator.EQUALS, FilterCondition.OR, DataType.STRING));
			criteria.getExpressions().add(new FilterExpression(ElkServiceConstants.eNodeBName, "Commscope1",
			FilterOperator.EQUALS, FilterCondition.NONE, DataType.STRING));
			
			
			
		 System.out.println(c.getJsonFromCriteria(criteria));
	 }
	public String getJsonFromCriteria(FilterCriteria filterCriteria) {
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
				Object obj = expression.getValue();
				if (obj instanceof List) {
					operator = FilterOperator.IN;
				} else {
					String value = expression.getValue().toString().replaceAll("%", "");
					match = "{\"wildcard\": {\""+expression.getColumnName()+".keyword\" : \"*"+value+"*\"}}";
				}
			} else if(FilterOperator.EQUALS.equals(operator) || FilterOperator.BOOLEAN.equals(operator) 
					|| FilterOperator.NOT_EQUALS.equals(operator)) {
				match = "{\"match\": {\""+expression.getColumnName()+".keyword\" : \""+expression.getValue()+"\"}}";
			} else if(FilterOperator.BETWEEN.equals(operator)) {
				match = "{\"range\": {\""+expression.getColumnName()+"\": { \"gte\":\""+expression.getValue()+"\",\"lte\":"+expression.getToValue()+"}}}";
			} else if(FilterOperator.LESSER_THAN.equals(operator)) {
				match = "{\"range\": {\""+expression.getColumnName()+"\": { \"lt\":\""+expression.getValue()+"\"}}}";
			} else if(FilterOperator.LESS_THAN_EQUAL.equals(operator)) {
				match = "{\"range\": {\""+expression.getColumnName()+"\": { \"lte\":\""+expression.getValue()+"\"}}}";
			} else if(FilterOperator.GREATER_THAN.equals(operator)) {
				match = "{\"range\": {\""+expression.getColumnName()+"\": { \"gt\":\""+expression.getValue()+"\"}}}";
			} else if(FilterOperator.GREATER_THAN_EQUAL.equals(operator)) {
				match = "{\"range\": {\""+expression.getColumnName()+"\": { \"gte\":\""+expression.getValue()+"\"}}}";
			} else if(FilterOperator.IS_NOT_EMPTY.equals(operator)) {
				match = "{\"bool\":{\"must\": {\"exists\": {\"field\": \""+ expression.getColumnName() +"\"}},\"must_not\": {\"term\": {\""+ expression.getColumnName() + ".keyword\": \"\"}}}}";		
			}
			if(FilterOperator.IN.equals(operator)) {
				Object obj = expression.getValue();
				if (obj instanceof List) {
					ArrayList<Object> list = (ArrayList<Object>) expression.getValue();
					ArrayList<String> tempList = new ArrayList<>();
					for (Object item : list) {
						if (item instanceof String)
							tempList.add("\"" + item + "\"");
					}
					match = "{\"terms\": {\"" + expression.getColumnName() + ".keyword\" : "
							+ (tempList.isEmpty() ? list : tempList) + "}}";
				} else
					match = "{\"terms\": {\"" + expression.getColumnName() + ".keyword\" : [" + expression.getValue()
							+ "]}}";
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
			entity = entity + "{\"match_all\":{}}" ;
		} else {
			entity = entity+tmp ;
		}
		String sortField = filterCriteria.getSortField();
		if(sortField!=null && !sortField.isEmpty()) {
			String order = "asc";
			if(filterCriteria.getReverseOrder()==true) {
				order = "desc";
			}
			entity = entity+",\"sort\": {\""+sortField+".keyword\": \""+order+"\"}" ; 
		}
		entity = entity+"}" ; 
		logger.debug("Json Query for search :"+entity);
		return entity;
	}
	private static Map<String, DocumentLock> docLockMap = new HashMap<String, DocumentLock>();
	private static DocumentLock getDocumentLock(String docId) {
		DocumentLock doc = docLockMap.get(docId);
		if (doc == null) {
			doc = createDocumentLock(docId);
		}
		return doc;
	}
	
	private synchronized static DocumentLock createDocumentLock(String docId) {
		DocumentLock doc = docLockMap.get(docId);
		logger.debug("createDocumentLock :"+docId);
		if (doc == null) {
			System.out.println("Creating docLock"+docId);
			doc = new DocumentLock(docId);
			docLockMap.put(docId, doc);
		}
		return doc;
	}
}
