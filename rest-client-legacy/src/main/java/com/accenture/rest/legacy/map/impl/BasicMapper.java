package com.accenture.rest.legacy.map.impl;

import java.io.IOException;
import java.nio.charset.Charset;

import com.accenture.rest.legacy.map.Mapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpResponseException;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class BasicMapper {
	
	private static Logger LOGGER = LoggerFactory.getLogger(BasicMapper.class);
	
	private static Gson createGson(){
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		return gson;
	}
	
	public static Mapper<Object, String> createMapperObjectToString(){
		return new Mapper<Object, String>() {
			@Override
			public String mapping(Object object) {
				if(LOGGER.isDebugEnabled()) {
					LOGGER.debug("Object {}", object);
				}
				
				Gson gson = createGson();
				return gson.toJson(object);
			}
		};
	}

	public static Mapper<Object, String> createMapperToString(){
		return new Mapper<Object, String>() {
			@Override
			public String mapping(Object object) {
				if(LOGGER.isDebugEnabled()) {
					LOGGER.debug("Object {}", object);
				}
				return object.toString();
			}
		};
	}

	public static Mapper<String, ?> createMapperStringToObject(final Class<?> classOfT){
		return new Mapper<String, Object>() {
			@Override
			public Object mapping(String object) {
				if(LOGGER.isDebugEnabled()) {
					LOGGER.debug("String {}", object);
				}
				Gson gson = createGson();
				Object fromJson = gson.fromJson(object, classOfT);
				return fromJson;
			}
		};
	}
	
	public static Mapper<HttpResponse, Object> createMapperHttpResponseToObject(final Class<?> classOfT){
		return new Mapper<HttpResponse, Object>() {
			@Override
			public Object mapping(HttpResponse response) throws HttpResponseException, IOException {
				if(LOGGER.isDebugEnabled()) {
					LOGGER.debug("HttpResponse {}", response);
				}
				Gson gson = createGson();
				BasicResponseHandler responseHandler = new BasicResponseHandler();
				String object = responseHandler.handleResponse(response);
				Object fromJson = gson.fromJson(object, classOfT);
				if(LOGGER.isDebugEnabled()) {
					LOGGER.debug("Object from Json {}", fromJson);
				}
				return fromJson;
			}
		};
	}

	public static Mapper<HttpResponse, Object> createMapperHttpResponseToObjectIgnoreError(final Class<?> classOfT){
		return new Mapper<HttpResponse, Object>() {
			@Override
			public Object mapping(HttpResponse response) throws HttpResponseException, IOException {
				if(LOGGER.isDebugEnabled()) {
					LOGGER.debug("HttpResponse {}", response);
				}
				Gson gson = createGson();
				String object = EntityUtils.toString(response.getEntity(), Charset.defaultCharset());
				Object fromJson = gson.fromJson(object, classOfT);
				if(LOGGER.isDebugEnabled()) {
					LOGGER.debug("Object from Json {}", fromJson);
				}
				return fromJson;
			}
		};
	}

	public static Mapper<HttpResponse, HttpResponse> createMapperHttpResponseToHttpResponse(){
		return new Mapper<HttpResponse, HttpResponse>() {
			@Override
			public HttpResponse mapping(HttpResponse response) throws HttpResponseException, IOException {
				return response;
			}
		};
	}
	
}
