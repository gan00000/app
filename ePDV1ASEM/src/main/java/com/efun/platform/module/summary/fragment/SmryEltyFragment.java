package com.efun.platform.module.summary.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.efun.core.tools.EfunStringUtil;
import com.efun.platform.AndroidScape.E_id;
import com.efun.platform.AndroidScape.E_layout;
import com.efun.platform.AndroidScape.E_string;
import com.efun.platform.http.dao.impl.IPlatformRequest;
import com.efun.platform.http.request.bean.BaseRequestBean;
import com.efun.platform.http.request.bean.SummaryHomeRequest;
import com.efun.platform.http.response.bean.BaseResponseBean;
import com.efun.platform.http.response.bean.SummaryHomeResponse;
import com.efun.platform.image.ImageManager;
import com.efun.platform.module.base.ElasticityFragment;
import com.efun.platform.module.base.impl.OnEfunItemClickListener;
import com.efun.platform.module.bean.ConfigInfoBean;
import com.efun.platform.module.summary.activity.SummaryListActivity;
import com.efun.platform.module.summary.adapter.BannerAdapter;
import com.efun.platform.module.summary.bean.SummaryHomeBean;
import com.efun.platform.module.summary.bean.VideoBean;
import com.efun.platform.module.summary.view.SummaryContainer;
import com.efun.platform.module.utils.AppUtils;
import com.efun.platform.module.utils.IntentUtils;
import com.efun.platform.module.utils.ProcessDatasUtils;
import com.efun.platform.module.utils.ToastUtils;
import com.efun.platform.module.utils.TrackingGoogleUtils;
import com.efun.platform.module.utils.ViewUtils;
import com.efun.platform.utils.Const.HttpParam;
import com.efun.platform.utils.Const.Summary;
import com.efun.platform.utils.Const.Web;
import com.efun.platform.widget.ZoomPointContainer;
import com.efun.platform.widget.list.XListView.IXListViewListener;

/**
 * 资讯首页
 * 
 * @author itxuxxey
 * 
 */
public class SmryEltyFragment extends ElasticityFragment {
	/**
	 * 广告位{@link ViewPager} 
	 */
	private ViewPager mBannerViewPager;
	/**
	 * 适配器{@link BannerAdapter}
	 */
	private BannerAdapter mBannerAdapter;
	/**
	 * 排列圆点容器 {@link ZoomPointContainer}
	 */
	private ZoomPointContainer mZoomPointContainer;
	/**
	 * 资讯列表 {@link SummaryContainer}
	 */
	private SummaryContainer mSummaryContainer;
	/**
	 * 更多按钮 moreBlue
	 */
	private TextView moreGray,mVideoTitle;
	
	private RelativeLayout mVideo,mVideoParent;
	
	private ImageView mVideoImg;
	
	private LinearLayout fbWebLayout,govWebLayout,downloadLayout,
						guideBtnLayout,noticeBtnLayout,activityBtnLayout,videoBtnLayout,jobBtnLayout,partnerBtnLayout;
	
	private ConfigInfoBean fbWebBean,govWebBean,downloadWebBean,guideInfoBean,
			noticeInfoBean,activityInfoBean,videoInfoBean,jobInfoBean,partnerInfoBean;
	
	@Override
	public View[] addHeaderViews() {
		View view = ViewUtils.createView(getActivity(), E_layout.efun_pd_summary_content);
		return new View[] { view };
	}

	@Override
	public void init(Bundle bundle) {
		mBannerViewPager = (ViewPager) mViews[0].findViewById(E_id.pager_view);
		mZoomPointContainer = (ZoomPointContainer) mViews[0].findViewById(E_id.zoom_point_parent);
		mSummaryContainer = (SummaryContainer) mViews[0].findViewById(E_id.summary_container);
		moreGray = (TextView) mViews[0].findViewById(E_id.more_gray);
		mVideo = (RelativeLayout) mViews[0].findViewById(E_id.item_video);
		mVideoParent = (RelativeLayout) mViews[0].findViewById(E_id.item_video_parent);
		mVideoTitle = (TextView) mViews[0].findViewById(E_id.video_title);
		mVideoImg = (ImageView) mViews[0].findViewById(E_id.video_img);
		fbWebLayout = (LinearLayout) mViews[0].findViewById(E_id.summary_fb);
		govWebLayout = (LinearLayout) mViews[0].findViewById(E_id.summary_govweb);
		downloadLayout = (LinearLayout) mViews[0].findViewById(E_id.summary_download);
		guideBtnLayout = (LinearLayout) mViews[0].findViewById(E_id.summary_guide);
		noticeBtnLayout = (LinearLayout) mViews[0].findViewById(E_id.summary_notice);
		activityBtnLayout = (LinearLayout) mViews[0].findViewById(E_id.summary_activity);
		videoBtnLayout = (LinearLayout) mViews[0].findViewById(E_id.summary_video);
		jobBtnLayout = (LinearLayout) mViews[0].findViewById(E_id.summary_job);
		partnerBtnLayout = (LinearLayout) mViews[0].findViewById(E_id.summary_partner);

		mListView.setPullLoadEnable(true);
		mListView.setPullLoadEnable(false);
		
		mSummaryContainer.setItemLayout(E_layout.efun_pd_summary_list_item);

		mListView.setXListViewListener(new IXListViewListener() {
			@Override
			public void onRefresh() {
				requestWithoutDialog(needRequestDataBean());
			}
			@Override
			public void onLoadMore() {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	@Override
	public void initDatas() {
		// TODO Auto-generated method stub
		super.initDatas();
		fbWebBean = ProcessDatasUtils.getAPPFirstPageInfo(ProcessDatasUtils.TYPE_APPFIRSTPAGE_FB);
		govWebBean = ProcessDatasUtils.getAPPFirstPageInfo(ProcessDatasUtils.TYPE_APPFIRSTPAGE_GOVWEB);
		downloadWebBean = ProcessDatasUtils.getAPPFirstPageInfo(ProcessDatasUtils.TYPE_APPFIRSTPAGE_DOWNLOAD);
		guideInfoBean = ProcessDatasUtils.getAPPFirstPageInfo(ProcessDatasUtils.TYPE_APPFIRSTPAGE_GUIDE);
		noticeInfoBean = ProcessDatasUtils.getAPPFirstPageInfo(ProcessDatasUtils.TYPE_APPFIRSTPAGE_NOTICE);
		activityInfoBean = ProcessDatasUtils.getAPPFirstPageInfo(ProcessDatasUtils.TYPE_APPFIRSTPAGE_ACTIVITY);
		videoInfoBean = ProcessDatasUtils.getAPPFirstPageInfo(ProcessDatasUtils.TYPE_APPFIRSTPAGE_VIDEO);
		jobInfoBean = ProcessDatasUtils.getAPPFirstPageInfo(ProcessDatasUtils.TYPE_APPFIRSTPAGE_JOB);
		partnerInfoBean = ProcessDatasUtils.getAPPFirstPageInfo(ProcessDatasUtils.TYPE_APPFIRSTPAGE_PARTNER);
	}
	
	@Override
	public void initListeners() {
		// TODO Auto-generated method stub
		super.initListeners();
		moreGray.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
			}
		});
		
		fbWebLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(fbWebBean != null){
					if(!EfunStringUtil.isEmpty(fbWebBean.getUrl())){
						IntentUtils.go2WithJSWeb(getContext(), fbWebBean.getUrl());
					}else{
						ToastUtils.toast(getContext(), E_string.efun_pd_toast_url_empty);
					}
				}else{
					ToastUtils.toast(getContext(), E_string.efun_pd_toast_url_empty);
				}
			}
		});
		
		govWebLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(govWebBean != null){
					if(!EfunStringUtil.isEmpty(govWebBean.getUrl())){
						IntentUtils.go2WithJSWeb(getContext(), govWebBean.getUrl());
					}else{
						ToastUtils.toast(getContext(), E_string.efun_pd_toast_url_empty);
					}
				}else{
					ToastUtils.toast(getContext(), E_string.efun_pd_toast_url_empty);
				}
			}
		});

		downloadLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(downloadWebBean != null){
					if(!EfunStringUtil.isEmpty(downloadWebBean.getUrl())){
						AppUtils.download(getContext(), downloadWebBean.getUrl());
					}else{
						ToastUtils.toast(getContext(), E_string.efun_pd_toast_url_empty);
					}
				}else{
					ToastUtils.toast(getContext(), E_string.efun_pd_toast_url_empty);
				}
			}
		});
		
		guideBtnLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				IntentUtils.go2SummaryList(getContext(), Summary.SUMMARY_STRATEGY);
			}
		});
		
		noticeBtnLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				IntentUtils.go2SummaryList(getContext(), Summary.SUMMARY_BULLETIN);
			}
		});

		activityBtnLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				IntentUtils.go2SummaryList(getContext(), Summary.SUMMARY_ACTIVITY);
			}
		});

		videoBtnLayout.setOnClickListener(new OnClickListener() {
	
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				IntentUtils.go2SummaryList(getContext(), Summary.SUMMARY_VIDEO);
			}
		});
		
		jobBtnLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(jobInfoBean != null){
					if(!EfunStringUtil.isEmpty(jobInfoBean.getUrl())){
						IntentUtils.go2WithJSWeb(getContext(), jobInfoBean.getUrl());
					}else{
						ToastUtils.toast(getContext(), E_string.efun_pd_toast_url_empty);
					}
				}else{
					ToastUtils.toast(getContext(), E_string.efun_pd_toast_url_empty);
				}
			}
		});
		
		partnerBtnLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(partnerInfoBean != null){
					if(!EfunStringUtil.isEmpty(partnerInfoBean.getUrl())){
						IntentUtils.go2WithJSWeb(getContext(), partnerInfoBean.getUrl());
					}else{
						ToastUtils.toast(getContext(), E_string.efun_pd_toast_url_empty);
					}
				}else{
					ToastUtils.toast(getContext(), E_string.efun_pd_toast_url_empty);
				}
			}
		});
		
	}

	@Override
	public void onSuccess(int requestType, BaseResponseBean baseResponse) {
		super.onSuccess(requestType,baseResponse);
		if (requestType==IPlatformRequest.REQ_SUMMARY_HOME){
			mListView.stopRefresh();
			SummaryHomeResponse response = (SummaryHomeResponse)baseResponse;
			final SummaryHomeBean bean = response.getSummaryHomeBean();
			mSummaryContainer.loadedData(bean.getSummaryArray());
			mSummaryContainer.setOnEfunItemClickListener(new OnEfunItemClickListener() {
				@Override
				public void onItemClick(int position) {
					IntentUtils.go2Web(getActivity(), Web.WEB_GO_SUMMARY,
							bean.getSummaryArray().get(position));
				}
			});
			
			if(mBannerAdapter==null){
				mBannerAdapter = new BannerAdapter(getActivity());
			}
			mBannerAdapter.refresh(bean.getBannerArray());
			mBannerAdapter.setOnEfunItemClickListener(new OnEfunItemClickListener() {
				@Override
				public void onItemClick(int position) {
					TrackingGoogleUtils.track(TrackingGoogleUtils.ACTION_BANNER, bean.getBannerArray().get(position).getTitle());
					IntentUtils.go2Web(getActivity(), Web.WEB_GO_BANNER,
							bean.getBannerArray().get(position));
				}
			});
			mBannerViewPager.setAdapter(mBannerAdapter);
			mBannerViewPager.setCurrentItem(0);
			
			new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				mBannerViewPager.setCurrentItem(1);
			}
			}, ZoomPointContainer.DELAYED_TIME);
			
			mZoomPointContainer.setCount(bean.getBannerArray().size());
			mZoomPointContainer.setItemLayout(E_layout.efun_pd_point);
			mZoomPointContainer.onInvalidate(0);
			mZoomPointContainer.setViewPager(mBannerViewPager);
			
			//video
			VideoBean mVideoBean = bean.getVideoBean();
			if(mVideoBean != null){
				mVideoParent.setVisibility(View.VISIBLE);
				mVideoTitle.setText(mVideoBean.getTitle());
				ImageManager.displayImage(mVideoBean.getVideoPic(), mVideoImg, ImageManager.IMAGE_RECTANGLE_H);
				mVideo.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						IntentUtils.go2VideoWeb(getActivity(), Web.WEB_GO_SUMMARY,bean.getVideoBean());
						
					}
				});
			}else{
				mVideoParent.setVisibility(View.GONE);
			}
			
		}
	}

	@Override
	public boolean needRequestData() {
		return true;
	}

	@Override
	public BaseRequestBean[] needRequestDataBean() {
		SummaryHomeRequest homeRequest = new SummaryHomeRequest(getActivity(),
				HttpParam.PLATFORM, HttpParam.PLATFORM_AREA, 
				"small", "");
		homeRequest.setGameCode(HttpParam.PLATFORM_CODE);
		homeRequest.setPackageVersion(AppUtils.getAppVersionName(getContext()));
		homeRequest.setQueryType(HttpParam.ABOUT);
		homeRequest.setReqType(IPlatformRequest.REQ_SUMMARY_HOME);
		return new BaseRequestBean[] { homeRequest };
	}

	@Override
	public BaseAdapter adapter() {
		return null;
	}
		
}
