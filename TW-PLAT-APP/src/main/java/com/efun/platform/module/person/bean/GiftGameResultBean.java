package com.efun.platform.module.person.bean;

import java.util.ArrayList;

import com.efun.platform.module.BaseDataBean;
/**
 * 可以获取奖励的游戏信息结果
 * @author harvery
 *
 */
public class GiftGameResultBean extends BaseDataBean{
	private String code;
	private String message;
	private ArrayList<GiftGameBean> mGiftGames;
	
	public GiftGameResultBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ArrayList<GiftGameBean> getmGiftGames() {
		return mGiftGames;
	}

	public void setmGiftGames(ArrayList<GiftGameBean> mGiftGames) {
		this.mGiftGames = mGiftGames;
	}
	
}
