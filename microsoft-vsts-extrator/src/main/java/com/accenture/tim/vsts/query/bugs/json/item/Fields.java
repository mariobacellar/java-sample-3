package com.accenture.tim.vsts.query.bugs.json.item;

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
public class Fields implements Serializable
{

@SerializedName("System.AreaPath")
@Expose
private String systemAreaPath;
@SerializedName("System.TeamProject")
@Expose
private String systemTeamProject;
@SerializedName("System.IterationPath")
@Expose
private String systemIterationPath;
@SerializedName("System.WorkItemType")
@Expose
private String systemWorkItemType;
@SerializedName("System.State")
@Expose
private String systemState;
@SerializedName("System.Reason")
@Expose
private String systemReason;
@SerializedName("System.AssignedTo")
@Expose
private String systemAssignedTo;
@SerializedName("System.CreatedDate")
@Expose
private String systemCreatedDate;
@SerializedName("System.CreatedBy")
@Expose
private String systemCreatedBy;
@SerializedName("System.ChangedDate")
@Expose
private String systemChangedDate;
@SerializedName("System.ChangedBy")
@Expose
private String systemChangedBy;
@SerializedName("System.Title")
@Expose
private String systemTitle;
@SerializedName("System.BoardColumn")
@Expose
private String systemBoardColumn;
@SerializedName("System.BoardColumnDone")
@Expose
private Boolean systemBoardColumnDone;
@SerializedName("Microsoft.VSTS.Common.Priority")
@Expose
private Integer microsoftVSTSCommonPriority;
@SerializedName("Microsoft.VSTS.Common.StateChangeDate")
@Expose
private String microsoftVSTSCommonStateChangeDate;
@SerializedName("Microsoft.VSTS.Scheduling.Effort")
@Expose
private Float microsoftVSTSSchedulingEffort;
@SerializedName("Microsoft.VSTS.Common.ValueArea")
@Expose
private String microsoftVSTSCommonValueArea;
@SerializedName("Microsoft.VSTS.Common.ActivatedDate")
@Expose
private String microsoftVSTSCommonActivatedDate;
@SerializedName("Microsoft.VSTS.Common.ActivatedBy")
@Expose
private String microsoftVSTSCommonActivatedBy;
@SerializedName("Microsoft.VSTS.Scheduling.TargetDate")
@Expose
private String microsoftVSTSSchedulingTargetDate;
@SerializedName("Microsoft.VSTS.Common.Triage")
@Expose
private String microsoftVSTSCommonTriage;
@SerializedName("Microsoft.VSTS.Common.StackRank")
@Expose
private Float microsoftVSTSCommonStackRank;
@SerializedName("WEF_24A59BE5FBA54C8A971FBF0D0A4E929F_Kanban.Column")
@Expose
private String wEF24A59BE5FBA54C8A971FBF0D0A4E929FKanbanColumn;
@SerializedName("WEF_24A59BE5FBA54C8A971FBF0D0A4E929F_Kanban.Column.Done")
@Expose
private Boolean wEF24A59BE5FBA54C8A971FBF0D0A4E929FKanbanColumnDone;
@SerializedName("CMMIPLT.Textfield3")
@Expose
private String cMMIPLTTextfield3;
@SerializedName("Custom.7f160bc4-aa99-459b-ae00-4812e6a8ba4c")
@Expose
private Boolean custom7f160bc4Aa99459bAe004812e6a8ba4c;
@SerializedName("WEF_D094A52300214A8091F309E26E8ACA28_Kanban.Column")
@Expose
private String wEFD094A52300214A8091F309E26E8ACA28KanbanColumn;
@SerializedName("WEF_D094A52300214A8091F309E26E8ACA28_Kanban.Column.Done")
@Expose
private Boolean wEFD094A52300214A8091F309E26E8ACA28KanbanColumnDone;
@SerializedName("CMMIPLT.InternalStatus")
@Expose
private String cMMIPLTInternalStatus;
private final static long serialVersionUID = 6787974331201629159L;

public String getSystemAreaPath() {
return systemAreaPath;
}

public void setSystemAreaPath(String systemAreaPath) {
this.systemAreaPath = systemAreaPath;
}

public String getSystemTeamProject() {
return systemTeamProject;
}

public void setSystemTeamProject(String systemTeamProject) {
this.systemTeamProject = systemTeamProject;
}

public String getSystemIterationPath() {
return systemIterationPath;
}

public void setSystemIterationPath(String systemIterationPath) {
this.systemIterationPath = systemIterationPath;
}

public String getSystemWorkItemType() {
return systemWorkItemType;
}

public void setSystemWorkItemType(String systemWorkItemType) {
this.systemWorkItemType = systemWorkItemType;
}

public String getSystemState() {
return systemState;
}

public void setSystemState(String systemState) {
this.systemState = systemState;
}

public String getSystemReason() {
return systemReason;
}

public void setSystemReason(String systemReason) {
this.systemReason = systemReason;
}

public String getSystemAssignedTo() {
return systemAssignedTo;
}

public void setSystemAssignedTo(String systemAssignedTo) {
this.systemAssignedTo = systemAssignedTo;
}

public String getSystemCreatedDate() {
return systemCreatedDate;
}

public void setSystemCreatedDate(String systemCreatedDate) {
this.systemCreatedDate = systemCreatedDate;
}

public String getSystemCreatedBy() {
return systemCreatedBy;
}

public void setSystemCreatedBy(String systemCreatedBy) {
this.systemCreatedBy = systemCreatedBy;
}

public String getSystemChangedDate() {
return systemChangedDate;
}

public void setSystemChangedDate(String systemChangedDate) {
this.systemChangedDate = systemChangedDate;
}

public String getSystemChangedBy() {
return systemChangedBy;
}

public void setSystemChangedBy(String systemChangedBy) {
this.systemChangedBy = systemChangedBy;
}

public String getSystemTitle() {
return systemTitle;
}

public void setSystemTitle(String systemTitle) {
this.systemTitle = systemTitle;
}

public String getSystemBoardColumn() {
return systemBoardColumn;
}

public void setSystemBoardColumn(String systemBoardColumn) {
this.systemBoardColumn = systemBoardColumn;
}

public Boolean getSystemBoardColumnDone() {
return systemBoardColumnDone;
}

public void setSystemBoardColumnDone(Boolean systemBoardColumnDone) {
this.systemBoardColumnDone = systemBoardColumnDone;
}

public Integer getMicrosoftVSTSCommonPriority() {
return microsoftVSTSCommonPriority;
}

public void setMicrosoftVSTSCommonPriority(Integer microsoftVSTSCommonPriority) {
this.microsoftVSTSCommonPriority = microsoftVSTSCommonPriority;
}

public String getMicrosoftVSTSCommonStateChangeDate() {
return microsoftVSTSCommonStateChangeDate;
}

public void setMicrosoftVSTSCommonStateChangeDate(String microsoftVSTSCommonStateChangeDate) {
this.microsoftVSTSCommonStateChangeDate = microsoftVSTSCommonStateChangeDate;
}

public Float getMicrosoftVSTSSchedulingEffort() {
return microsoftVSTSSchedulingEffort;
}

public void setMicrosoftVSTSSchedulingEffort(Float microsoftVSTSSchedulingEffort) {
this.microsoftVSTSSchedulingEffort = microsoftVSTSSchedulingEffort;
}

public String getMicrosoftVSTSCommonValueArea() {
return microsoftVSTSCommonValueArea;
}

public void setMicrosoftVSTSCommonValueArea(String microsoftVSTSCommonValueArea) {
this.microsoftVSTSCommonValueArea = microsoftVSTSCommonValueArea;
}

public String getMicrosoftVSTSCommonActivatedDate() {
return microsoftVSTSCommonActivatedDate;
}

public void setMicrosoftVSTSCommonActivatedDate(String microsoftVSTSCommonActivatedDate) {
this.microsoftVSTSCommonActivatedDate = microsoftVSTSCommonActivatedDate;
}

public String getMicrosoftVSTSCommonActivatedBy() {
return microsoftVSTSCommonActivatedBy;
}

public void setMicrosoftVSTSCommonActivatedBy(String microsoftVSTSCommonActivatedBy) {
this.microsoftVSTSCommonActivatedBy = microsoftVSTSCommonActivatedBy;
}

public String getMicrosoftVSTSSchedulingTargetDate() {
return microsoftVSTSSchedulingTargetDate;
}

public void setMicrosoftVSTSSchedulingTargetDate(String microsoftVSTSSchedulingTargetDate) {
this.microsoftVSTSSchedulingTargetDate = microsoftVSTSSchedulingTargetDate;
}

public String getMicrosoftVSTSCommonTriage() {
return microsoftVSTSCommonTriage;
}

public void setMicrosoftVSTSCommonTriage(String microsoftVSTSCommonTriage) {
this.microsoftVSTSCommonTriage = microsoftVSTSCommonTriage;
}

public Float getMicrosoftVSTSCommonStackRank() {
return microsoftVSTSCommonStackRank;
}

public void setMicrosoftVSTSCommonStackRank(Float microsoftVSTSCommonStackRank) {
this.microsoftVSTSCommonStackRank = microsoftVSTSCommonStackRank;
}

public String getWEF24A59BE5FBA54C8A971FBF0D0A4E929FKanbanColumn() {
return wEF24A59BE5FBA54C8A971FBF0D0A4E929FKanbanColumn;
}

public void setWEF24A59BE5FBA54C8A971FBF0D0A4E929FKanbanColumn(String wEF24A59BE5FBA54C8A971FBF0D0A4E929FKanbanColumn) {
this.wEF24A59BE5FBA54C8A971FBF0D0A4E929FKanbanColumn = wEF24A59BE5FBA54C8A971FBF0D0A4E929FKanbanColumn;
}

public Boolean getWEF24A59BE5FBA54C8A971FBF0D0A4E929FKanbanColumnDone() {
return wEF24A59BE5FBA54C8A971FBF0D0A4E929FKanbanColumnDone;
}

public void setWEF24A59BE5FBA54C8A971FBF0D0A4E929FKanbanColumnDone(Boolean wEF24A59BE5FBA54C8A971FBF0D0A4E929FKanbanColumnDone) {
this.wEF24A59BE5FBA54C8A971FBF0D0A4E929FKanbanColumnDone = wEF24A59BE5FBA54C8A971FBF0D0A4E929FKanbanColumnDone;
}

public String getCMMIPLTTextfield3() {
return cMMIPLTTextfield3;
}

public void setCMMIPLTTextfield3(String cMMIPLTTextfield3) {
this.cMMIPLTTextfield3 = cMMIPLTTextfield3;
}

public Boolean getCustom7f160bc4Aa99459bAe004812e6a8ba4c() {
return custom7f160bc4Aa99459bAe004812e6a8ba4c;
}

public void setCustom7f160bc4Aa99459bAe004812e6a8ba4c(Boolean custom7f160bc4Aa99459bAe004812e6a8ba4c) {
this.custom7f160bc4Aa99459bAe004812e6a8ba4c = custom7f160bc4Aa99459bAe004812e6a8ba4c;
}

public String getWEFD094A52300214A8091F309E26E8ACA28KanbanColumn() {
return wEFD094A52300214A8091F309E26E8ACA28KanbanColumn;
}

public void setWEFD094A52300214A8091F309E26E8ACA28KanbanColumn(String wEFD094A52300214A8091F309E26E8ACA28KanbanColumn) {
this.wEFD094A52300214A8091F309E26E8ACA28KanbanColumn = wEFD094A52300214A8091F309E26E8ACA28KanbanColumn;
}

public Boolean getWEFD094A52300214A8091F309E26E8ACA28KanbanColumnDone() {
return wEFD094A52300214A8091F309E26E8ACA28KanbanColumnDone;
}

public void setWEFD094A52300214A8091F309E26E8ACA28KanbanColumnDone(Boolean wEFD094A52300214A8091F309E26E8ACA28KanbanColumnDone) {
this.wEFD094A52300214A8091F309E26E8ACA28KanbanColumnDone = wEFD094A52300214A8091F309E26E8ACA28KanbanColumnDone;
}

public String getCMMIPLTInternalStatus() {
return cMMIPLTInternalStatus;
}

public void setCMMIPLTInternalStatus(String cMMIPLTInternalStatus) {
this.cMMIPLTInternalStatus = cMMIPLTInternalStatus;
}

@Override
public String toString() {
return new ToStringBuilder(this).append("systemAreaPath", systemAreaPath).append("systemTeamProject", systemTeamProject).append("systemIterationPath", systemIterationPath).append("systemWorkItemType", systemWorkItemType).append("systemState", systemState).append("systemReason", systemReason).append("systemAssignedTo", systemAssignedTo).append("systemCreatedDate", systemCreatedDate).append("systemCreatedBy", systemCreatedBy).append("systemChangedDate", systemChangedDate).append("systemChangedBy", systemChangedBy).append("systemTitle", systemTitle).append("systemBoardColumn", systemBoardColumn).append("systemBoardColumnDone", systemBoardColumnDone).append("microsoftVSTSCommonPriority", microsoftVSTSCommonPriority).append("microsoftVSTSCommonStateChangeDate", microsoftVSTSCommonStateChangeDate).append("microsoftVSTSSchedulingEffort", microsoftVSTSSchedulingEffort).append("microsoftVSTSCommonValueArea", microsoftVSTSCommonValueArea).append("microsoftVSTSCommonActivatedDate", microsoftVSTSCommonActivatedDate).append("microsoftVSTSCommonActivatedBy", microsoftVSTSCommonActivatedBy).append("microsoftVSTSSchedulingTargetDate", microsoftVSTSSchedulingTargetDate).append("microsoftVSTSCommonTriage", microsoftVSTSCommonTriage).append("microsoftVSTSCommonStackRank", microsoftVSTSCommonStackRank).append("wEF24A59BE5FBA54C8A971FBF0D0A4E929FKanbanColumn", wEF24A59BE5FBA54C8A971FBF0D0A4E929FKanbanColumn).append("wEF24A59BE5FBA54C8A971FBF0D0A4E929FKanbanColumnDone", wEF24A59BE5FBA54C8A971FBF0D0A4E929FKanbanColumnDone).append("cMMIPLTTextfield3", cMMIPLTTextfield3).append("custom7f160bc4Aa99459bAe004812e6a8ba4c", custom7f160bc4Aa99459bAe004812e6a8ba4c).append("wEFD094A52300214A8091F309E26E8ACA28KanbanColumn", wEFD094A52300214A8091F309E26E8ACA28KanbanColumn).append("wEFD094A52300214A8091F309E26E8ACA28KanbanColumnDone", wEFD094A52300214A8091F309E26E8ACA28KanbanColumnDone).append("cMMIPLTInternalStatus", cMMIPLTInternalStatus).toString();
}

@Override
public int hashCode() {
return new HashCodeBuilder().append(cMMIPLTInternalStatus).append(cMMIPLTTextfield3).append(microsoftVSTSCommonStateChangeDate).append(microsoftVSTSCommonStackRank).append(systemChangedDate).append(wEFD094A52300214A8091F309E26E8ACA28KanbanColumn).append(microsoftVSTSSchedulingEffort).append(systemIterationPath).append(systemCreatedBy).append(systemWorkItemType).append(systemCreatedDate).append(systemTeamProject).append(systemState).append(microsoftVSTSCommonActivatedDate).append(microsoftVSTSCommonValueArea).append(microsoftVSTSCommonTriage).append(systemAreaPath).append(wEFD094A52300214A8091F309E26E8ACA28KanbanColumnDone).append(systemTitle).append(custom7f160bc4Aa99459bAe004812e6a8ba4c).append(systemBoardColumn).append(systemReason).append(microsoftVSTSSchedulingTargetDate).append(systemBoardColumnDone).append(systemAssignedTo).append(microsoftVSTSCommonActivatedBy).append(wEF24A59BE5FBA54C8A971FBF0D0A4E929FKanbanColumnDone).append(wEF24A59BE5FBA54C8A971FBF0D0A4E929FKanbanColumn).append(systemChangedBy).append(microsoftVSTSCommonPriority).toHashCode();
}

@Override
public boolean equals(Object other) {
if (other == this) {
return true;
}
if ((other instanceof Fields) == false) {
return false;
}
Fields rhs = ((Fields) other);
return new EqualsBuilder().append(cMMIPLTInternalStatus, rhs.cMMIPLTInternalStatus).append(cMMIPLTTextfield3, rhs.cMMIPLTTextfield3).append(microsoftVSTSCommonStateChangeDate, rhs.microsoftVSTSCommonStateChangeDate).append(microsoftVSTSCommonStackRank, rhs.microsoftVSTSCommonStackRank).append(systemChangedDate, rhs.systemChangedDate).append(wEFD094A52300214A8091F309E26E8ACA28KanbanColumn, rhs.wEFD094A52300214A8091F309E26E8ACA28KanbanColumn).append(microsoftVSTSSchedulingEffort, rhs.microsoftVSTSSchedulingEffort).append(systemIterationPath, rhs.systemIterationPath).append(systemCreatedBy, rhs.systemCreatedBy).append(systemWorkItemType, rhs.systemWorkItemType).append(systemCreatedDate, rhs.systemCreatedDate).append(systemTeamProject, rhs.systemTeamProject).append(systemState, rhs.systemState).append(microsoftVSTSCommonActivatedDate, rhs.microsoftVSTSCommonActivatedDate).append(microsoftVSTSCommonValueArea, rhs.microsoftVSTSCommonValueArea).append(microsoftVSTSCommonTriage, rhs.microsoftVSTSCommonTriage).append(systemAreaPath, rhs.systemAreaPath).append(wEFD094A52300214A8091F309E26E8ACA28KanbanColumnDone, rhs.wEFD094A52300214A8091F309E26E8ACA28KanbanColumnDone).append(systemTitle, rhs.systemTitle).append(custom7f160bc4Aa99459bAe004812e6a8ba4c, rhs.custom7f160bc4Aa99459bAe004812e6a8ba4c).append(systemBoardColumn, rhs.systemBoardColumn).append(systemReason, rhs.systemReason).append(microsoftVSTSSchedulingTargetDate, rhs.microsoftVSTSSchedulingTargetDate).append(systemBoardColumnDone, rhs.systemBoardColumnDone).append(systemAssignedTo, rhs.systemAssignedTo).append(microsoftVSTSCommonActivatedBy, rhs.microsoftVSTSCommonActivatedBy).append(wEF24A59BE5FBA54C8A971FBF0D0A4E929FKanbanColumnDone, rhs.wEF24A59BE5FBA54C8A971FBF0D0A4E929FKanbanColumnDone).append(wEF24A59BE5FBA54C8A971FBF0D0A4E929FKanbanColumn, rhs.wEF24A59BE5FBA54C8A971FBF0D0A4E929FKanbanColumn).append(systemChangedBy, rhs.systemChangedBy).append(microsoftVSTSCommonPriority, rhs.microsoftVSTSCommonPriority).isEquals();
}

}
