package com.efun.platform.http.request.bean;

/**
 * 好康 - 礼包 - 我的序列号中心
 * @author Jesse
 *
 */
public class GiftSelfListRequest extends BaseRequestBean{
	private String uid;
	private String currentPage;
	private String pageSize;
	private String userName	;
	private String device;
	private String platform;
	private String sign;
	private String timestamp;
	public GiftSelfListRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public GiftSelfListRequest(String currentPage, String pageSize,
			String userName,String device,String platform) {
		super();
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		this.userName = userName;
		this.device = device;
		this.platform = platform;
	}
	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}
	public String getPageSize() {
		return pageSize;
	}
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}
	
	
}
