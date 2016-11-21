package com.efun.platform.module.utils;

import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.efun.google.MessageDispatcher;
import com.efun.platform.push.activity.PushDispatcherActivity;
import com.efun.platform.utils.Const;

public class MyFireBaseMessageDispatcher implements MessageDispatcher{

	@Override
	public boolean onMessage(Context context, String message,
			Map<String, String> data) {
		// TODO Auto-generated method stub
		Log.e("yang", "===========onMessage============");
		return false;
	}

	@Override
	public boolean onClickNotification(Context context, String message,
			Map<String, String> data) {
		// TODO Auto-generated method stub
		Log.e("yang", "===========onClickNotification============");
		Log.e("yang","message:"+message);
		Log.e("yang","data:"+data.toString());
		if(data != null){
			Object[] obj = AppUtils.isAppRunning(context, context.getPackageName());
			boolean isRunning = Boolean.parseBoolean(obj[0].toString());
			String topActivityName = obj[1].toString();
			data.put("curPageName", topActivityName);
			Log.e("yang", "topActivityName:"+topActivityName);
			Intent it = null;
			if(isRunning){
				Log.e("yang", "----------------isRunning");
				it = new Intent(context, PushDispatcherActivity.class);
				it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				it.putExtra(Const.PUSH_KEY,TextToolUtils.transMapToString(data));
			}
//			else{
//				Log.e("yang", "----------------isNOtRunning");
//				it = new Intent(Intent.ACTION_MAIN);
//				it.addCategory(Intent.CATEGORY_LAUNCHER);
//				it.setClass(context, LogoActivity.class);
//				it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);		
//				it.putExtra(Const.PUSH_KEY,data.toString());
//			}
			
			context.startActivity(it);
			
			return true;
		}else{
			return false;
		}
			
//			if(dataStr.contains(PushType.PUSH_TYPE_SUMMARY)){//资讯内容页面
//				SummaryItemBean mSummaryItemBean = new SummaryItemBean(BeanType.BEAN_SUMMARYITEMBEAN);
//				mSummaryItemBean.setIphoneUrl(data.get(PushType.PUSH_TYPE_SUMMARY));
//				IntentUtils.go2Web(context, Web.WEB_GO_SUMMARY, mSummaryItemBean);
//			}
////			else if(dataStr.contains(PushType.PUSH_TYPE_GAMELIST)){//游戏列表
////				if(FrameTabContainer.lastTag != Tab.TAB_ITEM_TAG_GAMES || !mPushInfoBean.getCurPageName().equals(FrameTabContainer.class.getName())){
////					context.startActivity(new Intent(context, PushGameListActivity.class));
////				}
////			}else if(dataStr.contains(PushType.PUSH_TYPE_GAMEDETAIL) || dataStr.contains(PushType.PUSH_TYPE_GIFT_DETAIL)){//游戏内容页面,//礼包内容页面
////				requestWithoutDialog(new BaseRequestBean[] { createRequest(mPushInfoBean.getPushType()) });
////			}else if(dataStr.contains(PushType.PUSH_TYPE_GIFTLIST)){//礼包列表页面
////				if(!mPushInfoBean.getCurPageName().equals(GiftListActivity.class.getName())){
////					context.startActivity(new Intent(context, GiftListActivity.class));
////				}
////			}
//			else if(dataStr.contains(PushType.PUSH_TYPE_WELFARE)){//好康
//				IPlatApplication.get().setHasHaoKangPush(true);
//			}else if(dataStr.contains(PushType.PUSH_TYPE_RECHARGE)){//储值
//				IPlatApplication.get().setHasRechargePush(true);
//			}else{
//				return false;
//			}
//			return true;
//		}
		
	}

	@Override
	public void onNotShowMessage(Context context, String message,
			Map<String, String> data) {
		// TODO Auto-generated method stub
		Log.e("yang", "===========onNotShowMessage============");
		
	}
	
}
