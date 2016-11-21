package com.efun.platform.http.response.bean;

import org.json.JSONObject;

import com.efun.platform.module.game.bean.GameCommendBean;
/**
 * 游戏-添加评论
 * @author Jesse
 *
 */
public class GameCommendResponse extends BaseResponseBean{
	private static final long serialVersionUID = 1L;
	private GameCommendBean mResponse ;
	@Override
	public void setValues(Object object) {
		JSONObject jsonObject= (JSONObject) object;
		mResponse = new GameCommendBean();
		mResponse.setCode(jsonObject.optString("code"));
		mResponse.setMessage(jsonObject.optString("message"));
	}
	public GameCommendBean getGameCommend() {
		return mResponse;
	}
}
