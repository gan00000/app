package com.efun.platform;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.Toast;

import com.appsflyer.AppsFlyerLib;
import com.efun.core.tools.EfunLogUtil;
import com.efun.game.tw.R;
import com.efun.google.EfunGoogleProxy;
import com.efun.platform.AndroidScape.E_drawable;
import com.efun.platform.AndroidScape.E_id;
import com.efun.platform.AndroidScape.E_layout;
import com.efun.platform.AndroidScape.E_string;
import com.efun.platform.module.base.impl.OnEfunItemClickListener;
import com.efun.platform.module.base.impl.OnLoginFinishListener;
import com.efun.platform.module.cs.CustomServiceFragment;
import com.efun.platform.module.game.GamesFragment;
import com.efun.platform.module.person.PersonalCenterFragment;
import com.efun.platform.module.summary.SummaryFragment;
import com.efun.platform.module.utils.AppUtils;
import com.efun.platform.module.utils.ToastUtils;
import com.efun.platform.module.utils.TrackingGoogleUtils;
import com.efun.platform.module.utils.TrackingUtils;
import com.efun.platform.module.utils.UserUtils;
import com.efun.platform.module.utils.ViewUtils;
import com.efun.platform.module.welfare.HaoKangFragment;
import com.efun.platform.push.PushInfoBean;
import com.efun.platform.push.PushUtils;
import com.efun.platform.push.activity.EfunPushDispatcherActivity;
import com.efun.platform.push.activity.PushDispatcherActivity;
import com.efun.platform.service.IPlatService;
import com.efun.platform.utils.Const;
import com.efun.platform.utils.Const.RequestCode;
import com.efun.platform.utils.Const.ResultCode;
import com.efun.platform.utils.Const.Tab;
import com.efun.platform.utils.StoreUtil;
import com.facebook.AppEventsLogger;
import com.google.ads.conversiontracking.AdWordsConversionReporter;

/**
 * 框架容器
 * 
 * @author Jesse
 * 
 */
public class FrameTabContainer extends FragmentActivity implements
		FrameTabListener {

	private final static int CWJ_HEAP_SIZE = 6 * 1024 * 1024;
	
	private static final String APPFLYER_APPID = "Z7hYKnACEnFatoyB7DM33V";
	/**
	 * {@link FragmentTabHost}
	 */
	private FragmentTabHost mFragmentTabHost;
	/**
	 * 资讯 Tab的Tag值
	 */
	private final String TAB_ITEM_TAG_SUMMARY = "Summary";
	/**
	 * 游戏 Tab的Tag值
	 */
	private final String TAB_ITEM_TAG_GAMES = "Games";
	/**
	 * 好康 Tab的Tag值
	 */
	private final String TAB_ITEM_TAG_HAOKANG = "HaoKang";
	/**
	 * 客服 Tab的Tag值
	 */
	private final String TAB_ITEM_TAG_CUSTOMSERVICE = "CustomService";
	/**
	 * 玩家'我' Tab的Tag值
	 */
	private final String TAB_ITEM_TAG_PLAYERSELF = "PlayerSelf";
	/**
	 * {@link TabSpec}
	 */
	private final String[] mTabSpecs = new String[] { TAB_ITEM_TAG_SUMMARY,
			TAB_ITEM_TAG_GAMES, TAB_ITEM_TAG_HAOKANG,
			TAB_ITEM_TAG_CUSTOMSERVICE, TAB_ITEM_TAG_PLAYERSELF };
	public static int lastTag = Tab.TAB_ITEM_TAG_SUMMARY;
	
	private ArrayList<ImageView> mTabIcons = null;
	private boolean hasHaoKangPush = false;
	private boolean hasRechargePush = false;
	/**
	 * 应用更新的广播
	 */
	private updateRecevier mReceiver;
	
	private IPlatService mService;

	// Note: Your consumer key and secret should be obfuscated in your source code before shipping.
//    private static final String TWITTER_KEY = "vtk8z0ipasYoWmw5V8OAvrJf1";
//    private static final String TWITTER_SECRET = "HzHanZmUdZgi7NYRgOxQHYrXNuO8KiiU1REj7hqERRI4xsNvyY";
	@Override
	protected void onCreate(Bundle arg0) {
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		super.onCreate(arg0);
		// FacebookSelfPluginImpl.getInstance().onCreate(arg0,this);

		//twitter
//		TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
//        Fabric.with(this, new Twitter(authConfig));
		
		// 廣播的注冊
		IntentFilter filter = new IntentFilter(Const.RECEIVER_UPDATEAPP_ACTION);
		mReceiver = new updateRecevier();
		this.registerReceiver(mReceiver, filter);

		//FB广告
		EfunLogUtil.logI("efun","-->FB广告");
		AppEventsLogger.activateApp(this, this.getString(E_string.efunFBApplicationId));
		AppsFlyerLib.setAppsFlyerKey(APPFLYER_APPID);
		AppsFlyerLib.setAppUserId("My-Unique-ID");
		AppsFlyerLib.setCurrencyCode("USD");
		AppsFlyerLib.sendTracking(this);
		
		// Efun平台_激活_Android -- adwords广告
		// Google Android first open conversion tracking snippet
		// Add this code to the onCreate() method of your application activity
		EfunLogUtil.logI("efun","-->adwords广告");
		AdWordsConversionReporter.reportWithConversionId(this.getApplicationContext(),"971591416", "Y288CP61oWEQ-J2lzwM", "0.00", false);

		
		
		Intent it = new Intent(this, IPlatService.class);
		bindService(it, conn, BIND_AUTO_CREATE);

		Log.e("efun", "platform onCreate:");
		
		setContentView(E_layout.efun_pd_main_tab_layout);
		initViews();
		initDatas();
		initListeners();
		//启动推送
		PushUtils.initPushWhenStart(this);
//		EfunGoogleProxy.onCreateMainActivity(this);
		// 保留google分析,增加fireBase分析
		TrackingGoogleUtils.init(this);
		TrackingUtils.initService(this);
		TrackingUtils.track(this,TrackingUtils.ACTION_APP,
				TrackingUtils.NAME_APP_START);
		TrackingGoogleUtils.track(TrackingGoogleUtils.ACTION_APP,
				TrackingGoogleUtils.NAME_APP_START);

		TrackingUtils.trackSingle(this, TrackingUtils.ACTION_APP,
				TrackingUtils.NAME_APP_START_UNIQUE, Const.BETWEENTIME,
				FrameTabContainer.class);
		TrackingGoogleUtils.trackSingle(this, TrackingGoogleUtils.ACTION_APP,
				TrackingGoogleUtils.NAME_APP_START_UNIQUE, Const.BETWEENTIME,
				FrameTabContainer.class);
		
		Log.e("efun", "position:"
				+ getResources().getDimension(R.dimen.e_index));
		// DimensUtils.px2dp(this,0.623);
		PushInfoBean mPushInfoBean = (PushInfoBean) getIntent()
				.getSerializableExtra(Const.EFUN_PUSH_KEY);
		EfunLogUtil.logE("FrameTabContainer PushInfoBean is " + mPushInfoBean);
		if (mPushInfoBean != null) {
			Intent intent = new Intent(this, EfunPushDispatcherActivity.class);
			intent.putExtra(Const.EFUN_PUSH_KEY, mPushInfoBean);
			startActivity(intent);
		}
		String mPushInfoStr = getIntent().getStringExtra(Const.PUSH_KEY);
		EfunLogUtil.logE("FrameTabContainer PushInfoBean is " + mPushInfoStr);
		if (mPushInfoStr != null) {
			Intent intent = new Intent(this, PushDispatcherActivity.class);
			intent.putExtra(Const.PUSH_KEY, mPushInfoStr);
			startActivity(intent);
		}
		//保存当前登录时间
		StoreUtil.saveValue(this, StoreUtil.Param_file_name, StoreUtil.Pamam_key_user_login_currentTime, System.currentTimeMillis());
	}

	private ServiceConnection conn = new ServiceConnection() {
		@Override
		public void onServiceDisconnected(ComponentName name) {
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			Log.e("efun", "onServiceConnected:"+service.toString());
			mService = ((IPlatService.IPlatBinder)service).getService();
			//设置账号是否过期监听
			mService.setmClickListener(new OnEfunItemClickListener() {
				
				@Override
				public void onItemClick(int position) {
					// TODO Auto-generated method stub
					Log.e("efun", "点击了确认按钮"+position);
					if(position == 0){//确认
								// TODO Auto-generated method stub
//						if (IPlatApplication.get().getUser() != null) {
//							onTabChange(Tab.TAB_ITEM_TAG_PLAYERSELF);
//						} else {
//							onTabChange(Tab.TAB_ITEM_TAG_SUMMARY);
//						}
						lastTag = Tab.TAB_ITEM_TAG_SUMMARY;
						onTabChange(Tab.TAB_ITEM_TAG_PLAYERSELF);
					}else{//取消
						onTabChange(Tab.TAB_ITEM_TAG_SUMMARY);
					}
				}
			});
		}
	};

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(mReceiver!=null){
			this.unregisterReceiver(mReceiver);
		}
		unbindService(conn);
		// FacebookSelfPluginImpl.getInstance().logout();
		// 去掉google分析,换成fireBase分析
		TrackingGoogleUtils.destory();
//		TrackingUtils.destoryService(this);
	};
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		if(mTabIcons != null){
			if(IPlatApplication.get().isHasHaoKangPush()){
				if(IPlatApplication.get().getUser() == null){
					hasHaoKangPush = true;
					mTabIcons.get(2).setImageResource(E_drawable.efun_pd_tab_item_haokang_push);
					IPlatApplication.get().setHasHaoKangPush(false);
				}else{
					hasHaoKangPush = false;
					lastTag = Tab.TAB_ITEM_TAG_HAOKANG;
					IPlatApplication.get().setHasHaoKangPush(false);
					onTabChange(Tab.TAB_ITEM_TAG_HAOKANG);
				}
			}
			if(IPlatApplication.get().isHasRechargePush()){
				if(IPlatApplication.get().getUser() == null){
					hasRechargePush = true;
					mTabIcons.get(4).setImageResource(E_drawable.efun_pd_tab_item_player_recharge_push);
					IPlatApplication.get().setHasRechargePush(false);
				}else{
					hasRechargePush = false;
					lastTag = Tab.TAB_ITEM_TAG_PLAYERSELF;
					IPlatApplication.get().setHasRechargePush(false);
					onTabChange(Tab.TAB_ITEM_TAG_PLAYERSELF);
				}
			}
		}
		super.onResume();
	}

	/**
	 * 初始化控件
	 */
	private void initViews() {
		mFragmentTabHost = (FragmentTabHost) findViewById(E_id.tabhost);
		mFragmentTabHost.setup(this, getSupportFragmentManager(),
				E_id.realtabcontent);
	}

	@SuppressLint("NewApi")
	private void initDatas() {
		Class<?>[] tabClasses = getFragmentClass();
		int length = tabClasses.length;
		View parentView = null;
		ImageView itemImgView = null;
		mTabIcons = new ArrayList<ImageView>();
		for (int i = 0; i < length; i++) {
			// 为每一个Tab按钮设置图标、文字和内容
			TabSpec tabSpec = mFragmentTabHost.newTabSpec(mTabSpecs[i])
					.setIndicator(getTabItemView());
			// 将Tab按钮添加进Tab选项卡中
			mFragmentTabHost.addTab(tabSpec, tabClasses[i], null);
			// 设置Tab按钮的背景
			parentView = mFragmentTabHost.getTabWidget().getChildAt(i);
			itemImgView = (ImageView) parentView.findViewById(E_id.imageview);
			itemImgView.setImageResource(getTabIcon()[i]);
			mTabIcons.add(itemImgView);
		}
	}

	/**
	 * 初始化Tab 切换监听
	 */
	private void initListeners() {
		mFragmentTabHost.setOnTabChangedListener(new OnTabChangeListener() {
			@Override
			public void onTabChanged(String tabId) {
				if (tabId.equals(TAB_ITEM_TAG_SUMMARY)) {
					TrackingUtils.track(FrameTabContainer.this,TrackingUtils.ACTION_TAB,
							TrackingUtils.NAME_TAB_SUMMARY);
					TrackingGoogleUtils.track(TrackingGoogleUtils.ACTION_TAB,
							TrackingGoogleUtils.NAME_TAB_SUMMARY);
					lastTag = Tab.TAB_ITEM_TAG_SUMMARY;
				} else if (tabId.equals(TAB_ITEM_TAG_GAMES)) {
					TrackingUtils.track(FrameTabContainer.this,TrackingUtils.ACTION_TAB,
							TrackingUtils.NAME_TAB_GAME);
					TrackingGoogleUtils.track(TrackingGoogleUtils.ACTION_TAB,
							TrackingGoogleUtils.NAME_TAB_GAME);
					lastTag = Tab.TAB_ITEM_TAG_GAMES;
				} else if (tabId.equals(TAB_ITEM_TAG_HAOKANG)) {
					TrackingUtils.track(FrameTabContainer.this,TrackingUtils.ACTION_TAB,
							TrackingUtils.NAME_TAB_WELFARE);
					TrackingGoogleUtils.track(TrackingGoogleUtils.ACTION_TAB,
							TrackingGoogleUtils.NAME_TAB_WELFARE);
					if(hasHaoKangPush){
						mTabIcons.get(2).setImageResource(E_drawable.efun_pd_tab_item_welfare_selector);
						hasHaoKangPush = false;
					}
					UserUtils.needLoginInTag(FrameTabContainer.this, lastTag,
							Tab.TAB_ITEM_TAG_HAOKANG,
							new OnLoginFinishListener() {
								@Override
								public void loginCompleted(boolean completed) {
									lastTag = Tab.TAB_ITEM_TAG_HAOKANG;
//									if (!completed) {
//										if (!completed) {
//											if (haokang != null) {
//												haokang.refreshView();
//											}
//										}
//									}
								}
							});
				} else if (tabId.equals(TAB_ITEM_TAG_CUSTOMSERVICE)) {
					TrackingUtils.track(FrameTabContainer.this,TrackingUtils.ACTION_TAB,
							TrackingUtils.NAME_TAB_CS);
					TrackingGoogleUtils.track(TrackingGoogleUtils.ACTION_TAB,
							TrackingGoogleUtils.NAME_TAB_CS);
					lastTag = Tab.TAB_ITEM_TAG_CUSTOMSERVICE;
//					if (UserUtils.isLogin()) {
//						if (cs != null) {
//							cs.requestReplyStatus();
//						}
//					}
				} else if (tabId.equals(TAB_ITEM_TAG_PLAYERSELF)) {
					TrackingUtils.track(FrameTabContainer.this,TrackingUtils.ACTION_TAB,
							TrackingUtils.NAME_TAB_SELF);
					TrackingGoogleUtils.track(TrackingGoogleUtils.ACTION_TAB,
							TrackingGoogleUtils.NAME_TAB_SELF);
					if(hasRechargePush){
						mTabIcons.get(4).setImageResource(E_drawable.efun_pd_tab_item_playerself_selector);
						hasRechargePush = false;
					}
					UserUtils.needLoginInTag(FrameTabContainer.this, lastTag,
							Tab.TAB_ITEM_TAG_PLAYERSELF,
							new OnLoginFinishListener() {
								@Override
								public void loginCompleted(boolean completed) {
									lastTag = Tab.TAB_ITEM_TAG_PLAYERSELF;
//									if (!completed) {
//										if (persion != null) {
//											persion.refresh();
//										}
//									}
								}
							});
				}
			}
		});
	}

	private PersonalCenterFragment persion;
//	private CsEltyFragment cs;
//	private HaoKangEltyFragment haokang;

	@Override
	public void onAttachFragment(Fragment fragment) {
		super.onAttachFragment(fragment);
		Log.e("efun", "fragment:"+fragment.getClass());
		if (fragment instanceof PersonalCenterFragment) {
			persion = (PersonalCenterFragment) fragment;
		} 
//		else if (fragment instanceof CsEltyFragment) {
//			cs = (CsEltyFragment) fragment;
//		}else if(fragment instanceof HaoKangEltyFragment){
//			haokang = (HaoKangEltyFragment) fragment;
//		}
	}

	/**
	 * 创建Tab Item View
	 */
	private View getTabItemView() {
		int id = E_layout.efun_pd_item_tab_layout;
		return LayoutInflater.from(this).inflate(id, null);
	}

	/**
	 * 获取Tab Icon 资源Ids
	 * 
	 * @return
	 */
	private int[] getTabIcon() {
		return new int[] { E_drawable.efun_pd_tab_item_summary_selector,
				E_drawable.efun_pd_tab_item_games_selector,
				E_drawable.efun_pd_tab_item_welfare_selector,
				E_drawable.efun_pd_tab_item_cs_selector,
				E_drawable.efun_pd_tab_item_playerself_selector };
	}

	/**
	 * 创建每个Tab 项 的Fragment页面
	 * 
	 * @return
	 */
	private Class<?>[] getFragmentClass() {
		return new Class<?>[] { SummaryFragment.class, GamesFragment.class,
				HaoKangFragment.class, CustomServiceFragment.class,
				PersonalCenterFragment.class };
	}

	@Override
	public void onTabChange(int index) {
		mFragmentTabHost.setCurrentTab(index);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Log.e("efun", "onActivityResult:requestCode--》"+requestCode+"resultCode:"+resultCode);
		if (resultCode == Tab.TAB_ITEM_TAG_PLAYERSELF && requestCode == lastTag) {
			onTabChange(lastTag);
		} else if (resultCode == ResultCode.RST_LOGOUT
				&& requestCode == RequestCode.REQ_LOGOUT) {
			onTabChange(Tab.TAB_ITEM_TAG_SUMMARY);
		}else if(resultCode == Tab.TAB_ITEM_TAG_HAOKANG && requestCode == lastTag){
//			if (UserUtils.isLogin()) {
//				if (haokang != null) {
//					haokang.refreshView();
//				}
//			}
			onTabChange(lastTag);
		}else if(((requestCode == RequestCode.REQ_USER_INFO_HEAD_PHONE || requestCode == RequestCode.REQ_USER_INFO_HEAD_THUMB) && resultCode == RequestCode.REQ_PIC_PHOTO_CAMERA_RETURN)){
			Log.e("efun", "persion:requestCode--》"+requestCode+"resultCode:"+resultCode);
			persion.onActivityResult(requestCode, resultCode, data);
		}else if(requestCode == RequestCode.REQ_RESET_PERINFO){
			Log.e("efun", "persion:1requestCode--》"+requestCode+"resultCode:"+resultCode);
			persion.onActivityResult(requestCode, resultCode, data);
		}
		// FacebookSelfPluginImpl.getInstance().onActivityResult(requestCode,resultCode,
		// data);
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		// FacebookSelfPluginImpl.getInstance().onStop();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		// FacebookSelfPluginImpl.getInstance().onSaveInstanceState(outState);
	}

	private long firstClick = 0;

	/**
	 * 連續點擊兩次，退出程序
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (lastTag != Tab.TAB_ITEM_TAG_SUMMARY) {
			mFragmentTabHost.setCurrentTab(Tab.TAB_ITEM_TAG_SUMMARY);
			return true;
		}
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			long secondClick = System.currentTimeMillis();
			if (secondClick - firstClick > 2000) {
				ToastUtils.toast(this, E_string.efun_pd_logout,
						Toast.LENGTH_SHORT);
				firstClick = secondClick;
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	class updateRecevier extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			final String url = intent.getStringExtra("downloadUrl");
			Dialog dialog = ViewUtils.createToastUpdate(FrameTabContainer.this, 
					new OnEfunItemClickListener() {
						@Override
						public void onItemClick(int position) {
							AppUtils.download(getApplicationContext(), url);
						}
					}, true);
			dialog.show();
		}

	}

}
