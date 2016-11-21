package com.efun.platform.module.cs.activity;

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
import com.efun.platform.http.request.bean.CsQuestionListRequest;
import com.efun.platform.http.response.bean.BaseResponseBean;
import com.efun.platform.http.response.bean.CsQuestionListResponse;
import com.efun.platform.module.VPagerAdapter;
import com.efun.platform.module.base.FixedActivity;
import com.efun.platform.module.cs.adapter.CsQuestionAdapter;
import com.efun.platform.module.cs.bean.CsQuestionItemBean;
import com.efun.platform.module.utils.IntentUtils;
import com.efun.platform.module.utils.ViewUtils;
import com.efun.platform.utils.Const.HttpParam;
import com.efun.platform.widget.PagerTab;
import com.efun.platform.widget.TitleView;
import com.efun.platform.widget.list.XListView;
import com.efun.platform.widget.list.XListView.IXListViewListener;
/**
 * 常见问题页面
 * @author Jesse
 *
 */
public class CsQuestionActivity extends FixedActivity{
	
	/**
	 * 不同分类的 {@link XListView}
	 */
	private XListView mPopular, mLogAndRegist, mRecharge;
	/**
	 * 不同分类的 {@link CsQuestionAdapter}
	 */
	private CsQuestionAdapter mPopularAdapter, mLogAndRegistAdapter, mRechargeAdapter;
	private PagerTab mPagerTabs;
	private ViewPager mViewPager;
	private ViewGroup[] groups = new ViewGroup[3];
	private final String pageSize = "10";
	private String TYPE_POP = "";
	private String TYPE_RCG = "101";
	private String TYPE_LOR = "100";
	
	/**
	 * 起始页从0开始，其他页面从1开始
	 */
	private int curPageOfPopular = 0;
	private int curPageOfLogAndRegist = 0;
	private int curPageOfRecharge = 0;
	@Override
	public boolean needRequestData() {
		return true;
	}

	@Override
	public BaseRequestBean[] needRequestDataBean() {
		CsQuestionListRequest popularRequest = new CsQuestionListRequest(TYPE_POP,
				HttpParam.PLATFORM_AREA,
				HttpParam.APP, curPageOfPopular+"", pageSize,HttpParam.PHONE);
		popularRequest.setReqType(IPlatformRequest.REQ_CS_QUESTION_LIST_POP);
		
		CsQuestionListRequest logOrRegRequest = new CsQuestionListRequest(TYPE_LOR,
				HttpParam.PLATFORM_AREA,
				HttpParam.APP, curPageOfLogAndRegist+"", pageSize,HttpParam.PHONE);
		logOrRegRequest.setReqType(IPlatformRequest.REQ_CS_QUESTION_LIST_LOR);

		CsQuestionListRequest rechargeRequest = new CsQuestionListRequest(TYPE_RCG,
				HttpParam.PLATFORM_AREA,
				HttpParam.APP, curPageOfRecharge+"", pageSize,HttpParam.PHONE);
		rechargeRequest.setReqType(IPlatformRequest.REQ_CS_QUESTION_LIST_RCG);
		return new BaseRequestBean[] { popularRequest, logOrRegRequest, rechargeRequest};
	}

	@Override
	public void init(Bundle bundle) {
		mPopularAdapter = new CsQuestionAdapter(mContext);
		mLogAndRegistAdapter = new CsQuestionAdapter(mContext);
		mRechargeAdapter = new CsQuestionAdapter(mContext);

		mViewPager = (ViewPager) findViewById(E_id.pager_view_v4);
		mPagerTabs = (PagerTab) findViewById(E_id.pager_view_tab);

		VPagerAdapter mAdapter = new VPagerAdapter(groups);
		mViewPager.setAdapter(mAdapter);

		mPagerTabs.setTab(E_layout.efun_pd_pager_tab_textview);
		mPagerTabs.setTabSelectedColor(E_color.e_5aa9ff);
		mPagerTabs.setTitles(E_array.efun_pd_question_v_tab);
		mPagerTabs.setLine(E_drawable.efun_pd_blue_line_each);
		mPagerTabs.setPagerAdapter(mViewPager);
		mPagerTabs.setSelectedItem(mViewPager, 0);
	}

	@Override
	public ViewGroup[] needShowLoading() {
		ViewGroup groupAll = new FrameLayout(mContext);
		mPopular = ViewUtils.createListView2(mContext);
		mPopular.addHeaderView(createMargin());
		mPopular.setAdapter(mPopularAdapter);
		mPopular.setOnItemClickListener(new ItemClickListener());
		mPopular.setXListViewListener(new CsQuestionXListViewListener(TYPE_POP));
		groupAll.addView(mPopular, LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
		groups[0] = groupAll;

		ViewGroup groupNews = new FrameLayout(mContext);
		mLogAndRegist = ViewUtils.createListView2(mContext);
		mLogAndRegist.addHeaderView(createMargin());
		mLogAndRegist.setAdapter(mLogAndRegistAdapter);
		mLogAndRegist.setOnItemClickListener(new ItemClickListener());
		mLogAndRegist.setXListViewListener(new CsQuestionXListViewListener(TYPE_LOR));
		groupNews.addView(mLogAndRegist, LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
		groups[1] = groupNews;

		ViewGroup groupBulletin = new FrameLayout(mContext);
		mRecharge = ViewUtils.createListView2(mContext);
		mRecharge.addHeaderView(createMargin());
		mRecharge.setAdapter(mRechargeAdapter);
		mRecharge.setOnItemClickListener(new ItemClickListener());
		mRecharge.setXListViewListener(new CsQuestionXListViewListener(TYPE_RCG));
		groupBulletin.addView(mRecharge, LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
		groups[2] = groupBulletin;
		return groups;
	}
	public View createMargin(){
		return ViewUtils.createMargin(mContext);
	}
	@Override
	public void onSuccess(int requestType, BaseResponseBean baseResponse) {
		super.onSuccess(requestType,baseResponse);
		if (requestType==IPlatformRequest.REQ_CS_QUESTION_LIST_POP) {
			CsQuestionListResponse response = (CsQuestionListResponse) baseResponse;
			CsQuestionListRequest request = (CsQuestionListRequest) response.getRequestBean();
			if(request.getCurrentPage().equals("0")){
				mPopularAdapter.refresh(response.getCsQuestionList());
				curPageOfPopular=0;
				mPopular.stopRefresh();
			}else{
				mPopularAdapter.append(response.getCsQuestionList());
				curPageOfPopular++;
				mPopular.stopLoadMore();
			}
			if(response.getPageInfoBean().getPageIndex()==response.getPageInfoBean().getTotalPage()-1){
				mPopular.setPullLoadEnable(false);
			}else{
				mPopular.setPullLoadEnable(true);
			}
		}else if(requestType==IPlatformRequest.REQ_CS_QUESTION_LIST_LOR){
			CsQuestionListResponse response = (CsQuestionListResponse) baseResponse;
			CsQuestionListRequest request = (CsQuestionListRequest) response.getRequestBean();
			if(request.getCurrentPage().equals("0")){
				mLogAndRegistAdapter.refresh(response.getCsQuestionList());
				curPageOfLogAndRegist=0;
				mLogAndRegist.stopRefresh();
			}else{
				mLogAndRegistAdapter.append(response.getCsQuestionList());
				curPageOfLogAndRegist++;
				mLogAndRegist.stopLoadMore();
			}
			if(response.getPageInfoBean().getPageIndex()==response.getPageInfoBean().getTotalPage()-1){
				mLogAndRegist.setPullLoadEnable(false);
			}else{
				mLogAndRegist.setPullLoadEnable(true);
			}
		}else if(requestType==IPlatformRequest.REQ_CS_QUESTION_LIST_RCG){
			CsQuestionListResponse response = (CsQuestionListResponse) baseResponse;
			CsQuestionListRequest request = (CsQuestionListRequest) response.getRequestBean();
			if(request.getCurrentPage().equals("0")){
				mRechargeAdapter.refresh(response.getCsQuestionList());
				curPageOfRecharge=0;
				mRecharge.stopRefresh();
			}else{
				mRechargeAdapter.append(response.getCsQuestionList());
				curPageOfRecharge++;
				mRecharge.stopLoadMore();
			}
			if(response.getPageInfoBean().getPageIndex()==response.getPageInfoBean().getTotalPage()-1){
				mRecharge.setPullLoadEnable(false);
			}else{
				mRecharge.setPullLoadEnable(true);
			}
		}
	}

	@Override
	public int LayoutId() {
		return E_layout.efun_pd_cs_question;
	}

	class CsQuestionXListViewListener implements IXListViewListener {
		private String type;
		public CsQuestionXListViewListener(String type) {
			this.type = type;
		}

		@Override
		public void onRefresh() {
			if(type.equals(TYPE_POP)){
				requestWithoutDialog(new BaseRequestBean[]{
						createRequest(IPlatformRequest.REQ_CS_QUESTION_LIST_POP,-1,type)});
			}else if(type.equals(TYPE_LOR)){
				requestWithoutDialog(new BaseRequestBean[]{
						createRequest(IPlatformRequest.REQ_CS_QUESTION_LIST_LOR,-1,type)});
			}else if(type.equals(TYPE_RCG)){
				requestWithoutDialog(new BaseRequestBean[]{
						createRequest(IPlatformRequest.REQ_CS_QUESTION_LIST_RCG,-1,type)});
			}
		}

		@Override
		public void onLoadMore() {
			if(type.equals(TYPE_POP)){
				requestWithoutDialog(new BaseRequestBean[]{
						createRequest(IPlatformRequest.REQ_CS_QUESTION_LIST_POP,curPageOfPopular,type)});
			}else if(type.equals(TYPE_LOR)){
				requestWithoutDialog(new BaseRequestBean[]{
						createRequest(IPlatformRequest.REQ_CS_QUESTION_LIST_LOR,curPageOfLogAndRegist,type)});
			}else if(type.equals(TYPE_RCG)){
				requestWithoutDialog(new BaseRequestBean[]{
						createRequest(IPlatformRequest.REQ_CS_QUESTION_LIST_RCG,curPageOfRecharge,type)});
			}
		}
	}

	private CsQuestionListRequest createRequest(int requestType,int currentPage,String type){
		++currentPage;
		CsQuestionListRequest request = new CsQuestionListRequest(type,
				HttpParam.PLATFORM_AREA,
				HttpParam.APP, currentPage+"", pageSize,HttpParam.PHONE);
		request.setReqType(requestType);
		return request;
	}

	class ItemClickListener implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			if(parent.getAdapter().getItem(position)!=null){
				CsQuestionItemBean bean = (CsQuestionItemBean) parent.getAdapter().getItem(position);
				IntentUtils.goWithBean(mContext, CsQuestionContentActivity.class, bean);
			}
		}

	}

	@Override
	public void initTitle(TitleView titleView) {
		titleView.setTitleRightStatus(View.GONE);
		titleView.setTitleCenterRes(E_string.efun_pd_question, false);
	}
}
