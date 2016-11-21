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
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.Toast;

import com.appsflyer.AppsFlyerLib;
import com.efun.core.tools.EfunLogUtil;
import com.efun.jqzs.sm.R;
import com.efun.platform.AndroidScape.E_drawable;
import com.efun.platform.AndroidScape.E_id;
import com.efun.platform.AndroidScape.E_layout;
import com.efun.platform.AndroidScape.E_string;
import com.efun.platform.module.base.impl.OnEfunItemClickListener;
import com.efun.platform.module.base.impl.OnLoginFinishListener;
import com.efun.platform.module.find.FindFragment;
import com.efun.platform.module.interaction.InteractionFragment;
import com.efun.platform.module.person.PersonalCenterFragment;
import com.efun.platform.module.summary.SummaryFragment;
import com.efun.platform.module.utils.AppUtils;
import com.efun.platform.module.utils.ToastUtils;
import com.efun.platform.module.utils.TrackingGoogleUtils;
import com.efun.platform.module.utils.UserUtils;
import com.efun.platform.module.utils.ViewUtils;
import com.efun.platform.module.welfare.WelfareFragment;
import com.efun.platform.service.IPlatService;
import com.efun.platform.utils.Const;
import com.efun.platform.utils.Const.RequestCode;
import com.efun.platform.utils.Const.ResultCode;
import com.efun.platform.utils.Const.Tab;
import com.efun.platform.utils.StoreUtil;
import com.efun.platform.widget.LinearLayoutTabView;
import com.efun.platform.widget.LinearLayoutTabView.KeyBordStateListener;
import com.facebook.AppEventsLogger;
import com.google.ads.conversiontracking.AdWordsConversionReporter;

/**
 * 框架容器
 *
 * @author itxuxxey
 *
 */
public class FrameTabContainer extends FragmentActivity implements
		FrameTabListener,KeyBordStateListener {

	private final static int CWJ_HEAP_SIZE = 6 * 1024 * 1024;

	/**
	 * {@link FragmentTabHost}
	 */
	private FragmentTabHost mFragmentTabHost;
	/**
	 * 资讯 Tab的Tag值
	 */
	private final String TAB_ITEM_TAG_SUMMARY = "Summary";
	/**
	 * 发现Tab的Tag值
	 */
	private final String TAB_ITEM_TAG_FIND = "Find";
	/**
	 * 福利 Tab的Tag值
	 */
	private final String TAB_ITEM_TAG_WELFARE = "Welfare";
	/**
	 * 交流 Tab的Tag值
	 */
	private final String TAB_ITEM_TAG_INTERACTION = "Interaction";
	/**
	 * 玩家'我' Tab的Tag值
	 */
	private final String TAB_ITEM_TAG_PLAYERSELF = "PlayerSelf";
	/**
	 * {@link TabSpec}
	 */
	private final String[] mTabSpecs = new String[] { TAB_ITEM_TAG_SUMMARY,
			TAB_ITEM_TAG_FIND, TAB_ITEM_TAG_WELFARE,
			TAB_ITEM_TAG_INTERACTION, TAB_ITEM_TAG_PLAYERSELF };
	public static int lastTag = Tab.TAB_ITEM_TAG_SUMMARY;

	private ArrayList<ImageView> mTabIcons = null;
	private boolean hasHaoKangPush = false;
	private boolean hasRechargePush = false;
	/**
	 * 应用更新的广播
	 */
	private updateRecevier mReceiver;

	private IPlatService mService;

	private LinearLayoutTabView mainLayout;
	private InputMethodManager imm = null;

	@Override
	protected void onCreate(Bundle arg0) {
		Log.i("yang", "======1===onCreate");
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		super.onCreate(arg0);

		// 廣播的注冊
		IntentFilter filter = new IntentFilter(Const.RECEIVER_UPDATEAPP_ACTION);
		mReceiver = new updateRecevier();
		this.registerReceiver(mReceiver, filter);


		Intent it = new Intent(this, IPlatService.class);
		bindService(it, conn, BIND_AUTO_CREATE);

		setContentView(E_layout.efun_pd_main_tab_layout);
		initViews();
		initDatas();
		initListeners();
		// 保留google分析,增加fireBase分析
		TrackingGoogleUtils.init(this);
		TrackingGoogleUtils.track(TrackingGoogleUtils.ACTION_APP,
				TrackingGoogleUtils.NAME_APP_START);
		TrackingGoogleUtils.trackSingle(this, TrackingGoogleUtils.ACTION_APP,
				TrackingGoogleUtils.NAME_APP_START_UNIQUE, Const.BETWEENTIME,
				FrameTabContainer.class);

		Log.e("efun", "position:"
				+ getResources().getDimension(R.dimen.e_index));
		//保存当前登录时间
		StoreUtil.saveValue(this, StoreUtil.Param_file_name, StoreUtil.Pamam_key_user_login_currentTime, System.currentTimeMillis());
		mainLayout.setKeyBordStateListener(this);
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
		Log.i("yang", "======1===onDestroy");
		super.onDestroy();
		if(mReceiver!=null){
			this.unregisterReceiver(mReceiver);
		}
		unbindService(conn);
		// FacebookSelfPluginImpl.getInstance().logout();
	};

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		Log.e("yang", "Tab:"+lastTag);
		Log.i("yang", "======1===onResume");
		if(mTabIcons != null){
			if(IPlatApplication.get().isHasHaoKangPush()){
				if(IPlatApplication.get().getUser() == null){
					hasHaoKangPush = true;
					IPlatApplication.get().setHasHaoKangPush(false);
				}else{
					hasHaoKangPush = false;
					lastTag = Tab.TAB_ITEM_TAG_WELFARE;
					IPlatApplication.get().setHasHaoKangPush(false);
					onTabChange(Tab.TAB_ITEM_TAG_WELFARE);
				}
			}
			if(IPlatApplication.get().isHasRechargePush()){
				if(IPlatApplication.get().getUser() == null){
					hasRechargePush = true;
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
		mainLayout = (LinearLayoutTabView) findViewById(R.id.tab_host_body_layout);
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
					TrackingGoogleUtils.track(TrackingGoogleUtils.ACTION_TAB,
							TrackingGoogleUtils.NAME_TAB_SUMMARY);
					lastTag = Tab.TAB_ITEM_TAG_SUMMARY;
				} else if (tabId.equals(TAB_ITEM_TAG_FIND)) {
					TrackingGoogleUtils.track(TrackingGoogleUtils.ACTION_TAB,
							TrackingGoogleUtils.NAME_TAB_GAME);
					IPlatApplication.get().setTabFlag(TAB_ITEM_TAG_FIND);
					UserUtils.needLoginInTag(FrameTabContainer.this, lastTag,
							Tab.TAB_ITEM_TAG_FIND,
							new OnLoginFinishListener() {
								@Override
								public void loginCompleted(boolean completed) {
									lastTag = Tab.TAB_ITEM_TAG_FIND;
								}
					});
				} else if (tabId.equals(TAB_ITEM_TAG_WELFARE)) {
					TrackingGoogleUtils.track(TrackingGoogleUtils.ACTION_TAB,
							TrackingGoogleUtils.NAME_TAB_WELFARE);
					if(hasHaoKangPush){
						mTabIcons.get(2).setImageResource(E_drawable.efun_pd_tab_item_welfare_selector);
						hasHaoKangPush = false;
					}
					UserUtils.needLoginInTag(FrameTabContainer.this, lastTag,
							Tab.TAB_ITEM_TAG_WELFARE,
							new OnLoginFinishListener() {
								@Override
								public void loginCompleted(boolean completed) {
									lastTag = Tab.TAB_ITEM_TAG_WELFARE;
								}
							});
				} else if (tabId.equals(TAB_ITEM_TAG_INTERACTION)) {
					TrackingGoogleUtils.track(TrackingGoogleUtils.ACTION_TAB,
							TrackingGoogleUtils.NAME_TAB_CS);
					IPlatApplication.get().setTabFlag(TAB_ITEM_TAG_INTERACTION);
					UserUtils.needLoginInTag(FrameTabContainer.this, lastTag,
							Tab.TAB_ITEM_TAG_INTERACTION,
							new OnLoginFinishListener() {
								@Override
								public void loginCompleted(boolean completed) {
									lastTag = Tab.TAB_ITEM_TAG_INTERACTION;
								}
					});
				} else if (tabId.equals(TAB_ITEM_TAG_PLAYERSELF)) {
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
								}
							});
				}
			}
		});
	}

	private PersonalCenterFragment persion;
	private FindFragment findFrag;
	private InteractionFragment InteractionFrag;

	@Override
	public void onAttachFragment(Fragment fragment) {
		super.onAttachFragment(fragment);
		Log.e("efun", "fragment:"+fragment.getClass());
		if (fragment instanceof PersonalCenterFragment) {
			persion = (PersonalCenterFragment) fragment;
		}
		else if (fragment instanceof FindFragment) {
			findFrag = (FindFragment) fragment;
		}else if(fragment instanceof InteractionFragment){
			InteractionFrag = (InteractionFragment) fragment;
		}
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
				E_drawable.efun_pd_tab_item_find_selector,
				E_drawable.efun_pd_tab_item_welfare_selector,
				E_drawable.efun_pd_tab_item_interaction_selector,
				E_drawable.efun_pd_tab_item_playerself_selector };
	}

	/**
	 * 创建每个Tab 项 的Fragment页面
	 *
	 * @return
	 */
	private Class<?>[] getFragmentClass() {
		return new Class<?>[] { SummaryFragment.class, FindFragment.class,
				WelfareFragment.class, InteractionFragment.class,
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
		}else if(resultCode == Tab.TAB_ITEM_TAG_WELFARE && requestCode == lastTag){
			onTabChange(lastTag);
		}else if(resultCode == Tab.TAB_ITEM_TAG_FIND && requestCode == lastTag){
			onTabChange(lastTag);
		}else if(resultCode == Tab.TAB_ITEM_TAG_INTERACTION && requestCode == lastTag){
			onTabChange(lastTag);
		}else if(((requestCode == RequestCode.REQ_USER_INFO_HEAD_PHONE || requestCode == RequestCode.REQ_USER_INFO_HEAD_THUMB) && resultCode == RequestCode.REQ_PIC_PHOTO_CAMERA_RETURN)){
			Log.e("efun", "persion:requestCode--》"+requestCode+"resultCode:"+resultCode);
			persion.onActivityResult(requestCode, resultCode, data);
		}else if(requestCode == RequestCode.REQ_SETTING){
			Log.e("efun", "persion:1requestCode--》"+requestCode+"resultCode:"+resultCode);
			persion.onActivityResult(requestCode, resultCode, data);
		}else if(requestCode == RequestCode.REQ_FRAGMENT_JS_UPLOAD_PICTURE && lastTag == Tab.TAB_ITEM_TAG_INTERACTION ){
			InteractionFrag.onActivityResult(requestCode, resultCode, data);
		}else if(requestCode == RequestCode.REQ_FRAGMENT_JS_UPLOAD_PICTURE && lastTag == Tab.TAB_ITEM_TAG_INTERACTION ){
			findFrag.onActivityResult(requestCode, resultCode, data);
		}

	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
	}

	private long firstClick = 0;

	/**
	 * 連續點擊兩次，退出程序
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		//音量键处理
		if(keyCode == KeyEvent.KEYCODE_VOLUME_UP || keyCode == KeyEvent.KEYCODE_VOLUME_DOWN){
			return super.onKeyDown(keyCode, event);
		}
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
	/**
	 * 弃用方法，软键盘处理，暂未完善
	 */
	@Override
	public void stateChange(int state) {
		// TODO Auto-generated method stub
		switch (state) {
		case LinearLayoutTabView.KEYBORAD_HIDE:
			mFragmentTabHost.setVisibility(View.VISIBLE);
			break;

		case LinearLayoutTabView.KEYBORAD_SHOW:
			imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
			boolean isOpen=imm.isActive();
			Log.e("yang", "isOpen:"+isOpen);
			if(isOpen){
//				mFragmentTabHost.setVisibility(View.GONE);
			}
			break;
		}
	}

}
