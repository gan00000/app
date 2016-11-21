package com.efun.platform.module.account.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.efun.core.tools.EfunStringUtil;
import com.efun.platform.AndroidScape.E_drawable;
import com.efun.platform.AndroidScape.E_id;
import com.efun.platform.AndroidScape.E_layout;
import com.efun.platform.AndroidScape.E_string;
import com.efun.platform.IPlatApplication;
import com.efun.platform.http.dao.impl.IPlatformRequest;
import com.efun.platform.http.request.bean.AccountFindPwdByPhoneRequest;
import com.efun.platform.http.request.bean.AccountFindPwdRequest;
import com.efun.platform.http.request.bean.BaseRequestBean;
import com.efun.platform.http.response.bean.AccountResponse;
import com.efun.platform.http.response.bean.BaseResponseBean;
import com.efun.platform.module.account.bean.PhoneAreaBean;
import com.efun.platform.module.account.bean.ResultBean;
import com.efun.platform.module.base.FixedActivity;
import com.efun.platform.module.utils.AppUtils;
import com.efun.platform.module.utils.ProcessDatasUtils;
import com.efun.platform.module.utils.ToastUtils;
import com.efun.platform.module.utils.TrackingGoogleUtils;
import com.efun.platform.module.utils.ViewUtils;
import com.efun.platform.module.utils.ViewUtils.OnDialogSelect;
import com.efun.platform.utils.Const.HttpParam;
import com.efun.platform.widget.TitleView;
/**
 * 忘记密码
 * @author Jesse
 *
 */
public class ForgetPwdActivity extends FixedActivity{
	
	private TextView mForget,mPhoneArea;
	private EditText mAccount,mEmail,mPhone;
	private String loginName, email,phone;
	
	private ImageView changeIv;
	private LinearLayout mlay;
	
	private String[] mValues;
	private ArrayList<PhoneAreaBean> mPhoneAreas;
	private String key, pattern;// 區號和手機號碼判斷的正則表達式
	
	private static final int FIND_PWD_BY_PHONE = 1;
	private static final int FIND_PWD_BY_EMAIL = 2;
	
	private int findType = FIND_PWD_BY_PHONE;
	
	@Override
	public boolean needRequestData() {
		return false;
	}

	@Override
	public BaseRequestBean[] needRequestDataBean() {
//		AccountFindPwdRequest findPwdRequest = new AccountFindPwdRequest(mContext,loginName, email);
//		findPwdRequest.setArea(HttpParam.PLATFORM_AREA);
//		findPwdRequest.setDevice(HttpParam.PHONE);
//		findPwdRequest.setReqType(IPlatformRequest.REQ_ACCOUNT_FORGET_PWD);
		return null;
	}

	@Override
	public void init(Bundle bundle) {
		mAccount = (EditText) findViewById(E_id.edit_1);
		mEmail= (EditText) findViewById(E_id.forget_pwd_email);
		mForget = (TextView) findViewById(E_id.text_1);
		mPhoneArea = (TextView) findViewById(E_id.person_phone_area);
		
		changeIv = (ImageView) findViewById(E_id.forget_pwd_change_ico);
		mlay = (LinearLayout) findViewById(E_id.forget_pwd_phone);
		mPhone = (EditText) findViewById(E_id.forget_pwd_ed_phone);
		
		changeIv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(mlay.getVisibility() == View.VISIBLE){
					findType = FIND_PWD_BY_EMAIL;
					changeIv.setBackgroundResource(E_drawable.efun_pd_forget_pwd_email_icon);
					mlay.setVisibility(View.GONE);
					mEmail.setVisibility(View.VISIBLE);
				}else{
					findType = FIND_PWD_BY_PHONE;
					changeIv.setBackgroundResource(E_drawable.efun_pd_forget_pwd_phone_icon);
					mlay.setVisibility(View.VISIBLE);
					mEmail.setVisibility(View.GONE);
				}
			}
		});
		
		mForget.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				loginName = mAccount.getText().toString();
				email = mEmail.getText().toString();
				phone = mPhone.getText().toString();
				if(TextUtils.isEmpty(loginName)){
					ToastUtils.toast(mContext, E_string.efun_pd_toast_empty_password);
					return;
				}
				if(findType == FIND_PWD_BY_PHONE){//通过手机找回密码
					if(TextUtils.isEmpty(phone)){
						ToastUtils.toast(mContext, E_string.efun_pd_toast_empty_phone);
						return;
					}
					if (!phone.matches(pattern)) {
						ToastUtils.toast(mContext, E_string.efun_pd_toast_error_phone_type);
						return;
					}
					phone = key + "-"+phone.trim();
					
					requestWithDialog(createFindPwdByPhone(), getString(E_string.efun_pd_loading_msg_commend));
					
				}else if(findType == FIND_PWD_BY_EMAIL){//通过邮箱找回密码
					if(TextUtils.isEmpty(email)){
						ToastUtils.toast(mContext, E_string.efun_pd_toast_empty_email);
						return;
					}
					if(!EfunStringUtil.email(email)){
						ToastUtils.toast(mContext, E_string.efun_pd_toast_error_email);
						return;
					}
					requestWithDialog(createFindPwdByEmail(),getString(E_string.efun_pd_loading_msg_commend));
				}
				
			}
		});
		
		mPhoneArea.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View paramView) {
				// TODO Auto-generated method stub
				if (mValues != null) {
					ViewUtils.paramListDialog(mValues, mContext, new OnDialogSelect() {

						@Override
						public void onSelect(int postion) {
							// TODO Auto-generated method stub
							TrackingGoogleUtils.track(TrackingGoogleUtils.ACTION_PHONE_AREA, mValues[postion]);
							mPhoneArea.setText(mValues[postion]);
							key = mPhoneAreas.get(postion).getValue();
							pattern = mPhoneAreas.get(postion).getPattern();
						}
					});
				}
			}
		});
		mPhoneAreas = IPlatApplication.get().getmPhoneAreas();
		mValues = ProcessDatasUtils.getAllPhoneAreaCode();
		if(mPhoneAreas != null && mValues != null){
			mPhoneArea.setText(mValues[0]);
			key = mPhoneAreas.get(0).getValue();
			pattern = mPhoneAreas.get(0).getPattern();
		}
//		requestWithoutDialog(getPhoneAreasRequest());
	}

	@Override
	public void onSuccess(int requestType, BaseResponseBean baseResponse) {
		super.onSuccess(requestType,baseResponse);
		if(requestType==IPlatformRequest.REQ_ACCOUNT_FORGET_PWD_BY_EMAIL || requestType==IPlatformRequest.REQ_ACCOUNT_FORGET_PWD_BY_PHONE){
			AccountResponse response = (AccountResponse) baseResponse;
			ResultBean result = response.getResultBean();
			ToastUtils.toast(mContext, result.getMessage());
			if(result.getCode().equals(HttpParam.RESULT_1000)){
				finish();
			}
		}
		
//		if (requestType == IPlatformRequest.REQ_ACCOUNT_BIND_PHONE_AREA) {
//			PhoneAreaTypeResponse response = (PhoneAreaTypeResponse) baseResponse;
//			PhoneAreaResultBean result = response.getPhoneAreaResultBean();
//			if (result.getCode().equals(HttpParam.RESULT_1000)) {
//				mPhoneAreas = result.getmPhoneAreas();
//				mValues = new String[mPhoneAreas.size()];
//				for (int i = 0; i < mPhoneAreas.size(); i++) {
//					mValues[i] = mPhoneAreas.get(i).getValue();
//				}
//				mPhoneArea.setText(mValues[0]);
//				key = mPhoneAreas.get(0).getKey();
//				pattern = mPhoneAreas.get(0).getPattern();
//				EfunLogUtil
//						.logD("platform", "phoneArea:" + result.getMessage());
//			}
//		}
	}
	
	@Override
	public ViewGroup[] needShowLoading() {
		return null;
	}


	@Override
	public int LayoutId() {
		return E_layout.efun_pd_forget_password_new;
	}

	@Override
	public void initTitle(TitleView titleView) {
		titleView.setTitleRightStatus(View.GONE);
		titleView.setTitleCenterRes(E_string.efun_pd_forget_password, false);
	}
	
	private BaseRequestBean[] createFindPwdByEmail(){
		AccountFindPwdRequest findPwdRequest = new AccountFindPwdRequest(mContext,loginName, email);
		findPwdRequest.setArea(HttpParam.PLATFORM_AREA);
		findPwdRequest.setDevice(HttpParam.PHONE);
		findPwdRequest.setReqType(IPlatformRequest.REQ_ACCOUNT_FORGET_PWD_BY_EMAIL);
		return new BaseRequestBean[]{findPwdRequest};
	}
	
	private BaseRequestBean[] createFindPwdByPhone(){
		AccountFindPwdByPhoneRequest findPwdRequest = new AccountFindPwdByPhoneRequest(
				loginName, 
				phone.trim(), 
				HttpParam.PLATFORM_AREA, 
				HttpParam.APP, 
				HttpParam.PLATFORM, 
				AppUtils.getAppVersionName(mContext), 
				HttpParam.LANGUAGE);
		findPwdRequest.setReqType(IPlatformRequest.REQ_ACCOUNT_FORGET_PWD_BY_PHONE);
		return new BaseRequestBean[]{findPwdRequest};
	}
	
//	private BaseRequestBean[] getPhoneAreasRequest() {
//		PhoneAreaTypeRequest phoneAreaRequest = new PhoneAreaTypeRequest(
//				HttpParam.APP, HttpParam.PLATFORM_AREA,
//				HttpParam.PHONE_AREA_TYPE, HttpParam.PLATFORM,
//				AppUtils.getAppVersionName(mContext), HttpParam.LANGUAGE);
//		phoneAreaRequest
//				.setReqType(IPlatformRequest.REQ_ACCOUNT_BIND_PHONE_AREA);
//		return new BaseRequestBean[] { phoneAreaRequest };
//	}

}
