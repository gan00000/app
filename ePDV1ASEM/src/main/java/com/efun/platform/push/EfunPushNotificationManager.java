package com.efun.platform.push;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.efun.core.tools.EfunLogUtil;
import com.efun.platform.AndroidScape.E_drawable;
import com.efun.platform.AndroidScape.E_string;
import com.efun.platform.module.logo.activity.LogoActivity;
import com.efun.platform.module.utils.AppUtils;
import com.efun.platform.push.activity.EfunPushDispatcherActivity;
import com.efun.platform.utils.Const;

public class EfunPushNotificationManager {
	private static EfunPushNotificationManager instance;
	public static EfunPushNotificationManager get(){
		if(instance==null){
			instance = new EfunPushNotificationManager();
		}
		return instance;
	}
	
	private PushInfoBean info;
	
	public void createNotification(Context context,PushInfoBean pushInfo){
		info = pushInfo;
		if(info!=null && context!=null){
			String titleStr = TextUtils.isEmpty(info.getTitle())?context.getString(E_string.app_name):info.getTitle();
			String contentStr=TextUtils.isEmpty(info.getContent())?context.getString(E_string.app_name):info.getContent();
			Object[] obj = AppUtils.isAppRunning(context, context.getPackageName());
			boolean isRunning = Boolean.parseBoolean(obj[0].toString());
			String topActivityName = obj[1].toString();
			info.setCurPageName(topActivityName);
			
			Intent it = null;
			if(isRunning){
				it = new Intent(context, EfunPushDispatcherActivity.class);
//				it.putExtra(Const.EFUN_PUSH_KEY,info);
			}else{
				it = new Intent(Intent.ACTION_MAIN);
				it.addCategory(Intent.CATEGORY_LAUNCHER);
				it.setClass(context, LogoActivity.class);
				it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);		
//				it.putExtra(Const.EFUN_PUSH_KEY,info);
			}
			
			PendingIntent intent = PendingIntent.getActivity(context, 0, it, PendingIntent.FLAG_UPDATE_CURRENT);
			
			Notification notification = new Notification();
			notification.icon = E_drawable.efun_pd_app_name;
			notification.flags|=Notification.FLAG_AUTO_CANCEL;//点击通知后自动清除通知
			notification.defaults = Notification.DEFAULT_ALL;
			notification.setLatestEventInfo(context, titleStr, contentStr, intent);

			NotificationManager manager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
			manager.notify((int)info.getMessageId(), notification);
		}
	}
	
	public void dismissNotification(Context context){
		if(info!=null && context!=null){
			NotificationManager manager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
			manager.cancel((int)info.getMessageId());
		}
	}
	
	
}
