package com.efun.platform.http.request.bean;

/**
 * 游戏-游戏详情
 * @author Jesse
 *
 */
public class GameDetailRequest extends BaseRequestBean{
	private String gameCode;
	private String newsEtag;
	private String picEtag;
	private String platform;
	private String version;
	public GameDetailRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	public GameDetailRequest(String gameCode, String newsEtag, String picEtag,
			String platform, String version) {
		super();
		this.gameCode = gameCode;
		this.newsEtag = newsEtag;
		this.picEtag = picEtag;
		this.platform = platform;
		this.version = version;
	}

	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getGameCode() {
		return gameCode;
	}
	public void setGameCode(String gameCode) {
		this.gameCode = gameCode;
	}
	public String getNewsEtag() {
		return newsEtag;
	}
	public void setNewsEtag(String newsEtag) {
		this.newsEtag = newsEtag;
	}
	public String getPicEtag() {
		return picEtag;
	}
	public void setPicEtag(String picEtag) {
		this.picEtag = picEtag;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	
	
}
