package com.facebook.efun;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.facebook.AppEventsLogger;
import com.facebook.FacebookException;
import com.facebook.HttpMethod;
import com.facebook.LoggingBehavior;
import com.facebook.Request;
import com.facebook.Request.GraphUserListCallback;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.Settings;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphUser;
import com.facebook.widget.WebDialog;
import com.facebook.widget.WebDialog.OnCompleteListener;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

public class EfunFacebookProxy {

	protected static final String TAG = "EfunFacebookProxy";

	int fb_action = 0;
	Context context;
	Activity activity;
	private static List<String> permissions = Arrays.asList(new String[]{"public_profile", "user_friends","email"});
	
	EfunFbLoginCallBack fbLoginCallBack;
	EfunFbShareCallBack efunFbShareCallBack;
	EfunFbMyFriendsCallBack efunFbMyFiendsCallBack;
	EfunFbGetInviteFriendsCallBack efunFbGetInviteFriendsCallBack;
	EfunFbBusinessIdCallBack efunFbBusinessIdCallBack;
	EfunFbInviteFriendsCallBack efunFbInviteFriendsCallBack;
	
	Bundle savedInstanceState;
	private Session.StatusCallback statusCallback = new SessionStatusCallback();
	
	
	public EfunFacebookProxy(Context context) {
		this.context = context;
	}

	public void initFbSdk(Context context) {
	}
	
	
	public static void activateApp(Context context){
		
		if (TextUtils.isEmpty(ResHelper.findStringByName(context,"efunFBApplicationId"))) {
			Toast.makeText(context, "fb applicationId is empty", Toast.LENGTH_LONG).show();
		}else{
			activateApp(context, ResHelper.findStringByName(context,"efunFBApplicationId"));
		}
		
	}
	
	public static void activateApp(Context context,String appId){
		AppEventsLogger.activateApp(context, appId);
	}
	

	/**
	 * <p>
	 * Description: fb 登陆
	 * </p>
	 * 
	 * @param activity
	 * @param fbLoginCallBack
	 * @date 2015年11月20日
	 */
	public void fbLogin(Activity activity, final EfunFbLoginCallBack fbLoginCallBack) {
		//fb_action = 0;
		this.activity = activity;
		this.fbLoginCallBack = fbLoginCallBack;
		authorizationRequestActivityResult = false;
		Settings.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
		Session session = Session.getActiveSession();
		if (session == null) {
			if (savedInstanceState != null) {
				session = Session.restoreSession(activity, null, statusCallback, savedInstanceState);
			}
			if (session == null) {
				session = new Session(activity);
			}
			Session.setActiveSession(session);
			if (session.getState().equals(SessionState.CREATED_TOKEN_LOADED)) {
				session.openForRead(new Session.OpenRequest(activity).setCallback(statusCallback).setPermissions(permissions));
			}
		}

		if (!session.isOpened() && !session.isClosed()) {
			session.openForRead(new Session.OpenRequest(activity).setCallback(statusCallback).setPermissions(permissions));
		} else {
			Session.openActiveSession(activity, true,permissions, statusCallback);
		}

	}
	
	

	public void onCreate(Activity activity, Bundle savedInstanceState) {
		this.savedInstanceState = savedInstanceState;
	}

	boolean authorizationRequestActivityResult = false;
	public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
		if (Session.getActiveSession() != null && Session.getActiveSession().onActivityResult(activity, requestCode, resultCode, data)) {
			authorizationRequestActivityResult = true;
			Log.d(TAG, "fb onActivityResult");	
			// Session session = Session.getActiveSession();
		}

	}

	public void onDestroy(Activity activity) {
		Session session = Session.getActiveSession();
		if (session != null && !session.isClosed()) {
			session.closeAndClearTokenInformation();
		}

	}

	private class SessionStatusCallback implements Session.StatusCallback {
		@Override
		public void call(Session session, SessionState state, Exception exception) {
			Log.d(TAG, session.toString());		
			callbackSession = session;
			if (session == null || !session.isOpened()) {
				Log.d(TAG, "session == null || !session.isOpened()");
				if (authorizationRequestActivityResult) {
					String errorMsg = "";
					if (exception != null) {
						errorMsg = exception.getMessage();
					}
					Log.d(TAG, "errorMsg:" + errorMsg);
					switch (fb_action) {
					case 0:
						if (fbLoginCallBack != null) {
							fbLoginCallBack.onError(errorMsg);
						}
						break;
					case 1:
						if (efunFbMyFiendsCallBack != null) {
							efunFbMyFiendsCallBack.onError();
						}
						break;
					case 2:
						if (efunFbShareCallBack != null) {
							efunFbShareCallBack.onError(errorMsg);
						}
						break;
					case 3:
						if (efunFbGetInviteFriendsCallBack != null) {
							efunFbGetInviteFriendsCallBack.onError();
						}
						break;
					case 4:
						if (efunFbInviteFriendsCallBack != null) {
							efunFbInviteFriendsCallBack.onError(errorMsg);
						}
						break;

					default:
						if (fbLoginCallBack != null) {
							fbLoginCallBack.onError(errorMsg);
						}
						break;
					}
					
					authorizationRequestActivityResult = false;
				}
				return;
			}
			Log.d(TAG, "session is ok");		
			updateCallback(session);
		}
	}

	//FB SessionStatusCallback 回调
	public void updateCallback(Session session) {
	
		switch (fb_action) {
		case 0:
			executeMeRequestAsync(session);
			break;
		case 1:
			executeMyFriend(session);
			break;
		case 2:
			executefbShare(session);
			break;
		case 3:
			executeGetInviteFriends(session);
			break;
		case 4:
			executeInviteFriend(session);
			break;

		default:
			break;
		}
	}

	private void executeMeRequestAsync(Session session) {

		Request.newMeRequest(session, new Request.GraphUserCallback() {
			@Override
			public void onCompleted(GraphUser user, Response response) {
				fb_action = 0;
				if (fbLoginCallBack == null) {
					return;
				}
				if (user != null && !TextUtils.isEmpty(user.getId())) {
					Log.d(TAG, "at executeMeRequestAsync userId = " + user.getId());
					User mUser = new User();
					mUser.setUserId(user.getId());
					mUser.setName(user.getName());
					mUser.setFirstName(user.getFirstName());
					fbLoginCallBack.onSuccess(mUser);
				} else {
					fbLoginCallBack.onError(response.getError().getErrorMessage());
				}
				fb_action = 0;
			}
		}).executeAsync();

	}

	private void executeMyFriend(Session session){
		com.facebook.Request.newMyFriendsRequest(session, new GraphUserListCallback() {

			@Override
			public void onCompleted(List<GraphUser> users, Response response) {
				Log.d(TAG, "newMyFriendsRequest response:" + response.getRawResponse());
				fb_action = 0;
				if (efunFbMyFiendsCallBack == null) {
					return;
				}
				if (users == null) {
					Log.d(TAG, "get friend list failed!");
					efunFbMyFiendsCallBack.onError();
					return;
				}
				JSONObject rawJsonObj = response.getGraphObject().getInnerJSONObject();
				efunFbMyFiendsCallBack.onSuccess(rawJsonObj.optJSONArray("data"), rawJsonObj);
				fb_action = 0;
			}
		}).executeAsync();
	}
	
	private void executeGetInviteFriends(Session session){

		if (requestInviteFriendsBundle == null) {
			requestInviteFriendsBundle = new Bundle();
			requestInviteFriendsBundle.putString("limit", "2000");
			requestInviteFriendsBundle.putString("fields", "name,picture.width(300)");
		}
		new Request(session, "/me/invitable_friends", requestInviteFriendsBundle, HttpMethod.GET, new com.facebook.Request.Callback() {

			@Override
			public void onCompleted(Response response) {
				fb_action = 0;
				if (response == null && efunFbGetInviteFriendsCallBack != null) {
					efunFbGetInviteFriendsCallBack.onError();
					return;
				}
				Log.d(TAG, "executeGetInviteFriends response:" + response.getRawResponse());
				List<InviteFriend> inviteFriends = new ArrayList<InviteFriend>();
				GraphObject go = response.getGraphObject();
				JSONObject jo = go.getInnerJSONObject();
				try {

					JSONArray ja = jo.optJSONArray("data");
					for (int i = 0; i < ja.length(); i++) {
						
						JSONObject user = ja.getJSONObject(i);
						
						String id = user.optString("id");
						String name = user.optString("name");
						String picUrl = "";

						JSONObject friendsItemPictureData = user.optJSONObject("picture").optJSONObject("data");
					
						InviteFriendPicture inviteFriendPicture = null;
						if (friendsItemPictureData != null) {
							inviteFriendPicture = new InviteFriendPicture();
							int height = friendsItemPictureData.optInt("height", 0);
							int width = friendsItemPictureData.optInt("width", 0);
							String url = friendsItemPictureData.optString("url", "");
							boolean is_silhouette = friendsItemPictureData.optBoolean("is_silhouette", false);
							inviteFriendPicture.setHeight(height);
							inviteFriendPicture.setWidth(width);
							inviteFriendPicture.setIs_silhouette(is_silhouette);
							inviteFriendPicture.setUrl(url);
						}
						InviteFriend inviteFriend = new InviteFriend();
						inviteFriend.setId(id);
						inviteFriend.setName(name);
						inviteFriend.setInviteFriendPicture(inviteFriendPicture);
						inviteFriends.add(inviteFriend);
					}
					
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				fb_action = 0;
				if (efunFbGetInviteFriendsCallBack != null) {
					efunFbGetInviteFriendsCallBack.onSuccess(jo, inviteFriends);
				}
			}
		
		}).executeAsync();
	}
	
	/**
	 * 弹框分享
	 */
	WebDialog webDialog = null;
	String link = "";
	String picture = "";
	String name = "";
	String caption = "";
	String description = "";
	
	private void executefbShare(Session session) {

		Log.d(TAG, "feed link=" + link + " picture=" + picture + " name=" + name + " caption=" + caption + " description=" + description);
		com.facebook.widget.WebDialog.FeedDialogBuilder webBuilder = new com.facebook.widget.WebDialog.FeedDialogBuilder(context,
				session);
		webBuilder.setOnCompleteListener(new OnCompleteListener() {

			@Override
			public void onComplete(Bundle data, FacebookException error) {
				Log.d(TAG, "executefbShare response");
				fb_action = 0;
				if (efunFbShareCallBack == null) {
					return;
				}
				efunFbShareCallBack.onSuccess();
				/*if (data != null && error == null) {
					efunFbShareCallBack.onSuccess();
				}else{
					efunFbShareCallBack.onError(error.getMessage());
				}*/
				if (error != null) {
					//error.printStackTrace();
					Log.d(TAG, error.getMessage() + "");
				}
				if (webDialog != null) {
					webDialog.dismiss();
				}
			}
		});

		webDialog = webBuilder.setLink(link).setPicture(picture).setName(name).setCaption(caption).setDescription(description).build();
		if (webDialog != null) {
			webDialog.show();
		}
		
	}
	
	private void executeGetBusinessIds(Session session,String meId) {
		Bundle b = new Bundle();
		b.putString("limit", "300");
		new Request(session, "/me/ids_for_business", b, HttpMethod.GET, new Request.Callback() {
			public void onCompleted(Response response) {
				fb_action = 0;
				Log.d(TAG, "getBuisses response:" + response.toString());
				String apps = "";
				try {
					if (response.getError() == null) {
						GraphObject bussesIdsGraphObject = response.getGraphObject();
						JSONObject InnerJSONObject = bussesIdsGraphObject.getInnerJSONObject();
					//	Log.d(TAG, "getBuisses InnerJSONObject:" + InnerJSONObject.toString());
						JSONArray dataArr = InnerJSONObject.optJSONArray("data");
						if (dataArr != null && dataArr.length() != 0) {
							StringBuilder stringBuilder = new StringBuilder();
							for (int i = 0; i < dataArr.length(); i++) {
								JSONObject mJsonObject = dataArr.optJSONObject(i);
								String scopeId = mJsonObject.optString("id", "");
								if (!TextUtils.isEmpty(scopeId)) {
									String appId = mJsonObject.optJSONObject("app") == null ? "" : mJsonObject.optJSONObject("app").optString("id", "");
									if (!TextUtils.isEmpty(appId)) {
										stringBuilder.append(scopeId + "_" + appId + ",");// 拼接scopeId和appId
									}
								}
							}
							apps = stringBuilder.substring(0, stringBuilder.lastIndexOf(","));
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				activity.getSharedPreferences(EFUN_FILE, Activity.MODE_PRIVATE).edit().putString(EFUN_APP_BUSINESS_IDS, apps).commit();
				Log.d(TAG, "getBuisses apps:" + apps);
				if (efunFbBusinessIdCallBack != null) {
					efunFbBusinessIdCallBack.onSuccess(apps);
				}

			}
		}).executeAsync();
	}
	
	String inviteToFriendIds;
	String inviteContent;
	 
	private void executeInviteFriend(Session session){
		
		Bundle params = new Bundle();
		params.putString("message", "inviting ...");
		WebDialog.RequestsDialogBuilder rdb = new WebDialog.RequestsDialogBuilder(activity, session, params);
		
		
		rdb.setTo(inviteToFriendIds);
		
		rdb.setMessage(inviteContent);
		WebDialog requestDialog = rdb.setOnCompleteListener(new OnCompleteListener() {

			@Override
			public void onComplete(Bundle bundle, FacebookException error) {
				fb_action = 0;
				if (error == null && bundle != null) {

					Log.d(TAG, "invite response---> " + bundle.toString());
					String requestId = bundle.getString("request");

					if (requestId != null) {
						List<String> requestRecipients = new ArrayList<String>();
						for (String s : bundle.keySet()) {
							if (!s.equals("request")) {
								String temp = bundle.getString(s);
								requestRecipients.add(temp);
							}
						}

						if (efunFbInviteFriendsCallBack != null) {
							efunFbInviteFriendsCallBack.onSuccess(requestId, requestRecipients);
						}
						return;
					}

				}
				if (efunFbInviteFriendsCallBack != null && error != null) {
					efunFbInviteFriendsCallBack.onError(error.getMessage());
				}
			}
			
		}).build();
		requestDialog.show();
	}
	
	
/*	private boolean canPresentShareDialogWithPhotos;// 能否弹出客户端分享窗口（是否有装客户端）

	private void executeShareLocalPhoto(Activity activity, Bitmap[] bitMapImages) {
		
		fb_action = 0;
		if (bitMapImages == null && bitMapImages.length < 1) {
			efunFbShareCallBack.onError("bitMap is null");
			return;
		}
		canPresentShareDialogWithPhotos = FacebookDialog.canPresentShareDialog(activity, FacebookDialog.ShareDialogFeature.PHOTOS);
		if (canPresentShareDialogWithPhotos) {// 弹出分享窗口
			Log.d(TAG, "has client");
			FacebookDialog shareDialog = new FacebookDialog.PhotoShareDialogBuilder(activity).addPhotos(Arrays.asList(bitMapImages)).build();
			shareDialog.present();
			// uiHelper.trackPendingDialogCall();
		} else if (hasPublishPermission()) {// 直接分享
			Log.d(TAG, "no client");
			Request request = Request.newUploadPhotoRequest(Session.getActiveSession(), bitMapImages[0], new Request.Callback() {
				@Override
				public void onCompleted(Response response) {
					Log.d(TAG, "executeShareLocalPhoto response:" + response.toString());
					if (response.getError() == null) {
						Log.d(TAG, "success");
						efunFbShareCallBack.onSuccess();
					} else {
						Log.d(TAG, "error");
						efunFbShareCallBack.onError(response.getError().getErrorMessage());
					}
				}
			});
			request.executeAsync();
		}else{
			efunFbShareCallBack.onError("not Facebook native app and not publish_actions permission");
		}

	}
	
	*//**
	 * 是否已经授权
	 * 
	 * @return
	 *//*
	private boolean hasPublishPermission() {
		Session session = Session.getActiveSession();
		return session != null && session.getPermissions().contains("publish_actions");
	}
	*/
	public void fbLogout(Activity activity) {
		Session session = Session.getActiveSession();
		if (session != null && !session.isClosed()) {
			session.closeAndClearTokenInformation();
		}
	}
	
	public void fbShare(Activity activity, final EfunFbShareCallBack efunFbShareCallBack, String shareCaption, String shareDescrition,
			String shareLinkUrl, String sharePictureUrl) {
		/*this.efunFbShareCallBack = efunFbShareCallBack;
		this.description = contentDescription;
		this.caption = contentTitle;
		this.link = contentUrl;
		this.picture = imageUrl;
		fb_action = 2;
		fbLogin(activity, null);*/
		
		fbShare(activity, efunFbShareCallBack, shareCaption, shareDescrition, shareLinkUrl, sharePictureUrl, "");
	}
	public void fbShare(Activity activity, final EfunFbShareCallBack efunFbShareCallBack, String shareCaption, String shareDescrition,
			String shareLinkUrl, String sharePictureUrl,String shareLinkName) {
		this.efunFbShareCallBack = efunFbShareCallBack;
		this.description = shareDescrition;
		this.caption = shareCaption;
		this.link = shareLinkUrl;
		this.picture = sharePictureUrl;
		this.name = shareLinkName;
		fb_action = 2;
		fbLogin(activity, null);
	}

	
	
	public void shareLocalPhotos(Activity activity, final EfunFbShareCallBack efunFbShareCallBack, Bitmap ...images) {
		this.efunFbShareCallBack = efunFbShareCallBack;
	//fb_action = 5;
	//	executeShareLocalPhoto(activity, images);
		Log.w(TAG, "Facebook 3.x SDK不支持实现分享本地图片功能，请使用4.x版本");
	}
	
	public void shareLocalPhoto(Activity activity, final EfunFbShareCallBack efunFbShareCallBack, Bitmap bitmap) {
		if (bitmap == null) {
			shareLocalPhotos(activity, efunFbShareCallBack, new Bitmap[] {});
		}else{
			shareLocalPhotos(activity, efunFbShareCallBack, new Bitmap[] { bitmap });
		}
	}
	
	public void shareLocalPhoto(Activity activity, final EfunFbShareCallBack efunFbShareCallBack, String imagePath) {
		Bitmap image = BitmapFactory.decodeFile(imagePath);
		shareLocalPhoto(activity, efunFbShareCallBack, image);
	}
	
	/**
	 * <p>
	 * Description: 获取可邀请的好友
	 * </p>
	 * 
	 * @param activity
	 * @param b
	 * @param efunFbGetInviteFriendsCallBack
	 * @date 2016年1月30日
	 */
	private Bundle requestInviteFriendsBundle;

	public void requestInviteFriends(Activity activity, Bundle b,  EfunFbGetInviteFriendsCallBack efunFbGetInviteFriendsCallBack) {
		fb_action = 3;
		this.requestInviteFriendsBundle = requestInviteFriendsBundle;
		this.efunFbGetInviteFriendsCallBack = efunFbGetInviteFriendsCallBack;
		fbLogin(activity, null);
	}

	public void inviteFriends(Activity activity, List<InviteFriend> inviteFriendIdsList, String message, EfunFbInviteFriendsCallBack efunFbInviteFriendsCallBack) {
		fb_action = 4;
		StringBuilder stringBuilder = new StringBuilder();
		if (inviteFriendIdsList != null && !inviteFriendIdsList.isEmpty()) {
			for (InviteFriend inviteFriend : inviteFriendIdsList) {
				stringBuilder.append(inviteFriend.getId()).append(",");
			}
		}
		
		inviteFriends(activity, stringBuilder.toString(), message, efunFbInviteFriendsCallBack);
	}

	
	public void inviteFriends(Activity activity, String inviteFriendIds, String message, EfunFbInviteFriendsCallBack efunFbInviteFriendsCallBack) {
		fb_action = 4;
		this.inviteToFriendIds = inviteFriendIds;
		this.inviteContent = message;
		this.efunFbInviteFriendsCallBack = efunFbInviteFriendsCallBack;
		fbLogin(activity, null);
	}

	public void requestMyFriends(Activity activity, final EfunFbMyFriendsCallBack efunFbMyFiendsCallBack) {
		fb_action = 1;
		this.efunFbMyFiendsCallBack = efunFbMyFiendsCallBack;
		fbLogin(activity, null);
	}

	Session callbackSession;
	public void requestBusinessId(Activity activity, EfunFbBusinessIdCallBack businessIdCallBack) {
		
		this.efunFbBusinessIdCallBack = businessIdCallBack;
		fbLogin(activity, new EfunFbLoginCallBack() {
			
			@Override
			public void onSuccess(User user) {
				executeGetBusinessIds(callbackSession, user.getUserId());
			}
			
			@Override
			public void onError(String message) {
				if (efunFbBusinessIdCallBack != null) {
					efunFbBusinessIdCallBack.onError();
				}
				
			}
			
			@Override
			public void onCancel() {
				if (efunFbBusinessIdCallBack != null) {
					efunFbBusinessIdCallBack.onError();
				}
				
			}
		});
	}

	public static final String EFUN_FILE = "Efun.db";
	public static final String EFUN_APP_BUSINESS_IDS = "EFUN_APP_BUSINESS_IDS";

	public interface EfunFbShareCallBack {
		public void onCancel();

		public void onError(String message);

		public void onSuccess();
	}

	public interface EfunFbLoginCallBack {
		public void onCancel();

		public void onError(String message);

		public void onSuccess(User user);
	}

	/**
	 * <p>
	 * Title: EfunFbInviteFriendsCallBack
	 * </p>
	 * <p>
	 * Description: 请求好友的回调接口
	 * </p>
	 * <p>
	 * Company: EFun
	 * </p>
	 * 
	 * @author GanYuanrong
	 * @date 2015年11月23日
	 */
	public interface EfunFbInviteFriendsCallBack {
		public void onCancel();

		public void onError(String message);

		public void onSuccess(String requestId, List<String> requestRecipients);
	}

	/**
	 * <p>
	 * Title: EfunFbGetInviteFriendsCallBack
	 * </p>
	 * <p>
	 * Description: 获取好友的回调接口
	 * </p>
	 * <p>
	 * Company: EFun
	 * </p>
	 * 
	 * @author GanYuanrong
	 * @date 2015年11月23日
	 */
	public interface EfunFbGetInviteFriendsCallBack {
		public void onError();

		public void onSuccess(JSONObject graphObject, List<InviteFriend> inviteFriends);
	}

	public interface EfunFbMyFriendsCallBack {
		public void onError();

		public void onSuccess(JSONArray objects, JSONObject graphObject);
	}

	public interface EfunFbBusinessIdCallBack {
		public void onError();

		public void onSuccess(String businessId);
	}

	public class User {

		String userId;

		/**
		 * @return the userId
		 */
		public String getUserId() {
			return userId;
		}

		/**
		 * @param userId
		 *            the userId to set
		 */
		public void setUserId(String userId) {
			this.userId = userId;
		}

		private String firstName;
		private String middleName;
		private String lastName;
		private String name;
		private Uri linkUri;

		/**
		 * @return the firstName
		 */
		public String getFirstName() {
			return firstName;
		}

		/**
		 * @param firstName
		 *            the firstName to set
		 */
		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		/**
		 * @return the middleName
		 */
		public String getMiddleName() {
			return middleName;
		}

		/**
		 * @param middleName
		 *            the middleName to set
		 */
		public void setMiddleName(String middleName) {
			this.middleName = middleName;
		}

		/**
		 * @return the lastName
		 */
		public String getLastName() {
			return lastName;
		}

		/**
		 * @param lastName
		 *            the lastName to set
		 */
		public void setLastName(String lastName) {
			this.lastName = lastName;
		}

		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * @param name
		 *            the name to set
		 */
		public void setName(String name) {
			this.name = name;
		}

		/**
		 * @return the linkUri
		 */
		public Uri getLinkUri() {
			return linkUri;
		}

		/**
		 * @param linkUri
		 *            the linkUri to set
		 */
		public void setLinkUri(Uri linkUri) {
			this.linkUri = linkUri;
		}

	}

}
