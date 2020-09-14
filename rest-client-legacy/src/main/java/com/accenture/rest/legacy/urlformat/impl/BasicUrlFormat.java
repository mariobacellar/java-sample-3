package com.accenture.rest.legacy.urlformat.impl;

import java.text.MessageFormat;
import java.util.List;

import com.accenture.rest.legacy.urlformat.UrlFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasicUrlFormat {
	
	private static Logger LOGGER = LoggerFactory.getLogger(BasicUrlFormat.class);
	
	public static final UrlFormat<String> createBasicUrlFormat(final String url){
		return new UrlFormat<String>(){
			private String urlBase = url;
			public String generate() {
				LOGGER.info("URL gerada: {}", url);
				return urlBase;
			}
		};
	}
	
	public static final UrlFormat<String> createBasicUrlFormat(final String url, final Object[] parametersIn){
		return createBasicUrlFormat(MessageFormat.format(url, parametersIn));
	}
	
	public static final UrlFormat<String> createBasicUrlFormat(final String url, final List<String> parametersIn){
		return createBasicUrlFormat(url, (String[]) parametersIn.toArray());
	}
	
	public static final UrlFormat<String> createBasicUrlFormat(final String url, final String parameter){
		return createBasicUrlFormat(url, new String[]{parameter});
	}
}
