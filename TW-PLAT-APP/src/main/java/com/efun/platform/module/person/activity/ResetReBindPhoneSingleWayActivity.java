package com.efun.platform.module.person.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.efun.core.db.EfunDatabase;
import com.efun.core.res.EfunResConfiguration;
import com.efun.core.tools.EfunResourceUtil;
import com.efun.core.tools.EfunStorageUtil;
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
import com.efun.platform.http.request.bean.AccountReBindPhoneRequest;
import com.efun.platform.http.request.bean.BaseRequestBean;
import com.efun.platform.http.request.bean.BindPhoneByCallPhoneRequest;
import com.efun.platform.http.request.bean.CheckBindPhoneStatusRequest;
import com.efun.platform.http.response.bean.AccountBindPhoneResponse;
import com.efun.platform.http.response.bean.BaseResponseBean;
import com.efun.platform.http.response.bean.BindPhoneByCallPhoneResponse;
import com.efun.platform.http.response.bean.CheckBindPhoneStatusResponse;
import com.efun.platform.module.account.activity.BindPhoneTwoWayActivity;
import com.efun.platform.module.account.bean.BindParamsBean;
import com.efun.platform.module.account.bean.BindResultBean;
import com.efun.platform.module.account.bean.PhoneAreaBean;
import com.efun.platform.module.account.bean.User;
import com.efun.platform.module.base.FixedActivity;
import com.efun.platform.module.base.impl.OnUpdateUserListener;
import com.efun.platform.module.person.bean.BindPhoneByCallPhoneBean;
import com.efun.platform.module.utils.AppUtils;
import com.efun.platform.module.utils.IntentUtils;
import com.efun.platform.module.utils.ProcessDatasUtils;
import com.efun.platform.module.utils.ToastUtils;
import com.efun.platform.module.utils.TrackingGoogleUtils;
import com.efun.platform.module.utils.ViewUtils;
import com.efun.platform.module.utils.ViewUtils.OnDialogSelect;
import com.efun.platform.utils.Const;
import com.efun.platform.utils.Const.HttpParam;
import com.efun.platform.widget.TitleView;

/**
 * 修改玩家安全设置
 * 
 * @author itxuxxey
 * 
 */
public class ResetReBindPhoneSingleWayActivity extends FixedActivity {

	private BindParamsBean mInfoBean;
	private String key,mPhoneNum,mCodeNum;;
	private TextView mGetVerifyCode, mFinishVerify;
	private EditText mCode;
	private User mUserInfoBean;
	

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
		}
		mUserInfoBean = IPlatApplication.get().getUser();
		
		mCode = (EditText) findViewById(E_id.edit_2);
		mGetVerifyCode = (TextView) findViewById(E_id.text_1);
		mFinishVerify = (TextView) findViewById(E_id.text_2);
		addListeners();
	}

	private void addListeners() {
		// TODO Auto-generated method stub
		//获取验证码
		mGetVerifyCode.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (TextUtils.isEmpty(mPhoneNum)) {
					return;
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
				requestWithDialog(bindPhoneRequest(),
						getString(E_string.efun_pd_bind_phone_intro));
			}
		});
		
	}

	@Override
	public void onSuccess(int requestType, BaseResponseBean baseResponse) {
		super.onSuccess(requestType, baseResponse);
		if (requestType == IPlatformRequest.REQ_ACCOUNT_BIND_PHONE_SEND_VCODE) {
			AccountBindPhoneResponse response = (AccountBindPhoneResponse) baseResponse;
			BindResultBean result = response.getResultBean();
			ToastUtils.toast(mContext, result.getMessage());
		}
		if (requestType == IPlatformRequest.REQ_ACCOUNT_BIND_PHONE) {
			AccountBindPhoneResponse response = (AccountBindPhoneResponse) baseResponse;
			BindResultBean result = response.getResultBean();
			ToastUtils.toast(mContext, result.getMessage());
			if (result.getCode().equals(HttpParam.RESULT_1000)) {
				mUserInfoBean.setAreaCode(key);
				mUserInfoBean.setPhone(result.getPhone());
				mUserInfoBean.setIsAuthPhone("1");
				IPlatApplication.get().setUser(mUserInfoBean);
				IPlatApplication.get().setUserHasChange(true);
				((OnUpdateUserListener) IPlatApplication.get()
						.getOnEfunListener()).onUpdate(mUserInfoBean);
				finish();
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
		return E_layout.efun_pd_rebind_phone_with_call_phone;
	}


	@Override
	public void initTitle(TitleView titleView) {
		titleView.setTitleLeftRes(E_drawable.efun_pd_back_white_normal);
		titleView.setTitleRightStatus(View.GONE);
		titleView.setTitleCenterRes(E_string.efun_pd_reset_per_safe_setting, false);
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
}
