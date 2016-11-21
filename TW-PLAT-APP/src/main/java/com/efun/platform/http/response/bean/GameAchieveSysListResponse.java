package com.efun.platform.http.response.bean;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.efun.platform.module.bean.PageInfoBean;
import com.efun.platform.module.game.bean.AchieveSysItemBean;
import com.efun.platform.module.game.bean.GameAchieveSysBean;
import com.efun.platform.module.game.bean.GameItemBean;
/**
 * 游戏 - 成就系統
 * @author itxuxxey
 *
 */
public class GameAchieveSysListResponse extends BaseResponseBean{
	private static final long serialVersionUID = 1L;
	private GameAchieveSysBean mResponse;
	private ArrayList<AchieveSysItemBean> beanlist;
	@Override
	public void setValues(Object object) {
		JSONObject jsonObject = (JSONObject) object;
		mResponse = new GameAchieveSysBean();
		mResponse.setCode(jsonObject.optString("code"));
		mResponse.setMessage(jsonObject.optString("message"));
		mResponse.setExtendedObj(jsonObject.optString("extendedObj"));
		JSONArray jsonArray= jsonObject.optJSONArray("result");
		beanlist = new ArrayList<AchieveSysItemBean>();
		AchieveSysItemBean bean = null;
		for (int i = 0; i < jsonArray.length(); i++) {
			bean = new AchieveSysItemBean();
			jsonObject = jsonArray.optJSONObject(i);
			bean.setGameName(jsonObject.optString("gameName"));
			bean.setGameIcon(jsonObject.optString("gameIcon"));
			bean.setTaskUrl(jsonObject.optString("taskUrl"));
			beanlist.add(bean);
		}
		mResponse.setAchieveSysBeans(beanlist);
	}
	public GameAchieveSysBean getResponse() {
		return mResponse;
	}
}
