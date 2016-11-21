package com.efun.platform.http.dao;

import android.content.Context;

import com.efun.platform.http.dao.impl.IPlatformImpl;
import com.efun.platform.http.dao.impl.IPlatformRequest;
import com.efun.platform.http.request.IPlatRequest;
import com.efun.platform.http.request.bean.BaseRequestBean;
import com.efun.platform.http.response.IPlatResponse;

/**
 * 接口请求
 * 
 * @author itxuxxey
 * 
 */
public class IPlatformDao implements IPlatformInterface {
	protected IPlatResponse mResponse;
	private IPlatformImpl mImpl;

	public IPlatformDao(Context context) {
		mImpl = new IPlatformImpl(context);
		mResponse = new IPlatResponse();
	}

	@Override
	public IPlatResponse request(IPlatRequest r) {
		BaseRequestBean bean = r.getRequestBean();
		switch (r.getRequestBean().getReqType()) {
		case IPlatformRequest.REQ_SUMMARY_HOME:
			mResponse.setBaseResponseBean(mImpl.summaryHome(bean));
			break;
		case IPlatformRequest.REQ_SUMMARY_LIST_ALL:
		case IPlatformRequest.REQ_SUMMARY_LIST_NEWS:
		case IPlatformRequest.REQ_SUMMARY_LIST_BULLETIN:
		case IPlatformRequest.REQ_SUMMARY_LIST_ACTIVITY:
		case IPlatformRequest.REQ_SUMMARY_LIST_STRATEGY:
		case IPlatformRequest.REQ_SUMMARY_LIST_VIDEO:
		case IPlatformRequest.REQ_SUMMARY_LIST_ABOUT_US:
			mResponse.setBaseResponseBean(mImpl.summaryList(bean));
			break;
		case IPlatformRequest.REQ_ACCOUNT_LOGIN:
			mResponse.setBaseResponseBean(mImpl.login(bean));
			break;
		case IPlatformRequest.REQ_ACCOUNT_THIRD_LOGIN:
			mResponse.setBaseResponseBean(mImpl.thirdLogin(bean));
			break;
		case IPlatformRequest.REQ_ACCOUNT_UPDATE:
			mResponse.setBaseResponseBean(mImpl.update(bean));
			break;
		case IPlatformRequest.REQ_ACCOUNT_REGISTER:
			mResponse.setBaseResponseBean(mImpl.register(bean));
			break;
		case IPlatformRequest.REQ_ACCOUNT_FORGET_PWD_BY_EMAIL:
			mResponse.setBaseResponseBean(mImpl.forgetPwd(bean));
			break;
		case IPlatformRequest.REQ_ACCOUNT_FORGET_PWD_BY_PHONE:
			mResponse.setBaseResponseBean(mImpl.findPwdByPhone(bean));
			break;
		case IPlatformRequest.REQ_ACCOUNT_RESET_PWD:
			mResponse.setBaseResponseBean(mImpl.resetPwd(bean));
			break;
		case IPlatformRequest.REQ_CS_ASK:
			mResponse.setBaseResponseBean(mImpl.csAsk(bean));
			break;
		case IPlatformRequest.REQ_USER_UPDATE_INFO:
			mResponse.setBaseResponseBean(mImpl.userUpdateInfo(bean));
			break;
		case IPlatformRequest.REQ_USER_UPDATE_HEADER:
			mResponse.setBaseResponseBean(mImpl.userUpdateHeader(bean));
			break;
		case IPlatformRequest.REQ_SETTING_CHECK_VERSION:
			mResponse.setBaseResponseBean(mImpl.checkVersion(bean));
			break;
		case IPlatformRequest.REQ_ACT_LIST:
			mResponse.setBaseResponseBean(mImpl.actsList(bean));
			break;
		case IPlatformRequest.REQ_CS_GET_FB_USER_INFO:
			mResponse.setBaseResponseBean(mImpl.accountGetUserFBUidsByUid(bean));
			break;
		case IPlatformRequest.REQ_STAT_LOGINFO:
			mResponse.setBaseResponseBean(mImpl.statLogInfo(bean));
			break;
		case IPlatformRequest.REQ_STAT_PUSHINFO:
			mResponse.setBaseResponseBean(mImpl.statPushInfo(bean));
			break;
		case IPlatformRequest.REQ_TXT_PLATFORM_CONFIG_INFOS:
			mResponse.setBaseResponseBean(mImpl.getPlatformConfigInfosOfText(bean));
			break;
		case IPlatformRequest.REQ_TXT_PLAYER_AREA:
			mResponse.setBaseResponseBean(mImpl.getPlayerAreaOfText(bean));
			break;
		case IPlatformRequest.REQ_USER_UPDATE_HEADER_NEW:
			mResponse.setBaseResponseBean(mImpl.refreshPlayerHeaderIcon(bean));
			break;
		case IPlatformRequest.REQ_WELFARE_INFOS:
			mResponse.setBaseResponseBean(mImpl.getWelfares(bean));
			break;
		case IPlatformRequest.REQ_SUMMARY_PRAISE:
			mResponse.setBaseResponseBean(mImpl.summaryPraise(bean));
			break;
		case IPlatformRequest.REQ_GIFT_SELF_LIST:
			mResponse.setBaseResponseBean(mImpl.giftSelfList(bean));
			break;
		}

		return mResponse;
	}
}
