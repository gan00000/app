package com.efun.platform.module.person.bean;

import com.efun.platform.module.BaseDataBean;
/**
 * 语音绑定手机
 * @author harvery
 *
 */
public class BindPhoneByCallPhoneBean extends BaseDataBean{
	private String code;
	private String message;
	private String call;

	public BindPhoneByCallPhoneBean() {
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

	public String getCall() {
		return call;
	}

	public void setCall(String call) {
		this.call = call;
	}

}
