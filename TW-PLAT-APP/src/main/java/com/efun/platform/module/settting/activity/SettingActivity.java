package com.efun.platform.module.settting.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.efun.core.tools.EfunStringUtil;
import com.efun.facebook.share.activity.EfunFBFunctionActivity;
import com.efun.platform.AndroidScape.E_color;
import com.efun.platform.AndroidScape.E_drawable;
import com.efun.platform.AndroidScape.E_id;
import com.efun.platform.AndroidScape.E_layout;
import com.efun.platform.AndroidScape.E_string;
import com.efun.platform.IPlatApplication;
import com.efun.platform.http.request.bean.BaseRequestBean;
import com.efun.platform.module.BaseDataBean;
import com.efun.platform.module.PopWindow;
import com.efun.platform.module.base.FixedActivity;
import com.efun.platform.module.utils.AppUtils;
import com.efun.platform.module.utils.PopUtils;
import com.efun.platform.module.utils.ToastUtils;
import com.efun.platform.module.utils.TrackingGoogleUtils;
import com.efun.platform.module.utils.TrackingUtils;
import com.efun.platform.status.bean.VersionBean;
import com.efun.platform.utils.Const.BeanType;
import com.efun.platform.utils.Const.Score;
import com.efun.platform.utils.StoreUtil;
import com.efun.platform.widget.TitleView;
/**
 * 设置页面
 * @author Jesse
 *
 */
public class SettingActivity extends FixedActivity{
	
	private LinearLayout mCheckUpdateLayout,mPushFriendsLayout,mhelpOrQSLayout,mScorePDLayout;
	private TextView mVersion;
	private ImageView mPushSwitch;
	private PopWindow mSharePopWindow;
	@Override
	public boolean needRequestData() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public BaseRequestBean[] needRequestDataBean() {
		return null;
	}

	@Override
	public void init(Bundle bundle) {
		
		mhelpOrQSLayout= (LinearLayout) findViewById(E_id.contaier_linear_3);
		mScorePDLayout= (LinearLayout) findViewById(E_id.contaier_linear_4);
		mCheckUpdateLayout = (LinearLayout) findViewById(E_id.contaier_linear_5);
		mPushFriendsLayout= (LinearLayout) findViewById(E_id.contaier_linear_6);
		mVersion  = (TextView) findViewById(E_id.item_text);
		mPushSwitch  = (ImageView) findViewById(E_id.setting_push_switch);
		mSharePopWindow = PopUtils.createShare(mContext,mPushFriendsLayout,new BaseDataBean(BeanType.BEAN_SETTINGBEAN));
		
		if(IPlatApplication.get().getIPlatStatus()!=null){
			VersionBean version = IPlatApplication.get().getIPlatStatus().getVersion();
			if(version!=null && version.getIsUpdate().equals("1")){
				mVersion.setText(getString(E_string.efun_pd_new_version)+version.getVersionName());
				mVersion.setTextColor(getResources().getColor(E_color.e_b8122a));
			}else{
				mCheckUpdateLayout.setEnabled(false);
			}
		}
		String pushFlag = StoreUtil.getValue(this, StoreUtil.Param_file_name, StoreUtil.Param_key_push_flag);
		if(!EfunStringUtil.isEmpty(pushFlag)){
			if(pushFlag.equals("open")){
				mPushSwitch.setSelected(false);
			}else{
				mPushSwitch.setSelected(true);
			}
		}else{
			mPushSwitch.setSelected(false);
		}
		mPushSwitch.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(mPushSwitch.isSelected()){
					TrackingUtils.track(mContext,TrackingUtils.ACTION_SETTING, TrackingUtils.NAME_SETTING_PUSH_OPEN);
					TrackingGoogleUtils.track(TrackingGoogleUtils.ACTION_SETTING, TrackingGoogleUtils.NAME_SETTING_PUSH_OPEN);
					StoreUtil.saveValue(mContext, StoreUtil.Param_file_name, StoreUtil.Param_key_push_flag, "open");
					mPushSwitch.setSelected(false);
					ToastUtils.toast(mContext,getWindow().getDecorView(),E_drawable.efun_pd_toast_green_bg, getString(E_string.efun_pd_setting_push_open),Toast.LENGTH_SHORT,0,500);
//					ToastUtils.toast(mContext, getString(E_string.efun_pd_setting_push_open));
				}else{
					TrackingUtils.track(mContext,TrackingUtils.ACTION_SETTING, TrackingUtils.NAME_SETTING_PUSH_CLOSE);
					TrackingGoogleUtils.track(TrackingGoogleUtils.ACTION_SETTING, TrackingGoogleUtils.NAME_SETTING_PUSH_CLOSE);
					StoreUtil.saveValue(mContext, StoreUtil.Param_file_name, StoreUtil.Param_key_push_flag, "close");
					mPushSwitch.setSelected(true);
					ToastUtils.toast(mContext,getWindow().getDecorView(),E_drawable.efun_pd_toast_green_bg, getString(E_string.efun_pd_setting_push_close),Toast.LENGTH_SHORT,0,500);
//					ToastUtils.toast(mContext, getString(E_string.efun_pd_setting_push_close));
				}
			}
		});
		
		
		mCheckUpdateLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(IPlatApplication.get().getIPlatStatus()!=null){
					TrackingUtils.track(mContext,TrackingUtils.ACTION_SETTING, TrackingUtils.NAME_SETTING_CHECK_UPDATE);
					TrackingGoogleUtils.track(TrackingGoogleUtils.ACTION_SETTING, TrackingGoogleUtils.NAME_SETTING_CHECK_UPDATE);
					VersionBean version = IPlatApplication.get().getIPlatStatus().getVersion();
					if(version!=null && version.getIsUpdate().equals("1")){
						AppUtils.download(mContext, version.getDownloadUrl());
					}
				}
			}
		});
		mPushFriendsLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				TrackingUtils.track(mContext,TrackingUtils.ACTION_SETTING, TrackingUtils.NAME_SETTING_SHARE);
				TrackingGoogleUtils.track(TrackingGoogleUtils.ACTION_SETTING, TrackingGoogleUtils.NAME_SETTING_SHARE);
				mSharePopWindow.showPopWindow(PopWindow.POP_WINDOW_SHARE);
			}
		});
		mhelpOrQSLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				TrackingUtils.track(mContext,TrackingUtils.ACTION_SETTING, TrackingUtils.NAME_SETTING_HELP);
				TrackingGoogleUtils.track(TrackingGoogleUtils.ACTION_SETTING, TrackingGoogleUtils.NAME_SETTING_HELP);
				Intent it = new Intent(mContext, HelpAndFeedBackActivity.class);
				startActivity(it);
			}
		});
		mScorePDLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				TrackingUtils.track(mContext,TrackingUtils.ACTION_SETTING, TrackingUtils.NAME_SETTING_SCORE_APP);
				TrackingGoogleUtils.track(TrackingGoogleUtils.ACTION_SETTING, TrackingGoogleUtils.NAME_SETTING_SCORE_APP);
				AppUtils.download(mContext, Score.SCORE_THIS_APP);
			}
		});
	}

	@Override
	public ViewGroup[] needShowLoading() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public int LayoutId() {
		// TODO Auto-generated method stub
		return E_layout.efun_pd_setting;
	}

	@Override
	public void initTitle(TitleView titleView) {
		titleView.setTitleRightStatus(View.GONE);
		hasShare = true;
		titleView.setPopWindowEnable(PopWindow.POP_WINDOW_SHARE,new BaseDataBean(BeanType.BEAN_SETTINGBEAN));
		titleView.setTitleCenterRes(E_string.efun_pd_set, false);
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == EfunFBFunctionActivity.EXTRA_SHARE_DIALOG_FLAG) {
			if(resultCode == EfunFBFunctionActivity.EXTRA_SUCCESS) {
				ToastUtils.toast(SettingActivity.this, E_string.efun_pd_share_success);
			} else {
				ToastUtils.toast(SettingActivity.this, E_string.efun_pd_share_failure);
			}
			
		}
	}

}
