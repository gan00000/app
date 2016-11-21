package com.efun.platform.module.welfare.fragment;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;

import com.efun.core.tools.EfunLogUtil;
import com.efun.core.tools.EfunStringUtil;
import com.efun.platform.IPlatApplication;
import com.efun.platform.http.dao.impl.IPlatformRequest;
import com.efun.platform.http.request.bean.ActivityExtensionRequest;
import com.efun.platform.http.request.bean.BaseRequestBean;
import com.efun.platform.http.request.bean.GiftListRequest;
import com.efun.platform.http.response.bean.ActivityExtensionResponse;
import com.efun.platform.http.response.bean.BaseResponseBean;
import com.efun.platform.http.response.bean.GiftListResponse;
import com.efun.platform.module.base.ElasticityFragment;
import com.efun.platform.module.utils.AppUtils;
import com.efun.platform.module.utils.IntentUtils;
import com.efun.platform.module.utils.TrackingGoogleUtils;
import com.efun.platform.module.utils.TrackingUtils;
import com.efun.platform.module.welfare.activity.ActExtensionActivity;
import com.efun.platform.module.welfare.activity.GiftDetailActivity;
import com.efun.platform.module.welfare.adapter.ExtensionAdapter;
import com.efun.platform.module.welfare.bean.ActExtensionBean;
import com.efun.platform.module.welfare.bean.ExtensionBean;
import com.efun.platform.module.welfare.bean.GiftItemBean;
import com.efun.platform.utils.Const.HttpParam;

/**
 * 好康
 * 
 * @author Jesse
 * @author itxuxxey 20150617修整
 * 
 */
public class HaoKangEltyFragment extends ElasticityFragment {

	private ExtensionAdapter mAdapter;
	private ArrayList<ActExtensionBean> mExtensions;
	private ArrayList<GiftItemBean> mRecommendGifts;

	@Override
	public View[] addHeaderViews() {
		return null;
	}

	@Override
	public void init(Bundle bundle) {
		mAdapter = new ExtensionAdapter(getActivity());
		mListView.setPullLoadEnable(false);
		mListView.setPullRefreshEnable(false);
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(final AdapterView<?> parent,
					final View view, final int position, long id) {
				EfunLogUtil.logI("efun", "position:"+position);
				if(mExtensions != null && mRecommendGifts != null){
					if(mExtensions.size() != 0 && mRecommendGifts.size() != 0){
						if (position <= mExtensions.size()) {
							if(!EfunStringUtil.isEmpty(IPlatApplication.get().getUser().getPhone())){
								TrackingUtils.track(getContext(),TrackingUtils.ACTION_WELFARE, TrackingUtils.NAME_WELFARE_EXTENSION+"_"+mExtensions.get(position-1).getGameBean().getGameCode());
								TrackingGoogleUtils.track(TrackingGoogleUtils.ACTION_WELFARE, mExtensions.get(position-1).getGameBean().getGameName()+TrackingGoogleUtils.NAME_WELFARE_EXTENSION);
								ActExtensionBean bean = (ActExtensionBean) parent
										.getAdapter().getItem(position);
								IntentUtils.goWithBean(getActivity(),
										ActExtensionActivity.class, bean);
							}
						} else {
							TrackingUtils.track(getContext(),TrackingUtils.ACTION_WELFARE, TrackingUtils.NAME_WELFARE_GIFT+"_"+mRecommendGifts.get(position-mExtensions.size()-1).getGameCode());
							TrackingGoogleUtils.track(TrackingGoogleUtils.ACTION_WELFARE, mRecommendGifts.get(position-mExtensions.size()-1).getGameName()+TrackingGoogleUtils.NAME_WELFARE_GIFT);
							GiftItemBean bean = (GiftItemBean) parent.getAdapter()
									.getItem(position);
							IntentUtils.goWithBean(getActivity(),
									GiftDetailActivity.class, bean);
						}
					}else if(mExtensions.size() != 0 && mRecommendGifts.size() == 0){
						if(!EfunStringUtil.isEmpty(IPlatApplication.get().getUser().getPhone())){
							TrackingUtils.track(getContext(),TrackingUtils.ACTION_WELFARE, TrackingUtils.NAME_WELFARE_EXTENSION+"_"+mExtensions.get(position-1).getGameBean().getGameCode());
							TrackingGoogleUtils.track(TrackingGoogleUtils.ACTION_WELFARE, mExtensions.get(position-1).getGameBean().getGameName()+TrackingGoogleUtils.NAME_WELFARE_EXTENSION);
							ActExtensionBean bean = (ActExtensionBean) parent
									.getAdapter().getItem(position);
							IntentUtils.goWithBean(getActivity(),
									ActExtensionActivity.class, bean);
						}
					}else if(mExtensions.size() == 0 && mRecommendGifts.size() != 0){
						TrackingUtils.track(getContext(),TrackingUtils.ACTION_WELFARE, TrackingUtils.NAME_WELFARE_GIFT+"_"+mRecommendGifts.get(position-1).getGameCode());
						TrackingGoogleUtils.track(TrackingGoogleUtils.ACTION_WELFARE, mRecommendGifts.get(position-1).getGameName()+TrackingGoogleUtils.NAME_WELFARE_GIFT);
						GiftItemBean bean = (GiftItemBean) parent.getAdapter()
								.getItem(position);
						IntentUtils.goWithBean(getActivity(),
								GiftDetailActivity.class, bean);
					}
				}else if(mExtensions == null && mRecommendGifts != null){
					if(mRecommendGifts.size() != 0){
						TrackingUtils.track(getContext(),TrackingUtils.ACTION_WELFARE, TrackingUtils.NAME_WELFARE_GIFT+"_"+mRecommendGifts.get(position-1).getGameCode());
						TrackingGoogleUtils.track(TrackingGoogleUtils.ACTION_WELFARE, mRecommendGifts.get(position-1).getGameName()+TrackingGoogleUtils.NAME_WELFARE_GIFT);
						GiftItemBean bean = (GiftItemBean) parent.getAdapter()
								.getItem(position);
						IntentUtils.goWithBean(getActivity(),
								GiftDetailActivity.class, bean);
					}
				}else if(mExtensions != null && mRecommendGifts == null){
					if(mExtensions.size() != 0){
						if(!EfunStringUtil.isEmpty(IPlatApplication.get().getUser().getPhone())){
							TrackingUtils.track(getContext(),TrackingUtils.ACTION_WELFARE, TrackingUtils.NAME_WELFARE_EXTENSION+"_"+mExtensions.get(position-1).getGameBean().getGameCode());
							TrackingGoogleUtils.track(TrackingGoogleUtils.ACTION_WELFARE, mExtensions.get(position-1).getGameBean().getGameName()+TrackingGoogleUtils.NAME_WELFARE_EXTENSION);
							ActExtensionBean bean = (ActExtensionBean) parent
									.getAdapter().getItem(position);
							IntentUtils.goWithBean(getActivity(),
									ActExtensionActivity.class, bean);
						}
					}
				}
			}
		});
	}

	@Override
	public BaseAdapter adapter() {
		return mAdapter;
	}

	@Override
	public boolean needRequestData() {
		return false;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(IPlatApplication.get().getUser() != null){
			refreshView();
		}
	}

	@Override
	public BaseRequestBean[] needRequestDataBean() {
			ActivityExtensionRequest request = new ActivityExtensionRequest(
					HttpParam.PHONE, HttpParam.PLATFORM_AREA, HttpParam.PLATFORM);
			if (IPlatApplication.get().getUser() != null) {
				request.setUid(IPlatApplication.get().getUser().getUid());
				request.setSign(IPlatApplication.get().getUser().getSign());
				request.setTimestamp(IPlatApplication.get().getUser()
						.getTimestamp());
			}
			request.setLanguage(HttpParam.LANGUAGE);
			request.setPackageVersion(AppUtils.getAppVersionName(getActivity()));
			request.setReqType(IPlatformRequest.REQ_ACT_EXTENSION);
			
			GiftListRequest giftsRequest = new GiftListRequest(
					HttpParam.PLATFORM_AREA, "1", "1000");
			if (IPlatApplication.get().getUser() != null) {
				giftsRequest.setUid(IPlatApplication.get().getUser().getUid());
				giftsRequest.setSign(IPlatApplication.get().getUser().getSign());
				giftsRequest.setTimestamp(IPlatApplication.get().getUser()
						.getTimestamp());
			}
			giftsRequest.setReqType(IPlatformRequest.REQ_GIFT_LIST);
			giftsRequest.setVersion(HttpParam.PLATFORM);
			giftsRequest.setPackageVersion(AppUtils
					.getAppVersionName(getActivity()));
			giftsRequest.setLanguage(HttpParam.LANGUAGE);
			giftsRequest.setIsUnique("1");
			return new BaseRequestBean[] { request, giftsRequest };
	}

	@Override
	public void onSuccess(int requestType, BaseResponseBean response) {
		// TODO Auto-generated method stub
		super.onSuccess(requestType, response);
		if (requestType == IPlatformRequest.REQ_ACT_EXTENSION) {
			ActivityExtensionResponse mExtensionRes = (ActivityExtensionResponse) response;
			ExtensionBean mExtensionBean = mExtensionRes.getExtensionBean();
			if (mExtensionBean.getCode().equals(HttpParam.RESULT_1000)) {
				mExtensions = mExtensionBean.getActExtensionBeans();
				mAdapter.addExtensions(mExtensions);
			}

		} else if (requestType == IPlatformRequest.REQ_GIFT_LIST) {
			GiftListResponse mRecommendGiftRes = (GiftListResponse) response;
			mRecommendGifts = mRecommendGiftRes.getGiftsBean();
			if (mRecommendGifts.size() > 0) {
				mAdapter.addRecommondGifts(mRecommendGifts);
			}
		}
	}
	@Override
	public void onFailure(int requestType) {
		// TODO Auto-generated method stub
		super.onFailure(requestType);
		if (requestType == IPlatformRequest.REQ_ACT_EXTENSION) {
			EfunLogUtil.logI("efun", "mExtensions数据有误");
				mExtensions = null;
				mAdapter.addExtensions(mExtensions);
		} else if (requestType == IPlatformRequest.REQ_GIFT_LIST) {
			EfunLogUtil.logI("efun", "mRecommendGifts数据有误");
			mRecommendGifts = null;
			mAdapter.addRecommondGifts(mRecommendGifts);
		}
	}
	

	public void refreshView() {
		requestWithoutDialog(needRequestDataBean());
	}

}
