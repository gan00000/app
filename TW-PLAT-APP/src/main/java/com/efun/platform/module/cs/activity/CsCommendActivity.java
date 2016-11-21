package com.efun.platform.module.cs.activity;

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
import com.efun.platform.http.request.bean.CsReplyCommitQuestionRequest;
import com.efun.platform.http.response.bean.BaseResponseBean;
import com.efun.platform.http.response.bean.CsReplyCommitQuestionResponse;
import com.efun.platform.module.base.FixedActivity;
import com.efun.platform.module.cs.bean.CsReplyQuestionBean;
import com.efun.platform.module.utils.ToastUtils;
import com.efun.platform.utils.Const;
import com.efun.platform.utils.Const.HttpParam;
import com.efun.platform.utils.Const.ResultCode;
import com.efun.platform.widget.TitleView;

/**
 * 问题提交页面
 * @author Jesse
 */
public class CsCommendActivity extends FixedActivity implements OnClickListener {
	private EditText edit;
	private TextView button;
	private String content;
	private String pid;


	@Override
	public int LayoutId() {
		return E_layout.efun_pd_commend;
	}

	@Override
	public void init(Bundle bundle) {
		edit = (EditText) findViewById(E_id.edit_text);
		button = (TextView) findViewById(E_id.center_text);
		button.setOnClickListener(this);
		CsReplyQuestionBean mGameItemBean = (CsReplyQuestionBean) bundle.getSerializable(Const.BEAN_KEY);
		pid = mGameItemBean.getTgppid();
	}
	

	@Override
	public void onClick(View v) {
		content = edit.getText().toString();
		if (TextUtils.isEmpty(content)) {
			ToastUtils.toast(this, E_string.efun_pd_empty_content);
		} else {
			content = content.trim();
			requestWithDialog(needRequestDataBean(), getString(E_string.efun_pd_loading_msg_commend));
		}
	}

	@Override
	public void onSuccess(int requestType, BaseResponseBean baseResponse) {
		super.onSuccess(requestType,baseResponse);
		if (requestType == IPlatformRequest.REQ_CS_REPLAY_COMMIT_QUESTION) {
			CsReplyCommitQuestionResponse response = (CsReplyCommitQuestionResponse) baseResponse;
			String code = response.getResponse().getCode();
			if (!TextUtils.isEmpty(code)) {
				if (code.equals(HttpParam.RESULT_1000)) {
					Intent data = new Intent();
					data.putExtra("code", code);
					data.putExtra("replyContent", content);
					setResult(ResultCode.RST_CS_REPLY_QUESTION_RESULT, data);
					finish();
				}
			}
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
		CsReplyCommitQuestionRequest csRequest = new CsReplyCommitQuestionRequest(pid, IPlatApplication.get().getUser().getUsername(), content, HttpParam.CS_PLAYER, HttpParam.CS_CHECK, HttpParam.APP);
		csRequest.setReqType(IPlatformRequest.REQ_CS_REPLAY_COMMIT_QUESTION);
		return new BaseRequestBean[]{csRequest};
	}


}
