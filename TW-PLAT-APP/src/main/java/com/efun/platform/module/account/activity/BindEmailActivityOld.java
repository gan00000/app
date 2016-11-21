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
import com.efun.platform.http.request.bean.PhoneAreaTypeRequest;
import com.efun.platform.http.response.bean.AccountBindEmailResponse;
import com.efun.platform.http.response.bean.AccountBindPhoneResponse;
import com.efun.platform.http.response.bean.BaseResponseBean;
import com.efun.platform.http.response.bean.PhoneAreaTypeResponse;
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
public class BindEmailActivityOld extends FixedActivity {
	private User mUserInfoBean;
	private EditText emailEt;
	private TextView sendBtn;
	private String email;

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
		mUserInfoBean = (User) bundle.get(Const.BEAN_KEY);
		emailEt = (EditText) findViewById(E_id.edit_1);
		sendBtn = (TextView) findViewById(E_id.text_2);
		sendBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View paramView) {
				// TODO Auto-generated method stub
				email = emailEt.getText().toString();
				if(!EfunStringUtil.email(email)){
					ToastUtils.toast(mContext, getString(E_string.efun_pd_toast_error_email));
					return;
				}
				requestWithDialog(bindEmailRequest(), getString(E_string.efun_pd_bind_phone_intro));
			}
		});
	}

	@Override
	public void onSuccess(int requestType, BaseResponseBean baseResponse) {
		super.onSuccess(requestType, baseResponse);
		if (requestType == IPlatformRequest.REQ_ACCOUNT_BIND_EMAIL) {
			AccountBindEmailResponse response = (AccountBindEmailResponse) baseResponse;
			BindResultBean result = response.getResultBean();
			ToastUtils.toast(mContext, result.getMessage());
			if (result.getCode().equals(HttpParam.RESULT_1000)) {
				mUserInfoBean.setEmail(result.getEmail());
//				mUserInfoBean.setBindEmail("1");
				((OnUpdateUserListener) IPlatApplication.get()
						.getOnEfunListener()).onUpdate(mUserInfoBean);
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
		return E_layout.efun_pd_bind_email;
	}

	private BaseRequestBean[] bindEmailRequest() {
		AccountBindEmailRequest bindEmailRequest = new AccountBindEmailRequest(
				IPlatApplication.get().getUser().getUid(),
				HttpParam.PLATFORM_AREA, email, IPlatApplication.get()
						.getUser().getSign(), IPlatApplication.get().getUser()
						.getTimestamp(), HttpParam.APP, HttpParam.PLATFORM,
				AppUtils.getAppVersionName(mContext), HttpParam.LANGUAGE);
		bindEmailRequest.setReqType(IPlatformRequest.REQ_ACCOUNT_BIND_EMAIL);
		return new BaseRequestBean[] { bindEmailRequest };
	}

	@Override
	public void initTitle(TitleView titleView) {
		titleView.setTitleRightStatus(View.GONE);
		titleView.setTitleCenterRes(E_string.efun_pd_bind_email, false);
	}

}
