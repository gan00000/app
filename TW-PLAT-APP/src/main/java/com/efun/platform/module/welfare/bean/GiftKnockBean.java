package com.efun.platform.module.welfare.bean;

import com.efun.platform.module.BaseDataBean;
/**
 * 抢礼包
 * @author Jesse
 *
 */
public class GiftKnockBean extends BaseDataBean{
	private String code;
	private String message;
	private String serial;
	public GiftKnockBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public GiftKnockBean(String code,String message, String serial) {
		super();
		this.code = code;
		this.message = message;
		this.serial = serial;
	}

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getSerial() {
		return serial;
	}
	public void setSerial(String serial) {
		this.serial = serial;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
