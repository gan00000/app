package com.efun.platform.push;

import com.efun.platform.module.BaseDataBean;

public class PushInfoBean extends BaseDataBean{
	private long messageId;
	private String content;
	private String title;
	private int pushType;
	private String pushUrl;
	private PushParamsBean pushParams;
	private String curPageName;
	public PushInfoBean() {
	}

	public PushInfoBean(int pushType, String pushUrl) {
		this.pushType = pushType;
		this.pushUrl = pushUrl;
	}

	public long getMessageId() {
		return messageId;
	}

	public void setMessageId(long messageId) {
		this.messageId = messageId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public PushInfoBean( PushParamsBean pushParams) {
		this.pushParams = pushParams;
	}

	public int getPushType() {
		return pushType;
	}
	public void setPushType(int pushType) {
		this.pushType = pushType;
	}
	public String getPushUrl() {
		return pushUrl;
	}
	public void setPushUrl(String pushUrl) {
		this.pushUrl = pushUrl;
	}
	public PushParamsBean getPushParams() {
		return pushParams;
	}
	public void setPushParams(PushParamsBean pushParams) {
		this.pushParams = pushParams;
	}

	public String getCurPageName() {
		return curPageName;
	}

	public void setCurPageName(String curPageName) {
		this.curPageName = curPageName;
	}
	
	
}
