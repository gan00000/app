package com.efun.platform.module.cs.bean;

import java.util.ArrayList;

import com.efun.platform.module.BaseDataBean;
/**
 * 获取FB角色信息
 * @author itxuxxey
 *
 */
public class CsGainFBRoleItemBean extends BaseDataBean {
	private CsGainRoleItemBean bean;
	private String gameFBUid;
	public CsGainFBRoleItemBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CsGainFBRoleItemBean(CsGainRoleItemBean bean, String gameFBUid) {
		super();
		this.bean = bean;
		this.gameFBUid = gameFBUid;
	}
	public CsGainRoleItemBean getBean() {
		return bean;
	}
	public void setBean(CsGainRoleItemBean bean) {
		this.bean = bean;
	}
	public String getGameFBUid() {
		return gameFBUid;
	}
	public void setGameFBUid(String gameFBUid) {
		this.gameFBUid = gameFBUid;
	}
	
}
