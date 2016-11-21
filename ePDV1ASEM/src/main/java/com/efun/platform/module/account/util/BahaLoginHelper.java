package com.efun.platform.module.account.util;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;

import com.efun.core.tools.EfunLogUtil;
import com.efun.platform.module.account.view.WebDialog;
import com.efun.platform.module.account.view.WebDialog.OnCompleteListener;
/**
 * Baha登陆
 * @author itxuxxey
 *
 */
public class BahaLoginHelper {
//	private static String mConsumer_key;
//	private static String mConsumer_secret;
	private Context con;
	private static final String oAuthUrl = "http://user.gamer.com.tw/api/oauth2/oauth2_authorize.php?response_type=code&client_id=";
	
	public BahaLoginHelper(Context paramContext) {
		this.con = paramContext;
	}

	public void bahaLogin(String consumer_key, final BahaLoginCallback paramBahaLoginCallback) {
		if(TextUtils.isEmpty(consumer_key)) {
			EfunLogUtil.logD("baha login consumer_key is empty!!");
			return;
		}
		String url = oAuthUrl + consumer_key;
		WebDialog dialog = new WebDialog(con, url, WebDialog.DEFAULT_THEME, new OnCompleteListener(){
			@Override
			public void onComplete(Bundle values) {
				if(values != null) {
					String baha_uid = values.getString("bahaencodeid");
					if(paramBahaLoginCallback != null) {
						paramBahaLoginCallback.onBahaLoginFinished(baha_uid);
					}
				}
			}
			
		});
		
		dialog.show();
	}

	
	
	
	
	
	public static interface BahaLoginCallback {
		public void onBahaLoginFinished(String uid);
	}
}