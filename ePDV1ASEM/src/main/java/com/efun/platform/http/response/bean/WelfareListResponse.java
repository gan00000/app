package com.efun.platform.http.response.bean;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.efun.platform.module.welfare.bean.WelfareItemBean;
import com.efun.platform.module.welfare.bean.WelfareListBean;
/**
 * 福利 -福利列表
 * @author harvey
 *
 */
public class WelfareListResponse extends BaseResponseBean{
	private static final long serialVersionUID = 1L;
	private WelfareListBean mResponse;
	@Override
	public void setValues(Object object) {
		JSONObject jsonObject = (JSONObject) object;
		mResponse = new WelfareListBean();
		mResponse.setCode(jsonObject.optString("code"));
		mResponse.setMessage(jsonObject.optString("message"));
		JSONArray jsonArray= jsonObject.optJSONArray("result");
		ArrayList<WelfareItemBean> welfareItemBeans = new ArrayList<WelfareItemBean>();
		WelfareItemBean bean = null;
		for (int i = 0; i < jsonArray.length(); i++) {
				bean = new WelfareItemBean();
				jsonObject = jsonArray.optJSONObject(i);
				bean.setContent(jsonObject.optString("content"));
				bean.setIcon(jsonObject.optString("icon"));
				bean.setUrl(jsonObject.optString("url"));
				bean.setTitle(jsonObject.optString("title"));
				welfareItemBeans.add(bean);
		}
		mResponse.setmResponse(welfareItemBeans);
	}
	public WelfareListBean getWelFareList() {
		return mResponse;
	}
}
