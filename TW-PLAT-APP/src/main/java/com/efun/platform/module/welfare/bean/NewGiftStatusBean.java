package com.efun.platform.module.welfare.bean;

import com.efun.platform.module.BaseDataBean;

/**
 * 新礼包 状态
 * @author itxuxxey
 *
 */
public class NewGiftStatusBean extends BaseDataBean {
	private String code;
	private String message;
	private long result;
	public NewGiftStatusBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public NewGiftStatusBean(String code, String message, long result) {
		super();
		this.code = code;
		this.message = message;
		this.result = result;
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

	public long getResult() {
		return result;
	}

	public void setResult(long result) {
		this.result = result;
	}

}
