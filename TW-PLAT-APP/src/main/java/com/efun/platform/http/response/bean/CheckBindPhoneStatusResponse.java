package com.efun.platform.http.response.bean;

import org.json.JSONObject;

import com.efun.platform.module.cs.bean.CsAskBean;
import com.efun.platform.module.person.bean.CheckPhoneStatusBean;
import com.efun.platform.module.person.bean.NewSysEmailBean;
/**
 * 檢測手机绑定状态
 * @author itxuxxey
 *
 */
public class CheckBindPhoneStatusResponse extends BaseResponseBean {
	/**
	 * {@link CsAskBean}
	 */
	private CheckPhoneStatusBean response;
	@Override
	public void setValues(Object object) {
		JSONObject jsonObject = (JSONObject) object;
		response = new CheckPhoneStatusBean();
		response.setCode(jsonObject.optString("code"));
		response.setMessage(jsonObject.optString("message"));
		response.setPhone(jsonObject.optString("phone"));
		response.setMd5Phone(jsonObject.optString("md5Phone"));
		response.setFinished(jsonObject.optBoolean("isFinished"));
		response.setGetAward(jsonObject.optBoolean("isGetAward"));
	}

	public CheckPhoneStatusBean getResponse() {
		return response;
	}
}
