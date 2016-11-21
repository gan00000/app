package com.efun.platform.http.response.bean;

import org.json.JSONObject;

import com.efun.platform.module.welfare.bean.GiftSelfStatusBean;
import com.efun.platform.module.welfare.bean.NewGiftStatusBean;

/**
 * 检查新礼包 
 * @author itxuxxey
 *
 */
public class NewGiftStatusResponse extends BaseResponseBean {
	private NewGiftStatusBean response;
	@Override
	public void setValues(Object object) {
		JSONObject jsonObject = (JSONObject) object;
		response = new NewGiftStatusBean();
		response.setCode(jsonObject.optString("code"));
		response.setMessage(jsonObject.optString("message"));
		response.setResult(jsonObject.optLong("result"));
	}
	public NewGiftStatusBean getNewGiftStatusBean() {
		return response;
	}
}
