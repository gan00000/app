package com.efun.platform.http.response.bean;

import org.json.JSONObject;

import com.efun.platform.module.interaction.bean.CsAskBean;
/**
 * 客服- 提问 - 提问
 * @author itxuxxey
 *
 */
public class CsAskResponse extends BaseResponseBean {
	/**
	 * {@link CsAskBean}
	 */
	private CsAskBean response;
	@Override
	public void setValues(Object object) {
		JSONObject jsonObject = (JSONObject) object;
		response = new CsAskBean();
		response.setCode(jsonObject.optString("code"));
		response.setMessage(jsonObject.optString("message"));
	}

	public CsAskBean getResponse() {
		return response;
	}
}
