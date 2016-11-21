package com.efun.platform.module.utils;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.efun.facebook.share.activity.EfunFacebookCommonUtil;
import com.efun.platform.utils.Const.Share;
//import com.efun.facebook.share.self.FacebookSelfPluginImpl;
//import com.efun.facebook.share.self.FacebookSelfPluginImpl.FeedCallback;
import com.efun.twitter.plugin.TwitterCallback;
import com.efun.twitter.plugin.TwitterPluginImpl;

public class ShareUtils {

	/**
	 * google+分享
	 * 
	 * @param activity
	 */
	public static void shareGoogleJia(Activity activity, String text,
			String shareUrl) {
//		try {
////			EfunGoogleShare.getInstance().share(activity, text, shareUrl,
////					Type.NORMAL);
//			new GoogleSignIn((FragmentActivity) activity).share(text, shareUrl);
//		} catch (Exception e) {
//			ToastUtils.toast(activity, E_string.efun_pd_share_googlejia_error);
//		}
	}

	/**
	 * google+分享在onActivityResult生命周期方法中调用
	 * 
	 * @param activity
	 * @param requestCode
	 * @param resultCode
	 * @param data
	 */
//	public static void shareGoogleJiaOnActivityResult(Activity activity,
//			int requestCode, int resultCode, Intent data) {
//		EfunGoogleShare.getInstance().onActivityResult(activity, requestCode,
//				resultCode, data);
//	}

	/**
	 * line分享
	 * 
	 * @param context
	 * @param paramString
	 *            分享的內容
	 */
	public static void shareLine(Context context, String paramString) {
		AppUtils.openLineAPP(context, Share.SHARE_LINESHARE_URL + paramString);
		// context.startActivity(new Intent(Intent.ACTION_VIEW,
		// Uri.parse("http://line.naver.jp/R/msg/text/?" + paramString)));
	}

	/**
	 * facebook分享
	 * 
	 * @param context
	 * @param link
	 * @param picture
	 * @param name
	 * @param caption
	 * @param description
	 */
	// public static void shareFacebook(final Context context,String link,
	// String picture, String name, String caption, String
	// description,FeedCallback callBack){
	// // FacebookSelfPluginImpl.getInstance().feed(link, picture, name,
	// caption, description, callBack);
	// }

	public static void shareFacebook(Context context, String link,
			String picture, String name, String caption, String description) {
//		EfunFacebookCommonUtil.efunFacebookShare((Activity) context, link,
//				picture, name, caption, description);
		EfunFacebookCommonUtil.efunFacebookShare((Activity) context, link,
				picture, name, caption, description);
	}

	/**
	 * 只分享文字的接口
	 */
	public static void shareTwitterWithWord(final Context ctx, String msg) {
		TwitterPluginImpl imp = TwitterPluginImpl.get();
		imp.TwitterShareWord(ctx, msg, new TwitterCallback() {

			@Override
			public void shareSuccess() {
				Toast.makeText(ctx, "Twitter share success", Toast.LENGTH_LONG)
						.show();
			}

			@Override
			public void shareFailure() {
				Toast.makeText(ctx, "Twitter share failed", Toast.LENGTH_LONG)
						.show();
			}
		});
	}

	/**
	 * 文字和图片一起分享的接口
	 */
	public static void shareTwitterWithWordAndImage(final Context ctx, File Imagefile, String msg) {
		TwitterPluginImpl imp = TwitterPluginImpl.get();
		imp.TwitterShareWordAndImage(ctx, Imagefile, msg,
				new TwitterCallback() {

					@Override
					public void shareSuccess() {
						Toast.makeText(ctx, "Twitter share success",
								Toast.LENGTH_LONG).show();
					}

					@Override
					public void shareFailure() {
						Toast.makeText(ctx, "Twitter share failed",
								Toast.LENGTH_LONG).show();
					}
				});
	}
	
	
	public static void shareEmail(Context context,String emailSubject,String emailBody){
		//发邮件  
	         Intent email = new Intent(android.content.Intent.ACTION_SEND);  
	         email.setType("plain/text");  
	           
	         //设置邮件默认地址  
	        // email.putExtra(android.content.Intent.EXTRA_EMAIL, emailReciver);  
	         //设置邮件默认标题  
	         email.putExtra(android.content.Intent.EXTRA_SUBJECT, emailSubject);  
	         //设置要默认发送的内容  
	         email.putExtra(android.content.Intent.EXTRA_TEXT, emailBody);  
	         //调用系统的邮件系统  
	         ((Activity) context).startActivity(Intent.createChooser(email, "請選擇郵件分享"));  
	}

}
