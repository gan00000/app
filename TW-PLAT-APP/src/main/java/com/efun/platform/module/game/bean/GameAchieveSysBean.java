package com.efun.platform.module.game.bean;

import java.util.ArrayList;

import com.efun.platform.module.BaseDataBean;
import com.efun.platform.utils.Const.Web;
/**
 * 成就系統
 * @author itxuxxey
 *
 */
public class GameAchieveSysBean extends BaseDataBean{
	private String code;
	private String message;
	private String extendedObj;//標題內容
	private ArrayList<AchieveSysItemBean> achieveSysBeans;
	public GameAchieveSysBean() {
		super();
		// TODO Auto-generated constructor stub
	}	
	
	public GameAchieveSysBean(int beanType) {
		super(beanType);
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

	public String getExtendedObj() {
		return extendedObj;
	}

	public void setExtendedObj(String extendedObj) {
		this.extendedObj = extendedObj;
	}

	public ArrayList<AchieveSysItemBean> getAchieveSysBeans() {
		return achieveSysBeans;
	}

	public void setAchieveSysBeans(ArrayList<AchieveSysItemBean> achieveSysBeans) {
		this.achieveSysBeans = achieveSysBeans;
	}
	
}
