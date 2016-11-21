package com.efun.platform.push.activity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.efun.core.tools.EfunLogUtil;
import com.efun.platform.AndroidScape.E_layout;
import com.efun.platform.FrameTabContainer;
import com.efun.platform.IPlatApplication;
import com.efun.platform.http.dao.impl.IPlatformRequest;
import com.efun.platform.http.request.bean.BaseRequestBean;
import com.efun.platform.http.request.bean.GameItemRequest;
import com.efun.platform.http.request.bean.GiftItemRequest;
import com.efun.platform.http.response.bean.BaseResponseBean;
import com.efun.platform.http.response.bean.GameItemResponse;
import com.efun.platform.http.response.bean.GiftItemResponse;
import com.efun.platform.module.base.FixedActivity;
import com.efun.platform.module.game.activity.GameDetailActivity;
import com.efun.platform.module.game.bean.GameItemBean;
import com.efun.platform.module.summary.bean.SummaryItemBean;
import com.efun.platform.module.utils.AppUtils;
import com.efun.platform.module.utils.IntentUtils;
import com.efun.platform.module.welfare.activity.GiftDetailActivity;
import com.efun.platform.module.welfare.activity.GiftListActivity;
import com.efun.platform.module.welfare.bean.GiftItemBean;
import com.efun.platform.push.PushUtils.PushType;
import com.efun.platform.utils.Const;
import com.efun.platform.utils.Const.AppStatus;
import com.efun.platform.utils.Const.BeanType;
import com.efun.platform.utils.Const.HttpParam;
import com.efun.platform.utils.Const.Tab;
import com.efun.platform.utils.Const.Web;
import com.efun.platform.widget.TitleView;

public class PushDispatcherActivity extends FixedActivity {
//	private PushInfoBean mPushInfoBean;
	private String mPushInfoStr;
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
		if(requestType==IPlatformRequest.REQ_GAME_ITEM){
			GameItemResponse mResponse = (GameItemResponse)response;
			GameItemBean bean = mResponse.getGameItemBean();
			if(bean.getCode().equals(HttpParam.RESULT_1000)){
				if(AppUtils.isAppInstalled(mContext, bean.getPackageName())){
					if(AppUtils.isAppUpdate(mContext, bean.getPackageName(), bean.getAndroidVersion())){
						bean.setStatus(AppStatus.UPDATE);
					}else{
						bean.setStatus(AppStatus.START);
					}
				}else{
					bean.setStatus(AppStatus.DOWNLOAD);
				}
				IntentUtils.goWithBeanForResult(this, GameDetailActivity.class, bean,null);
			}
		}else if(requestType==IPlatformRequest.REQ_GIFT_ITEM){
			GiftItemResponse mResponse = (GiftItemResponse)response;
			GiftItemBean bean = mResponse.getGiftItemBean();
			if(bean.getCode().equals(HttpParam.RESULT_1000)){
				IntentUtils.goWithBean(mContext, GiftDetailActivity.class, bean);
			}
		}
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
//		mPushInfoBean = (PushInfoBean) getIntent().getSerializableExtra(Const.PUSH_KEY);
//		EfunLogUtil.logE("PushDispatcherActivity PushInfoBean is "+mPushInfoBean);
//		if (mPushInfoBean != null) {
////			SummaryItemBean mSummaryItemBean = new SummaryItemBean(BeanType.BEAN_SUMMARYITEMBEAN);
////			mSummaryItemBean.setIphoneUrl(mPushInfoBean.getUrl());
////			IntentUtils.go2Web(this, Web.WEB_GO_SUMMARY, mSummaryItemBean);
////			finish();
////			StatLogInfoUtils.setStaticPushInfo(mContext, mPushInfoBean.getMessageId());
//			switch (mPushInfoBean.getPushType()) {
//			case PushType.PUSH_TYPE_No1://资讯内容页面
//				SummaryItemBean mSummaryItemBean = new SummaryItemBean(BeanType.BEAN_SUMMARYITEMBEAN);
//				mSummaryItemBean.setIphoneUrl(mPushInfoBean.getUrl());
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
		mPushInfoStr = getIntent().getStringExtra(Const.PUSH_KEY);
		EfunLogUtil.logE("PushDispatcherActivity PushInfoBean is "+mPushInfoStr);
		
		if(mPushInfoStr != null){
			Map<String,String> data = transStringToMap(mPushInfoStr);
			String dataStr = data.toString();
			
			if(dataStr.contains(PushType.PUSH_TYPE_SUMMARY)){//资讯内容页面
				SummaryItemBean mSummaryItemBean = new SummaryItemBean(BeanType.BEAN_SUMMARYITEMBEAN);
				mSummaryItemBean.setIphoneUrl(data.get(PushType.PUSH_TYPE_SUMMARY));
				IntentUtils.go2Web(this, Web.WEB_GO_SUMMARY, mSummaryItemBean);
				finish();
			}else if(dataStr.contains(PushType.PUSH_TYPE_GAMELIST)){//游戏列表
				if(FrameTabContainer.lastTag != Tab.TAB_ITEM_TAG_GAMES || !data.get("curPageName").equals(FrameTabContainer.class.getName())){
					startActivity(new Intent(this, PushGameListActivity.class));
				}
				finish();
			}else if(dataStr.contains(PushType.PUSH_TYPE_GAMEDETAIL)){//游戏内容页面
				requestWithoutDialog(new BaseRequestBean[] { createRequest(PushType.PUSH_TYPE_GAMEDETAIL,data.get(PushType.PUSH_TYPE_GAMEDETAIL)) });
			}else if(dataStr.contains(PushType.PUSH_TYPE_GIFT_DETAIL)){//礼包内容页面
				requestWithoutDialog(new BaseRequestBean[] { createRequest(PushType.PUSH_TYPE_GIFT_DETAIL,data.get(PushType.PUSH_TYPE_GIFT_DETAIL)) });
			}else if(dataStr.contains(PushType.PUSH_TYPE_GIFTLIST)){//礼包列表页面
				if(!data.get("curPageName").equals(GiftListActivity.class.getName())){
					startActivity(new Intent(this, GiftListActivity.class));
				}
				finish();
			}else if(dataStr.contains(PushType.PUSH_TYPE_WELFARE)){//好康
				IPlatApplication.get().setHasHaoKangPush(true);
				finish();
			}else if(dataStr.contains(PushType.PUSH_TYPE_RECHARGE)){//储值
				IPlatApplication.get().setHasRechargePush(true);
				finish();
			}
		}
		
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		 super.onBackPressed();
	}

//	private BaseRequestBean createRequest(int pushType) {
//		switch (pushType) {
//		case PushType.PUSH_TYPE_No2:
//			break;
//		case PushType.PUSH_TYPE_No3:
//			GameItemRequest gameItemRequest = new GameItemRequest(
//					HttpParam.PLATFORM_AREA, 
//					mPushInfoBean.getPushGameCode(),
//					HttpParam.APP);
//			gameItemRequest.setReqType(IPlatformRequest.REQ_GAME_ITEM);
//			return gameItemRequest;
//		case PushType.PUSH_TYPE_No6:
//			String uid = "";
//			if(IPlatApplication.get().getUser()!=null){
//				uid =IPlatApplication.get().getUser().getUid();
//			}
//			GiftItemRequest giftItemRequest = new GiftItemRequest(
//					uid, mPushInfoBean.getPushGoodsId(),
//					HttpParam.APP);
//			if(IPlatApplication.get().getUser()!=null){
//				giftItemRequest.setSign(IPlatApplication.get().getUser().getSign());
//				giftItemRequest.setTimestamp(IPlatApplication.get().getUser().getTimestamp());
//			}
//			giftItemRequest.setReqType(IPlatformRequest.REQ_GIFT_ITEM);
//			return giftItemRequest;
//	}
//		return null;
//	}
	
	private BaseRequestBean createRequest(String pushType,String pushData) {
		if(pushType.equals(PushType.PUSH_TYPE_GAMEDETAIL)){
			GameItemRequest gameItemRequest = new GameItemRequest(
					HttpParam.PLATFORM_AREA, 
					pushData,
					HttpParam.APP);
			gameItemRequest.setReqType(IPlatformRequest.REQ_GAME_ITEM);
			return gameItemRequest;
		}else if(pushType.equals(PushType.PUSH_TYPE_GIFT_DETAIL)){
			String uid = "";
			if(IPlatApplication.get().getUser()!=null){
				uid =IPlatApplication.get().getUser().getUid();
			}
			GiftItemRequest giftItemRequest = new GiftItemRequest(
					uid, pushData,
					HttpParam.APP);
			if(IPlatApplication.get().getUser()!=null){
				giftItemRequest.setSign(IPlatApplication.get().getUser().getSign());
				giftItemRequest.setTimestamp(IPlatApplication.get().getUser().getTimestamp());
			}
			giftItemRequest.setReqType(IPlatformRequest.REQ_GIFT_ITEM);
			return giftItemRequest;
		}else{
			return null;
		}
	}
	
	public static Map transStringToMap(String mapString){  
		  Map map = new HashMap();  
		  java.util.StringTokenizer items;  
		  for(StringTokenizer entrys = new StringTokenizer(mapString, ",");entrys.hasMoreTokens();   
		    map.put(items.nextToken(), items.hasMoreTokens() ? ((Object) (items.nextToken())) : null))  
		      items = new StringTokenizer(entrys.nextToken(), "=");  
		  return map;  
		} 
	
}
