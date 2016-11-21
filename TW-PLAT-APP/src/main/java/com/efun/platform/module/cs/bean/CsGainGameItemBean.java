package com.efun.platform.module.cs.bean;

import com.efun.platform.module.BaseDataBean;
/**
 * 提问页面-获取游戏信息
 * @author Jesse
 *
 */
public class CsGainGameItemBean extends BaseDataBean {
	private String gameCode;
	private String gameName;
	
	public CsGainGameItemBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CsGainGameItemBean(String gameCode, String gameName) {
		super();
		this.gameCode = gameCode;
		this.gameName = gameName;
	}
	public String getGameCode() {
		return gameCode;
	}
	public void setGameCode(String gameCode) {
		this.gameCode = gameCode;
	}
	public String getGameName() {
		return gameName;
	}
	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	
}
