package com.efun.platform.http.response.bean;

import org.json.JSONObject;

import com.efun.platform.module.game.bean.GamePraiseBean;
/**
 * 游戏 - 点赞
 * @author Jesse
 *
 */
public class GamePraiseResponse extends BaseResponseBean{
	/**
	 * 点赞 {@link GamePraiseBean}
	 */
	private GamePraiseBean response;
	@Override
	public void setValues(Object object) {
		JSONObject jsonObject = (JSONObject) object;
		response = new GamePraiseBean();
		response.setFlag(jsonObject.optBoolean("flag"));
		response.setMessage(jsonObject.optString("message"));
		response.setCode(jsonObject.optString("code"));
	}
	public GamePraiseBean getGamePraiseBean() {
		return response;
	}
}
