package com.efun.platform.http.request.bean;

/**
 * 更新玩家头像
 * @author itxuxxey
 *
 */
public class PersonRefreshHeadIconRequest extends BaseRequestBean{
	private String uid;
	private String platform;
	private String sign;
	private String timestamp;
	private String icon;
	private String fromType;
	private String language;
	private String version;
	private String packageVersion;

	public PersonRefreshHeadIconRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PersonRefreshHeadIconRequest(String uid, String platform,
			String sign, String timestamp, String icon, String fromType,
			String language, String version, String packageVersion) {
		super();
		this.uid = uid;
		this.platform = platform;
		this.sign = sign;
		this.timestamp = timestamp;
		this.icon = icon;
		this.fromType = fromType;
		this.language = language;
		this.version = version;
		this.packageVersion = packageVersion;
	}
	
	
}
