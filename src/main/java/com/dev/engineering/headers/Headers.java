package com.dev.engineering.headers;

import org.springframework.http.HttpHeaders;
import org.springframework.util.MultiValueMap;

public class Headers {
	
	private static MultiValueMap<String, String> head;
	
	public static MultiValueMap<String, String> construct() {
		
		head = new HttpHeaders();
		head.set(HttpHeaders.ACCEPT, "application/json");
		head.set(HttpHeaders.CONTENT_TYPE, "application/json");
		head.set(HttpHeaders.ETAG, "Version-1");
		
		return head;
	}

}
