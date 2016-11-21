package com.efun.platform.module.summary.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;

import com.efun.platform.AndroidScape.E_array;
import com.efun.platform.AndroidScape.E_color;
import com.efun.platform.AndroidScape.E_drawable;
import com.efun.platform.AndroidScape.E_id;
import com.efun.platform.AndroidScape.E_layout;
import com.efun.platform.AndroidScape.E_string;
import com.efun.platform.http.dao.impl.IPlatformRequest;
import com.efun.platform.http.request.bean.BaseRequestBean;
import com.efun.platform.http.request.bean.SummaryListRequest;
import com.efun.platform.http.response.bean.BaseResponseBean;
import com.efun.platform.http.response.bean.SummaryListResponse;
import com.efun.platform.module.VPagerAdapter;
import com.efun.platform.module.base.FixedActivity;
import com.efun.platform.module.summary.adapter.SummaryAdapter;
import com.efun.platform.module.summary.adapter.VideoAdapter;
import com.efun.platform.module.summary.bean.SummaryItemBean;
import com.efun.platform.module.utils.IntentUtils;
import com.efun.platform.module.utils.ViewUtils;
import com.efun.platform.utils.Const.HttpParam;
import com.efun.platform.utils.Const.Web;
import com.efun.platform.widget.PagerTab;
import com.efun.platform.widget.TitleView;
import com.efun.platform.widget.list.XListView;
import com.efun.platform.widget.list.XListView.IXListViewListener;

/**
 * 資訊列表
 * 
 * @author Jesse
 * 
 */
public class SummaryListActivityOld extends FixedActivity {

	/**
	 * 不同分类的 {@link XListView}
	 */
	private XListView mAll, mNews, mBulletin, mActivity, mStrategy,mVideos;
	/**
	 * 不同分类的 {@link SummaryAdapter}
	 */
	private SummaryAdapter mAllAdapter, mNewsAdapter, mBulletinAdapter,
			mActivityAdapter, mStrategyAdapter;
	private VideoAdapter mVideoAdapter;
	private PagerTab mPagerTabs;
	private ViewPager mViewPager;
	private ViewGroup[] groups = new ViewGroup[6];
	private final String pageSize = "10";
	/**
	 * 全部
	 */
	private final String SUMMARY_ALL= "";
	private int curPageOfAll = 1;
	/**
	 * 新闻
	 */
	private final String SUMMARY_NEWS= "1";
	private int curPageOfNews= 1;
	/**
	 * 公告
	 */
	private final String SUMMARY_BULLETIN= "0";
	private int curPageOfBulletin= 1;
	/**
	 * 活动
	 */
	private final String SUMMARY_ACTIVITY= "4";
	private int curPageOfActivity= 1;
	/**
	 * 攻略
	 */
	private final String SUMMARY_STRATEGY= "2";
	private int curPageOfStrategy= 1;
	/**
	 * 视频
	 */
	private final String SUMMARY_VIDEO= "7";
	private int curPageOfVideo= 1;
	@Override
	public boolean needRequestData() {
		return true;
	}

	@Override
	public BaseRequestBean[] needRequestDataBean() {
		SummaryListRequest allRequest = new SummaryListRequest(
				HttpParam.PLATFORM_AREA,
				curPageOfAll+"", pageSize, SUMMARY_ALL,"","");
		allRequest.setReqType(IPlatformRequest.REQ_SUMMARY_LIST_ALL);
		SummaryListRequest newsRequest = new SummaryListRequest(
				HttpParam.PLATFORM_AREA,
				curPageOfNews+"", pageSize, SUMMARY_NEWS,"","");
		newsRequest.setReqType(IPlatformRequest.REQ_SUMMARY_LIST_NEWS);
		SummaryListRequest bulletinRequest = new SummaryListRequest(
				HttpParam.PLATFORM_AREA,
				curPageOfBulletin+"", pageSize, SUMMARY_BULLETIN,"","");
		bulletinRequest.setReqType(IPlatformRequest.REQ_SUMMARY_LIST_BULLETIN);
		SummaryListRequest activityRequest = new SummaryListRequest(
				HttpParam.PLATFORM_AREA,
				curPageOfActivity+"", pageSize, SUMMARY_ACTIVITY,"","");
		activityRequest.setReqType(IPlatformRequest.REQ_SUMMARY_LIST_ACTIVITY);
		SummaryListRequest strategyRequest = new SummaryListRequest(
				HttpParam.PLATFORM_AREA,
				curPageOfStrategy+"", pageSize, SUMMARY_STRATEGY,"","");
		strategyRequest.setReqType(IPlatformRequest.REQ_SUMMARY_LIST_STRATEGY);
		SummaryListRequest videoRequest = new SummaryListRequest(
				HttpParam.PLATFORM_AREA,
				curPageOfVideo+"", pageSize, SUMMARY_VIDEO,"","");
		videoRequest.setReqType(IPlatformRequest.REQ_SUMMARY_LIST_VIDEO);
		return new BaseRequestBean[] { allRequest, newsRequest, bulletinRequest, activityRequest, strategyRequest, videoRequest };
	}

	@Override
	public void init(Bundle bundle) {
		mAllAdapter = new SummaryAdapter(mContext);
		mNewsAdapter = new SummaryAdapter(mContext);
		mBulletinAdapter = new SummaryAdapter(mContext);
		mActivityAdapter = new SummaryAdapter(mContext);
		mStrategyAdapter = new SummaryAdapter(mContext);
		mVideoAdapter = new VideoAdapter(mContext);

		mViewPager = (ViewPager) findViewById(E_id.pager_view_v4);
		mPagerTabs = (PagerTab) findViewById(E_id.pager_view_tab);

		VPagerAdapter mAdapter = new VPagerAdapter(groups);
		mViewPager.setAdapter(mAdapter);

		mPagerTabs.setTab(E_layout.efun_pd_pager_tab_textview);
		mPagerTabs.setTabSelectedColor(E_color.e_5aa9ff);
		mPagerTabs.setTitles(E_array.efun_pd_summary_v_tab);
		mPagerTabs.setLine(E_drawable.efun_pd_blue_line_each);
		mPagerTabs.setPagerAdapter(mViewPager);
		mPagerTabs.setSelectedItem(mViewPager, 0);
	}

	@Override
	public ViewGroup[] needShowLoading() {
		ViewGroup groupAll = new FrameLayout(mContext);
		mAll = ViewUtils.createListView2(mContext);
		mAll.addHeaderView(createMargin());
		mAll.setAdapter(mAllAdapter);
		mAll.setOnItemClickListener(new ItemClickListener());
		mAll.setXListViewListener(new SummaryXListViewListener(SUMMARY_ALL));
		groupAll.addView(mAll, LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
		groups[0] = groupAll;

		ViewGroup groupNews = new FrameLayout(mContext);
		mNews = ViewUtils.createListView2(mContext);
		mNews.addHeaderView(createMargin());
		mNews.setAdapter(mNewsAdapter);
		mNews.setOnItemClickListener(new ItemClickListener());
		mNews.setXListViewListener(new SummaryXListViewListener(SUMMARY_NEWS));
		groupNews.addView(mNews, LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
		groups[1] = groupNews;

		ViewGroup groupBulletin = new FrameLayout(mContext);
		mBulletin = ViewUtils.createListView2(mContext);
		mBulletin.addHeaderView(createMargin());
		mBulletin.setAdapter(mBulletinAdapter);
		mBulletin.setOnItemClickListener(new ItemClickListener());
		mBulletin.setXListViewListener(new SummaryXListViewListener(SUMMARY_BULLETIN));
		groupBulletin.addView(mBulletin, LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
		groups[2] = groupBulletin;

		ViewGroup groupActivity = new FrameLayout(mContext);
		mActivity = ViewUtils.createListView2(mContext);
		mActivity.addHeaderView(createMargin());
		mActivity.setAdapter(mActivityAdapter);
		mActivity.setOnItemClickListener(new ItemClickListener());
		mActivity.setXListViewListener(new SummaryXListViewListener(SUMMARY_ACTIVITY));
		groupActivity.addView(mActivity, LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
		groups[3] = groupActivity;

		ViewGroup groupStrategy = new FrameLayout(mContext);
		mStrategy = ViewUtils.createListView2(mContext);
		mStrategy.addHeaderView(createMargin());
		mStrategy.setAdapter(mStrategyAdapter);
		mStrategy.setOnItemClickListener(new ItemClickListener());
		mStrategy.setXListViewListener(new SummaryXListViewListener(SUMMARY_STRATEGY));
		groupStrategy.addView(mStrategy, LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
		groups[4] = groupStrategy;
		
		ViewGroup groupVideo = new FrameLayout(mContext);
		mVideos = ViewUtils.createListView2(mContext);
		mVideos.addHeaderView(createMargin());
		mVideos.setAdapter(mVideoAdapter);
		mVideos.setOnItemClickListener(new ItemClickListener());
		mVideos.setXListViewListener(new SummaryXListViewListener(SUMMARY_VIDEO));
		groupVideo.addView(mVideos, LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
		groups[5] = groupVideo;
		return groups;
	}
	public View createMargin(){
		return ViewUtils.createMargin(mContext);
	}
	@Override
	public void onSuccess(int requestType, BaseResponseBean baseResponse) {
		super.onSuccess(requestType,baseResponse);
		if (requestType==IPlatformRequest.REQ_SUMMARY_LIST_ALL) {
			SummaryListResponse bean = (SummaryListResponse)baseResponse;
			SummaryListRequest reqBean = (SummaryListRequest) bean.getRequestBean();
			if(reqBean.getCurrentPage().equals("1")){
				mAllAdapter.refresh(bean.getSummaryList());
				curPageOfAll=1;
				mAll.stopRefresh();
			}else{
				mAllAdapter.append(bean.getSummaryList());
				curPageOfAll++;
				mAll.stopLoadMore();
			}
			if(bean.getPageInfoBean().getPageIndex()==bean.getPageInfoBean().getTotalPage()){
				mAll.setPullLoadEnable(false);
			}else{
				mAll.setPullLoadEnable(true);
			}
		} else if (requestType==IPlatformRequest.REQ_SUMMARY_LIST_NEWS) {
			SummaryListResponse bean = (SummaryListResponse) baseResponse;
			SummaryListRequest reqBean = (SummaryListRequest) bean.getRequestBean();
			if(reqBean.getCurrentPage().equals("1")){
				mNewsAdapter.refresh(bean.getSummaryList());
				curPageOfNews=1;
				mNews.stopRefresh();
			}else{
				mNewsAdapter.append(bean.getSummaryList());
				curPageOfNews++;
				mNews.stopLoadMore();
			}
			if(bean.getPageInfoBean().getPageIndex()==bean.getPageInfoBean().getTotalPage()){
				mNews.setPullLoadEnable(false);
			}else{
				mNews.setPullLoadEnable(true);
			}
		} else if (requestType==IPlatformRequest.REQ_SUMMARY_LIST_BULLETIN) {
			SummaryListResponse bean = (SummaryListResponse)baseResponse;
			SummaryListRequest reqBean = (SummaryListRequest) bean.getRequestBean();
			if(reqBean.getCurrentPage().equals("1")){
				mBulletinAdapter.refresh(bean.getSummaryList());
				curPageOfBulletin=1;
				mBulletin.stopRefresh();
			}else{
				mBulletinAdapter.append(bean.getSummaryList());
				curPageOfBulletin++;
				mBulletin.stopLoadMore();
			}
			if(bean.getPageInfoBean().getPageIndex()==bean.getPageInfoBean().getTotalPage()){
				mBulletin.setPullLoadEnable(false);
			}else{
				mBulletin.setPullLoadEnable(true);
			}
		} else if (requestType==IPlatformRequest.REQ_SUMMARY_LIST_ACTIVITY) {
			SummaryListResponse bean = (SummaryListResponse) baseResponse;
			SummaryListRequest reqBean = (SummaryListRequest) bean.getRequestBean();
			if(reqBean.getCurrentPage().equals("1")){
				mActivityAdapter.refresh(bean.getSummaryList());
				curPageOfActivity=1;
				mActivity.stopRefresh();
			}else{
				mActivityAdapter.append(bean.getSummaryList());
				curPageOfActivity++;
				mActivity.stopLoadMore();
			}
			if(bean.getPageInfoBean().getPageIndex()==bean.getPageInfoBean().getTotalPage()){
				mActivity.setPullLoadEnable(false);
			}else{
				mActivity.setPullLoadEnable(true);
			}
		} else if (requestType==IPlatformRequest.REQ_SUMMARY_LIST_STRATEGY) {
			SummaryListResponse bean = (SummaryListResponse) baseResponse;
			SummaryListRequest reqBean = (SummaryListRequest) bean.getRequestBean();
			if(reqBean.getCurrentPage().equals("1")){
				mStrategyAdapter.refresh(bean.getSummaryList());
				curPageOfStrategy=1;
				mStrategy.stopRefresh();
			}else{
				mStrategyAdapter.append(bean.getSummaryList());
				curPageOfStrategy++;
				mStrategy.stopLoadMore();
			}
			if(bean.getPageInfoBean().getPageIndex()==bean.getPageInfoBean().getTotalPage()){
				mStrategy.setPullLoadEnable(false);
			}else{
				mStrategy.setPullLoadEnable(true);
			}
		} else if (requestType==IPlatformRequest.REQ_SUMMARY_LIST_VIDEO) {
			SummaryListResponse bean = (SummaryListResponse) baseResponse;
			SummaryListRequest reqBean = (SummaryListRequest) bean.getRequestBean();
			if(reqBean.getCurrentPage().equals("1")){
				mVideoAdapter.refresh(bean.getSummaryList());
				curPageOfVideo=1;
				mVideos.stopRefresh();
			}else{
				mVideoAdapter.append(bean.getSummaryList());
				curPageOfVideo++;
				mVideos.stopLoadMore();
			}
			if(bean.getPageInfoBean().getPageIndex()==bean.getPageInfoBean().getTotalPage()){
				mVideos.setPullLoadEnable(false);
			}else{
				mVideos.setPullLoadEnable(true);
			}
		}
	}

	@Override
	public int LayoutId() {
		return E_layout.efun_pd_summary_list;
	}

	class SummaryXListViewListener implements IXListViewListener {
		private String type;
		public SummaryXListViewListener(String type) {
			this.type = type;
		}

		@Override
		public void onRefresh() {
			if(type.equals(SUMMARY_ALL)){
				requestWithoutDialog(new BaseRequestBean[]{
						createRequest(IPlatformRequest.REQ_SUMMARY_LIST_ALL,0,type)});
			}else if(type.equals(SUMMARY_NEWS)){
				requestWithoutDialog(new BaseRequestBean[]{
						createRequest(IPlatformRequest.REQ_SUMMARY_LIST_NEWS,0,type)});
			}else if(type.equals(SUMMARY_BULLETIN)){
				requestWithoutDialog(new BaseRequestBean[]{
						createRequest(IPlatformRequest.REQ_SUMMARY_LIST_BULLETIN,0,type)});
			}else if(type.equals(SUMMARY_ACTIVITY)){
				requestWithoutDialog(new BaseRequestBean[]{
						createRequest(IPlatformRequest.REQ_SUMMARY_LIST_ACTIVITY,0,type)});
			}else if(type.equals(SUMMARY_STRATEGY)){
				requestWithoutDialog(new BaseRequestBean[]{
						createRequest(IPlatformRequest.REQ_SUMMARY_LIST_STRATEGY,0,type)});
			}else if(type.equals(SUMMARY_VIDEO)){
				requestWithoutDialog(new BaseRequestBean[]{
						createRequest(IPlatformRequest.REQ_SUMMARY_LIST_VIDEO,0,type)});
			}
		}

		@Override
		public void onLoadMore() {
			if(type.equals(SUMMARY_ALL)){
				requestWithoutDialog(new BaseRequestBean[]{
						createRequest(IPlatformRequest.REQ_SUMMARY_LIST_ALL,curPageOfAll,type)});
			}else if(type.equals(SUMMARY_NEWS)){
				requestWithoutDialog(new BaseRequestBean[]{
						createRequest(IPlatformRequest.REQ_SUMMARY_LIST_NEWS,curPageOfNews,type)});
			}else if(type.equals(SUMMARY_BULLETIN)){
				requestWithoutDialog(new BaseRequestBean[]{
						createRequest(IPlatformRequest.REQ_SUMMARY_LIST_BULLETIN,curPageOfBulletin,type)});
			}else if(type.equals(SUMMARY_ACTIVITY)){
				requestWithoutDialog(new BaseRequestBean[]{
						createRequest(IPlatformRequest.REQ_SUMMARY_LIST_ACTIVITY,curPageOfActivity,type)});
			}else if(type.equals(SUMMARY_STRATEGY)){
				requestWithoutDialog(new BaseRequestBean[]{
						createRequest(IPlatformRequest.REQ_SUMMARY_LIST_STRATEGY,curPageOfStrategy,type)});
			}else if(type.equals(SUMMARY_VIDEO)){
				requestWithoutDialog(new BaseRequestBean[]{
						createRequest(IPlatformRequest.REQ_SUMMARY_LIST_VIDEO,curPageOfVideo,type)});
			}
		}

	}

	private SummaryListRequest createRequest(int requestType,int currentPage,String type){
		++currentPage;
		SummaryListRequest request = new SummaryListRequest(HttpParam.PLATFORM_AREA,currentPage+"", pageSize, type,"","");
		request.setReqType(requestType);
		return request;
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

	@Override
	public void initTitle(TitleView titleView) {
		titleView.setTitleRightStatus(View.GONE);
		titleView.setTitleCenterRes(E_string.efun_pd_summary, false);
	
	}

	
}