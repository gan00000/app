package com.efun.platform.http.dao.impl;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.text.TextUtils;

import com.efun.core.http.HttpRequest;
import com.efun.core.tools.EfunLogUtil;
import com.efun.core.tools.EfunResourceUtil;
import com.efun.jqzs.sm.R;
import com.efun.platform.AndroidScape.E_string;
import com.efun.platform.IPlatApplication;
import com.efun.platform.http.request.bean.AccountFindPwdByPhoneRequest;
import com.efun.platform.http.request.bean.AccountFindPwdRequest;
import com.efun.platform.http.request.bean.AccountGetUserFBUidsByUidRequest;
import com.efun.platform.http.request.bean.AccountLoginRequest;
import com.efun.platform.http.request.bean.AccountRegisterRequest;
import com.efun.platform.http.request.bean.AccountResetPwdRequest;
import com.efun.platform.http.request.bean.AccountThirdLoginRequest;
import com.efun.platform.http.request.bean.AccountUpdateRequest;
import com.efun.platform.http.request.bean.ActivityListRequest;
import com.efun.platform.http.request.bean.BaseRequestBean;
import com.efun.platform.http.request.bean.CsAskRequest;
import com.efun.platform.http.request.bean.GiftSelfListRequest;
import com.efun.platform.http.request.bean.PersonRefreshHeadIconRequest;
import com.efun.platform.http.request.bean.SettingCheckRequest;
import com.efun.platform.http.request.bean.StatLogInfoRequest;
import com.efun.platform.http.request.bean.StatPushInfoRequest;
import com.efun.platform.http.request.bean.SummaryHomeRequest;
import com.efun.platform.http.request.bean.SummaryListRequest;
import com.efun.platform.http.request.bean.SummaryPraiseRequest;
import com.efun.platform.http.request.bean.UserUpdateHeaderRequest;
import com.efun.platform.http.request.bean.UserUpdateInfoRequest;
import com.efun.platform.http.request.bean.WelfareListRequest;
import com.efun.platform.http.response.bean.AccountGetUserFBUidsByUidResponse;
import com.efun.platform.http.response.bean.AccountResponse;
import com.efun.platform.http.response.bean.AccountUpdateResponse;
import com.efun.platform.http.response.bean.ActivityListResponse;
import com.efun.platform.http.response.bean.BaseResponseBean;
import com.efun.platform.http.response.bean.CsAskResponse;
import com.efun.platform.http.response.bean.GiftSelfListResponse;
import com.efun.platform.http.response.bean.PersonRefreshHeadIconResponse;
import com.efun.platform.http.response.bean.PlatformConfigInfosResponse;
import com.efun.platform.http.response.bean.PlayerAreaResponse;
import com.efun.platform.http.response.bean.SettingCheckResponse;
import com.efun.platform.http.response.bean.StatLogInfoResponse;
import com.efun.platform.http.response.bean.SummaryHomeResponse;
import com.efun.platform.http.response.bean.SummaryListResponse;
import com.efun.platform.http.response.bean.SummaryPraiseResponse;
import com.efun.platform.http.response.bean.UserUpdateHeaderResponse;
import com.efun.platform.http.response.bean.UserUpdateInfoResponse;
import com.efun.platform.http.response.bean.WelfareListResponse;
import com.efun.platform.module.utils.UrlUtils;
import com.efun.platform.utils.Const;
import com.efun.platform.utils.StatLogInfoUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * {@link IPlatformRequest} 实现类
 *
 * @author itxuxxey
 */
public class IPlatformImpl implements IPlatformRequest {
    /**
     * URL - 平台域名
     */
    private String mUrl;
    /**
     * URL - 游戏域名
     */
    private String mGameUrl;
    /**
     * URL - fb域名
     */
    private String mFbUrl;
    /**
     * URL - 查询用户信息域名
     */
    private String mLoginUrl;
    /**
     * URL - 图片上传域名
     */
    private String mImgUrl;
    /**
     * URL - Pay域名
     */
    private String mPayUrl;
    /**
     * URL - 统计域名
     */
    private String mStatUrl;
    /**
     * URL - CDN域名
     */
    private String mCDNUrl;
    /**
     * Method - 接口方法名称
     */
    private String mMethod;
    /**
     * {@link Context} 上下文
     */
    private Context mContext;

    public IPlatformImpl(Context context) {
        mUrl = IPlatApplication.get().getIPlatUrlByKey(UrlUtils.PLATFORM_PRE_KEY);
        mGameUrl = IPlatApplication.get().getIPlatUrlByKey(UrlUtils.GAME_PRE_KEY);
        mFbUrl = IPlatApplication.get().getIPlatUrlByKey(UrlUtils.FB_PRE_KEY);
        mLoginUrl = IPlatApplication.get().getIPlatUrlByKey(UrlUtils.LOGIN_PRE_KEY);
        mImgUrl = IPlatApplication.get().getIPlatUrlByKey(UrlUtils.IMG_UPLOAD_PRE_KEY);
        mPayUrl = IPlatApplication.get().getIPlatUrlByKey(UrlUtils.PAY_PRE_KEY);
        mStatUrl = EfunResourceUtil.findStringByName(context, "efun_pd_url_stat_data_base");
        mCDNUrl = EfunResourceUtil.findStringByName(context, "efun_pd_sdk_cdn_download_base");
        mUrl = checkUrl(mUrl);
        mGameUrl = checkUrl(mGameUrl);
        mFbUrl = checkUrl(mFbUrl);
        mLoginUrl = checkUrl(mLoginUrl);
        mImgUrl = checkUrl(mImgUrl);
        mPayUrl = checkUrl(mPayUrl);
        mStatUrl = checkUrl(mStatUrl);
        mCDNUrl = checkUrl(mCDNUrl);
        mContext = context;
    }

    /**
     * 校验链接是否标准
     *
     * @param url
     * @return
     */
    private String checkUrl(String url) {
        if (!url.endsWith("/")) {
            url = url + "/";
        }
        return url;
    }

    /**
     * 校验Method是否标准
     *
     * @param method
     */
    private void checkMethod(String method) {
        if (!method.endsWith(".shtml")) {
            throw new IllegalAccessError("Method Illeagel");
        }
    }

    @Override
    public BaseResponseBean summaryHome(BaseRequestBean request) {
        mMethod = mContext.getString(E_string.efun_pd_method_summary_home);
        checkMethod(mMethod);
//		List<NameValuePair> params = request.buildPostParams(SummaryHomeRequest.class);
//		String responseStr = EfunHttpUtil.efunExecutePostRequest(mUrl + mMethod, params);
        Map<String, String> params = request.buildPostParamsAfter6(SummaryHomeRequest.class);
        String responseStr = HttpRequest.post(mUrl + mMethod, params);
        SummaryHomeResponse response = new SummaryHomeResponse();
        response.setContext(mContext);
        response.pares(request, responseStr, false);
        return response;
    }

    @Override
    public BaseResponseBean summaryList(BaseRequestBean request) {
        mMethod = mContext.getString(E_string.efun_pd_method_summary_list);
        checkMethod(mMethod);
//		List<NameValuePair> params = request.buildPostParams(SummaryListRequest.class);
//		String responseStr = EfunHttpUtil.efunExecutePostRequest(mUrl + mMethod, params);
        Map<String, String> params = request.buildPostParamsAfter6(SummaryListRequest.class);
        String responseStr = HttpRequest.post(mUrl + mMethod, params);
        SummaryListResponse response = new SummaryListResponse();
        response.setContext(mContext);
        response.pares(request, responseStr, false);
        return response;
    }

/*	@Override
    public BaseResponseBean login(BaseRequestBean request) {
		mMethod = mContext.getString(E_string.efun_pd_method_login);
		checkMethod(mMethod);
//		List<NameValuePair> params = request.buildPostParams(AccountLoginRequest.class);
//		String responseStr = EfunHttpUtil.efunExecutePostRequest(mUrl + mMethod, params);
		Map<String,String> params = request.buildPostParamsAfter6(AccountLoginRequest.class);
		String responseStr = HttpRequest.post(mUrl + mMethod, params);
		AccountResponse response = new AccountResponse();
		response.setContext(mContext);
		response.setRequestBean(request);
		response.pares(request, responseStr, false);
		return response;
	}*/


    @Override
    public BaseResponseBean login(BaseRequestBean request) {
        mMethod = mContext.getString(R.string.interface_login);
        checkMethod(mMethod);
//		List<NameValuePair> params = request.buildPostParams(AccountLoginRequest.class);
//		String responseStr = EfunHttpUtil.efunExecutePostRequest(mUrl + mMethod, params);
        Map<String, String> params = request.buildPostParamsAfter6(AccountLoginRequest.class);
        String loginUrl = IPlatApplication.get().getIPlatUrlByKey(UrlUtils.LOGIN_PRE_KEY);
        String loginSpaUrl = IPlatApplication.get().getIPlatUrlByKey(UrlUtils.LOGIN_SPA_KEY);

        if (TextUtils.isEmpty(loginUrl)) {
            loginUrl = mContext.getString(R.string.eee_login_url);
        }
        String responseStr = HttpRequest.postIn2Url(loginUrl + mMethod, loginSpaUrl + mMethod, params);

        if (!TextUtils.isEmpty(responseStr)) {
            try {
                JSONObject loginJson = new JSONObject(responseStr);
                if (loginJson != null) {
                    if (loginJson.optString("code", "").equals(Const.HttpParam.RESULT_1000)) {
                        String userid = loginJson.optString("userid", "");
                        String timestamp = loginJson.optString("timestamp", "");
                        String sign = loginJson.optString("sign", "");
                        String userToken = loginJson.optString("userToken", "");

                        Map<String, String> getMemberInfo = new HashMap<>();
                        getMemberInfo.put("token", userToken);
                        getMemberInfo.put("language", ((AccountLoginRequest) request).getLanguage());
                        getMemberInfo.put("platform", Const.HttpParam.PLATFORM_AREA);
                        getMemberInfo.put("userid", userid);
                        getMemberInfo.put("timestamp", timestamp);
                        getMemberInfo.put("sign", sign);
                        mMethod = mContext.getString(R.string.member_getMemberInfoByToken);
                        responseStr = HttpRequest.postIn2Url(mUrl + mMethod, mUrl + mMethod, getMemberInfo);

                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        AccountResponse response = new AccountResponse();
        response.setContext(mContext);
        response.setRequestBean(request);
        response.pares(request, responseStr, false);
        return response;
    }

    @Override
    public BaseResponseBean update(BaseRequestBean request) {
        mMethod = mContext.getString(E_string.efun_pd_method_login);
        checkMethod(mMethod);
//		List<NameValuePair> params = request.buildPostParams(AccountUpdateRequest.class);
//		String responseStr = EfunHttpUtil.efunExecutePostRequest(mUrl + mMethod, params);
        Map<String, String> params = request.buildPostParamsAfter6(AccountUpdateRequest.class);
        String responseStr = HttpRequest.post(mUrl + mMethod, params);
        AccountUpdateResponse response = new AccountUpdateResponse();
        response.setContext(mContext);
        response.setRequestBean(request);
        response.pares(request, responseStr, false);
        return response;
    }

    @Override
    public BaseResponseBean register(BaseRequestBean request) {
        mMethod = mContext.getString(R.string.interface_third_register);
        checkMethod(mMethod);
//		List<NameValuePair> params = request.buildPostParams(AccountRegisterRequest.class);
//		String responseStr = EfunHttpUtil.efunExecutePostRequest(mUrl + mMethod, params);
        Map<String, String> params = request.buildPostParamsAfter6(AccountRegisterRequest.class);


        String loginUrl = IPlatApplication.get().getIPlatUrlByKey(UrlUtils.LOGIN_PRE_KEY);
        String loginSpaUrl = IPlatApplication.get().getIPlatUrlByKey(UrlUtils.LOGIN_SPA_KEY);

        if (TextUtils.isEmpty(loginUrl)) {
            loginUrl = mContext.getString(R.string.eee_login_url);
        }
        String responseStr = HttpRequest.postIn2Url(loginUrl + mMethod, loginSpaUrl + mMethod, params);


        if (!TextUtils.isEmpty(responseStr)) {
            try {
                JSONObject loginJson = new JSONObject(responseStr);
                if (loginJson != null) {
                    if (loginJson.optString("code", "").equals(Const.HttpParam.RESULT_1000)) {
                        String userid = loginJson.optString("userid", "");
                        String timestamp = loginJson.optString("timestamp", "");
                        String sign = loginJson.optString("sign", "");
                        String userToken = loginJson.optString("userToken", "");

                        Map<String, String> getMemberInfo = new HashMap<>();
                        getMemberInfo.put("token", userToken);
                        getMemberInfo.put("language", ((AccountRegisterRequest) request).getLanguage());
                        getMemberInfo.put("platform", Const.HttpParam.PLATFORM_AREA);
                        getMemberInfo.put("userid", userid);
                        getMemberInfo.put("timestamp", timestamp);
                        getMemberInfo.put("sign", sign);
                        mMethod = mContext.getString(R.string.member_getMemberInfoByToken);
                        responseStr = HttpRequest.postIn2Url(mUrl + mMethod, mUrl + mMethod, getMemberInfo);

                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

//        String responseStr = HttpRequest.post(mUrl + mMethod, params);
        AccountResponse response = new AccountResponse();
        response.setContext(mContext);
        response.pares(request, responseStr, false);
        return response;
    }

    @Override
    public BaseResponseBean forgetPwd(BaseRequestBean request) {
        mMethod = mContext.getString(E_string.efun_pd_method_forget_pwd);
        checkMethod(mMethod);
//		List<NameValuePair> params = request.buildPostParams(AccountFindPwdRequest.class);
//		String responseStr = EfunHttpUtil.efunExecutePostRequest(mUrl + mMethod, params);
        Map<String, String> params = request.buildPostParamsAfter6(AccountFindPwdRequest.class);
        String responseStr = HttpRequest.post(mUrl + mMethod, params);
        AccountResponse response = new AccountResponse();
        response.setContext(mContext);
        response.pares(request, responseStr, false);
        return response;
    }

    @Override
    public BaseResponseBean findPwdByPhone(BaseRequestBean request) {
        mMethod = mContext.getString(E_string.efun_pd_method_forget_pwd_by_phone);
        checkMethod(mMethod);
//		List<NameValuePair> params = request.buildPostParams(AccountFindPwdRequest.class);
//		String responseStr = EfunHttpUtil.efunExecutePostRequest(mUrl + mMethod, params);
        Map<String, String> params = request.buildPostParamsAfter6(AccountFindPwdByPhoneRequest.class);
        String responseStr = HttpRequest.post(mUrl + mMethod, params);
        AccountResponse response = new AccountResponse();
        response.setContext(mContext);
        response.pares(request, responseStr, false);
        return response;
    }

    @Override
    public BaseResponseBean resetPwd(BaseRequestBean request) {
        mMethod = mContext.getString(E_string.efun_pd_method_reset_pwd);
        checkMethod(mMethod);
//		List<NameValuePair> params = request.buildPostParams(AccountResetPwdRequest.class);
//		String responseStr = EfunHttpUtil.efunExecutePostRequest(mUrl + mMethod, params);
        Map<String, String> params = request.buildPostParamsAfter6(AccountResetPwdRequest.class);
        String responseStr = HttpRequest.post(mUrl + mMethod, params);
        AccountResponse response = new AccountResponse();
        response.setContext(mContext);
        response.pares(request, responseStr, false);
        return response;
    }

    @Override
    public BaseResponseBean userUpdateHeader(BaseRequestBean request) {
        mMethod = mContext.getString(E_string.efun_pd_method_user_header);
        checkMethod(mMethod);
//		List<NameValuePair> params = request.buildPostParams(UserUpdateHeaderRequest.class);
//		String responseStr = EfunHttpUtil.efunExecutePostRequest(mImgUrl + mMethod, params);
        Map<String, String> params = request.buildPostParamsAfter6(UserUpdateHeaderRequest.class);
        String responseStr = HttpRequest.post(mImgUrl + mMethod, params);
        UserUpdateHeaderResponse response = new UserUpdateHeaderResponse();
        response.setContext(mContext);
        response.pares(request, responseStr, false);
        return response;
    }

    @Override
    public BaseResponseBean userUpdateInfo(BaseRequestBean request) {
        mMethod = mContext.getString(E_string.efun_pd_method_user_update);
        checkMethod(mMethod);
//		List<NameValuePair> params = request.buildPostParams(UserUpdateInfoRequest.class);
//		String responseStr = EfunHttpUtil.efunExecutePostRequest(mUrl + mMethod, params);
        Map<String, String> params = request.buildPostParamsAfter6(UserUpdateInfoRequest.class);
        String responseStr = HttpRequest.post(mUrl + mMethod, params);
        UserUpdateInfoResponse response = new UserUpdateInfoResponse();
        response.setContext(mContext);
        response.pares(request, responseStr, false);
        return response;
    }

    @Override
    public BaseResponseBean checkVersion(BaseRequestBean request) {
        mMethod = mContext.getString(E_string.efun_pd_method_check_version);
        checkMethod(mMethod);
//		List<NameValuePair> params = request.buildPostParams(SettingCheckRequest.class);
//		String responseStr = EfunHttpUtil.efunExecutePostRequest(mUrl + mMethod, params);
        Map<String, String> params = request.buildPostParamsAfter6(SettingCheckRequest.class);
        String responseStr = HttpRequest.post(mUrl + mMethod, params);
        SettingCheckResponse response = new SettingCheckResponse();
        response.setContext(mContext);
        response.pares(request, responseStr, false);
        return response;
    }

    @Override
    public BaseResponseBean actsList(BaseRequestBean request) {
        mMethod = mContext.getString(E_string.efun_pd_method_act_list);
        checkMethod(mMethod);
//		List<NameValuePair> params = request.buildPostParams(ActivityListRequest.class);
//		String responseStr = EfunHttpUtil.efunExecutePostRequest(mUrl + mMethod, params);
        Map<String, String> params = request.buildPostParamsAfter6(ActivityListRequest.class);
        String responseStr = HttpRequest.post(mUrl + mMethod, params);
        ActivityListResponse response = new ActivityListResponse();
        response.setContext(mContext);
        response.pares(request, responseStr, false);
        return response;
    }

    @Override
    public BaseResponseBean csAsk(BaseRequestBean request) {
        mMethod = mContext.getString(E_string.efun_pd_method_cs_ask);
        checkMethod(mMethod);
//		List<NameValuePair> params = request.buildPostParams(CsAskRequest.class);
//		String responseStr = EfunHttpUtil.efunExecutePostRequest(mUrl + mMethod, params);
        Map<String, String> params = request.buildPostParamsAfter6(CsAskRequest.class);
        String responseStr = HttpRequest.post(mUrl + mMethod, params);
        CsAskResponse response = new CsAskResponse();
        response.setContext(mContext);
        response.pares(request, responseStr, false);
        return response;
    }


    @Override
    public BaseResponseBean accountGetUserFBUidsByUid(BaseRequestBean request) {
        mMethod = mContext.getString(E_string.efun_pd_method_cs_get_fbuserInfos);
        checkMethod(mMethod);
//		List<NameValuePair> params = request.buildPostParams(AccountGetUserFBUidsByUidRequest.class);
//		String responseStr = EfunHttpUtil.efunExecutePostRequest(mLoginUrl + mMethod, params);
        Map<String, String> params = request.buildPostParamsAfter6(AccountGetUserFBUidsByUidRequest.class);
        String responseStr = HttpRequest.post(mLoginUrl + mMethod, params);
        AccountGetUserFBUidsByUidResponse response = new AccountGetUserFBUidsByUidResponse();
        response.setContext(mContext);
        response.pares(request, responseStr, false);
        return response;
    }

    @Override
    public BaseResponseBean statLogInfo(BaseRequestBean request) {
        mMethod = mContext.getString(E_string.efun_pd_method_stat_loginfo);
        checkMethod(mMethod);
//		List<NameValuePair> params = request.buildPostParams(StatLogInfoRequest.class);
//		String responseStr = EfunHttpUtil.efunExecutePostRequest(mUrl + mMethod, params);
        Map<String, String> paramsMap = request.buildPostParamsAfter6(StatLogInfoRequest.class);
        String params = "";
        try {
            params = StatLogInfoUtils.map2strData(paramsMap, "StatLogInfoRequest");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        EfunLogUtil.logI("statLogInfo:" + mStatUrl + mMethod + "?" + params);
//		String responseStr = EfunHttpUtil.efunGetRequest(mStatUrl + mMethod+"?"+ params);
        String responseStr = HttpRequest.get(mStatUrl + mMethod + "?" + params);
        StatLogInfoResponse response = new StatLogInfoResponse();
        response.setContext(mContext);
        response.pares(request, responseStr, false);
        return response;
    }

    @Override
    public BaseResponseBean statPushInfo(BaseRequestBean request) {
        mMethod = mContext.getString(E_string.efun_pd_method_stat_push);
        checkMethod(mMethod);
//		List<NameValuePair> params = request.buildPostParams(StatLogInfoRequest.class);
//		String responseStr = EfunHttpUtil.efunExecutePostRequest(mUrl + mMethod, params);
        Map<String, String> paramsMap = request.buildPostParamsAfter6(StatPushInfoRequest.class);
        String params = "";
        try {
            params = StatLogInfoUtils.map2strData(paramsMap, "StatPushInfoRequest");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        EfunLogUtil.logI("statPushInfo:" + mStatUrl + mMethod + "?" + params);
//		String responseStr = EfunHttpUtil.efunGetRequest(mStatUrl + mMethod+"?"+params);
        String responseStr = HttpRequest.get(mStatUrl + mMethod + "?" + params);
        StatLogInfoResponse response = new StatLogInfoResponse();
        response.setContext(mContext);
        response.pares(request, responseStr, false);
        return response;
    }

    @Override
    public BaseResponseBean getPlatformConfigInfosOfText(BaseRequestBean request) {
        // TODO Auto-generated method stub
        mMethod = mContext.getString(E_string.efun_pd_method_text_platform_config_infos);
        String responseStr = HttpRequest.get(mCDNUrl + mMethod);
        PlatformConfigInfosResponse response = new PlatformConfigInfosResponse();
        response.setContext(mContext);
        response.pares(request, responseStr, true);
        return response;
    }

    @Override
    public BaseResponseBean getPlayerAreaOfText(BaseRequestBean request) {
        // TODO Auto-generated method stub
        mMethod = mContext.getString(E_string.efun_pd_method_text_player_area);
        String responseStr = HttpRequest.get(mCDNUrl + mMethod);
        PlayerAreaResponse response = new PlayerAreaResponse();
        response.setContext(mContext);
        response.pares(request, responseStr, true);
        return response;
    }

    @Override
    public BaseResponseBean refreshPlayerHeaderIcon(BaseRequestBean request) {
        mMethod = mContext.getString(E_string.efun_pd_method_user_header_new);
        checkMethod(mMethod);
        Map<String, String> params = request.buildPostParamsAfter6(PersonRefreshHeadIconRequest.class);
        String responseStr = HttpRequest.post(mUrl + mMethod, params);
        PersonRefreshHeadIconResponse response = new PersonRefreshHeadIconResponse();
        response.setContext(mContext);
        response.pares(request, responseStr, false);
        return response;
    }

    @Override
    public BaseResponseBean getWelfares(BaseRequestBean request) {
        // TODO Auto-generated method stub
        mMethod = mContext.getString(E_string.efun_pd_method_get_welfare_list);
        checkMethod(mMethod);
        Map<String, String> params = request.buildPostParamsAfter6(WelfareListRequest.class);
        String responseStr = HttpRequest.post(mUrl + mMethod, params);
//        String responseStr = HttpRequest.post("http://t.pf.efunen.com/" + mMethod, params);
        WelfareListResponse response = new WelfareListResponse();
        response.setContext(mContext);
        response.pares(request, responseStr, false);
        return response;
    }

    @Override
    public BaseResponseBean summaryPraise(BaseRequestBean request) {
        mMethod = mContext.getString(E_string.efun_pd_method_summary_praise);
        checkMethod(mMethod);
//		List<NameValuePair> params = request.buildPostParams(SummaryPraiseRequest.class);
//		String responseStr = EfunHttpUtil.efunExecutePostRequest(mUrl + mMethod, params);
        Map<String, String> params = request.buildPostParamsAfter6(SummaryPraiseRequest.class);
        String responseStr = HttpRequest.post(mUrl + mMethod, params);
        SummaryPraiseResponse response = new SummaryPraiseResponse();
        response.setContext(mContext);
        response.pares(request, responseStr, false);
        return response;
    }
/*	@Override
	public BaseResponseBean thirdLogin(BaseRequestBean request) {
		// TODO Auto-generated method stub
		mMethod = mContext.getString(E_string.efun_pd_method_third_login);
		checkMethod(mMethod);
//		List<NameValuePair> params = request.buildPostParams(AccountLoginRequest.class);
//		String responseStr = EfunHttpUtil.efunExecutePostRequest(mUrl + mMethod, params);
		Map<String,String> params = request.buildPostParamsAfter6(AccountThirdLoginRequest.class);
		String responseStr = HttpRequest.post(mUrl + mMethod, params);
		AccountResponse response = new AccountResponse();
		response.setContext(mContext);
		response.setRequestBean(request);
		response.pares(request, responseStr, false);
		return response;
	}*/

    @Override
    public BaseResponseBean thirdLogin(BaseRequestBean request) {

        mMethod = mContext.getString(R.string.interface_third_login);
        checkMethod(mMethod);
//		List<NameValuePair> params = request.buildPostParams(AccountLoginRequest.class);
//		String responseStr = EfunHttpUtil.efunExecutePostRequest(mUrl + mMethod, params);
        Map<String, String> params = request.buildPostParamsAfter6(AccountThirdLoginRequest.class);

        String loginUrl = IPlatApplication.get().getIPlatUrlByKey(UrlUtils.LOGIN_PRE_KEY);
        String loginSpaUrl = IPlatApplication.get().getIPlatUrlByKey(UrlUtils.LOGIN_SPA_KEY);

        if (TextUtils.isEmpty(loginUrl)) {
            loginUrl = mContext.getString(R.string.eee_login_url);
        }
        String responseStr = HttpRequest.postIn2Url(loginUrl + mMethod, loginSpaUrl + mMethod, params);


        if (!TextUtils.isEmpty(responseStr)) {
            try {
                JSONObject loginJson = new JSONObject(responseStr);
                if (loginJson != null) {
                    if (loginJson.optString("code", "").equals(Const.HttpParam.RESULT_1000) || loginJson.optString("code", "").equals(Const.HttpParam.RESULT_1006)) {
                        String userid = loginJson.optString("userid", "");
                        String timestamp = loginJson.optString("timestamp", "");
                        String sign = loginJson.optString("sign", "");
                        String userToken = loginJson.optString("userToken", "");

                        Map<String, String> getMemberInfo = new HashMap<>();
                        getMemberInfo.put("token", userToken);
                        getMemberInfo.put("language", ((AccountThirdLoginRequest) request).getLanguage());
                        getMemberInfo.put("platform", Const.HttpParam.PLATFORM_AREA);
                        getMemberInfo.put("userid", userid);
                        getMemberInfo.put("timestamp", timestamp);
                        getMemberInfo.put("sign", sign);
                        mMethod = mContext.getString(R.string.member_getMemberInfoByToken);
                        responseStr = HttpRequest.postIn2Url(mUrl + mMethod, mUrl + mMethod, getMemberInfo);

                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        AccountResponse response = new AccountResponse();
        response.setContext(mContext);
        response.setRequestBean(request);
        response.pares(request, responseStr, false);
        return response;
    }

    @Override
    public BaseResponseBean giftSelfList(BaseRequestBean request) {
        mMethod = mContext.getString(E_string.efun_pd_method_gift_self_list);
        checkMethod(mMethod);
        Map<String, String> params = request.buildPostParamsAfter6(GiftSelfListRequest.class);
        String responseStr = HttpRequest.post(mUrl + mMethod, params);
        GiftSelfListResponse response = new GiftSelfListResponse();
        response.setContext(mContext);
        response.pares(request, responseStr, false);
        return response;
    }
}
