package com.efun.platform.http.request.bean;

/**
 * 游戏 - 点赞
 * @author Jesse
 *
 */
public class GamePraiseRequest extends BaseRequestBean{
	private String uid;
	private String gameCode;
	private String sign;
	private String timestamp;
	public GamePraiseRequest(String gameCode) {
		super();
		this.gameCode = gameCode;
	}
	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getGameCode() {
		return gameCode;
	}
	public void setGameCode(String gameCode) {
		this.gameCode = gameCode;
	}
	
}
