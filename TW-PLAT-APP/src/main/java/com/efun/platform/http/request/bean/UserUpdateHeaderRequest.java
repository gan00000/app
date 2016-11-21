package com.efun.platform.http.request.bean;

import com.efun.platform.utils.Const.HttpParam;

/**
 * 玩家 - 上传头像
 * @author Jesse
 *
 */
public class UserUpdateHeaderRequest extends BaseRequestBean{
	private String signature;
	private String timestamp;
	private String userid;
	private String gameCode;
	private String imgName;
	private String suffix;
	private String group;
	private String packageVersion;
	private String language;
	private String imgStr;
	
//	private String uid;
//	private String encodeFile;
//	private String fileName;
//	private String platform;
//	private String sign;
//	private String timestamp;
	public UserUpdateHeaderRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserUpdateHeaderRequest(String gameCode, String imgName, String suffix,
			String group, String packageVersion, String language, String imgStr) {
		super();
		this.gameCode = gameCode;
		this.imgName = imgName;
		this.suffix = suffix;
		this.group = group;
		this.packageVersion = packageVersion;
		this.language = language;
		this.imgStr = imgStr;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getGameCode() {
		return gameCode;
	}

	public void setGameCode(String gameCode) {
		this.gameCode = gameCode;
	}

	public String getImgName() {
		return imgName;
	}

	public void setImgName(String imgName) {
		this.imgName = imgName;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
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

	public String getImgStr() {
		return imgStr;
	}

	public void setImgStr(String imgStr) {
		this.imgStr = imgStr;
	}
	
}
