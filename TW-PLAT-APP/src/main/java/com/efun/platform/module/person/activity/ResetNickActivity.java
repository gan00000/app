package com.efun.platform.module.person.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.efun.platform.AndroidScape.E_id;
import com.efun.platform.AndroidScape.E_layout;
import com.efun.platform.AndroidScape.E_string;
import com.efun.platform.http.request.bean.BaseRequestBean;
import com.efun.platform.module.base.FixedActivity;
import com.efun.platform.module.utils.ToastUtils;
import com.efun.platform.utils.Const.Key;
import com.efun.platform.utils.Const.ResultCode;
import com.efun.platform.widget.TitleView;

/**
 * 修改玩家昵称页面
 * @author Jesse
 */
public class ResetNickActivity extends FixedActivity implements OnClickListener{
	private EditText edit;
	private TextView button;
	private String content;
	private View mContainer;
	@Override
	public int LayoutId() {
		return E_layout.efun_pd_commend;
	}
	@Override
	public void onClick(View v) {
		content =edit.getText().toString();
		if(TextUtils.isEmpty(content)){
			ToastUtils.toast(this, E_string.efun_pd_empty_content);
		}else{
			content = content.trim();
			Intent it = new Intent();
			it.putExtra(Key.STRING_KEY, content);
			setResult(ResultCode.RST_CODE_UPDATE_NICK,it);
			finish();
		}
	}
	@Override
	public ViewGroup[] needShowLoading() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void initTitle(TitleView titleView) {
		// TODO Auto-generated method stub
		
	}
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
		mContainer = findViewById(E_id.contaier_relative_1);
		mContainer.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		edit = (EditText) findViewById(E_id.edit_text);
		button = (TextView) findViewById(E_id.center_text);
		button.setOnClickListener(this);
	}
}
