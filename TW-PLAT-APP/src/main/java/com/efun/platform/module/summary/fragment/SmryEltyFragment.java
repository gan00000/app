package com.efun.platform.module.summary.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.efun.core.tools.EfunStringUtil;
import com.efun.platform.AndroidScape.E_id;
import com.efun.platform.AndroidScape.E_layout;
import com.efun.platform.FrameTabListener;
import com.efun.platform.http.dao.impl.IPlatformRequest;
import com.efun.platform.http.request.bean.BaseRequestBean;
import com.efun.platform.http.request.bean.SummaryHomeRequest;
import com.efun.platform.http.response.bean.BaseResponseBean;
import com.efun.platform.http.response.bean.SummaryHomeResponse;
import com.efun.platform.image.ImageManager;
import com.efun.platform.module.base.ElasticityFragment;
import com.efun.platform.module.base.impl.OnEfunItemClickListener;
import com.efun.platform.module.game.activity.GameDetailActivity;
import com.efun.platform.module.game.bean.GameItemBean;
import com.efun.platform.module.summary.activity.SummaryListActivity;
import com.efun.platform.module.summary.adapter.BannerAdapter;
import com.efun.platform.module.summary.bean.EventGameBean;
import com.efun.platform.module.summary.bean.SummaryHomeBean;
import com.efun.platform.module.summary.bean.VideoBean;
import com.efun.platform.module.summary.view.RecmdedGameContainer;
import com.efun.platform.module.summary.view.SummaryContainer;
import com.efun.platform.module.utils.AppUtils;
import com.efun.platform.module.utils.IntentUtils;
import com.efun.platform.module.utils.TrackingGoogleUtils;
import com.efun.platform.module.utils.TrackingUtils;
import com.efun.platform.module.utils.ViewUtils;
import com.efun.platform.utils.Const.AppStatus;
import com.efun.platform.utils.Const.HttpParam;
import com.efun.platform.utils.Const.Tab;
import com.efun.platform.utils.Const.Web;
import com.efun.platform.widget.ZoomPointContainer;
import com.efun.platform.widget.list.XListView.IXListViewListener;

/**
 * 资讯首页
 * 
 * @author Jesse
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
	 * 推荐游戏控件 {@link RecmdedGameContainer}
	 */
	private RecmdedGameContainer mRecmdedGameContainer;
	/**
	 * 资讯列表 {@link SummaryContainer}
	 */
	private SummaryContainer mSummaryContainer;
	/**
	 * 更多按钮
	 */
	private TextView moreBlue, moreGray,mVideoTitle;
	
	private RelativeLayout mVideo,mVideoParent;
	
	private ImageView mVideoImg;
	
	@Override
	public View[] addHeaderViews() {
		View view = ViewUtils.createView(getActivity(), E_layout.efun_pd_summary_content);
		return new View[] { view };
	}

	@Override
	public void init(Bundle bundle) {
		mBannerViewPager = (ViewPager) mViews[0].findViewById(E_id.pager_view);
		mZoomPointContainer = (ZoomPointContainer) mViews[0].findViewById(E_id.zoom_point_parent);
		mRecmdedGameContainer = (RecmdedGameContainer) mViews[0].findViewById(E_id.recommended_game_container);
		mSummaryContainer = (SummaryContainer) mViews[0].findViewById(E_id.summary_container);
		moreBlue = (TextView) mViews[0].findViewById(E_id.more_blue);
		moreGray = (TextView) mViews[0].findViewById(E_id.more_gray);
		mVideo = (RelativeLayout) mViews[0].findViewById(E_id.item_video);
		mVideoParent = (RelativeLayout) mViews[0].findViewById(E_id.item_video_parent);
		mVideoTitle = (TextView) mViews[0].findViewById(E_id.video_title);
		mVideoImg = (ImageView) mViews[0].findViewById(E_id.video_img);

		mRecmdedGameContainer.setItemLayout(E_layout.efun_pd_summary_recmded_game);
		mRecmdedGameContainer.onInvalidate();

		mListView.setPullLoadEnable(true);
		mListView.setPullLoadEnable(false);
		
		mSummaryContainer.setItemLayout(E_layout.efun_pd_summary_list_item);

		moreBlue.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//推荐游戏more按钮处理事件，切换到游戏页面
				((FrameTabListener) getActivity()).onTabChange(Tab.TAB_ITEM_TAG_GAMES);
			}
		});
		moreGray.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//游戏资讯more按钮处理事件，跳转到资讯页面
				Intent it = new Intent(getActivity(), SummaryListActivity.class);
				startActivity(it);
			}
		});

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
	public void onSuccess(int requestType, BaseResponseBean baseResponse) {
		super.onSuccess(requestType,baseResponse);
		if (requestType==IPlatformRequest.REQ_SUMMARY_HOME){
			mListView.stopRefresh();
			SummaryHomeResponse response = (SummaryHomeResponse)baseResponse;
			final SummaryHomeBean bean = response.getSummaryHomeBean();
			mRecmdedGameContainer.setValues(bean.getGameArray());
			mRecmdedGameContainer.setOnEfunItemClickListener(new OnEfunItemClickListener() {
				@Override
				public void onItemClick(int position) {
					TrackingUtils.track(getContext(), TrackingUtils.ACTION_SUMMARY, TrackingUtils.NAME_SUMMARY_RECOMMEND_GAME+"_"+bean.getGameArray().get(position).getGameCode());
					String actCode = bean.getGameArray().get(position).getActGameCode();
					if(actCode.equals("1")||actCode.equals("3")){
//						((FrameTabListener) getActivity()).onTabChange(Tab.TAB_ITEM_TAG_HAOKANG);
//						UserUtils.needLogin(getActivity(), new OnLoginFinishListener() {
//							@Override
//							public void loginCompleted(boolean completed) {
//								IntentUtils.startActivity(getActivity(), ActExtensionActivity.class);
								((FrameTabListener) getActivity()).onTabChange(Tab.TAB_ITEM_TAG_HAOKANG);
//							}
//						});
					}else{
						EventGameBean mEventGameBean = bean.getGameArray().get(position);
						GameItemBean gameItemBean = new GameItemBean(
								mEventGameBean.getGameCode(),
								mEventGameBean.getGameType(),
								mEventGameBean.getGameName(), 
								0, 
								mEventGameBean.getBigpic(),
								mEventGameBean.getSmallpic(),
								mEventGameBean.getUrl(), 
								"",
								mEventGameBean.getAndroidDownload(),
								"",
								mEventGameBean.getAndroidVersion(),
								mEventGameBean.getGameDesc(), 
								mEventGameBean.getVideoUrl(), 
								mEventGameBean.getLike(), 
								mEventGameBean.getVersion(),
								mEventGameBean.getPackageName(),
								mEventGameBean.getPackageSize(),
								mEventGameBean.getPic_display(),
								mEventGameBean.getHkAndroidDownloadURL(),
								mEventGameBean.getHkPackageName(),
								mEventGameBean.getHkAndroidVersion(),
								mEventGameBean.getHkIOSgameCode(),
								mEventGameBean.getFbUrl(),
								mEventGameBean.getApkDownloadUrl());
						
						if(EfunStringUtil.isEmpty(gameItemBean.getHkAndroidDownloadURL())){//只有一个地区包的游戏
							if(AppUtils.isAppInstalled(getActivity(), gameItemBean.getPackageName())){
								if(AppUtils.isAppUpdate(getActivity(), gameItemBean.getPackageName(), gameItemBean.getAndroidVersion())){
									
									gameItemBean.setStatus(AppStatus.UPDATE);
								}else{
									
									gameItemBean.setStatus(AppStatus.START);
								}
							}else{
								gameItemBean.setStatus(AppStatus.DOWNLOAD);
							}
						}else{//有两个地区包的游戏
							if(AppUtils.isAppInstalled(getActivity(), gameItemBean.getPackageName()) || AppUtils.isAppInstalled(getActivity(), gameItemBean.getHkPackageName())){//设备里有一个地区包或者有两个地区的包
								if((AppUtils.isAppInstalled(getActivity(), gameItemBean.getPackageName()) && 
										AppUtils.isAppUpdate(getActivity(),gameItemBean.getPackageName(),gameItemBean.getAndroidVersion())) || 
										(AppUtils.isAppInstalled(getActivity(),gameItemBean.getHkPackageName()) && 
												AppUtils.isAppUpdate(getActivity(),gameItemBean.getHkPackageName(),gameItemBean.getHkAndroidVersion()))){
									
									gameItemBean.setStatus(AppStatus.UPDATE);
								}else{
									
									gameItemBean.setStatus(AppStatus.START);
								}
							}else{
								gameItemBean.setStatus(AppStatus.DOWNLOAD);
							}
						}
						IntentUtils.goWithBean(getActivity(), GameDetailActivity.class, gameItemBean);
					}
				}
			});
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
					TrackingUtils.track(getContext(), TrackingUtils.ACTION_SUMMARY, TrackingUtils.NAME_SUMMARY_BANNER);
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
//				ImageLoader.getInstance().displayImage(mVideoBean.getVideoPic(), mVideoImg);
				ImageManager.displayImage(mVideoBean.getVideoPic(), mVideoImg, ImageManager.IMAGE_RECTANGLE_H);
				mVideo.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
//						Intent it = new Intent(getActivity(), VideoWebActivity.class);
//						startActivity(it);
						TrackingUtils.track(getContext(), TrackingUtils.ACTION_SUMMARY, TrackingUtils.NAME_SUMMARY_VIDEO);
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
		homeRequest.setReqType(IPlatformRequest.REQ_SUMMARY_HOME);
		return new BaseRequestBean[] { homeRequest };
	}

	@Override
	public BaseAdapter adapter() {
		return null;
	}
		
}
