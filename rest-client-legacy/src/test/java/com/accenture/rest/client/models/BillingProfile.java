package com.accenture.rest.client.models;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BillingProfile {
	
	@Expose
	@SerializedName("document")
	public String document;
	@Expose
	@SerializedName("paymentResp")
	public Boolean paymentResp;
	@Expose
	@SerializedName("custCode")
	public String custCode;
	@Expose
	@SerializedName("csLevel")
	public String csLevel;
	@Expose
	@SerializedName("billingProfileId")
	public String billingProfileId;
	/**
	 * @return the document
	 */
	public String getDocument() {
		return document;
	}
	/**
	 * @param document the document to set
	 */
	public void setDocument(String document) {
		this.document = document;
	}
	/**
	 * @return the paymentResp
	 */
	public Boolean getPaymentResp() {
		return paymentResp;
	}
	/**
	 * @param paymentResp the paymentResp to set
	 */
	public void setPaymentResp(Boolean paymentResp) {
		this.paymentResp = paymentResp;
	}
	/**
	 * @return the custCode
	 */
	public String getCustCode() {
		return custCode;
	}
	/**
	 * @param custCode the custCode to set
	 */
	public void setCustCode(String custCode) {
		this.custCode = custCode;
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
	 * @return the billingProfileId
	 */
	public String getBillingProfileId() {
		return billingProfileId;
	}
	/**
	 * @param billingProfileId the billingProfileId to set
	 */
	public void setBillingProfileId(String billingProfileId) {
		this.billingProfileId = billingProfileId;
	}
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((billingProfileId == null) ? 0 : billingProfileId.hashCode());
		result = prime * result + ((csLevel == null) ? 0 : csLevel.hashCode());
		result = prime * result + ((custCode == null) ? 0 : custCode.hashCode());
		result = prime * result + ((document == null) ? 0 : document.hashCode());
		result = prime * result + ((paymentResp == null) ? 0 : paymentResp.hashCode());
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BillingProfile other = (BillingProfile) obj;
		if (billingProfileId == null) {
			if (other.billingProfileId != null)
				return false;
		} else if (!billingProfileId.equals(other.billingProfileId))
			return false;
		if (csLevel == null) {
			if (other.csLevel != null)
				return false;
		} else if (!csLevel.equals(other.csLevel))
			return false;
		if (custCode == null) {
			if (other.custCode != null)
				return false;
		} else if (!custCode.equals(other.custCode))
			return false;
		if (document == null) {
			if (other.document != null)
				return false;
		} else if (!document.equals(other.document))
			return false;
		if (paymentResp == null) {
			if (other.paymentResp != null)
				return false;
		} else if (!paymentResp.equals(other.paymentResp))
			return false;
		return true;
	}
}