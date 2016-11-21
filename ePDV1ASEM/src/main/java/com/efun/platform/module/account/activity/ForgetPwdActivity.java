package com.efun.platform.module.account.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.efun.core.tools.EfunStringUtil;
import com.efun.platform.AndroidScape.E_color;
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
 * @author harvey
 *
 */
public class ForgetPwdActivity extends FixedActivity{
	
	private LinearLayout mForget;
	private EditText mAccount,mEmail;
	private String loginName, email;
//	private ImageView emailEye;
//	private boolean isEmailPWHidden = true;
	
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
		mAccount = (EditText) findViewById(E_id.edit_1);
		mEmail= (EditText) findViewById(E_id.edit_2);
//		emailEye = (ImageView) findViewById(E_id.findpwd_eye);
		mForget = (LinearLayout) findViewById(E_id.contaier_linear_1);
		mForget.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				loginName = mAccount.getText().toString();
				email = mEmail.getText().toString();
				if(TextUtils.isEmpty(loginName)){
					ToastUtils.toast(mContext, E_string.efun_pd_toast_empty_account);
					return;
				}
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
		});
		
	}

	@Override
	public void onSuccess(int requestType, BaseResponseBean baseResponse) {
		super.onSuccess(requestType,baseResponse);
		if(requestType==IPlatformRequest.REQ_ACCOUNT_FORGET_PWD_BY_EMAIL){
			AccountResponse response = (AccountResponse) baseResponse;
			ResultBean result = response.getResultBean();
			ToastUtils.toast(mContext, result.getMessage());
			if(result.getCode().equals(HttpParam.RESULT_1000)){
				finish();
			}
		}
	}
	
	@Override
	public ViewGroup[] needShowLoading() {
		return null;
	}


	@Override
	public int LayoutId() {
		return E_layout.efun_pd_forget_password_1;
	}

	@Override
	public void initTitle(TitleView titleView) {
		titleView.setTitleLeftRes(E_drawable.efun_pd_back_focus);
		titleView.setTitleRightStatus(View.GONE);
//		titleView.setTitleBarBackgroundRes(getResources().getColor(
//				E_color.e_transparent));
//		titleView.setTitleCenterRes(E_string.efun_pd_login, false);
		titleView.setTitleCenterStatus(View.GONE);
//		titleView.setTitleCenterTextColor(Color.WHITE);
		titleView.setTitleBottomLineStatus(View.GONE);
		titleView.setTitleBarBackgroundRes(E_color.e_ffffff);
	}
	
	private BaseRequestBean[] createFindPwdByEmail(){
		AccountFindPwdRequest findPwdRequest = new AccountFindPwdRequest(mContext,loginName, email);
		findPwdRequest.setArea(HttpParam.PLATFORM_AREA);
		findPwdRequest.setDevice(HttpParam.PHONE);
		findPwdRequest.setReqType(IPlatformRequest.REQ_ACCOUNT_FORGET_PWD_BY_EMAIL);
		return new BaseRequestBean[]{findPwdRequest};
	}

}
