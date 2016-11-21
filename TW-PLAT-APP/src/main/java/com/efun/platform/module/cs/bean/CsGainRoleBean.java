package com.efun.platform.module.cs.bean;

import java.util.ArrayList;

import com.efun.platform.module.BaseDataBean;
/**
 * 提问页面-获取角色信息
 * @author Jesse
 *
 */
public class CsGainRoleBean extends BaseDataBean {
	private String code;
	private String message;
	private ArrayList<CsGainRoleItemBean> roleList;
	
	public CsGainRoleBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public CsGainRoleBean(String code, String message,
			ArrayList<CsGainRoleItemBean> roleList) {
		super();
		this.code = code;
		this.message = message;
		this.roleList = roleList;
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
	public ArrayList<CsGainRoleItemBean> getRoleList() {
		return roleList;
	}
	public void setRoleList(ArrayList<CsGainRoleItemBean> roleList) {
		this.roleList = roleList;
	}

}
