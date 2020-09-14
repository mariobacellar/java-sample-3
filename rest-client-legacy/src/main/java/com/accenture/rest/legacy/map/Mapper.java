package com.accenture.rest.legacy.map;

import java.io.IOException;

import org.apache.http.client.HttpResponseException;

public interface Mapper<L, K> {
	K mapping(L object) throws HttpResponseException, IOException;
}
