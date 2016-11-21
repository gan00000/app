package com.efun.platform.http.response.bean;

import org.json.JSONObject;

import android.text.TextUtils;

import com.efun.platform.http.dao.impl.IPlatformRequest;
import com.efun.platform.http.request.bean.AccountLoginRequest;
import com.efun.platform.http.request.bean.AccountRegisterRequest;
import com.efun.platform.http.request.bean.AccountThirdLoginRequest;
import com.efun.platform.module.account.bean.ResultBean;
import com.efun.platform.module.account.bean.User;
import com.efun.platform.utils.Const.HttpParam;
import com.efun.platform.utils.Const.LoginPlatform;
import com.efun.platform.utils.StoreUtil;
/**
 * 账号
 * @author itxuxxey
 *
 */
public class AccountResponse extends BaseResponseBean {
	private static final long serialVersionUID = 1L;
	/**
	 * 玩家信息 {@link User}
	 */
	private User userInfoBean;
	@Override
	public void setValues(Object object) {
		JSONObject jsonObject = (JSONObject) object;
		userInfoBean = new User();
		userInfoBean.setCode(jsonObject.optString("code"));
		userInfoBean.setMessage(jsonObject.optString("message"));

		int reqType = getRequestBean().getReqType();

		switch (reqType){
			case IPlatformRequest.REQ_ACCOUNT_LOGIN:
			case IPlatformRequest.REQ_ACCOUNT_REGISTER:
				if (userInfoBean.getCode().equals(HttpParam.RESULT_1000)){
					//登陆或者注册不成功
					processLoginSuccess(jsonObject);
				}
			case IPlatformRequest.REQ_ACCOUNT_THIRD_LOGIN:
				if (userInfoBean.getCode().equals(HttpParam.RESULT_1000) || userInfoBean.getCode().equals(HttpParam.RESULT_1006)) {
					//三方登陆成功
					processLoginSuccess(jsonObject);
				}
			default:
		}
	}
	

	public ResultBean getResultBean() {
		return userInfoBean;
	}

	private void processLoginSuccess(JSONObject jsonObject){
		{
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
			userInfoBean.setExperienceValue(jsonObject.optInt("experienceValue"));
			userInfoBean.setModifiedTime(jsonObject.optLong("modifiedTime"));
			userInfoBean.setPassword(jsonObject.optString("password"));
			userInfoBean.setPrivilege(jsonObject.optString("privilege"));
			userInfoBean.setRango(jsonObject.optString("rango"));
			userInfoBean.setRangoLevel(jsonObject.optString("rangoLevel"));
			userInfoBean.setSex(jsonObject.optString("sex"));
			userInfoBean.setSign(jsonObject.optString("sign"));
			userInfoBean.setThirdId(jsonObject.optString("thirdId"));
			userInfoBean.setTimestamp(jsonObject.optString("timestamp"));
			long user_id = jsonObject.optLong("uid",0) ;
			if (user_id == 0){
				try {
					user_id = Long.parseLong(jsonObject.optString("userid", "0"));
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
			}
			userInfoBean.setUid(user_id);
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


			int reqType = getRequestBean().getReqType();
			if(reqType==IPlatformRequest.REQ_ACCOUNT_LOGIN
					|| reqType==IPlatformRequest.REQ_ACCOUNT_REGISTER || reqType==IPlatformRequest.REQ_ACCOUNT_THIRD_LOGIN){

				//PushUtils.initPushWhenLog(getContext());

				String mUid = "";
				String mUsername= "";
				String mPassword= "";
				String mLoginType= "";
				String mThirdId= "";
				String mApps = "";
				if(reqType==IPlatformRequest.REQ_ACCOUNT_LOGIN){
					AccountLoginRequest bean = (AccountLoginRequest)getRequestBean();
					mUid = userInfoBean.getUid();
					if(TextUtils.isEmpty(bean.getUsername())){
						mUsername = "";
					}else{
						mUsername= bean.getUsername().trim();
					}
					if(TextUtils.isEmpty(bean.getPassword())){
						mPassword = "";
					}else{
						mPassword= bean.getPassword().trim();
					}
					mLoginType= bean.getLoginType();
					mThirdId= bean.getThirdId();
					mApps = bean.getApps();
				}else if(reqType==IPlatformRequest.REQ_ACCOUNT_THIRD_LOGIN){
					AccountThirdLoginRequest bean = (AccountThirdLoginRequest)getRequestBean();
					mUid = userInfoBean.getUid();
					mLoginType= bean.getLoginType();
					mThirdId= bean.getThirdId();
					mApps = bean.getApps();
				}else{
					AccountRegisterRequest bean = (AccountRegisterRequest)getRequestBean();
					mUid = userInfoBean.getUid();
					mUsername= bean.getUserName().trim();
					mPassword= bean.getPassword().trim();
					mLoginType= LoginPlatform.EFUN;
					mThirdId= "";
					mApps = "";
				}

				if(mLoginType.equals(LoginPlatform.EFUN)){
					//Store.saveHistoryLogin(getContext(), new String[]{"username","password"}, new String[]{mUsername,mPassword});
					String[] keys = new String[]{"username","password"};
					StoreUtil.saveValues(getContext(), StoreUtil.Param_file_name, keys, new String[]{mUsername,mPassword});
				}

				String[] keys = new String[]{"uid"};
				String[] values = new String[]{mUid};
//				boolean flag = Store.changeNotify(getContext(),
//						keys,values,AccountResponse.class);

				boolean flag = StoreUtil.valueChangeNotify(getContext(),
						StoreUtil.Json_file_name,
						StoreUtil.Json_key_account_response,
						keys,
						values);

				if(flag){
//					Store.saveResponseByClazz(getContext(), AccountResponse.class,getResponseJson2String());
					StoreUtil.saveValue(getContext(),
							StoreUtil.Json_file_name,
							StoreUtil.Json_key_account_response,
							getResponseJson2String());
					keys = new String[]{"uid","username","password","loginType","thirdId","apps"};
					values = new String[]{mUid,mUsername,mPassword,mLoginType,mThirdId,mApps};
//					Store.saveRequestByClazz(getContext(),
//							keys,
//							values,
//							AccountLoginRequest.class);
					StoreUtil.saveValues(getContext(), StoreUtil.Request_file_name_login_request, keys, values);
				}
			}
		}
	}
}
