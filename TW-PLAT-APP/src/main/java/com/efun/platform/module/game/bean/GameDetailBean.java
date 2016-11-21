package com.efun.platform.module.game.bean;

import com.efun.platform.module.BaseDataBean;
/**
 * 游戏详情
 * @author Jesse
 *
 */
public class GameDetailBean extends BaseDataBean{
	private String gameCode;
	private String newsEtag;
	private String picEtag;
	private String description;
	private String[] biggroup;
	private String[] smallgroup;
	private String videoUrl;
	private String newVersionDesc;
	
	public GameDetailBean() {
		super();
		// TODO Auto-generated constructor stub
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String[] getBiggroup() {
		return biggroup;
	}
	public void setBiggroup(String[] biggroup) {
		this.biggroup = biggroup;
	}
	public String[] getSmallgroup() {
		return smallgroup;
	}
	public void setSmallgroup(String[] smallgroup) {
		this.smallgroup = smallgroup;
	}
	public String getVideoUrl() {
		return videoUrl;
	}
	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}
	public String getNewVersionDesc() {
		return newVersionDesc;
	}
	public void setNewVersionDesc(String newVersionDesc) {
		this.newVersionDesc = newVersionDesc;
	}
	
}
