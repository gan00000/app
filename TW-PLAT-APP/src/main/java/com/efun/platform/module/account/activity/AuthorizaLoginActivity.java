package com.efun.platform.module.account.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Html;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.efun.core.db.EfunDatabase;
import com.efun.core.tools.EfunResourceUtil;
import com.efun.core.tools.EfunStringUtil;
import com.efun.google.GoogleSignIn;
import com.efun.google.GoogleSignIn.GoogleSignInCallBack;
import com.efun.platform.AndroidScape.E_drawable;
import com.efun.platform.AndroidScape.E_id;
import com.efun.platform.AndroidScape.E_layout;
import com.efun.platform.AndroidScape.E_string;
import com.efun.platform.IPlatApplication;
import com.efun.platform.http.dao.impl.IPlatformRequest;
import com.efun.platform.http.request.bean.AccountBindPhoneSendVcodeRequest;
import com.efun.platform.http.request.bean.AccountLoginRequest;
import com.efun.platform.http.request.bean.AccountRegisterRequest;
import com.efun.platform.http.request.bean.BaseRequestBean;
import com.efun.platform.http.response.bean.AccountBindPhoneResponse;
import com.efun.platform.http.response.bean.AccountResponse;
import com.efun.platform.http.response.bean.BaseResponseBean;
import com.efun.platform.module.account.bean.BindResultBean;
import com.efun.platform.module.account.bean.PhoneAreaBean;
import com.efun.platform.module.account.bean.ResultBean;
import com.efun.platform.module.account.bean.User;
import com.efun.platform.module.account.util.BahaLoginHelper;
import com.efun.platform.module.account.util.BahaLoginHelper.BahaLoginCallback;
import com.efun.platform.module.base.FixedActivity;
import com.efun.platform.module.utils.AnimUtils;
import com.efun.platform.module.utils.AppUtils;
import com.efun.platform.module.utils.IntentUtils;
import com.efun.platform.module.utils.ProcessDatasUtils;
import com.efun.platform.module.utils.TextToolUtils;
import com.efun.platform.module.utils.ToastUtils;
import com.efun.platform.module.utils.TrackingGoogleUtils;
import com.efun.platform.module.utils.ViewUtils;
import com.efun.platform.module.utils.ViewUtils.OnDialogSelect;
import com.efun.platform.utils.Const.HttpParam;
import com.efun.platform.utils.Const.Key;
import com.efun.platform.utils.Const.LoginPlatform;
import com.efun.platform.utils.Const.RequestCode;
import com.efun.platform.utils.Const.ResultCode;
import com.efun.platform.utils.Const.StartAPPParams;
import com.efun.platform.utils.StoreUtil.IPlatDatabaseValues;
import com.efun.platform.utils.StoreUtil;
import com.efun.platform.widget.TitleView;

/**
 * 授权登陆
 * 
 * @author itxuxxey
 * 
 */
public class AuthorizaLoginActivity extends FixedActivity {
	private boolean comeFromTag;
	private int resultCode;
	private GoogleSignIn googleSignIn;
	private CountDownTimer vcodePhone;

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (googleSignIn != null) {
			googleSignIn.handleActivityDestroy(this);
		}
		EfunDatabase.saveSimpleInfo(this, IPlatDatabaseValues.PLATFORM_FILE, IPlatDatabaseValues.DATA_COUNT_TIME, System.currentTimeMillis());
		if(vcodePhone != null){
			vcodePhone.cancel();
		}
	}

	public void GoogleLogin() {
		googleSignIn = new GoogleSignIn(this);
		googleSignIn.startSignIn(new GoogleSignInCallBack() {
			
			@Override
			public void success(String id, String mFullName, String mEmail) {
				// TODO Auto-generated method stub
				if (!TextUtils.isEmpty(id)) {
					IPlatApplication.get().setPfLType(LoginPlatform.GOOGLE);
					IPlatApplication.get().setPfAName("");
					IPlatApplication.get().setPfPCode("");
					requestLogin(id, LoginPlatform.GOOGLE);
				}
			}
			
			@Override
			public void failure() {
				// TODO Auto-generated method stub
				//登录异常
			}
		});
	}

	@Override
	public void onBackPressed() {
		if(IPlatApplication.get().getUser() != null){//切换账号的返回
			Intent it = new Intent();
			setResult(StartAPPParams.AU_CHANGERETURN, it);
			finish();
		}else{//未登录的返回
			Intent it = new Intent();
			setResult(StartAPPParams.AU_UNLOGINRETURN, it);
			finish();
		}
	}

	private LinearLayout mLoginLayout;
	private ImageView mFacebookLayout, mGoogleLayout,
			mBahamutLayout;
	private EditText mUsername, mPassword;
	private TextView mForget;
	private String name, passowrd;
	private String loginType;
	private String thirdId;
	private String apps;
//	private TextView loginBtn, registerBtn;
	private LinearLayout loginBtn, registerBtn;
	private LinearLayout mLoginBody, mRegisterBody,mChoiceAreaLayout;
	private ImageView mLoginPW;
	private TextView mPhoneArea;

	private TextView mRegisterBtn, mServiceClause,mPhoneCodeBtn;
	private EditText mAccount, mRegisterPassword, mRegisterPhone,mRegisterVcode;
	private CheckBox mCheckBox;
	private String loginName, loginPwd, registerPhone,registerVcode;
	private ImageView mRegisterPW,mReturn;
//	private mRegisterPWConfirm,
	private String[] mValues;
	private ArrayList<PhoneAreaBean> mPhoneAreas;
	private String key, pattern;// 區號和手機號碼判斷的正則表達式
	private String phoneStr;

	private boolean isLoginPWHidden = true;
	private boolean isRPWHidden = true;
//	private boolean isRPWConfirmHidden = true;

	@Override
	public boolean needRequestData() {
		return false;
	}

	@Override
	public BaseRequestBean[] needRequestDataBean() {
		AccountLoginRequest loginRequest = new AccountLoginRequest(mContext,
				name, passowrd);
		loginRequest.setLoginType(loginType);
		loginRequest.setPfArea(HttpParam.PLATFORM_AREA);
		loginRequest.setDevice(HttpParam.PHONE);
		loginRequest.setThirdId(thirdId);
		loginRequest.setApps(apps);
		loginRequest.setReqType(IPlatformRequest.REQ_ACCOUNT_LOGIN);
		return new BaseRequestBean[] { loginRequest };
	}

	@Override
	public void init(Bundle bundle) {
		if (bundle != null) {
			comeFromTag = bundle.getBoolean(Key.BOOLEAN_KEY);
			resultCode = bundle.getInt(Key.INTEGER_KEY);
		}

		mLoginLayout = (LinearLayout) findViewById(E_id.contaier_linear_1);
//		mFacebookLayout = (LinearLayout) findViewById(E_id.contaier_linear_2);
//		mGoogleLayout = (LinearLayout) findViewById(E_id.contaier_linear_3);
//		mBahamutLayout = (LinearLayout) findViewById(E_id.contaier_linear_4);
		mFacebookLayout = (ImageView) findViewById(E_id.login_fb);
		mGoogleLayout = (ImageView) findViewById(E_id.login_google);
		mBahamutLayout = (ImageView) findViewById(E_id.login_bh);
		mUsername = (EditText) findViewById(E_id.edit_1);
		mPassword = (EditText) findViewById(E_id.edit_2);
		mForget = (TextView) findViewById(E_id.text_1);
//		mRegister = (TextView) findViewById(E_id.text_2);
		loginBtn = (LinearLayout) findViewById(E_id.login_login);
		registerBtn = (LinearLayout) findViewById(E_id.login_register);
		mLoginBody = (LinearLayout) findViewById(E_id.login_login_body);
		mRegisterBody = (LinearLayout) findViewById(E_id.login_login_register);

		mLoginPW = (ImageView) findViewById(E_id.login_login_pw_eye);

		loginBtn.setSelected(true);
		registerBtn.setSelected(false);

		// 注册
		mAccount = (EditText) findViewById(E_id.edit_register_1);
		mRegisterPassword = (EditText) findViewById(E_id.edit_register_2);
//		mConfirm = (EditText) findViewById(E_id.edit_register_3);
		mRegisterPhone = (EditText) findViewById(E_id.edit_register_3);
		mRegisterVcode = (EditText) findViewById(E_id.edit_register_4);
		mRegisterBtn = (TextView) findViewById(E_id.text_register_1);
		mServiceClause = (TextView) findViewById(E_id.txt_server_clause);
		mCheckBox = (CheckBox) findViewById(E_id.cb_server_clause);
		mRegisterPW = (ImageView) findViewById(E_id.login_register_eye_1);
		mChoiceAreaLayout = (LinearLayout) findViewById(E_id.phone_area);
		mPhoneArea = (TextView) findViewById(E_id.phone_area_content);
		mPhoneCodeBtn = (TextView) findViewById(E_id.phone_send_code);
		
//		mRegisterPWConfirm = (ImageView) findViewById(E_id.login_register_eye_2);
		
		mReturn = (ImageView) findViewById(E_id.authoriza_return);
//		mForget.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG ); //下划线
		mForget.getPaint().setAntiAlias(true);//抗锯齿
		mForget.setText(Html.fromHtml(EfunResourceUtil.findStringByName(mContext, "efun_pd_login_forget")));
		
		/**
		 * 返回
		 */
		mReturn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(IPlatApplication.get().getUser() != null){//切换账号的返回
					Intent it = new Intent();
					setResult(StartAPPParams.AU_CHANGERETURN, it);
					finish();
				}else{//未登录的返回
					Intent it = new Intent();
					setResult(StartAPPParams.AU_UNLOGINRETURN, it);
					finish();
				}
			}
		});
		// 登入
		loginBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				loginBtn.setSelected(true);
				registerBtn.setSelected(false);
				AnimUtils.actAnimation_back(mContext, mRegisterBody, mLoginBody);
//				mRegisterBody.setVisibility(View.GONE);
//				mLoginBody.setVisibility(View.VISIBLE);

			}
		});
		// 注册
		registerBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				loginBtn.setSelected(false);
				registerBtn.setSelected(true);
				AnimUtils.actAnimation_go(mContext, mLoginBody, mRegisterBody);
//				mLoginBody.setVisibility(View.GONE);
//				mRegisterBody.setVisibility(View.VISIBLE);
			}
		});
		// 登录时的密码明文与隐藏设置
		mLoginPW.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (isLoginPWHidden) {
					// 设置EditText文本为可见的
					mLoginPW.setSelected(true);
					mPassword
							.setTransformationMethod(HideReturnsTransformationMethod
									.getInstance());
				} else {
					mLoginPW.setSelected(false);
					// 设置EditText文本为隐藏的
					mPassword
							.setTransformationMethod(PasswordTransformationMethod
									.getInstance());
				}
				isLoginPWHidden = !isLoginPWHidden;
				mPassword.postInvalidate();
				// 切换后将EditText光标置于末尾
				CharSequence charSequence = mPassword.getText();
				if (charSequence instanceof Spannable) {
					Spannable spanText = (Spannable) charSequence;
					Selection.setSelection(spanText, charSequence.length());
				}

			}
		});
		// 登录按钮事件
		mLoginLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				name = mUsername.getText().toString();
				passowrd = mPassword.getText().toString();
				if (TextUtils.isEmpty(name)) {
					ToastUtils.toast(mContext,
							E_string.efun_pd_toast_empty_account);
					return;
				}
				if (TextUtils.isEmpty(passowrd)) {
					ToastUtils.toast(mContext,
							E_string.efun_pd_toast_empty_password);
					return;
				}
				loginType = LoginPlatform.EFUN;
				thirdId = "";
				apps = "";
				requestWithDialog(needRequestDataBean(),
						getString(E_string.efun_pd_loading_msg_login));
			}
		});
		mForget.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				IntentUtils.startActivity(mContext, ForgetPwdActivity.class);
			}
		});
		// mRegister.setOnClickListener(new OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// IntentUtils.startActivityForResult(mContext,
		// RegisterActivity.class,RequestCode.REQ_REQUEST);
		// }
		// });

		mFacebookLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				IntentUtils.startActivityForResult(mContext,
						FacebookActivity.class,
						RequestCode.REQ_ACCOUNT_FACEBOOK_LOGIN);
			}
		});
		mGoogleLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				GoogleLogin();
//				((AuthorizaLoginActivity) mContext).GoogleLogin(new OnLoginListener() {
//					@Override
//					public void loginCompleted(String third_uid) {
//						IPlatApplication.get().setPfLType(LoginPlatform.GOOGLE);
//						IPlatApplication.get().setPfAName("");
//						IPlatApplication.get().setPfPCode("");
//						requestLogin(third_uid, LoginPlatform.GOOGLE);
//					}
//				});
			}
		});
		mBahamutLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				BahaLoginHelper helper = new BahaLoginHelper(mContext);
				helper.bahaLogin(getString(E_string.bahamut_clinet_id),
						new BahaLoginCallback() {
							@Override
							public void onBahaLoginFinished(String uid) {
								IPlatApplication.get().setPfLType(LoginPlatform.BAHAMUT);
								IPlatApplication.get().setPfAName("");
								IPlatApplication.get().setPfPCode("");
								requestLogin(uid, LoginPlatform.BAHAMUT);
							}
						});
			}
		});

		// 注册时的密码明文与隐藏设置
		mRegisterPW.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (isRPWHidden) {
					// 设置EditText文本为可见的
					mRegisterPW.setSelected(true);
					mRegisterPassword
							.setTransformationMethod(HideReturnsTransformationMethod
									.getInstance());
				} else {
					// 设置EditText文本为隐藏的
					mRegisterPW.setSelected(false);
					mRegisterPassword
							.setTransformationMethod(PasswordTransformationMethod
									.getInstance());
				}
				isRPWHidden = !isRPWHidden;
				mRegisterPassword.postInvalidate();
				// 切换后将EditText光标置于末尾
				CharSequence charSequence = mRegisterPassword.getText();
				if (charSequence instanceof Spannable) {
					Spannable spanText = (Spannable) charSequence;
					Selection.setSelection(spanText, charSequence.length());
				}

			}
		});
		
		//手機地區
				mChoiceAreaLayout.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if (mValues != null) {
							ViewUtils.paramListDialog(mValues, mContext, new OnDialogSelect() {

								@Override
								public void onSelect(int postion) {
									// TODO Auto-generated method stub
									TrackingGoogleUtils.track(TrackingGoogleUtils.ACTION_PHONE_AREA, mValues[postion]);
									key = mPhoneAreas.get(postion).getValue();
									mPhoneArea.setText(mValues[postion]);
									pattern = mPhoneAreas.get(postion).getPattern();
								}
							});
						}
					}
				});
				
				//發送驗證碼
				mPhoneCodeBtn.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						if (EfunStringUtil.isEmpty(key)) {
							ToastUtils.toast(mContext, E_string.efun_pd_toast_empty_phone_area);
							return;
						}
						phoneStr = mRegisterPhone.getText().toString();
						if (TextUtils.isEmpty(phoneStr)) {
							ToastUtils.toast(mContext,
									E_string.efun_pd_toast_empty_phone);
							return;
						}
						if (!phoneStr.matches(pattern)) {
							ToastUtils.toast(mContext, E_string.efun_pd_toast_error_new_phone_type);
							return;
						}
						vcodePhone = settingCountTime(mPhoneCodeBtn,true);
						if(vcodePhone != null){
							vcodePhone.start();
							mPhoneCodeBtn.setClickable(false);
						}
						requestWithDialog(createSendPhoneVcode(),
								getString(E_string.efun_pd_bind_phone_intro));
					}
				});

//		// 注册时的确认密码明文与隐藏设置
//		mRegisterPWConfirm.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//				if (isRPWConfirmHidden) {
//					// 设置EditText文本为可见的
//					mRegisterPWConfirm.setSelected(true);
//					mConfirm
//							.setTransformationMethod(HideReturnsTransformationMethod
//									.getInstance());
//				} else {
//					// 设置EditText文本为隐藏的
//					mRegisterPWConfirm.setSelected(false);
//					mConfirm
//							.setTransformationMethod(PasswordTransformationMethod
//									.getInstance());
//				}
//				isRPWConfirmHidden = !isRPWConfirmHidden;
//				mConfirm.postInvalidate();
//				// 切换后将EditText光标置于末尾
//				CharSequence charSequence = mConfirm.getText();
//				if (charSequence instanceof Spannable) {
//					Spannable spanText = (Spannable) charSequence;
//					Selection.setSelection(spanText, charSequence.length());
//				}
//
//			}
//		});

		mRegisterBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				loginName = mAccount.getText().toString();
				loginPwd = mRegisterPassword.getText().toString();
//				confirmPwd = mConfirm.getText().toString();
				registerPhone = mRegisterPhone.getText().toString();
				registerVcode = mRegisterVcode.getText().toString();
				if (TextUtils.isEmpty(loginName)) {
					ToastUtils.toast(mContext,
							E_string.efun_pd_toast_empty_account);
					return;
				}
				if (!TextToolUtils.isStringLimited(loginName)) {
					ToastUtils.toast(mContext,
							E_string.efun_pd_toast_error_account_first_char);
					return;
				}
				if (!TextToolUtils.isStringLimited(loginName, 6, 18)) {
					ToastUtils.toast(mContext,
							E_string.efun_pd_toast_error_account);
					return;
				}
				if (TextUtils.isEmpty(loginPwd)) {
					ToastUtils.toast(mContext,
							E_string.efun_pd_toast_empty_password);
					return;
				}
				if (!TextToolUtils.isStringLimited(loginPwd, 6, 16)) {
					ToastUtils.toast(mContext,
							E_string.efun_pd_toast_error_password);
					return;
				}
				
				if (EfunStringUtil.isEmpty(registerPhone) && EfunStringUtil.isEmpty(registerVcode)) {
					registerPhone = "";
					registerVcode = "";
				}else if(!EfunStringUtil.isEmpty(registerPhone)){
					if (!registerPhone.matches(pattern)) {
						ToastUtils.toast(mContext, E_string.efun_pd_toast_error_phone_type);
						return;
					}
					if (EfunStringUtil.isEmpty(registerVcode)) {
						ToastUtils.toast(mContext,
								E_string.efun_pd_toast_empty_vcode);
						return;
					}
					registerPhone = key + "-" + registerPhone;
				}else{
					ToastUtils.toast(mContext,
							E_string.efun_pd_toast_empty_phone);
					return;
				}
//				if (TextUtils.isEmpty(confirmPwd)) {
//					ToastUtils.toast(mContext,
//							E_string.efun_pd_toast_empty_confirm_password);
//					return;
//				}
//				if (!confirmPwd.equals(loginPwd)) {
//					ToastUtils.toast(mContext,
//							E_string.efun_pd_toast_empty_different_password);
//					return;
//				}
				if (!mCheckBox.isChecked()) {
					ToastUtils.toast(mContext,
							E_string.efun_pd_check_service_clause);
					return;
				}
				loginType = LoginPlatform.EFUN;
				thirdId = "";
				apps = "";
				requestWithDialog(requestRegister(),
						getString(E_string.efun_pd_loading_msg_commend));
			}
		});
		mServiceClause.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(mContext, ServiceClauseActivity.class));
			}
		});

		// String[] oldUserInfo = Store.getHistoryLogin(mContext, new
		// String[]{"username","password"});
		String[] keys = new String[] { "username", "password" };
		String[] oldUserInfo = StoreUtil.getValues(mContext,
				StoreUtil.Param_file_name, keys);
		if (!TextUtils.isEmpty(oldUserInfo[0])) {
			mUsername.setText(oldUserInfo[0]);
			mPassword.setText(oldUserInfo[1]);
		}
		
		mPhoneAreas = IPlatApplication.get().getmPhoneAreas();
		mValues = ProcessDatasUtils.getAllPhoneAreaCode();
		if(mPhoneAreas != null && mValues != null){
			mPhoneArea.setText(mValues[0]);
			key = mPhoneAreas.get(0).getValue();
			pattern = mPhoneAreas.get(0).getPattern();
		}
		vcodePhone = settingCountTime(mPhoneCodeBtn,false);
		if(vcodePhone != null){
			vcodePhone.start();
			mPhoneCodeBtn.setClickable(false);
		}
	}

	private void requestLogin(String uid, String thirdPlat) {
		requestLogin(uid, thirdPlat, "");
	}

	private void requestLogin(String uid, String thirdPlat, String facebookApps) {
		name = uid;
		loginType = thirdPlat;
		thirdId = uid;
		apps = facebookApps;
		requestWithDialog(needRequestDataBean(),
				getString(E_string.efun_pd_loading_msg_login));
	}

	private BaseRequestBean[] requestRegister() {
		AccountRegisterRequest mRegisterRequest = new AccountRegisterRequest(
				mContext, loginName, loginPwd, "");
		mRegisterRequest.setArea(HttpParam.PLATFORM_AREA);
		mRegisterRequest.setDevice(HttpParam.PHONE);
		mRegisterRequest.setPhone(registerPhone);
		mRegisterRequest.setPackageVersion(AppUtils.getAppVersionName(mContext));
		mRegisterRequest.setVcode(registerVcode);
		mRegisterRequest.setVersion(HttpParam.PLATFORM);
		mRegisterRequest.setReqType(IPlatformRequest.REQ_ACCOUNT_REGISTER);
		return new BaseRequestBean[] { mRegisterRequest };
	}

	@Override
	public void onSuccess(int requestType, BaseResponseBean baseResponse) {
		super.onSuccess(requestType, baseResponse);
		if (requestType == IPlatformRequest.REQ_ACCOUNT_LOGIN) {
			AccountResponse response = (AccountResponse) baseResponse;
			success(response);
		}

		if (requestType == IPlatformRequest.REQ_ACCOUNT_REGISTER) {
			AccountResponse response = (AccountResponse) baseResponse;
			ResultBean result = response.getResultBean();
			if(vcodePhone != null){
				vcodePhone.cancel();
			}
			if (result.getCode().equals(HttpParam.RESULT_1000)) {
				IPlatApplication.get().setPfLType(loginType);
				IPlatApplication.get().setPfAName(loginName);
				IPlatApplication.get().setPfPCode(loginPwd);
				IPlatApplication.get().setUser((User) result);
				for (int i = 0; i < Integer.MAX_VALUE; i++) {
					if (IPlatApplication.get().getUser() != null) {
//						finish();
//						((OnLoginFinishListener) IPlatApplication.get()
//								.getOnEfunListener()).loginCompleted(true);
						Intent it = new Intent();
						setResult(StartAPPParams.AU_LOGIN_SUCCESS, it);
						finish();
						break;
					}
				}
			} else {
				ToastUtils.toast(mContext, result.getMessage());
			}
		}
		
		if (requestType == IPlatformRequest.REQ_ACCOUNT_BIND_PHONE_SEND_VCODE) {
			AccountBindPhoneResponse response = (AccountBindPhoneResponse) baseResponse;
			BindResultBean result = response.getResultBean();
			if(result.getCode().equals(HttpParam.RESULT_1000)){
				if(vcodePhone != null){
					vcodePhone.start();
					mPhoneCodeBtn.setClickable(false);
				}
			}
			ToastUtils.toast(mContext, result.getMessage());
	}

	}

	private void success(AccountResponse response) {
		ResultBean result = response.getResultBean();
		if (result.getCode().equals(HttpParam.RESULT_1000)) {
			Log.i("yang", "success-->loginType:"+loginType);
			IPlatApplication.get().setPfLType(loginType);
			IPlatApplication.get().setPfAName(name);
			IPlatApplication.get().setPfPCode(passowrd);
			IPlatApplication.get().setUser((User) result);
			for (int i = 0; i < Integer.MAX_VALUE; i++) {
				if (IPlatApplication.get().getUser() != null) {//登录成功的回到授权页面
					Intent it = new Intent();
					setResult(StartAPPParams.AU_LOGIN_SUCCESS, it);
					finish();
					break;
				}
			}
		}
		ToastUtils.toast(mContext, result.getMessage());
	}

	private BaseRequestBean[] createSendPhoneVcode(){
		AccountBindPhoneSendVcodeRequest sendVCodeRequest = new AccountBindPhoneSendVcodeRequest(
				phoneStr,
				HttpParam.APP, HttpParam.PLATFORM_AREA, key,
				HttpParam.PLATFORM, AppUtils.getAppVersionName(mContext),
				HttpParam.LANGUAGE);
		if (IPlatApplication.get().getUser() != null) {
			sendVCodeRequest.setUid(IPlatApplication.get().getUser().getUid());
			sendVCodeRequest
					.setSign(IPlatApplication.get().getUser().getSign());
			sendVCodeRequest.setTimestamp(IPlatApplication.get().getUser()
					.getTimestamp());
		}else{//没有用户的时候
			sendVCodeRequest.setUid("");
			sendVCodeRequest
					.setSign("");
			sendVCodeRequest.setTimestamp("");
		}
		sendVCodeRequest
				.setReqType(IPlatformRequest.REQ_ACCOUNT_BIND_PHONE_SEND_VCODE);
		return new BaseRequestBean[] { sendVCodeRequest };
	}
	
	@Override
	public int LayoutId() {
		return E_layout.efun_pd_authoriza_login;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == ResultCode.RST_ACCOUNT_FACEBOOK_LOGIN
				&& requestCode == RequestCode.REQ_ACCOUNT_FACEBOOK_LOGIN) {
			IPlatApplication.get().setPfLType(LoginPlatform.FACEBOOK);
			IPlatApplication.get().setPfAName("");
			IPlatApplication.get().setPfPCode("");
			String loginid = data.getStringExtra(Key.STRING_KEY);
			String facebookApps = data.getStringExtra(Key.APPS_KEY);
			requestLogin(loginid, LoginPlatform.FACEBOOK, facebookApps);
		}
		// else if(resultCode == ResultCode.RST_REQUEST && requestCode ==
		// RequestCode.REQ_REQUEST){
		// ResultBean result = (ResultBean)
		// data.getSerializableExtra(Const.BEAN_KEY);
		// IPlatApplication.get().setUser((User)result);
		// for (int i = 0; i < Integer.MAX_VALUE; i++) {
		// if(IPlatApplication.get().getUser()!=null){
		// finish();
		// ((OnLoginFinishListener)IPlatApplication.get().getOnEfunListener()).loginCompleted(true);
		// break;
		// }
		// }
		// }
		if (googleSignIn != null) {
			googleSignIn.handleActivityResult(this, requestCode, resultCode,
					data);
		}
	}

	@Override
	public void initTitle(TitleView titleView) {
//		titleView.setTitleLeftRes(E_drawable.efun_pd_back_white_selector);
//		titleView.setTitleRightStatus(View.GONE);
////		titleView.setTitleBarBackgroundRes(getResources().getColor(
////				E_color.e_transparent));
//		titleView.setTitleCenterRes(E_string.efun_pd_login, false);
//		titleView.setTitleCenterTextColor(Color.WHITE);
//		titleView.setTitleBottomLineStatus(View.GONE);
//		titleView.setTitleBarBackgroundRes(E_color.e_4f83e3);
		titleView.setVisibility(View.GONE);
	}

	@Override
	public ViewGroup[] needShowLoading() {
		// TODO Auto-generated method stub
		return null;
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
			controlBtn.setBackgroundResource(E_drawable.efun_pd_2eade4_rectangle_bg);
			controlBtn.setText(EfunResourceUtil.findStringByName(this, "efun_pd_get_vcode_again"));
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
				EfunDatabase.saveSimpleInfo(AuthorizaLoginActivity.this, IPlatDatabaseValues.PLATFORM_FILE, IPlatDatabaseValues.DATA_REMAIN_TIME, millisUntilFinished);
				controlBtn.setBackgroundResource(E_drawable.efun_pd_a6a6a6_rectangle_bg);
				controlBtn.setText(millisUntilFinished/1000+EfunResourceUtil.findStringByName(AuthorizaLoginActivity.this, "efun_pd_get_vcode_again_timedown"));
			}
			
			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				EfunDatabase.saveSimpleInfo(AuthorizaLoginActivity.this, IPlatDatabaseValues.PLATFORM_FILE, IPlatDatabaseValues.DATA_REMAIN_TIME, 0);
				controlBtn.setBackgroundResource(E_drawable.efun_pd_2eade4_rectangle_bg);
				controlBtn.setText(EfunResourceUtil.findStringByName(AuthorizaLoginActivity.this, "efun_pd_get_vcode_again"));
				if(vcodePhone != null){
					vcodePhone = null;
				}
				mPhoneCodeBtn.setClickable(true);
			}
		};
		return downTime;
		
	}

}
