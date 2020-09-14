package com.accenture.rest.client.models;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Body {
	
	@Expose
	@SerializedName("billingProfile")
	private BillingProfile billingProfile;
	@Expose
	@SerializedName("customer")
	private Customer customer;

	public Body() {
	}

	public BillingProfile getBillingProfile() {
		return billingProfile;
	}

	public void setBillingProfile(BillingProfile billingProfile) {
		this.billingProfile = billingProfile;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
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
		result = prime * result + ((billingProfile == null) ? 0 : billingProfile.hashCode());
		result = prime * result + ((customer == null) ? 0 : customer.hashCode());
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
		Body other = (Body) obj;
		if (billingProfile == null) {
			if (other.billingProfile != null)
				return false;
		} else if (!billingProfile.equals(other.billingProfile))
			return false;
		if (customer == null) {
			if (other.customer != null)
				return false;
		} else if (!customer.equals(other.customer))
			return false;
		return true;
	}
}