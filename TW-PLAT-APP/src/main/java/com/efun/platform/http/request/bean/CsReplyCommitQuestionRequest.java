package com.efun.platform.http.request.bean;

/**
 * 客服 - 客服回复 - 提交问题
 * @author Jesse
 *
 */
public class CsReplyCommitQuestionRequest extends BaseRequestBean {
	private String tgppid;
	private String replyName;
	private String replyContent;
	private String isPlayer;
	private String hasRead;
	private String fromType;
	private String uid;
	private String sign;
	private String timestamp;

	public CsReplyCommitQuestionRequest(String tgppid, String replyName, String replyContent, String isPlayer, String hasRead, String fromType) {
		super();
		this.tgppid = tgppid;
		this.replyName = replyName;
		this.replyContent = replyContent;
		this.isPlayer = isPlayer;
		this.hasRead = hasRead;
		this.fromType = fromType;
	}

	public String getTgppid() {
		return tgppid;
	}

	public void setTgppid(String tgppid) {
		this.tgppid = tgppid;
	}

	public String getReplyName() {
		return replyName;
	}

	public void setReplyName(String replyName) {
		this.replyName = replyName;
	}

	public String getReplyContent() {
		return replyContent;
	}

	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}

	public String getIsPlayer() {
		return isPlayer;
	}

	public void setIsPlayer(String isPlayer) {
		this.isPlayer = isPlayer;
	}

	public String getHasRead() {
		return hasRead;
	}

	public void setHasRead(String hasRead) {
		this.hasRead = hasRead;
	}

	public String getFromType() {
		return fromType;
	}

	public void setFromType(String fromType) {
		this.fromType = fromType;
	}
	
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
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
}
