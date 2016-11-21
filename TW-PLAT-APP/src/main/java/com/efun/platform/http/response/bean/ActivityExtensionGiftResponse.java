package com.efun.platform.http.response.bean;

import org.json.JSONObject;

import com.efun.platform.module.welfare.bean.ActExtensionGiftGetBean;


/**
 * 好康 - 领取免费点数 - 选礼包
 * @author Jesse
 *
 */
public class ActivityExtensionGiftResponse extends BaseResponseBean{
	/**
	 * 选礼包 {@link ActExtensionGiftGetBean}
	 */
	private ActExtensionGiftGetBean response;
	@Override
	public void setValues(Object object) {
		JSONObject jsonObject = (JSONObject) object;
		response = new ActExtensionGiftGetBean();
		response.setCode(jsonObject.optString("code"));
		response.setMessage(jsonObject.optString("message"));
	}
	public ActExtensionGiftGetBean getActExtensionGiftGetBean() {
		return response;
	}
	
}
