package com.accenture.tim.vsts.query.brachs.ativas.json.repository;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Value implements Serializable
{

@SerializedName("id")
@Expose
private String id;
@SerializedName("name")
@Expose
private String name;
@SerializedName("url")
@Expose
private String url;
@SerializedName("project")
@Expose
private Project project;
@SerializedName("defaultBranch")
@Expose
private String defaultBranch;
@SerializedName("remoteUrl")
@Expose
private String remoteUrl;
@SerializedName("sshUrl")
@Expose
private String sshUrl;
private final static long serialVersionUID = 3049709020464442663L;

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

public String getUrl() {
return url;
}

public void setUrl(String url) {
this.url = url;
}

public Project getProject() {
return project;
}

public void setProject(Project project) {
this.project = project;
}

public String getDefaultBranch() {
return defaultBranch;
}

public void setDefaultBranch(String defaultBranch) {
this.defaultBranch = defaultBranch;
}

public String getRemoteUrl() {
return remoteUrl;
}

public void setRemoteUrl(String remoteUrl) {
this.remoteUrl = remoteUrl;
}

public String getSshUrl() {
return sshUrl;
}

public void setSshUrl(String sshUrl) {
this.sshUrl = sshUrl;
}

@Override
public String toString() {
return new ToStringBuilder(this).append("id", id).append("name", name).append("url", url).append("project", project).append("defaultBranch", defaultBranch).append("remoteUrl", remoteUrl).append("sshUrl", sshUrl).toString();
}

@Override
public int hashCode() {
return new HashCodeBuilder().append(id).append(project).append(sshUrl).append(name).append(remoteUrl).append(url).append(defaultBranch).toHashCode();
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
return new EqualsBuilder().append(id, rhs.id).append(project, rhs.project).append(sshUrl, rhs.sshUrl).append(name, rhs.name).append(remoteUrl, rhs.remoteUrl).append(url, rhs.url).append(defaultBranch, rhs.defaultBranch).isEquals();
}

}
