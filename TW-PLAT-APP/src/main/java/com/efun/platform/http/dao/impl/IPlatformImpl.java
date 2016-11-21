package com.efun.platform.http.dao.impl;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import android.content.Context;

import com.efun.core.http.HttpRequest;
import com.efun.core.tools.EfunLogUtil;
import com.efun.core.tools.EfunResourceUtil;
import com.efun.platform.AndroidScape.E_string;
import com.efun.platform.IPlatApplication;
import com.efun.platform.http.request.bean.AccountBindEmailRequest;
import com.efun.platform.http.request.bean.AccountBindPhoneRequest;
import com.efun.platform.http.request.bean.AccountBindPhoneSendVcodeRequest;
import com.efun.platform.http.request.bean.AccountFindPwdByPhoneRequest;
import com.efun.platform.http.request.bean.AccountFindPwdRequest;
import com.efun.platform.http.request.bean.AccountGetUserFBUidsByUidRequest;
import com.efun.platform.http.request.bean.AccountLoginRequest;
import com.efun.platform.http.request.bean.AccountReBindPhoneRequest;
import com.efun.platform.http.request.bean.AccountRegisterRequest;
import com.efun.platform.http.request.bean.AccountResetPwdRequest;
import com.efun.platform.http.request.bean.AccountUpdateRequest;
import com.efun.platform.http.request.bean.ActivityExtensionDownloadRequest;
import com.efun.platform.http.request.bean.ActivityExtensionGiftRequest;
import com.efun.platform.http.request.bean.ActivityExtensionRequest;
import com.efun.platform.http.request.bean.ActivityListRequest;
import com.efun.platform.http.request.bean.BaseRequestBean;
import com.efun.platform.http.request.bean.BindEmailByVcodeRequest;
import com.efun.platform.http.request.bean.BindPhoneByCallPhoneRequest;
import com.efun.platform.http.request.bean.CheckBindPhoneStatusRequest;
import com.efun.platform.http.request.bean.CheckNewSysEmailRequest;
import com.efun.platform.http.request.bean.CsAskRequest;
import com.efun.platform.http.request.bean.CsGainGameListRequest;
import com.efun.platform.http.request.bean.CsGainRoleListRequest;
import com.efun.platform.http.request.bean.CsGainServerListRequest;
import com.efun.platform.http.request.bean.CsQuestionListRequest;
import com.efun.platform.http.request.bean.CsReplyCommitQuestionRequest;
import com.efun.platform.http.request.bean.CsReplyDetailsRequest;
import com.efun.platform.http.request.bean.CsReplyFinishQuestionRequest;
import com.efun.platform.http.request.bean.CsReplyQuestionListRequest;
import com.efun.platform.http.request.bean.CsReplyStatusRequest;
import com.efun.platform.http.request.bean.GameAchieveSysListRequest;
import com.efun.platform.http.request.bean.GameDetailRequest;
import com.efun.platform.http.request.bean.GameItemRequest;
import com.efun.platform.http.request.bean.GameListOfModuleRequest;
import com.efun.platform.http.request.bean.GameListRequest;
import com.efun.platform.http.request.bean.GameNewsRequest;
import com.efun.platform.http.request.bean.GamePraiseRequest;
import com.efun.platform.http.request.bean.GameVoteRequest;
import com.efun.platform.http.request.bean.GameVoteScoreRequest;
import com.efun.platform.http.request.bean.GiftItemRequest;
import com.efun.platform.http.request.bean.GiftKnockRequest;
import com.efun.platform.http.request.bean.GiftListRequest;
import com.efun.platform.http.request.bean.GiftSelfListRequest;
import com.efun.platform.http.request.bean.GiftSelfStatusRequest;
import com.efun.platform.http.request.bean.NewGiftStatusRequest;
import com.efun.platform.http.request.bean.PersonGetAwardGiftRequest;
import com.efun.platform.http.request.bean.PersonGetSignInGiftRequest;
import com.efun.platform.http.request.bean.PersonPlaformPointRequest;
import com.efun.platform.http.request.bean.PersonRefreshHeadIconRequest;
import com.efun.platform.http.request.bean.PersonSignRequest;
import com.efun.platform.http.request.bean.PhoneAreaTypeRequest;
import com.efun.platform.http.request.bean.RechargeControlRequest;
import com.efun.platform.http.request.bean.SendVcodeToEmailRequest;
import com.efun.platform.http.request.bean.SettingCheckRequest;
import com.efun.platform.http.request.bean.StatLogInfoRequest;
import com.efun.platform.http.request.bean.StatPushInfoRequest;
import com.efun.platform.http.request.bean.SummaryHomeRequest;
import com.efun.platform.http.request.bean.SummaryListRequest;
import com.efun.platform.http.request.bean.SummaryPraiseRequest;
import com.efun.platform.http.request.bean.UserUpdateHeaderRequest;
import com.efun.platform.http.request.bean.UserUpdateInfoRequest;
import com.efun.platform.http.response.bean.AccountBindEmailResponse;
import com.efun.platform.http.response.bean.AccountBindPhoneResponse;
import com.efun.platform.http.response.bean.AccountGetUserFBUidsByUidResponse;
import com.efun.platform.http.response.bean.AccountResponse;
import com.efun.platform.http.response.bean.AccountUpdateResponse;
import com.efun.platform.http.response.bean.ActivityExtensionDownloadResponse;
import com.efun.platform.http.response.bean.ActivityExtensionGiftResponse;
import com.efun.platform.http.response.bean.ActivityExtensionResponse;
import com.efun.platform.http.response.bean.ActivityListResponse;
import com.efun.platform.http.response.bean.BaseResponseBean;
import com.efun.platform.http.response.bean.BindEmailByVcodeResponse;
import com.efun.platform.http.response.bean.BindPhoneByCallPhoneResponse;
import com.efun.platform.http.response.bean.CheckBindPhoneStatusResponse;
import com.efun.platform.http.response.bean.CheckSysEmailResponse;
import com.efun.platform.http.response.bean.CsAskResponse;
import com.efun.platform.http.response.bean.CsGainGameListResponse;
import com.efun.platform.http.response.bean.CsGainRoleListResponse;
import com.efun.platform.http.response.bean.CsGainServersListResponse;
import com.efun.platform.http.response.bean.CsQuestionListResponse;
import com.efun.platform.http.response.bean.CsReplyCommitQuestionResponse;
import com.efun.platform.http.response.bean.CsReplyDetailsResponse;
import com.efun.platform.http.response.bean.CsReplyFinishQuestionResponse;
import com.efun.platform.http.response.bean.CsReplyQuestionListResponse;
import com.efun.platform.http.response.bean.CsReplyStatusResponse;
import com.efun.platform.http.response.bean.GameAchieveSysListResponse;
import com.efun.platform.http.response.bean.GameDetailResponse;
import com.efun.platform.http.response.bean.GameItemResponse;
import com.efun.platform.http.response.bean.GameListOfModuleResponse;
import com.efun.platform.http.response.bean.GameListResponse;
import com.efun.platform.http.response.bean.GameNewsResponse;
import com.efun.platform.http.response.bean.GamePraiseResponse;
import com.efun.platform.http.response.bean.GameVoteResponse;
import com.efun.platform.http.response.bean.GameVoteScoreResponse;
import com.efun.platform.http.response.bean.GiftItemResponse;
import com.efun.platform.http.response.bean.GiftKnockResponse;
import com.efun.platform.http.response.bean.GiftListResponse;
import com.efun.platform.http.response.bean.GiftSelfListResponse;
import com.efun.platform.http.response.bean.GiftSelfStatusResponse;
import com.efun.platform.http.response.bean.NewGiftStatusResponse;
import com.efun.platform.http.response.bean.PersonGetSignInGiftResponse;
import com.efun.platform.http.response.bean.PersonPlatformPointResponse;
import com.efun.platform.http.response.bean.PersonRefreshHeadIconResponse;
import com.efun.platform.http.response.bean.PersonSignResponse;
import com.efun.platform.http.response.bean.PhoneAreaCodeResponse;
import com.efun.platform.http.response.bean.PhoneAreaTypeResponse;
import com.efun.platform.http.response.bean.PlatformConfigInfosResponse;
import com.efun.platform.http.response.bean.PlayerAreaResponse;
import com.efun.platform.http.response.bean.RechargeControlResponse;
import com.efun.platform.http.response.bean.SendVcodeToEmailResponse;
import com.efun.platform.http.response.bean.SettingCheckResponse;
import com.efun.platform.http.response.bean.StatLogInfoResponse;
import com.efun.platform.http.response.bean.SummaryHomeResponse;
import com.efun.platform.http.response.bean.SummaryListResponse;
import com.efun.platform.http.response.bean.SummaryPraiseResponse;
import com.efun.platform.http.response.bean.UserUpdateHeaderResponse;
import com.efun.platform.http.response.bean.UserUpdateInfoResponse;
import com.efun.platform.module.utils.UrlUtils;
import com.efun.platform.utils.StatLogInfoUtils;
/**
 * {@link IPlatformRequest} 实现类
 * @author Jesse
 *
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
//		mUrl = "http://172.16.60.120:8080/";
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
		Map<String,String> params = request.buildPostParamsAfter6(SummaryHomeRequest.class);
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
		Map<String,String> params = request.buildPostParamsAfter6(SummaryListRequest.class);
		String responseStr = HttpRequest.post(mUrl + mMethod, params);
		SummaryListResponse response = new SummaryListResponse();
		response.setContext(mContext);
		response.pares(request, responseStr, false);
		return response;
	}

	@Override
	public BaseResponseBean gameList(BaseRequestBean request) {
		mMethod = mContext.getString(E_string.efun_pd_method_game_list);
		checkMethod(mMethod);
//		List<NameValuePair> params = request.buildPostParams(GameListRequest.class);
//		String responseStr = EfunHttpUtil.efunExecutePostRequest(mUrl + mMethod, params);
		Map<String,String> params = request.buildPostParamsAfter6(GameListRequest.class);
		String responseStr = HttpRequest.post(mUrl + mMethod, params);
		GameListResponse response = new GameListResponse();
		response.setContext(mContext);
		response.pares(request, responseStr, false);
		return response;
	}

	@Override
	public BaseResponseBean gamePraise(BaseRequestBean request) {
		mMethod = mContext.getString(E_string.efun_pd_method_game_like);
		checkMethod(mMethod);
//		List<NameValuePair> params = request.buildPostParams(GamePraiseRequest.class);
//		String responseStr = EfunHttpUtil.efunExecutePostRequest(mUrl + mMethod, params);
		Map<String,String> params = request.buildPostParamsAfter6(GamePraiseRequest.class);
		String responseStr = HttpRequest.post(mUrl + mMethod, params);
		GamePraiseResponse response = new GamePraiseResponse();
		response.setContext(mContext);
		response.pares(request, responseStr, false);
		return response;
	}

	@Override
	public BaseResponseBean gameDetail(BaseRequestBean request) {
		mMethod = mContext.getString(E_string.efun_pd_method_game_detail);
		checkMethod(mMethod);
//		List<NameValuePair> params = request.buildPostParams(GameDetailRequest.class);
//		String responseStr = EfunHttpUtil.efunExecutePostRequest(mUrl + mMethod, params);
		Map<String,String> params = request.buildPostParamsAfter6(GameDetailRequest.class);
		String responseStr = HttpRequest.post(mUrl + mMethod, params);
		GameDetailResponse response = new GameDetailResponse();
		response.setContext(mContext);
		response.pares(request, responseStr, false);
		return response;
	}

	@Override
	public BaseResponseBean gameNewsList(BaseRequestBean request) {
		mMethod = mContext.getString(E_string.efun_pd_method_game_news);
		checkMethod(mMethod);
//		List<NameValuePair> params = request.buildPostParams(GameNewsRequest.class);
//		String responseStr = EfunHttpUtil.efunExecutePostRequest(mUrl + mMethod, params);
		Map<String,String> params = request.buildPostParamsAfter6(GameNewsRequest.class);
		String responseStr = HttpRequest.post(mUrl + mMethod, params);
		GameNewsResponse response = new GameNewsResponse();
		response.setContext(mContext);
		response.pares(request, responseStr, true);
		return response;
	}

	@Override
	public BaseResponseBean gameCommendList(BaseRequestBean request) {
		mMethod = mContext.getString(E_string.efun_pd_method_game_commend_list);
		checkMethod(mMethod);
//		List<NameValuePair> params = request.buildPostParams(GameVoteRequest.class);
//		String responseStr = EfunHttpUtil.efunExecutePostRequest(mUrl + mMethod, params);
		Map<String,String> params = request.buildPostParamsAfter6(GameVoteRequest.class);
		String responseStr = HttpRequest.post(mUrl + mMethod, params);
		GameVoteResponse response = new GameVoteResponse();
		response.setContext(mContext);
		response.pares(request, responseStr, false);
		return response;
	}
	
//	@Override
//	public BaseResponseBean gameCommendList(BaseRequestBean request) {
//		mMethod = mContext.getString(E_string.efun_pd_method_game_commend_list);
//		checkMethod(mMethod);
//		List<NameValuePair> params = request.buildPostParams(GameCommendListRequest.class);
//		String responseStr = EfunHttpUtil.efunExecutePostRequest(mUrl + mMethod, params);
//		GameCommendListResponse response = new GameCommendListResponse();
//		response.setContext(mContext);
//		response.pares(request, responseStr, true);
//		return response;
//	}
//
//	@Override
//	public BaseResponseBean gameCommend(BaseRequestBean request) {
//		mMethod = mContext.getString(E_string.efun_pd_method_game_commend);
//		checkMethod(mMethod);
//		List<NameValuePair> params = request.buildPostParams(GameCommendRequest.class);
//		String responseStr = EfunHttpUtil.efunExecutePostRequest(mUrl + mMethod, params);
//		GameCommendResponse response = new GameCommendResponse();
//		response.setContext(mContext);
//		response.pares(request, responseStr, false);
//		return response;
//	}
	
	@Override
	public BaseResponseBean gameCommend(BaseRequestBean request) {
		mMethod = mContext.getString(E_string.efun_pd_method_game_commend);
		checkMethod(mMethod);
//		List<NameValuePair> params = request.buildPostParams(GameVoteScoreRequest.class);
//		String responseStr = EfunHttpUtil.efunExecutePostRequest(mUrl + mMethod, params);
		Map<String,String> params = request.buildPostParamsAfter6(GameVoteScoreRequest.class);
		String responseStr = HttpRequest.post(mUrl + mMethod, params);
		GameVoteScoreResponse response = new GameVoteScoreResponse();
		response.setContext(mContext);
		response.pares(request, responseStr, false);
		return response;
	}

	@Override
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
	}
	@Override
	public BaseResponseBean update(BaseRequestBean request) {
		mMethod = mContext.getString(E_string.efun_pd_method_login);
		checkMethod(mMethod);
//		List<NameValuePair> params = request.buildPostParams(AccountUpdateRequest.class);
//		String responseStr = EfunHttpUtil.efunExecutePostRequest(mUrl + mMethod, params);
		Map<String,String> params = request.buildPostParamsAfter6(AccountUpdateRequest.class);
		String responseStr = HttpRequest.post(mUrl + mMethod, params);
		AccountUpdateResponse response = new AccountUpdateResponse();
		response.setContext(mContext);
		response.setRequestBean(request);
		response.pares(request, responseStr, false);
		return response;
	}
	@Override
	public BaseResponseBean register(BaseRequestBean request) {
		mMethod = mContext.getString(E_string.efun_pd_method_register);
		checkMethod(mMethod);
//		List<NameValuePair> params = request.buildPostParams(AccountRegisterRequest.class);
//		String responseStr = EfunHttpUtil.efunExecutePostRequest(mUrl + mMethod, params);
		Map<String,String> params = request.buildPostParamsAfter6(AccountRegisterRequest.class);
		String responseStr = HttpRequest.post(mUrl + mMethod, params);
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
		Map<String,String> params = request.buildPostParamsAfter6(AccountFindPwdRequest.class);
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
		Map<String,String> params = request.buildPostParamsAfter6(AccountFindPwdByPhoneRequest.class);
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
		Map<String,String> params = request.buildPostParamsAfter6(AccountResetPwdRequest.class);
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
		Map<String,String> params = request.buildPostParamsAfter6(UserUpdateHeaderRequest.class);
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
		Map<String,String> params = request.buildPostParamsAfter6(UserUpdateInfoRequest.class);
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
		Map<String,String> params = request.buildPostParamsAfter6(SettingCheckRequest.class);
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
		Map<String,String> params = request.buildPostParamsAfter6(ActivityListRequest.class);
		String responseStr = HttpRequest.post(mUrl + mMethod, params);
		ActivityListResponse response = new ActivityListResponse();
		response.setContext(mContext);
		response.pares(request, responseStr, false);
		return response;
	}

	@Override
	public BaseResponseBean actExtension(BaseRequestBean request) {
		mMethod = mContext.getString(E_string.efun_pd_method_act_extension);
		checkMethod(mMethod);
//		List<NameValuePair> params = request.buildPostParams(ActivityExtensionRequest.class);
//		String responseStr = EfunHttpUtil.efunExecutePostRequest(mUrl+ mMethod, params);
		Map<String,String> params = request.buildPostParamsAfter6(ActivityExtensionRequest.class);
		String responseStr = HttpRequest.post(mUrl + mMethod, params);
		ActivityExtensionResponse response = new ActivityExtensionResponse();
		response.setContext(mContext);
		response.pares(request, responseStr,false);
		return response;
	}
	
	@Override
	public BaseResponseBean actExtensionGift(BaseRequestBean request) {
		mMethod = mContext.getString(E_string.efun_pd_method_act_extension_gift);
		checkMethod(mMethod);
//		List<NameValuePair> params = request.buildPostParams(ActivityExtensionGiftRequest.class);
//		String responseStr = EfunHttpUtil.efunExecutePostRequest(mUrl+ mMethod, params);
		Map<String,String> params = request.buildPostParamsAfter6(ActivityExtensionGiftRequest.class);
		String responseStr = HttpRequest.post(mUrl + mMethod, params);
		ActivityExtensionGiftResponse response = new ActivityExtensionGiftResponse();
		response.setContext(mContext);
		response.pares(request, responseStr,false);
		return response;
	}
	@Override
	public BaseResponseBean actExtensionDownload(BaseRequestBean request) {
		mMethod = mContext.getString(E_string.efun_pd_method_act_extension_download);
		checkMethod(mMethod);
//		List<NameValuePair> params = request.buildPostParams(ActivityExtensionDownloadRequest.class);
//		String responseStr = EfunHttpUtil.efunExecutePostRequest(mFbUrl+ mMethod, params);
		Map<String,String> params = request.buildPostParamsAfter6(ActivityExtensionDownloadRequest.class);
		String responseStr = HttpRequest.post(mFbUrl+ mMethod, params);
		ActivityExtensionDownloadResponse response = new ActivityExtensionDownloadResponse();
		response.setContext(mContext);
		response.pares(request, responseStr,false);
		return response;
	}
	@Override
	public BaseResponseBean giftsList(BaseRequestBean request) {
		mMethod = mContext.getString(E_string.efun_pd_method_gift_list);
		checkMethod(mMethod);
//		List<NameValuePair> params = request.buildPostParams(GiftListRequest.class);
//		String responseStr = EfunHttpUtil.efunExecutePostRequest(mUrl + mMethod, params);
		Map<String,String> params = request.buildPostParamsAfter6(GiftListRequest.class);
		String responseStr = HttpRequest.post(mUrl + mMethod, params);
		GiftListResponse response = new GiftListResponse();
		response.setContext(mContext);
		response.pares(request, responseStr, false);
		return response;
	}

	@Override
	public BaseResponseBean giftKnock(BaseRequestBean request) {
		mMethod = mContext.getString(E_string.efun_pd_method_gift_knock);
		checkMethod(mMethod);
//		List<NameValuePair> params = request.buildPostParams(GiftKnockRequest.class);
//		String responseStr = EfunHttpUtil.efunExecutePostRequest(mUrl + mMethod, params);
		Map<String,String> params = request.buildPostParamsAfter6(GiftKnockRequest.class);
		String responseStr = HttpRequest.post(mUrl + mMethod, params);
		GiftKnockResponse response = new GiftKnockResponse();
		response.setContext(mContext);
		response.pares(request, responseStr, false);
		return response;
	}

	@Override
	public BaseResponseBean giftSelfList(BaseRequestBean request) {
		mMethod = mContext.getString(E_string.efun_pd_method_gift_self_list);
		checkMethod(mMethod);
//		List<NameValuePair> params = request.buildPostParams(GiftSelfListRequest.class);
//		String responseStr = EfunHttpUtil.efunExecutePostRequest(mUrl + mMethod, params);
		Map<String,String> params = request.buildPostParamsAfter6(GiftSelfListRequest.class);
		String responseStr = HttpRequest.post(mUrl + mMethod, params);
		GiftSelfListResponse response = new GiftSelfListResponse();
		response.setContext(mContext);
		response.pares(request, responseStr, false);
		return response;
	}
	
	@Override
	public BaseResponseBean giftSelfStatus(BaseRequestBean request) {
		mMethod = mContext.getString(E_string.efun_pd_method_gift_self_status);
		checkMethod(mMethod);
//		List<NameValuePair> params = request.buildPostParams(GiftSelfStatusRequest.class);
//		String responseStr = EfunHttpUtil.efunExecutePostRequest(mUrl + mMethod, params);
		Map<String,String> params = request.buildPostParamsAfter6(GiftSelfStatusRequest.class);
		String responseStr = HttpRequest.post(mUrl + mMethod, params);
		GiftSelfStatusResponse response = new GiftSelfStatusResponse();
		response.setContext(mContext);
		response.pares(request, responseStr, false);
		return response;
	}

	@Override
	public BaseResponseBean csAskGainGames(BaseRequestBean request) {
		mMethod = mContext.getString(E_string.efun_pd_method_cs_gain_games);
		checkMethod(mMethod);
//		List<NameValuePair> params = request.buildPostParams(CsGainGameListRequest.class);
//		String responseStr = EfunHttpUtil.efunExecutePostRequest(mGameUrl + mMethod, params);
		Map<String,String> params = request.buildPostParamsAfter6(CsGainGameListRequest.class);
		String responseStr = HttpRequest.post(mGameUrl + mMethod, params);
		CsGainGameListResponse response = new CsGainGameListResponse();
		response.setContext(mContext);
		response.pares(request, responseStr, false);
		return response;
	}

	@Override
	public BaseResponseBean csAskGainServers(BaseRequestBean request) {
		mMethod = mContext.getString(E_string.efun_pd_method_cs_gain_servers);
		checkMethod(mMethod);
//		List<NameValuePair> params = request.buildPostParams(CsGainServerListRequest.class);
//		String responseStr = EfunHttpUtil.efunExecutePostRequest(mGameUrl + mMethod, params);
		Map<String,String> params = request.buildPostParamsAfter6(CsGainServerListRequest.class);
		String responseStr = HttpRequest.post(mGameUrl + mMethod, params);
		CsGainServersListResponse response = new CsGainServersListResponse();
		response.setContext(mContext);
		response.pares(request, responseStr, false);
		return response;
	}

	@Override
	public BaseResponseBean csAskGainRole(BaseRequestBean request) {
		mMethod = mContext.getString(E_string.efun_pd_method_cs_gain_role);
		checkMethod(mMethod);
//		List<NameValuePair> params = request.buildPostParams(CsGainRoleListRequest.class);
//		String responseStr = EfunHttpUtil.efunExecutePostRequest(mGameUrl + mMethod, params);
		Map<String,String> params = request.buildPostParamsAfter6(CsGainRoleListRequest.class);
		String responseStr = HttpRequest.post(mGameUrl + mMethod, params);
		CsGainRoleListResponse response = new CsGainRoleListResponse();
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
		Map<String,String> params = request.buildPostParamsAfter6(CsAskRequest.class);
		String responseStr = HttpRequest.post(mUrl + mMethod, params);
		CsAskResponse response = new CsAskResponse();
		response.setContext(mContext);
		response.pares(request, responseStr, false);
		return response;
	}

	@Override
	public BaseResponseBean csReplayQuestionList(BaseRequestBean request) {
		mMethod = mContext.getString(E_string.efun_pd_method_cs_replay_question_list);
		checkMethod(mMethod);
//		List<NameValuePair> params = request.buildPostParams(CsReplyQuestionListRequest.class);
//		String responseStr = EfunHttpUtil.efunExecutePostRequest(mUrl + mMethod, params);
		Map<String,String> params = request.buildPostParamsAfter6(CsReplyQuestionListRequest.class);
		String responseStr = HttpRequest.post(mUrl + mMethod, params);
		CsReplyQuestionListResponse response = new CsReplyQuestionListResponse();
		response.setContext(mContext);
		response.pares(request, responseStr, false);
		return response;
	}

	@Override
	public BaseResponseBean csCommitQuestion(BaseRequestBean request) {
		mMethod = mContext.getString(E_string.efun_pd_method_cs_replay_question);
		checkMethod(mMethod);
//		List<NameValuePair> params = request.buildPostParams(CsReplyCommitQuestionRequest.class);
//		String responseStr = EfunHttpUtil.efunExecutePostRequest(mUrl + mMethod, params);
		Map<String,String> params = request.buildPostParamsAfter6(CsReplyCommitQuestionRequest.class);
		String responseStr = HttpRequest.post(mUrl + mMethod, params);
		CsReplyCommitQuestionResponse response = new CsReplyCommitQuestionResponse();
		response.setContext(mContext);
		response.pares(request, responseStr, false);
		return response;
	}

	@Override
	public BaseResponseBean csFinishQuestion(BaseRequestBean request) {
		mMethod = mContext.getString(E_string.efun_pd_method_cs_replay_finish_question);
		checkMethod(mMethod);
//		List<NameValuePair> params = request.buildPostParams(CsReplyFinishQuestionRequest.class);
//		String responseStr = EfunHttpUtil.efunExecutePostRequest(mUrl + mMethod, params);
		Map<String,String> params = request.buildPostParamsAfter6(CsReplyFinishQuestionRequest.class);
		String responseStr = HttpRequest.post(mUrl + mMethod, params);
		CsReplyFinishQuestionResponse response = new CsReplyFinishQuestionResponse();
		response.setContext(mContext);
		response.pares(request, responseStr, false);
		return response;
	}

	@Override
	public BaseResponseBean csQuestionList(BaseRequestBean request) {
		mMethod = mContext.getString(E_string.efun_pd_method_cs_question_list);
		checkMethod(mMethod);
//		List<NameValuePair> params = request.buildPostParams(CsQuestionListRequest.class);
//		String responseStr = EfunHttpUtil.efunExecutePostRequest(mUrl + mMethod, params);
		Map<String,String> params = request.buildPostParamsAfter6(CsQuestionListRequest.class);
		String responseStr = HttpRequest.post(mUrl + mMethod, params);
		CsQuestionListResponse response = new CsQuestionListResponse();
		response.setContext(mContext);
		response.pares(request, responseStr, false);
		return response;
	}

	@Override
	public BaseResponseBean bindPhoneToSendVCode(BaseRequestBean request) {
		mMethod = mContext.getString(E_string.efun_pd_method_bind_phone_send_vcode);
		checkMethod(mMethod);
//		List<NameValuePair> params = request.buildPostParams(AccountBindPhoneSendVcodeRequest.class);
//		String responseStr = EfunHttpUtil.efunExecutePostRequest(mUrl + mMethod, params);
		Map<String,String> params = request.buildPostParamsAfter6(AccountBindPhoneSendVcodeRequest.class);
		String responseStr = HttpRequest.post(mUrl + mMethod, params);
		AccountBindPhoneResponse response = new AccountBindPhoneResponse();
		response.setContext(mContext);
		response.pares(request, responseStr, false);
		return response;
	}

	@Override
	public BaseResponseBean bindPhone(BaseRequestBean request) {
		mMethod = mContext.getString(E_string.efun_pd_method_bind_phone);
		checkMethod(mMethod);
//		List<NameValuePair> params = request.buildPostParams(AccountBindPhoneRequest.class);
//		String responseStr = EfunHttpUtil.efunExecutePostRequest(mUrl + mMethod, params);
		Map<String,String> params = request.buildPostParamsAfter6(AccountBindPhoneRequest.class);
		String responseStr = HttpRequest.post(mUrl + mMethod, params);
		AccountBindPhoneResponse response = new AccountBindPhoneResponse();
		response.setContext(mContext);
		response.pares(request, responseStr, false);
		return response;
	}

	@Override
	public BaseResponseBean csReplyDetails(BaseRequestBean request) {
		mMethod = mContext.getString(E_string.efun_pd_method_cs_replay_details);
		checkMethod(mMethod);
//		List<NameValuePair> params = request.buildPostParams(CsReplyDetailsRequest.class);
//		String responseStr = EfunHttpUtil.efunExecutePostRequest(mUrl + mMethod, params);
		Map<String,String> params = request.buildPostParamsAfter6(CsReplyDetailsRequest.class);
		String responseStr = HttpRequest.post(mUrl + mMethod, params);
		CsReplyDetailsResponse response = new CsReplyDetailsResponse();
		response.setContext(mContext);
		response.pares(request, responseStr, false);
		return response;
	}
	@Override
	public BaseResponseBean csReplyStatus(BaseRequestBean request) {
		mMethod = mContext.getString(E_string.efun_pd_method_cs_replay_status);
		checkMethod(mMethod);
//		List<NameValuePair> params = request.buildPostParams(CsReplyStatusRequest.class);
//		String responseStr = EfunHttpUtil.efunExecutePostRequest(mUrl + mMethod, params);
		Map<String,String> params = request.buildPostParamsAfter6(CsReplyStatusRequest.class);
		String responseStr = HttpRequest.post(mUrl + mMethod, params);
		CsReplyStatusResponse response = new CsReplyStatusResponse();
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
		Map<String,String> params = request.buildPostParamsAfter6(AccountGetUserFBUidsByUidRequest.class);
		String responseStr = HttpRequest.post(mLoginUrl + mMethod, params);
		AccountGetUserFBUidsByUidResponse response = new AccountGetUserFBUidsByUidResponse();
		response.setContext(mContext);
		response.pares(request, responseStr, false);
		return response;
	}
	@Override
	public BaseResponseBean gameItem(BaseRequestBean request) {
		mMethod = mContext.getString(E_string.efun_pd_method_game_item);
		checkMethod(mMethod);
//		List<NameValuePair> params = request.buildPostParams(GameItemRequest.class);
//		String responseStr = EfunHttpUtil.efunExecutePostRequest(mUrl + mMethod, params);
		Map<String,String> params = request.buildPostParamsAfter6(GameItemRequest.class);
		String responseStr = HttpRequest.post(mUrl + mMethod, params);
		GameItemResponse response = new GameItemResponse();
		response.setContext(mContext);
		response.pares(request, responseStr, false);
		return response;
	}
	@Override
	public BaseResponseBean giftItem(BaseRequestBean request) {
		mMethod = mContext.getString(E_string.efun_pd_method_gift_item);
		checkMethod(mMethod);
//		List<NameValuePair> params = request.buildPostParams(GiftItemRequest.class);
//		String responseStr = EfunHttpUtil.efunExecutePostRequest(mUrl + mMethod, params);
		Map<String,String> params = request.buildPostParamsAfter6(GiftItemRequest.class);
		String responseStr = HttpRequest.post(mUrl + mMethod, params);
		GiftItemResponse response = new GiftItemResponse();
		response.setContext(mContext);
		response.pares(request, responseStr, false);
		return response;
	}
	@Override
	public BaseResponseBean SendVCodeToEmail(BaseRequestBean request) {
		mMethod = mContext.getString(E_string.efun_pd_method_send_vcode_to_email);
		checkMethod(mMethod);
//		List<NameValuePair> params = request.buildPostParams(SendVcodeToEmailRequest.class);
//		String responseStr = EfunHttpUtil.efunExecutePostRequest(mUrl + mMethod, params);
		Map<String,String> params = request.buildPostParamsAfter6(SendVcodeToEmailRequest.class);
		String responseStr = HttpRequest.post(mUrl + mMethod, params);
		SendVcodeToEmailResponse response = new SendVcodeToEmailResponse();
		response.setContext(mContext);
		response.pares(request, responseStr, false);
		return response;
	}
	@Override
	public BaseResponseBean bindEmailByVCode(BaseRequestBean request) {
		mMethod = mContext.getString(E_string.efun_pd_method_bind_email_by_vcode);
		checkMethod(mMethod);
//		List<NameValuePair> params = request.buildPostParams(BindEmailByVcodeRequest.class);
//		String responseStr = EfunHttpUtil.efunExecutePostRequest(mUrl + mMethod, params);
		Map<String,String> params = request.buildPostParamsAfter6(BindEmailByVcodeRequest.class);
		String responseStr = HttpRequest.post(mUrl + mMethod, params);
		BindEmailByVcodeResponse response = new BindEmailByVcodeResponse();
		response.setContext(mContext);
		response.pares(request, responseStr, false);
		return response;
	}
	@Override
	public BaseResponseBean GetPhoneAreas(BaseRequestBean request) {
		mMethod = mContext.getString(E_string.efun_pd_method_phone_area);
		checkMethod(mMethod);
//		List<NameValuePair> params = request.buildPostParams(PhoneAreaTypeRequest.class);
//		String responseStr = EfunHttpUtil.efunExecutePostRequest(mUrl + mMethod, params);
		Map<String,String> params = request.buildPostParamsAfter6(PhoneAreaTypeRequest.class);
		String responseStr = HttpRequest.post(mUrl + mMethod, params);
		PhoneAreaTypeResponse response = new PhoneAreaTypeResponse();
		response.setContext(mContext);
		response.pares(request, responseStr, false);
		return response;
	}
	@Override
	public BaseResponseBean reBindPhone(BaseRequestBean request) {
		mMethod = mContext.getString(E_string.efun_pd_method_rebind_phone);
		checkMethod(mMethod);
//		List<NameValuePair> params = request.buildPostParams(AccountReBindPhoneRequest.class);
//		String responseStr = EfunHttpUtil.efunExecutePostRequest(mUrl + mMethod, params);
		Map<String,String> params = request.buildPostParamsAfter6(AccountReBindPhoneRequest.class);
		String responseStr = HttpRequest.post(mUrl + mMethod, params);
		AccountBindPhoneResponse response = new AccountBindPhoneResponse();
		response.setContext(mContext);
		response.pares(request, responseStr, false);
		return response;
	}
	@Override
	public BaseResponseBean chargeBtnControl(BaseRequestBean request) {
		mMethod = mContext.getString(E_string.efun_pd_method_btn_control_url);
		checkMethod(mMethod);
//		List<NameValuePair> params = request.buildPostParams(RechargeControlRequest.class);
//		String responseStr = EfunHttpUtil.efunExecutePostRequest(mGameUrl + mMethod, params);
		Map<String,String> params = request.buildPostParamsAfter6(RechargeControlRequest.class);
		String responseStr = HttpRequest.post(mGameUrl + mMethod, params);
		RechargeControlResponse response = new RechargeControlResponse();
		response.setContext(mContext);
		response.pares(request, responseStr, false);
		return response;
	}
	@Override
	public BaseResponseBean newGiftStatus(BaseRequestBean request) {
		mMethod = mContext.getString(E_string.efun_pd_method_new_gift_status);
		checkMethod(mMethod);
//		List<NameValuePair> params = request.buildPostParams(NewGiftStatusRequest.class);
//		String responseStr = EfunHttpUtil.efunExecutePostRequest(mUrl + mMethod, params);
		Map<String,String> params = request.buildPostParamsAfter6(NewGiftStatusRequest.class);
		String responseStr = HttpRequest.post(mUrl + mMethod, params);
		NewGiftStatusResponse response = new NewGiftStatusResponse();
		response.setContext(mContext);
		response.pares(request, responseStr, false);
		return response;
	}
	@Override
	public BaseResponseBean bindEmail(BaseRequestBean request) {
		mMethod = mContext.getString(E_string.efun_pd_method_bind_email);
		checkMethod(mMethod);
//		List<NameValuePair> params = request.buildPostParams(AccountBindEmailRequest.class);
//		String responseStr = EfunHttpUtil.efunExecutePostRequest(mUrl + mMethod, params);
		Map<String,String> params = request.buildPostParamsAfter6(AccountBindEmailRequest.class);
		String responseStr = HttpRequest.post(mUrl + mMethod, params);
		AccountBindEmailResponse response = new AccountBindEmailResponse();
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
		Map<String,String> paramsMap = request.buildPostParamsAfter6(StatLogInfoRequest.class);
		String params = "";
		try {
			params = StatLogInfoUtils.map2strData(paramsMap,"StatLogInfoRequest");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		EfunLogUtil.logI("statLogInfo:" + mStatUrl + mMethod + "?" + params);
//		String responseStr = EfunHttpUtil.efunGetRequest(mStatUrl + mMethod+"?"+ params);
		String responseStr = HttpRequest.get(mStatUrl + mMethod+"?"+ params);
		StatLogInfoResponse response = new StatLogInfoResponse();
		response.setContext(mContext);
		response.pares(request, responseStr, false);
		return response;
	}
	@Override
	public BaseResponseBean gameAchieveSys(BaseRequestBean request) {
		mMethod = mContext.getString(E_string.efun_pd_method_game_achieve_sys);
		checkMethod(mMethod);
//		List<NameValuePair> params = request.buildPostParams(GameAchieveSysListRequest.class);
//		String responseStr = EfunHttpUtil.efunExecutePostRequest(mUrl + mMethod, params);
		Map<String,String> params = request.buildPostParamsAfter6(GameAchieveSysListRequest.class);
		String responseStr = HttpRequest.post(mUrl + mMethod, params);
		GameAchieveSysListResponse response = new GameAchieveSysListResponse();
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
		Map<String,String> params = request.buildPostParamsAfter6(SummaryPraiseRequest.class);
		String responseStr = HttpRequest.post(mUrl + mMethod, params);
		SummaryPraiseResponse response = new SummaryPraiseResponse();
		response.setContext(mContext);
		response.pares(request, responseStr, false);
		return response;
	}
	@Override
	public BaseResponseBean checkPlatformPoint(BaseRequestBean request) {
		mMethod = mContext.getString(E_string.efun_pd_method_person_point);
		checkMethod(mMethod);
//		List<NameValuePair> params = request.buildPostParams(PersonPlaformPointRequest.class);
//		String responseStr = EfunHttpUtil.efunExecutePostRequest(mPayUrl + mMethod, params);
		Map<String,String> params = request.buildPostParamsAfter6(PersonPlaformPointRequest.class);
		String responseStr = HttpRequest.post(mPayUrl + mMethod, params);
		PersonPlatformPointResponse response = new PersonPlatformPointResponse();
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
		Map<String,String> paramsMap = request.buildPostParamsAfter6(StatPushInfoRequest.class);
		String params = "";
		try {
			params = StatLogInfoUtils.map2strData(paramsMap,"StatPushInfoRequest");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		EfunLogUtil.logI("statPushInfo:" + mStatUrl + mMethod + "?" + params);
//		String responseStr = EfunHttpUtil.efunGetRequest(mStatUrl + mMethod+"?"+params);
		String responseStr = HttpRequest.get(mStatUrl + mMethod+"?"+params);
		StatLogInfoResponse response = new StatLogInfoResponse();
		response.setContext(mContext);
		response.pares(request, responseStr, false);
		return response;
	}
	@Override
	public BaseResponseBean gameListOfModuleGift(BaseRequestBean request) {
		mMethod = mContext.getString(E_string.efun_pd_method_get_gift_game);
		checkMethod(mMethod);
		Map<String,String> params = request.buildPostParamsAfter6(GameListOfModuleRequest.class);
		String responseStr = HttpRequest.post(mUrl + mMethod, params);
		GameListOfModuleResponse response = new GameListOfModuleResponse();
		response.setContext(mContext);
		response.pares(request, responseStr, false);
		return response;
	}
	@Override
	public BaseResponseBean getPlatformConfigInfosOfText(BaseRequestBean request) {
		// TODO Auto-generated method stub
		mMethod = mContext.getString(E_string.efun_pd_method_text_platform_config_infos);
		String responseStr = HttpRequest.get(mCDNUrl+mMethod);
		PlatformConfigInfosResponse response = new PlatformConfigInfosResponse();
		response.setContext(mContext);
		response.pares(request, responseStr, true);
		return response;
	}
	@Override
	public BaseResponseBean getPhoneAreaCodeOfText(BaseRequestBean request) {
		// TODO Auto-generated method stub
		mMethod = mContext.getString(E_string.efun_pd_method_text_phone_area_code);
		String responseStr = HttpRequest.get(mCDNUrl+mMethod);
		PhoneAreaCodeResponse response = new PhoneAreaCodeResponse();
		response.setContext(mContext);
		response.pares(request, responseStr, true);
		return response;
	}
	@Override
	public BaseResponseBean getPlayerAreaOfText(BaseRequestBean request) {
		// TODO Auto-generated method stub
		mMethod = mContext.getString(E_string.efun_pd_method_text_player_area);
		String responseStr = HttpRequest.get(mCDNUrl+mMethod);
		PlayerAreaResponse response = new PlayerAreaResponse();
		response.setContext(mContext);
		response.pares(request, responseStr, true);
		return response;
	}
	@Override
	public BaseResponseBean refreshPlayerHeaderIcon(BaseRequestBean request) {
		mMethod = mContext.getString(E_string.efun_pd_method_user_header_new);
		checkMethod(mMethod);
		Map<String,String> params = request.buildPostParamsAfter6(PersonRefreshHeadIconRequest.class);
		String responseStr = HttpRequest.post(mUrl + mMethod, params);
		PersonRefreshHeadIconResponse response = new PersonRefreshHeadIconResponse();
		response.setContext(mContext);
		response.pares(request, responseStr, false);
		return response;
	}
	@Override
	public BaseResponseBean getFinishUserInfoGift(BaseRequestBean request) {
		mMethod = mContext.getString(E_string.efun_pd_method_get_finish_info_gift);
		checkMethod(mMethod);
		Map<String,String> params = request.buildPostParamsAfter6(PersonGetAwardGiftRequest.class);
		String responseStr = HttpRequest.post(mUrl + mMethod, params);
		GiftKnockResponse response = new GiftKnockResponse();
		response.setContext(mContext);
		response.pares(request, responseStr, false);
		return response;
	}
	@Override
	public BaseResponseBean checkNewSysEmail(BaseRequestBean request) {
		mMethod = mContext.getString(E_string.efun_pd_method_check_new_sys_email);
		checkMethod(mMethod);
		Map<String,String> params = request.buildPostParamsAfter6(CheckNewSysEmailRequest.class);
		String responseStr = HttpRequest.post(mUrl + mMethod, params);
		CheckSysEmailResponse response = new CheckSysEmailResponse();
		response.setContext(mContext);
		response.pares(request, responseStr, false);
		return response;
	}
	@Override
	public BaseResponseBean checkBindPhoneStatus(BaseRequestBean request) {
		mMethod = mContext.getString(E_string.efun_pd_method_check_phone_status);
		checkMethod(mMethod);
		Map<String,String> params = request.buildPostParamsAfter6(CheckBindPhoneStatusRequest.class);
		String responseStr = HttpRequest.post(mUrl + mMethod, params);
		CheckBindPhoneStatusResponse response = new CheckBindPhoneStatusResponse();
		response.setContext(mContext);
		response.pares(request, responseStr, false);
		return response;
	}
	@Override
	public BaseResponseBean BindPhoneByCallPhone(BaseRequestBean request) {
		mMethod = mContext.getString(E_string.efun_pd_method_bind_phone_by_call_phone);
		checkMethod(mMethod);
		Map<String,String> params = request.buildPostParamsAfter6(BindPhoneByCallPhoneRequest.class);
		String responseStr = HttpRequest.post(mLoginUrl + mMethod, params);
		BindPhoneByCallPhoneResponse response = new BindPhoneByCallPhoneResponse();
		response.setContext(mContext);
		response.pares(request, responseStr, false);
		return response;
	}
	@Override
	public BaseResponseBean personSignIn(BaseRequestBean request) {
		mMethod = mContext.getString(E_string.efun_pd_method_user_signin);
		checkMethod(mMethod);
        Map<String,String> params = request.buildPostParamsAfter6(PersonSignRequest.class);
		String responseStr = HttpRequest.post(mUrl + mMethod, params);
		PersonSignResponse response = new PersonSignResponse();
        response.setContext(mContext);
        response.pares(request, responseStr, false);
        return response;
	}
	@Override
	public BaseResponseBean getSignInGift(BaseRequestBean request) {
		// TODO Auto-generated method stub
		mMethod = mContext.getString(E_string.efun_pd_method_get_signin_reward);
		checkMethod(mMethod);
        Map<String,String> params = request.buildPostParamsAfter6( PersonGetSignInGiftRequest.class);
		String responseStr = HttpRequest.post(mUrl + mMethod, params);
		PersonGetSignInGiftResponse response = new PersonGetSignInGiftResponse();
        response.setContext(mContext);
        response.pares(request, responseStr, false);
        return response;
	}
}
