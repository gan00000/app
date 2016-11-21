package com.efun.platform.module.welfare.bean;

import com.efun.platform.module.BaseDataBean;
/**
 * 推荐游戏下载许可
 * @author Jesse
 *
 */
public class ActExtensionDownloadBean extends BaseDataBean{
	private String code;
	private String message;
	private String params;
	public ActExtensionDownloadBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ActExtensionDownloadBean(String code, String message, String params) {
		super();
		this.code = code;
		this.message = message;
		this.params = params;
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
	public String getParams() {
		return params;
	}
	public void setParams(String params) {
		this.params = params;
	}
	
	
}
