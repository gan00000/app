package com.efun.platform.http.request.bean;

/**
 * 语音绑定手机
 * @author itxuxxey
 * (登录那边的接口)
 * assist_voice.shtml
 */
public class BindPhoneByCallPhoneRequest extends BaseRequestBean{
	private String language;
	private String signature;
	private String timestamp;
	private String phone;
	private String userId;
	private String gameCode;
	private String oldPhone;

	public BindPhoneByCallPhoneRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BindPhoneByCallPhoneRequest(String language, String signature,
			String timestamp, String phone, String userId, String gameCode,String oldPhone) {
		super();
		this.language = language;
		this.signature = signature;
		this.timestamp = timestamp;
		this.phone = phone;
		this.userId = userId;
		this.gameCode = gameCode;
		this.oldPhone = oldPhone;
	}

}
