package com.efun.platform.module.account.bean;

import com.efun.platform.module.BaseDataBean;
/**
 * 綁定手機用到的參數
 * @author itxuxxey
 *
 */
public class BindParamsBean extends BaseDataBean{
	
	private String key;
	private String pattern;
	private String phoneNum;
	private String callNum;
	
	public BindParamsBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getPattern() {
		return pattern;
	}
	public void setPattern(String pattern) {
		this.pattern = pattern;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public String getCallNum() {
		return callNum;
	}
	public void setCallNum(String callNum) {
		this.callNum = callNum;
	}
	
	
}
