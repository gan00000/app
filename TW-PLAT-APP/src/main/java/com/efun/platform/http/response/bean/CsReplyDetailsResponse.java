package com.efun.platform.http.response.bean;

import org.json.JSONArray;
import org.json.JSONObject;

import com.efun.platform.module.cs.bean.CsReplyDetailsBean;
import com.efun.platform.module.cs.bean.CsReplyDetailsListBean;
/**
 * 客服 - 客服回复- 问题详情
 * @author Jesse
 *
 */
public class CsReplyDetailsResponse extends BaseResponseBean {
	/**
	 * 问题，回复 列表 {@link CsReplyDetailsListBean}
	 */
	private CsReplyDetailsListBean response = new CsReplyDetailsListBean();

	@Override
	public void setValues(Object object) {
		JSONObject jsonObject = (JSONObject) object;
		JSONArray jsonArray = jsonObject.optJSONArray("result");
		response.setCode(jsonObject.optString("code"));
		for (int i = 0; i < jsonArray.length(); i++) {
			CsReplyDetailsBean bean = new CsReplyDetailsBean();
			jsonObject = jsonArray.optJSONObject(i);
			bean.setId(jsonObject.optString("id"));
			bean.setReplyTime(jsonObject.optString("replyTime"));
			bean.setReplyRole(jsonObject.optString("replyRole"));
			bean.setReplyContent(jsonObject.optString("content"));
			response.getmBeans().add(bean);
		}
	}

	public CsReplyDetailsListBean getResponse() {
		return response;
	}
}
