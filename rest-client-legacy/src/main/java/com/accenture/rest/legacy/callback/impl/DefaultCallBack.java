package com.accenture.rest.legacy.callback.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.accenture.rest.legacy.callback.Callback;
import com.accenture.rest.legacy.exception.ValidationException;
import com.accenture.rest.legacy.map.Mapper;
import com.accenture.rest.legacy.validation.Validation;

public class DefaultCallBack {
	
	private static Logger LOGGER = LoggerFactory.getLogger(DefaultCallBack.class);
	
	public static Callback<HttpResponse, ?> create(final Validation<HttpResponse> validation, final Mapper<HttpResponse, ?> mapper){
		return new Callback<HttpResponse, Object>() {
			@Override
			public Object completed(HttpResponse result) throws ValidationException, HttpResponseException, IOException {
				if(LOGGER.isDebugEnabled()) {
					LOGGER.debug("HttpResponse {}", result);
				}
				if(validation.test(result)){
					return mapper.mapping(result);
				}
				ValidationException e = new ValidationException();
				e.setStatusCode(result != null ? String.valueOf(result.getStatusLine().getStatusCode()) : "");
				throw e;
			}

			@Override
			public void failed(Exception ex) throws ClientProtocolException, UnsupportedEncodingException, IOException {
				if(ex instanceof ClientProtocolException ){
					throw (ClientProtocolException) ex;
				} else if(ex instanceof UnsupportedEncodingException){
					throw (UnsupportedEncodingException) ex;
				} else if (ex instanceof IOException){
					throw (IOException) ex;
				}
			}
		};
	}
	
}
