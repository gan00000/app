package com.efun.platform.module.account.bean;

import com.efun.platform.module.BaseDataBean;
/**
 * 账号处理结果
 * @author itxuxxey
 *
 */
public class ResultBean extends BaseDataBean{
	/**
	 * Code - 状态值
	 */
	private String code;
	/**
	 * Message - 返回消息
	 */
	private String message;
	public ResultBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ResultBean(String code, String message) {
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
