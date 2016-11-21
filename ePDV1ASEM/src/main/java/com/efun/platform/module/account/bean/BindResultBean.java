package com.efun.platform.module.account.bean;

import com.efun.platform.module.BaseDataBean;
/**
 * 綁定处理结果
 * @author itxuxxey
 *
 */
public class BindResultBean extends BaseDataBean{
	/**
	 * Code - 状态值
	 */
	private String code;
	/**
	 * Message - 返回消息
	 */
	private String message;
	
	private String email;
	
	private String phone;
	
	private String md5Phone;
	
	private boolean isFinished;
	private boolean isGetAward;
	
	public BindResultBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BindResultBean(String code, String message) {
		super();
		this.code = code;
		this.message = message;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMd5Phone() {
		return md5Phone;
	}

	public void setMd5Phone(String md5Phone) {
		this.md5Phone = md5Phone;
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
