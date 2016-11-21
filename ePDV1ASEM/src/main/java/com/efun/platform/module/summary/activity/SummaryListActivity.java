package com.efun.platform.module.summary.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.efun.facebook.share.activity.EfunFBFunctionActivity;
import com.efun.platform.IPlatApplication;
import com.efun.platform.AndroidScape.E_id;
import com.efun.platform.AndroidScape.E_string;
import com.efun.platform.http.dao.impl.IPlatformRequest;
import com.efun.platform.http.request.bean.BaseRequestBean;
import com.efun.platform.http.request.bean.SummaryListRequest;
import com.efun.platform.http.request.bean.SummaryPraiseRequest;
import com.efun.platform.http.response.bean.BaseResponseBean;
import com.efun.platform.http.response.bean.SummaryListResponse;
import com.efun.platform.http.response.bean.SummaryPraiseResponse;
import com.efun.platform.module.BaseDataBean;
import com.efun.platform.module.PopWindow;
import com.efun.platform.module.base.ElasticityActivity;
import com.efun.platform.module.base.impl.OnEfunItemAttrsClickListener;
import com.efun.platform.module.base.impl.OnLoginFinishListener;
import com.efun.platform.module.base.impl.OnUpdateListener;
import com.efun.platform.module.summary.adapter.SummaryAdapter;
import com.efun.platform.module.summary.adapter.VideoAdapter;
import com.efun.platform.module.summary.bean.SummaryItemBean;
import com.efun.platform.module.summary.bean.SummaryPraiseBean;
import com.efun.platform.module.utils.AppUtils;
import com.efun.platform.module.utils.IntentUtils;
import com.efun.platform.module.utils.PopUtils;
import com.efun.platform.module.utils.ToastUtils;
import com.efun.platform.module.utils.UserUtils;
import com.efun.platform.utils.Const.ClickButtonType;
import com.efun.platform.utils.Const.HttpParam;
import com.efun.platform.utils.Const.Summary;
import com.efun.platform.utils.Const.Web;
import com.efun.platform.widget.TitleView;
import com.efun.platform.widget.list.XListView.IXListViewListener;

public class SummaryListActivity extends ElasticityActivity{
	private SummaryAdapter mAdapter;
	private VideoAdapter mVideoAdapter;
	private int mCurrentPage = 0;
	private String mPageSize = "10";
	private int mComeFrom;
	private PopWindow sharePop;
	private int praisePosition;
	private SummaryItemBean mSummaryItemBean;
	
	@Override
	public View[] addHeaderViews() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean needRequestData() {
		return true;
	}

	@Override
	public BaseAdapter adapter() {
		if(mComeFrom != Summary.SUMMARY_VIDEO){
			return mAdapter;
		}else{
			return mVideoAdapter;
		}
	}

	@Override
	public BaseRequestBean[] needRequestDataBean() {
		if(mComeFrom != Summary.SUMMARY_VIDEO){
			SummaryListRequest request = new SummaryListRequest(
					HttpParam.PLATFORM_AREA,
					mCurrentPage+"", mPageSize, "",mComeFrom+"");
			request.setVersion(HttpParam.PLATFORM);
			request.setPackageVersion(AppUtils.getAppVersionName(mContext));
			request.setGameCode(HttpParam.PLATFORM_CODE);
			request.setReqType(IPlatformRequest.REQ_SUMMARY_LIST_NEWS);
			return new BaseRequestBean[] { request };
		}else{
			SummaryListRequest videoRequest = null;
			if(IPlatApplication.get().getUser() != null){//與我相關
				videoRequest = new SummaryListRequest(
						HttpParam.PLATFORM_AREA,
						mCurrentPage+"", mPageSize, IPlatApplication.get().getUser().getUid(),Summary.SUMMARY_VIDEO+"");
			}else{
				videoRequest = new SummaryListRequest(
						HttpParam.PLATFORM_AREA,
						mCurrentPage+"", mPageSize, "",Summary.SUMMARY_VIDEO+"");
			}
			videoRequest.setVersion(HttpParam.PLATFORM);
			videoRequest.setPackageVersion(AppUtils.getAppVersionName(mContext));
			videoRequest.setGameCode(HttpParam.PLATFORM_CODE);
			videoRequest.setReqType(IPlatformRequest.REQ_SUMMARY_LIST_VIDEO);
			return new BaseRequestBean[] { videoRequest };
		}
	}
	
	private SummaryListRequest createRequest(int requestType,int currentPage,String type){
		++currentPage;
		SummaryListRequest request = null;
		if(requestType == IPlatformRequest.REQ_SUMMARY_LIST_VIDEO){
			if(IPlatApplication.get().getUser() != null){
				request = new SummaryListRequest(HttpParam.PLATFORM_AREA,currentPage+"", mPageSize, IPlatApplication.get().getUser().getUid(),type);
			}else{
				request = new SummaryListRequest(HttpParam.PLATFORM_AREA,currentPage+"", mPageSize, "",type);
			}
		}else{
			request = new SummaryListRequest(HttpParam.PLATFORM_AREA,currentPage+"", mPageSize, "",type);
		}
		request.setVersion(HttpParam.PLATFORM);
		request.setPackageVersion(AppUtils.getAppVersionName(mContext));
		request.setReqType(requestType);
		request.setGameCode(HttpParam.PLATFORM_CODE);
		return request;
	}

	@Override
	public void initTitle(TitleView titleView) {
		titleView.setTitleRightStatus(View.GONE);
		titleView.setTitleCenterRes(E_string.efun_pd_summary, false);
	}


	@Override
	public void init(Bundle bundle) {
		if (bundle != null) {
			mComeFrom = bundle.getInt(Summary.SUMMARY_GO_KEY);
		}
		mAdapter = new SummaryAdapter(mContext);
		mVideoAdapter = new VideoAdapter(mContext);
		
		mVideoAdapter.setOnClickLinstener(new OnEfunItemAttrsClickListener() {
			
			@Override
			public void onItemClick(int position, int type, BaseDataBean bean) {
				// TODO Auto-generated method stub
				switch (type) {
				case ClickButtonType.ZAN://點贊
					praisePosition = position;
					mSummaryItemBean = (SummaryItemBean)bean;
					Log.i("efun", "點贊：position:"+position+"title:"+((SummaryItemBean)bean).getTitle());
					UserUtils.needLogin(mContext, new OnLoginFinishListener() {
						
						@Override
						public void loginCompleted(boolean completed) {
							// TODO Auto-generated method stub
								requestWithDialog(new BaseRequestBean[]{createPraiseRequest(mSummaryItemBean.getId())}, mContext.getString(E_string.efun_pd_loading_msg_commend));
						}
					});
					break;

				case ClickButtonType.SHARE://分享
					Log.i("efun", "分享：position:"+position+"title:"+((SummaryItemBean)bean).getTitle());
					sharePop = PopUtils.createShare(mContext, mListView, bean);
					sharePop.showPopWindow(PopWindow.POP_WINDOW_SHARE);
					break;
				}
			}
		});
		mListView.setPullLoadEnable(true);
		mListView.setXListViewListener(new IXListViewListener() {
			@Override
			public void onRefresh() {
				if(mComeFrom != Summary.SUMMARY_VIDEO){
					requestWithoutDialog(new BaseRequestBean[]{
							createRequest(IPlatformRequest.REQ_SUMMARY_LIST_NEWS,-1,mComeFrom+"")});
				}else{
					requestWithoutDialog(new BaseRequestBean[]{
							createRequest(IPlatformRequest.REQ_SUMMARY_LIST_VIDEO,-1,mComeFrom+"")});
				}
			}

			@Override
			public void onLoadMore() {
				if(mComeFrom != Summary.SUMMARY_VIDEO){
					requestWithoutDialog(new BaseRequestBean[]{
							createRequest(IPlatformRequest.REQ_SUMMARY_LIST_NEWS,mCurrentPage,mComeFrom+"")});
				}else{
					requestWithoutDialog(new BaseRequestBean[]{
							createRequest(IPlatformRequest.REQ_SUMMARY_LIST_VIDEO,mCurrentPage,mComeFrom+"")});
				}
			}
		});
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(final AdapterView<?> parent,final  View view,
					final int position, long id) {
				//各个游戏子项点击处理事件		
				if(parent.getAdapter()!=null){
					if(mComeFrom != Summary.SUMMARY_VIDEO){
						SummaryItemBean bean = (SummaryItemBean) parent.getAdapter().getItem(position);
						IntentUtils.go2Web(mContext, Web.WEB_GO_SUMMARY,bean);
					}else{
						SummaryItemBean mVideoBean = (SummaryItemBean) parent.getAdapter().getItem(position);
						IntentUtils.go2VideoWeb(mContext, Web.WEB_GO_SUMMARY_LIST,mVideoBean);
					}
				}
			}
		});
	}
	@Override
	public void onSuccess(int requestType, BaseResponseBean baseResponse) {
		super.onSuccess(requestType,baseResponse);
		if (requestType==IPlatformRequest.REQ_SUMMARY_LIST_NEWS){
			SummaryListResponse bean = (SummaryListResponse) baseResponse;
			SummaryListRequest reqBean = (SummaryListRequest) bean.getRequestBean();
			if(reqBean.getCurrentPage().equals("1")){
				mAdapter.refresh(bean.getSummaryList());
				mCurrentPage=1;
				mListView.stopRefresh();
			}else{
				mAdapter.append(bean.getSummaryList());
				mCurrentPage++;
				mListView.stopLoadMore();
			}
			if(bean.getPageInfoBean().getPageIndex()==bean.getPageInfoBean().getTotalPage()){
				mListView.setPullLoadEnable(false);
			}else{
				mListView.setPullLoadEnable(true);
			}
		}else if (requestType==IPlatformRequest.REQ_SUMMARY_LIST_VIDEO) {
			SummaryListResponse bean = (SummaryListResponse) baseResponse;
			SummaryListRequest reqBean = (SummaryListRequest) bean.getRequestBean();
			if(reqBean.getCurrentPage().equals("1")){
				mVideoAdapter.refresh(bean.getSummaryList());
				mCurrentPage=1;
				mListView.stopRefresh();
			}else{
				mVideoAdapter.append(bean.getSummaryList());
				mCurrentPage++;
				mListView.stopLoadMore();
			}
			if(bean.getPageInfoBean().getPageIndex()==bean.getPageInfoBean().getTotalPage()){
				mListView.setPullLoadEnable(false);
			}else{
				mListView.setPullLoadEnable(true);
			}
		}else if(requestType==IPlatformRequest.REQ_SUMMARY_PRAISE){
			SummaryPraiseResponse response = (SummaryPraiseResponse) baseResponse;
			SummaryPraiseBean bean = response.getSummaryPraiseBean();
			if (bean.getCode().equals(HttpParam.RESULT_1000)) {
				mSummaryItemBean.setLikes(mSummaryItemBean.getLikes() + 1);
				mSummaryItemBean.setIsLiked(1);
				mVideoAdapter.refreshByOne(praisePosition, mSummaryItemBean);//刷新数据
			} else if (bean.getCode().equals(HttpParam.RESULT_1100)) {
			}
			ToastUtils.toast(mContext, bean.getMessage());
		}
	}
	
	/**
	 * 资讯点赞
	 * @return
	 */
	private SummaryPraiseRequest createPraiseRequest(long nid){
		SummaryPraiseRequest request = new SummaryPraiseRequest(
				IPlatApplication.get().getUser().getUid(), 
				nid+"", 
				HttpParam.PLATFORM_AREA, 
				IPlatApplication.get().getUser().getSign(), 
				IPlatApplication.get().getUser().getTimestamp(), 
				HttpParam.PLATFORM,
				AppUtils.getAppVersionName(mContext),
				HttpParam.LANGUAGE, 
				HttpParam.APP);
		request.setReqType(IPlatformRequest.REQ_SUMMARY_PRAISE);
		return request;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == EfunFBFunctionActivity.EXTRA_SHARE_DIALOG_FLAG) {
			if(resultCode == EfunFBFunctionActivity.EXTRA_SUCCESS) {
				ToastUtils.toast(mContext, E_string.efun_pd_share_success);
			} else {
				ToastUtils.toast(mContext, E_string.efun_pd_share_failure);
			}
			
		}
	}
}
