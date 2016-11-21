package com.efun.platform.http.response.bean;

import org.json.JSONObject;

import com.efun.platform.module.person.bean.GetSignInGiftRewardBean;
/**
 * 获取签到奖励
 *
 */
public class PersonGetSignInGiftResponse extends BaseResponseBean {
	private GetSignInGiftRewardBean mResponse;
	@Override
	public void setValues(Object object) {
		JSONObject jsonObject = (JSONObject) object;
		mResponse = new GetSignInGiftRewardBean();
		mResponse.setCode(jsonObject.optString("code"));
		mResponse.setMessage(jsonObject.optString("message"));
		mResponse.setAddExp(jsonObject.optInt("addExp"));
		mResponse.setAddGold(jsonObject.optInt("addGold"));
		mResponse.setIsSerial(jsonObject.optString("isSerial"));
		mResponse.setSerial(jsonObject.optString("serial"));
		mResponse.setAddPoint(jsonObject.optInt("addPoint"));
		mResponse.setExperienceValue(jsonObject.optString("experienceValue"));
		mResponse.setGoldValue(jsonObject.optString("goldValue"));
	}

	public GetSignInGiftRewardBean getResponse() {
		return mResponse;
	}
}
