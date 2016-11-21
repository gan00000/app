package com.efun.platform.http.request.bean;

import android.content.Context;

import com.efun.core.tools.EfunStringUtil;
import com.efun.platform.IPlatApplication;
import com.efun.platform.utils.Const;
/**
 * 通过手机找回密码
 * @author itxuxxey
 *
 */
public class AccountFindPwdByPhoneRequest extends BaseRequestBean{
	private String loginName;
	private String phone;
	private String platform;//平台标识 tw
	private String fromType;//app
	private String version;//android
	private String packageVersion;
	private String language;
	
	public AccountFindPwdByPhoneRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AccountFindPwdByPhoneRequest(String loginName, String phone,
			String platform, String fromType, String version,
			String packageVersion, String language) {
		super();
		this.loginName = loginName;
		this.phone = phone;
		this.platform = platform;
		this.fromType = fromType;
		this.version = version;
		this.packageVersion = packageVersion;
		this.language = language;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getFromType() {
		return fromType;
	}

	public void setFromType(String fromType) {
		this.fromType = fromType;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
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
