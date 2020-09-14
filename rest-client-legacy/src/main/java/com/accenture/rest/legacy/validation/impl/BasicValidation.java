package com.accenture.rest.legacy.validation.impl;

import java.util.Arrays;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.accenture.rest.legacy.validation.Validation;

public class BasicValidation {
	
	private static Logger LOGGER = LoggerFactory.getLogger(BasicValidation.class);
	
	public static final Validation<HttpResponse> basicValidation(final List<Integer> listOfValidsStatus) {
		return new Validation<HttpResponse>(){
			@Override
			public boolean test(HttpResponse response) {
				LOGGER.info("getStatusLine " + response.getStatusLine().getStatusCode());
				return (response != null && response.getStatusLine() != null) ?  listOfValidsStatus.contains(response.getStatusLine().getStatusCode()) : Boolean.FALSE;
			}
		};
	}
	
	public static final Validation<HttpResponse> basicValidation() {
		return basicValidation(HttpStatus.SC_OK);
	}	
	
	public static final Validation<HttpResponse> basicValidation(final Integer validStatusCode) {
		return basicValidation(Arrays.asList(new Integer[]{ validStatusCode }));
	}	
	
	public static final Validation<HttpResponse> basicValidation(final Integer[] listOfValidsStatus) {
		return basicValidation(Arrays.asList(listOfValidsStatus));
	}
	
}
