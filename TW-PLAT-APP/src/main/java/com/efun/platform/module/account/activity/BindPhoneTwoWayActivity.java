package com.efun.platform.module.account.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.efun.core.db.EfunDatabase;
import com.efun.core.tools.EfunResourceUtil;
import com.efun.core.tools.EfunStringUtil;
import com.efun.platform.AndroidScape.E_color;
import com.efun.platform.AndroidScape.E_drawable;
import com.efun.platform.AndroidScape.E_id;
import com.efun.platform.AndroidScape.E_layout;
import com.efun.platform.AndroidScape.E_string;
import com.efun.platform.IPlatApplication;
import com.efun.platform.http.dao.impl.IPlatformRequest;
import com.efun.platform.http.request.bean.AccountBindPhoneRequest;
import com.efun.platform.http.request.bean.AccountBindPhoneSendVcodeRequest;
import com.efun.platform.http.request.bean.BaseRequestBean;
import com.efun.platform.http.request.bean.CheckBindPhoneStatusRequest;
import com.efun.platform.http.response.bean.AccountBindPhoneResponse;
import com.efun.platform.http.response.bean.BaseResponseBean;
import com.efun.platform.http.response.bean.CheckBindPhoneStatusResponse;
import com.efun.platform.module.account.bean.BindParamsBean;
import com.efun.platform.module.account.bean.BindResultBean;
import com.efun.platform.module.account.bean.User;
import com.efun.platform.module.base.FixedActivity;
import com.efun.platform.module.base.impl.OnUpdateUserListener;
import com.efun.platform.module.utils.AppUtils;
import com.efun.platform.module.utils.ToastUtils;
import com.efun.platform.module.utils.TrackingGoogleUtils;
import com.efun.platform.module.utils.TrackingUtils;
import com.efun.platform.utils.Const;
import com.efun.platform.utils.Const.HttpParam;
import com.efun.platform.utils.StoreUtil.IPlatDatabaseValues;
import com.efun.platform.widget.TitleView;

/**
 * 绑定手机
 * （兩種綁定方式）
 * @author harvery
 * 
 */
public class BindPhoneTwoWayActivity extends FixedActivity {

	private BindParamsBean mInfoBean;
	private String key,mPhoneNum,callNum,mCodeNum;;
	private TextView callNumTv;
	private LinearLayout mCallPhoneBtn;
	private TextView mGetVerifyCode, mFinishVerify;
	private EditText mCode;
	private User mUserInfoBean;
	private boolean isCallBindPhone = false;//是否是通过语音绑定手机
	private CountDownTimer vcodePhone;

	@Override
	public boolean needRequestData() {
		return false;
	}

	@Override
	public BaseRequestBean[] needRequestDataBean() {
		return null;
	}

	@Override
	public void init(Bundle bundle) {
		mInfoBean = (BindParamsBean) bundle.get(Const.BEAN_KEY);
		if(mInfoBean != null){
			key = mInfoBean.getKey();
			mPhoneNum = mInfoBean.getPhoneNum();
			callNum = mInfoBean.getCallNum();
		}
		mUserInfoBean = IPlatApplication.get().getUser();
		
		callNumTv = (TextView) findViewById(E_id.bind_phone_call_num);
		mCallPhoneBtn = (LinearLayout) findViewById(E_id.bind_phone_call_btn);
		mCode = (EditText) findViewById(E_id.edit_2);
		mGetVerifyCode = (TextView) findViewById(E_id.text_1);
		mFinishVerify = (TextView) findViewById(E_id.text_2);
		
		initDatas();
		addListeners();
		vcodePhone = settingCountTime(mGetVerifyCode,false);
		if(vcodePhone != null){
			vcodePhone.start();
			mGetVerifyCode.setClickable(false);
		}
	}

	private void addListeners() {
		// TODO Auto-generated method stub
		//拨打电话
		mCallPhoneBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!EfunStringUtil.isEmpty(callNum)){
					if(mInfoBean.isReBindStatus()){
						TrackingUtils.track(mContext,TrackingUtils.ACTION_ACCOUNT,
								TrackingUtils.NAME_ACCOUNT_UPDATE_PHONE_DIAL);
						TrackingGoogleUtils.track(TrackingGoogleUtils.ACTION_ACCOUNT,
								TrackingGoogleUtils.NAME_ACCOUNT_UPDATE_PHONE_DIAL);
					}else{
						TrackingUtils.track(mContext,TrackingUtils.ACTION_ACCOUNT,
								TrackingUtils.NAME_ACCOUNT_BIND_PHONE_DIAL);
						TrackingGoogleUtils.track(TrackingGoogleUtils.ACTION_ACCOUNT,
								TrackingGoogleUtils.NAME_ACCOUNT_BIND_PHONE_DIAL);
					}
					isCallBindPhone = true;
					callPhone(callNum);
				}
			}
		});
		//获取验证码
		mGetVerifyCode.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (TextUtils.isEmpty(mPhoneNum)) {
					return;
				}
				vcodePhone = settingCountTime(mGetVerifyCode,true);
				if(vcodePhone != null){
					vcodePhone.start();
					mGetVerifyCode.setClickable(false);
				}
				requestWithDialog(sendVCodeRequest(),
						getString(E_string.efun_pd_bind_phone_intro));
			}
		});
		//绑定手机
		mFinishVerify.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mCodeNum = mCode.getText().toString();
				if (TextUtils.isEmpty(mPhoneNum)) {
					return;
				}
				if (TextUtils.isEmpty(mCodeNum)) {
					ToastUtils.toast(mContext,
							E_string.efun_pd_toast_empty_vcode);
					return;
				}
				if(mInfoBean.isReBindStatus()){
					TrackingUtils.track(mContext,TrackingUtils.ACTION_ACCOUNT,
							TrackingUtils.NAME_ACCOUNT_UPDATE_PHONE_CODE);
					TrackingGoogleUtils.track(TrackingGoogleUtils.ACTION_ACCOUNT,
							TrackingGoogleUtils.NAME_ACCOUNT_UPDATE_PHONE_CODE);
				}else{
					TrackingUtils.track(mContext,TrackingUtils.ACTION_ACCOUNT,
							TrackingUtils.NAME_ACCOUNT_BIND_PHONE_CODE);
					TrackingGoogleUtils.track(TrackingGoogleUtils.ACTION_ACCOUNT,
							TrackingGoogleUtils.NAME_ACCOUNT_BIND_PHONE_CODE);
				}
				requestWithDialog(bindPhoneRequest(),
						getString(E_string.efun_pd_bind_phone_intro));
			}
		});
		
	}

	private void initDatas() {
		// TODO Auto-generated method stub
		callNumTv.setText(callNum);
	}

	@Override
	public void onSuccess(int requestType, BaseResponseBean baseResponse) {
		super.onSuccess(requestType, baseResponse);
//		if (requestType == IPlatformRequest.REQ_ACCOUNT_BIND_PHONE_BY_CALL_PHONE) {
//			BindPhoneByCallPhoneResponse response = (BindPhoneByCallPhoneResponse)baseResponse;
//			BindPhoneByCallPhoneBean bean = response.getResponse();
//			if (bean.getCode().equals(HttpParam.RESULT_1000)) {
//				
//			}
//		}
		if (requestType == IPlatformRequest.REQ_ACCOUNT_BIND_PHONE_SEND_VCODE) {
			AccountBindPhoneResponse response = (AccountBindPhoneResponse) baseResponse;
			BindResultBean result = response.getResultBean();
			if(result.getCode().equals(HttpParam.RESULT_1000)){
				if(vcodePhone != null){
					vcodePhone.start();
					mGetVerifyCode.setClickable(false);
				}
			}
			ToastUtils.toast(mContext, result.getMessage());
		}
		if (requestType == IPlatformRequest.REQ_ACCOUNT_BIND_PHONE) {
			AccountBindPhoneResponse response = (AccountBindPhoneResponse) baseResponse;
			BindResultBean result = response.getResultBean();
			ToastUtils.toast(mContext, result.getMessage());
			if (result.getCode().equals(HttpParam.RESULT_1000)) {
				if(vcodePhone != null){
					vcodePhone.cancel();
				}
				mUserInfoBean.setAreaCode(key);
				mUserInfoBean.setPhone(result.getPhone());
				mUserInfoBean.setMd5Phone(result.getMd5Phone());
				mUserInfoBean.setIsAuthPhone("1");
				mUserInfoBean.setGetAward(result.isGetAward());
				mUserInfoBean.setFinished(result.isFinished());
				IPlatApplication.get().setUser(mUserInfoBean);
				IPlatApplication.get().setUserHasChange(true);
				((OnUpdateUserListener) IPlatApplication.get()
						.getOnEfunListener()).onUpdate(mUserInfoBean);
				finish();
			}
		}
		if(requestType == IPlatformRequest.REQ_ACCOUNT_BIND_PHONE_CHECK_STATUS){
			isCallBindPhone = false;
			CheckBindPhoneStatusResponse response = (CheckBindPhoneStatusResponse) baseResponse;
			if(response.getResponse().getCode().equals("1040")){//绑定成功
				mUserInfoBean.setAreaCode(key);
//				mUserInfoBean.setPhone(mPhoneNum);
				mUserInfoBean.setPhone(response.getResponse().getPhone());
				mUserInfoBean.setIsAuthPhone("1");
				mUserInfoBean.setMd5Phone(response.getResponse().getMd5Phone());
				mUserInfoBean.setGetAward(response.getResponse().isGetAward());
				mUserInfoBean.setFinished(response.getResponse().isFinished());
				IPlatApplication.get().setUser(mUserInfoBean);
				IPlatApplication.get().setUserHasChange(true);
				((OnUpdateUserListener) IPlatApplication.get()
						.getOnEfunListener()).onUpdate(mUserInfoBean);
				ToastUtils.toast(mContext, response.getResponse().getMessage());
				finish();
			}
//			else if(response.getResponse().getCode().equals("1006")){//没有绑定过
//				ToastUtils.toast(mContext, response.getResponse().getMessage());
//			}
			else{
				ToastUtils.toast(mContext, response.getResponse().getMessage());
			}
		}
	}

	@Override
	public ViewGroup[] needShowLoading() {
//		return new ViewGroup[] {(ViewGroup) mParentView};
		return null;
	}

	@Override
	public int LayoutId() {
		return E_layout.efun_pd_bind_phone_with_call_phone;
	}


	@Override
	public void initTitle(TitleView titleView) {
		titleView.setTitleLeftRes(E_drawable.efun_pd_back_white_normal);
		titleView.setTitleRightStatus(View.GONE);
		titleView.setTitleCenterRes(E_string.efun_pd_bind_phone, false);
		titleView.setTitleBarBackgroundRes(E_color.e_50c1e9);
		titleView.setTitleCenterTextColor(getResources().getColor(E_color.e_ffffff));
	}
	
	private BaseRequestBean[] sendVCodeRequest() {
		AccountBindPhoneSendVcodeRequest sendVCodeRequest = new AccountBindPhoneSendVcodeRequest(
				mPhoneNum,
				HttpParam.APP, HttpParam.PLATFORM_AREA, key,
				HttpParam.PLATFORM, AppUtils.getAppVersionName(mContext),
				HttpParam.LANGUAGE);
		if (IPlatApplication.get().getUser() != null) {
			sendVCodeRequest.setUid(IPlatApplication.get().getUser().getUid());
			sendVCodeRequest
					.setSign(IPlatApplication.get().getUser().getSign());
			sendVCodeRequest.setTimestamp(IPlatApplication.get().getUser()
					.getTimestamp());
		}
		sendVCodeRequest
				.setReqType(IPlatformRequest.REQ_ACCOUNT_BIND_PHONE_SEND_VCODE);
		return new BaseRequestBean[] { sendVCodeRequest };
	}

	private BaseRequestBean[] bindPhoneRequest() {
		AccountBindPhoneRequest bindPhoneRequest = new AccountBindPhoneRequest(
				mPhoneNum, IPlatApplication.get().getUser().getUid(), mCodeNum,
				IPlatApplication.get().getUser().getSign(), IPlatApplication
						.get().getUser().getTimestamp(),
				HttpParam.PLATFORM_AREA, HttpParam.APP, HttpParam.PLATFORM,
				AppUtils.getAppVersionName(mContext), HttpParam.LANGUAGE, key);
		bindPhoneRequest.setReqType(IPlatformRequest.REQ_ACCOUNT_BIND_PHONE);
		return new BaseRequestBean[] { bindPhoneRequest };
	}
	
	
	private BaseRequestBean[] checkPhoneHasBind() {
		CheckBindPhoneStatusRequest request = new CheckBindPhoneStatusRequest(
				HttpParam.PLATFORM_AREA, 
				key+"-"+mPhoneNum, 
				HttpParam.LANGUAGE, 
				HttpParam.APP,
				IPlatApplication.get().getUser().getUid());
		request.setReqType(IPlatformRequest.REQ_ACCOUNT_BIND_PHONE_CHECK_STATUS);
		return new BaseRequestBean[] { request };
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.e("yang", "----->onStop():"+isCallBindPhone);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.e("yang", "----->onResume():"+isCallBindPhone);
		if(isCallBindPhone){//检测绑定手机状态
			requestWithoutDialog(checkPhoneHasBind());
		}
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		EfunDatabase.saveSimpleInfo(this, IPlatDatabaseValues.PLATFORM_FILE, IPlatDatabaseValues.DATA_COUNT_TIME, System.currentTimeMillis());
		if(vcodePhone != null){
			vcodePhone.cancel();
		}
	}
	
	/**
	 * 拨打电话
	 * @param phone
	 */
	private void callPhone(String phone){
		String uri = "tel:"+phone;
		Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(uri));
		mContext.startActivity(intent); 
	}
	
	/**
	 * 倒計時
	 * @param controlBtn
	 * @param firstFlag
	 * @return
	 */
	private CountDownTimer settingCountTime(final TextView controlBtn ,boolean firstFlag){
		CountDownTimer downTime = null;
		
		long countTime = EfunDatabase.getSimpleLong(this, IPlatDatabaseValues.PLATFORM_FILE, IPlatDatabaseValues.DATA_COUNT_TIME);
		long remainTime = EfunDatabase.getSimpleLong(this, IPlatDatabaseValues.PLATFORM_FILE, IPlatDatabaseValues.DATA_REMAIN_TIME);
		if(remainTime == 0 && !firstFlag){
			return downTime;
		}
		if((System.currentTimeMillis() - countTime) > remainTime && !firstFlag){//超时了
			controlBtn.setBackgroundResource(E_drawable.efun_pd_cons_00d2c1_bg);
			controlBtn.setTextColor(BindPhoneTwoWayActivity.this.getResources().getColor(E_color.e_00d2c1));
			controlBtn.setText(EfunResourceUtil.findStringByName(this, "efun_pd_get_vcode_again"));
			EfunDatabase.saveSimpleInfo(BindPhoneTwoWayActivity.this, IPlatDatabaseValues.PLATFORM_FILE, IPlatDatabaseValues.DATA_REMAIN_TIME, 0);
			return downTime;
		}
		long currentTime = 60000;
		if(remainTime != 0){
			currentTime = remainTime - (System.currentTimeMillis() - countTime);
		}
		
		downTime = new CountDownTimer(currentTime,1000) {
			
			@Override
			public void onTick(long millisUntilFinished) {
				// TODO Auto-generated method stub
				EfunDatabase.saveSimpleInfo(BindPhoneTwoWayActivity.this, IPlatDatabaseValues.PLATFORM_FILE, IPlatDatabaseValues.DATA_REMAIN_TIME, millisUntilFinished);
				controlBtn.setBackgroundResource(E_drawable.efun_pd_cons_a6a6a6_bg);
				controlBtn.setTextColor(BindPhoneTwoWayActivity.this.getResources().getColor(E_color.e_a6a6a6));
				controlBtn.setText(millisUntilFinished/1000+EfunResourceUtil.findStringByName(BindPhoneTwoWayActivity.this, "efun_pd_get_vcode_again_timedown"));
			}
			
			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				EfunDatabase.saveSimpleInfo(BindPhoneTwoWayActivity.this, IPlatDatabaseValues.PLATFORM_FILE, IPlatDatabaseValues.DATA_REMAIN_TIME, 0);
				controlBtn.setBackgroundResource(E_drawable.efun_pd_cons_00d2c1_bg);
				controlBtn.setTextColor(BindPhoneTwoWayActivity.this.getResources().getColor(E_color.e_00d2c1));
				controlBtn.setText(EfunResourceUtil.findStringByName(BindPhoneTwoWayActivity.this, "efun_pd_get_vcode_again"));
				if(vcodePhone != null){
					vcodePhone = null;
				}
				mGetVerifyCode.setClickable(true);
			}
		};
		return downTime;
		
	}

}
