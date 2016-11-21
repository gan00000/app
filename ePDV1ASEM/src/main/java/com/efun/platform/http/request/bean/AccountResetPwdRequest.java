package com.efun.platform.http.request.bean;

import android.content.Context;

import com.efun.platform.utils.Const;
/**
 * 修改密码
 * @author itxuxxey
 *
 */
public class AccountResetPwdRequest extends BaseRequestBean{
	private String timestamp;
	private String loginName;
	private String loginPwd;
	private String newPwd;
	private String language ;
	public AccountResetPwdRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AccountResetPwdRequest(Context context,String loginName,String loginPwd,String newPwd) {
		super();
		this.timestamp = System.currentTimeMillis()+"";
		this.loginName = loginName;
		this.loginPwd = loginPwd;
		this.newPwd = newPwd;
		this.language = Const.HttpParam.LANGUAGE;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getLoginPwd() {
		return loginPwd;
	}
	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}
	public String getNewPwd() {
		return newPwd;
	}
	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}
	
	
	
}
