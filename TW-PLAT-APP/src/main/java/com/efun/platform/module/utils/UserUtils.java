package com.efun.platform.module.utils;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.efun.platform.IPlatApplication;
import com.efun.platform.http.dao.impl.IPlatformRequest;
import com.efun.platform.http.request.bean.AccountLoginRequest;
import com.efun.platform.module.account.activity.LoginActivity;
import com.efun.platform.module.account.bean.User;
import com.efun.platform.module.base.impl.OnLoginFinishListener;
import com.efun.platform.module.base.impl.OnToastClickListener;
import com.efun.platform.utils.Const;
import com.efun.platform.utils.Const.HttpParam;
import com.efun.platform.utils.Const.Key;
import com.efun.platform.utils.StoreUtil;

public class UserUtils {
	/**
	 * 是否已经登陆
	 * @return
	 */
	public static boolean isLogin(){
		if(IPlatApplication.get().getUser()==null){
			return false;
		}
		return true;
	}
	
	/**
	 * 登出
	 */
	public static void logout(Context context){
		IPlatApplication.get().setUser(null);
//		Store.clearByClazz(context, AccountResponse.class);
//		Store.clearByClazz(context, AccountLoginRequest.class);
		String[] keys = new String[]{"username","password"};
		StoreUtil.saveValues(context, StoreUtil.Param_file_name, keys,new String[]{"",""});
		StoreUtil.clearValueByKey(context, StoreUtil.Json_file_name,StoreUtil.Json_key_account_response);
		StoreUtil.clearValue(context, StoreUtil.Request_file_name_login_request);
	}
	
	/**
	 * 需要登陆方可操作
	 * @param context
	 */
	public static void needLogin(Context context,OnLoginFinishListener onLoginFinishListener){
		if(!isLogin()){
			IPlatApplication.get().setOnEfunListener(onLoginFinishListener);
			Intent it = new Intent(context,LoginActivity.class);
			context.startActivity(it);
		}else{
			onLoginFinishListener.loginCompleted(false);
		}
	}
	
	/**
	 * 需要登陆方可操作
	 * @param context
	 */
	public static void needLogin(final Context context,String message,final OnLoginFinishListener onLoginFinishListener){
		if(!isLogin()){
			ViewUtils.createLoginWaitingDialog(context, message, new OnToastClickListener() {
				@Override
				public void onClick() {
					IPlatApplication.get().setOnEfunListener(onLoginFinishListener);
					Intent it = new Intent(context,LoginActivity.class);
					context.startActivity(it);
				}
			}, new Handler());
		}else{
			onLoginFinishListener.loginCompleted(false);
		}
	}
	
	
	
	
	/**
	 * 需要登陆方可操作
	 * @param context
	 */
	public static void changeUser(Context context,OnLoginFinishListener onLoginFinishListener){
		IPlatApplication.get().setOnEfunListener(onLoginFinishListener);
		Intent it = new Intent(context,LoginActivity.class);
//		Store.saveHistoryLogin(context, new String[]{"username","password"}, new String[]{"",""});
		
		String[] keys = new String[]{"username","password"};
		StoreUtil.saveValues(context, StoreUtil.Param_file_name, keys,new String[]{"",""});
		context.startActivity(it);
	}
	
	/**
	 * Tab中需要登陆方可操作
	 * @param context
	 */
	public static void needLoginInTag(Context context,int requestCode,int resultCode,OnLoginFinishListener onLoginFinishListener){
		if(!isLogin()){
			IPlatApplication.get().setOnEfunListener(onLoginFinishListener);
			Intent it = new Intent(context,LoginActivity.class);
			Bundle bundle = new Bundle();
			bundle.putBoolean(Key.BOOLEAN_KEY, true);
			bundle.putInt(Key.INTEGER_KEY, resultCode);
			it.putExtra(Const.DATA_KEY, bundle);
			((Activity)context).startActivityForResult(it, requestCode);
		}else{
			onLoginFinishListener.loginCompleted(false);
		}
	}
	
	/**
	 * 
	 * @param context
	 */
	public static void initUser(Context context){
//		String date = Store.getResponseByClazz(context, AccountResponse.class);
		String date = StoreUtil.getValue(context, StoreUtil.Json_file_name, StoreUtil.Json_key_account_response);
		if(!TextUtils.isEmpty(date)){
			JSONObject jsonObject;
			try {
				jsonObject = new JSONObject(date);
				User userInfoBean = new User();
				userInfoBean.setAccessToken(jsonObject.optString("accessToken"));
				userInfoBean.setAccountName(jsonObject.optString("accountName"));
				userInfoBean.setAddress(jsonObject.optString("address"));
				userInfoBean.setAreaDesc(jsonObject.optString("areaDesc"));
				userInfoBean.setAuth_code(jsonObject.optString("auth_code"));
				userInfoBean.setAuthed(jsonObject.optString("authed"));
				userInfoBean.setCreatedTime(jsonObject.optLong("createdTime"));
				userInfoBean.setCurrentExp(jsonObject.optInt("currentExp"));
				userInfoBean.setEmail(jsonObject.optString("email"));
				userInfoBean.setExpPercentage(jsonObject.optInt("expPercentage"));
				userInfoBean.setGameCode(jsonObject.optString("gameCode"));
				userInfoBean.setGoldValue(jsonObject.optInt("goldValue"));
				userInfoBean.setGotExp(jsonObject.optInt("gotExp"));
				userInfoBean.setGotGold(jsonObject.optInt("gotGold"));
				userInfoBean.setIsSendGold(jsonObject.optString("isSendGold"));
				userInfoBean.setIcon(jsonObject.optString("icon"));
				userInfoBean.setIp(jsonObject.optString("ip"));
				userInfoBean.setIsAccept(jsonObject.optString("isAccept"));
				userInfoBean.setIsVip(jsonObject.optString("isVip"));
				userInfoBean.setLevelupExp(jsonObject.optInt("levelupExp"));
				userInfoBean.setLoginType(jsonObject.optString("loginType"));
				userInfoBean.setMemberLevel(jsonObject.optInt("memberLevel"));
				userInfoBean.setModifiedTime(jsonObject.optLong("modifiedTime"));
				userInfoBean.setPassword(jsonObject.optString("password"));
				userInfoBean.setPrivilege(jsonObject.optString("privilege"));
				userInfoBean.setRango(jsonObject.optString("rango"));
				userInfoBean.setRangoLevel(jsonObject.optString("rangoLevel"));
				userInfoBean.setSex(jsonObject.optString("sex"));
				userInfoBean.setSign(jsonObject.optString("sign"));
				userInfoBean.setThirdId(jsonObject.optString("thirdId"));
				userInfoBean.setTimestamp(jsonObject.optString("timestamp"));
				userInfoBean.setUid(jsonObject.optLong("uid"));
				userInfoBean.setUrlParamString(jsonObject.optString("urlParamString"));
				userInfoBean.setUsername(jsonObject.optString("username"));
				userInfoBean.setPhone(jsonObject.optString("phone"));
				userInfoBean.setBindEmail(jsonObject.optString("bindEmail"));
				userInfoBean.setApps(jsonObject.optString("apps"));
				userInfoBean.setArea(jsonObject.optString("area"));
				userInfoBean.setAreaCode(jsonObject.optString("areaCode"));
				userInfoBean.setDeleted(jsonObject.optString("deleted"));
				userInfoBean.setEncryptEmail(jsonObject.optString("encryptEmail"));
				userInfoBean.setFrom(jsonObject.optString("from"));
				userInfoBean.setId(jsonObject.optString("id"));
				userInfoBean.setIsAuthPhone(jsonObject.optString("isAuthPhone"));
				userInfoBean.setIsVipShow(jsonObject.optInt("isVipShow"));
				userInfoBean.setLanguage(jsonObject.optString("language"));
				userInfoBean.setNextGotGold(jsonObject.optInt("nextGotGold"));
				userInfoBean.setPackageVersion(jsonObject.optString("packageVersion"));
				userInfoBean.setTelecomOperator(jsonObject.optString("telecomOperator"));
				userInfoBean.setVipLikes(jsonObject.optInt("vipLikes"));
				userInfoBean.setVipShow(jsonObject.optBoolean("vipShow"));
				userInfoBean.setSettedSecurityAnswers(jsonObject.optBoolean("settedSecurityAnswers"));
				userInfoBean.setBirthday(jsonObject.optString("birthdayStr"));
				userInfoBean.setCity(jsonObject.optString("city"));
				userInfoBean.setLine(jsonObject.optString("line"));
				userInfoBean.setFinished(jsonObject.optBoolean("isFinished"));
				userInfoBean.setGetAward(jsonObject.optBoolean("isGetAward"));
				userInfoBean.setIsAuthEmail(jsonObject.optString("isAuthEmail"));
				userInfoBean.setRealName(jsonObject.optString("realName"));
				userInfoBean.setIdCard(jsonObject.optString("idCard"));
				userInfoBean.setSigninDays(jsonObject.optInt("signinDays"));
				userInfoBean.setSignin(jsonObject.optBoolean("isSignin"));
				userInfoBean.setTodayHasSigninGift(jsonObject.optBoolean("isTodayHasSigninGift"));
				userInfoBean.setMd5Phone(jsonObject.optString("md5Phone"));
				userInfoBean.setGetSigninGift(jsonObject.optBoolean("getSigninGift"));
				userInfoBean.setSiginDaysGetGameGift(jsonObject.optBoolean("siginDaysGetGameGift"));
				IPlatApplication.get().setPfLType(jsonObject.optString("loginType"));
				IPlatApplication.get().setPfAName(jsonObject.optString("accountName"));
				IPlatApplication.get().setUser(userInfoBean);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 上次登录信息请求类
	 * @param context
	 * @return
	 */
	public static AccountLoginRequest createRequest(Context context){
		String[] keys = new String[]{"uid","username","password","loginType","thirdId","apps"};
//		HashMap<String, String > paramsMap = Store.createValuesByClazz(context, keys, AccountLoginRequest.class);
		HashMap<String, String > paramsMap = StoreUtil.getValues(context, keys, StoreUtil.Request_file_name_login_request);
		IPlatApplication.get().setPfLType(paramsMap.get("loginType"));
		IPlatApplication.get().setPfAName(paramsMap.get("username").trim());
		IPlatApplication.get().setPfPCode(paramsMap.get("password").trim());
		Log.e("efun", "loginType:"+paramsMap.get("loginType"));
		AccountLoginRequest request = new AccountLoginRequest(
				context, 
				paramsMap.get("username").trim());
		request.setPassword(paramsMap.get("password").trim());
		request.setLoginType(paramsMap.get("loginType"));
		request.setThirdId(paramsMap.get("thirdId"));
		request.setApps(paramsMap.get("apps"));
		request.setPfArea(HttpParam.PLATFORM_AREA);
		request.setDevice(HttpParam.PHONE);
		request.setReqType(IPlatformRequest.REQ_ACCOUNT_LOGIN);
		return request;
		
	}
	
}
