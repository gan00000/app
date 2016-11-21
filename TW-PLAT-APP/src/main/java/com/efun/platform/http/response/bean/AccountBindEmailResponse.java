package com.efun.platform.http.response.bean;

import org.json.JSONObject;

import com.efun.platform.module.account.bean.BindResultBean;
import com.efun.platform.module.account.bean.ResultBean;
/**
 * 郵箱綁定
 * @author itxuxxey
 *
 */
public class AccountBindEmailResponse extends BaseResponseBean {
	/**
	 * 账号相关处理结果{@link ResultBean}
	 */
	private BindResultBean bindPhoneBean;
	@Override
	public void setValues(Object object) {
		JSONObject jsonObject = (JSONObject) object;
		bindPhoneBean = new BindResultBean();
		bindPhoneBean.setCode(jsonObject.optString("code"));
		bindPhoneBean.setMessage(jsonObject.optString("message"));
		if(jsonObject.has("result")){
			JSONObject _jsonObject = jsonObject.optJSONObject("result");
			bindPhoneBean.setEmail(_jsonObject.optString("email"));
//			bindPhoneBean.setPhone(_jsonObject.optString("phone"));
		}
	}

	public BindResultBean getResultBean() {
		return bindPhoneBean;
	}

}
