package com.efun.platform.http.response.bean;

import org.json.JSONObject;

import com.efun.platform.module.game.bean.GameCommendBean;
/**
 * 游戏-评分（玩家评论）
 * @author harvery
 *
 */
public class GameVoteScoreResponse extends BaseResponseBean{
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
