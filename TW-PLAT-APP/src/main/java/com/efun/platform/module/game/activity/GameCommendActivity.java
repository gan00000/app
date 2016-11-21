package com.efun.platform.module.game.activity;

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
import com.efun.platform.IPlatApplication;
import com.efun.platform.http.dao.impl.IPlatformRequest;
import com.efun.platform.http.request.bean.BaseRequestBean;
import com.efun.platform.http.request.bean.GameCommendRequest;
import com.efun.platform.http.response.bean.BaseResponseBean;
import com.efun.platform.http.response.bean.GameCommendResponse;
import com.efun.platform.module.account.bean.User;
import com.efun.platform.module.base.FixedActivity;
import com.efun.platform.module.game.bean.GameCommendBean;
import com.efun.platform.module.game.bean.GameCommendItemBean;
import com.efun.platform.module.game.bean.GameItemBean;
import com.efun.platform.module.utils.ToastUtils;
import com.efun.platform.utils.Const;
import com.efun.platform.utils.Const.HttpParam;
import com.efun.platform.utils.Const.ResultCode;
import com.efun.platform.widget.TitleView;

/**
 * 游戏评论页面
 * @author Jesse
 */
public class GameCommendActivity extends FixedActivity implements OnClickListener{
	private EditText edit;
	private TextView button;
	private String userName;
	private String content;
	private String  gameCode;
	private String  type;
	private String  pid;
	private String icon;
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
			requestWithDialog(needRequestDataBean(),getString(E_string.efun_pd_loading_msg_commend));
		}
	}
	
	@Override
	public void onSuccess(int requestType, BaseResponseBean baseResponse) {
		super.onSuccess(requestType,baseResponse);
		if (requestType==IPlatformRequest.REQ_GAME_COMMEND){
			GameCommendResponse mGameCommendResponse= (GameCommendResponse)baseResponse;
			GameCommendBean bean = mGameCommendResponse.getGameCommend();
			if(bean.getCode().equals(HttpParam.RESULT_1000)){
				Intent it = new Intent();
				GameCommendItemBean itemBean = new GameCommendItemBean();
				itemBean.setContent(content);
				itemBean.setModifiedTime(System.currentTimeMillis());
				itemBean.setUserName(userName);
				itemBean.setIcon(icon);
				it.putExtra(Const.BEAN_KEY, itemBean);
				setResult(ResultCode.RST_GAME_COMMEND, it);
				finish();
			}
		}
	}
	@Override
	public ViewGroup[] needShowLoading() {
		return null;
	}
	@Override
	public void initTitle(TitleView titleView) {
		
	}
	@Override
	public boolean needRequestData() {
		return false;
	}
	@Override
	public BaseRequestBean[] needRequestDataBean() {
		GameCommendRequest mGameCommendRequest = new GameCommendRequest(userName, content, gameCode, type, pid);
		if(IPlatApplication.get().getUser()!=null){
			mGameCommendRequest.setSign(IPlatApplication.get().getUser().getSign());
			mGameCommendRequest.setTimestamp(IPlatApplication.get().getUser().getTimestamp());
		}
		mGameCommendRequest.setReqType(IPlatformRequest.REQ_GAME_COMMEND);
		return new BaseRequestBean[]{mGameCommendRequest};
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
		
		User user = IPlatApplication.get().getUser();
		GameItemBean mGameItemBean  = (GameItemBean) bundle.getSerializable(Const.BEAN_KEY);
		gameCode = mGameItemBean.getGameCode();
		type = "game";
		pid = "0";
		if(user!=null){
			if(!TextUtils.isEmpty(user.getUsername())){				
				userName = user.getUsername();
			}else{
				userName = getString(E_string.efun_pd_anonymous);
			}
			icon = user.getIcon();
		}else{
			userName = getString(E_string.efun_pd_anonymous);
			icon = "";
		}
	}
}
