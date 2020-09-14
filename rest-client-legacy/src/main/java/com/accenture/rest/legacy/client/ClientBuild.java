package com.accenture.rest.legacy.client;

import java.util.concurrent.TimeUnit;

import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

public class ClientBuild {
	
	private HttpClientConnectionManager connManager;
	private CredentialsProvider credentialsProvider;
	private SocketConfig socketConfig;
	private RequestConfig requestConfig;
	private int timeToLive;
	
	protected ClientBuild(){
		super();
	}
	
	public static ClientBuild create() {
		return new ClientBuild();
	}
	
	public static ClientSync createDefault(){
		return ClientBuild.create().build();
	}
	
	public ClientSync build() {
		
		HttpClientConnectionManager connManager = this.connManager;
		if(connManager == null){
			connManager = new PoolingHttpClientConnectionManager();
		}

		HttpClientBuilder httpClientBuild = HttpClients.custom()
				.setConnectionManager(connManager)
				.setConnectionTimeToLive(timeToLive, TimeUnit.MILLISECONDS);
		
		if(credentialsProvider != null){
			httpClientBuild = httpClientBuild.setDefaultCredentialsProvider(credentialsProvider);
		}
		
		if(socketConfig == null){
			socketConfig = SocketConfig.DEFAULT;
		}
		httpClientBuild = httpClientBuild.setDefaultSocketConfig(socketConfig);
		
		/* 20180302 - [WEB0083][TIR#322530][DM16001474] - edgard.barbosa.rocha - INICIO */
		if(requestConfig == null){
			requestConfig = RequestConfig.DEFAULT;
		}
		httpClientBuild = httpClientBuild.setDefaultRequestConfig(requestConfig);
		/* 20180302 - [WEB0083][TIR#322530][DM16001474] - edgard.barbosa.rocha - INICIO */
		
		return new ClientSync(httpClientBuild.build());
	}
	
	public final ClientBuild setHttpClientConnectionManager(HttpClientConnectionManager connManager){
		this.connManager = connManager;
		return this;
	}

	public final ClientBuild setCredentials(CredentialsProvider credentialsProvider){
		this.credentialsProvider = credentialsProvider;
		return this;
	}
	
	public final ClientBuild setSocketConfig(SocketConfig socketConfig){
		this.socketConfig = socketConfig;
		return this;
	}
	
	/* 20180302 - [WEB0083][TIR#322530][DM16001474] - edgard.barbosa.rocha - INICIO */
	public final ClientBuild setRequestConfig(RequestConfig requestConfig){
		this.requestConfig = requestConfig;
		return this;
	}
	
	public final ClientBuild setTimeToLive(int timeToLive){
		this.timeToLive = timeToLive;
		return this;
	}
	/* 20180302 - [WEB0083][TIR#322530][DM16001474] - edgard.barbosa.rocha - FIM */
}
