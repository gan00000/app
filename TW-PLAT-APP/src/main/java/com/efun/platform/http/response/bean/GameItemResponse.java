package com.efun.platform.http.response.bean;

import org.json.JSONObject;

import com.efun.platform.module.game.bean.GameItemBean;

public class GameItemResponse extends BaseResponseBean{
	private GameItemBean mResponse;
	@Override
	public void setValues(Object object) {
		mResponse = new GameItemBean();
		JSONObject jsonObject = (JSONObject) object;
		mResponse.setCode(jsonObject.optString("code"));
		if(jsonObject.has("result")){
			JSONObject gameJson = jsonObject.optJSONObject("result");
			mResponse.setGameCode(gameJson.optString("gameCode"));
			mResponse.setGameType(gameJson.optString("gameType"));
			mResponse.setGameName(gameJson.optString("gameName"));
			mResponse.setScore(gameJson.optInt("score"));
			mResponse.setBigpic(gameJson.optString("bigpic"));
			mResponse.setSmallpic(gameJson.optString("smallpic"));
			mResponse.setUrl(gameJson.optString("url"));
			mResponse.setLang(gameJson.optString("lang"));
			mResponse.setAndroidDownload(gameJson.optString("androidDownload"));
			mResponse.setHkAndroidDownloadURL(gameJson.optString("hkAndroidDownloadURL"));
			mResponse.setIosDownload(gameJson.optString("iosDownload"));
			mResponse.setAndroidVersion(gameJson.optString("androidVersion"));
			mResponse.setGameDesc(gameJson.optString("gameDesc"));
			mResponse.setVideoUrl(gameJson.optString("videoUrl"));
			mResponse.setLike(gameJson.optInt("like"));
			mResponse.setVersion(gameJson.optString("version"));
			mResponse.setPackageName(gameJson.optString("packageName"));
			mResponse.setHkPackageName(gameJson.optString("hkPackageName"));
			mResponse.setPackageSize(gameJson.optString("packageSize"));
			mResponse.setPic_display(gameJson.optString("pic_display"));
			mResponse.setHkAndroidVersion(gameJson.optString("hkAndroidVersion"));
			mResponse.setHkIOSgameCode(gameJson.optString("hkIOSgameCode"));
			mResponse.setFbUrl(gameJson.optString("fbUrl"));
		}
	}
	public GameItemBean getGameItemBean() {
		return mResponse;
	}
}
