package com.efun.platform.push;

import android.content.Context;

import com.efun.core.tools.EfunLogUtil;
import com.efun.core.tools.EfunStringUtil;
import com.efun.platform.utils.StoreUtil;
import com.efun.push.client.MessageDispatcher;


public class PushReceiver implements MessageDispatcher{
	
	@Override
	public void onMessage(Context context, String message) {
		String pushFlag = StoreUtil.getValue(context, StoreUtil.Param_file_name, StoreUtil.Param_key_push_flag);
		if(!EfunStringUtil.isEmpty(pushFlag)){
			if(pushFlag.equals("open")){
				EfunLogUtil.logD("Push--message:"+message);
				EfunPushNotificationManager	manager = EfunPushNotificationManager.get();
				PushInfoBean info = PushUtils.json2Object(message);
				if(info!=null){
					EfunLogUtil.logD("info is no null notification create");
					manager.createNotification(context,info);
				}
			}else{
				EfunLogUtil.logD("unPush--message:"+message);
			}
		}else{
			EfunLogUtil.logD("Push--message:"+message);
			EfunPushNotificationManager	manager = EfunPushNotificationManager.get();
			PushInfoBean info = PushUtils.json2Object(message);
			if(info!=null){
				EfunLogUtil.logD("info is no null notification create");
				manager.createNotification(context,info);
			}
		}
		
	}
		


	
}
