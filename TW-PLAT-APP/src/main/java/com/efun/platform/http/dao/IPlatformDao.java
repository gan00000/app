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
 * @author Jesse
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
		case IPlatformRequest.REQ_GAME_LIST:
			mResponse.setBaseResponseBean(mImpl.gameList(bean));
			break;
		case IPlatformRequest.REQ_GAME_ITEM:
			mResponse.setBaseResponseBean(mImpl.gameItem(bean));
			break;
		case IPlatformRequest.REQ_GAME_PRAISE:
			mResponse.setBaseResponseBean(mImpl.gamePraise(bean));
			break;
		case IPlatformRequest.REQ_GAME_NEWS_LIST:
			mResponse.setBaseResponseBean(mImpl.gameNewsList(bean));
			break;
		case IPlatformRequest.REQ_GAME_DETAIL:
			mResponse.setBaseResponseBean(mImpl.gameDetail(bean));
			break;
		case IPlatformRequest.REQ_GAME_COMMEND_LIST:
			mResponse.setBaseResponseBean(mImpl.gameCommendList(bean));
			break;
		case IPlatformRequest.REQ_GAME_COMMEND:
			mResponse.setBaseResponseBean(mImpl.gameCommend(bean));
			break;
		case IPlatformRequest.REQ_ACCOUNT_LOGIN:
			mResponse.setBaseResponseBean(mImpl.login(bean));
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
		case IPlatformRequest.REQ_ACCOUNT_BIND_PHONE_SEND_VCODE:
			mResponse.setBaseResponseBean(mImpl.bindPhoneToSendVCode(bean));
			break;
		case IPlatformRequest.REQ_ACCOUNT_BIND_PHONE:
			mResponse.setBaseResponseBean(mImpl.bindPhone(bean));
			break;
		case IPlatformRequest.REQ_ACCOUNT_BIND_EMAIL:
			mResponse.setBaseResponseBean(mImpl.bindEmail(bean));
			break;
		case IPlatformRequest.REQ_ACCOUNT_REBIND_PHONE:
			mResponse.setBaseResponseBean(mImpl.reBindPhone(bean));
			break;
		case IPlatformRequest.REQ_CS_GAIN_GAMES:
			mResponse.setBaseResponseBean(mImpl.csAskGainGames(bean));
			break;
		case IPlatformRequest.REQ_CS_GAIN_SERVER:
			mResponse.setBaseResponseBean(mImpl.csAskGainServers(bean));
			break;
		case IPlatformRequest.REQ_CS_GAIN_ROLE:
			mResponse.setBaseResponseBean(mImpl.csAskGainRole(bean));
			break;
		case IPlatformRequest.REQ_CS_ASK:
			mResponse.setBaseResponseBean(mImpl.csAsk(bean));
			break;
		case IPlatformRequest.REQ_CS_REPLAY_QUESTION_LIST:
			mResponse.setBaseResponseBean(mImpl.csReplayQuestionList(bean));
			break;
		case IPlatformRequest.REQ_CS_REPLAY_COMMIT_QUESTION:
			mResponse.setBaseResponseBean(mImpl.csCommitQuestion(bean));
			break;
		case IPlatformRequest.REQ_CS_REPLAY_FINISH_QUESTION:
			mResponse.setBaseResponseBean(mImpl.csFinishQuestion(bean));
			break;
		case IPlatformRequest.REQ_CS_REPLY_DETAILS:
			mResponse.setBaseResponseBean(mImpl.csReplyDetails(bean));
			break;
		case IPlatformRequest.REQ_CS_QUESTION_LIST_POP:
		case IPlatformRequest.REQ_CS_QUESTION_LIST_LOR:
		case IPlatformRequest.REQ_CS_QUESTION_LIST_RCG:
			mResponse.setBaseResponseBean(mImpl.csQuestionList(bean));
			break;
		case IPlatformRequest.REQ_CS_REPLY_STATUS:
			mResponse.setBaseResponseBean(mImpl.csReplyStatus(bean));
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
		case IPlatformRequest.REQ_ACT_EXTENSION:
			mResponse.setBaseResponseBean(mImpl.actExtension(bean));
			break;
		case IPlatformRequest.REQ_ACT_EXTENSION_GIFT:
			mResponse.setBaseResponseBean(mImpl.actExtensionGift(bean));
			break;
		case IPlatformRequest.REQ_ACT_EXTENSION_DOWNLOAD:
			mResponse.setBaseResponseBean(mImpl.actExtensionDownload(bean));
			break;
		case IPlatformRequest.REQ_GIFT_LIST:
			mResponse.setBaseResponseBean(mImpl.giftsList(bean));
			break;
		case IPlatformRequest.REQ_GIFT_ITEM:
			mResponse.setBaseResponseBean(mImpl.giftItem(bean));
			break;
		case IPlatformRequest.REQ_GIFT_KNOCK:
			mResponse.setBaseResponseBean(mImpl.giftKnock(bean));
			break;
		case IPlatformRequest.REQ_GIFT_SELF_LIST:
			mResponse.setBaseResponseBean(mImpl.giftSelfList(bean));
			break;
		case IPlatformRequest.REQ_GIFT_SELF_STATUS:
			mResponse.setBaseResponseBean(mImpl.giftSelfStatus(bean));
			break;
		case IPlatformRequest.REQ_CS_GET_FB_USER_INFO:
			mResponse.setBaseResponseBean(mImpl.accountGetUserFBUidsByUid(bean));
			break;
		case IPlatformRequest.REQ_ACCOUNT_SEND_VCODE_TO_EMAIL:
			mResponse.setBaseResponseBean(mImpl.SendVCodeToEmail(bean));
			break;
		case IPlatformRequest.REQ_ACCOUNT_BIND_EMAIL_BY_VCODE:
			mResponse.setBaseResponseBean(mImpl.bindEmailByVCode(bean));
			break;
		case IPlatformRequest.REQ_ACCOUNT_BIND_PHONE_AREA:
			mResponse.setBaseResponseBean(mImpl.GetPhoneAreas(bean));
			break;
		case IPlatformRequest.REQ_CHARGE_CONTROL:
			mResponse.setBaseResponseBean(mImpl.chargeBtnControl(bean));
			break;
		case IPlatformRequest.REQ_NEW_GIFT_STATUS:
			mResponse.setBaseResponseBean(mImpl.newGiftStatus(bean));
			break;
		case IPlatformRequest.REQ_STAT_LOGINFO:
			mResponse.setBaseResponseBean(mImpl.statLogInfo(bean));
			break;
		case IPlatformRequest.REQ_GAME_ACHIEVE_SYS:
			mResponse.setBaseResponseBean(mImpl.gameAchieveSys(bean));
			break;
		case IPlatformRequest.REQ_SUMMARY_PRAISE:
			mResponse.setBaseResponseBean(mImpl.summaryPraise(bean));
			break;
		case IPlatformRequest.REQ_USER_PLATFORM_POINT:
			mResponse.setBaseResponseBean(mImpl.checkPlatformPoint(bean));
			break;
		case IPlatformRequest.REQ_USER_GIFT_GAMES_LIST:
			mResponse.setBaseResponseBean(mImpl.gameListOfModuleGift(bean));
			break;
		case IPlatformRequest.REQ_STAT_PUSHINFO:
			mResponse.setBaseResponseBean(mImpl.statPushInfo(bean));
			break;
		case IPlatformRequest.REQ_TXT_PLATFORM_CONFIG_INFOS:
			mResponse.setBaseResponseBean(mImpl.getPlatformConfigInfosOfText(bean));
			break;
		case IPlatformRequest.REQ_TXT_PHONE_AREA_CODE:
			mResponse.setBaseResponseBean(mImpl.getPhoneAreaCodeOfText(bean));
			break;
		case IPlatformRequest.REQ_TXT_PLAYER_AREA:
			mResponse.setBaseResponseBean(mImpl.getPlayerAreaOfText(bean));
			break;
		case IPlatformRequest.REQ_USER_UPDATE_HEADER_NEW:
			mResponse.setBaseResponseBean(mImpl.refreshPlayerHeaderIcon(bean));
			break;
		case IPlatformRequest.REQ_USER_GET_GIFT_SERIAL:
			mResponse.setBaseResponseBean(mImpl.getFinishUserInfoGift(bean));
			break;
		case IPlatformRequest.REQ_USER_CHECK_NEW_SYS_EMAIL:
			mResponse.setBaseResponseBean(mImpl.checkNewSysEmail(bean));
			break;
		case IPlatformRequest.REQ_ACCOUNT_BIND_PHONE_BY_CALL_PHONE:
			mResponse.setBaseResponseBean(mImpl.BindPhoneByCallPhone(bean));
			break;
		case IPlatformRequest.REQ_ACCOUNT_BIND_PHONE_CHECK_STATUS:
			mResponse.setBaseResponseBean(mImpl.checkBindPhoneStatus(bean));
			break;
		case IPlatformRequest.REQ_PERSON_SIGN:
			mResponse.setBaseResponseBean(mImpl.personSignIn(bean));
			break;
		case IPlatformRequest.REQ_PERSON_GET_SIGN_REWARD:
			mResponse.setBaseResponseBean(mImpl.getSignInGift(bean));
			break;
		}

		return mResponse;
	}
}
