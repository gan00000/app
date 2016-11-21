package com.efun.platform.module.cs.bean;

import com.efun.platform.module.BaseDataBean;
/**
 * 常见问题
 * @author Jesse
 *
 */
public class CsQuestionItemBean extends BaseDataBean{
	private String questionsTitle;
	private String theQuestions;
	private String type;
	private String createTime;
	public CsQuestionItemBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CsQuestionItemBean(String questionsTitle, String theQuestions,
			String type, String createTime) {
		super();
		this.questionsTitle = questionsTitle;
		this.theQuestions = theQuestions;
		this.type = type;
		this.createTime = createTime;
	}
	public String getQuestionsTitle() {
		return questionsTitle;
	}
	public void setQuestionsTitle(String questionsTitle) {
		this.questionsTitle = questionsTitle;
	}
	public String getTheQuestions() {
		return theQuestions;
	}
	public void setTheQuestions(String theQuestions) {
		this.theQuestions = theQuestions;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
}
