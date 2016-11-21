package com.efun.platform.http.request.bean;

import android.content.Context;

import com.efun.core.tools.EfunStringUtil;
import com.efun.platform.IPlatApplication;
import com.efun.platform.utils.Const;
/**
 * 找回密码
 * @author Jesse
 *
 */
public class AccountFindPwdRequest extends BaseRequestBean{
	private String area;
	private String device;
	private String timestamp;
	private String loginName;
	private String email;
	private String language ;
	public AccountFindPwdRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AccountFindPwdRequest(Context context,String loginName, String email) {
		super();
		this.timestamp = System.currentTimeMillis()+"";
		this.loginName = loginName;
		this.email = email;
		this.language = Const.HttpParam.LANGUAGE;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getDevice() {
		return device;
	}
	public void setDevice(String device) {
		this.device = device;
	}
	
}
