package com.accenture.rest.legacy.header.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.accenture.rest.legacy.header.RestHeader;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.auth.BasicScheme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultHeader {
	
	private static Logger LOGGER = LoggerFactory.getLogger(DefaultHeader.class);
	
	public static RestHeader<HttpUriRequest, HttpUriRequest> addHeaders(final Map<String, String> headers){
		return new RestHeader<HttpUriRequest, HttpUriRequest>(){
			private final Map<String, String> headersToadd = headers;
			@Override
			public HttpUriRequest build(HttpUriRequest request) {
				for(Entry<String,String> header : headersToadd.entrySet()){
					request.addHeader(header.getKey(), header.getValue());
				}
				LOGGER.info("Request : {}", request);
				return request;
			}
		};
	}
	
	public static RestHeader<HttpUriRequest, HttpUriRequest> addHeaders(final Map<String, String> headers, String userName, String password){
		return addHeaders(headers, new UsernamePasswordCredentials(userName, password));
	}
	
	public static RestHeader<HttpUriRequest, HttpUriRequest> addHeaders(final Map<String, String> headers,final Credentials credentials){
		return new RestHeader<HttpUriRequest, HttpUriRequest>(){
			private final Map<String, String> headersToadd = headers;
			//private final Credentials credentialsToRequest = credentials;
			@SuppressWarnings("deprecation")
			@Override
			public HttpUriRequest build(HttpUriRequest request) {
				
				try {
					request.addHeader(new BasicScheme().authenticate(credentials, request));
				} catch (AuthenticationException e) {
					LOGGER.warn("não foi possivel realizar o set do header de autenticação.");
				}
				
				for(Entry<String,String> header : headersToadd.entrySet()){
					request.addHeader(header.getKey(), header.getValue());
				}
				LOGGER.info("Request : {}", request);
				return request;
			}
		};
	}
	
	public static RestHeader<HttpUriRequest, HttpUriRequest> addHeaders(){
		return addHeaders(new HashMap<String, String>());
	}

}
