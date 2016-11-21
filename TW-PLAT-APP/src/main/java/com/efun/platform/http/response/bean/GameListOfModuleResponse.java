package com.efun.platform.http.response.bean;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.efun.platform.module.person.bean.GiftGameBean;
import com.efun.platform.module.person.bean.GiftGameResultBean;
/**
 * 个人中心- 功能模块的游戏列表
 * @author harvery
 *
 */
public class GameListOfModuleResponse extends BaseResponseBean{
	private static final long serialVersionUID = 1L;
	private GiftGameResultBean mResponse;
	@Override
	public void setValues(Object object) {
		JSONObject jsonObject = (JSONObject) object;
		mResponse = new GiftGameResultBean();
		mResponse.setCode(jsonObject.optString("code"));
		mResponse.setMessage(jsonObject.optString("message"));
		JSONArray jsonArray= jsonObject.optJSONArray("result");
		GiftGameBean bean = null;
		ArrayList<GiftGameBean> mGiftGames = new ArrayList<GiftGameBean>();
		for (int i = 0; i < jsonArray.length(); i++) {
			bean = new GiftGameBean();
			jsonObject = jsonArray.optJSONObject(i);
			bean.setGameCode(jsonObject.optString("gameCode"));
			bean.setGameName(jsonObject.optString("gameName"));
			bean.setIsGame(jsonObject.optString("isGame"));
			mGiftGames.add(bean);
		}
		mResponse.setmGiftGames(mGiftGames);
	}
	
	public GiftGameResultBean getmResponse() {
		return mResponse;
	}
}
