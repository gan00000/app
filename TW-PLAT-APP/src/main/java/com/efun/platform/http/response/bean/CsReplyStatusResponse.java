package com.efun.platform.http.response.bean;

import org.json.JSONObject;

import com.efun.platform.module.cs.bean.CsReplyStatusBean;

/**
 * 客服 - 客服回复- 问题详情
 * @author Jesse
 *
 */
public class CsReplyStatusResponse extends BaseResponseBean {
	private CsReplyStatusBean mResponse;
	public CsReplyStatusResponse() {
		super();
	}
	@Override
	public void setValues(Object object) {
		JSONObject jsonObject = (JSONObject) object;
		mResponse = new CsReplyStatusBean();
		mResponse.setCode(jsonObject.optString("code"));
	}
	public CsReplyStatusBean getCsReplyStatusBean() {
		return mResponse;
	}
	
}
