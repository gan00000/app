package com.efun.platform.module.cs.activity;

import java.util.ArrayList;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.efun.platform.AndroidScape.E_id;
import com.efun.platform.AndroidScape.E_layout;
import com.efun.platform.AndroidScape.E_string;
import com.efun.platform.AndroidScape.E_style;
import com.efun.platform.IPlatApplication;
import com.efun.platform.http.dao.impl.IPlatformRequest;
import com.efun.platform.http.request.bean.BaseRequestBean;
import com.efun.platform.http.request.bean.CsReplyCommitQuestionRequest;
import com.efun.platform.http.request.bean.CsReplyDetailsRequest;
import com.efun.platform.http.request.bean.CsReplyFinishQuestionRequest;
import com.efun.platform.http.response.bean.BaseResponseBean;
import com.efun.platform.http.response.bean.CsReplyCommitQuestionResponse;
import com.efun.platform.http.response.bean.CsReplyDetailsResponse;
import com.efun.platform.http.response.bean.CsReplyFinishQuestionResponse;
import com.efun.platform.module.base.FixedActivity;
import com.efun.platform.module.base.impl.OnEfunItemClickListener;
import com.efun.platform.module.cs.bean.CsReplyDetailsBean;
import com.efun.platform.module.cs.bean.CsReplyDetailsListBean;
import com.efun.platform.module.cs.bean.CsReplyQuestionBean;
import com.efun.platform.module.utils.ToastUtils;
import com.efun.platform.module.utils.ViewUtils;
import com.efun.platform.utils.Const;
import com.efun.platform.utils.Const.HttpParam;
import com.efun.platform.utils.Const.ResultCode;
import com.efun.platform.utils.TimeFormatUtil;
import com.efun.platform.widget.RatingBarView;
import com.efun.platform.widget.TitleView;

/**
 * 客服回复详情页面
 * @author Jesse
 */
public class CsReplyDetailsActivity extends FixedActivity implements OnClickListener {
	
	@Override
	public void onClickLeft() {
		super.onClickLeft();
		onBack();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onClick(View v) {
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		onBack();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			onBack();
		}
		return false;
	}
	
	
	private CsReplyQuestionBean mReplyQuestionBean;

	private CsReplyDetailsListBean mDetailsListBeans;

	private ArrayList<CsReplyDetailsBean> mBeans;

	private LinearLayout containerLy;

	private EditText mEditText;

	private TextView mQuestionTxt;

	private TextView mReplyBtn;

	private TextView mFinishBtn;

	private final int CS_REPLY_DETAILS = 0;
	private final int CS_REPLY_COMMIT_QUESTION = 1;
	private final int CS_REPLY_FINISH_QUESTION = 2;

	private int requestFlag = CS_REPLY_DETAILS;

	private int mEva = 3;

	private String replyContent = null;
	
	private boolean replyFlag = false;
	private boolean finishFlag = false;

	@Override
	public boolean needRequestData() {
		return true;
	}

	@Override
	public BaseRequestBean[] needRequestDataBean() {
		if (requestFlag == CS_REPLY_DETAILS) {
			CsReplyDetailsRequest csRequest = new CsReplyDetailsRequest(mReplyQuestionBean.getTgppid());
			if(IPlatApplication.get().getUser()!=null){
				csRequest.setSign(IPlatApplication.get().getUser().getSign());
				csRequest.setTimestamp(IPlatApplication.get().getUser().getTimestamp());
			}
			csRequest.setReqType(IPlatformRequest.REQ_CS_REPLY_DETAILS);
			return new BaseRequestBean[] { csRequest };
		} else if (requestFlag == CS_REPLY_COMMIT_QUESTION && mReplyQuestionBean != null && !TextUtils.isEmpty(mEditText.getText().toString())) {
			replyContent = mEditText.getText().toString();
			CsReplyCommitQuestionRequest csRequest = new CsReplyCommitQuestionRequest(mReplyQuestionBean.getTgppid(), IPlatApplication.get().getUser().getUsername(), replyContent, HttpParam.CS_PLAYER, HttpParam.CS_CHECK, HttpParam.APP);
			if(IPlatApplication.get().getUser()!=null){
				csRequest.setUid(IPlatApplication.get().getUser().getUid());
				csRequest.setSign(IPlatApplication.get().getUser().getSign());
				csRequest.setTimestamp(IPlatApplication.get().getUser().getTimestamp());
			}
			csRequest.setReqType(IPlatformRequest.REQ_CS_REPLAY_COMMIT_QUESTION);
			return new BaseRequestBean[] { csRequest };
		} else if (requestFlag == CS_REPLY_FINISH_QUESTION) {
			CsReplyFinishQuestionRequest csRequest = new CsReplyFinishQuestionRequest(mReplyQuestionBean.getTgppid(), mEva, HttpParam.APP, HttpParam.PHONE);
			if(IPlatApplication.get().getUser()!=null){
				csRequest.setSign(IPlatApplication.get().getUser().getSign());
				csRequest.setTimestamp(IPlatApplication.get().getUser().getTimestamp());
			}
			csRequest.setReqType(IPlatformRequest.REQ_CS_REPLAY_FINISH_QUESTION);
			return new BaseRequestBean[] { csRequest };
		}
		return null;
	}

	@Override
	public void init(Bundle bundle) {
		mReplyQuestionBean = (CsReplyQuestionBean) bundle.getSerializable(Const.BEAN_KEY);
		containerLy = (LinearLayout) findViewById(E_id.container);
		mEditText = (EditText) findViewById(E_id.edit_player_ask);
		mReplyBtn = (TextView) findViewById(E_id.text_1);
		mFinishBtn = (TextView) findViewById(E_id.text_2);
		mQuestionTxt = (TextView) findViewById(E_id.txt_question);
		View view = findViewById(E_id.contaier_linear_1);
		View bottom= findViewById(E_id.contaier_relative_2);
		if (mReplyQuestionBean != null) {
			mQuestionTxt.setText(mReplyQuestionBean.getTheQuestions());
			if (mReplyQuestionBean.getReplyStatus().equals("9")) {
				bottom.setVisibility(View.GONE);
				mEditText.setVisibility(View.GONE);
				view.setVisibility(View.GONE);
			}
		}
		
		mReplyBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View paramView) {
				if (!TextUtils.isEmpty(mEditText.getText().toString())) {
					requestFlag = CS_REPLY_COMMIT_QUESTION;
					requestWithDialog(needRequestDataBean(), getString(E_string.efun_pd_loading_msg_commend));
				} else {
					ToastUtils.toast(mContext, getString(E_string.efun_pd_cs_hints_ask_write_content_empty));
				}
			}
		});
		mFinishBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View paramView) {
				showGradeDialog();
			}
		});

	}

	@Override
	public void onSuccess(int requestType, BaseResponseBean baseResponse) {
		super.onSuccess(requestType,baseResponse);
		if (requestType==IPlatformRequest.REQ_CS_REPLY_DETAILS) {
			CsReplyDetailsResponse response = (CsReplyDetailsResponse)baseResponse;
			mDetailsListBeans = response.getResponse();
			// 判断返回的bean是否为空
			if (mDetailsListBeans != null) {
				// 其中的问题回复列表
				mBeans = mDetailsListBeans.getmBeans();
				for (int i = 0; i < mBeans.size(); i++) {
					String replyRole = mBeans.get(i).getReplyRole();
					String replyTime = mBeans.get(i).getReplyTime();
					String replyContent = mBeans.get(i).getReplyContent();
					int layoutId = 0;
					if (!TextUtils.isEmpty(replyRole)) {
						if (replyRole.equals("cs")) {
							layoutId = E_layout.efun_pd_cs_reply_details_cs_item;
						} else if (replyRole.equals("player")) {
							layoutId = E_layout.efun_pd_cs_reply_details_player_item;
						}
					}
					View convertView = ViewUtils.createView(mContext,layoutId);
					TextView mCategory = (TextView) convertView.findViewById(E_id.item_category);
					TextView mTime = (TextView) convertView.findViewById(E_id.item_time);
					TextView mContent = (TextView) convertView.findViewById(E_id.item_content);
					if (!TextUtils.isEmpty(replyRole)) {
						if (replyRole.equals("cs")) {
							mCategory.setText(getResources().getString(E_string.efun_pd_role_cs));
						} else if (replyRole.equals("player")) {
							mCategory.setText(getResources().getString(E_string.efun_pd_role_self));
						}
					}
					if (!TextUtils.isEmpty(replyTime)) {
						mTime.setText(replyTime);
					}
					if (!TextUtils.isEmpty(replyContent)) {
						mContent.setText(replyContent);
					}
					containerLy.addView(convertView);
				}
			}
		} else if (requestType==IPlatformRequest.REQ_CS_REPLAY_COMMIT_QUESTION) {
			CsReplyCommitQuestionResponse response = (CsReplyCommitQuestionResponse)baseResponse;
			String code = response.getResponse().getCode();
			if (!TextUtils.isEmpty(code)) {
				if (code.equals(HttpParam.RESULT_1000)) {
					ToastUtils.toast(mContext, getString(E_string.efun_pd_cs_hints_reply_ok));
					Intent data = new Intent();
					data.putExtra("code", code);
//					data.putExtra("replyContent", replyContent);
					setResult(ResultCode.RST_CS_REPLY_QUESTION_RESULT, data);
					replyFlag = true;
					View convertView = ViewUtils.createView(mContext, E_layout.efun_pd_cs_reply_details_player_item);
					TextView mCategory = (TextView) convertView.findViewById(E_id.item_category);
					TextView mTime = (TextView) convertView.findViewById(E_id.item_time);
					TextView mContent = (TextView) convertView.findViewById(E_id.item_content);
					mCategory.setText(getResources().getString(E_string.efun_pd_role_self));
					mTime.setText(TimeFormatUtil.LongFormatDate3(System.currentTimeMillis()));
					mContent.setText(replyContent);
					containerLy.addView(convertView);
					mEditText.setText("");
//					finish();
				}
			}

		} else if (requestType==IPlatformRequest.REQ_CS_REPLAY_FINISH_QUESTION) {
			CsReplyFinishQuestionResponse response = (CsReplyFinishQuestionResponse)baseResponse;
			String code = response.getResponse().getCode();
			if (!TextUtils.isEmpty(code)) {
				if (code.equals(HttpParam.RESULT_1000)) {
					finishFlag = true;
					ToastUtils.toast(mContext, getString(E_string.efun_pd_cs_hints_reply_finish_question));
					if(replyFlag){
						Intent data = new Intent();
						data.putExtra("code", code);
						setResult(ResultCode.RST_CS_REPLY_FINISH,data);
					}else{
						setResult(ResultCode.RST_CS_REPLY_FINISH);
					}
					finish();
				}
			}
		}
	}

	@Override
	public ViewGroup[] needShowLoading() {
		RelativeLayout container = (RelativeLayout)findViewById(E_id.contaier_relative_3);
		return new ViewGroup[] { container };
	}

	@Override
	public int LayoutId() {
		return E_layout.efun_pd_cs_reply_list_item_body;
	}

	/**
	 * 展示評分的對話框
	 */
	private void showGradeDialog() {
		final Dialog dialog = new Dialog(mContext, E_style.CS_Dialog);
		// 设置它的ContentView
		dialog.setContentView(E_layout.efun_pd_cs_reply_grade_dialog);
		TextView txtGrade = (TextView) dialog.findViewById(E_id.txt_grade_question);
		RatingBarView ratingBar = (RatingBarView) dialog.findViewById(E_id.ratingbar_grade_question);
		ratingBar.createdStarBar(new OnEfunItemClickListener() {
			
			@Override
			public void onItemClick(int position) {
				mEva = position+1;
			}
		});
		txtGrade.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				requestFlag = CS_REPLY_FINISH_QUESTION;
				requestWithDialog(needRequestDataBean(), getString(E_string.efun_pd_loading_msg_commend));
				dialog.dismiss();
			}
		});
		dialog.show();
	}

	public void onBack() {
		if (mDetailsListBeans != null && !replyFlag) {
			setResult(ResultCode.RST_CS_REPLY_READ);
		}else if(finishFlag){
			Intent data = new Intent();
			data.putExtra("code", HttpParam.RESULT_1000);
			setResult(ResultCode.RST_CS_REPLY_QUESTION_RESULT,data);
		}else{
			setResult(ResultCode.RST_CS_REPLY_READ);
		}
		finish();
	}

	@Override
	public void initTitle(TitleView titleView) {
		titleView.setTitleRightStatus(View.GONE);
		titleView.setTitleCenterRes(E_string.efun_pd_cs_reply, false);
	}
}
