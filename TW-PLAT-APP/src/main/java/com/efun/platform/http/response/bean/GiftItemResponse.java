package com.efun.platform.http.response.bean;

import org.json.JSONObject;

import com.efun.platform.module.welfare.bean.GiftItemBean;

public class GiftItemResponse extends BaseResponseBean{
	private GiftItemBean response;
	@Override
	public void setValues(Object object) {
		response = new GiftItemBean();
		JSONObject jsonObject = (JSONObject) object;
		response.setCode(jsonObject.optString("code"));
		if(jsonObject.has("result")){
			JSONObject giftJson = jsonObject.optJSONObject("result");
			response.setId(giftJson.optString("id"));
			response.setGameCode(giftJson.optString("gameCode"));
			response.setGameName(giftJson.optString("gameName"));
			response.setGoodsName(giftJson.optString("goodsName"));
			response.setAwardDesc(giftJson.optString("awardDesc"));
			response.setAwardRule(giftJson.optString("awardRule"));
			response.setActiveEndTime(giftJson.optString("activeEndTime"));
			response.setTotal(giftJson.optInt("total"));
			response.setHasUse(giftJson.optInt("hasUse"));
			response.setUsePercent(giftJson.optString("usePercent"));
			response.setIcon(giftJson.optString("icon"));
			response.setUserHasGot(giftJson.optInt("userHasGot"));
			response.setGoodsType(giftJson.optString("goodsType"));
		}
	}
	public GiftItemBean getGiftItemBean() {
		return response;
	}

}
