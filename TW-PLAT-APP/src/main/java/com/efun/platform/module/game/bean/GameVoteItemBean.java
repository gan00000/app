package com.efun.platform.module.game.bean;

import com.efun.platform.module.BaseDataBean;
/**
 * 游戏一条评论
 * @author harvery
 *
 */
public class GameVoteItemBean extends BaseDataBean{
	private long createdTime;//时间
	private int star;//星级
	private String review;//内容
	private String nickname;//昵称
	private String icon;//头像
	public GameVoteItemBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public long getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(long createdTime) {
		this.createdTime = createdTime;
	}
	public int getStar() {
		return star;
	}
	public void setStar(int star) {
		this.star = star;
	}
	public String getReview() {
		return review;
	}
	public void setReview(String review) {
		this.review = review;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	
}
