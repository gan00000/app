package com.efun.platform.module.account.activity;

import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;

import com.efun.core.tools.EfunLogUtil;
import com.efun.platform.AndroidScape.E_string;
import com.efun.platform.utils.Const.Key;
import com.efun.platform.utils.Const.ResultCode;
import com.facebook.HttpMethod;
import com.facebook.LoggingBehavior;
import com.facebook.Request;
import com.facebook.Request.GraphUserCallback;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.Settings;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphUser;

public class FacebookActivity extends FragmentActivity {
	
	private Session.StatusCallback statusCallback = new SessionStatusCallback();
	private static final List<String> PERMISSIONS_CLIENT = Arrays.asList("publish_actions");// 客服端分享，权限相对小
	private boolean isBindCome;
	private boolean unsupportedOperation = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Settings.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
		Session session = Session.getActiveSession();
		if (session == null) {
			if (savedInstanceState != null) {
				session = Session.restoreSession(this, null, statusCallback, savedInstanceState);
			}
			if (session == null) {
				session = new Session(this);
			}
			Session.setActiveSession(session);
			if (session.getState().equals(SessionState.CREATED_TOKEN_LOADED)) {
				session.openForRead(new Session.OpenRequest(this).setCallback(statusCallback));
			}
		}
		if (!session.isOpened() && !session.isClosed()) { // 非正常退出
			session.openForRead(new Session.OpenRequest(this).setCallback(statusCallback));
		} else {
			Session.openActiveSession(FacebookActivity.this, true, statusCallback);
		}
		dialog = new ProgressDialog(this);
		dialog.setMessage(getString(E_string.efun_pd_loading_msg_login));
		dialog.show();
	}


	private ProgressDialog dialog;

	private void executeMeRequestAsync(final Session session) {
		Request.newMeRequest(session, new GraphUserCallback() {

			@Override
			public void onCompleted(GraphUser user, Response response) {

				if (user != null) {
					// dialog.dismiss();
					String meId = user.getId();
					if (!TextUtils.isEmpty(meId)) {
						getBusinessIds(session, meId);
					}
				}
			}
		}).executeAsync();
	}

	private void getBusinessIds(Session session, final String meId) {
		Bundle b = new Bundle();
		b.putString("limit", "300");
		new Request(session, "/me/ids_for_business", b, HttpMethod.GET, new Request.Callback() {
			public void onCompleted(Response response) {
				String apps = "";
				try {
					if (response.getError() == null) {
						GraphObject bussesIdsGraphObject = response.getGraphObject();
						JSONObject InnerJSONObject = bussesIdsGraphObject.getInnerJSONObject();
						JSONArray dataArr = InnerJSONObject.optJSONArray("data");
						if (dataArr != null && dataArr.length() != 0) {
							StringBuilder stringBuilder = new StringBuilder();
							for (int i = 0; i < dataArr.length(); i++) {
								JSONObject mJsonObject = dataArr.optJSONObject(i);
								String scopeId = mJsonObject.optString("id", "");
								if (!TextUtils.isEmpty(scopeId)) {
									String appId = mJsonObject.optJSONObject("app") == null ? "" : mJsonObject.optJSONObject("app").optString("id",
											"");
									if (!TextUtils.isEmpty(appId)) {
										stringBuilder.append(scopeId + "_" + appId + ",");// 拼接scopeId和appId
									}
								}
							}
							apps = stringBuilder.substring(0, stringBuilder.lastIndexOf(","));
							EfunLogUtil.logE(apps);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(dialog!=null)
					dialog.dismiss();
				Intent it = new Intent();
				it.putExtra(Key.STRING_KEY, meId);
				it.putExtra(Key.APPS_KEY,apps);
				setResult(ResultCode.RST_ACCOUNT_FACEBOOK_LOGIN, it);
				finish();
				return;

			}
		}).executeAsync();
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Session.getActiveSession().onActivityResult(this, requestCode,resultCode, data);
		Session session = Session.getActiveSession();
		if (session!=null && !session.isOpened()) {
			this.finish();
		}
	}
	
	@Override
	public void onDestroy(){
		super.onDestroy();
		if(dialog!=null)
			dialog.dismiss();
		Session session = Session.getActiveSession();
		if (!session.isClosed()) {
            session.closeAndClearTokenInformation();
        }
	}
	private class SessionStatusCallback implements Session.StatusCallback {
		@Override
		public void call(Session session, SessionState state, Exception exception) {
			updateView();
		}
	}
	private void updateView() {
		Session session = Session.getActiveSession();
        if (session.isOpened()) {
	       executeMeRequestAsync(session);
        }

}
	
	
//	private Session.StatusCallback statusCallback = new SessionStatusCallback();
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		Settings.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
//		Session session = Session.getActiveSession();
//		if (session == null) {
//            if (savedInstanceState != null) {
//                session = Session.restoreSession(this, null, statusCallback, savedInstanceState);
//            }
//            if (session == null) {
//                session = new Session(this);
//            }
//            Session.setActiveSession(session);
//            if (session.getState().equals(SessionState.CREATED_TOKEN_LOADED)) {
//            	session.openForRead(new Session.OpenRequest(this).setCallback(statusCallback));
//            }
//        }
//		
//	    if (!session.isOpened() && !session.isClosed()) {
//	    	session.openForRead(new Session.OpenRequest(this).setCallback(statusCallback));
//	    } else {
//	        Session.openActiveSession(FacebookActivity.this, true, statusCallback);
//	    }
//	    dialog = new ProgressDialog(this);
//	    dialog.setMessage(getString(E_string.efun_pd_loading_msg_login));
//	    dialog.show();
//	}
//	

//	private ProgressDialog dialog ;
//	private void executeMeRequestAsync(Session session){
//		Request.newMeRequest(session, new Request.GraphUserCallback() {
//			@Override
//			public void onCompleted(GraphUser user,
//					Response response) {
//				if (user != null) {
//					String id = user.getId();
//					if (!TextUtils.isEmpty(id)){
//						Intent it = new Intent();
//						it.putExtra(Key.STRING_KEY, id);
//						setResult(ResultCode.RST_ACCOUNT_FACEBOOK_LOGIN, it);
//					}
//				}
//				if(dialog!=null)
//					dialog.dismiss();
//				finish();
//			}
//		}).executeAsync();
//	}
//
//	private void updateView() {
//		Session session = Session.getActiveSession();
//        if (session.isOpened()) {
//	       executeMeRequestAsync(session);
//        }
//    }
//	
//	private class SessionStatusCallback implements Session.StatusCallback {
//        @Override
//        public void call(Session session, SessionState state, Exception exception) {
//        	updateView();
//        }
//    }
//	
//	@Override
//	public void onBackPressed() {
//		// TODO Auto-generated method stub
//	}

}
