package com.efun.platform.module.person.bean;

import com.efun.platform.module.BaseDataBean;
/**
 * 检查手机绑定状态
 * @author harvery
 *
 */
public class CheckPhoneStatusBean extends BaseDataBean{
	
	private String code;
	private String message;
	private String md5Phone;
	private String phone;
	private boolean isFinished;
	private boolean isGetAward;
	
	public CheckPhoneStatusBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMd5Phone() {
		return md5Phone;
	}

	public void setMd5Phone(String md5Phone) {
		this.md5Phone = md5Phone;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public boolean isFinished() {
		return isFinished;
	}

	public void setFinished(boolean isFinished) {
		this.isFinished = isFinished;
	}

	public boolean isGetAward() {
		return isGetAward;
	}

	public void setGetAward(boolean isGetAward) {
		this.isGetAward = isGetAward;
	}
	
	
	
}
