package com.efun.platform.http.request.bean;
/**
 * 按钮控制开关
 * @author itxuxxey
 *
 */
public class RechargeControlRequest extends BaseRequestBean{
	private String gameCode;//游戏gameCode
	private String flag;//按钮标识
	private String versionCode;//游戏版本名
	private String payFrom;//渠道标识
	private String roleLevel;//角色等级
	private String netFlag;//网络标识
	private String userId;//用户ID
	private String platform;//平台标识tw
	private String packageVersion;//平台版本号
	private String language;//平台语言
	
	public RechargeControlRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public RechargeControlRequest(String gameCode, String flag,
			String versionCode, String payFrom, String roleLevel,
			String netFlag, String userId, String platform,
			String packageVersion, String language) {
		super();
		this.gameCode = gameCode;
		this.flag = flag;
		this.versionCode = versionCode;
		this.payFrom = payFrom;
		this.roleLevel = roleLevel;
		this.netFlag = netFlag;
		this.userId = userId;
		this.platform = platform;
		this.packageVersion = packageVersion;
		this.language = language;
	}

	public String getGameCode() {
		return gameCode;
	}

	public void setGameCode(String gameCode) {
		this.gameCode = gameCode;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}

	public String getPayFrom() {
		return payFrom;
	}

	public void setPayFrom(String payFrom) {
		this.payFrom = payFrom;
	}

	public String getRoleLevel() {
		return roleLevel;
	}

	public void setRoleLevel(String roleLevel) {
		this.roleLevel = roleLevel;
	}

	public String getNetFlag() {
		return netFlag;
	}

	public void setNetFlag(String netFlag) {
		this.netFlag = netFlag;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getPackageVersion() {
		return packageVersion;
	}

	public void setPackageVersion(String packageVersion) {
		this.packageVersion = packageVersion;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}
	
	
}
