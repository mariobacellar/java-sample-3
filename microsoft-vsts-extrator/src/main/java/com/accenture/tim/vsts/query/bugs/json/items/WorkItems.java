package com.accenture.tim.vsts.query.bugs.json.items;
import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * http://www.jsonschema2pojo.org/
 * @author mario.bacellar
 *
 */
public class WorkItems implements Serializable {

	@SerializedName("id")
	@Expose
	private Integer id;

	@SerializedName("url")
	@Expose
	private String url;
	
	private final static long serialVersionUID = 7027210464109931929L;

	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("id", id).append("url", url).toString();
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(id).append(url).toHashCode();
	}
	
	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if ((other instanceof WorkItems) == false) {
			return false;
		}
		WorkItems rhs = ((WorkItems) other);
		return new EqualsBuilder().append(id, rhs.id).append(url, rhs.url).isEquals();
	}
	
}