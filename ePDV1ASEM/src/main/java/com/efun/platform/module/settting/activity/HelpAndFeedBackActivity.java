package com.efun.platform.module.settting.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.efun.core.tools.EfunStringUtil;
import com.efun.platform.AndroidScape.E_id;
import com.efun.platform.AndroidScape.E_layout;
import com.efun.platform.AndroidScape.E_string;
import com.efun.platform.IPlatApplication;
import com.efun.platform.http.dao.impl.IPlatformRequest;
import com.efun.platform.http.request.bean.BaseRequestBean;
import com.efun.platform.http.request.bean.CsAskRequest;
import com.efun.platform.http.response.bean.BaseResponseBean;
import com.efun.platform.http.response.bean.CsAskResponse;
import com.efun.platform.module.base.FixedActivity;
import com.efun.platform.module.utils.ToastUtils;
import com.efun.platform.utils.Const.HttpParam;
import com.efun.platform.widget.TitleView;

/**
 * 幫助或反饋問題
 * 
 * @author itxuxxey
 * 
 */
public class HelpAndFeedBackActivity extends FixedActivity {
	private EditText mAskContent;
	private TextView mConfirmBtn;
	
	private String content;
	private String phoneNum;
	private String email;
	private String mCurrentGameCode;
	private String mCurrentServerCode;
	private String mCurrentRoleId;
	private String mCurrentGameUid;

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
		return null;
	}

	@Override
	public void init(Bundle bundle) {
		mAskContent = (EditText) findViewById(E_id.edit_helper_ask);
		mConfirmBtn = (TextView) findViewById(E_id.helper_confirm_btn);
		
		mConfirmBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				content = mAskContent.getText().toString();
				phoneNum = IPlatApplication.get().getUser().getPhone();
				email = IPlatApplication.get().getUser().getEmail();
				mCurrentRoleId = IPlatApplication.get().getUser().getUid();
				mCurrentGameUid = IPlatApplication.get().getUser().getUid();
				mCurrentGameCode = HttpParam.PLATFORM_CODE;
				mCurrentServerCode = "";
				if(TextUtils.isEmpty(content) || EfunStringUtil.isEmpty(content.trim())){
					ToastUtils.toast(mContext, getString(E_string.efun_pd_setting_help_empty));
					return;
				}
				requestWithDialog(createQuestionRequest(), getString(E_string.efun_pd_loading_msg_commend));
			}
		});
	}

	@Override
	public void onSuccess(int requestType, BaseResponseBean baseResponse) {
		if (requestType == IPlatformRequest.REQ_CS_ASK) {
			CsAskResponse response = (CsAskResponse) baseResponse;
			String code = response.getResponse().getCode();
			if (!TextUtils.isEmpty(code)) {
				if (code.equals(HttpParam.RESULT_1000)) {
					ToastUtils.toast(mContext, response.getResponse().getMessage());
					finish();
				}
			}
		}
	}

	@Override
	public ViewGroup[] needShowLoading() {
		return null;
	}

	public void requst() {
	}

	@Override
	public int LayoutId() {
		return E_layout.efun_pd_setting_help_and_feedback;
	}
	
	private BaseRequestBean[] createQuestionRequest(){
		int length = content.length();
		// 标题
		String title = null;
		if (length < 14) {
			title = content;
		} else {
			char[] strChar = content.toCharArray();
			char[] chars = new char[14];
			for (int i = 0; i < chars.length; i++) {
				chars[i] = strChar[i];
			}
			title = String.valueOf(chars);
		}
		//反饋問題這里，與客服提交接口共用，不同在于問題類型的不同，這里使用的是113(應用反饋)
		CsAskRequest csRequest = new CsAskRequest(HttpParam.FEEDBACK_TYPE, HttpParam.PLATFORM_AREA, mCurrentGameCode, title, content, HttpParam.APP);
		if(IPlatApplication.get().getUser()!=null){
			csRequest.setSign(IPlatApplication.get().getUser().getSign());
			csRequest.setTimestamp(IPlatApplication.get().getUser().getTimestamp());
		}
		csRequest.setServerCode(mCurrentServerCode);
		csRequest.setIsMobile("true");
		csRequest.setEmail(email);
		csRequest.setContactWay(phoneNum);
		csRequest.setRoleId(mCurrentRoleId);
		csRequest.setGameUid(mCurrentGameUid);
		csRequest.setReqType(IPlatformRequest.REQ_CS_ASK);
		return new BaseRequestBean[] { csRequest };
}

	@Override
	public void initTitle(TitleView titleView) {
		titleView.setTitleCenterRes(E_string.efun_pd_setting_help, false);
		titleView.setTitleRightStatus(View.GONE);
	}
}
