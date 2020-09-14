package com.accenture.rest.client.model.analytics;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Channel {
	
	@Expose
	@SerializedName("name")
	private String name;
	@Expose
	@SerializedName("deviceOS")
	private String deviceOS;
	@Expose
	@SerializedName("deviceModel")
	private String deviceModel;
	@Expose
	@SerializedName("deviceManufacturer")
	private String deviceManufacturer;
	@Expose
	@SerializedName("appVersion")
	private String appVersion;
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the deviceOS
	 */
	public String getDeviceOS() {
		return deviceOS;
	}
	/**
	 * @param deviceOS the deviceOS to set
	 */
	public void setDeviceOS(String deviceOS) {
		this.deviceOS = deviceOS;
	}
	/**
	 * @return the deviceModel
	 */
	public String getDeviceModel() {
		return deviceModel;
	}
	/**
	 * @param deviceModel the deviceModel to set
	 */
	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}
	/**
	 * @return the deviceManufacturer
	 */
	public String getDeviceManufacturer() {
		return deviceManufacturer;
	}
	/**
	 * @param deviceManufacturer the deviceManufacturer to set
	 */
	public void setDeviceManufacturer(String deviceManufacturer) {
		this.deviceManufacturer = deviceManufacturer;
	}
	/**
	 * @return the appVersion
	 */
	public String getAppVersion() {
		return appVersion;
	}
	/**
	 * @param appVersion the appVersion to set
	 */
	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
