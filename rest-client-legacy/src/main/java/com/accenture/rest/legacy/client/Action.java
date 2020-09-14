package com.accenture.rest.legacy.client;

import java.io.IOException;
import java.util.concurrent.Future;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.nio.client.HttpAsyncClient;

class Action {

	protected static HttpResponse sendAndRecive(HttpClient client, HttpUriRequest request) throws ClientProtocolException, IOException{
		return client.execute(request);
	}
	
	protected static Future<HttpResponse> sendAndRecive(HttpAsyncClient client, HttpUriRequest request, FutureCallback<HttpResponse> callback){
		return client.execute(request, callback);
	}
	
}
