package com.efun.platform.http.response.bean;

import org.json.JSONObject;

import com.efun.platform.module.welfare.bean.GiftKnockBean;

/**
 * 好康 - 礼包 - 抢礼包
 * @author Jesse
 *
 */
public class GiftKnockResponse extends BaseResponseBean{
	/**
	 * 抢礼包 {@link GiftKnockBean}
	 */
	private GiftKnockBean response;
	@Override
	public void setValues(Object object) {
		JSONObject jsonObject = (JSONObject) object;
		response = new GiftKnockBean();
		response.setCode(jsonObject.optString("code"));
		response.setMessage(jsonObject.optString("message"));
		response.setSerial(jsonObject.optString("serial"));
	}
	public GiftKnockBean getGiftKnockBean() {
		return response;
	}
}
