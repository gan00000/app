package com.efun.platform.module.welfare.bean;

import java.util.ArrayList;

import com.efun.platform.module.BaseDataBean;
import com.efun.platform.module.game.bean.GameItemBean;
/**
 * 免费领取点数任务列表
 * @author itxuxxey
 *
 */
public class ExtensionBean extends BaseDataBean{
	
	private String code;
	private String activityCode;
	private String message;
	private String gameCode;
	private String context;
	private String startTime;//结束时间
	private String endTime;//开始时间
	
	private ArrayList<ActExtensionBean> mArrayOfExtension;
	
	public ExtensionBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getActivityCode() {
		return activityCode;
	}

	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getGameCode() {
		return gameCode;
	}

	public void setGameCode(String gameCode) {
		this.gameCode = gameCode;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public ArrayList<ActExtensionBean> getActExtensionBeans() {
		return mArrayOfExtension;
	}

	public void setActExtensionBeans(ArrayList<ActExtensionBean> mArrayOfExtension) {
		this.mArrayOfExtension = mArrayOfExtension;
	}
	
	
}
