package com.efun.platform.http.request.bean;

/**
 * 游戏 - 成就系統
 * @author itxuxxey
 *
 */
public class GameAchieveSysListRequest extends BaseRequestBean{
	private String uid;
	private String platform;
	private String sign;
	private String timestamp;
	private String version;
	private String packageVersion;
	private String fromType;
	private String language;
	
	public GameAchieveSysListRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GameAchieveSysListRequest(String uid, String platform, String sign,
			String timestamp, String version, String packageVersion,
			String fromType, String language) {
		super();
		this.uid = uid;
		this.platform = platform;
		this.sign = sign;
		this.timestamp = timestamp;
		this.version = version;
		this.packageVersion = packageVersion;
		this.fromType = fromType;
		this.language = language;
	}
	
}
