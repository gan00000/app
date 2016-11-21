package com.efun.platform.push.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.efun.platform.AndroidScape.E_layout;
import com.efun.platform.http.request.bean.BaseRequestBean;
import com.efun.platform.http.response.bean.BaseResponseBean;
import com.efun.platform.module.base.FixedActivity;
import com.efun.platform.push.PushInfoBean;
import com.efun.platform.widget.TitleView;

public class EfunPushDispatcherActivity extends FixedActivity {
	private PushInfoBean mPushInfoBean;
	@Override
	public ViewGroup[] needShowLoading() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int LayoutId() {
		// TODO Auto-generated method stub
		return E_layout.efun_pd_push_dispatcher;
	}

	@Override
	public void initTitle(TitleView titleView) {
		titleView.setTitleBarBackgroundColor(Color.TRANSPARENT);
		titleView.setTitleCenterText("");
		titleView.setTitleRightStatus(View.GONE);
		titleView.setTitleBottomLineStatus(View.GONE);
	}

	@Override
	public boolean needRequestData() {
		return false;
	}

	@Override
	public BaseRequestBean[] needRequestDataBean() {
		return null;
	}

	@Override
	public void onSuccess(int requestType, BaseResponseBean response) {
		super.onSuccess(requestType, response);
		finish();
	}
	
	@Override
	public void onFailure(int requestType) {
		super.onFailure(requestType);
		finish();
	}
	
	@Override
	public void onNoData(int requestType, String noDataNotify) {
		super.onNoData(requestType, noDataNotify);
		finish();
	}
//	//test game
//	mPushInfoBean = new PushInfoBean();
//	mPushInfoBean.setPushType(PushType.PUSH_TYPE_No3);
//	PushParamsBean mPushParams1 = new PushParamsBean();
//	mPushParams1.setPushGameCode("zslm");
//	mPushInfoBean.setPushParams(mPushParams1);
	
	
	//test gift
//	mPushInfoBean = new PushInfoBean();
//	mPushInfoBean.setPushType(PushType.PUSH_TYPE_No6);
//	PushParamsBean mPushParams2 = new PushParamsBean();
//	mPushParams2.setPushGoodsId("e83c09c152d64e858b1836a14a7a965a");
//	mPushInfoBean.setPushParams(mPushParams2);
	@Override
	public void init(Bundle bundle) {
//		mPushInfoBean = (PushInfoBean) getIntent().getSerializableExtra(Const.EFUN_PUSH_KEY);
//		EfunLogUtil.logE("PushDispatcherActivity PushInfoBean is "+mPushInfoBean);
//		if (mPushInfoBean != null) {
//			StatLogInfoUtils.setStaticPushInfo(mContext, mPushInfoBean.getMessageId());
//			switch (mPushInfoBean.getPushType()) {
//			case PushType.PUSH_TYPE_No1://资讯内容页面
//				SummaryItemBean mSummaryItemBean = new SummaryItemBean(BeanType.BEAN_SUMMARYITEMBEAN);
//				mSummaryItemBean.setIphoneUrl(mPushInfoBean.getPushUrl());
//				IntentUtils.go2Web(this, Web.WEB_GO_SUMMARY, mSummaryItemBean);
//				finish();
//				break;
//			case PushType.PUSH_TYPE_No2://游戏列表
//				if(FrameTabContainer.lastTag != Tab.TAB_ITEM_TAG_GAMES || !mPushInfoBean.getCurPageName().equals(FrameTabContainer.class.getName())){
//					startActivity(new Intent(this, PushGameListActivity.class));
//				}
//				finish();
//				break;
//			case PushType.PUSH_TYPE_No3://游戏内容页面
//			case PushType.PUSH_TYPE_No6://礼包内容页面
//				requestWithoutDialog(new BaseRequestBean[] { createRequest(mPushInfoBean.getPushType()) });
//				break;
//			case PushType.PUSH_TYPE_No4://活动内容页面
////				ActItemBean mActItemBean = new ActItemBean(BeanType.BEAN_ACTITEMBEAN);
////				mActItemBean.setActivityUrl(mPushInfoBean.getPushUrl());
////				IntentUtils.go2Web(this, Web.WEB_GO_ACT, mActItemBean);
//				finish();
//				break;
//			case PushType.PUSH_TYPE_No5://礼包列表页面
//				if(!mPushInfoBean.getCurPageName().equals(GiftListActivity.class.getName())){
//					startActivity(new Intent(this, GiftListActivity.class));
//				}
//				finish();
//				break;
//			case PushType.PUSH_TYPE_No7://好康
//				IPlatApplication.get().setHasHaoKangPush(true);
//				finish();
//				break;
//			case PushType.PUSH_TYPE_No8://储值
//				IPlatApplication.get().setHasRechargePush(true);
//				finish();
//				break;
//			default:
//				finish();
//				break;
//			}
//		}else{
//			finish();
//		}
		finish();
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		// super.onBackPressed();
	}

//	private BaseRequestBean createRequest(int pushType) {
//		switch (pushType) {
//		case PushType.PUSH_TYPE_No2:
//			break;
//		case PushType.PUSH_TYPE_No3:
//			GameItemRequest gameItemRequest = new GameItemRequest(
//					HttpParam.PLATFORM_AREA, 
//					mPushInfoBean.getPushParams().getPushGameCode(),
//					HttpParam.APP);
//			gameItemRequest.setReqType(IPlatformRequest.REQ_GAME_ITEM);
//			return gameItemRequest;
//		case PushType.PUSH_TYPE_No6:
//			String uid = "";
//			if(IPlatApplication.get().getUser()!=null){
//				uid =IPlatApplication.get().getUser().getUid();
//			}
//			GiftItemRequest giftItemRequest = new GiftItemRequest(
//					uid, mPushInfoBean.getPushParams().getPushGoodsId(),
//					HttpParam.APP);
//			if(IPlatApplication.get().getUser()!=null){
//				giftItemRequest.setSign(IPlatApplication.get().getUser().getSign());
//				giftItemRequest.setTimestamp(IPlatApplication.get().getUser().getTimestamp());
//			}
//			giftItemRequest.setReqType(IPlatformRequest.REQ_GIFT_ITEM);
//			return giftItemRequest;
//		}
//		return null;
//	}
	
	
	
}
