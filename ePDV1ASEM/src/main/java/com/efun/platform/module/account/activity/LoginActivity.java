package com.efun.platform.module.account.activity;

import android.content.Intent;
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
import com.efun.platform.AndroidScape.E_color;
import com.efun.platform.AndroidScape.E_drawable;
import com.efun.platform.AndroidScape.E_id;
import com.efun.platform.AndroidScape.E_layout;
import com.efun.platform.AndroidScape.E_string;
import com.efun.platform.IPlatApplication;
import com.efun.platform.http.dao.impl.IPlatformRequest;
import com.efun.platform.http.request.bean.AccountLoginRequest;
import com.efun.platform.http.request.bean.AccountRegisterRequest;
import com.efun.platform.http.request.bean.AccountThirdLoginRequest;
import com.efun.platform.http.request.bean.BaseRequestBean;
import com.efun.platform.http.response.bean.AccountResponse;
import com.efun.platform.http.response.bean.BaseResponseBean;
import com.efun.platform.module.account.bean.ResultBean;
import com.efun.platform.module.account.bean.User;
import com.efun.platform.module.base.FixedActivity;
import com.efun.platform.module.base.impl.OnLoginFinishListener;
import com.efun.platform.module.utils.AnimUtils;
import com.efun.platform.module.utils.AppUtils;
import com.efun.platform.module.utils.IntentUtils;
import com.efun.platform.module.utils.TextToolUtils;
import com.efun.platform.module.utils.ToastUtils;
import com.efun.platform.module.utils.TwitterLoginUtils;
import com.efun.platform.utils.Const.HttpParam;
import com.efun.platform.utils.Const.Key;
import com.efun.platform.utils.Const.LoginPlatform;
import com.efun.platform.utils.Const.RequestCode;
import com.efun.platform.utils.Const.ResultCode;
import com.efun.platform.utils.StoreUtil;
import com.efun.platform.utils.StoreUtil.IPlatDatabaseValues;
import com.efun.platform.widget.TitleView;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;

/**
 * 登陆
 * 
 * @author itxuxxey
 * 
 */
public class LoginActivity extends FixedActivity {
	private boolean comeFromTag;
	private int resultCode;
//	private GoogleSignIn googleSignIn;
//	private CountDownTimer vcodePhone;

	@Override
	public void onClickLeft() {
		if (comeFromTag) {
			setResult(resultCode);
		}
		super.onClickLeft();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
//		if (googleSignIn != null) {
//			googleSignIn.handleActivityDestroy(this);
//		}
//		EfunDatabase.saveSimpleInfo(this, IPlatDatabaseValues.PLATFORM_FILE, IPlatDatabaseValues.DATA_COUNT_TIME, System.currentTimeMillis());
//		if(vcodePhone != null){
//			vcodePhone.cancel();
//		}
	}

	public void GoogleLogin() {
//		googleSignIn.startSignIn(new GoogleSignInCallBack() {
//			
//			@Override
//			public void success(String id, String mFullName, String mEmail) {
//				// TODO Auto-generated method stub
//				if (!TextUtils.isEmpty(id)) {
//					IPlatApplication.get().setPfLType(LoginPlatform.GOOGLE);
//					IPlatApplication.get().setPfAName("");
//					IPlatApplication.get().setPfPCode("");
//					requestLogin(id, LoginPlatform.GOOGLE);
//				}
//			}
//			
//			@Override
//			public void failure() {
//				// TODO Auto-generated method stub
//				//登录异常
//			}
//		});
//		.startSignIn(onLoginListener)
	}

	@Override
	public void onBackPressed() {
		onClickLeft();
	}

	private LinearLayout mLoginLayout;
	private ImageView mFacebookLayout,mTwitterLayout; 
//	mGoogleLayout, mBahamutLayout;
	private EditText mUsername, mPassword;
	private TextView mForget;
	private String name, passowrd;
	private String loginType;
	private String thirdId;
	private String apps;
//	private TextView loginBtn, registerBtn;
	private LinearLayout loginBtn, registerBtn;
	private LinearLayout mLoginBody, mRegisterBody;
	private ImageView mLoginPW;

	private TextView mRegisterBtn, mServiceClause;
	private EditText mAccount, mRegisterPassword,mRegisterEmail;
	private CheckBox mCheckBox;
	private String loginName, loginPwd,registerEmail;
	private ImageView mRegisterPW;
//	private ImageView mRegisterPWConfirm;

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
//		googleSignIn = new GoogleSignIn(this);
		if (bundle != null) {
			comeFromTag = bundle.getBoolean(Key.BOOLEAN_KEY);
			resultCode = bundle.getInt(Key.INTEGER_KEY);
		}

		mLoginLayout = (LinearLayout) findViewById(E_id.contaier_linear_1);
//		mFacebookLayout = (LinearLayout) findViewById(E_id.contaier_linear_2);
//		mGoogleLayout = (LinearLayout) findViewById(E_id.contaier_linear_3);
//		mBahamutLayout = (LinearLayout) findViewById(E_id.contaier_linear_4);
		mFacebookLayout = (ImageView) findViewById(E_id.login_fb);
		mTwitterLayout = (ImageView) findViewById(E_id.login_twitter);
//		mGoogleLayout = (ImageView) findViewById(E_id.login_google);
//		mBahamutLayout = (ImageView) findViewById(E_id.login_bh);
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
		mRegisterBtn = (TextView) findViewById(E_id.text_register_1);
		mServiceClause = (TextView) findViewById(E_id.txt_server_clause);
		mCheckBox = (CheckBox) findViewById(E_id.cb_server_clause);
		mRegisterPW = (ImageView) findViewById(E_id.login_register_eye_1);
		mRegisterEmail = (EditText) findViewById(E_id.edit_register_5);
//		mRegisterPWConfirm = (ImageView) findViewById(E_id.login_register_eye_2);
//		mForget.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG ); //下划线
		mForget.getPaint().setAntiAlias(true);//抗锯齿
		mForget.setText(Html.fromHtml(EfunResourceUtil.findStringByName(mContext, "efun_pd_login_forget")));
		
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
				requestWithDialog(needRequestDataBean(),getString(E_string.efun_pd_loading_msg_login));

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
		mTwitterLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				TwitterLoginUtils.twitterLogin(LoginActivity.this, new Callback<TwitterSession>() {
					
					@Override
					public void success(Result<TwitterSession> paramResult) {
						// TODO Auto-generated method stub
//						if (!TextUtils.isEmpty(id)) {
//							IPlatApplication.get().setPfLType(LoginPlatform.GOOGLE);
//							IPlatApplication.get().setPfAName("");
//							IPlatApplication.get().setPfPCode("");
//							requestLogin(id, LoginPlatform.GOOGLE);
//						}
						Log.i("yang", "twitter is login success");
//						Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_LONG).show();
						IPlatApplication.get().setPfLType(LoginPlatform.TWITTER);
						IPlatApplication.get().setPfAName("");
						IPlatApplication.get().setPfPCode("");
						String loginid = paramResult.data.getUserId()+"";
						requestLogin(loginid, LoginPlatform.TWITTER);
					}
					
					@Override
					public void failure(TwitterException paramTwitterException) {
						// TODO Auto-generated method stub
						Log.i("yang", "twitter is login failure");
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

		mRegisterBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				loginName = mAccount.getText().toString();
				loginPwd = mRegisterPassword.getText().toString();
				registerEmail = mRegisterEmail.getText().toString();
//				confirmPwd = mRegisterPhone.getText().toString();
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
				if(!EfunStringUtil.isEmpty(registerEmail)){
					if(!EfunStringUtil.email(registerEmail)){
						ToastUtils.toast(mContext, E_string.efun_pd_toast_error_email);
						return;
					}
				}
				
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

		String[] keys = new String[] { "username", "password" };
		String[] oldUserInfo = StoreUtil.getValues(mContext,
				StoreUtil.Param_file_name, keys);
		if (!TextUtils.isEmpty(oldUserInfo[0])) {
			mUsername.setText(oldUserInfo[0]);
			mPassword.setText(oldUserInfo[1]);
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
		requestWithDialog(requestThirdLogin(),
				getString(E_string.efun_pd_loading_msg_login));
	}
	
	private BaseRequestBean[] requestRegister() {
		AccountRegisterRequest mRegisterRequest = new AccountRegisterRequest(
				mContext, loginName, loginPwd, registerEmail);
		mRegisterRequest.setArea(HttpParam.PLATFORM_AREA);
		mRegisterRequest.setDevice(HttpParam.PHONE);
		mRegisterRequest.setPhone("");
		mRegisterRequest.setPackageVersion(AppUtils.getAppVersionName(mContext));
		mRegisterRequest.setVcode("");
		mRegisterRequest.setVersion(HttpParam.PLATFORM);
		mRegisterRequest.setReqType(IPlatformRequest.REQ_ACCOUNT_REGISTER);
		return new BaseRequestBean[] { mRegisterRequest };
	}

	private BaseRequestBean[] requestThirdLogin() {
		AccountThirdLoginRequest loginRequest = new AccountThirdLoginRequest(mContext);
		loginRequest.setLoginType(loginType);
		loginRequest.setPlatform(HttpParam.PLATFORM_AREA);
		loginRequest.setDevice(HttpParam.PHONE);
		loginRequest.setThirdId(thirdId);
		loginRequest.setApps(apps);
		loginRequest.setReqType(IPlatformRequest.REQ_ACCOUNT_THIRD_LOGIN);
		return new BaseRequestBean[] { loginRequest };
	}
	
	@Override
	public void onSuccess(int requestType, BaseResponseBean baseResponse) {
		super.onSuccess(requestType, baseResponse);
		if (requestType == IPlatformRequest.REQ_ACCOUNT_LOGIN) {
			AccountResponse response = (AccountResponse) baseResponse;
			success(response);
		}
		
		if(requestType == IPlatformRequest.REQ_ACCOUNT_THIRD_LOGIN){
			AccountResponse response = (AccountResponse) baseResponse;
			success(response);
		}

		if (requestType == IPlatformRequest.REQ_ACCOUNT_REGISTER) {
			AccountResponse response = (AccountResponse) baseResponse;
			ResultBean result = response.getResultBean();
			if (result.getCode().equals(HttpParam.RESULT_1000)) {
				IPlatApplication.get().setPfLType(loginType);
				IPlatApplication.get().setPfAName(loginName);
				IPlatApplication.get().setPfPCode(loginPwd);
				IPlatApplication.get().setUser((User) result);
				for (int i = 0; i < Integer.MAX_VALUE; i++) {
					if (IPlatApplication.get().getUser() != null) {
						finish();
						((OnLoginFinishListener) IPlatApplication.get()
								.getOnEfunListener()).loginCompleted(true);
						break;
					}
				}
				//提交推送成功后点开的用户UID
//				EfunPushManager.gameLoginSuccessReport(mContext, ((User)result).getUid());
			} else {
				ToastUtils.toast(mContext, result.getMessage());
			}
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
//			IPlatApplication.get().setFindWebNeedFresh(true);
//			IPlatApplication.get().setInteractionWebNeedFresh(true);
			for (int i = 0; i < Integer.MAX_VALUE; i++) {
				if (IPlatApplication.get().getUser() != null) {
					finish();
					((OnLoginFinishListener) IPlatApplication.get()
							.getOnEfunListener()).loginCompleted(true);
					break;
				}
			}
//			EfunPushManager.gameLoginSuccessReport(mContext, ((User)result).getUid());
		}
		ToastUtils.toast(mContext, result.getMessage());
	}
	

	@Override
	public int LayoutId() {
		return E_layout.efun_pd_login;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		TwitterLoginUtils.onActivityResult(LoginActivity.this, requestCode, resultCode, data);
		if (resultCode == ResultCode.RST_ACCOUNT_FACEBOOK_LOGIN
				&& requestCode == RequestCode.REQ_ACCOUNT_FACEBOOK_LOGIN) {
			IPlatApplication.get().setPfLType(LoginPlatform.FACEBOOK);
			IPlatApplication.get().setPfAName("");
			IPlatApplication.get().setPfPCode("");
			String loginid = data.getStringExtra(Key.STRING_KEY);
			String facebookApps = data.getStringExtra(Key.APPS_KEY);
			requestLogin(loginid, LoginPlatform.FACEBOOK, facebookApps);
		}
	}

	@Override
	public void initTitle(TitleView titleView) {
		titleView.setTitleLeftRes(E_drawable.efun_pd_back_focus);
		titleView.setTitleRightStatus(View.GONE);
		titleView.setTitleCenterStatus(View.GONE);
		titleView.setTitleBottomLineStatus(View.GONE);
		titleView.setTitleBarBackgroundRes(E_color.e_ffffff);
	}

	@Override
	public ViewGroup[] needShowLoading() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
