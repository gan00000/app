package com.efun.platform.http.response.bean;

import org.json.JSONObject;

import com.efun.platform.module.account.bean.BindResultBean;
import com.efun.platform.module.account.bean.ResultBean;
/**
 * 綁定email
 * @author itxuxxey
 *
 */
public class BindEmailByVcodeResponse extends BaseResponseBean {
	/**
	 * 账号相关处理结果{@link ResultBean}
	 */
	private BindResultBean emailVcodeBean;
	@Override
	public void setValues(Object object) {
		JSONObject jsonObject = (JSONObject) object;
		emailVcodeBean = new BindResultBean();
		emailVcodeBean.setCode(jsonObject.optString("code"));
		emailVcodeBean.setMessage(jsonObject.optString("message"));
		if(jsonObject.has("result")){
			JSONObject _jsonObject = jsonObject.optJSONObject("result");
			emailVcodeBean.setEmail(_jsonObject.optString("email"));
//			bindPhoneBean.setPhone(_jsonObject.optString("phone"));
		}
	}

	public BindResultBean getResultBean() {
		return emailVcodeBean;
	}

}
