package com.efun.platform.http.response.bean;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.efun.platform.module.cs.bean.CsGainGameBean;
import com.efun.platform.module.cs.bean.CsGainGameItemBean;
import com.efun.platform.module.cs.bean.CsGainServerItemBean;

/**
 * 客服- 提问 -游戏列表
 * 
 * @author Jesse
 * 
 */
public class CsGainGameListResponse extends BaseResponseBean {
	private CsGainGameBean response;

	@Override
	public void setValues(Object object) {
		JSONObject jsonObject = (JSONObject) object;
		response = new CsGainGameBean();
		response.setCode(jsonObject.optString("code"));
		response.setMessage(jsonObject.optString("message"));
		ArrayList<CsGainGameItemBean> gameList = new ArrayList<CsGainGameItemBean>();
		if (jsonObject.has("gameNameList")) {
			JSONArray jsonArray = jsonObject.optJSONArray("gameNameList");
			CsGainGameItemBean bean = null;
			for (int i = 0; i < jsonArray.length(); i++) {
				jsonObject = jsonArray.optJSONObject(i);
				bean = new CsGainGameItemBean();
				bean.setGameCode(jsonObject.optString("gameCode"));
				bean.setGameName(jsonObject.optString("gameName"));
				gameList.add(bean);
			}
		}
		response.setGameList(gameList);
	}

	public CsGainGameBean getCsGainGameBean() {
		return response;
	}
}
