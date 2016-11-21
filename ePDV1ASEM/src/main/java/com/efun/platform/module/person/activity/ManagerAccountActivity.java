package com.efun.platform.module.person.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.efun.platform.AndroidScape.E_id;
import com.efun.platform.AndroidScape.E_layout;
import com.efun.platform.AndroidScape.E_string;
import com.efun.platform.http.request.bean.BaseRequestBean;
import com.efun.platform.module.base.FixedActivity;
import com.efun.platform.module.base.impl.OnLoginFinishListener;
import com.efun.platform.module.utils.UserUtils;
import com.efun.platform.utils.Const.ResultCode;
import com.efun.platform.widget.TitleView;
/**
 * 账号管理
 * @author itxuxxey
 *
 */
public class ManagerAccountActivity extends FixedActivity{
	
	private LinearLayout mResetAccountLayout,mExitLoginLayout;
	@Override
	public boolean needRequestData() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public BaseRequestBean[] needRequestDataBean() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void init(Bundle bundle) {
		mResetAccountLayout= (LinearLayout) findViewById(E_id.contaier_linear_2);
		mExitLoginLayout= (LinearLayout) findViewById(E_id.contaier_linear_3);
		
		mResetAccountLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				UserUtils.changeUser(mContext, new OnLoginFinishListener() {
					@Override
					public void loginCompleted(boolean completed) {
						if(completed){
							finish();
						}
					}
				});
			}
		});
		mExitLoginLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				UserUtils.logout(mContext);
				setResult(ResultCode.RST_LOGOUT);
				finish();
			}
		});
	}

	@Override
	public ViewGroup[] needShowLoading() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int LayoutId() {
		return E_layout.efun_pd_manager_account;
	}

	@Override
	public void initTitle(TitleView titleView) {
		titleView.setTitleCenterRes(E_string.efun_pd_manager_account, false);
		titleView.setTitleRightStatus(View.GONE);
	}

}
