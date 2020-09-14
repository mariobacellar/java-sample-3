package com.accenture.rest.legacy.client;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.BasicCredentialsProvider;

public class RestClientBuild {
	
	private Credentials credentials;
	private AuthScope authscope;
	private HttpClientConnectionManager connManager; 
	private int soTimeout;
    private boolean soReuseAddress;
    private int soLinger;
    private boolean soKeepAlive;
    private boolean tcpNoDelay;
    private int sndBufSize;
    private int rcvBufSize;
    private int backlogSize;
    
    /* 20180302 - [WEB0083][TIR#322530][DM16001474] - edgard.barbosa.rocha - INICIO */
    private int timeToLive;
    private int connectionTimeout;
    private int connectionManagerTimeOut;
    
    /**
	 * @return the timeToLive
	 */
	public int getTimeToLive() {
		return timeToLive;
	}

	/**
	 * @param timeToLive the timeToLive to set
	 */
	public void setTimeToLive(int timeToLive) {
		this.timeToLive = timeToLive;
	}
	
	/**
	 * @return the connectionTimeout
	 */
	public int getConnectionTimeout() {
		return connectionTimeout;
	}

	/**
	 * @param connectionTimeout the connectionTimeout to set
	 */
	public void setConnectionTimeout(int connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}
	
	/**
	 * @return the connectionManagerTimeOut
	 */
	public int getConnectionManagerTimeOut() {
		return connectionManagerTimeOut;
	}

	/**
	 * @param connectionManagerTimeOut the connectionManagerTimeOut to set
	 */
	public void setConnectionManagerTimeOut(int connectionManagerTimeOut) {
		this.connectionManagerTimeOut = connectionManagerTimeOut;
	}
    
	public RequestConfig getRequestConfig(){
		return RequestConfig
				.custom()
				.setConnectTimeout(connectionTimeout)
				.setConnectionRequestTimeout(connectionManagerTimeOut)
				.setSocketTimeout(soTimeout)
				.build();
	}
	/* 20180302 - [WEB0083][TIR#322530][DM16001474] - edgard.barbosa.rocha - FIM */
	
	public ClientBuild customClientbuild(){
		ClientBuild clientBuild = ClientBuild
				.create()
				.setSocketConfig(getSocketConfig())
				.setHttpClientConnectionManager(getConnManager())
				.setRequestConfig(getRequestConfig())
				.setTimeToLive(getTimeToLive());
		
		if(credentials != null){
			clientBuild = clientBuild.setCredentials(getCredentialsProvider());
		}
		
		return clientBuild;
	}

	public CredentialsProvider getCredentialsProvider() {
		BasicCredentialsProvider basicCredentialsProvider = new BasicCredentialsProvider();
		basicCredentialsProvider.setCredentials(authscope, credentials);
		return basicCredentialsProvider;
	}

	/**
	 * @return the connManager
	 */
	public HttpClientConnectionManager getConnManager() {
		return connManager;
	}

	/**
	 * @param connManager the connManager to set
	 */
	public void setConnManager(HttpClientConnectionManager connManager) {
		this.connManager = connManager;
	}

	/**
	 * @return the backlogSize
	 */
	public int getBacklogSize() {
		return backlogSize;
	}

	/**
	 * @param backlogSize the backlogSize to set
	 */
	public void setBacklogSize(int backlogSize) {
		this.backlogSize = backlogSize;
	}

	/**
	 * @return the rcvBufSize
	 */
	public int getRcvBufSize() {
		return rcvBufSize;
	}

	/**
	 * @param rcvBufSize the rcvBufSize to set
	 */
	public void setRcvBufSize(int rcvBufSize) {
		this.rcvBufSize = rcvBufSize;
	}

	/**
	 * @return the sndBufSize
	 */
	public int getSndBufSize() {
		return sndBufSize;
	}

	/**
	 * @param sndBufSize the sndBufSize to set
	 */
	public void setSndBufSize(int sndBufSize) {
		this.sndBufSize = sndBufSize;
	}

	/**
	 * @return the tcpNoDelay
	 */
	public boolean isTcpNoDelay() {
		return tcpNoDelay;
	}

	/**
	 * @param tcpNoDelay the tcpNoDelay to set
	 */
	public void setTcpNoDelay(boolean tcpNoDelay) {
		this.tcpNoDelay = tcpNoDelay;
	}

	/**
	 * @return the soKeepAlive
	 */
	public boolean isSoKeepAlive() {
		return soKeepAlive;
	}

	/**
	 * @param soKeepAlive the soKeepAlive to set
	 */
	public void setSoKeepAlive(boolean soKeepAlive) {
		this.soKeepAlive = soKeepAlive;
	}

	/**
	 * @return the soLinger
	 */
	public int getSoLinger() {
		return soLinger;
	}

	/**
	 * @param soLinger the soLinger to set
	 */
	public void setSoLinger(int soLinger) {
		this.soLinger = soLinger;
	}

	/**
	 * @return the soReuseAddress
	 */
	public boolean isSoReuseAddress() {
		return soReuseAddress;
	}

	/**
	 * @param soReuseAddress the soReuseAddress to set
	 */
	public void setSoReuseAddress(boolean soReuseAddress) {
		this.soReuseAddress = soReuseAddress;
	}

	/**
	 * @return the soTimeout
	 */
	public int getSoTimeout() {
		return soTimeout;
	}

	/**
	 * @param soTimeout the soTimeout to set
	 */
	public void setSoTimeout(int soTimeout) {
		this.soTimeout = soTimeout;
	}
	
	public SocketConfig getSocketConfig(){
		return SocketConfig.custom()
				.setBacklogSize(backlogSize)
				.setRcvBufSize(rcvBufSize)
				.setSndBufSize(sndBufSize)
				.setSoKeepAlive(soKeepAlive)
				.setSoLinger(soLinger)
				.setSoReuseAddress(soReuseAddress)
				.setSoTimeout(soTimeout)
				.setTcpNoDelay(tcpNoDelay)
				.build();
	}

	/**
	 * @return the credentials
	 */
	public Credentials getCredentials() {
		return credentials;
	}

	/**
	 * @param credentials the credentials to set
	 */
	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}

	/**
	 * @return the authScope
	 */
	public AuthScope getAuthScope() {
		return authscope;
	}

	/**
	 * @param authScope the authScope to set
	 */
	public void setAuthScope(AuthScope authScope) {
		this.authscope = authScope;
	}
}
