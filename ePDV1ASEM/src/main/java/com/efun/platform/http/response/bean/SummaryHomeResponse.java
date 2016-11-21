package com.efun.platform.http.response.bean;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.efun.platform.module.summary.bean.BannerItemBean;
import com.efun.platform.module.summary.bean.EventGameBean;
import com.efun.platform.module.summary.bean.SummaryHomeBean;
import com.efun.platform.module.summary.bean.SummaryItemBean;
import com.efun.platform.module.summary.bean.VideoBean;
import com.efun.platform.utils.Const.BeanType;
/**
 * 资讯 - 首页
 * @author itxuxxey
 *
 */
public class SummaryHomeResponse extends BaseResponseBean{
	/**
	 * 首页内容 {@link SummaryHomeBean}
	 */
	private SummaryHomeBean response;
	@Override
	public void setValues(Object object) {
		JSONObject jsonObject = (JSONObject) object;
		response = new SummaryHomeBean();
		response.setGameEtag(jsonObject.optString("gameEtag"));
		response.setNewsEtag(jsonObject.optString("newsEtag"));
		response.setPicEtag(jsonObject.optString("picEtag"));
		JSONArray bannerArray = jsonObject.optJSONArray("picsInfo");
		ArrayList<BannerItemBean> banners = new ArrayList<BannerItemBean>();
		BannerItemBean banner = null;
		JSONObject picJson = null;
		for (int i = 0; i < bannerArray.length(); i++) {
			picJson = bannerArray.optJSONObject(i);
			banner = new BannerItemBean();
			banner.setPic(picJson.optString("pic"));
			banner.setUrl(picJson.optString("url"));
			banner.setTitle(picJson.optString("title"));
			banners.add(banner);
		}
		JSONArray gameArray = jsonObject.optJSONArray("gamesInfo");
		ArrayList<EventGameBean> games = new ArrayList<EventGameBean>();
		EventGameBean game = null;
		JSONObject gameJson = null;
		for (int i = 0; i < gameArray.length(); i++) {
			gameJson = gameArray.optJSONObject(i);
			game = new EventGameBean();
			game.setGameCode(gameJson.optString("gameCode"));
			game.setGameName(gameJson.optString("gameName"));
			game.setActGameCode(gameJson.optString("actGameCode"));
			game.setGameDesc(gameJson.optString("gameDesc"));
			game.setGameType(gameJson.optString("gameType"));
			game.setBigpic(gameJson.optString("bigpic"));
			game.setSmallpic(gameJson.optString("smallpic"));
			game.setUrl(gameJson.optString("url"));
			game.setLike(gameJson.optInt("like"));
			game.setAndroidDownload(gameJson.optString("androidDownload"));
			game.setHkAndroidDownloadURL(gameJson.optString("hkAndroidDownloadURL"));
			game.setAndroidVersion(gameJson.optString("androidVersion"));
			game.setPackageName(gameJson.optString("packageName"));
			game.setPackageSize(gameJson.optString("packageSize"));
			game.setVideoUrl(gameJson.optString("videoUrl"));
			game.setVersion(gameJson.optString("version"));
			game.setPic_display(gameJson.optString("pic_display"));
			game.setApkDownloadUrl(gameJson.optString("apkDownloadUrl"));
			games.add(game);
		}
		JSONArray newsArray= jsonObject.optJSONArray("newsInfo");
		ArrayList<SummaryItemBean> summarys = new ArrayList<SummaryItemBean>();
		SummaryItemBean summary = null;
		JSONObject summaryJson = null;
		for (int i = 0; i < newsArray.length(); i++) {
			summaryJson = newsArray.optJSONObject(i);
			summary = new SummaryItemBean(BeanType.BEAN_SUMMARYITEMBEAN);
			summary.setTitle(summaryJson.optString("title"));
			summary.setType(summaryJson.optInt("type"));
			summary.setCrtime(summaryJson.optLong("crtime"));
			summary.setHtmlpathurl(summaryJson.optString("htmlpathurl"));
			summary.setGameCode(summaryJson.optString("gameCode"));
			summary.setIphoneUrl(summaryJson.optString("iphoneUrl")+"#"+summaryJson.optInt("type"));
			summary.setId(summaryJson.optLong("id"));
			summary.setSubTitle(summaryJson.optString("subTitle"));
			summary.setVideoPic(summaryJson.optString("videoPic"));
			summary.setVideoUrl(summaryJson.optString("videoUrl"));
			summary.setVideoTime(summaryJson.optString("videoTime"));
			summary.setLikes(summaryJson.optInt("likes"));
			summary.setIsLiked(summaryJson.optInt("isLiked"));
			summary.setSmallpic(summaryJson.optString("smallpic"));
			summary.setHot(summaryJson.optInt("hot"));
			summary.setHotTag(summaryJson.optString("hotTag"));
			summary.setTypeTag(summaryJson.optString("typeTag"));
			summarys.add(summary);
		}
		JSONObject videoObject = jsonObject.optJSONObject("videoInfo");
		VideoBean videoBean = null;
		if(videoObject.has("videoUrl")){
			videoBean = new VideoBean();
			videoBean.setTitle(videoObject.optString("title"));
			videoBean.setVideoPic(videoObject.optString("videoPic"));
			videoBean.setVideoUrl(videoObject.optString("videoUrl"));
		}
		response.setBannerArray(banners);
		response.setGameArray(games);
		response.setSummaryArray(summarys);
		response.setVideoBean(videoBean);
	}
	public SummaryHomeBean getSummaryHomeBean() {
		return response;
	}

}
