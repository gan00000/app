package com.efun.platform.module.game.bean;

import com.efun.platform.module.BaseDataBean;
/**
 * 游戏一条评论
 * @author Jesse
 *
 */
public class GameCommendItemBean extends BaseDataBean{
	private String userName;
	private String content;
	private long modifiedTime;
	private String icon;
	public GameCommendItemBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public long getModifiedTime() {
		return modifiedTime;
	}
	public void setModifiedTime(long modifiedTime) {
		this.modifiedTime = modifiedTime;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	
}
