package com.efun.platform.http.response.bean;

import org.json.JSONObject;

import com.efun.platform.module.welfare.bean.GiftSelfStatusBean;

/**
 * 礼包中心-状态
 * @author Jesse
 *
 */
public class GiftSelfStatusResponse extends BaseResponseBean {
	private GiftSelfStatusBean response;
	@Override
	public void setValues(Object object) {
		JSONObject jsonObject = (JSONObject) object;
		response = new GiftSelfStatusBean();
		response.setCode(jsonObject.optString("code"));
	}
	public GiftSelfStatusBean getGiftSelfStatusBean() {
		return response;
	}
}
