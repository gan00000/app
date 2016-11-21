package com.efun.platform.module.summary.bean;

import com.efun.platform.module.BaseDataBean;

public class SummaryPraiseBean extends BaseDataBean {
	private String code;
	private String message;
	public SummaryPraiseBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public SummaryPraiseBean(int beanType) {
		super(beanType);
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
	
}
