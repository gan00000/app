package com.efun.platform.http.response.bean;

import org.json.JSONObject;

import com.efun.platform.module.person.bean.SigninBean;
/**
 * 签到
 *
 */
public class PersonSignResponse extends BaseResponseBean {
	/**
	 * {@link SigninBean}
	 */
	private SigninBean mResponse;
	@Override
	public void setValues(Object object) {
		JSONObject jsonObject = (JSONObject) object;
		mResponse = new SigninBean();

		mResponse.setCode(jsonObject.optString("code"));
		mResponse.setMessage(jsonObject.optString("message"));
		if(jsonObject.has("result")){
			JSONObject jsonResult = jsonObject.optJSONObject("result");
			mResponse.setExps(jsonResult.optInt("exps"));
			mResponse.setPoints(jsonResult.optInt("points"));
			if(jsonResult.has("goldValue")){
				mResponse.setGoldValue(jsonResult.optInt("goldValue"));
			}
			if(jsonResult.has("isTodayHasSigninGift")){
				mResponse.setTodayHasSigninGift(jsonResult.optBoolean("isTodayHasSigninGift"));
			}
			if(jsonResult.has("signinDays")){
				mResponse.setSigninDays(jsonResult.optInt("signinDays"));
			}
			if(jsonResult.has("siginDaysGetGameGift")){
				mResponse.setSiginDaysGetGameGift(jsonResult.optBoolean("siginDaysGetGameGift"));
			}
			
			
		}
	}

	public SigninBean getResponse() {
		return mResponse;
	}
}
