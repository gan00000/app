package com.efun.platform.http.response.bean;

import org.json.JSONObject;

import com.efun.platform.module.account.bean.ResultBean;
/**
 * 更新玩家头像
 * @author itxuxxey
 *
 */
public class PersonRefreshHeadIconResponse extends BaseResponseBean {
	/**
	 * 账号相关处理结果{@link ResultBean}
	 */
	private ResultBean bean;
	@Override
	public void setValues(Object object) {
		JSONObject jsonObject = (JSONObject) object;
		bean = new ResultBean();
		bean.setCode(jsonObject.optString("code"));
		bean.setMessage(jsonObject.optString("message"));
	}

	public ResultBean getResultBean() {
		return bean;
	}

}
