package com.efun.platform.module.cs.bean;

import com.efun.platform.module.BaseDataBean;
/**
 * 回復狀態
 * @author Jesse
 *
 */
public class CsReplyStatusBean extends BaseDataBean{
	private String code;

	public CsReplyStatusBean() {
		super();
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
}
