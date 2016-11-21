package com.efun.platform.http.response.bean;

import org.json.JSONObject;

import com.efun.platform.module.cs.bean.CsAskBean;
import com.efun.platform.module.person.bean.NewSysEmailBean;
/**
 * 檢測新信件
 * @author itxuxxey
 *
 */
public class CheckSysEmailResponse extends BaseResponseBean {
	/**
	 * {@link CsAskBean}
	 */
	private NewSysEmailBean response;
	@Override
	public void setValues(Object object) {
		JSONObject jsonObject = (JSONObject) object;
		response = new NewSysEmailBean();
		response.setCode(jsonObject.optString("code"));
		response.setMessage(jsonObject.optString("message"));
		response.setResult(jsonObject.optInt("result"));
	}

	public NewSysEmailBean getResponse() {
		return response;
	}
}
