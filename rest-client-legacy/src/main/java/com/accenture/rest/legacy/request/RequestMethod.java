package com.accenture.rest.legacy.request;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.client.HttpResponseException;

public interface RequestMethod<T> {
	T build() throws UnsupportedEncodingException, HttpResponseException, IOException;
}
