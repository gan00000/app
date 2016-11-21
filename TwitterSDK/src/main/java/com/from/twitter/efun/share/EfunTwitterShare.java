package com.from.twitter.efun.share;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import com.efun.core.tools.EfunLogUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.text.TextUtils;

public class EfunTwitterShare {
	
	public static int SHARE_TWITTER_REQUEST_CODE = 1001;
	private static final String tag = "EfunTwitterShare";

	public static Intent createTwitterIntent(Context context, String msg, String linkUrl, String imageUri) {
		Intent intent = new Intent("android.intent.action.SEND");

		StringBuilder builder = new StringBuilder();

		if (!TextUtils.isEmpty(msg)) {
			builder.append(msg);
		}

		if (!TextUtils.isEmpty(linkUrl)) {
			if (builder.length() > 0) {
				builder.append(' ');
			}
			builder.append(linkUrl);
		}

		intent.putExtra("android.intent.extra.TEXT", builder.toString());
		intent.setType("text/plain");

		if (!TextUtils.isEmpty(imageUri)) {
			intent.putExtra("android.intent.extra.STREAM", Uri.parse(imageUri));
			intent.setType("image/jpeg");
		}

		PackageManager packManager = context.getPackageManager();
		List<ResolveInfo> resolvedInfoList = packManager.queryIntentActivities(intent, 65536);

		for (ResolveInfo resolveInfo : resolvedInfoList) {
			if (resolveInfo.activityInfo.packageName.startsWith("com.twitter.android")) {
				intent.setClassName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name);

				return intent;
			}
		}

		return null;
	}

	public static Intent createWebIntent(String msg, String linkUrl) {
		String url = TextUtils.isEmpty(linkUrl) ? "" : linkUrl;
		String message = TextUtils.isEmpty(msg) ? "" : msg;

		String tweetUrl = String.format("https://twitter.com/intent/tweet?text=%s&url=%s",
				new Object[] {urlEncode(message), urlEncode(url) });

		return new Intent("android.intent.action.VIEW", Uri.parse(tweetUrl));
	}

	public static String urlEncode(String s){
		if (!TextUtils.isEmpty(s)) {
			try {
				return URLEncoder.encode(s, "UTF8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return "";
	}

	
	public static void startTwitterShare(Activity activity, String message, String picUrl, String url) {
		
		Intent twitterIntent = createTwitterIntent(activity, message, url, picUrl);
		if (twitterIntent != null) {
			EfunLogUtil.logI(tag, "使用twitter客戶端分享");
			activity.startActivityForResult(twitterIntent, SHARE_TWITTER_REQUEST_CODE);
		} else {
			EfunLogUtil.logI(tag, "使用web分享");
			Intent webIntent = createWebIntent(message, url);
			activity.startActivityForResult(webIntent, SHARE_TWITTER_REQUEST_CODE);
		}
	}
}
