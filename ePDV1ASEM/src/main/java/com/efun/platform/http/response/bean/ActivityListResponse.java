package com.efun.platform.http.response.bean;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.efun.platform.module.bean.PageInfoBean;
import com.efun.platform.module.welfare.bean.ActItemBean;
import com.efun.platform.utils.Const.BeanType;
/**
 * 好康 - 活动列表
 * @author itxuxxey
 *
 */
public class ActivityListResponse extends BaseResponseBean{
	private static final long serialVersionUID = 1L;
	/**
	 * 活动列表
	 */
	private ArrayList<ActItemBean> response;
	@Override
	public void setValues(Object object) {
		JSONObject jsonObject = (JSONObject) object;
//		createPageInfo(jsonObject);
		response = new ArrayList<ActItemBean>();
		JSONArray actArray = jsonObject.optJSONArray("result");
		JSONObject actJson = null;
		ActItemBean bean = null;
		for (int i = 0; i < actArray.length(); i++) {
			bean = new ActItemBean(BeanType.BEAN_ACTITEMBEAN);
			actJson = actArray.optJSONObject(i);
			bean.setActivityCode(actJson.optString("activityCode"));
			bean.setActivityName(actJson.optString("activityName"));
			bean.setActivityUrl(actJson.optString("activityUrl"));
			bean.setEndTime(actJson.optString("endTime"));
			bean.setImg(actJson.optString("img"));
			bean.setIsPayactivity(actJson.optString("isPayactivity"));
			bean.setStartTime(actJson.optString("startTime"));
			bean.setShareicon(actJson.optString("shareicon"));
			response.add(bean);
		}
	}
	public ArrayList<ActItemBean> getActsBean() {
		return response;
	}
}
