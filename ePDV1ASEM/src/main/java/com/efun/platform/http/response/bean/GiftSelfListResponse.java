package com.efun.platform.http.response.bean;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.efun.platform.module.bean.PageInfoBean;
import com.efun.platform.module.welfare.bean.GiftSelfItemBean;
/**
 * 好康 - 礼包 - 我的序列号中心
 * @author itxuxxey
 *
 */
public class GiftSelfListResponse extends BaseResponseBean{
	private ArrayList<GiftSelfItemBean> response;
	@Override
	public void setValues(Object object) {
		JSONObject jsonObject = (JSONObject)object;
		createPageInfo(jsonObject);
		response = new ArrayList<GiftSelfItemBean>();
		JSONArray jsonArray = jsonObject.optJSONArray("result");
		GiftSelfItemBean bean = null;
		JSONObject jsonItem = null;
		for (int i = 0; i < jsonArray.length(); i++) {
			jsonItem = jsonArray.optJSONObject(i);
			bean = new GiftSelfItemBean();
			bean.setId(jsonItem.optString("id"));
			bean.setGoodsId(jsonItem.optString("goodsId"));
			bean.setUid(jsonItem.optString("uid"));
			bean.setSerial(jsonItem.optString("serial"));
			bean.setSecretCode(jsonItem.optString("secretCode"));
			bean.setHasUse(jsonItem.optString("hasUse"));
			bean.setGameCode(jsonItem.optString("gameCode"));
			bean.setGoodsType(jsonItem.optString("goodsType"));
			bean.setModifiedTime(jsonItem.optLong("modifiedTime"));
			bean.setGameName(jsonItem.optString("gameName"));
			bean.setRewardTime(jsonItem.optLong("rewardTime"));
			bean.setGoodsName(jsonItem.optString("goodsName"));
			bean.setActivityName(jsonItem.optString("activityName"));
			bean.setCategory(jsonItem.optString("category"));
			response.add(bean);
		}
	}
	public ArrayList<GiftSelfItemBean> getGiftSelfList() {
		return response;
	}
}
