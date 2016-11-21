package com.efun.platform.http.response.bean;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.efun.platform.module.bean.PageInfoBean;
import com.efun.platform.module.summary.bean.SummaryItemBean;
import com.efun.platform.utils.Const.BeanType;
/**
 * 资讯-资讯列表
 * @author Jesse
 *
 */
public class SummaryListResponse extends BaseResponseBean {
	private ArrayList<SummaryItemBean> response = new ArrayList<SummaryItemBean>();
	@Override
	public void setValues(Object object) {
		JSONObject jsonObject = (JSONObject) object;
		createPageInfo(jsonObject);
		JSONArray jsonArray = jsonObject.optJSONArray("result");
		SummaryItemBean bean = null;
		for (int i = 0; i < jsonArray.length(); i++) {
			jsonObject = jsonArray.optJSONObject(i);
			bean = new SummaryItemBean(BeanType.BEAN_SUMMARYITEMBEAN);
			bean.setTitle(jsonObject.optString("title"));
			bean.setType(jsonObject.optInt("type"));
			bean.setCrtime(jsonObject.optLong("crtime"));
			bean.setHtmlpathurl(jsonObject.optString("htmlpathurl"));
			bean.setGameCode(jsonObject.optString("gameCode"));
			bean.setIphoneUrl(jsonObject.optString("iphoneUrl")+"#"+jsonObject.optInt("type"));
			bean.setId(jsonObject.optLong("id"));
			bean.setSubTitle(jsonObject.optString("subTitle"));
			bean.setVideoPic(jsonObject.optString("videoPic"));
			bean.setVideoUrl(jsonObject.optString("videoUrl"));
			bean.setVideoTime(jsonObject.optString("videoTime"));
			bean.setLikes(jsonObject.optInt("likes"));
			bean.setIsLiked(jsonObject.optInt("isLiked"));
			response.add(bean);
		}
	}
	public ArrayList<SummaryItemBean> getSummaryList() {
		return response;
	}
}
