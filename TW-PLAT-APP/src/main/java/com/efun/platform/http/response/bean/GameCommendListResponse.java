package com.efun.platform.http.response.bean;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.efun.platform.AndroidScape.E_string;
import com.efun.platform.module.game.bean.GameCommendItemBean;
/**
 * 游戏-游戏评论列表
 * @author Jesse
 *
 */
public class GameCommendListResponse extends BaseResponseBean{
	private ArrayList<GameCommendItemBean> mResponse = new ArrayList<GameCommendItemBean>();
	@Override
	public void setValues(Object object) {
		JSONArray jsonArray= (JSONArray) object;
		GameCommendItemBean bean = null;
		JSONObject jsonItem = null;
		for (int i = 0; i < jsonArray.length(); i++) {
			bean = new GameCommendItemBean();
			jsonItem = jsonArray.optJSONObject(i);
			bean.setUserName(jsonItem.optString("userName"));
			bean.setContent(jsonItem.optString("content"));
			bean.setModifiedTime(jsonItem.optLong("modifiedTime"));
			bean.setIcon(jsonItem.optString("icon"));
			mResponse.add(bean);
		}
		if(mResponse.size()==0){
			setHasNoData(true);
			setNoDataNotify(getContext().getString(E_string.efun_pd_no_data_no_commend));
		}
	}
	public ArrayList<GameCommendItemBean> getGameCommendList() {
		return mResponse;
	}
}
