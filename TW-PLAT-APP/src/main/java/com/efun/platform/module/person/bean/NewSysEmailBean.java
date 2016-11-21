package com.efun.platform.module.person.bean;

import com.efun.platform.module.BaseDataBean;
/**
 * 新信件
 * @author itxuxxey
 *
 */
public class NewSysEmailBean extends BaseDataBean{
	private static final long serialVersionUID = 1L;
	private String code;
	private String message;
	private int result;

	public NewSysEmailBean() {
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
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	
}
