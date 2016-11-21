package com.efun.platform.http.request.bean;


/**
 * 游戏-游戏评论列表
 * @author Jesse
 *
 */
public class GameCommendListRequest extends BaseRequestBean{
	private String gameCode;
	
	public GameCommendListRequest(String gameCode) {
		super();
		this.gameCode = gameCode;
	}
	public String getGameCode() {
		return gameCode;
	}
	public void setGameCode(String gameCode) {
		this.gameCode = gameCode;
	}
}
