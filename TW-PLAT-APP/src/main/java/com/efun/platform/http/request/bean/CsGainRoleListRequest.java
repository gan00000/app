package com.efun.platform.http.request.bean;

/**
 * 客服- 提问-选角色
 * @author Jesse
 *
 */
public class CsGainRoleListRequest extends BaseRequestBean {
	private String gameCode;
	private String serverCode;
	private String uid;
	private String sign;
	private String timestamp;
	public CsGainRoleListRequest(String gameCode, String serverCode) {
		super();
		this.gameCode = gameCode;
		this.serverCode = serverCode;
	}
	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getGameCode() {
		return gameCode;
	}

	public void setGameCode(String gameCode) {
		this.gameCode = gameCode;
	}

	public String getServerCode() {
		return serverCode;
	}

	public void setServerCode(String serverCode) {
		this.serverCode = serverCode;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

}
