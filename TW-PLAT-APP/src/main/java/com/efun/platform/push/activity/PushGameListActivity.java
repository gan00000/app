package com.efun.platform.push.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.efun.platform.AndroidScape.E_id;
import com.efun.platform.AndroidScape.E_string;
import com.efun.platform.http.dao.impl.IPlatformRequest;
import com.efun.platform.http.request.bean.BaseRequestBean;
import com.efun.platform.http.request.bean.GameListRequest;
import com.efun.platform.http.response.bean.BaseResponseBean;
import com.efun.platform.http.response.bean.GameListResponse;
import com.efun.platform.module.PopWindow;
import com.efun.platform.module.base.ElasticityActivity;
import com.efun.platform.module.base.impl.OnUpdateListener;
import com.efun.platform.module.game.activity.GameDetailActivity;
import com.efun.platform.module.game.adapter.GameAdapter;
import com.efun.platform.module.game.bean.GameItemBean;
import com.efun.platform.module.utils.IntentUtils;
import com.efun.platform.utils.Const.HttpParam;
import com.efun.platform.widget.TitleView;
import com.efun.platform.widget.list.XListView.IXListViewListener;

public class PushGameListActivity extends ElasticityActivity{
	private GameAdapter mAdapter;
	private int mCurrentPage = 1;
	private String mPageSize = "10";
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
		return mAdapter;
	}

	@Override
	public BaseRequestBean[] needRequestDataBean() {
		GameListRequest request = new GameListRequest();
		request.setReqType(IPlatformRequest.REQ_GAME_LIST);
		request.setCurrentPage("1");
		request.setPageSize(mPageSize);
		request.setPlateform(HttpParam.PLATFORM_AREA);
		return new BaseRequestBean[] { request };
	}
	
	private GameListRequest createRequest(int currentPage){
		++currentPage;
		GameListRequest request = new GameListRequest();
		request.setReqType(IPlatformRequest.REQ_GAME_LIST);
		request.setCurrentPage(""+currentPage);
		request.setPageSize(mPageSize);
		request.setPlateform(HttpParam.PLATFORM_AREA);
		return request;
	}

	@Override
	public void initTitle(TitleView titleView) {
		titleView.setTitleCenterRes(E_string.efun_pd_game, false);
		titleView.setPopWindowEnable(PopWindow.POP_WINDOW_CODE,null);
	}


	@Override
	public void init(Bundle bundle) {
		mAdapter = new GameAdapter(this);
		mListView.setPullLoadEnable(true);
		mListView.setXListViewListener(new IXListViewListener() {
			@Override
			public void onRefresh() {
				requestWithoutDialog(new BaseRequestBean[]{createRequest(0)});
			}

			@Override
			public void onLoadMore() {
				requestWithoutDialog(new BaseRequestBean[]{createRequest(mCurrentPage)});
			}
		});
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(final AdapterView<?> parent,final  View view,
					final int position, long id) {
				//各个游戏子项点击处理事件		
				if(parent.getAdapter()!=null){
					final GameItemBean bean = (GameItemBean) parent.getAdapter().getItem(position);
					IntentUtils.goWithBeanForResult(PushGameListActivity.this, GameDetailActivity.class, bean,new OnUpdateListener() {
						@Override
						public void onUpdate() {
							int visiblePosition = mListView.getFirstVisiblePosition();  
				            View view = mListView.getChildAt(position - visiblePosition);  
				            View v =  mListView.getAdapter().getView(position, view, mListView);  
							TextView text = (TextView) v.findViewById(E_id.item_count);
							text.setText((bean.getLike()+1)+ PushGameListActivity.this.getString(E_string.efun_pd_game_item_zan));
						}
					});
				}
			}
		});
	}
	@Override
	public void onSuccess(int requestType, BaseResponseBean baseResponse) {
		super.onSuccess(requestType,baseResponse);
		if (requestType==IPlatformRequest.REQ_GAME_LIST){
			GameListResponse bean = (GameListResponse) baseResponse;
			GameListRequest reqBean = (GameListRequest) bean.getRequestBean();
			if(reqBean.getCurrentPage().equals("1")){
				mAdapter.refresh(bean.getGameList());
				mCurrentPage=1;
				mListView.stopRefresh();
			}else{
				mAdapter.append(bean.getGameList());
				mCurrentPage++;
				mListView.stopLoadMore();
			}
			if(bean.getPageInfoBean().getPageIndex()==bean.getPageInfoBean().getTotalPage()){
				mListView.setPullLoadEnable(false);
			}else{
				mListView.setPullLoadEnable(true);
			}
		}
	}
}
