package com.efun.platform.module.account.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.efun.core.tools.EfunStringUtil;
import com.efun.platform.AndroidScape.E_dimens;
import com.efun.platform.AndroidScape.E_drawable;
import com.efun.platform.AndroidScape.E_id;
import com.efun.platform.AndroidScape.E_layout;
import com.efun.platform.AndroidScape.E_string;
import com.efun.platform.IPlatApplication;
import com.efun.platform.http.request.bean.BaseRequestBean;
import com.efun.platform.http.response.bean.BaseResponseBean;
import com.efun.platform.image.ImageManager;
import com.efun.platform.module.account.bean.User;
import com.efun.platform.module.base.FixedActivity;
import com.efun.platform.utils.Const.LoginPlatform;
import com.efun.platform.utils.Const.StartAPPParams;
import com.efun.platform.widget.TitleView;

/**
 * 登陆
 * 
 * @author itxuxxey
 * 
 */
public class AuthorizaGameToPlatformActivity extends FixedActivity {
	private TextView mChangeAccount,mGameName,mCancel,mSubmit,headName;
	private ImageView gameIcon,headIcon;
	private User mUser;
	private String gameName = "";
	private String gameCode = "";

	@Override
	protected void onDestroy() {
		super.onDestroy();
	
	}
	
	@Override
	public void onBackPressed() {
		Intent it = new Intent();
		setResult(StartAPPParams.PFACTIVITY_RETURNRESULT, it);
		finish();
	}

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
		if(bundle != null){
			gameName = bundle.getString(StartAPPParams.GAMENAME);
			gameCode = bundle.getString(StartAPPParams.GAMECODE);
    	}
		mChangeAccount = (TextView) findViewById(E_id.change_account);
		mGameName = (TextView) findViewById(E_id.game_name);
		headName = (TextView) findViewById(E_id.user_nickName);
		mCancel = (TextView) findViewById(E_id.authori_cancel);
		mSubmit = (TextView) findViewById(E_id.authori_confirm);
		gameIcon = (ImageView) findViewById(E_id.game_icon);
		headIcon = (ImageView) findViewById(E_id.head_icon);
		if(IPlatApplication.get().getUser() != null){
			mUser = IPlatApplication.get().getUser();
		}
		addListeners();
		if(IPlatApplication.get().getUser() != null){
			initDatas();
		}else{
			Intent it = new Intent(this,AuthorizaLoginActivity.class);
			this.startActivityForResult(it, StartAPPParams.AU_UNLOGINTOLOGIN);
		}
	}

	private void initDatas() {
		// TODO Auto-generated method stub
		if(mUser != null){
			if (TextUtils.isEmpty(mUser.getIcon())) {
				if (mUser.getSex().equals(getString(E_string.efun_pd_sex_girl))) {
					headIcon.setImageResource(E_drawable.efun_pd_default_round_user_girl_icon);
				} else {
					headIcon.setImageResource(E_drawable.efun_pd_default_round_user_boy_icon);
				}
			} else {
				ImageManager.displayImage(mUser.getIcon(), headIcon,
						ImageManager.IMAGE_ROUND_USER,
						E_dimens.e_corners_radius_124);
			}
			if(!EfunStringUtil.isEmpty(mUser.getAccountName()) && IPlatApplication.get().getPfLType().equals(LoginPlatform.EFUN)){
				headName.setText(mUser.getAccountName());
			}else{
				headName.setText(getString(E_string.efun_pd_efun_account_empty));
			}
		}
		if(!EfunStringUtil.isEmpty(gameCode)){
			ImageManager.displayImage(getString(E_string.efun_pd_url_game_icon_base) + gameCode + ".png", gameIcon);
		}
		if (!EfunStringUtil.isEmpty(gameName)) {
			mGameName.setText(gameName);
		}
	}

	private void addListeners() {
		// TODO Auto-generated method stub
		//切換帳號
		mChangeAccount.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent it = new Intent(mContext, AuthorizaLoginActivity.class);
				AuthorizaGameToPlatformActivity.this.startActivityForResult(it, StartAPPParams.AU_CHANGETOLOGIN);
			}
		});
		//取消
		mCancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent it = new Intent();
				setResult(StartAPPParams.PFACTIVITY_RETURNRESULT, it);
				finish();
			}
		});
		//授權
		mSubmit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent it = new Intent();
				it.putExtra(StartAPPParams.USERUID, IPlatApplication.get().getUser().getUid());
				it.putExtra(StartAPPParams.USERSIGN,IPlatApplication.get().getUser().getSign());
				it.putExtra(StartAPPParams.USERTIMESTAMP, IPlatApplication.get().getUser().getTimestamp());
				it.putExtra(StartAPPParams.USERNAME, IPlatApplication.get().getUser().getAccountName());
				it.putExtra(StartAPPParams.LOGINTYPE, IPlatApplication.get().getPfLType());
				
				setResult(StartAPPParams.PFACTIVITY_AURESULT, it);
				finish();
			}
		});
	}

	@Override
	public void onSuccess(int requestType, BaseResponseBean baseResponse) {
		super.onSuccess(requestType, baseResponse);
	}


	@Override
	public int LayoutId() {
		return E_layout.efun_pd_authorization;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (resultCode) {
		case StartAPPParams.AU_CHANGERETURN://切换账号的返回
			break;
		case StartAPPParams.AU_UNLOGINRETURN://平台还没登录时的返回
			Intent it = new Intent();
			setResult(StartAPPParams.PFACTIVITY_RETURNRESULT, it);
			finish();
			break;
		case StartAPPParams.AU_LOGIN_SUCCESS://登录成功时的跳转
			if(IPlatApplication.get().getUser() != null){
				mUser = IPlatApplication.get().getUser();
			}
			initDatas();
			break;
		}
	}

	@Override
	public void initTitle(TitleView titleView) {
		
	}

	@Override
	public ViewGroup[] needShowLoading() {
		// TODO Auto-generated method stub
		return null;
	}

}
