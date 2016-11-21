package com.efun.platform.http.request.bean;

/**
 * 游戏-添加评论
 * @author Jesse
 *
 */
public class GameCommendRequest extends BaseRequestBean{
	private String userName;
	private String content;
	private String gameCode;
	private String type;
	private String pid;
	private String uid;
	private String sign;
	private String timestamp;
	public GameCommendRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	public GameCommendRequest(String userName, String content, String gameCode,
			String type, String pid) {
		super();
		this.userName = userName;
		this.content = content;
		this.gameCode = gameCode;
		this.type = type;
		this.pid = pid;
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
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getGameCode() {
		return gameCode;
	}
	public void setGameCode(String gameCode) {
		this.gameCode = gameCode;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	
	
}
