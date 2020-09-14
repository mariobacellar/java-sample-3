package com.accenture.rest.legacy.request.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import com.accenture.rest.legacy.map.Mapper;
import com.accenture.rest.legacy.map.impl.BasicMapper;
import com.accenture.rest.legacy.request.RequestMethod;
import com.accenture.rest.legacy.urlformat.UrlFormat;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;

import com.accenture.rest.legacy.header.RestHeader;

public class BasicRequestMethods {

	public static RequestMethod<HttpUriRequest> createGetMethod(final RestHeader<HttpUriRequest, HttpUriRequest> restHeader, final UrlFormat<String> url){
		return new RequestMethod<HttpUriRequest>() {
			@Override
			public HttpUriRequest build() throws UnsupportedEncodingException {
				HttpGet httpGet = new HttpGet(url.generate());
				return restHeader.build(httpGet);
			}
		};
	}
	
	public static RequestMethod<HttpUriRequest> createPostMethod(final RestHeader<HttpUriRequest, HttpUriRequest> restHeader, final UrlFormat<String> url, final Object bodyObject){
		return createPostMethod(restHeader,	BasicMapper.createMapperObjectToString(), url, bodyObject);
	}
	
	public static RequestMethod<HttpUriRequest> createPostMethod(final RestHeader<HttpUriRequest, HttpUriRequest> restHeader, final Mapper<Object, String> mapper, final UrlFormat<String> url, final Object bodyObject){
		return new RequestMethod<HttpUriRequest>() {
			@Override
			public HttpUriRequest build() throws HttpResponseException, IOException {
				HttpPost httpPost = new HttpPost(url.generate()); 
				httpPost.setEntity(new StringEntity(mapper.mapping(bodyObject), ContentType.APPLICATION_JSON));
				return restHeader.build(httpPost);
			}
		};
	}

    public static RequestMethod<HttpUriRequest> createPostMethodWithTypeApplicationFormUrlencoded(final RestHeader<HttpUriRequest, HttpUriRequest> restHeader, final Mapper<Object, String> mapper, final UrlFormat<String> url, final Object bodyObject){
        return new RequestMethod<HttpUriRequest>() {
            @Override
            public HttpUriRequest build() throws HttpResponseException, IOException {
                HttpPost httpPost = new HttpPost(url.generate());
                httpPost.setEntity(new StringEntity(mapper.mapping(bodyObject), ContentType.APPLICATION_FORM_URLENCODED));
                return restHeader.build(httpPost);
            }
        };
    }

	public static RequestMethod<HttpUriRequest> createPutMethod(final RestHeader<HttpUriRequest, HttpUriRequest> restHeader, final UrlFormat<String> url, final Object bodyObject){
		return createPutMethod(restHeader, BasicMapper.createMapperObjectToString(), url, bodyObject); 
	}
	
	public static RequestMethod<HttpUriRequest> createPutMethod(final RestHeader<HttpUriRequest, HttpUriRequest> restHeader, final Mapper<Object, String> mapper, final UrlFormat<String> url, final Object bodyObject){
		return new RequestMethod<HttpUriRequest>() {
			@Override
			public HttpUriRequest build() throws HttpResponseException, IOException {
				HttpPut httpPost = new HttpPut(url.generate());
				httpPost.setEntity(new StringEntity(mapper.mapping(bodyObject)));
				return restHeader.build(httpPost);
			}
		};
	}
	
}
