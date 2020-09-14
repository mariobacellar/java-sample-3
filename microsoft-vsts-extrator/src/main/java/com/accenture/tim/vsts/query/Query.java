package com.accenture.tim.vsts.query;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.log4j.Logger;

import com.accenture.rest.legacy.client.RestClientBuild;
import com.accenture.tim.service.restclient.legacy.impl.RestClientServiceImpl;


/**
 * Samples of connection from: accenture-tim-integration-connector
 * - com.accenture.mashups.tfs.integration.facade.impl.ConnectorFacadeImpl
 * - com.accenture.mashups.tfs.integration.tfs.client.impl.TFSClient
 * - 
 * @author mario.bacellar
 *
 */
public abstract class Query {

	final static Logger log  = Logger.getLogger(Query.class);
	
	protected String urlQuery;
	
	protected String user;
	protected String pass;

	private Credentials cred; 
	
	protected HttpClientConnectionManager	connManager;
	protected RestClientBuild				restClientBuild;
	protected RestClientServiceImpl			restClientServiceImpl;
	protected Map<String, String> 			headers;
	
	
	
	/**
	 * 
	 * @param user
	 * @param pass
	 */
	public Query(String user, String pass) {
		this.user = user;
		this.pass = pass;
	}

	
	public Query(String user, String pass, String url) {
		this.user		= user;
		this.pass		= pass;
		this.urlQuery	= url;
	}
	

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public abstract Object getResultSet() throws Exception;

	
	/**
	 * 
	 */
	protected void buildHeaders() {
		log.info("->  buildHeaders()");

		headers = new HashMap<String, String>();
		//headers.put("cookie", "SpsAuthenticatedUser=DisplayName=mario.bacellar&aad=False; __RequestVerificationToken=3tOK1qLHuNPttRv0BJ5ETxKNAr-YuT79HneGG6YY6PBTGr7YrakEWjl3-QdHjIucQ6sDWmHlLmsHljGenX3DnF0ubVs1; __RequestVerificationToken2b1fdc460-3ea9-4fe2-b04e-6eb7059fb3e9=3tOK1qLHuNPttRv0BJ5ETxKNAr-YuT79HneGG6YY6PBTGr7YrakEWjl3-QdHjIucQ6sDWmHlLmsHljGenX3DnF0ubVs1; FedAuth=77u/PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0idXRmLTgiPz48U2VjdXJpdHlDb250ZXh0VG9rZW4gcDE6SWQ9Il81YTllZTE0My0yNDNmLTRiYWItOTY3OC1hMDVlNzZkZDA0YmMtMEY4RDdBNTk4MEZCMDEyOTc4Qjg4ODk5REY4MkFDQjgiIHhtbG5zOnAxPSJodHRwOi8vZG9jcy5vYXNpcy1vcGVuLm9yZy93c3MvMjAwNC8wMS9vYXNpcy0yMDA0MDEtd3NzLXdzc2VjdXJpdHktdXRpbGl0eS0xLjAueHNkIiB4bWxucz0iaHR0cDovL2RvY3Mub2FzaXMtb3Blbi5vcmcvd3Mtc3gvd3Mtc2VjdXJlY29udmVyc2F0aW9uLzIwMDUxMiI+PElkZW50aWZpZXI+dXJuOnV1aWQ6MjE1ZTM0MzctN2ZlNy00MWQzLWJiZmMtMmU1NzkxMjkzNzkwPC9JZGVudGlmaWVyPjxDb29raWUgeG1sbnM9Imh0dHA6Ly9zY2hlbWFzLm1pY3Jvc29mdC5jb20vd3MvMjAwNi8wNS9zZWN1cml0eSI+MFlkQ1daWk9tSTRBQVFBQVRMcWh2Wk9BYnY2cDZxcDdNZUpVMFpONXduN0xzc0JsZUhleUlQMUU5aGFRUXZtb1dGWHRuWDhsSGR1ejV0SGplTDh2MXc0eHdxVzJCeWRkUDBWMWNUUlBTeUhaTjl1VXh0WGlYNUtKZU94dzJIVkRRc0JZd2d4MHpPRklBNGY1d01Eb0ZYbUNMbERCRXZpdUxGcG82OHdheWxiQVVOaHNRdjF5citJMGZ6VjZ2cmM0ajBLRlJnR3NjbUViMEZVenJDb0UrZWtPUWNQM1Ivb0lJLytvUWhjN3ZUVmc0SFltUFBGZlNuT3JCTWxvU21CenZTUnMyUG1KRFl0YjBqVnpiTWxjS3dhaVp2MWJ4V3M1cnB2SWUzQ0FjNGdySDRNd2ZlZzkrMUdIUnY3MFVHbFZZb2lkU1YwTzFqMUJQY3c5ZmRjOFZFdTZ3eWFURk5RZjNWUm5TT3Z1NlcvY0JEcXlFL1hzM0dKbGVXOXV5NE5pOFBBeFY1bW13OUhEOXZwdEFBRUFBRlRYOGN5UjZHY2xDMllEZGRmU2k4cVJnSjN4TEhKVXNONFdiZVoxYmNXZWpMTk1DZERjaE43a2xsQldvc2FUQURYZ05SdnlQbE9sV2pPZkNCd3lUZzB3R09KVURYMkZrdXNzQjM4cnEySnMrcGtPdEd5YnFIQmlHVVZhSE9DQnFIeVAzTVlwbFJUTmgwWmlVYUVCLzA4WUdlMUVMbDBYWUUxdFNqa3BTM21UQzloVE5SdHNDcjFkM3g0RjBqUE55Nm04WkIzRC9uWjFhUVZXWVNuSjBJNGhhb0p6cEQvMlgyMllQUmNEazJqU1E1NC9jRzJtbXdLYnNCUjNUNjJPM3hFU3c2dTN0Nm8zNVl6TkI5Q0ZxWUdJbnNyNnZtSW9qR3VTZWVHc2NrQlVOUk5vMHZLbmx6Snc3TDNVRndZRk1lMkF1aXJ4bUVyZDA0UGtFRnBybFVId0F3QUFwWkRsUUd1YVl0d0lyVUM5NHZFYUtGQ1FnMEVYTENwMnV3VS9pWkQzWDRaSCtaTHpqSHJLYzJGVitqQXRSMmNYeVRGTEhsNW5GdG4wZFVMUWpxS09JbWNZOGhHYkQwOUdjVXEvK1dTRXhYUDNyeGdDQUROY0QrS2hTVGRncUpBbXU2ajUyV3RJckcwVVd6Mi9pYVc2NE9qQ2N4RlBvK20yL2d0UkVLRXk5U2hWMlRFNWo5cVM2OWhHOHcvaWh1aWt3SkgybUVuZk5Fcmg3eTJjYlJ3aWtzSG1Pam9sbVRpMVFIWjgrc1VtczVwQVFwdEZXekIweFJzQlB3L0hWUU1CZlozNWJIM2FJbGEyd2VVcEFnMGpablg0OWtMMjQzK1BUNXVNemE0Z01sa253Q2VSK2Zz; FedAuth1=TXFwODJ6dEhlVlZRVXdrWEEyWElWalNxejBXZVRZT3lIcDJQeG4yMUxFMkN0c01ScDlPT253MkY0TWo4M1R4NGNGbFNDaWxGaXV3ZkhIc3VYdW45WUVPMGlrdjUyNC9lbisxUDZWeUlzUVd6QWo4WmJQQ0RQTmk1dG1kTFVOTC9kOTVFUU1IcGRBTVdMbm9BbmQ3MXlMbGJYWWlhb3VlK0hOQzgzbDFpQVIvNEtWOVAwN3lwa3VTNFZOc3VNT1NJZWl0MGFjRmpma204dXpONUZqdVo4UnRXN0lGZDJjSk1PK3dOR09xd2puYkZCaDN0MllQV3J5UkxZTUt6cG1ZMDN1RGp3WFdNOGsrcFVXVWRJc3JmWklIb253bnV3cWFJTmRYTWRKMEsvd3ZDQWlNMnF3M0VsWmdtcGdzZzlPS2hWSElvcmVHOVI3MFhkc3F5QjF4c3RIajJSdmw0YUQwSytBSlk4VGdZTHpUbndCckxxRUZIQ3YvN2N6b0x1allIUDFUcDlnUjN2cEVzd0xqcU9iaGhyUFZjWGtTYWlnRGJwNlFxWThPWG1jb1pkalRUU1pEMVFpNE1uUk01ZHRodWJrblJPdkh1aE5uZmdtdTQ0TFVCTEZoczJaZ040QVBCZ3F1bVJqSEVMRlhDMmZLQ3h5bGl5c09HK1QvWmVmTHNkdkYvNVRrZ1MwdVVkTmRBa21CQ1k5eVQ5MFBRdG9sTmtsWXUraFE1SkNqUHdEbit0UTNOTFhIOEtHVjUrK04yRms4cDl4WkZpM0FUZTFONFJpY3Uyd1hrNk4weEhvamNCMHlHUHRmN1Fscm4yQmFland4SnVPUnExc01CcmdYRjZhOTU2VHp6bGp5UmpGSmZqYWpvdUFBUzBNY3MwRzhKMDNmdUhPcVROeFJUT3VDcDU2b0JRc1JnbmsyczNiTnUrSGcwTkhDMTZLK2gzZmVoMEJPTHgxM3VQeSs1MnU1YThONEZKUlRpYllPenJGNEYvRHlseGlFVXUzWmRPN2pKVjA2bmVWdllFT1ZWTmV4YlNob0g2WjFTR2JqZWJiaXFQbjdaNDBzK1htdmhLZUk4RzJydlVqR0M1TVptU0JCcm9nV2x2MFhVa045ZkxYRWFpTllJZ20yaFRXa0gycTd3MVNwWmdJVTMvMWdiU3RBMndTOXk1dmVyVGc1ZzBKbTZaZmpCNlRFOTI3c0p6WWJkVmtlNlB5WGdHZ1JwalFCd1F5dzV3bTFFeWQyR3F5d1hwWnBYRDhUZWE3cGFOQ3BhMzFjOEMzSG5EU3BWQ0ZoN2trejEydEF0TDZxRWlSTTF5ZXdkQUNtUHVmcE05K21JRjNFb2s1dGhGMEpOSEk4WVR1N25xQXBGNDk5Q3E8L0Nvb2tpZT48L1NlY3VyaXR5Q29udGV4dFRva2VuPg==; _ga=GA1.2.1743407107.1527691233; _gid=GA1.2.1424031589.1527691233; X-VSS-UseRequestRouting=True; VstsSession=%7B%22PersistentSessionId%22%3A%22dd5a0b67-a482-4aac-9f40-a17f1798de26%22%2C%22PendingAuthenticationSessionId%22%3A%2200000000-0000-0000-0000-000000000000%22%2C%22CurrentAuthenticationSessionId%22%3A%2214c3cb29-6759-4e69-b9cd-17d39edb9abb%22%7D");

		String credsText		= user+":"+pass;
		byte[] credsBytes		= credsText.getBytes();
		byte[] credsBase64Bytes = Base64.encodeBase64(credsBytes);
		String credsBase64		= new String(credsBase64Bytes);
		
		log.debug("-  Authorization=[Basic " + credsBase64+"]");
		headers.put("Authorization", "Basic " + credsBase64);
		
		log.info("<-  buildHeaders()");
	}
	
	/**
	 * Configuracao do Client
	 */
	protected void buildClient() {
		
		log.info("-> buildClient() ");
		
		this.cred		= new UsernamePasswordCredentials(user,pass); 
		this.connManager= new PoolingHttpClientConnectionManager();
		
		restClientBuild = new RestClientBuild();
		restClientBuild.setCredentials	 (this.cred); 
		restClientBuild.setAuthScope	 (AuthScope.ANY);
		restClientBuild.setConnManager	 (this.connManager);
		restClientBuild.setSoTimeout	 (10000000);
		restClientBuild.setSoReuseAddress(false);
		restClientBuild.setSoLinger		 (100000);
		restClientBuild.setSoKeepAlive	 (true);
		restClientBuild.setTcpNoDelay	 (false);
		restClientBuild.setRcvBufSize	 (99999);
		restClientBuild.setSndBufSize	 (99999);
		
		restClientServiceImpl = new RestClientServiceImpl();
		restClientServiceImpl.setRestClientBuild(restClientBuild);
		
		log.info("<- restClientBuild=["+restClientBuild+"]");
		log.info("<- buildClient() ");
	}

	
}
