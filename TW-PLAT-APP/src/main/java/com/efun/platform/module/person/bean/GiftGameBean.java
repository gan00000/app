package com.efun.platform.module.person.bean;

import com.efun.platform.module.BaseDataBean;
/**
 * 可以获取奖励的游戏信息
 * @author harvery
 *
 */
public class GiftGameBean extends BaseDataBean{
	private String gameCode;
	private String gameName;
	private String isGame;
	
	public GiftGameBean() {
		super();
		// TODO Auto-generated constructor stub
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

	public String getIsGame() {
		return isGame;
	}

	public void setIsGame(String isGame) {
		this.isGame = isGame;
	}
	
}
