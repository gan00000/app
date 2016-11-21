package com.efun.platform.http.response.bean;

import org.json.JSONObject;

import com.efun.platform.module.account.bean.ResultBean;
/**
 * 發送驗證碼到郵箱
 * @author itxuxxey
 *
 */
public class SendVcodeToEmailResponse extends BaseResponseBean {
	/**
	 * 账号相关处理结果{@link ResultBean}
	 */
	private ResultBean bindEmailBean;
	@Override
	public void setValues(Object object) {
		JSONObject jsonObject = (JSONObject) object;
		bindEmailBean = new ResultBean();
		bindEmailBean.setCode(jsonObject.optString("code"));
		bindEmailBean.setMessage(jsonObject.optString("message"));
	}

	public ResultBean getResultBean() {
		return bindEmailBean;
	}

}
