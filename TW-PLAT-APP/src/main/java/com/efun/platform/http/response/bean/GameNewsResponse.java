package com.efun.platform.http.response.bean;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.efun.platform.AndroidScape.E_string;
import com.efun.platform.module.game.bean.GameNewsBean;
import com.efun.platform.utils.Const.BeanType;
/**
 * 游戏-游戏资讯
 * @author Jesse
 *
 */
public class GameNewsResponse extends BaseResponseBean{
	private static final long serialVersionUID = 1L;
	private ArrayList<GameNewsBean> mResponse = new ArrayList<GameNewsBean>();
	@Override
	public void setValues(Object object) {
		JSONArray jsonArray= (JSONArray) object;
		JSONObject jsonObject = null;
		GameNewsBean bean = null;
		for (int i = 0; i < jsonArray.length(); i++) {
			bean = new GameNewsBean(BeanType.BEAN_GAMENEWSBEAN);
			jsonObject = jsonArray.optJSONObject(i);
			bean.setTitle(jsonObject.optString("title"));
			bean.setCrtime(jsonObject.optLong("crtime"));
			bean.setHtmlpathurl(jsonObject.optString("htmlpathurl"));
			bean.setType(jsonObject.optInt("type"));
			bean.setGameCode(jsonObject.optString("gameCode"));
			bean.setIphoneUrl(jsonObject.optString("iphoneUrl")+"#"+jsonObject.optInt("type"));
			mResponse.add(bean);
		}
		if(mResponse.size()==0){
			setHasNoData(true);
			setNoDataNotify(getContext().getString(E_string.efun_pd_no_data_no_summary));
		}
	}
	public ArrayList<GameNewsBean> getGameNewsList() {
		return mResponse;
	}
}
