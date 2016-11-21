package com.efun.platform.module.welfare.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;

import com.efun.platform.http.dao.impl.IPlatformRequest;
import com.efun.platform.http.request.bean.BaseRequestBean;
import com.efun.platform.http.request.bean.WelfareListRequest;
import com.efun.platform.http.response.bean.BaseResponseBean;
import com.efun.platform.http.response.bean.WelfareListResponse;
import com.efun.platform.module.base.ElasticityFragment;
import com.efun.platform.module.utils.AppUtils;
import com.efun.platform.module.utils.IntentUtils;
import com.efun.platform.module.welfare.adapter.WelfareAdapter;
import com.efun.platform.module.welfare.bean.WelfareItemBean;
import com.efun.platform.module.welfare.bean.WelfareListBean;
import com.efun.platform.utils.Const.HttpParam;
import com.efun.platform.utils.Const.Web;
import com.efun.platform.widget.list.XListView.IXListViewListener;

/**
 * 福利內容頁面
 */
public class WelfareEltyFragment extends ElasticityFragment {
	private WelfareAdapter mdapter;

	@Override
	public View[] addHeaderViews() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void init(Bundle bundle) {
		// TODO Auto-generated method stub
		mdapter = new WelfareAdapter(getActivity());
		mListView.setPullLoadEnable(false);
		mListView.setPullRefreshEnable(true);
		mListView.setXListViewListener(new IXListViewListener() {
			
			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onLoadMore() {
				// TODO Auto-generated method stub
			}
		});
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent,
					View view, int position, long id) {
				// TODO Auto-generated method stub
				if (parent.getAdapter() != null) {
					WelfareItemBean bean = (WelfareItemBean) parent
							.getAdapter().getItem(position);
					IntentUtils.go2WithJSWeb(getActivity(), Web.WEB_GO_WELFARE_LIST, bean);
				}
			}
			
		});
	}
	
	@Override
	public void onSuccess(int requestType, BaseResponseBean baseResponse) {
		// TODO Auto-generated method stub
		super.onSuccess(requestType, baseResponse);
		if (requestType == IPlatformRequest.REQ_WELFARE_INFOS) {
			WelfareListResponse response = (WelfareListResponse) baseResponse;
			WelfareListBean bean = response.getWelFareList();
			if(bean.getCode().equals(HttpParam.RESULT_1000)){
				mdapter.refresh(bean.getmResponse());
			}
		}
	}

	@Override
	public BaseAdapter adapter() {
		// TODO Auto-generated method stub
		return mdapter;
	}

	@Override
	public boolean needRequestData() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public BaseRequestBean[] needRequestDataBean() {
		// TODO Auto-generated method stub
		WelfareListRequest request = new WelfareListRequest();
		request.setFromType(HttpParam.APP);
		request.setGameCode(HttpParam.PLATFORM_CODE);
		request.setLanguage(HttpParam.LANGUAGE);
		request.setPlatform(HttpParam.PLATFORM_AREA);
		request.setReqType(IPlatformRequest.REQ_WELFARE_INFOS);
		request.setVersion(HttpParam.PLATFORM);
		request.setPackageVersion(AppUtils.getAppVersionName(getContext()));
		return new BaseRequestBean[] { request };
	}
	
}
