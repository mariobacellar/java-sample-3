package com.accenture.tim.vsts.query.brachs.ativas.json.repository;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Project implements Serializable
{

@SerializedName("id")
@Expose
private String id;
@SerializedName("name")
@Expose
private String name;
@SerializedName("description")
@Expose
private String description;
@SerializedName("url")
@Expose
private String url;
@SerializedName("state")
@Expose
private String state;
@SerializedName("revision")
@Expose
private Integer revision;
@SerializedName("visibility")
@Expose
private String visibility;
private final static long serialVersionUID = -2737708819644728838L;

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public String getDescription() {
return description;
}

public void setDescription(String description) {
this.description = description;
}

public String getUrl() {
return url;
}

public void setUrl(String url) {
this.url = url;
}

public String getState() {
return state;
}

public void setState(String state) {
this.state = state;
}

public Integer getRevision() {
return revision;
}

public void setRevision(Integer revision) {
this.revision = revision;
}

public String getVisibility() {
return visibility;
}

public void setVisibility(String visibility) {
this.visibility = visibility;
}

@Override
public String toString() {
return new ToStringBuilder(this).append("id", id).append("name", name).append("description", description).append("url", url).append("state", state).append("revision", revision).append("visibility", visibility).toString();
}

@Override
public int hashCode() {
return new HashCodeBuilder().append(id).append(revision).append(visibility).append(description).append(name).append(state).append(url).toHashCode();
}

@Override
public boolean equals(Object other) {
if (other == this) {
return true;
}
if ((other instanceof Project) == false) {
return false;
}
Project rhs = ((Project) other);
return new EqualsBuilder().append(id, rhs.id).append(revision, rhs.revision).append(visibility, rhs.visibility).append(description, rhs.description).append(name, rhs.name).append(state, rhs.state).append(url, rhs.url).isEquals();
}

}
