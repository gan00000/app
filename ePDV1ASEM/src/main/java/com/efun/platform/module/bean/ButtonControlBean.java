package com.efun.platform.module.bean;

import java.io.Serializable;

public class ButtonControlBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private String code;
	private String message;

	public ButtonControlBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ButtonControlBean(String code, String message) {
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
