package com.efun.platform.http.response.bean;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.efun.platform.module.bean.PageInfoBean;
import com.efun.platform.module.game.bean.GameItemBean;
/**
 * 游戏 - 游戏列表
 * @author Jesse
 *
 */
public class GameListResponse extends BaseResponseBean{
	private static final long serialVersionUID = 1L;
	private ArrayList<GameItemBean> mResponse;
	@Override
	public void setValues(Object object) {
		JSONObject jsonObject = (JSONObject) object;
		mResponse = new ArrayList<GameItemBean>();
		createPageInfo(jsonObject);
		JSONArray jsonArray= jsonObject.optJSONArray("result");
		GameItemBean bean = null;
		for (int i = 0; i < jsonArray.length(); i++) {
			bean = new GameItemBean();
			jsonObject = jsonArray.optJSONObject(i);
			bean.setGameCode(jsonObject.optString("gameCode"));
			bean.setGameType(jsonObject.optString("gameType"));
			bean.setGameName(jsonObject.optString("gameName"));
			bean.setScore(jsonObject.optInt("score"));
			bean.setBigpic(jsonObject.optString("bigpic"));
			bean.setSmallpic(jsonObject.optString("smallpic"));
			bean.setUrl(jsonObject.optString("url"));
			bean.setLang(jsonObject.optString("lang"));
			bean.setAndroidDownload(jsonObject.optString("androidDownload"));
			bean.setHkAndroidDownloadURL(jsonObject.optString("hkAndroidDownloadURL"));
			bean.setIosDownload(jsonObject.optString("iosDownload"));
			bean.setAndroidVersion(jsonObject.optString("androidVersion"));
			bean.setGameDesc(jsonObject.optString("gameDesc"));
			bean.setVideoUrl(jsonObject.optString("videoUrl"));
			bean.setLike(jsonObject.optInt("like"));
			bean.setVersion(jsonObject.optString("version"));
			bean.setPackageName(jsonObject.optString("packageName"));
			bean.setHkPackageName(jsonObject.optString("hkPackageName"));
			bean.setPackageSize(jsonObject.optString("packageSize"));
			bean.setPic_display(jsonObject.optString("pic_display"));
			bean.setHkAndroidVersion(jsonObject.optString("hkAndroidVersion"));
			bean.setHkIOSgameCode(jsonObject.optString("hkIOSgameCode"));
			bean.setFbUrl(jsonObject.optString("fbUrl"));
			bean.setApkDownloadUrl(jsonObject.optString("apkDownloadUrl"));
			mResponse.add(bean);
		}
	}
	public ArrayList<GameItemBean> getGameList() {
		return mResponse;
	}
}
