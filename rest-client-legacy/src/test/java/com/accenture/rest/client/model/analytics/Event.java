package com.accenture.rest.client.model.analytics;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Event {
	
	@Expose
	@SerializedName("hit-type")
	private String hitType;
	@Expose
	@SerializedName("campaign-source")
	private String campaignSource;
	@Expose
	@SerializedName("campaign-medium")
	private String campaignMedium;
	@Expose
	@SerializedName("campaign-name")
	private String campaignName;
	@Expose
	@SerializedName("campaign-content")
	private String campaignContent;
	@Expose
	@SerializedName("event-category")
	private String eventCategory;
	@Expose
	@SerializedName("event-action")
	private String eventAction;
	@Expose
	@SerializedName("event-label")
	private String eventLabel;
	@Expose
	@SerializedName("event-value")
	private String eventValue;
	@Expose
	@SerializedName("event-date")
	private String eventDate;
	@Expose
	@SerializedName("channel")
	private Channel channel;
	
	
	/**
	 * @return the hitType
	 */
	public String getHitType() {
		return hitType;
	}
	/**
	 * @param hitType the hitType to set
	 */
	public void setHitType(String hitType) {
		this.hitType = hitType;
	}
	/**
	 * @return the eventAction
	 */
	public String getEventAction() {
		return eventAction;
	}
	/**
	 * @param eventAction the eventAction to set
	 */
	public void setEventAction(String eventAction) {
		this.eventAction = eventAction;
	}
	/**
	 * @return the eventLabel
	 */
	public String getEventLabel() {
		return eventLabel;
	}
	/**
	 * @param eventLabel the eventLabel to set
	 */
	public void setEventLabel(String eventLabel) {
		this.eventLabel = eventLabel;
	}
	/**
	 * @return the eventDate
	 */
	public String getEventDate() {
		return eventDate;
	}
	/**
	 * @param eventDate the eventDate to set
	 */
	public void setEventDate(String eventDate) {
		this.eventDate = eventDate;
	}
	/**
	 * @return the channel
	 */
	public Channel getChannel() {
		return channel;
	}
	/**
	 * @param channel the channel to set
	 */
	public void setChannel(Channel channel) {
		this.channel = channel;
	}
	
	/**
	 * @return the campaignSource
	 */
	public String getCampaignSource() {
		return campaignSource;
	}
	/**
	 * @param campaignSource the campaignSource to set
	 */
	public void setCampaignSource(String campaignSource) {
		this.campaignSource = campaignSource;
	}
	/**
	 * @return the campaignMedium
	 */
	public String getCampaignMedium() {
		return campaignMedium;
	}
	/**
	 * @param campaignMedium the campaignMedium to set
	 */
	public void setCampaignMedium(String campaignMedium) {
		this.campaignMedium = campaignMedium;
	}
	/**
	 * @return the campaignName
	 */
	public String getCampaignName() {
		return campaignName;
	}
	/**
	 * @param campaignName the campaignName to set
	 */
	public void setCampaignName(String campaignName) {
		this.campaignName = campaignName;
	}
	/**
	 * @return the campaignContent
	 */
	public String getCampaignContent() {
		return campaignContent;
	}
	/**
	 * @param campaignContent the campaignContent to set
	 */
	public void setCampaignContent(String campaignContent) {
		this.campaignContent = campaignContent;
	}
	/**
	 * @return the eventCategory
	 */
	public String getEventCategory() {
		return eventCategory;
	}
	/**
	 * @param eventCategory the eventCategory to set
	 */
	public void setEventCategory(String eventCategory) {
		this.eventCategory = eventCategory;
	}
	/**
	 * @return the eventValue
	 */
	public String getEventValue() {
		return eventValue;
	}
	/**
	 * @param eventValue the eventValue to set
	 */
	public void setEventValue(String eventValue) {
		this.eventValue = eventValue;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
