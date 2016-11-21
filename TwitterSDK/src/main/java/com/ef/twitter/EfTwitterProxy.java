package com.ef.twitter;

import java.lang.ref.WeakReference;

import com.efun.sdk.callback.EfunShareCallback;
import com.from.twitter.efun.share.EfunTwitterShare;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.services.common.CommonUtils;

public class EfTwitterProxy {

	private TwitterAuthClient authClient;
	WeakReference<Activity> activityRef;
	
	TwitterCallback loginCallback;
	Callback<TwitterSession> twitterSessionCallback;
	
	public void onCreate(Activity activity,String twitterKey,String twitterSecret){
		
		if (activity == null || TextUtils.isEmpty(twitterSecret) || TextUtils.isEmpty(twitterKey)) {
			callbackFailure("activity is null or twitterSecret or twitterKey is empty");
			return;
		}
		
		TwitterAuthConfig authConfig = new TwitterAuthConfig(twitterKey,twitterSecret);
		Fabric.with(activity, new Twitter(authConfig));
		
		this.authClient = new TwitterAuthClient();
		if (authClient == null) {
			callbackFailure("TwitterAuthClient is null");
			return;
		}
		
		twitterSessionCallback = new Callback<TwitterSession>() {
			
			@Override
			public void success(Result<TwitterSession> result) {
				// TODO Auto-generated method stub
				User u = new User();
				u.setId(result.data.getId() + "");
				u.setName(result.data.getUserName());
				if (loginCallback != null) {
					loginCallback.success(u);
				}
			}
			
			@Override
			public void failure(TwitterException arg0) {
				// TODO Auto-generated method stub
				if (arg0 != null) {
					callbackFailure("error:" + arg0.getMessage());
				}else{
					callbackFailure("error");
				}
			}
		};
		
	}

	public void loginWithTwitter(Activity activity,TwitterCallback loginCallback){
		
		this.loginCallback = loginCallback;
		
		
		if (authClient == null || twitterSessionCallback == null){
			CommonUtils.logOrThrowIllegalStateException("Twitter","authClient,twitterSessionCallback must not be null, did you call setCallback?");
			callbackFailure("authClient,twitterSessionCallback must not be null, did you call setCallback?");
			return;
		}
		
		authClient.authorize(activity,twitterSessionCallback);
	}
	
	
	public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
		
		if (authClient != null && requestCode == authClient.getRequestCode()){
			
			authClient.onActivityResult(requestCode, resultCode, data);
			
		}else if (requestCode == EfunTwitterShare.SHARE_TWITTER_REQUEST_CODE) {
			//推特分享回调
			if (efunShareCallback != null) {
				efunShareCallback.onShareSuccess(null);
			}
		}
		
	}
	
	EfunShareCallback efunShareCallback;
	
	public void twitterShare(Activity activity, String message, String picUrl, String url,EfunShareCallback efunShareCallback) {
		this.efunShareCallback = efunShareCallback;
		EfunTwitterShare.startTwitterShare(activity, message, picUrl, url);
	}
	
	
	
	private void callbackFailure(String msg){
		if (loginCallback != null) {
			loginCallback.failure(msg);
		}
	}
	
	public interface TwitterCallback{

		public void failure(String exception);

		public void success(User user) ;
	}
}
