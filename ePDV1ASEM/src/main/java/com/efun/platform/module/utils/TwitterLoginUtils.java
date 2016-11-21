package com.efun.platform.module.utils;

import io.fabric.sdk.android.Fabric;

import java.lang.ref.WeakReference;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;

public class TwitterLoginUtils {
	private Context mContext;
	static final String TAG = "Twitter";
	static final String ERROR_MSG_NO_ACTIVITY = "TwitterLoginButton requires an activity. Override getActivity to provide the activity for this button.";
	WeakReference<Activity> activityRef;
	volatile static TwitterAuthClient authClient;
	private static final String TWITTER_KEY = "MQIv2371pRLBVhtr0RUCIXKFn";
	private static final String TWITTER_SECRET = "pSiZ5F0Ksv0F4M0NTOvgQN5ULdlU25n720EfxlUhI50X6e9OWF";
	
	//初始化
	public static void initTwitterLogin(Context context){
		TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY,
				TWITTER_SECRET);
		Fabric.with(context, new Twitter(authConfig));
		try {
			TwitterCore.getInstance();
		} catch (IllegalStateException ex) {
			Fabric.getLogger().e("Twitter", ex.getMessage());
		}
		authClient = getTwitterAuthClient(context);
	}
	
	/**
	 * onActivityResult回调
	 * @param context
	 * @param requestCode
	 * @param resultCode
	 * @param data
	 */
	public static void onActivityResult(Context context, int requestCode, int resultCode, Intent data){
		if (requestCode == getTwitterAuthClient(context).getRequestCode())
			getTwitterAuthClient(context).onActivityResult(requestCode, resultCode,
					data);
	}
	
	private static TwitterAuthClient getTwitterAuthClient(Context context) {
		if (authClient == null) {
			synchronized (context) {
				if (authClient == null) {
					authClient = new TwitterAuthClient();
				}
			}
		}
		return authClient;
	}
	
	/**
	 * twitter登录
	 * @param activity
	 * @param callback
	 */
	public static void twitterLogin(Activity activity, Callback<TwitterSession> callback){
		getTwitterAuthClient(activity).authorize(activity, callback);
	}
}
