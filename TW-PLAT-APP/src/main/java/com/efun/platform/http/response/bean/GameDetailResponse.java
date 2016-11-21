package com.efun.platform.http.response.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.efun.platform.module.game.bean.GameDetailBean;
/**
 * 游戏-游戏详情
 * @author Jesse
 *
 */
public class GameDetailResponse extends BaseResponseBean{
	/**
	 * 游戏详情 {@link GameDetailBean}
	 */
	private GameDetailBean mGameDetailBean = new GameDetailBean();
	@Override
	public void setValues(Object object) {
		JSONObject jsonObject = (JSONObject) object;
		mGameDetailBean.setNewsEtag(jsonObject.optString("newsEtag"));
		mGameDetailBean.setPicEtag(jsonObject.optString("picEtag"));
		try {
			JSONObject childObject = jsonObject.getJSONObject("gameDetail");
			mGameDetailBean.setGameCode(childObject.optString("gameCode"));
			mGameDetailBean.setVideoUrl(childObject.optString("videoUrl"));
			mGameDetailBean.setDescription(childObject.optString("description"));
			mGameDetailBean.setNewVersionDesc(childObject.optString("newVersionDesc"));
			JSONArray smallArray = childObject.optJSONArray("small_group");
			String[] small = new String[smallArray.length()];
			for (int i = 0; i < smallArray.length(); i++) {
				small[i] = smallArray.optString(i);
			}
			mGameDetailBean.setSmallgroup(small);
			JSONArray bigArray = childObject.optJSONArray("big_group");
			String[] big = new String[bigArray.length()];
			for (int i = 0; i < bigArray.length(); i++) {
				big[i] = bigArray.optString(i);
			}
			mGameDetailBean.setBiggroup(big);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	public GameDetailBean getGameDetailBean() {
		return mGameDetailBean;
	}
}
