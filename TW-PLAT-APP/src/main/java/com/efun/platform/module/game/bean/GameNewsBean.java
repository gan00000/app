package com.efun.platform.module.game.bean;

import com.efun.platform.module.BaseDataBean;
import com.efun.platform.utils.Const.Web;
/**
 * 游戏资讯
 * @author Jesse
 *
 */
public class GameNewsBean extends BaseDataBean{
	private String title;
	private long crtime;
	private String htmlpathurl;
	private int type;
	private String gameCode;
	private String iphoneUrl;
	public GameNewsBean() {
		super();
		// TODO Auto-generated constructor stub
	}	
	
	public GameNewsBean(int beanType) {
		super(beanType);
	}

	public GameNewsBean(String title, long crtime, String htmlpathurl,
			int type, String gameCode, String iphoneUrl) {
		super();
		this.title = title;
		this.crtime = crtime;
		this.htmlpathurl = htmlpathurl;
		this.type = type;
		this.gameCode = gameCode;
		this.iphoneUrl = iphoneUrl;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public long getCrtime() {
		return crtime;
	}
	public void setCrtime(long crtime) {
		this.crtime = crtime;
	}
	public String getHtmlpathurl() {
		return Web.WEB_PERFIX+htmlpathurl;
	}
	public void setHtmlpathurl(String htmlpathurl) {
		this.htmlpathurl = htmlpathurl;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}

	public String getGameCode() {
		return gameCode;
	}

	public void setGameCode(String gameCode) {
		this.gameCode = gameCode;
	}

	public String getIphoneUrl() {
		return iphoneUrl;
	}

	public void setIphoneUrl(String iphoneUrl) {
		this.iphoneUrl = iphoneUrl;
	}


	
}
