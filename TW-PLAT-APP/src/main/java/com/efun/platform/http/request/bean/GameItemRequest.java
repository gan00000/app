package com.efun.platform.http.request.bean;

public class GameItemRequest extends BaseRequestBean{
	private String platform;
	private String gameCode;
	private String fromType;
	public GameItemRequest(String platform, String gameCode, String fromType) {
		super();
		this.platform = platform;
		this.gameCode = gameCode;
		this.fromType = fromType;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public String getGameCode() {
		return gameCode;
	}
	public void setGameCode(String gameCode) {
		this.gameCode = gameCode;
	}
	public String getFromType() {
		return fromType;
	}
	public void setFromType(String fromType) {
		this.fromType = fromType;
	}
	
}
