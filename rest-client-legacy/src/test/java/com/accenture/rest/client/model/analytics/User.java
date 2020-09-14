package com.accenture.rest.client.model.analytics;

import java.lang.reflect.Field;
import java.util.Hashtable;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class User {
	
	private String login;
	private String loginType;
	private String segment;
	private String subSegment;
	private String lineType;
	private String csLevel;
	private String paymentResponsible;
	private String planCode;
	private String planName;
	
	/**
	 * @return the subSegment
	 */
	public String getSubSegment() {
		return subSegment;
	}
	/**
	 * @param subSegment the subSegment to set
	 */
	public void setSubSegment(String subSegment) {
		this.subSegment = subSegment;
	}
	/**
	 * @return the csLevel
	 */
	public String getCsLevel() {
		return csLevel;
	}
	/**
	 * @param csLevel the csLevel to set
	 */
	public void setCsLevel(String csLevel) {
		this.csLevel = csLevel;
	}
	/**
	 * @return the paymentResponsible
	 */
	public String getPaymentResponsible() {
		return paymentResponsible;
	}
	/**
	 * @param paymentResponsible the paymentResponsible to set
	 */
	public void setPaymentResponsible(String paymentResponsible) {
		this.paymentResponsible = paymentResponsible;
	}
	/**
	 * @return the planCode
	 */
	public String getPlanCode() {
		return planCode;
	}
	/**
	 * @param planCode the planCode to set
	 */
	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}
	/**
	 * @return the planName
	 */
	public String getPlanName() {
		return planName;
	}
	/**
	 * @param planName the planName to set
	 */
	public void setPlanName(String planName) {
		this.planName = planName;
	}
	/**
	 * @return the login
	 */
	public String getLogin() {
		return login;
	}
	/**
	 * @param login the login to set
	 */
	public void setLogin(String login) {
		this.login = login;
	}
	/**
	 * @return the loginType
	 */
	public String getLoginType() {
		return loginType;
	}
	/**
	 * @param loginType the loginType to set
	 */
	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}
	/**
	 * @return the segment
	 */
	public String getSegment() {
		return segment;
	}
	/**
	 * @param segment the segment to set
	 */
	public void setSegment(String segment) {
		this.segment = segment;
	}
	/**
	 * @return the lineType
	 */
	public String getLineType() {
		return lineType;
	}
	/**
	 * @param lineType the lineType to set
	 */
	public void setLineType(String lineType) {
		this.lineType = lineType;
	}
	
	public Map<String, Object> toMap() {
		Map<String, Object> map = new Hashtable<String, Object>();
		
		for (Field field : this.getClass().getDeclaredFields()) {
			try {
				map.put(field.getName(), field.get(this));
			} catch (Exception e) {
			}
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
