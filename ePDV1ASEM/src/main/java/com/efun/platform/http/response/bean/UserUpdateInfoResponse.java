package com.efun.platform.http.response.bean;

import org.json.JSONObject;

import com.efun.platform.module.account.bean.User;
import com.efun.platform.module.person.bean.UserUpdateBean;
/**
 * 玩家 - 更新玩家信息
 * @author itxuxxey
 *
 */
public class UserUpdateInfoResponse extends BaseResponseBean{
	private static final long serialVersionUID = 1L;
	/**
	 * 更新玩家信息 {@link UserUpdateBean}
	 */
	private UserUpdateBean mResponse ;
	@Override
	public void setValues(Object object) {
		JSONObject jsonObject= (JSONObject) object;
		mResponse = new UserUpdateBean();
		mResponse.setCode(jsonObject.optString("code"));
		mResponse.setMsg(jsonObject.optString("message"));
		User userInfoBean = null;
		JSONObject jsonObjectBean = null;
		if(jsonObject.has("result")){
			userInfoBean = new User();
			jsonObjectBean = jsonObject.optJSONObject("result");
			userInfoBean.setCode(jsonObjectBean.optString("code"));
			userInfoBean.setMessage(jsonObjectBean.optString("message"));
			userInfoBean.setAccessToken(jsonObjectBean.optString("accessToken"));
			userInfoBean.setAccountName(jsonObjectBean.optString("accountName"));
			userInfoBean.setAddress(jsonObjectBean.optString("address"));
			userInfoBean.setAreaDesc(jsonObjectBean.optString("areaDesc"));
			userInfoBean.setAuth_code(jsonObjectBean.optString("auth_code"));
			userInfoBean.setAuthed(jsonObjectBean.optString("authed"));
			userInfoBean.setCreatedTime(jsonObjectBean.optLong("createdTime"));
			userInfoBean.setCurrentExp(jsonObjectBean.optInt("currentExp"));
			userInfoBean.setEmail(jsonObjectBean.optString("email"));
			userInfoBean.setExpPercentage(jsonObjectBean.optInt("expPercentage"));
			userInfoBean.setGameCode(jsonObjectBean.optString("gameCode"));
			userInfoBean.setGoldValue(jsonObjectBean.optInt("goldValue"));
			userInfoBean.setGotExp(jsonObjectBean.optInt("gotExp"));
			userInfoBean.setGotGold(jsonObjectBean.optInt("gotGold"));
			userInfoBean.setIsSendGold(jsonObjectBean.optString("isSendGold"));
			userInfoBean.setIcon(jsonObjectBean.optString("icon"));
			userInfoBean.setIp(jsonObjectBean.optString("ip"));
			userInfoBean.setIsAccept(jsonObjectBean.optString("isAccept"));
			userInfoBean.setIsVip(jsonObjectBean.optString("isVip"));
			userInfoBean.setLevelupExp(jsonObjectBean.optInt("levelupExp"));
			userInfoBean.setLoginType(jsonObjectBean.optString("loginType"));
			userInfoBean.setMemberLevel(jsonObjectBean.optInt("memberLevel"));
			userInfoBean.setExperienceValue(jsonObjectBean.optInt("experienceValue"));
			userInfoBean.setModifiedTime(jsonObjectBean.optLong("modifiedTime"));
			userInfoBean.setPassword(jsonObjectBean.optString("password"));
			userInfoBean.setPrivilege(jsonObjectBean.optString("privilege"));
			userInfoBean.setRango(jsonObjectBean.optString("rango"));
			userInfoBean.setRangoLevel(jsonObjectBean.optString("rangoLevel"));
			userInfoBean.setSex(jsonObjectBean.optString("sex"));
			userInfoBean.setSign(jsonObjectBean.optString("sign"));
			userInfoBean.setThirdId(jsonObjectBean.optString("thirdId"));
			userInfoBean.setTimestamp(jsonObjectBean.optString("timestamp"));
			userInfoBean.setUid(jsonObjectBean.optLong("uid"));
			userInfoBean.setUrlParamString(jsonObjectBean.optString("urlParamString"));
			userInfoBean.setUsername(jsonObjectBean.optString("username"));
			userInfoBean.setPhone(jsonObjectBean.optString("phone"));
			userInfoBean.setBindEmail(jsonObjectBean.optString("bindEmail"));
			
			userInfoBean.setApps(jsonObjectBean.optString("apps"));
			userInfoBean.setArea(jsonObjectBean.optString("area"));
			userInfoBean.setAreaCode(jsonObjectBean.optString("areaCode"));
			userInfoBean.setDeleted(jsonObjectBean.optString("deleted"));
			userInfoBean.setEncryptEmail(jsonObjectBean.optString("encryptEmail"));
			userInfoBean.setFrom(jsonObjectBean.optString("from"));
			userInfoBean.setId(jsonObjectBean.optString("id"));
			userInfoBean.setIsAuthPhone(jsonObjectBean.optString("isAuthPhone"));
			userInfoBean.setIsVipShow(jsonObjectBean.optInt("isVipShow"));
			userInfoBean.setLanguage(jsonObjectBean.optString("language"));
			userInfoBean.setNextGotGold(jsonObjectBean.optInt("nextGotGold"));
			userInfoBean.setPackageVersion(jsonObjectBean.optString("packageVersion"));
			userInfoBean.setTelecomOperator(jsonObjectBean.optString("telecomOperator"));
			userInfoBean.setVipLikes(jsonObjectBean.optInt("vipLikes"));
			userInfoBean.setVipShow(jsonObjectBean.optBoolean("vipShow"));
			userInfoBean.setSettedSecurityAnswers(jsonObjectBean.optBoolean("settedSecurityAnswers"));
			userInfoBean.setBirthday(jsonObjectBean.optString("birthdayStr"));
			userInfoBean.setCity(jsonObjectBean.optString("city"));
			userInfoBean.setLine(jsonObjectBean.optString("line"));
			userInfoBean.setFinished(jsonObjectBean.optBoolean("isFinished"));
			userInfoBean.setGetAward(jsonObjectBean.optBoolean("isGetAward"));
			userInfoBean.setIsAuthEmail(jsonObjectBean.optString("isAuthEmail"));
			userInfoBean.setRealName(jsonObjectBean.optString("realName"));
			userInfoBean.setIdCard(jsonObjectBean.optString("idCard"));
			userInfoBean.setSigninDays(jsonObjectBean.optInt("signinDays"));
			userInfoBean.setSignin(jsonObjectBean.optBoolean("isSignin"));
			userInfoBean.setTodayHasSigninGift(jsonObjectBean.optBoolean("isTodayHasSigninGift"));
			userInfoBean.setMd5Phone(jsonObjectBean.optString("md5Phone"));
			userInfoBean.setGetSigninGift(jsonObjectBean.optBoolean("getSigninGift"));
		}
		mResponse.setUser(userInfoBean);
	}
	public UserUpdateBean getUserUpdateBean() {
		return mResponse;
	}
}
