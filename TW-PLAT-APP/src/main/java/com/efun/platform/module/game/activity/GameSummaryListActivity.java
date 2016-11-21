package com.efun.platform.module.game.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;

import com.efun.platform.http.dao.impl.IPlatformRequest;
import com.efun.platform.http.request.bean.BaseRequestBean;
import com.efun.platform.http.request.bean.SummaryListRequest;
import com.efun.platform.http.response.bean.BaseResponseBean;
import com.efun.platform.http.response.bean.SummaryListResponse;
import com.efun.platform.module.BaseDataBean;
import com.efun.platform.module.base.ElasticityActivity;
import com.efun.platform.module.game.bean.GameItemBean;
import com.efun.platform.module.summary.adapter.SummaryAdapter;
import com.efun.platform.module.summary.bean.SummaryItemBean;
import com.efun.platform.module.utils.IntentUtils;
import com.efun.platform.utils.Const;
import com.efun.platform.utils.Const.HttpParam;
import com.efun.platform.utils.Const.Summary;
import com.efun.platform.utils.Const.Web;
import com.efun.platform.widget.TitleView;
import com.efun.platform.widget.list.XListView.IXListViewListener;

public class GameSummaryListActivity extends ElasticityActivity {
	private SummaryAdapter mSummaryAdapter;
	private int mCurrentPage = 1;
	private String mPageSize = "10";
	private int type;
	private BaseDataBean mBean;
	private String mGameCode;
	private String mGameName;

	@Override
	public View[] addHeaderViews() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BaseAdapter adapter() {
		// TODO Auto-generated method stub
		return mSummaryAdapter;
	}

	@Override
	public void initTitle(TitleView titleView) {
		// TODO Auto-generated method stub
		titleView.setTitleRightStatus(View.GONE);
		titleView.setTitleCenterText(mGameName);
	}

	@Override
	public boolean needRequestData() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public BaseRequestBean[] needRequestDataBean() {
		// TODO Auto-generated method stub
		SummaryListRequest newsRequest = new SummaryListRequest(
				HttpParam.PLATFORM_AREA,
				mCurrentPage+"", mPageSize, type+"","","");
		newsRequest.setGameCode(mGameCode);
		newsRequest.setReqType(IPlatformRequest.REQ_SUMMARY_LIST_NEWS);
		return new BaseRequestBean[]{newsRequest};
	}

	@Override
	public void init(Bundle bundle) {
		// TODO Auto-generated method stub
		if (bundle != null) {
			type = bundle.getInt(Summary.SUMMARY_GO_KEY);
			if(bundle.getSerializable(Const.BEAN_KEY)!=null){
				mBean = (BaseDataBean) bundle.getSerializable(Const.BEAN_KEY);
				mGameCode = ((GameItemBean)mBean).getGameCode();
				mGameName = ((GameItemBean)mBean).getGameName();
			}
		}
		mSummaryAdapter = new SummaryAdapter(mContext);
		mListView.setPullLoadEnable(true);
		mListView.setXListViewListener(new IXListViewListener() {
			
			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				requestWithoutDialog(new BaseRequestBean[]{	createRequest(0,type+"")});
			}
			
			@Override
			public void onLoadMore() {
				// TODO Auto-generated method stub
				requestWithoutDialog(new BaseRequestBean[]{	createRequest(mCurrentPage,type+"")});
			}
		});
		
		mListView.setOnItemClickListener(new ItemClickListener());

	}
	
	private SummaryListRequest createRequest(int currentPage,String type){
		++currentPage;
		SummaryListRequest request = new SummaryListRequest(HttpParam.PLATFORM_AREA,currentPage+"", mPageSize, type,"","");
		request.setGameCode(mGameCode);
		request.setReqType(IPlatformRequest.REQ_SUMMARY_LIST_NEWS);
//		request.setReqType(requestType);
		return request;
	}
	
	@Override
	public void onSuccess(int requestType, BaseResponseBean baseResponse) {
		super.onSuccess(requestType,baseResponse);
		if (requestType==IPlatformRequest.REQ_SUMMARY_LIST_NEWS) {
			SummaryListResponse bean = (SummaryListResponse) baseResponse;
			SummaryListRequest reqBean = (SummaryListRequest) bean.getRequestBean();
			if(reqBean.getCurrentPage().equals("1")){
				mSummaryAdapter.refresh(bean.getSummaryList());
				mCurrentPage=1;
				mListView.stopRefresh();
			}else{
				mSummaryAdapter.append(bean.getSummaryList());
				mCurrentPage++;
				mListView.stopLoadMore();
			}
			if(bean.getPageInfoBean().getPageIndex()==bean.getPageInfoBean().getTotalPage()){
				mListView.setPullLoadEnable(false);
			}else{
				mListView.setPullLoadEnable(true);
			}
		}  
//		else if (requestType==IPlatformRequest.REQ_SUMMARY_LIST_STRATEGY) {
//			SummaryListResponse bean = (SummaryListResponse) baseResponse;
//			SummaryListRequest reqBean = (SummaryListRequest) bean.getRequestBean();
//			if(reqBean.getCurrentPage().equals("1")){
//				mStrategyAdapter.refresh(bean.getSummaryList());
//				curPageOfStrategy=1;
//				mStrategy.stopRefresh();
//			}else{
//				mStrategyAdapter.append(bean.getSummaryList());
//				curPageOfStrategy++;
//				mStrategy.stopLoadMore();
//			}
//			if(bean.getPageInfoBean().getPageIndex()==bean.getPageInfoBean().getTotalPage()){
//				mStrategy.setPullLoadEnable(false);
//			}else{
//				mStrategy.setPullLoadEnable(true);
//			}
//		}
	}
	
	class ItemClickListener implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			if(parent.getAdapter().getItem(position)!=null){
				SummaryItemBean bean = (SummaryItemBean) parent.getAdapter().getItem(position);
				IntentUtils.go2Web(mContext, Web.WEB_GO_SUMMARY,bean);
			}
		}
	}

}
