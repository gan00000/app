package com.efun.platform.http.request.bean;

/**
 * 檢測手机绑定状态
 * @author itxuxxey
 * 
 *
 */
public class CheckBindPhoneStatusRequest extends BaseRequestBean{
	private String platform;
	private String phone;
	private String language;
	private String fromType;
	private String uid;

	public CheckBindPhoneStatusRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CheckBindPhoneStatusRequest(String platform, String phone,
			String language, String fromType,String uid) {
		super();
		this.platform = platform;
		this.phone = phone;
		this.language = language;
		this.fromType = fromType;
		this.uid = uid;
	}
	
}
