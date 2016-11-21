package com.efun.platform.widget;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import com.efun.core.component.EfunWebView;
import com.efun.core.tools.EfunStringUtil;
import com.efun.platform.IPlatApplication;
import com.efun.platform.module.account.bean.User;

public class WebViewWithJS extends EfunWebView {
	private User mUser = null;
	public WebViewWithJS(Context context) {
		super(context);
	}

	public WebViewWithJS(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	@Override
	public void jsCallBack(String msg) {
		// TODO Auto-generated method stub
		super.jsCallBack(msg);
		if(!EfunStringUtil.isEmpty(msg)){
			Log.e("efun", "msg:"+msg);
			stringToJson(msg);
		}
		
	}
	
	private void stringToJson(String jsonStr){
		try {
			JSONObject jsonObject = new JSONObject(jsonStr);
			mUser = IPlatApplication.get().getUser();
			if (jsonObject.has("memberInfo")) {
				JSONObject memberJson = jsonObject.optJSONObject("memberInfo");
				if(memberJson.has("dailyLoginInfo")){
					JSONObject dailyJson = memberJson.optJSONObject("dailyLoginInfo");
					if(dailyJson.has("isGetSigninGift")){
						mUser.setGetSigninGift(dailyJson.optBoolean("isGetSigninGift"));
					}
					if(dailyJson.has("isSignin")){
						mUser.setSignin(dailyJson.optBoolean("isSignin"));
					}
					if(dailyJson.has("signinDays")){
						mUser.setSigninDays(dailyJson.optInt("signinDays"));
					}
				}
				if(memberJson.has("memberLevelInfo")){
					JSONObject memJson = memberJson.optJSONObject("memberLevelInfo");
					if(memJson.has("goldValue")){
						mUser.setGoldValue(memJson.optInt("goldValue"));
					}
				}
			}
			if (jsonObject.has("userInfo")) {
				JSONObject userJson = jsonObject.optJSONObject("userInfo");
				if(userJson.has("emailInfo")){
					JSONObject emailJson = userJson.optJSONObject("emailInfo");
					if(emailJson.has("email")){
						mUser.setEmail(emailJson.optString("email"));
					}
					if(emailJson.has("encryptEmail")){
						mUser.setEncryptEmail(emailJson.optString("encryptEmail"));
					}
					if(emailJson.has("isAuthEmail")){
						boolean isBindEmail = emailJson.optBoolean("isAuthEmail");
						if(isBindEmail){
							mUser.setBindEmail("1");
						}else{
							mUser.setBindEmail("0");
						}
					}
				}
				if(userJson.has("phoneInfo")){
					JSONObject phoneJson = userJson.optJSONObject("phoneInfo");
					if(phoneJson.has("areaCode")){
						mUser.setAreaCode(phoneJson.optString("areaCode"));
					}
					if(phoneJson.has("isAuthPhone")){
						boolean isBindPhone = phoneJson.optBoolean("isAuthPhone");
						if(isBindPhone){
							mUser.setIsAuthPhone("1");
						}else{
							mUser.setIsAuthPhone("0");
						}
					}
					if(phoneJson.has("phone")){
						mUser.setPhone(phoneJson.optString("phone"));
					}
					if(phoneJson.has("telecomOperator")){
						mUser.setTelecomOperator(phoneJson.optString("telecomOperator"));
					}
				}
				if(userJson.has("username")){
					mUser.setUsername(userJson.optString("username"));
				}
				if(userJson.has("icon")){
					mUser.setIcon(userJson.optString("icon"));
				}
				if(userJson.has("unreadMessage")){//未阅读信件条数
					int unReadNum = userJson.optInt("unreadMessage");
				}
			}
			if (jsonObject.has("pointInfo")) {
				JSONObject pointJson = jsonObject.optJSONObject("pointInfo");
				if(pointJson.has("sumPoint")){
					IPlatApplication.get().setSumPoint(pointJson.optInt("sumPoint")+"");
				}
			}
			IPlatApplication.get().setUser(mUser);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
}
