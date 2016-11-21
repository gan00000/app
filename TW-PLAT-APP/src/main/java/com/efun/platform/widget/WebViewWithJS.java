package com.efun.platform.widget;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.Log;

import com.efun.core.component.EfunWebView;
import com.efun.core.tools.EfunLogUtil;
import com.efun.core.tools.EfunStringUtil;
import com.efun.platform.IPlatApplication;
import com.efun.platform.AndroidScape.E_string;
import com.efun.platform.http.IPlatController;
import com.efun.platform.http.dao.impl.IPlatformRequest;
import com.efun.platform.http.request.IPlatRequest;
import com.efun.platform.http.request.bean.AccountLoginRequest;
import com.efun.platform.http.request.bean.BaseRequestBean;
import com.efun.platform.http.request.bean.PersonPlaformPointRequest;
import com.efun.platform.http.request.bean.SettingCheckRequest;
import com.efun.platform.http.response.bean.AccountResponse;
import com.efun.platform.http.response.bean.BaseResponseBean;
import com.efun.platform.http.response.bean.PersonPlatformPointResponse;
import com.efun.platform.http.response.bean.SettingCheckResponse;
import com.efun.platform.module.account.bean.ResultBean;
import com.efun.platform.module.account.bean.User;
import com.efun.platform.module.base.impl.OnEfunItemClickListener;
import com.efun.platform.module.base.impl.OnRequestListener;
import com.efun.platform.module.person.bean.PlatformPointBean;
import com.efun.platform.module.utils.AppUtils;
import com.efun.platform.module.utils.UserUtils;
import com.efun.platform.module.utils.ViewUtils;
import com.efun.platform.service.IPlatService;
import com.efun.platform.status.IPlatStatus;
import com.efun.platform.utils.Const;
import com.efun.platform.utils.StoreUtil;
import com.efun.platform.utils.Const.HttpParam;

public class WebViewWithJS extends EfunWebView {
	private IPlatController mController;
	private Context mContext;
	public WebViewWithJS(Context context) {
		super(context);
		mContext = context;
	}

	public WebViewWithJS(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
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
			User mUser = IPlatApplication.get().getUser();
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
					if(dailyJson.has("siginDaysGetGameGift")){
						mUser.setSiginDaysGetGameGift(dailyJson.optBoolean("siginDaysGetGameGift"));
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
				
				String pointInfoStr = jsonObject.optString("pointInfo");
				if(!EfunStringUtil.isEmpty(pointInfoStr) && pointInfoStr.equals("update_pointInfo")){
					request();	
				}
			}
			IPlatApplication.get().setUser(mUser);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	private void request(){
		mController = new IPlatController(mContext);
		mController.executeAll(null, createIPlatRequests(needRequestBean()), new OnRequestListener() {
			@Override
			public void onSuccess(int requestType, BaseResponseBean baseResponse) {
				if (requestType == IPlatformRequest.REQ_USER_PLATFORM_POINT) {
					PersonPlatformPointResponse response = (PersonPlatformPointResponse) baseResponse;
					PlatformPointBean mPointBean = response.getPlatformPointBean();
					if (mPointBean.getCode().equals(HttpParam.RESULT_0000)) {
						IPlatApplication.get().setSumPoint(mPointBean.getSumPoint());
						IPlatApplication.get().setSumPoint(mPointBean.getSumPoint());
					}
				}
			}
			
			@Override
			public void onFailure(int requestType) {
				if (requestType == IPlatformRequest.REQ_USER_PLATFORM_POINT) {
					IPlatApplication.get().setSumPoint("0");
				}
			}

			@Override
			public void onTimeout(int requestType) {
				if (requestType == IPlatformRequest.REQ_USER_PLATFORM_POINT) {
					IPlatApplication.get().setSumPoint("0");
				}
			}

			@Override
			public void onNoData(int requestType, String noDataNotify) {
				if (requestType == IPlatformRequest.REQ_USER_PLATFORM_POINT) {
					IPlatApplication.get().setSumPoint("0");
				}
			}
		});
	}
	
	private IPlatRequest[] createIPlatRequests(BaseRequestBean[] requests){
		IPlatRequest[] mRequests = new IPlatRequest[requests.length];
		for (int i = 0; i < mRequests.length; i++) {
			mRequests[i] = new IPlatRequest(mContext).setRequestBean(requests[i]);
		}
		return mRequests;
	}
	private BaseRequestBean[] needRequestBean(){
		if (IPlatApplication.get().getUser() != null) {
			PersonPlaformPointRequest request = new PersonPlaformPointRequest(
					IPlatApplication.get().getUser().getUid(), "false",
					IPlatApplication.get().getUser().getSign(),
					IPlatApplication.get().getUser().getTimestamp());
			request.setReqType(IPlatformRequest.REQ_USER_PLATFORM_POINT);
			return new BaseRequestBean[]{request};
		}else {
			return null;
		}
	}
	
	
}
