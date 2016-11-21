package com.efun.platform.module.game.bean;

import com.efun.platform.module.BaseDataBean;
/**
 * 提交游戏评论
 * @author Jesse
 *
 */
public class GameCommendBean extends BaseDataBean{
	private String message;
	private String code;
	public GameCommendBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public GameCommendBean(String message, String code) {
		super();
		this.message = message;
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
}
