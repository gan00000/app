package com.efun.platform.http.request.bean;
/**
 * 客服- 提问-选服务器
 * @author Jesse
 *
 */
public class CsGainServerListRequest extends BaseRequestBean {
	private String gameCode;
	private String dept;//31：表示港台，33表示亞歐
	private String type;
	
	public CsGainServerListRequest() {
		super();
	}

	public CsGainServerListRequest(String gameCode, String dept, String type) {
		super();
		this.gameCode = gameCode;
		this.dept = dept;
		this.type = type;
	}

	public String getGameCode() {
		return gameCode;
	}

	public void setGameCode(String gameCode) {
		this.gameCode = gameCode;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
}
