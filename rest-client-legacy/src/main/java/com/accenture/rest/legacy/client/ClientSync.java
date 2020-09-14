package com.accenture.rest.legacy.client;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;

import com.accenture.rest.legacy.callback.Callback;
import com.accenture.rest.legacy.exception.ValidationException;
import com.accenture.rest.legacy.request.RequestMethod;

public class ClientSync {
	
	private HttpClient httpClient;
	
	protected ClientSync(HttpClient httpClient){
		this.httpClient = httpClient;
	}

	public Object sendAndRecive(RequestMethod<HttpUriRequest> request, Callback<HttpResponse, ?> callback) throws ClientProtocolException, UnsupportedEncodingException, IOException, ValidationException{
		HttpResponse response = null;
		try {
			response = Action.sendAndRecive(httpClient, request.build());
		} catch (ClientProtocolException e) {
			callback.failed(e);
		} catch (UnsupportedEncodingException e) {
			callback.failed(e);
		} catch (IOException e) {
			callback.failed(e);
		}
		return callback.completed(response);
	}
	
}
