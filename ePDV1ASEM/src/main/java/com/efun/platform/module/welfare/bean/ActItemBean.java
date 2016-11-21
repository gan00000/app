package com.efun.platform.module.welfare.bean;

import com.efun.platform.module.BaseDataBean;
/**
 * 活动信息
 * @author itxuxxey
 *
 */
public class ActItemBean extends BaseDataBean{
	private String activityCode;
	private String activityName;
	private String activityUrl;
	private String img;
	private String startTime;
	private String endTime;
	private String isPayactivity;
	private String shareicon;
	public ActItemBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public ActItemBean(int beanType) {
		super(beanType);
		// TODO Auto-generated constructor stub
	}

	public ActItemBean(String activityCode, String activityName,
			String activityUrl, String img, String startTime, String endTime,
			String isPayactivity, String shareicon) {
		super();
		this.activityCode = activityCode;
		this.activityName = activityName;
		this.activityUrl = activityUrl;
		this.img = img;
		this.startTime = startTime;
		this.endTime = endTime;
		this.isPayactivity = isPayactivity;
		this.shareicon = shareicon;
	}
	public String getActivityCode() {
		return activityCode;
	}
	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
	}
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public String getActivityUrl() {
		return activityUrl;
	}
	public void setActivityUrl(String activityUrl) {
		this.activityUrl = activityUrl;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getIsPayactivity() {
		return isPayactivity;
	}
	public void setIsPayactivity(String isPayactivity) {
		this.isPayactivity = isPayactivity;
	}
	public String getShareicon() {
		return shareicon;
	}
	public void setShareicon(String shareicon) {
		this.shareicon = shareicon;
	}
	
}
