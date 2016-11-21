package com.efun.platform.module.account.activity;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.efun.core.tools.EfunLogUtil;
import com.efun.core.tools.EfunStringUtil;
import com.efun.platform.AndroidScape.E_id;
import com.efun.platform.AndroidScape.E_layout;
import com.efun.platform.AndroidScape.E_string;
import com.efun.platform.IPlatApplication;
import com.efun.platform.http.dao.impl.IPlatformRequest;
import com.efun.platform.http.request.bean.AccountBindEmailRequest;
import com.efun.platform.http.request.bean.AccountBindPhoneRequest;
import com.efun.platform.http.request.bean.AccountBindPhoneSendVcodeRequest;
import com.efun.platform.http.request.bean.BaseRequestBean;
import com.efun.platform.http.request.bean.BindEmailByVcodeRequest;
import com.efun.platform.http.request.bean.PhoneAreaTypeRequest;
import com.efun.platform.http.request.bean.SendVcodeToEmailRequest;
import com.efun.platform.http.response.bean.AccountBindEmailResponse;
import com.efun.platform.http.response.bean.AccountBindPhoneResponse;
import com.efun.platform.http.response.bean.BaseResponseBean;
import com.efun.platform.http.response.bean.BindEmailByVcodeResponse;
import com.efun.platform.http.response.bean.PhoneAreaTypeResponse;
import com.efun.platform.http.response.bean.SendVcodeToEmailResponse;
import com.efun.platform.module.account.bean.BindResultBean;
import com.efun.platform.module.account.bean.PhoneAreaBean;
import com.efun.platform.module.account.bean.PhoneAreaResultBean;
import com.efun.platform.module.account.bean.ResultBean;
import com.efun.platform.module.account.bean.User;
import com.efun.platform.module.base.FixedActivity;
import com.efun.platform.module.base.impl.OnUpdateUserListener;
import com.efun.platform.module.utils.AppUtils;
import com.efun.platform.module.utils.ToastUtils;
import com.efun.platform.module.utils.TrackingUtils;
import com.efun.platform.utils.Const;
import com.efun.platform.utils.Const.HttpParam;
import com.efun.platform.widget.TitleView;

/**
 * 绑定郵箱
 * 
 * @author itxuxxey
 * 
 */
public class BindEmailActivity extends FixedActivity {
	private EditText mEmail,mEmailCode;
	private TextView mEmailCodeBtn,mEmailSaveInfoBtn;
	private String emailStr,emailVcodeStr;
	
	private static final int SEND_EMAIL_VCODE = 0;//發送驗證碼到郵箱
	private static final int BIND_EMAIL_BY_VCODE = 2;//通過驗證碼綁定郵箱
	private int requestFlag = 99;
	private User mUserInfoBean;

	@Override
	public boolean needRequestData() {
		return false;
	}

	@Override
	public BaseRequestBean[] needRequestDataBean() {
		if(requestFlag == SEND_EMAIL_VCODE){
			SendVcodeToEmailRequest mRequest = new SendVcodeToEmailRequest();
			if(IPlatApplication.get().getUser()!=null){
				mRequest.setSign(IPlatApplication.get().getUser().getSign());
				mRequest.setTimestamp(IPlatApplication.get().getUser().getTimestamp());
				mRequest.setUid(IPlatApplication.get().getUser().getUid());
			}
			mRequest.setReqType(IPlatformRequest.REQ_ACCOUNT_SEND_VCODE_TO_EMAIL);
			mRequest.setEmail(emailStr);
			mRequest.setFromType(HttpParam.APP);
			mRequest.setLanguage(HttpParam.LANGUAGE);
			mRequest.setPackageVersion(AppUtils.getAppVersionName(this));
			mRequest.setPlatform(HttpParam.PLATFORM_AREA);
			mRequest.setVersion(HttpParam.PLATFORM);
			return new BaseRequestBean[] { mRequest };
		}else if(requestFlag == BIND_EMAIL_BY_VCODE){
			BindEmailByVcodeRequest mRequest = new BindEmailByVcodeRequest();
			if(IPlatApplication.get().getUser()!=null){
				mRequest.setSign(IPlatApplication.get().getUser().getSign());
				mRequest.setTimestamp(IPlatApplication.get().getUser().getTimestamp());
				mRequest.setUid(IPlatApplication.get().getUser().getUid());
			}
			mRequest.setReqType(IPlatformRequest.REQ_ACCOUNT_BIND_EMAIL_BY_VCODE);
			mRequest.setEmail("");
			mRequest.setFromType(HttpParam.APP);
			mRequest.setLanguage(HttpParam.LANGUAGE);
			mRequest.setPackageVersion(AppUtils.getAppVersionName(this));
			mRequest.setPlatform(HttpParam.PLATFORM_AREA);
			mRequest.setValidEmail(emailStr);
			mRequest.setVcode(emailVcodeStr);
			mRequest.setVersion(HttpParam.PLATFORM);
			mRequest.setBusiCode(HttpParam.BINDEMAIL);
			return new BaseRequestBean[] { mRequest };
		}else{
			return null;
		}
	}

	@Override
	public void init(Bundle bundle) {
		mUserInfoBean = IPlatApplication.get().getUser();
		mEmail = (EditText) findViewById(E_id.edit_1);
		mEmailCode = (EditText) findViewById(E_id.edit_3);
		mEmailCodeBtn = (TextView) findViewById(E_id.email_send_code);
		mEmailSaveInfoBtn = (TextView) findViewById(E_id.text_2);
		if(mUserInfoBean != null){
			if(!TextUtils.isEmpty(mUserInfoBean.getEmail())){
				mEmail.setText(mUserInfoBean.getEmail());
				mEmail.setEnabled(false);
			}else{
				mEmail.setEnabled(true);
			}
		}
		addListeners();
	}
	
	private void addListeners(){
		mEmailCodeBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				emailStr = mEmail.getText().toString();
				if(TextUtils.isEmpty(mUserInfoBean.getEmail())){//避开自动填写的邮箱地址
					if(!EfunStringUtil.email(emailStr)){
						ToastUtils.toast(mContext, getString(E_string.efun_pd_toast_error_email));
						return;
					}
				}
				requestFlag = SEND_EMAIL_VCODE;
				requestWithDialog(needRequestDataBean(), getString(E_string.efun_pd_send_vcode));
			}
		});
		mEmailSaveInfoBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				emailStr = mEmail.getText().toString();
				emailVcodeStr = mEmailCode.getText().toString();
				if(TextUtils.isEmpty(mUserInfoBean.getEmail())){//避开自动填写的邮箱地址
					if(!EfunStringUtil.email(emailStr)){
						ToastUtils.toast(mContext, getString(E_string.efun_pd_toast_error_email));
						return;
					}
				}
				if(EfunStringUtil.isEmpty(emailVcodeStr)){
					ToastUtils.toast(mContext, getString(E_string.efun_pd_toast_error_vcode));
					return;
				}
				requestFlag = BIND_EMAIL_BY_VCODE;
				requestWithDialog(needRequestDataBean(), getString(E_string.efun_pd_loading_msg_commend));
			}
		});
	}

	@Override
	public void onSuccess(int requestType, BaseResponseBean baseResponse) {
		super.onSuccess(requestType, baseResponse);
		
		if (requestType == IPlatformRequest.REQ_ACCOUNT_SEND_VCODE_TO_EMAIL) {
			SendVcodeToEmailResponse response = (SendVcodeToEmailResponse) baseResponse;
			ResultBean resultBean = response.getResultBean();
			ToastUtils.toast(mContext, resultBean.getMessage());
		}else if (requestType == IPlatformRequest.REQ_ACCOUNT_BIND_EMAIL_BY_VCODE) {
			BindEmailByVcodeResponse response = (BindEmailByVcodeResponse) baseResponse;
			BindResultBean resultBean = response.getResultBean();
			ToastUtils.toast(mContext, resultBean.getMessage());
			if(resultBean.getCode().equals(HttpParam.RESULT_1000)){
				mUserInfoBean.setBindEmail("1");
				mUserInfoBean.setEmail(resultBean.getEmail());
				((OnUpdateUserListener) IPlatApplication.get()
						.getOnEfunListener()).onUpdate(mUserInfoBean);
				setEmailStatue();
				finish();
			}
		}

	}
	
	private void setEmailStatue(){
		mEmail.setText("");
		mEmailCode.setText("");
		emailStr = "";
		emailVcodeStr = "";
	}

	@Override
	public ViewGroup[] needShowLoading() {
		return null;
	}

	@Override
	public int LayoutId() {
		return E_layout.efun_pd_bind_email;
	}

//	private BaseRequestBean[] bindEmailRequest() {
//		AccountBindEmailRequest bindEmailRequest = new AccountBindEmailRequest(
//				IPlatApplication.get().getUser().getUid(),
//				HttpParam.PLATFORM_AREA, email, IPlatApplication.get()
//						.getUser().getSign(), IPlatApplication.get().getUser()
//						.getTimestamp(), HttpParam.APP, HttpParam.PLATFORM,
//				AppUtils.getAppVersionName(mContext), HttpParam.LANGUAGE);
//		bindEmailRequest.setReqType(IPlatformRequest.REQ_ACCOUNT_BIND_EMAIL);
//		return new BaseRequestBean[] { bindEmailRequest };
//	}

	@Override
	public void initTitle(TitleView titleView) {
		titleView.setTitleRightStatus(View.GONE);
		titleView.setTitleCenterRes(E_string.efun_pd_bind_email, false);
	}

}
