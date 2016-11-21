package com.efun.platform.module.cs.bean;

import com.efun.platform.module.BaseDataBean;
/**
 * 回答
 * @author Jesse
 *
 */
public class CsReplyDetailsBean extends BaseDataBean {
	private String id;
	private String replyTime;
	private String replyContent;
	private String replyRole;

	public CsReplyDetailsBean() {
		super();
	}

	public CsReplyDetailsBean(String id, String replyTime, String replyContent, String replyRole) {
		super();
		this.id = id;
		this.replyTime = replyTime;
		this.replyContent = replyContent;
		this.replyRole = replyRole;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getReplyTime() {
		return replyTime;
	}

	public void setReplyTime(String replyTime) {
		this.replyTime = replyTime;
	}

	public String getReplyContent() {
		return replyContent;
	}

	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}

	public String getReplyRole() {
		return replyRole;
	}

	public void setReplyRole(String replyRole) {
		this.replyRole = replyRole;
	}

}
