package com.efun.platform.module.cs.bean;

import com.efun.platform.module.BaseDataBean;
/**
 * 回复问题
 * @author Jesse
 *
 */
public class CsReplyQuestionBean extends BaseDataBean {
	private String tgppid;
	private String questionTitle;
	private String theQuestions;
	private String createTime;
	private String gameName;
	private String replyStatus;
	private String hasNew;
	private String score;
	private String lastModifiedTime;

	public CsReplyQuestionBean() {
		super();
	}

	public CsReplyQuestionBean(String tgppid, String questionTitle, String theQuestions, String createTime, String gameName, String replyStatus, String hasNew, String score, String lastModifiedTime) {
		super();
		this.tgppid = tgppid;
		this.questionTitle = questionTitle;
		this.theQuestions = theQuestions;
		this.createTime = createTime;
		this.gameName = gameName;
		this.replyStatus = replyStatus;
		this.hasNew = hasNew;
		this.score = score;
		this.lastModifiedTime = lastModifiedTime;
	}

	public String getTgppid() {
		return tgppid;
	}

	public void setTgppid(String tgppid) {
		this.tgppid = tgppid;
	}

	public String getQuestionTitle() {
		return questionTitle;
	}

	public void setQuestionTitle(String questionTitle) {
		this.questionTitle = questionTitle;
	}

	public String getTheQuestions() {
		return theQuestions;
	}

	public void setTheQuestions(String theQuestions) {
		this.theQuestions = theQuestions;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	public String getReplyStatus() {
		return replyStatus;
	}

	public void setReplyStatus(String replyStatus) {
		this.replyStatus = replyStatus;
	}

	public String getHasNew() {
		return hasNew;
	}

	public void setHasNew(String hasNew) {
		this.hasNew = hasNew;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getLastModifiedTime() {
		return lastModifiedTime;
	}

	public void setLastModifiedTime(String lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}
	
}
