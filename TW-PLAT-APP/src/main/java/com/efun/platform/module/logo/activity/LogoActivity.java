package com.efun.platform.module.logo.activity;

import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.efun.core.tools.EfunLogUtil;
import com.efun.google.EfunGoogleProxy;
import com.efun.platform.AndroidScape.E_id;
import com.efun.platform.AndroidScape.E_layout;
import com.efun.platform.FrameTabContainer;
import com.efun.platform.http.request.bean.BaseRequestBean;
import com.efun.platform.module.base.FixedActivity;
import com.efun.platform.module.logo.adapter.LogoAdapter;
import com.efun.platform.module.utils.AppUtils;
import com.efun.platform.module.utils.TextToolUtils;
import com.efun.platform.module.utils.TrackingGoogleUtils;
import com.efun.platform.module.utils.TrackingUtils;
import com.efun.platform.push.PushInfoBean;
import com.efun.platform.push.PushUtils;
import com.efun.platform.utils.Const;
import com.efun.platform.utils.StoreUtil;
import com.efun.platform.widget.TitleView;
import com.efun.platform.widget.ZoomPoint;
import com.efun.platform.widget.ZoomPointContainer;
/**
 * 启动Logo，只执行一次
 * @author Jesse
 *
 */
public class LogoActivity extends FixedActivity {
	
	private ViewPager mViewPager;
	private LogoAdapter adapter;
	private ZoomPointContainer mZoomPointContainer;
	private RelativeLayout startPictureLayout;
	private Handler myHandler;
	private boolean misScrolled;
	private ZoomPoint[] mZoomPoint;
	private String mPushInfoStr;
//	private String startType = "";
//	private String gameName = "";
//	private String gameCode = "";
	@Override
	public boolean needRequestData() {
		return false;
	}

	@Override
	public BaseRequestBean[] needRequestDataBean() {
		return null;
	}
//
//	@Override
//	protected void onCreate(Bundle arg0) {
//		// TODO Auto-generated method stub
//		super.onCreate(arg0);
//		//启动推送
//		Map<String,String> dataMap = EfunGoogleProxy.onCreateMainActivity(this,false);
//		Log.e("yang", "--onCreate-->dataMap:"+dataMap.toString());
//		if(dataMap != null){
//			Object[] obj = AppUtils.isAppRunning(this, this.getPackageName());
//			boolean isRunning = Boolean.parseBoolean(obj[0].toString());
//			String topActivityName = obj[1].toString();
//			dataMap.put("curPageName", topActivityName);
//			mPushInfoStr = dataMap.toString();
//			Log.e("yang", "--onCreate-->mPushInfoStr:"+mPushInfoStr);
//		}
//	}
	
	@Override
	public void init(Bundle bundle) {
//		//启动推送
//		PushUtils.initPushWhenStart(this);
		final PushInfoBean mPushInfoBean = (PushInfoBean) getIntent().getSerializableExtra(Const.EFUN_PUSH_KEY);
		EfunLogUtil.logE("LogoActivity PushInfoBean is "+mPushInfoBean);
//		final String mPushInfoStr = getIntent().getStringExtra(Const.PUSH_KEY);
		
		// 启动推送
		Map<String, String> dataMap = EfunGoogleProxy.onCreateMainActivity(this, false);
		if (dataMap != null) {
			Object[] obj = AppUtils.isAppRunning(this, this.getPackageName());
			boolean isRunning = Boolean.parseBoolean(obj[0].toString());
			String topActivityName = obj[1].toString();
			dataMap.put("curPageName", topActivityName);
			mPushInfoStr = TextToolUtils.transMapToString(dataMap);
			Log.e("yang", "--onCreate-->mPushInfoStr:" + mPushInfoStr);
		}
//		//检测google-service版本
//		EfunGoogleProxy.isGooglePlayServicesAvailable(this);
		EfunLogUtil.logE("LogoActivity mPushInfoStr is " + mPushInfoStr);
		Log.e("yang", "LogoActivity mPushInfoStr is " + mPushInfoStr);
		startPictureLayout = (RelativeLayout) findViewById(E_id.logo_final);
		
//		if(bundle != null){
//			startType = bundle.getString("START_TYPE");
//			gameName = bundle.getString("START_GAME_NAME");
//			gameCode = bundle.getString("START_GAMECODE");
//			IPlatApplication.get().setStartGameName(gameName);
//			IPlatApplication.get().setStartGameCode(gameCode);
//    	}
		
		if(!StoreUtil.firstStart(this)){
			/**
			 * 启动一个延迟线程
			 */
			myHandler = new Handler() {
			    public void handleMessage(Message msg) {
//			    	if(startType.equals("game")){
//			    		if(IPlatApplication.get().getUser() != null){
//			    			Intent it = new Intent(LogoActivity.this,AuthorizaGameToPlatformActivity.class);
//							LogoActivity.this.startActivity(it);
//							LogoActivity.this.finish();
//			    		}else{
//			    			Intent it = new Intent(LogoActivity.this,AuthorizaLoginActivity.class);
//							LogoActivity.this.startActivity(it);
//							LogoActivity.this.finish();
//			    		}
//			    	}else{
			    		Intent it = new Intent(LogoActivity.this,FrameTabContainer.class);
				    	if(mPushInfoBean!=null){
				    		it.putExtra(Const.EFUN_PUSH_KEY, mPushInfoBean);
				    	}
			    		if(mPushInfoStr!=null){
				    		it.putExtra(Const.PUSH_KEY, mPushInfoStr);
				    	}
						LogoActivity.this.startActivity(it);
						LogoActivity.this.finish();
//			    	}
			    	
			    }
			};
			new Thread(){
				@Override
				public void run() {
					try {
						startPictureLayout.setVisibility(View.VISIBLE);
						Thread.sleep(3000);	//540*960  1080*1920						
						Message msg = myHandler.obtainMessage();
						myHandler.sendMessage(msg);
					} catch (InterruptedException e) {
					}
				}
			}.start();			
			
		}else{
			TrackingUtils.track(this,TrackingUtils.ACTION_APP, TrackingUtils.NAME_APP_INSTALLED);
			TrackingGoogleUtils.track(TrackingGoogleUtils.ACTION_APP, TrackingGoogleUtils.NAME_APP_INSTALLED);
			//廣告統計
			TrackingUtils.initAds(this);
			mViewPager = (ViewPager) findViewById(E_id.pager_view);
			mZoomPointContainer = (ZoomPointContainer)findViewById(E_id.zoom_point_parent);
			adapter = new LogoAdapter(mContext);
			mZoomPointContainer.setCount(3);
			mZoomPointContainer.setItemLayout(E_layout.efun_pd_logo_point);
			mZoomPointContainer.onInvalidate(0);
			mZoomPoint = mZoomPointContainer.getZoomPoint();
			mZoomPointContainer.setViewPager(mViewPager,new OnPageChangeListener() {
				@Override
				public void onPageSelected(final int index) {
					for (int i = 0; i < 3; i++) {
						if (i != index% mViewPager.getAdapter().getCount()) {
							if (mZoomPoint[i].getZoomNormalStatus())
								mZoomPoint[i].startZoomInCenter();
						} else {
							mZoomPoint[i].startZoomOutCenter();
						}
					}
				}

				
				@Override
				public void onPageScrolled(int arg0, float arg1, int arg2) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onPageScrollStateChanged(int state) {
					switch (state) {
					case ViewPager.SCROLL_STATE_DRAGGING:
						misScrolled = false;
						break;
					case ViewPager.SCROLL_STATE_SETTLING:
						misScrolled = true;
						break;
					case ViewPager.SCROLL_STATE_IDLE:
						if (mViewPager.getCurrentItem() == mViewPager.getAdapter().getCount() - 1 && !misScrolled) {
//							if(startType.equals("game")){
////					    		if(IPlatApplication.get().getUser() != null){
////					    			Intent it = new Intent(LogoActivity.this,AuthorizaGameToPlatformActivity.class);
////					    			mContext.startActivity(it);
////									finish();
////					    		}else{
//					    			Intent it = new Intent(LogoActivity.this,AuthorizaLoginActivity.class);
//					    			mContext.startActivity(it);
//									finish();
////					    		}
//					    	}else{
					    		Intent it = new Intent(mContext, FrameTabContainer.class);
								if(mPushInfoBean!=null){
						    		it.putExtra(Const.EFUN_PUSH_KEY, mPushInfoBean);
						    	}
								if(mPushInfoStr!=null){
						    		it.putExtra(Const.PUSH_KEY, mPushInfoStr);
						    	}
								mContext.startActivity(it);
								finish();
//					    	}
						}
						misScrolled = true;
						break;
					}
				}});
			mViewPager.setAdapter(adapter);
		}
	}

	@Override
	public ViewGroup[] needShowLoading() {
		return null;
	}

	@Override
	public int LayoutId() {
		return E_layout.efun_pd_logo;
	}

	@Override
	public void initTitle(TitleView titleView) {
		
	}
	
	@Override
	protected void onDestroy() {
		if(adapter!=null){
			adapter.destroyImage();
		}
				
		super.onDestroy();
	}

}

