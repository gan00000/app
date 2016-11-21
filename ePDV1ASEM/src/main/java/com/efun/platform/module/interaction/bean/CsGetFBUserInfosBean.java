package com.efun.platform.module.interaction.bean;

import java.util.ArrayList;

import com.efun.platform.module.BaseDataBean;
/**
 * 获取FB信息列表
 * @author itxuxxey
 *
 */
public class CsGetFBUserInfosBean extends BaseDataBean {
	private String code;
	private String message;
	private String relation;
	
	public CsGetFBUserInfosBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public CsGetFBUserInfosBean(String code, String message,
			String relation) {
		super();
		this.code = code;
		this.message = message;
		this.relation = relation;
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

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}
	
}
