package com.accenture.tim.vsts.query.brachs.ativas.json.branch;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
* http://www.jsonschema2pojo.org/
* @author mario.bacellar
*
*/
public class Avatar implements Serializable {

	@SerializedName("href")
	@Expose
	private String href;
	private final static long serialVersionUID = -684080308621001904L;
	
	public String getHref() {
	return href;
	}
	
	public void setHref(String href) {
	this.href = href;
	}
	
	@Override
	public String toString() {
	return new ToStringBuilder(this).append("href", href).toString();
	}
	
	@Override
	public int hashCode() {
	return new HashCodeBuilder().append(href).toHashCode();
	}
	
	@Override
	public boolean equals(Object other) {
	if (other == this) {
	return true;
	}
	if ((other instanceof Avatar) == false) {
	return false;
	}
	Avatar rhs = ((Avatar) other);
	return new EqualsBuilder().append(href, rhs.href).isEquals();
	}
	
}
