package com.efun.platform.module.cs.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;

import com.efun.platform.IPlatApplication;
import com.efun.platform.AndroidScape.E_string;
import com.efun.platform.http.dao.impl.IPlatformRequest;
import com.efun.platform.http.request.bean.BaseRequestBean;
import com.efun.platform.http.request.bean.CsReplyQuestionListRequest;
import com.efun.platform.http.response.bean.BaseResponseBean;
import com.efun.platform.http.response.bean.CsReplyQuestionListResponse;
import com.efun.platform.module.base.ElasticityActivity;
import com.efun.platform.module.bean.PageInfoBean;
import com.efun.platform.module.cs.adapter.CsReplyAdapter;
import com.efun.platform.module.cs.bean.CsReplyQuestionBean;
import com.efun.platform.module.utils.IntentUtils;
import com.efun.platform.utils.Const.HttpParam;
import com.efun.platform.utils.Const.RequestCode;
import com.efun.platform.utils.Const.ResultCode;
import com.efun.platform.widget.TitleView;
import com.efun.platform.widget.list.XListView.IXListViewListener;

/**
 * 客服回复
 * 
 * @author Jesse
 */
public class CsReplyActivity extends ElasticityActivity {

	
	private ArrayList<CsReplyQuestionBean> mCsReplayQuestionBeans;

	// 当前页
	private int currentPage = 1;
	// 每頁獲取的問題個數
	private final int GET_PAGE_SIZE = 20;

	private CsReplyAdapter mAdapter;

	private int mCurrentSelected = 0;

	@Override
	public View[] addHeaderViews() {
		return null;
	}

	@Override
	public void init(Bundle bundle) {
		mAdapter = new CsReplyAdapter(mContext);
		mListView.setPullLoadEnable(true);
		mListView.setXListViewListener(new IXListViewListener() {

			@Override
			public void onRefresh() {
				currentPage = 1;
				requestWithoutDialog(needRequestDataBean());
			}

			@Override
			public void onLoadMore() {
				if (mAdapter != null) {
					currentPage = (int) (mAdapter.getCount() / GET_PAGE_SIZE) + 1;
					requestWithoutDialog(needRequestDataBean());
				}
			}
		});
		
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				mCurrentSelected = arg2 - 1;
				mAdapter.setSelectionPostion(mCurrentSelected);
				mAdapter.notifyDataSetChanged();
				if (mAdapter.getItem(mCurrentSelected) != null) {
					IntentUtils.goWithBeanForResult(mContext, CsReplyDetailsActivity.class, (CsReplyQuestionBean) mAdapter.getItem(mCurrentSelected), RequestCode.REQ_CS_REPLY_QUESTION_REQUEST);
				}
			}
		});
	}

	@Override
	public void onSuccess(int requestType, BaseResponseBean baseResponse) {
		super.onSuccess(requestType, baseResponse);
		if (requestType == IPlatformRequest.REQ_CS_REPLAY_QUESTION_LIST) {
			CsReplyQuestionListResponse response = (CsReplyQuestionListResponse) baseResponse;

			mCsReplayQuestionBeans = response.getcReplayQuestionBeans();
			// 判断返回的bean是否为空
			if (currentPage == 1) {
				mAdapter.refresh(mCsReplayQuestionBeans);
				mListView.stopRefresh();
			} else {
				mAdapter.append(mCsReplayQuestionBeans);
				mListView.stopLoadMore();
			}

			PageInfoBean pageInfoBean = response.getPageInfoBean();
			if (pageInfoBean != null) {
				if(pageInfoBean.getTotalPage()==0){
				}else if (pageInfoBean.getPageIndex() == pageInfoBean.getTotalPage()) {
					mListView.setPullLoadEnable(false);
				} else {
					mListView.setPullLoadEnable(true);
				}
			}
		}
	}


	@Override
	public boolean needRequestData() {
		return true;
	}

	@Override
	public BaseRequestBean[] needRequestDataBean() {
		CsReplyQuestionListRequest csRequest = new CsReplyQuestionListRequest(HttpParam.APP, HttpParam.PLATFORM_AREA, currentPage, String.valueOf(GET_PAGE_SIZE));
		if(IPlatApplication.get().getUser()!=null){
			csRequest.setSign(IPlatApplication.get().getUser().getSign());
			csRequest.setTimestamp(IPlatApplication.get().getUser().getTimestamp());
		}
		csRequest.setReqType(IPlatformRequest.REQ_CS_REPLAY_QUESTION_LIST);
		return new BaseRequestBean[] { csRequest };
	}

	@Override
	public BaseAdapter adapter() {
		return mAdapter;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == ResultCode.RST_CS_REPLY_QUESTION_RESULT) {
			if (data != null) {
				String code = data.getStringExtra("code");
//				String replyString = data.getStringExtra("replyContent");
				if (!TextUtils.isEmpty(code)) {
					CsReplyQuestionBean mBean = (CsReplyQuestionBean) mAdapter.getItem(mCurrentSelected);
					mBean.setHasNew("0");
					mAdapter.getmArray().remove(mCurrentSelected);
					mAdapter.getmArray().add(0, mBean);
					mAdapter.notifyDataSetChanged();
				}
			}
		} else if (resultCode == ResultCode.RST_CS_REPLY_FINISH) {
			if(data != null){
				String code = data.getStringExtra("code");
				if(!TextUtils.isEmpty(code)){
					CsReplyQuestionBean mBean = (CsReplyQuestionBean) mAdapter.getItem(mCurrentSelected);
					mBean.setHasNew("0");
					mBean.setReplyStatus("9");
					mAdapter.getmArray().remove(mCurrentSelected);
					mAdapter.getmArray().add(0, mBean);
					mAdapter.notifyDataSetChanged();
				}
			}else{		
				if( mAdapter.getItem(mCurrentSelected)!=null){
					((CsReplyQuestionBean) mAdapter.getItem(mCurrentSelected)).setHasNew("0");
					((CsReplyQuestionBean) mAdapter.getItem(mCurrentSelected)).setReplyStatus("9");
					mAdapter.notifyDataSetChanged();
				}
			}
		} else if (resultCode == ResultCode.RST_CS_REPLY_READ) {
			if(mAdapter.getItem(mCurrentSelected)!=null){
				((CsReplyQuestionBean) mAdapter.getItem(mCurrentSelected)).setHasNew("0");
				mAdapter.notifyDataSetChanged();
			}
		}else{
			if(mAdapter.getItem(mCurrentSelected)!=null){
				((CsReplyQuestionBean) mAdapter.getItem(mCurrentSelected)).setHasNew("0");
				mAdapter.notifyDataSetChanged();
			}
		}
	}

	@Override
	public void initTitle(TitleView titleView) {
		titleView.setTitleRightStatus(View.GONE);
		titleView.setTitleCenterRes(E_string.efun_pd_cs_reply, false);
	}

}
