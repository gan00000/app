package com.efun.platform.module.person.activity;

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
import com.efun.platform.http.request.bean.AccountBindPhoneSendVcodeRequest;
import com.efun.platform.http.request.bean.AccountReBindPhoneRequest;
import com.efun.platform.http.request.bean.BaseRequestBean;
import com.efun.platform.http.request.bean.BindEmailByVcodeRequest;
import com.efun.platform.http.request.bean.SendVcodeToEmailRequest;
import com.efun.platform.http.response.bean.AccountBindPhoneResponse;
import com.efun.platform.http.response.bean.BaseResponseBean;
import com.efun.platform.http.response.bean.BindEmailByVcodeResponse;
import com.efun.platform.http.response.bean.SendVcodeToEmailResponse;
import com.efun.platform.module.account.bean.BindResultBean;
import com.efun.platform.module.account.bean.PhoneAreaBean;
import com.efun.platform.module.account.bean.ResultBean;
import com.efun.platform.module.account.bean.User;
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
 * 修改玩家安全设置
 * 
 * @author itxuxxey
 * 
 */
public class ResetSafeSettingActivity extends FixedActivity {
	private EditText mOldEmail,mNewEmail,mEmailCode,mOldPhone,mNewPhone,mPhoneCode;
	private TextView mEmailCodeBtn,mPhoneCodeBtn,mEmailSaveInfoBtn,mPhoneSaveInfoBtn,mPhoneArea;
	private String oldEmailStr,newEmailStr,emailVcodeStr,oldPhoneStr,newPhoneStr,phoneVcodeStr;
	private LinearLayout mChoiceAreaLayout,mBindEmialLayout,mBindPhoneLayout;
	private String[] mValues;
	private ArrayList<PhoneAreaBean> mPhoneAreas;
	private String key, pattern;// 區號和手機號碼判斷的正則表達式
	private User mUserInfoBean;
	
	private static final int SEND_EMAIL_VCODE = 0;//發送驗證碼到郵箱
	private static final int SEND_PHONE_VCODE = 1;//發送驗證碼到手機
	private static final int BIND_EMAIL_BY_VCODE = 2;//通過驗證碼綁定郵箱
	private static final int BIND_PHONE_BY_VCODE = 3;//通過驗證碼綁定手機
	private static final int CHECK_PHONE_AREA = 4;//查詢手機號碼區域信息
	private int requestFlag = 99;

	@Override
	public void onClickRight() {
		super.onClickRight();
		finish();
	}

	@Override
	public boolean needRequestData() {
		// TODO Auto-generated method stub
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
			mRequest.setEmail(newEmailStr);
			mRequest.setFromType(HttpParam.APP);
			mRequest.setLanguage(HttpParam.LANGUAGE);
			mRequest.setPackageVersion(AppUtils.getAppVersionName(this));
			mRequest.setPlatform(HttpParam.PLATFORM_AREA);
			mRequest.setVersion(HttpParam.PLATFORM);
			return new BaseRequestBean[] { mRequest };
		}else if(requestFlag == SEND_PHONE_VCODE){
			AccountBindPhoneSendVcodeRequest sendVCodeRequest = new AccountBindPhoneSendVcodeRequest(
					newPhoneStr,
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
		}else if(requestFlag == BIND_EMAIL_BY_VCODE){
			BindEmailByVcodeRequest mRequest = new BindEmailByVcodeRequest();
			if(IPlatApplication.get().getUser()!=null){
				mRequest.setSign(IPlatApplication.get().getUser().getSign());
				mRequest.setTimestamp(IPlatApplication.get().getUser().getTimestamp());
				mRequest.setUid(IPlatApplication.get().getUser().getUid());
			}
			mRequest.setReqType(IPlatformRequest.REQ_ACCOUNT_BIND_EMAIL_BY_VCODE);
			mRequest.setEmail(oldEmailStr);
			mRequest.setFromType(HttpParam.APP);
			mRequest.setLanguage(HttpParam.LANGUAGE);
			mRequest.setPackageVersion(AppUtils.getAppVersionName(this));
			mRequest.setPlatform(HttpParam.PLATFORM_AREA);
			mRequest.setValidEmail(newEmailStr);
			mRequest.setVcode(emailVcodeStr);
			mRequest.setVersion(HttpParam.PLATFORM);
			mRequest.setBusiCode(HttpParam.BINDEMAIL);
			return new BaseRequestBean[] { mRequest };
		}else if(requestFlag == BIND_PHONE_BY_VCODE){
			AccountReBindPhoneRequest bindPhoneRequest = new AccountReBindPhoneRequest(
					oldPhoneStr, IPlatApplication.get().getUser().getUid(), phoneVcodeStr,
					IPlatApplication.get().getUser().getSign(), IPlatApplication
							.get().getUser().getTimestamp(),
					HttpParam.PLATFORM_AREA, HttpParam.APP, HttpParam.PLATFORM,
					AppUtils.getAppVersionName(mContext), HttpParam.LANGUAGE, key,newPhoneStr);
			bindPhoneRequest.setReqType(IPlatformRequest.REQ_ACCOUNT_REBIND_PHONE);
			return new BaseRequestBean[] { bindPhoneRequest };
			
		}
//		else if(requestFlag == CHECK_PHONE_AREA){
//			PhoneAreaTypeRequest phoneAreaRequest = new PhoneAreaTypeRequest(
//					HttpParam.APP, HttpParam.PLATFORM_AREA,
//					HttpParam.PHONE_AREA_TYPE, HttpParam.PLATFORM,
//					AppUtils.getAppVersionName(mContext), HttpParam.LANGUAGE);
//			phoneAreaRequest
//					.setReqType(IPlatformRequest.REQ_ACCOUNT_BIND_PHONE_AREA);
//			return new BaseRequestBean[] { phoneAreaRequest };
//		}
		else{
			return null;
		}
	}

	@Override
	public void init(Bundle bundle) {
		mUserInfoBean = IPlatApplication.get().getUser();
		mOldEmail = (EditText) findViewById(E_id.edit_1);
		mNewEmail = (EditText) findViewById(E_id.edit_2);
		mEmailCode = (EditText) findViewById(E_id.edit_3);
		mOldPhone = (EditText) findViewById(E_id.edit_4);
		mNewPhone = (EditText) findViewById(E_id.edit_5);
		mPhoneCode = (EditText) findViewById(E_id.edit_6);
		mEmailCodeBtn = (TextView) findViewById(E_id.email_send_code);
		mPhoneCodeBtn = (TextView) findViewById(E_id.phone_send_code);
		mEmailSaveInfoBtn = (TextView) findViewById(E_id.email_save);
		mPhoneSaveInfoBtn = (TextView) findViewById(E_id.phone_save);
		mChoiceAreaLayout = (LinearLayout) findViewById(E_id.phone_area);
		mPhoneArea = (TextView) findViewById(E_id.phone_area_content);
		
		mBindEmialLayout = (LinearLayout) findViewById(E_id.reset_bind_email);
		mBindPhoneLayout = (LinearLayout) findViewById(E_id.reset_bind_phone);
		
		if(mUserInfoBean.getBindEmail().equals("1")){
			mBindEmialLayout.setVisibility(View.VISIBLE);
		}
		if(!EfunStringUtil.isEmpty(mUserInfoBean.getPhone())){
			mBindPhoneLayout.setVisibility(View.VISIBLE);
		}
		

		mPhoneAreas = IPlatApplication.get().getmPhoneAreas();
		mValues = ProcessDatasUtils.getAllPhoneAreaCode();
		if(mPhoneAreas != null && mValues != null){
			mPhoneArea.setText(mValues[0]);
			key = mPhoneAreas.get(0).getValue();
			pattern = mPhoneAreas.get(0).getPattern();
		}
//		requestFlag = CHECK_PHONE_AREA;
//		requestWithoutDialog(needRequestDataBean());
		addListeners();
	}
	
	private void addListeners(){
		mEmailCodeBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				oldEmailStr = mOldEmail.getText().toString();
				newEmailStr = mNewEmail.getText().toString();
				if(EfunStringUtil.isEmpty(oldEmailStr)){
					ToastUtils.toast(mContext, getString(E_string.efun_pd_toast_error_oldemail));
					return;
				}
				if(!EfunStringUtil.email(newEmailStr)){
					ToastUtils.toast(mContext, getString(E_string.efun_pd_toast_error_newemail));
					return;
				}
				requestFlag = SEND_EMAIL_VCODE;
				requestWithDialog(needRequestDataBean(), getString(E_string.efun_pd_send_vcode));
			}
		});
		mEmailSaveInfoBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				oldEmailStr = mOldEmail.getText().toString();
				newEmailStr = mNewEmail.getText().toString();
				emailVcodeStr = mEmailCode.getText().toString();
				if(EfunStringUtil.isEmpty(oldEmailStr)){
					ToastUtils.toast(mContext, getString(E_string.efun_pd_toast_error_oldemail));
					return;
				}
				if(!EfunStringUtil.email(newEmailStr)){
					ToastUtils.toast(mContext, getString(E_string.efun_pd_toast_error_newemail));
					return;
				}
				if(EfunStringUtil.isEmpty(emailVcodeStr)){
					ToastUtils.toast(mContext, getString(E_string.efun_pd_toast_error_vcode));
					return;
				}
				requestFlag = BIND_EMAIL_BY_VCODE;
				requestWithDialog(needRequestDataBean(), getString(E_string.efun_pd_loading_msg_commend));
			}
		});
		mPhoneCodeBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (EfunStringUtil.isEmpty(key)) {
					ToastUtils.toast(mContext, E_string.efun_pd_toast_empty_phone_area);
					return;
				}
				newPhoneStr = mNewPhone.getText().toString();
				if (TextUtils.isEmpty(newPhoneStr)) {
					ToastUtils.toast(mContext,
							E_string.efun_pd_toast_empty_phone);
					return;
				}
				if (!newPhoneStr.matches(pattern)) {
					ToastUtils.toast(mContext, E_string.efun_pd_toast_error_new_phone_type);
					return;
				}
				requestFlag = SEND_PHONE_VCODE;
				requestWithDialog(needRequestDataBean(),
						getString(E_string.efun_pd_bind_phone_intro));
			}
		});
		mPhoneSaveInfoBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				phoneVcodeStr = mPhoneCode.getText().toString();
				oldPhoneStr = mOldPhone.getText().toString();
				newPhoneStr = mNewPhone.getText().toString();
				if (EfunStringUtil.isEmpty(key)) {
					ToastUtils.toast(mContext, E_string.efun_pd_toast_empty_phone_area);
					return;
				}
				if (TextUtils.isEmpty(oldPhoneStr)) {
					ToastUtils.toast(mContext,
							E_string.efun_pd_toast_empty_phone);
					return;
				}
				if (TextUtils.isEmpty(newPhoneStr)) {
					ToastUtils.toast(mContext,
							E_string.efun_pd_toast_empty_phone);
					return;
				}
				if (!newPhoneStr.matches(pattern)) {
					ToastUtils.toast(mContext, E_string.efun_pd_toast_error_new_phone_type);
					return;
				}
				if (TextUtils.isEmpty(phoneVcodeStr)) {
					ToastUtils.toast(mContext,
							E_string.efun_pd_toast_empty_vcode);
					return;
				}
				requestFlag = BIND_PHONE_BY_VCODE;
				requestWithDialog(needRequestDataBean(),
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
		if (requestType == IPlatformRequest.REQ_ACCOUNT_SEND_VCODE_TO_EMAIL) {
			SendVcodeToEmailResponse response = (SendVcodeToEmailResponse) baseResponse;
			ResultBean resultBean = response.getResultBean();
			ToastUtils.toast(mContext, resultBean.getMessage());
//			if(resultBean.getCode().equals(HttpParam.RESULT_1000)){
//				ToastUtils.toast(mContext, resultBean.getMessage());
//			}else{
//				ToastUtils.toast(mContext, resultBean.getMessage());
//			}
		}else if (requestType == IPlatformRequest.REQ_ACCOUNT_BIND_EMAIL_BY_VCODE) {
			BindEmailByVcodeResponse response = (BindEmailByVcodeResponse) baseResponse;
			BindResultBean resultBean = response.getResultBean();
			ToastUtils.toast(mContext, resultBean.getMessage());
			if(resultBean.getCode().equals(HttpParam.RESULT_1000)){
				mUserInfoBean.setEmail(resultBean.getEmail());
				IPlatApplication.get().setUser(mUserInfoBean);
				setEmailStatue();
			}
//			else{
//				ToastUtils.toast(mContext, resultBean.getMessage());
//			}
		}
//		else if (requestType == IPlatformRequest.REQ_ACCOUNT_BIND_PHONE_AREA) {
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
		else if (requestType == IPlatformRequest.REQ_ACCOUNT_BIND_PHONE_SEND_VCODE) {
			AccountBindPhoneResponse response = (AccountBindPhoneResponse) baseResponse;
			BindResultBean result = response.getResultBean();
			ToastUtils.toast(mContext, result.getMessage());
		}else if (requestType == IPlatformRequest.REQ_ACCOUNT_REBIND_PHONE) {
			AccountBindPhoneResponse response = (AccountBindPhoneResponse) baseResponse;
			BindResultBean result = response.getResultBean();
			ToastUtils.toast(mContext, result.getMessage());
			if (result.getCode().equals(HttpParam.RESULT_1000)) {
				mUserInfoBean.setPhone(result.getPhone());
				mUserInfoBean.setIsAuthPhone("1");
				setPhoneStatue();
			}
		}
	}
	
	private void setEmailStatue(){
		mOldEmail.setText("");
		mNewEmail.setText("");
		mEmailCode.setText("");
		oldEmailStr = "";
		newEmailStr = "";
		emailVcodeStr = "";
	}
	private void setPhoneStatue(){
		mOldPhone.setText("");
		mNewPhone.setText("");
		mPhoneCode.setText("");
		oldPhoneStr = "";
		newPhoneStr = "";
		phoneVcodeStr = "";
		key = "";
		mPhoneArea.setText(getString(E_string.efun_pd_bind_phone_choice_area));
	}

	@Override
	public ViewGroup[] needShowLoading() {
		return null;
	}

	@Override
	public int LayoutId() {
		return E_layout.efun_pd_persion_reset_safe_setting;
	}

	@Override
	public void initTitle(TitleView titleView) {
		titleView.setTitleCenterRes(E_string.efun_pd_reset_per_safe_setting, false);
		titleView.setTitleRightStatus(View.GONE);
	}
}
