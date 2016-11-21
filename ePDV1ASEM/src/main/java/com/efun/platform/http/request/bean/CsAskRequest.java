package com.efun.platform.http.request.bean;

/**
 * 客服- 提问 - 提问
 * @author itxuxxey
 *
 */
public class CsAskRequest extends BaseRequestBean {

	private String questionType; // 问题类型
	private String platform; // 平台
	private String gameCode; //
	private String title; //
	private String content; //
	private String serverCode; // 服务器Code
	private String isMobile; // 是否移动端
	private String contactWay; // 联系方式
	private String email; // 邮箱
	private String fromType; // 填app
	private String roleId; // 角色ID
	private String uid; // 账号ID
	private String gameUid;//fb对应有游戏UID
	private String sign;
	private String timestamp;
	public CsAskRequest() {
		super();
	}

	public CsAskRequest(String questionType, String platform, String gameCode, String title, String content, String fromType) {
		super();
		this.questionType = questionType;
		this.platform = platform;
		this.gameCode = gameCode;
		this.title = title;
		this.content = content;
		this.fromType = fromType;
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
	public String getQuestionType() {
		return questionType;
	}

	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getGameCode() {
		return gameCode;
	}

	public void setGameCode(String gameCode) {
		this.gameCode = gameCode;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getServerCode() {
		return serverCode;
	}

	public void setServerCode(String serverCode) {
		this.serverCode = serverCode;
	}

	public String getIsMobile() {
		return isMobile;
	}

	public void setIsMobile(String isMobile) {
		this.isMobile = isMobile;
	}

	public String getContactWay() {
		return contactWay;
	}

	public void setContactWay(String contactWay) {
		this.contactWay = contactWay;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFromType() {
		return fromType;
	}

	public void setFromType(String fromType) {
		this.fromType = fromType;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getGameUid() {
		return gameUid;
	}

	public void setGameUid(String gameUid) {
		this.gameUid = gameUid;
	}

}
