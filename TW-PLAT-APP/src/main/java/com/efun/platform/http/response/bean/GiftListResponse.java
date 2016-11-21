package com.efun.platform.http.response.bean;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.efun.platform.module.bean.PageInfoBean;
import com.efun.platform.module.welfare.bean.GiftItemBean;
/**
 * 好康 - 礼包 - 礼包列表
 * @author Jesse
 *
 */
public class GiftListResponse extends BaseResponseBean{
	private static final long serialVersionUID = 1L;
	private ArrayList<GiftItemBean> response;
	@Override
	public void setValues(Object object) {
		JSONObject jsonObject = (JSONObject) object;
		createPageInfo(jsonObject);
		response = new ArrayList<GiftItemBean>();
		JSONArray giftArray = jsonObject.optJSONArray("result");
		JSONObject giftJson = null;
		GiftItemBean bean = null;
		for (int i = 0; i < giftArray.length(); i++) {
			bean = new GiftItemBean();
			giftJson = giftArray.optJSONObject(i);
			bean.setId(giftJson.optString("id"));
			bean.setGameCode(giftJson.optString("gameCode"));
			bean.setGameName(giftJson.optString("gameName"));
			bean.setGoodsName(giftJson.optString("goodsName"));
			bean.setAwardDesc(giftJson.optString("awardDesc"));
			bean.setAwardRule(giftJson.optString("awardRule"));
			bean.setActiveEndTime(giftJson.optString("activeEndTime"));
			bean.setTotal(giftJson.optInt("total"));
			bean.setHasUse(giftJson.optInt("hasUse"));
			bean.setUsePercent(giftJson.optString("usePercent"));
			bean.setIcon(giftJson.optString("icon"));
			bean.setUserHasGot(giftJson.optInt("userHasGot"));
			bean.setGoodsType(giftJson.optString("goodsType"));
			bean.setGoodsTypeName(giftJson.optString("goodsTypeName"));
			bean.setTag(giftJson.optString("tag"));
			response.add(bean);
		}
	}
	public ArrayList<GiftItemBean> getGiftsBean() {
		return response;
	}
}
