package com.efun.platform.module.person.activity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.efun.core.tools.EfunLogUtil;
import com.efun.core.tools.EfunResourceUtil;
import com.efun.core.tools.EfunStringUtil;
import com.efun.platform.AndroidScape.E_array;
import com.efun.platform.AndroidScape.E_color;
import com.efun.platform.AndroidScape.E_dimens;
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
import com.efun.platform.http.request.bean.BindEmailByVcodeRequest;
import com.efun.platform.http.request.bean.CsGainRoleListRequest;
import com.efun.platform.http.request.bean.PhoneAreaTypeRequest;
import com.efun.platform.http.request.bean.SendVcodeToEmailRequest;
import com.efun.platform.http.request.bean.UserUpdateHeaderRequest;
import com.efun.platform.http.request.bean.UserUpdateInfoRequest;
import com.efun.platform.http.response.bean.AccountBindPhoneResponse;
import com.efun.platform.http.response.bean.BaseResponseBean;
import com.efun.platform.http.response.bean.BindEmailByVcodeResponse;
import com.efun.platform.http.response.bean.CsGainGameListResponse;
import com.efun.platform.http.response.bean.PhoneAreaTypeResponse;
import com.efun.platform.http.response.bean.SendVcodeToEmailResponse;
import com.efun.platform.http.response.bean.UserUpdateHeaderResponse;
import com.efun.platform.http.response.bean.UserUpdateInfoResponse;
import com.efun.platform.image.ImageManager;
import com.efun.platform.module.PopWindow;
import com.efun.platform.module.account.bean.BindResultBean;
import com.efun.platform.module.account.bean.PhoneAreaBean;
import com.efun.platform.module.account.bean.PhoneAreaResultBean;
import com.efun.platform.module.account.bean.ResultBean;
import com.efun.platform.module.account.bean.User;
import com.efun.platform.module.base.FixedActivity;
import com.efun.platform.module.base.impl.OnEfunItemClickListener;
import com.efun.platform.module.base.impl.OnUpdateUserListener;
import com.efun.platform.module.person.bean.UserUpdateBean;
import com.efun.platform.module.utils.AppUtils;
import com.efun.platform.module.utils.IntentUtils;
import com.efun.platform.module.utils.PopUtils;
import com.efun.platform.module.utils.ToastUtils;
import com.efun.platform.module.utils.TrackingUtils;
import com.efun.platform.utils.Const;
import com.efun.platform.utils.Const.HttpParam;
import com.efun.platform.utils.Const.Key;
import com.efun.platform.utils.Const.LoginPlatform;
import com.efun.platform.utils.Const.RequestCode;
import com.efun.platform.utils.Const.ResultCode;
import com.efun.platform.widget.TitleView;

/**
 * 修改玩家安全设置
 * 
 * @author itxuxxey
 * 
 */
public class ResetReBindEmailActivity extends FixedActivity {
	private EditText mOldEmail,mNewEmail,mEmailCode;
	private TextView mEmailCodeBtn,mEmailSaveInfoBtn;
	private String oldEmailStr,newEmailStr,emailVcodeStr;
	
	private static final int SEND_EMAIL_VCODE = 0;//發送驗證碼到郵箱
	private static final int BIND_EMAIL_BY_VCODE = 2;//通過驗證碼綁定郵箱
	private int requestFlag = 99;
	private User mUserInfoBean;

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
		}else{
			return null;
		}
	}

	@Override
	public void init(Bundle bundle) {
		mUserInfoBean = IPlatApplication.get().getUser();
		mOldEmail = (EditText) findViewById(E_id.edit_1);
		mNewEmail = (EditText) findViewById(E_id.edit_2);
		mEmailCode = (EditText) findViewById(E_id.edit_3);
		mEmailCodeBtn = (TextView) findViewById(E_id.email_send_code);
		mEmailSaveInfoBtn = (TextView) findViewById(E_id.email_save);
		
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
				mUserInfoBean.setBindEmail("1");
				mUserInfoBean.setEmail(resultBean.getEmail());
				((OnUpdateUserListener) IPlatApplication.get()
						.getOnEfunListener()).onUpdate(mUserInfoBean);
				setEmailStatue();
				finish();
			}
//			else{
//				ToastUtils.toast(mContext, resultBean.getMessage());
//			}
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

	@Override
	public ViewGroup[] needShowLoading() {
		return null;
	}

	@Override
	public int LayoutId() {
		return E_layout.efun_pd_persion_rebind_email;
	}

	@Override
	public void initTitle(TitleView titleView) {
		titleView.setTitleCenterRes(E_string.efun_pd_reset_per_bind_email, false);
		titleView.setTitleRightStatus(View.GONE);
	}
}
