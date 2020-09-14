package com.accenture.tim.vsts.query.brachs.ativas.json.branch;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Value implements Serializable {

	@SerializedName("name")
	@Expose
	private String name;
	@SerializedName("objectId")
	@Expose
	private String objectId;
	@SerializedName("creator")
	@Expose
	private Creator creator;
	@SerializedName("url")
	@Expose
	private String url;
	private final static long serialVersionUID = 2460621301512479934L;
	
	public String getName() {
	return name;
	}
	
	public void setName(String name) {
	this.name = name;
	}
	
	public String getObjectId() {
	return objectId;
	}
	
	public void setObjectId(String objectId) {
	this.objectId = objectId;
	}
	
	public Creator getCreator() {
	return creator;
	}
	
	public void setCreator(Creator creator) {
	this.creator = creator;
	}
	
	public String getUrl() {
	return url;
	}
	
	public void setUrl(String url) {
	this.url = url;
	}
	
	@Override
	public String toString() {
	return new ToStringBuilder(this).append("name", name).append("objectId", objectId).append("creator", creator).append("url", url).toString();
	}
	
	@Override
	public int hashCode() {
	return new HashCodeBuilder().append(objectId).append(name).append(url).append(creator).toHashCode();
	}
	
	@Override
	public boolean equals(Object other) {
	if (other == this) {
	return true;
	}
	if ((other instanceof Value) == false) {
	return false;
	}
	Value rhs = ((Value) other);
	return new EqualsBuilder().append(objectId, rhs.objectId).append(name, rhs.name).append(url, rhs.url).append(creator, rhs.creator).isEquals();
	}

}