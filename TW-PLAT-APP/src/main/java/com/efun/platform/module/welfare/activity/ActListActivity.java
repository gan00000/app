package com.efun.platform.module.welfare.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;

import com.efun.platform.AndroidScape.E_string;
import com.efun.platform.http.dao.impl.IPlatformRequest;
import com.efun.platform.http.request.bean.ActivityListRequest;
import com.efun.platform.http.request.bean.BaseRequestBean;
import com.efun.platform.http.response.bean.ActivityListResponse;
import com.efun.platform.http.response.bean.BaseResponseBean;
import com.efun.platform.module.base.ElasticityActivity;
import com.efun.platform.module.utils.IntentUtils;
import com.efun.platform.module.utils.TrackingUtils;
import com.efun.platform.module.welfare.adapter.ActAdapter;
import com.efun.platform.module.welfare.bean.ActItemBean;
import com.efun.platform.utils.Const.Web;
import com.efun.platform.widget.TitleView;
import com.efun.platform.widget.list.XListView.IXListViewListener;
/**
 * 活动列表
 * @author Jesse
 *
 */
public class ActListActivity  extends ElasticityActivity {

	
	private ActAdapter mAdapter;
	private int mCurrentPage = 1;
	private String mPageSize = "10";
	
	@Override
	public View[] addHeaderViews() {
		return null;
	}

	@Override
	public void init(Bundle bundle) {
		mAdapter = new ActAdapter(mContext);
		mListView.setPullLoadEnable(true);
		mListView.setXListViewListener(new IXListViewListener() {
			@Override
			public void onRefresh() {
				requestWithoutDialog(new BaseRequestBean[]{createRequest(0)});
//				new GetDataTask().execute();
			}

			@Override
			public void onLoadMore() {
				requestWithoutDialog(new BaseRequestBean[]{createRequest(mCurrentPage)});
//				new GetDataTask(true).execute();
			}
		});
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				//各个游戏子项点击处理事件	
				if(parent.getAdapter()!=null){
					ActItemBean bean = (ActItemBean) parent.getAdapter().getItem(position);
//					TrackingUtils.track(TrackingUtils.ACTION_WELFARE_ACTIVITY, bean.getActivityName());
					IntentUtils.go2Web(mContext, Web.WEB_GO_ACT, bean);
				}
			}
		});
	}


	@Override
	public boolean needRequestData() {
		return true;
	}

	@Override
	public BaseAdapter adapter() {
		return mAdapter;
	}

	@Override
	public void onSuccess(int requestType, BaseResponseBean baseResponse) {
		super.onSuccess(requestType,baseResponse);
		if (requestType==IPlatformRequest.REQ_ACT_LIST){
			ActivityListResponse response = (ActivityListResponse)baseResponse;
			ActivityListRequest request = (ActivityListRequest) response.getRequestBean();
			if(request.getCurrentPage().equals("1")){
				mAdapter.refresh(response.getActsBean());
				mCurrentPage=1;
			}else{
				mAdapter.append(response.getActsBean());
				mCurrentPage++;
			}
			if(response.getPageInfoBean().getPageIndex()==response.getPageInfoBean().getTotalPage()){
				mListView.setPullLoadEnable(false);
			}else{
				mListView.setPullLoadEnable(true);
			}
		}
	}
	
	
	@Override
	public BaseRequestBean[] needRequestDataBean() {
		ActivityListRequest actRequest = new ActivityListRequest("1", mPageSize, "102");
		actRequest.setReqType(IPlatformRequest.REQ_ACT_LIST);
		return new BaseRequestBean[] { actRequest };
	}

	
	private ActivityListRequest createRequest(int currentPage){
		++currentPage;
		ActivityListRequest request = new ActivityListRequest();
		request.setReqType(IPlatformRequest.REQ_ACT_LIST);
		request.setCurrentPage(""+currentPage);
		request.setPageSize(mPageSize);
		request.setIsPayactivity("102");
		return request;
	}

	@Override
	public void initTitle(TitleView titleView) {
		titleView.setTitleRightStatus(View.GONE);
		titleView.setTitleCenterRes(E_string.efun_pd_act, false);
	}
}