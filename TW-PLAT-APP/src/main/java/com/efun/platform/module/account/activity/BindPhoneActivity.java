package com.efun.platform.module.account.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.efun.core.tools.EfunStringUtil;
import com.efun.platform.AndroidScape.E_id;
import com.efun.platform.AndroidScape.E_layout;
import com.efun.platform.AndroidScape.E_string;
import com.efun.platform.IPlatApplication;
import com.efun.platform.http.dao.impl.IPlatformRequest;
import com.efun.platform.http.request.bean.AccountBindPhoneRequest;
import com.efun.platform.http.request.bean.AccountBindPhoneSendVcodeRequest;
import com.efun.platform.http.request.bean.BaseRequestBean;
import com.efun.platform.http.response.bean.AccountBindPhoneResponse;
import com.efun.platform.http.response.bean.BaseResponseBean;
import com.efun.platform.module.account.bean.BindResultBean;
import com.efun.platform.module.account.bean.PhoneAreaBean;
import com.efun.platform.module.account.bean.User;
import com.efun.platform.module.base.FixedActivity;
import com.efun.platform.module.base.impl.OnUpdateUserListener;
import com.efun.platform.module.utils.AppUtils;
import com.efun.platform.module.utils.ProcessDatasUtils;
import com.efun.platform.module.utils.ToastUtils;
import com.efun.platform.module.utils.TrackingGoogleUtils;
import com.efun.platform.module.utils.TrackingUtils;
import com.efun.platform.module.utils.ViewUtils;
import com.efun.platform.module.utils.ViewUtils.OnDialogSelect;
import com.efun.platform.utils.Const;
import com.efun.platform.utils.Const.HttpParam;
import com.efun.platform.widget.TitleView;

/**
 * 绑定手机
 * 
 * @author Jesse
 * 
 */
public class BindPhoneActivity extends FixedActivity {

	private TextView mGetVerifyCode, mFinishVerify, mPhoneArea;
	private LinearLayout mChoiceAreaLayout;
	private EditText mPhone, mCode;
	private String mPhoneNum, mCodeNum;
	private User mUserInfoBean;
	private String[] mValues;
	private ArrayList<PhoneAreaBean> mPhoneAreas;
	private String key, pattern;// 區號和手機號碼判斷的正則表達式

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
		mPhone = (EditText) findViewById(E_id.edit_1);
		mCode = (EditText) findViewById(E_id.edit_2);
		mGetVerifyCode = (TextView) findViewById(E_id.text_1);
		mFinishVerify = (TextView) findViewById(E_id.text_2);
		mChoiceAreaLayout = (LinearLayout) findViewById(E_id.phone_area);
		mPhoneArea = (TextView) findViewById(E_id.phone_area_content);

		mPhoneAreas = IPlatApplication.get().getmPhoneAreas();
		mValues = ProcessDatasUtils.getAllPhoneAreaCode();
		if(mPhoneAreas != null && mValues != null){
			mPhoneArea.setText(mValues[0]);
			key = mPhoneAreas.get(0).getValue();
			pattern = mPhoneAreas.get(0).getPattern();
		}
		
//		requestWithoutDialog(getPhoneAreasRequest());
		mGetVerifyCode.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (EfunStringUtil.isEmpty(key)) {
					ToastUtils.toast(mContext, E_string.efun_pd_toast_empty_phone_area);
					return;
				}
				mPhoneNum = mPhone.getText().toString();
				if (TextUtils.isEmpty(mPhoneNum)) {
					ToastUtils.toast(mContext,
							E_string.efun_pd_toast_empty_phone);
					return;
				}
				if (!mPhoneNum.matches(pattern)) {
					ToastUtils.toast(mContext, E_string.efun_pd_toast_error_phone_type);
					return;
				}

				requestWithDialog(sendVCodeRequest(),
						getString(E_string.efun_pd_bind_phone_intro));
			}
		});
		mFinishVerify.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mCodeNum = mCode.getText().toString();
				mPhoneNum = mPhone.getText().toString();
				if (EfunStringUtil.isEmpty(key)) {
					ToastUtils.toast(mContext, E_string.efun_pd_toast_empty_phone_area);
					return;
				}
				if (TextUtils.isEmpty(mPhoneNum)) {
					ToastUtils.toast(mContext,
							E_string.efun_pd_toast_error_phone);
					return;
				}
				if (!mPhoneNum.matches(pattern)) {
					ToastUtils.toast(mContext, E_string.efun_pd_toast_error_phone_type);
					return;
				}
				if (TextUtils.isEmpty(mCodeNum)) {
					ToastUtils.toast(mContext,
							E_string.efun_pd_toast_empty_vcode);
					return;
				}
				TrackingUtils.track(BindPhoneActivity.this,TrackingUtils.ACTION_ACCOUNT,
						TrackingUtils.NAME_ACCOUNT_BIND_PHONE);
				TrackingGoogleUtils.track(TrackingGoogleUtils.ACTION_ACCOUNT,
						TrackingGoogleUtils.NAME_ACCOUNT_BIND_PHONE);
				requestWithDialog(bindPhoneRequest(),
						getString(E_string.efun_pd_bind_phone_intro));
			}
		});

		mChoiceAreaLayout.setOnClickListener(new OnClickListener() {

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
				((OnUpdateUserListener) IPlatApplication.get()
						.getOnEfunListener()).onUpdate(mUserInfoBean);
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
		return E_layout.efun_pd_bind_phone;
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

//	private BaseRequestBean[] getPhoneAreasRequest() {
//		PhoneAreaTypeRequest phoneAreaRequest = new PhoneAreaTypeRequest(
//				HttpParam.APP, HttpParam.PLATFORM_AREA,
//				HttpParam.PHONE_AREA_TYPE, HttpParam.PLATFORM,
//				AppUtils.getAppVersionName(mContext), HttpParam.LANGUAGE);
//		phoneAreaRequest
//				.setReqType(IPlatformRequest.REQ_ACCOUNT_BIND_PHONE_AREA);
//		return new BaseRequestBean[] { phoneAreaRequest };
//	}

	@Override
	public void initTitle(TitleView titleView) {
		titleView.setTitleRightStatus(View.GONE);
		titleView.setTitleCenterRes(E_string.efun_pd_bind_phone, false);
	}

}
