package com.efun.platform.http.response.bean;

import org.json.JSONObject;

import com.efun.platform.module.bean.ButtonControlBean;
/**
 * 控制开关结果
 * @author itxuxxey
 *
 */
public class RechargeControlResponse extends BaseResponseBean {
	private ButtonControlBean bean;
	@Override
	public void setValues(Object object) {
		JSONObject jsonObject = (JSONObject) object;
		bean = new ButtonControlBean();
		bean.setCode(jsonObject.optString("code"));
		bean.setMessage(jsonObject.optString("message"));
	}

	public ButtonControlBean getButtonControlBean() {
		return bean;
	}

}
