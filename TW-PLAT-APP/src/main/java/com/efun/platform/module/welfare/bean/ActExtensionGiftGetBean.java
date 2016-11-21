package com.efun.platform.module.welfare.bean;

import com.efun.platform.module.BaseDataBean;
/**
 * 领取礼包点数
 * @author Jesse
 *
 */
public class ActExtensionGiftGetBean extends BaseDataBean {
	private String code;
	private String message;

	public ActExtensionGiftGetBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ActExtensionGiftGetBean(String code, String message) {
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
	
	
}
