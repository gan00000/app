package com.efun.platform.http.response.bean;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.efun.platform.module.game.bean.GameItemBean;
import com.efun.platform.module.welfare.bean.ActExtensionBean;
import com.efun.platform.module.welfare.bean.ActExtensionGiftBean;
import com.efun.platform.module.welfare.bean.ActExtensionTaskBean;
import com.efun.platform.module.welfare.bean.ExtensionBean;

/**
 * 好康 - 领取免费点数
 * @author Jesse
 *
 */
public class ActivityExtensionResponse extends BaseResponseBean{
	/**
	 * 任务，礼包列表 {@link ActExtensionBean}
	 */
	private ExtensionBean response;
	@Override
	public void setValues(Object object) {
		JSONObject jsonObject = (JSONObject) object;
		response = new ExtensionBean();
		response.setCode(jsonObject.optString("code"));
		response.setActivityCode(jsonObject.optString("activityCode"));
		response.setMessage(jsonObject.optString("message"));
		response.setGameCode(jsonObject.optString("gameCode"));
		response.setContext(jsonObject.optString("context"));
		response.setStartTime(jsonObject.optString("startTime"));
		response.setEndTime(jsonObject.optString("endTime"));
		
		JSONArray jsonArrayOfExtension = jsonObject.optJSONArray("games");
		ArrayList<ActExtensionBean> arrayOfExtension = new ArrayList<ActExtensionBean>();
		JSONObject jsonItemOfExtension = null;
		ActExtensionBean extensionItemBean = null;
		for(int j = 0 ; j < jsonArrayOfExtension.length(); j++){
			jsonItemOfExtension = jsonArrayOfExtension.optJSONObject(j);
			extensionItemBean = new ActExtensionBean();
			extensionItemBean.setGameCode(jsonItemOfExtension.optString("gameCode"));
			extensionItemBean.setCurrentState(jsonItemOfExtension.optString("currentState"));
			extensionItemBean.setContext(jsonItemOfExtension.optString("context"));
			extensionItemBean.setGiftsAllCount(jsonItemOfExtension.optInt("giftsAllCount"));
			extensionItemBean.setGiftsLastCount(jsonItemOfExtension.optInt("giftsLastCount"));
			extensionItemBean.setStartTime(jsonItemOfExtension.optString("startTime"));
			extensionItemBean.setEndTime(jsonItemOfExtension.optString("endTime"));
			extensionItemBean.setOutLine(jsonItemOfExtension.optString("outLine"));
			extensionItemBean.setRewarddescription(jsonItemOfExtension.optString("rewarddescription"));
			
			JSONObject gameObject = jsonItemOfExtension.optJSONObject("gameInfo");
			GameItemBean game = new GameItemBean();
			game.setGameCode(gameObject.optString("gameCode"));
			game.setGameName(gameObject.optString("gameName"));
			game.setGameType(gameObject.optString("gameType"));
			game.setScore(gameObject.optInt("score"));
			game.setBigpic(gameObject.optString("bigpic"));
			game.setSmallpic(gameObject.optString("smallpic"));
			game.setUrl(gameObject.optString("url"));
			game.setLang(gameObject.optString("lang"));
			game.setGameDesc(gameObject.optString("gameDesc"));
			game.setAndroidDownload(gameObject.optString("androidDownload"));
			game.setHkAndroidDownloadURL(gameObject.optString("hkAndroidDownloadURL"));
			game.setIosDownload(gameObject.optString("iosDownload"));
			game.setPackageName(gameObject.optString("packageName"));
			game.setHkPackageName(gameObject.optString("hkPackageName"));
			game.setAndroidVersion(gameObject.optString("androidVersion"));
			game.setVideoUrl(gameObject.optString("videoUrl"));
			game.setPackageSize(gameObject.optString("packageSize"));
			game.setVersion(gameObject.optString("version"));
			game.setLike(gameObject.optInt("like"));
			game.setHkAndroidVersion(gameObject.optString("hkAndroidVersion"));
			game.setHkIOSgameCode(gameObject.optString("hkIOSgameCode"));
			game.setFbUrl(gameObject.optString("fbUrl"));
			extensionItemBean.setGameBean(game);
			
			JSONArray jsonArrayOfGift = jsonItemOfExtension.optJSONArray("gifts");
			ArrayList<ActExtensionGiftBean> arrayOfGift = new ArrayList<ActExtensionGiftBean>();
			JSONObject jsonItemOfGift = null;
			ActExtensionGiftBean giftItemBean = null;
			for (int i = 0; i < jsonArrayOfGift.length(); i++) {
				jsonItemOfGift = jsonArrayOfGift.optJSONObject(i);
				giftItemBean = new ActExtensionGiftBean();
				giftItemBean.setId(jsonItemOfGift.optString("id"));
				giftItemBean.setGameCode(jsonItemOfGift.optString("gameCode"));
				giftItemBean.setGoodsName(jsonItemOfGift.optString("goodsName"));
				giftItemBean.setAwardDesc(jsonItemOfGift.optString("awardDesc"));
				giftItemBean.setAwardRule(jsonItemOfGift.optString("awardRule"));
				giftItemBean.setActiveStartTime(jsonItemOfGift.optString("activeStartTime"));
				giftItemBean.setActiveEndTime(jsonItemOfGift.optString("activeEndTime"));
				giftItemBean.setOrderno(jsonItemOfGift.optString("orderno"));
				giftItemBean.setAllCount(jsonItemOfGift.optString("allCount"));
				giftItemBean.setLastCount(jsonItemOfGift.optString("lastCount"));
				arrayOfGift.add(giftItemBean);
			}
			extensionItemBean.setArrayOfGift(arrayOfGift);
			
			JSONArray jsonArrayOfTask= jsonItemOfExtension.optJSONArray("tasks");
			ArrayList<ActExtensionTaskBean> arrayOfTask = new ArrayList<ActExtensionTaskBean>();
			JSONObject jsonItemOfTask = null;
			ActExtensionTaskBean taskItemBean = null;
			for (int i = 0; i < jsonArrayOfTask.length(); i++) {
				jsonItemOfTask = jsonArrayOfTask.optJSONObject(i);
				taskItemBean = new ActExtensionTaskBean();
				taskItemBean.setCurrentState(jsonItemOfTask.optString("currentState"));
				taskItemBean.setDescripTion(jsonItemOfTask.optString("descripTion"));
				taskItemBean.setFlag(jsonItemOfTask.optString("flag"));
				taskItemBean.setRewardCode(jsonItemOfTask.optString("rewardCode"));
				taskItemBean.setRewardName(jsonItemOfTask.optString("rewardName"));
				taskItemBean.setRewardNum(jsonItemOfTask.optString("rewardNum"));
				taskItemBean.setTaskCode(jsonItemOfTask.optString("taskCode"));
				taskItemBean.setTaskTitle(jsonItemOfTask.optString("taskTitle"));
				taskItemBean.setTaskUrl(jsonItemOfTask.optString("taskUrl"));
				taskItemBean.setSpecifiedServerCode(jsonItemOfTask.optString("specifiedServerCode"));
				taskItemBean.setOrderId(jsonItemOfTask.optString("orderId"));
				arrayOfTask.add(taskItemBean);
			}
			extensionItemBean.setArrayOfTask(arrayOfTask);
			arrayOfExtension.add(extensionItemBean);
		}
		
		response.setActExtensionBeans(arrayOfExtension);
		
	}
	public ExtensionBean getExtensionBean() {
		return response;
	}
}
