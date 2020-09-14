package com.accenture.rest.client.model.analytics;

import java.util.Hashtable;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Headers {
	
	private String tid;
	private String stid;
	private String schema;
	private String cnField;
	private String authorization;
	
	/**
	 * @return the tid
	 */
	public String getTid() {
		return tid;
	}
	/**
	 * @param tid the tid to set
	 */
	public void setTid(String tid) {
		this.tid = tid;
	}

	/**
	 * @return the schema
	 */
	public String getSchema() {
		return schema;
	}
	/**
	 * @param schema the schema to set
	 */
	public void setSchema(String schema) {
		this.schema = schema;
	}
	/**
	 * @return the cnField
	 */
	public String getCnField() {
		return cnField;
	}
	/**
	 * @param cnField the cnField to set
	 */
	public void setCnField(String cnField) {
		this.cnField = cnField;
	}
	/**
	 * @return the authorization
	 */
	public String getAuthorization() {
		return authorization;
	}
	/**
	 * @param authorization the authorization to set
	 */
	public void setAuthorization(String authorization) {
		this.authorization = authorization;
	}
	/**
	 * @return the stid
	 */
	public String getStid() {
		return stid;
	}
	/**
	 * @param stid the stid to set
	 */
	public void setStid(String stid) {
		this.stid = stid;
	}
	
	public Map<String, String> toMap() {
				
		Map<String, String> map = new Hashtable<String, String>();		
		
		if(!StringUtils.isEmpty(authorization)){
			map.put(AnalyticsTagEventHeadersConstants.AUTHORIZATION, authorization);
		}
		
		if(!StringUtils.isEmpty(schema)){
			map.put(AnalyticsTagEventHeadersConstants.SCHEMA, schema);
		}
		
		if(!StringUtils.isEmpty(cnField)){
			map.put(AnalyticsTagEventHeadersConstants.CN_FIELD, cnField);
		}
		
		if(!StringUtils.isEmpty(stid)){
			map.put(AnalyticsTagEventHeadersConstants.STID, stid);
		}
		
		if(!StringUtils.isEmpty(tid)){
			map.put(AnalyticsTagEventHeadersConstants.TID, tid);
		}
		
		
		return map;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
