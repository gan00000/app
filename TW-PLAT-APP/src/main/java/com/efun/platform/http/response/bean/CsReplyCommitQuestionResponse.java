package com.efun.platform.http.response.bean;

import org.json.JSONObject;

import com.efun.platform.module.cs.bean.CsReplyCommitQuestionBean;
/**
 * 客服 - 客服回复 - 提交问题
 * @author Jesse
 *
 */
public class CsReplyCommitQuestionResponse extends BaseResponseBean {
	private CsReplyCommitQuestionBean response;

	@Override
	public void setValues(Object object) {
		JSONObject jsonObject = (JSONObject) object;
		response = new CsReplyCommitQuestionBean();
		response.setCode(jsonObject.optString("code"));
	}

	public CsReplyCommitQuestionBean getResponse() {
		return response;
	}
}
