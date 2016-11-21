package com.efun.platform.module.cs.bean;

import com.efun.platform.module.BaseDataBean;
/**
 * 提问页面-获取游戏服务器信息
 * @author Jesse
 *
 */
public class CsGainServerItemBean extends BaseDataBean {
	private String GameDomain;
	private String ServerCode;
	private String ServerName;
	private String gameCode;
	private String info;
	private int isCom;
	private String serverPrefix;
	private int status;
	public CsGainServerItemBean(String gameDomain, String serverCode,
			String serverName, String gameCode, String info, int isCom,
			String serverPrefix, int status) {
		super();
		GameDomain = gameDomain;
		ServerCode = serverCode;
		ServerName = serverName;
		this.gameCode = gameCode;
		this.info = info;
		this.isCom = isCom;
		this.serverPrefix = serverPrefix;
		this.status = status;
	}
	public CsGainServerItemBean() {
		super();
	}
	public String getGameDomain() {
		return GameDomain;
	}
	public void setGameDomain(String gameDomain) {
		GameDomain = gameDomain;
	}
	public String getServerCode() {
		return ServerCode;
	}
	public void setServerCode(String serverCode) {
		ServerCode = serverCode;
	}
	public String getServerName() {
		return ServerName;
	}
	public void setServerName(String serverName) {
		ServerName = serverName;
	}
	public String getGameCode() {
		return gameCode;
	}
	public void setGameCode(String gameCode) {
		this.gameCode = gameCode;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public int getIsCom() {
		return isCom;
	}
	public void setIsCom(int isCom) {
		this.isCom = isCom;
	}
	public String getServerPrefix() {
		return serverPrefix;
	}
	public void setServerPrefix(String serverPrefix) {
		this.serverPrefix = serverPrefix;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
}
