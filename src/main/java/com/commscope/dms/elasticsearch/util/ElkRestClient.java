package com.commscope.dms.elasticsearch.util;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.web.client.RestTemplate;



public class ElkRestClient {
	@SuppressWarnings("resource")
	public static String  getELKURL() {
		String host = System.getenv("ELASTIC_HOST");
		if(host!=null) {
			if(!host.toUpperCase().contains("://")) {
				host = "http://"+host;
			}
			host = host +":"+System.getenv("ELASTIC_PORT")+"/";
		}
		return host;
	}
	
	public static RestTemplate getRestTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(System.getenv("ELASTIC_USER"), System.getenv("ELASTIC_PASSWORD")));
		return restTemplate;
	}
}