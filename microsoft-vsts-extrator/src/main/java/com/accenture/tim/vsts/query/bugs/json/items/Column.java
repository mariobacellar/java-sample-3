package com.accenture.tim.vsts.query.bugs.json.items;
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
public class Column implements Serializable {

	@SerializedName("referenceName")
	@Expose
	private String referenceName;
	
	@SerializedName("name")
	@Expose
	private String name;
	
	@SerializedName("url")
	@Expose
	private String url;

	private final static long serialVersionUID = 831904913734112627L;
	
	public String getReferenceName() {
		return referenceName;
	}
	
	public void setReferenceName(String referenceName) {
		this.referenceName = referenceName;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this).append("referenceName", referenceName).append("name", name).append("url", url).toString();
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(name).append(url).append(referenceName).toHashCode();
	}
	
	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if ((other instanceof Column) == false) {
			return false;
		}
		Column rhs = ((Column) other);
		return new EqualsBuilder().append(name, rhs.name).append(url, rhs.url).append(referenceName, rhs.referenceName).isEquals();
	}

}
