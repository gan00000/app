package com.efun.platform.module.person.activity;

import android.content.Intent;
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
import android.widget.TextView;

import com.efun.platform.AndroidScape.E_id;
import com.efun.platform.AndroidScape.E_layout;
import com.efun.platform.AndroidScape.E_string;
import com.efun.platform.IPlatApplication;
import com.efun.platform.http.dao.impl.IPlatformRequest;
import com.efun.platform.http.request.bean.AccountResetPwdRequest;
import com.efun.platform.http.request.bean.BaseRequestBean;
import com.efun.platform.http.response.bean.AccountResponse;
import com.efun.platform.http.response.bean.BaseResponseBean;
import com.efun.platform.module.account.bean.ResultBean;
import com.efun.platform.module.base.FixedActivity;
import com.efun.platform.module.base.impl.OnEfunItemClickListener;
import com.efun.platform.module.base.impl.OnLoginFinishListener;
import com.efun.platform.module.utils.TextToolUtils;
import com.efun.platform.module.utils.ToastUtils;
import com.efun.platform.module.utils.UserUtils;
import com.efun.platform.module.utils.ViewUtils;
import com.efun.platform.utils.Const.HttpParam;
import com.efun.platform.utils.Const.RequestCode;
import com.efun.platform.utils.Const.ResultCode;
import com.efun.platform.utils.Const.ToastType;
import com.efun.platform.widget.TitleView;

/**
 * 更改密碼
 * 
 * @author itxuxxey
 * 
 */
public class ResetPasswordActivity extends FixedActivity {

	private EditText oldPwdEdit, newPwdEdit, confirmEdit;
	private String loginName, loginPwd, newPwd, confirmPwd;
	private AccountResetPwdRequest resetPwdRequest;
	private TextView confirmBtn;
	private ImageView mOldEye, mNewEye, mConfirmEye;
	private boolean isOldPWHidden = true;
	private boolean isNewPWHidden = true;
	private boolean isConfirmPWHidden = true;

	@Override
	public boolean needRequestData() {
		return false;
	}

	@Override
	public BaseRequestBean[] needRequestDataBean() {
		resetPwdRequest = new AccountResetPwdRequest(mContext, loginName,
				loginPwd, newPwd);
		resetPwdRequest.setReqType(IPlatformRequest.REQ_ACCOUNT_RESET_PWD);
		return new BaseRequestBean[] { resetPwdRequest };
	}

	@Override
	public void init(Bundle bundle) {
		loginName = IPlatApplication.get().getUser().getAccountName();
		oldPwdEdit = (EditText) findViewById(E_id.edit_1);
		newPwdEdit = (EditText) findViewById(E_id.edit_2);
		confirmEdit = (EditText) findViewById(E_id.edit_3);
		confirmBtn = (TextView) findViewById(E_id.confirm_btn);
		mOldEye = (ImageView) findViewById(E_id.old_eye);
		mNewEye = (ImageView) findViewById(E_id.new_eye);
		mConfirmEye = (ImageView) findViewById(E_id.cfirm_eye);

		confirmBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				loginPwd = oldPwdEdit.getText().toString();
				newPwd = newPwdEdit.getText().toString();
				confirmPwd = confirmEdit.getText().toString();
				if (TextUtils.isEmpty(loginPwd)) {
					ToastUtils.toast(mContext,
							E_string.efun_pd_toast_empty_password);
					return;
				}
				if (TextUtils.isEmpty(newPwd)) {
					ToastUtils.toast(mContext,
							E_string.efun_pd_toast_empty_password);
					return;
				}
				if (!TextToolUtils.isStringLimited(newPwd, 6, 16)) {
					ToastUtils.toast(mContext,
							E_string.efun_pd_toast_error_password);
					return;
				}
				if (TextUtils.isEmpty(confirmPwd)) {
					ToastUtils.toast(mContext,
							E_string.efun_pd_toast_empty_confirm_password);
					return;
				}
				if (!TextToolUtils.isStringLimited(confirmPwd, 6, 16)) {
					ToastUtils.toast(mContext,
							E_string.efun_pd_toast_error_password);
					return;
				}
				if (!newPwd.equals(confirmPwd)) {
					ToastUtils.toast(mContext,
							E_string.efun_pd_toast_empty_different_password);
					return;
				}
				requestWithDialog(needRequestDataBean(),
						getString(E_string.efun_pd_loading_msg_commend));
			}
		});

		// 舊密码明文与隐藏设置
		mOldEye.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (isOldPWHidden) {
					// 设置EditText文本为可见的
					mOldEye.setSelected(true);
					oldPwdEdit
							.setTransformationMethod(HideReturnsTransformationMethod
									.getInstance());
				} else {
					// 设置EditText文本为隐藏的
					mOldEye.setSelected(false);
					oldPwdEdit
							.setTransformationMethod(PasswordTransformationMethod
									.getInstance());
				}
				isOldPWHidden = !isOldPWHidden;
				oldPwdEdit.postInvalidate();
				// 切换后将EditText光标置于末尾
				CharSequence charSequence = oldPwdEdit.getText();
				if (charSequence instanceof Spannable) {
					Spannable spanText = (Spannable) charSequence;
					Selection.setSelection(spanText, charSequence.length());
				}

			}
		});
		// 新密码明文与隐藏设置
		mNewEye.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (isNewPWHidden) {
					// 设置EditText文本为可见的
					mNewEye.setSelected(true);
					newPwdEdit
					.setTransformationMethod(HideReturnsTransformationMethod
							.getInstance());
				} else {
					// 设置EditText文本为隐藏的
					mNewEye.setSelected(false);
					newPwdEdit
					.setTransformationMethod(PasswordTransformationMethod
							.getInstance());
				}
				isNewPWHidden = !isNewPWHidden;
				newPwdEdit.postInvalidate();
				// 切换后将EditText光标置于末尾
				CharSequence charSequence = newPwdEdit.getText();
				if (charSequence instanceof Spannable) {
					Spannable spanText = (Spannable) charSequence;
					Selection.setSelection(spanText, charSequence.length());
				}
				
			}
		});
		// 確認密码明文与隐藏设置
		mConfirmEye.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (isConfirmPWHidden) {
					// 设置EditText文本为可见的
					mConfirmEye.setSelected(true);
					confirmEdit
					.setTransformationMethod(HideReturnsTransformationMethod
							.getInstance());
				} else {
					// 设置EditText文本为隐藏的
					mConfirmEye.setSelected(false);
					confirmEdit
					.setTransformationMethod(PasswordTransformationMethod
							.getInstance());
				}
				isConfirmPWHidden = !isConfirmPWHidden;
				confirmEdit.postInvalidate();
				// 切换后将EditText光标置于末尾
				CharSequence charSequence = confirmEdit.getText();
				if (charSequence instanceof Spannable) {
					Spannable spanText = (Spannable) charSequence;
					Selection.setSelection(spanText, charSequence.length());
				}
				
			}
		});

	}

	@Override
	public ViewGroup[] needShowLoading() {
		return null;
	}

	@Override
	public void onSuccess(int requestType, BaseResponseBean baseResponse) {
		super.onSuccess(requestType, baseResponse);
		if (requestType == IPlatformRequest.REQ_ACCOUNT_RESET_PWD) {
			AccountResponse response = (AccountResponse) baseResponse;
			ResultBean result = response.getResultBean();
			ToastUtils.toast(mContext, result.getMessage());
			if (result.getCode().equals(HttpParam.RESULT_1000)) {
//				finish();
				UserUtils.logout(mContext);
				//提醒跳转到登录页面进行登录
				ViewUtils.createToastCommon(mContext, getString(E_string.efun_pd_reset_person_change_pwd_desc), null, null, ToastType.COMMONTYPE, new OnEfunItemClickListener() {
					
					@Override
					public void onItemClick(int position) {
						// TODO Auto-generated method stub
						//跳轉到登錄頁面
						UserUtils.needLoginInTag(mContext, RequestCode.REQ_LOGIN_AFTER_RET_PWD, ResultCode.RST_LOGIN_AFTER_RET_PWD, new OnLoginFinishListener() {
							
							@Override
							public void loginCompleted(boolean completed) {
								// TODO Auto-generated method stub
								setResult(ResultCode.RST_LOGIN_AFTER_RET_PWD);
								finish();
							}
						});
						
						
					}
				}, null).show();
			}
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == RequestCode.REQ_LOGIN_AFTER_RET_PWD){
			setResult(ResultCode.RST_LOGIN_AFTER_RET_PWD);
			finish();
		}
	}

	@Override
	public int LayoutId() {
		return E_layout.efun_pd_persion_reset_password;
	}

	@Override
	public void initTitle(TitleView titleView) {
		titleView.setTitleRightStatus(View.GONE);
		titleView.setTitleCenterRes(E_string.efun_pd_reset_password, false);
	}
}
