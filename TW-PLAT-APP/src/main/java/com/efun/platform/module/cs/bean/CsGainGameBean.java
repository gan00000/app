package com.efun.platform.module.cs.bean;

import java.util.ArrayList;

import com.efun.platform.module.BaseDataBean;
/**
 * 提问页面-获取游戏信息
 * @author Jesse
 *
 */
public class CsGainGameBean extends BaseDataBean {
	private String code;
	private String message;
	private ArrayList<CsGainGameItemBean> gameList;

	public CsGainGameBean() {
		super();
	}

	public CsGainGameBean(String code, String message,
			ArrayList<CsGainGameItemBean> gameList) {
		super();
		this.code = code;
		this.message = message;
		this.gameList = gameList;
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

	public ArrayList<CsGainGameItemBean> getGameList() {
		return gameList;
	}

	public void setGameList(ArrayList<CsGainGameItemBean> gameList) {
		this.gameList = gameList;
	}
	
}
