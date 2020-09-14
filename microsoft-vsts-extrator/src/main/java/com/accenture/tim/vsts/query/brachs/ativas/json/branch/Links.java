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
public class Links implements Serializable {

	@SerializedName("avatar")
	@Expose
	private Avatar avatar;
	private final static long serialVersionUID = -451051130803942067L;
	
	public Avatar getAvatar() {
	return avatar;
	}
	
	public void setAvatar(Avatar avatar) {
	this.avatar = avatar;
	}
	
	@Override
	public String toString() {
	return new ToStringBuilder(this).append("avatar", avatar).toString();
	}
	
	@Override
	public int hashCode() {
	return new HashCodeBuilder().append(avatar).toHashCode();
	}
	
	@Override
	public boolean equals(Object other) {
	if (other == this) {
	return true;
	}
	if ((other instanceof Links) == false) {
	return false;
	}
	Links rhs = ((Links) other);
	return new EqualsBuilder().append(avatar, rhs.avatar).isEquals();
	}
	
}
