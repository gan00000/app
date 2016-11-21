package com.efun.platform.http.request.bean;

import com.efun.platform.utils.Const.HttpParam;

/**
 *	签到
 *
 */
public class PersonSignRequest extends BaseRequestBean {
	private String uid;
	private String awardType;
	private String language;
	private String platform;
	private String sign;
	private String timestamp;
	private String version;
	private String packageVersion;
	
	
	public PersonSignRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PersonSignRequest(String uid, String awardType, String language,
			String platform, String sign,String timestamp, String version, 
			String packageVersion) {
		super();
		this.uid = uid;
		this.awardType = awardType;
		this.language = language;
		this.platform = platform;
		this.sign = sign;
		this.timestamp = timestamp;
		this.version = version;
		this.packageVersion = packageVersion;
	}
	
	
}
