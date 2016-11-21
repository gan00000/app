package com.efun.platform.module.cs.bean;

import com.efun.platform.module.BaseDataBean;
/**
 * 提交回复
 * @author Jesse
 *
 */
public class CsReplyCommitQuestionBean extends BaseDataBean {
	private String code;

	public CsReplyCommitQuestionBean() {
		super();
	}

	public CsReplyCommitQuestionBean(String code) {
		super();
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
