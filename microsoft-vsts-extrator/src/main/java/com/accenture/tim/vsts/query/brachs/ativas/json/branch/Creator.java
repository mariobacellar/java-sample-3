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
public class Creator implements Serializable {

	@SerializedName("displayName")
	@Expose
	private String displayName;
	@SerializedName("url")
	@Expose
	private String url;
	@SerializedName("_links")
	@Expose
	private Links links;
	@SerializedName("id")
	@Expose
	private String id;
	@SerializedName("uniqueName")
	@Expose
	private String uniqueName;
	@SerializedName("imageUrl")
	@Expose
	private String imageUrl;
	@SerializedName("descriptor")
	@Expose
	private String descriptor;
	private final static long serialVersionUID = -4842499306471200400L;
	
	public String getDisplayName() {
	return displayName;
	}
	
	public void setDisplayName(String displayName) {
	this.displayName = displayName;
	}
	
	public String getUrl() {
	return url;
	}
	
	public void setUrl(String url) {
	this.url = url;
	}
	
	public Links getLinks() {
	return links;
	}
	
	public void setLinks(Links links) {
	this.links = links;
	}
	
	public String getId() {
	return id;
	}
	
	public void setId(String id) {
	this.id = id;
	}
	
	public String getUniqueName() {
	return uniqueName;
	}
	
	public void setUniqueName(String uniqueName) {
	this.uniqueName = uniqueName;
	}
	
	public String getImageUrl() {
	return imageUrl;
	}
	
	public void setImageUrl(String imageUrl) {
	this.imageUrl = imageUrl;
	}
	
	public String getDescriptor() {
	return descriptor;
	}
	
	public void setDescriptor(String descriptor) {
	this.descriptor = descriptor;
	}
	
	@Override
	public String toString() {
	return new ToStringBuilder(this).append("displayName", displayName).append("url", url).append("links", links).append("id", id).append("uniqueName", uniqueName).append("imageUrl", imageUrl).append("descriptor", descriptor).toString();
	}
	
	@Override
	public int hashCode() {
	return new HashCodeBuilder().append(id).append(descriptor).append(imageUrl).append(links).append(displayName).append(uniqueName).append(url).toHashCode();
	}
	
	@Override
	public boolean equals(Object other) {
	if (other == this) {
	return true;
	}
	if ((other instanceof Creator) == false) {
	return false;
	}
	Creator rhs = ((Creator) other);
	return new EqualsBuilder().append(id, rhs.id).append(descriptor, rhs.descriptor).append(imageUrl, rhs.imageUrl).append(links, rhs.links).append(displayName, rhs.displayName).append(uniqueName, rhs.uniqueName).append(url, rhs.url).isEquals();
	}
	
}
