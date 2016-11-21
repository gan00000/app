package com.efun.platform.module.cs.bean;

import java.util.ArrayList;

import com.efun.platform.module.BaseDataBean;
/**
 * 提问页面-获取游戏服务器信息
 * @author Jesse
 *
 */
public class CsGainServerBean extends BaseDataBean {
	private String code;
	private String message;
	private ArrayList<CsGainServerItemBean> serverList;
	
	public CsGainServerBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CsGainServerBean(String code, String message,
			ArrayList<CsGainServerItemBean> serverList) {
		super();
		this.code = code;
		this.message = message;
		this.serverList = serverList;
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

	public ArrayList<CsGainServerItemBean> getServerList() {
		return serverList;
	}

	public void setServerList(ArrayList<CsGainServerItemBean> serverList) {
		this.serverList = serverList;
	}

	
}
