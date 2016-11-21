package com.efun.platform.http.response.bean;

import org.json.JSONObject;

import com.efun.platform.module.account.bean.BindResultBean;
import com.efun.platform.module.account.bean.ResultBean;
/**
 * 账号绑定
 * @author Jesse
 *
 */
public class AccountBindPhoneResponse extends BaseResponseBean {
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
//			bindPhoneBean.setEmail(_jsonObject.optString("email"));
			bindPhoneBean.setPhone(_jsonObject.optString("phone"));
			bindPhoneBean.setMd5Phone(_jsonObject.optString("md5Phone"));
			bindPhoneBean.setFinished(_jsonObject.optBoolean("isFinished"));
			bindPhoneBean.setGetAward(_jsonObject.optBoolean("isGetAward"));
			
		}
	}

	public BindResultBean getResultBean() {
		return bindPhoneBean;
	}

}
