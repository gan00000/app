package com.efun.platform.module.game.bean;

import com.efun.platform.module.BaseDataBean;
import com.efun.platform.utils.Const.Web;
/**
 * 成就系統item
 * @author itxuxxey
 *
 */
public class AchieveSysItemBean extends BaseDataBean{
	private String gameName;
	private String gameIcon;
    private String taskUrl;
	public AchieveSysItemBean() {
		super();
		// TODO Auto-generated constructor stub
	}	
	
	public AchieveSysItemBean(int beanType) {
		super(beanType);
	}

	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	public String getGameIcon() {
		return gameIcon;
	}

	public void setGameIcon(String gameIcon) {
		this.gameIcon = gameIcon;
	}

	public String getTaskUrl() {
		return taskUrl;
	}

	public void setTaskUrl(String taskUrl) {
		this.taskUrl = taskUrl;
	}
	
}
