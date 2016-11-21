package com.efun.platform.http.response.bean;

import org.json.JSONObject;

import com.efun.platform.module.cs.bean.CsAskBean;
import com.efun.platform.module.person.bean.BindPhoneByCallPhoneBean;
/**
 * 语音绑定
 * @author itxuxxey
 *
 */
public class BindPhoneByCallPhoneResponse extends BaseResponseBean {
	/**
	 * {@link CsAskBean}
	 */
	private BindPhoneByCallPhoneBean response;
	@Override
	public void setValues(Object object) {
		JSONObject jsonObject = (JSONObject) object;
		response = new BindPhoneByCallPhoneBean();
		response.setCode(jsonObject.optString("code"));
		response.setMessage(jsonObject.optString("message"));
		response.setCall(jsonObject.optString("call"));
	}

	public BindPhoneByCallPhoneBean getResponse() {
		return response;
	}
}
