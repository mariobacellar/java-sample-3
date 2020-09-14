package com.accenture.rest.legacy.callback;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import com.accenture.rest.legacy.exception.ValidationException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;

public interface Callback<T, K> {
	K completed(T result) throws ValidationException, HttpResponseException, IOException;
	void failed(Exception ex) throws ClientProtocolException, UnsupportedEncodingException, IOException;	
}
