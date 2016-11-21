package com.efun.platform.http.response.bean;

import org.json.JSONObject;

import com.efun.platform.module.summary.bean.SummaryPraiseBean;
/**
 * 资讯 - 点赞
 * @author itxuxxey
 *
 */
public class SummaryPraiseResponse extends BaseResponseBean{
	/**
	 * 点赞 {@link GamePraiseBean}
	 */
	private SummaryPraiseBean response;
	@Override
	public void setValues(Object object) {
		JSONObject jsonObject = (JSONObject) object;
		response = new SummaryPraiseBean();
		response.setMessage(jsonObject.optString("message"));
		response.setCode(jsonObject.optString("code"));
	}
	public SummaryPraiseBean getSummaryPraiseBean() {
		return response;
	}
}
