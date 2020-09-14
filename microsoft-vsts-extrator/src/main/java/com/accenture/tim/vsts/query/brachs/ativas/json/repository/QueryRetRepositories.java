package com.accenture.tim.vsts.query.brachs.ativas.json.repository;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class QueryRetRepositories implements Serializable {

	@SerializedName("value")
	@Expose
	private List<Value> value = null;
	@SerializedName("count")
	@Expose
	private Integer count;
	private final static long serialVersionUID = -8109490383738583561L;
	
	public List<Value> getValue() {
	return value;
	}
	
	public void setValue(List<Value> value) {
	this.value = value;
	}
	
	public Integer getCount() {
	return count;
	}
	
	public void setCount(Integer count) {
	this.count = count;
	}
	
	@Override
	public String toString() {
	return new ToStringBuilder(this).append("value", value).append("count", count).toString();
	}
	
	@Override
	public int hashCode() {
	return new HashCodeBuilder().append(count).append(value).toHashCode();
	}
	
	@Override
	public boolean equals(Object other) {
	if (other == this) {
	return true;
	}
	if ((other instanceof QueryRetRepositories) == false) {
	return false;
	}
	QueryRetRepositories rhs = ((QueryRetRepositories) other);
	return new EqualsBuilder().append(count, rhs.count).append(value, rhs.value).isEquals();
	}

}
