package com.efun.platform.module.summary.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.efun.facebook.share.activity.EfunFBFunctionActivity;
import com.efun.platform.AndroidScape.E_drawable;
import com.efun.platform.AndroidScape.E_id;
import com.efun.platform.AndroidScape.E_layout;
import com.efun.platform.AndroidScape.E_string;
import com.efun.platform.IPlatApplication;
import com.efun.platform.http.dao.impl.IPlatformRequest;
import com.efun.platform.http.request.bean.ActivityListRequest;
import com.efun.platform.http.request.bean.BaseRequestBean;
import com.efun.platform.http.request.bean.SummaryListRequest;
import com.efun.platform.http.request.bean.SummaryPraiseRequest;
import com.efun.platform.http.response.bean.ActivityListResponse;
import com.efun.platform.http.response.bean.BaseResponseBean;
import com.efun.platform.http.response.bean.GamePraiseResponse;
import com.efun.platform.http.response.bean.SummaryListResponse;
import com.efun.platform.http.response.bean.SummaryPraiseResponse;
import com.efun.platform.image.core.ImageLoader;
import com.efun.platform.image.core.listener.PauseOnScrollListener;
import com.efun.platform.module.BaseDataBean;
import com.efun.platform.module.PopWindow;
import com.efun.platform.module.VPagerAdapter;
import com.efun.platform.module.base.FixedActivity;
import com.efun.platform.module.base.impl.OnEfunItemAttrsClickListener;
import com.efun.platform.module.base.impl.OnLoginFinishListener;
import com.efun.platform.module.base.impl.OnUpdateListener;
import com.efun.platform.module.game.bean.GamePraiseBean;
import com.efun.platform.module.summary.adapter.SummaryAdapter;
import com.efun.platform.module.summary.adapter.VideoAdapter;
import com.efun.platform.module.summary.bean.SummaryItemBean;
import com.efun.platform.module.summary.bean.SummaryPraiseBean;
import com.efun.platform.module.utils.AppUtils;
import com.efun.platform.module.utils.IntentUtils;
import com.efun.platform.module.utils.PopUtils;
import com.efun.platform.module.utils.ToastUtils;
import com.efun.platform.module.utils.UserUtils;
import com.efun.platform.module.utils.ViewUtils;
import com.efun.platform.module.welfare.bean.ActItemBean;
import com.efun.platform.utils.Const.ClickButtonType;
import com.efun.platform.utils.Const.HttpParam;
import com.efun.platform.utils.Const.Web;
import com.efun.platform.utils.TimeFormatUtil;
import com.efun.platform.widget.TitleView;
import com.efun.platform.widget.list.XListView;
import com.efun.platform.widget.list.XListView.IXListViewListener;
import com.efun.platform.widget.tabpage.TabPageIndicator;

/**
 * 資訊列表
 * 
 * @author Jesse
 * 
 */
public class SummaryListActivity extends FixedActivity {

	/**
	 * 不同分类的 {@link XListView}
	 */
	private XListView mAll, mNews, mBulletin, mActivity, mStrategy,mVideos,mAboutUs;
	/**
	 * 不同分类的 {@link SummaryAdapter}
	 */
	private SummaryAdapter mAllAdapter, mNewsAdapter, mBulletinAdapter,
			mActivityAdapter, mStrategyAdapter,mAboutUsAdapter;
	private VideoAdapter mVideoAdapter;
	private View activityHeadView,activityHeadViewOther;
	private ImageView activityIcon;
	private TextView activityTitle,activityTime;
	private ArrayList<ActItemBean> actItems;
	private TabPageIndicator mPagerTabs;
	private ViewPager mViewPager;
	private PopWindow sharePop;
	private int praisePosition;
	private SummaryItemBean mSummaryItemBean;
	private ViewGroup[] groups;
	private String[] titles;
	private final String pageSize = "10";
	/**
	 * 全部
	 */
	private final String SUMMARY_ALL= "";
	private int curPageOfAll = 1;
	/**
	 * 與我相關
	 */
	private final String SUMMARY_ABOUT_US= "8";
	private int curPageOfAboutUs = 1;
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
		SummaryListRequest aboutUsRequest = null;
		if(IPlatApplication.get().getUser() != null){//與我相關
			aboutUsRequest = new SummaryListRequest(
					HttpParam.PLATFORM_AREA,
					curPageOfAboutUs+"", pageSize, "",
					IPlatApplication.get().getUser().getUid(),HttpParam.ABOUT);
			aboutUsRequest.setReqType(IPlatformRequest.REQ_SUMMARY_LIST_ABOUT_US);
		}
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
		SummaryListRequest videoRequest = null;
		
		if(IPlatApplication.get().getUser() != null){//與我相關
			videoRequest = new SummaryListRequest(
					HttpParam.PLATFORM_AREA,
					curPageOfVideo+"", pageSize, SUMMARY_VIDEO,IPlatApplication.get().getUser().getUid(),"");
		}else{
			videoRequest = new SummaryListRequest(
					HttpParam.PLATFORM_AREA,
					curPageOfVideo+"", pageSize, SUMMARY_VIDEO,"","");
		}
		videoRequest.setReqType(IPlatformRequest.REQ_SUMMARY_LIST_VIDEO);
		if(IPlatApplication.get().getUser() != null){
			return new BaseRequestBean[] { allRequest,aboutUsRequest, newsRequest, bulletinRequest, activityRequest, strategyRequest, videoRequest };
		}else{
			return new BaseRequestBean[] { allRequest, newsRequest, bulletinRequest, activityRequest, strategyRequest, videoRequest };
		}
	}

	@Override
	public void init(Bundle bundle) {
		mAllAdapter = new SummaryAdapter(mContext);
		mAboutUsAdapter = new SummaryAdapter(mContext);
		mNewsAdapter = new SummaryAdapter(mContext);
		mBulletinAdapter = new SummaryAdapter(mContext);
		mActivityAdapter = new SummaryAdapter(mContext);
		mStrategyAdapter = new SummaryAdapter(mContext);
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
					sharePop = PopUtils.createShare(mContext, mVideos, bean);
					sharePop.showPopWindow(PopWindow.POP_WINDOW_SHARE);
					break;
				}
			}
		});

		mViewPager = (ViewPager) findViewById(E_id.pager_view_v4);
		mPagerTabs = (TabPageIndicator) findViewById(E_id.pager_view_tab);
		
		if(IPlatApplication.get().getUser() != null){//表示有登录
			groups = new ViewGroup[7];
			titles = new String[]{"全部","與我相關","新聞","公告","活動","攻略","視頻"};
		}else{
			groups = new ViewGroup[6];
			titles = new String[]{"全部","新聞","公告","活動","攻略","視頻"};
		}

		VPagerAdapter mAdapter = new VPagerAdapter(groups);
		mAdapter.setTitles(titles);
		mViewPager.setAdapter(mAdapter);
		mPagerTabs.setViewPager(mViewPager);
		requestWithoutDialog(new BaseRequestBean[]{createActsRequest()});

//		mPagerTabs.setTab(E_layout.efun_pd_pager_tab_textview);
//		mPagerTabs.setTabSelectedColor(E_color.e_5aa9ff);
//		mPagerTabs.setTitles(E_array.efun_pd_summary_v_tab);
//		mPagerTabs.setLine(E_drawable.efun_pd_blue_line_each);
//		mPagerTabs.setPagerAdapter(mViewPager);
//		mPagerTabs.setSelectedItem(mViewPager, 0);
		
		
	}

	@Override
	public ViewGroup[] needShowLoading() {
		ViewGroup groupAll = new FrameLayout(mContext);
		mAll = ViewUtils.createListView2(mContext);
		mAll.addHeaderView(createMargin());
		mAll.setAdapter(mAllAdapter);
		mAll.setOnItemClickListener(new ItemClickListener(SUMMARY_ALL));
		mAll.setXListViewListener(new SummaryXListViewListener(SUMMARY_ALL));
		groupAll.addView(mAll, LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
		
		
		ViewGroup groupAboutUs = new FrameLayout(mContext);
		mAboutUs = ViewUtils.createListView2(mContext);
		mAboutUs.addHeaderView(createMargin());
		mAboutUs.setAdapter(mAboutUsAdapter);
		mAboutUs.setOnItemClickListener(new ItemClickListener(SUMMARY_ABOUT_US));
		mAboutUs.setXListViewListener(new SummaryXListViewListener(SUMMARY_ABOUT_US));
		groupAboutUs.addView(mAboutUs, LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);

		ViewGroup groupNews = new FrameLayout(mContext);
		mNews = ViewUtils.createListView2(mContext);
		mNews.addHeaderView(createMargin());
		mNews.setAdapter(mNewsAdapter);
		mNews.setOnItemClickListener(new ItemClickListener(SUMMARY_NEWS));
		mNews.setXListViewListener(new SummaryXListViewListener(SUMMARY_NEWS));
		groupNews.addView(mNews, LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
		

		ViewGroup groupBulletin = new FrameLayout(mContext);
		mBulletin = ViewUtils.createListView2(mContext);
		mBulletin.addHeaderView(createMargin());
		mBulletin.setAdapter(mBulletinAdapter);
		mBulletin.setOnItemClickListener(new ItemClickListener(SUMMARY_BULLETIN));
		mBulletin.setXListViewListener(new SummaryXListViewListener(SUMMARY_BULLETIN));
		groupBulletin.addView(mBulletin, LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
		

		ViewGroup groupActivity = new FrameLayout(mContext);
		mActivity = ViewUtils.createListView2(mContext);
		activityHeadView = ViewUtils.createActivityHeader(mContext);
		mActivity.addHeaderView(activityHeadView);
		mActivity.setAdapter(mActivityAdapter);
		mActivity.setOnItemClickListener(new ItemClickListener(SUMMARY_ACTIVITY));
		mActivity.setXListViewListener(new SummaryXListViewListener(SUMMARY_ACTIVITY));
		groupActivity.addView(mActivity, LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
		
		activityIcon = (ImageView) activityHeadView.findViewById(E_id.act_icon);
		activityTitle = (TextView) activityHeadView.findViewById(E_id.act_title);
		activityTime = (TextView) activityHeadView.findViewById(E_id.act_time);
		activityHeadView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View paramView) {
				// TODO Auto-generated method stub
				IntentUtils.go2Web(mContext, Web.WEB_GO_ACT,actItems.get(0));
			}
		});
		

		ViewGroup groupStrategy = new FrameLayout(mContext);
		mStrategy = ViewUtils.createListView2(mContext);
		mStrategy.addHeaderView(createMargin());
		mStrategy.setAdapter(mStrategyAdapter);
		mStrategy.setOnItemClickListener(new ItemClickListener(SUMMARY_STRATEGY));
		mStrategy.setXListViewListener(new SummaryXListViewListener(SUMMARY_STRATEGY));
		groupStrategy.addView(mStrategy, LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
		
		
		ViewGroup groupVideo = new FrameLayout(mContext);
		mVideos = ViewUtils.createListView2(mContext);
		mVideos.addHeaderView(createMargin());
		mVideos.setAdapter(mVideoAdapter);
		mVideos.setOnItemClickListener(new ItemClickListener(SUMMARY_VIDEO));
		mVideos.setXListViewListener(new SummaryXListViewListener(SUMMARY_VIDEO));
		groupVideo.addView(mVideos, LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
		
		
		if(IPlatApplication.get().getUser() != null){
			groups[0] = groupAll;
			groups[1] = groupAboutUs;
			groups[2] = groupNews;
			groups[3] = groupBulletin;
			groups[4] = groupActivity;
			groups[5] = groupStrategy;
			groups[6] = groupVideo;
		}else{
			groups[0] = groupAll;
			groups[1] = groupNews;
			groups[2] = groupBulletin;
			groups[3] = groupActivity;
			groups[4] = groupStrategy;
			groups[5] = groupVideo;
		}
		
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
		} else if (requestType==IPlatformRequest.REQ_SUMMARY_LIST_ABOUT_US) {
			SummaryListResponse bean = (SummaryListResponse)baseResponse;
			SummaryListRequest reqBean = (SummaryListRequest) bean.getRequestBean();
			if(reqBean.getCurrentPage().equals("1")){
				mAboutUsAdapter.refresh(bean.getSummaryList());
				curPageOfAboutUs=1;
				mAboutUs.stopRefresh();
			}else{
				mAboutUsAdapter.append(bean.getSummaryList());
				curPageOfAboutUs++;
				mAboutUs.stopLoadMore();
			}
			if(bean.getPageInfoBean().getPageIndex()==bean.getPageInfoBean().getTotalPage()){
				mAboutUs.setPullLoadEnable(false);
			}else{
				mAboutUs.setPullLoadEnable(true);
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
		}else if(requestType==IPlatformRequest.REQ_ACT_LIST){
			ActivityListResponse response = (ActivityListResponse)baseResponse;
			if(response.getActsBean() != null && response.getActsBean().size() > 0){
				actItems = response.getActsBean();
				ImageLoader.getInstance().displayImage(response.getActsBean().get(0).getImg(), activityIcon);
				activityTitle.setText(response.getActsBean().get(0).getActivityName());
				activityTime.setText(TimeFormatUtil.StringFormatDate2(response.getActsBean().get(0).getEndTime()));
			}else{
				mActivity.removeHeaderView(activityHeadView);
				if(activityHeadViewOther != null){
					mActivity.removeHeaderView(activityHeadViewOther);
				}
				activityHeadViewOther = createMargin();
				mActivity.addHeaderView(activityHeadViewOther);
				
				if(IPlatApplication.get().getUser() != null){
					groups[4].invalidate();
				}else{
					groups[3].invalidate();
				}
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
	
	@Override
	public void onNoData(int requestType, String noDataNotify) {
		// TODO Auto-generated method stub
		super.onNoData(requestType, noDataNotify);
		Log.i("efun", "requestType:"+requestType);
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
			}else if(type.equals(SUMMARY_ABOUT_US)){
				requestWithoutDialog(new BaseRequestBean[]{
						createRequest(IPlatformRequest.REQ_SUMMARY_LIST_ABOUT_US,0,"")});
			}else if(type.equals(SUMMARY_NEWS)){
				requestWithoutDialog(new BaseRequestBean[]{
						createRequest(IPlatformRequest.REQ_SUMMARY_LIST_NEWS,0,type)});
			}else if(type.equals(SUMMARY_BULLETIN)){
				requestWithoutDialog(new BaseRequestBean[]{
						createRequest(IPlatformRequest.REQ_SUMMARY_LIST_BULLETIN,0,type)});
			}else if(type.equals(SUMMARY_ACTIVITY)){
				requestWithoutDialog(new BaseRequestBean[]{createActsRequest(),
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
			}else if(type.equals(SUMMARY_ABOUT_US)){
				requestWithoutDialog(new BaseRequestBean[]{
						createRequest(IPlatformRequest.REQ_SUMMARY_LIST_ABOUT_US,curPageOfAboutUs,"")});
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
		SummaryListRequest request = null;
		if(requestType == IPlatformRequest.REQ_SUMMARY_LIST_ABOUT_US){
			request =  new SummaryListRequest(HttpParam.PLATFORM_AREA,currentPage+"", pageSize, type,IPlatApplication.get().getUser().getUid(),HttpParam.ABOUT);
		}else if(requestType == IPlatformRequest.REQ_SUMMARY_LIST_VIDEO){
			if(IPlatApplication.get().getUser() != null){
				request = new SummaryListRequest(HttpParam.PLATFORM_AREA,currentPage+"", pageSize, type,IPlatApplication.get().getUser().getUid(),"");
			}else{
				request = new SummaryListRequest(HttpParam.PLATFORM_AREA,currentPage+"", pageSize, type,"","");
			}
		}else{
			request = new SummaryListRequest(HttpParam.PLATFORM_AREA,currentPage+"", pageSize, type,"","");
		}
		request.setReqType(requestType);
		return request;
	}
	/**
	 * 游戏活动列表
	 * @return
	 */
	private ActivityListRequest createActsRequest(){
		ActivityListRequest request = new ActivityListRequest("1", "10", "102");
		request.setPlatform(HttpParam.PLATFORM_AREA);
		request.setVersion(HttpParam.PLATFORM);
		request.setLanguage(HttpParam.LANGUAGE);
		request.setPackageVersion(AppUtils.getAppVersionName(mContext));
		request.setActivityStatus("1");
		request.setReqType(IPlatformRequest.REQ_ACT_LIST);
		return request;
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
	
	

	class ItemClickListener implements OnItemClickListener {
		private String type;
		public ItemClickListener(String type) {
			this.type = type;
		}
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			if(parent.getAdapter().getItem(position)!=null){
				if(type.equals(SUMMARY_VIDEO)){
					SummaryItemBean mVideoBean = (SummaryItemBean) parent.getAdapter().getItem(position);
					IntentUtils.go2VideoWeb(mContext, Web.WEB_GO_SUMMARY_LIST,mVideoBean);
				}else{
					SummaryItemBean bean = (SummaryItemBean) parent.getAdapter().getItem(position);
					IntentUtils.go2Web(mContext, Web.WEB_GO_SUMMARY,bean);
				}
			}
		}
	}

	@Override
	public void initTitle(TitleView titleView) {
		titleView.setTitleRightStatus(View.GONE);
		titleView.setTitleCenterRes(E_string.efun_pd_summary, false);
	
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