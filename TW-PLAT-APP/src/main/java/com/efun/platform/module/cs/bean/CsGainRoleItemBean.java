package com.efun.platform.module.cs.bean;

import java.util.ArrayList;

import com.efun.platform.module.BaseDataBean;
/**
 * 提问页面-获取角色信息
 * @author Jesse
 *
 */
public class CsGainRoleItemBean extends BaseDataBean {
	private int level;
	private String name;
	private String roleid;
	private String subgame;
	
	public CsGainRoleItemBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CsGainRoleItemBean(int level, String name, String roleid,
			String subgame) {
		super();
		this.level = level;
		this.name = name;
		this.roleid = roleid;
		this.subgame = subgame;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRoleid() {
		return roleid;
	}
	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}
	public String getSubgame() {
		return subgame;
	}
	public void setSubgame(String subgame) {
		this.subgame = subgame;
	}

	
}
