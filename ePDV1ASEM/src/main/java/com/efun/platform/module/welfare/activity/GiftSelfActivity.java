package com.efun.platform.module.welfare.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;

import com.efun.platform.AndroidScape.E_string;
import com.efun.platform.IPlatApplication;
import com.efun.platform.http.dao.impl.IPlatformRequest;
import com.efun.platform.http.request.bean.BaseRequestBean;
import com.efun.platform.http.request.bean.GiftSelfListRequest;
import com.efun.platform.http.response.bean.BaseResponseBean;
import com.efun.platform.http.response.bean.GiftSelfListResponse;
import com.efun.platform.module.base.ElasticityActivity;
import com.efun.platform.module.welfare.adapter.GiftSelfAdapter;
import com.efun.platform.utils.Const.HttpParam;
import com.efun.platform.widget.TitleView;
import com.efun.platform.widget.list.XListView.IXListViewListener;
/**
 * 我的序列号中心
 * @author itxuxxey
 *
 */
public class GiftSelfActivity extends ElasticityActivity{
	
	private int currentPage = 0;
	private String pageSize = "10";
	private GiftSelfAdapter mAdapter;
	@Override
	public View[] addHeaderViews() {
		return null;
	}

	@Override
	public void init(Bundle bundle) {
		mAdapter =  new GiftSelfAdapter(mContext);
		mListView.setPullLoadEnable(true);
		mListView.setXListViewListener(new GiftSelfXListViewListener());
	}

	
	@Override
	public BaseAdapter adapter() {
		return mAdapter;
	}

	@Override
	public boolean needRequestData() {
		return true;
	}
	@Override
	public void onSuccess(int requestType, BaseResponseBean baseResponse) {
		super.onSuccess(requestType,baseResponse);
		if (requestType==IPlatformRequest.REQ_GIFT_SELF_LIST) {
			GiftSelfListResponse response = (GiftSelfListResponse)baseResponse;
			GiftSelfListRequest request = (GiftSelfListRequest) response.getRequestBean();
			if(request.getCurrentPage().equals("0")){
				mAdapter.refresh(response.getGiftSelfList());
				currentPage=0;
			}else{
				mAdapter.append(response.getGiftSelfList());
				currentPage++;
			}
			if(response.getPageInfoBean().getPageIndex()==response.getPageInfoBean().getTotalPage()-1){
				mListView.setPullLoadEnable(false);
			}else{
				mListView.setPullLoadEnable(true);
			}
//			updateGiftSelfStatus();
		}
//		else if(requestType==IPlatformRequest.REQ_GIFT_SELF_STATUS){
//			allow = false;
//			GiftSelfStatusResponse response = (GiftSelfStatusResponse)baseResponse;
//			GiftSelfStatusBean mGiftSelfStatusBean = response.getGiftSelfStatusBean();
//			if(mGiftSelfStatusBean.getCode().equals(HttpParam.RESULT_1000)){
//				IPlatApplication.get().setNewStatusOfGiftSelf(false);
//			}
//		}
	}

	@Override
	public BaseRequestBean[] needRequestDataBean() {
		GiftSelfListRequest selfRequest = new GiftSelfListRequest(currentPage+"", pageSize, IPlatApplication.get().getUser().getAccountName(),HttpParam.PHONE,HttpParam.PLATFORM_AREA);
		selfRequest.setReqType(IPlatformRequest.REQ_GIFT_SELF_LIST);
		if(IPlatApplication.get().getUser()!=null){
			selfRequest.setSign(IPlatApplication.get().getUser().getSign());
			selfRequest.setTimestamp(IPlatApplication.get().getUser().getTimestamp());
		}
		return new BaseRequestBean[]{selfRequest};
	}
	private boolean allow = true;
//	private void updateGiftSelfStatus(){
//		if(IPlatApplication.get().isNewStatusOfGiftSelf() && allow){
//			GiftSelfStatusRequest request = new GiftSelfStatusRequest(
//					IPlatApplication.get().getUser().getUid(), 
//					HttpParam.GIFT_STATUS_UPDATE);
//			request.setSign(IPlatApplication.get().getUser().getSign());
//			request.setTimestamp(IPlatApplication.get().getUser().getTimestamp());
//			request.setReqType(IPlatformRequest.REQ_GIFT_SELF_STATUS);
//			requestWithoutDialog(new BaseRequestBean[]{request});
//		}
//	}
	
	private GiftSelfListRequest createRequest(int currentPage){
		++currentPage;
		GiftSelfListRequest request = new GiftSelfListRequest(currentPage+"", pageSize, IPlatApplication.get().getUser().getAccountName(),HttpParam.PHONE,HttpParam.PLATFORM_AREA);
		if(IPlatApplication.get().getUser()!=null){
			request.setSign(IPlatApplication.get().getUser().getSign());
			request.setTimestamp(IPlatApplication.get().getUser().getTimestamp());
		}
		request.setReqType(IPlatformRequest.REQ_GIFT_SELF_LIST);
		return request;
	}
	class GiftSelfXListViewListener implements IXListViewListener {
		public GiftSelfXListViewListener() {
		}

		@Override
		public void onRefresh() {
			requestWithoutDialog(new BaseRequestBean[]{createRequest(-1)});
		}

		@Override
		public void onLoadMore() {
			requestWithoutDialog(new BaseRequestBean[]{createRequest(currentPage)});
		}

	}
	@Override
	public void initTitle(TitleView titleView) {
		titleView.setTitleRightStatus(View.GONE);
		titleView.setTitleCenterRes(E_string.efun_pd_code_center, false);
	}
	
}
