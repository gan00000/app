package com.efun.platform.module.game.bean;

import com.efun.platform.module.BaseDataBean;
/**
 * 点赞游戏
 * @author Jesse
 *
 */
public class GamePraiseBean extends BaseDataBean{
	private boolean flag;
	private String message;
	private String code;
	public GamePraiseBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public GamePraiseBean(boolean flag, String message, String code) {
		super();
		this.flag = flag;
		this.message = message;
		this.code = code;
	}

	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
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
