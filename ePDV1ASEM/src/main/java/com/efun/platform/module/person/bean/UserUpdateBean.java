package com.efun.platform.module.person.bean;

import com.efun.platform.module.BaseDataBean;
import com.efun.platform.module.account.bean.User;

/**
 * 更新玩家信息
 * @author itxuxxey
 *
 */
public class UserUpdateBean extends BaseDataBean{
	private static final long serialVersionUID = 1L;
	private String code;
	private String message;
	private User user;
	
	public UserUpdateBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UserUpdateBean(String code, String message) {
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
	public String getMsg() {
		return message;
	}
	public void setMsg(String msg) {
		this.message = msg;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
}
