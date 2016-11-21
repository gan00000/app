package com.efun.platform.module.game.activity;

import java.math.BigDecimal;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.efun.core.tools.EfunStringUtil;
import com.efun.platform.AndroidScape.E_array;
import com.efun.platform.AndroidScape.E_color;
import com.efun.platform.AndroidScape.E_dimens;
import com.efun.platform.AndroidScape.E_drawable;
import com.efun.platform.AndroidScape.E_id;
import com.efun.platform.AndroidScape.E_layout;
import com.efun.platform.AndroidScape.E_string;
import com.efun.platform.IPlatApplication;
import com.efun.platform.http.dao.impl.IPlatformRequest;
import com.efun.platform.http.request.bean.BaseRequestBean;
import com.efun.platform.http.request.bean.GameDetailRequest;
import com.efun.platform.http.request.bean.GameNewsRequest;
import com.efun.platform.http.request.bean.GamePraiseRequest;
import com.efun.platform.http.request.bean.GameVoteRequest;
import com.efun.platform.http.response.bean.BaseResponseBean;
import com.efun.platform.http.response.bean.GameDetailResponse;
import com.efun.platform.http.response.bean.GameNewsResponse;
import com.efun.platform.http.response.bean.GamePraiseResponse;
import com.efun.platform.http.response.bean.GameVoteResponse;
import com.efun.platform.image.ImageManager;
import com.efun.platform.module.PopWindow;
import com.efun.platform.module.VPagerAdapter;
import com.efun.platform.module.base.FixedActivity;
import com.efun.platform.module.base.impl.OnEfunItemClickListener;
import com.efun.platform.module.base.impl.OnLoginFinishListener;
import com.efun.platform.module.base.impl.OnUpdateListener;
import com.efun.platform.module.game.adapter.GameCommendAdapter;
import com.efun.platform.module.game.adapter.GameSummaryAdapter;
import com.efun.platform.module.game.bean.GameDetailBean;
import com.efun.platform.module.game.bean.GameItemBean;
import com.efun.platform.module.game.bean.GameNewsBean;
import com.efun.platform.module.game.bean.GamePraiseBean;
import com.efun.platform.module.game.bean.GameVoteBean;
import com.efun.platform.module.game.bean.GameVoteItemBean;
import com.efun.platform.module.game.view.GamePictureList;
import com.efun.platform.module.game.view.GameVPager;
import com.efun.platform.module.utils.AppUtils;
import com.efun.platform.module.utils.IntentUtils;
import com.efun.platform.module.utils.PopUtils;
import com.efun.platform.module.utils.ToastUtils;
import com.efun.platform.module.utils.UserUtils;
import com.efun.platform.module.utils.ViewUtils;
import com.efun.platform.utils.Const;
import com.efun.platform.utils.Const.AppStatus;
import com.efun.platform.utils.Const.HttpParam;
import com.efun.platform.utils.Const.RequestCode;
import com.efun.platform.utils.Const.ResultCode;
import com.efun.platform.utils.Const.Web;
import com.efun.platform.utils.StatLogInfoUtils;
import com.efun.platform.widget.DrawViewUtil;
import com.efun.platform.widget.PagerTab;
import com.efun.platform.widget.RatingBarView;
import com.efun.platform.widget.TitleView;
import com.efun.platform.widget.list.XListView;
import com.efun.platform.widget.list.XListView.IXListViewListener;

/**
 * 游戏详情页面
 * 
 * @author Jesse
 * 
 */
public class GameDetailActivity extends FixedActivity {
	private String gameTitle;
	private PopWindow mDownloadChoice, mStartAppChoice;
	private int mCurrentPage = 0;
	private String mPageSize = "10";

	@Override
	public void initTitle(TitleView titleView) {
		titleView.setTitleRightStatus(View.GONE);
		titleView.setTitleCenterText(gameTitle);
	}

	private final int PAGER_TAB_COUNT = 3;
	private GameItemBean mGameItemBean;
	private ViewGroup[] groups = new ViewGroup[PAGER_TAB_COUNT];
	private ImageView headerIcon, zanIcon, vedioIcon;
	private ImageView iconDetail;
	private TextView textDetail;
	private TextView gameVersionCode, gameSize, gameDownIcon;
	private PagerTab mPagerTabs;
	private GameVPager mViewPager;
	// private RoundCornerTextView mCategory;
	private TextView mCategory;
	private TextView gameIntroDetail, gameVersionDetail,gameShowAll, gameVersionShowAll,voteCommend;
	private GamePictureList mSwitchV;
	private XListView listCommend, listSummary;
	private String gameCode;
	
	private TextView avgStar;//平均分
	private RatingBarView starImages;//星圖
	private DrawViewUtil starLevel5,starLevel4,starLevel3,starLevel2,starLevel1;//五條評分條
	
	private GameSummaryAdapter mGameSummaryAdapter;
	private GameCommendAdapter mGameCommendAdapter;
	private boolean screenOrientation;
	private Boolean flag1 = true;
	private Boolean flag2 = true;

	@Override
	public ViewGroup[] needShowLoading() {
		// 游戏详情
		ViewGroup groupDetail = new FrameLayout(mContext);

		View headerDetail = ViewUtils.createView(mContext,
				E_layout.efun_pd_game_detail_tab_particular);
		mSwitchV = (GamePictureList) headerDetail
				.findViewById(E_id.gamePictureList);
		gameIntroDetail = (TextView) headerDetail.findViewById(E_id.item_text);
		gameVersionDetail = (TextView) headerDetail.findViewById(E_id.item_version_text);
		gameShowAll = (TextView) headerDetail
				.findViewById(E_id.game_instro_show_all);
		gameVersionShowAll = (TextView) headerDetail
				.findViewById(E_id.game_version_show_all);

		XListView listDetail = ViewUtils.createListView(mContext);
		listDetail.addHeaderView(headerDetail);
		listDetail.setAdapter(null);
		LinearLayout containerDetail = new LinearLayout(mContext);
		containerDetail.setOrientation(LinearLayout.VERTICAL);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT, 1);
		containerDetail.addView(listDetail, params);

		flag1 = true;
		gameShowAll.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.e("efun",
						"当前gameIntroDetail的行数："
								+ gameIntroDetail.getLineCount());
				if (flag1) {
					flag1 = false;
					gameIntroDetail.setEllipsize(null); // 展开
					gameIntroDetail.setSingleLine(flag1);
					gameShowAll.setText(E_string.efun_pd_games_show_all_return);
				} else {
					flag1 = true;
					// gameIntroDetail.setEllipsize(TextUtils.TruncateAt.END);
					// // 收缩
					gameIntroDetail.setLines(4);
					gameShowAll.setText(E_string.efun_pd_games_show_all);
					// tv.setSingleLine(flag);
				}
			}
		});
		flag2 = true;
		gameVersionShowAll.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.e("efun",
						"当前gameVersionDetail的行数："
								+ gameVersionDetail.getLineCount());
				if (flag2) {
					flag2 = false;
					gameVersionDetail.setEllipsize(null); // 展开
					gameVersionDetail.setSingleLine(flag2);
					gameVersionShowAll.setText(E_string.efun_pd_games_show_all_return);
				} else {
					flag2 = true;
					// gameIntroDetail.setEllipsize(TextUtils.TruncateAt.END);
					// // 收缩
					gameVersionDetail.setLines(4);
					gameVersionShowAll.setText(E_string.efun_pd_games_show_all);
					// tv.setSingleLine(flag);
				}
			}
		});
		// View v = ViewUtils.createBottom(mContext);
		// ImageView iconDetail = (ImageView) v.findViewById(E_id.item_icon);
		// TextView textDetail = (TextView) v.findViewById(E_id.item_text);
		//
		// if(mGameItemBean!=null){
		// switch (mGameItemBean.getStatus()) {
		// case AppStatus.DOWNLOAD:
		// iconDetail.setBackgroundResource(E_drawable.efun_pd_download_selector);
		// textDetail.setText(E_string.efun_pd_download_en);
		// break;
		// case AppStatus.START:
		// iconDetail.setBackgroundResource(E_drawable.efun_pd_start_selector);
		// textDetail.setText(E_string.efun_pd_start_en);
		// break;
		// case AppStatus.UPDATE:
		// iconDetail.setBackgroundResource(E_drawable.efun_pd_update_selector);
		// textDetail.setText(E_string.efun_pd_update_en);
		// break;
		// }
		// }else{
		// iconDetail.setBackgroundResource(E_drawable.efun_pd_download_selector);
		// textDetail.setText(E_string.efun_pd_download_en);
		// }
		// containerDetail.addView(v);
		// mDownloadChoice = PopUtils.createDownLoadAreaChose(mContext, v,
		// mContext.getResources().getStringArray(E_array.efun_pd_pop_chose_area_download),
		// new OnEfunItemClickListener() {
		//
		// @Override
		// public void onItemClick(int position) {
		// // TODO Auto-generated method stub
		// if(position == 0){
		// if(mGameItemBean.getStatus() == AppStatus.UPDATE){
		// StatLogInfoUtils.setStaticLogInfo(mContext,
		// mGameItemBean.getGameCode(), HttpParam.GAMELIST,
		// HttpParam.UPDATECLICK, HttpParam.GAME_TW);
		// }else{
		// StatLogInfoUtils.setStaticLogInfo(mContext,
		// mGameItemBean.getGameCode(), HttpParam.GAMELIST,
		// HttpParam.DOWNLOADCLICK, HttpParam.GAME_TW);
		// }
		// AppUtils.download(mContext, mGameItemBean.getAndroidDownload());
		// }else{
		// if(mGameItemBean.getStatus() == AppStatus.UPDATE){
		// StatLogInfoUtils.setStaticLogInfo(mContext,
		// mGameItemBean.getGameCode(), HttpParam.GAMELIST,
		// HttpParam.UPDATECLICK, HttpParam.GAME_HK);
		// }else{
		// StatLogInfoUtils.setStaticLogInfo(mContext,
		// mGameItemBean.getGameCode(), HttpParam.GAMELIST,
		// HttpParam.DOWNLOADCLICK, HttpParam.GAME_HK);
		// }
		// AppUtils.download(mContext, mGameItemBean.getHkAndroidDownloadURL());
		// }
		// }
		// });
		//
		// mStartAppChoice = PopUtils.createStartAPPChose(mContext, v,
		// mContext.getResources().getStringArray(E_array.efun_pd_pop_chose_area_download),
		// new OnEfunItemClickListener() {
		//
		// @Override
		// public void onItemClick(int position) {
		// // TODO Auto-generated method stub
		// if(position == 0){
		// StatLogInfoUtils.setStaticLogInfo(mContext,
		// mGameItemBean.getGameCode(), HttpParam.GAMELIST, HttpParam.STARTAPP,
		// HttpParam.GAME_TW);
		// AppUtils.startApp(mContext, mGameItemBean.getPackageName());
		// }else{
		// StatLogInfoUtils.setStaticLogInfo(mContext,
		// mGameItemBean.getGameCode(), HttpParam.GAMELIST, HttpParam.STARTAPP,
		// HttpParam.GAME_HK);
		// AppUtils.startApp(mContext, mGameItemBean.getHkPackageName());
		// }
		// }
		// });
		//
		// v.setOnClickListener(new OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// if(mGameItemBean!=null){
		// switch (mGameItemBean.getStatus()) {
		// case AppStatus.START:
		// if(AppUtils.isAppInstalled(mContext, mGameItemBean.getPackageName())
		// && !AppUtils.isAppInstalled(mContext,
		// mGameItemBean.getHkPackageName())){
		// StatLogInfoUtils.setStaticLogInfo(mContext,
		// mGameItemBean.getGameCode(), HttpParam.GAMELIST, HttpParam.STARTAPP,
		// HttpParam.GAME_TW);
		// AppUtils.startApp(mContext, mGameItemBean.getPackageName());
		// }else if(!AppUtils.isAppInstalled(mContext,
		// mGameItemBean.getPackageName()) && AppUtils.isAppInstalled(mContext,
		// mGameItemBean.getHkPackageName())){
		// StatLogInfoUtils.setStaticLogInfo(mContext,
		// mGameItemBean.getGameCode(), HttpParam.GAMELIST, HttpParam.STARTAPP,
		// HttpParam.GAME_HK);
		// AppUtils.startApp(mContext, mGameItemBean.getHkPackageName());
		// }else{
		// mStartAppChoice.showPopWindow(PopWindow.POP_WINDOW_CHOSE_START_APP);
		// }
		// break;
		// case AppStatus.UPDATE:
		// if(AppUtils.isAppInstalled(mContext, mGameItemBean.getPackageName())
		// && !AppUtils.isAppInstalled(mContext,
		// mGameItemBean.getHkPackageName())){
		// StatLogInfoUtils.setStaticLogInfo(mContext,
		// mGameItemBean.getGameCode(), HttpParam.GAMELIST,
		// HttpParam.UPDATECLICK, HttpParam.GAME_TW);
		// AppUtils.download(mContext, mGameItemBean.getAndroidDownload());
		// }else if(!AppUtils.isAppInstalled(mContext,
		// mGameItemBean.getPackageName()) && AppUtils.isAppInstalled(mContext,
		// mGameItemBean.getHkPackageName())){
		// StatLogInfoUtils.setStaticLogInfo(mContext,
		// mGameItemBean.getGameCode(), HttpParam.GAMELIST,
		// HttpParam.UPDATECLICK, HttpParam.GAME_HK);
		// AppUtils.download(mContext, mGameItemBean.getHkAndroidDownloadURL());
		// }else{
		// mDownloadChoice.showPopWindow(PopWindow.POP_WINDOW_CHOSE_DOWNLOAD_AREA);
		// }
		// break;
		// case AppStatus.DOWNLOAD:
		// if(!EfunStringUtil.isEmpty(mGameItemBean.getHkAndroidDownloadURL())){
		// mDownloadChoice.showPopWindow(PopWindow.POP_WINDOW_CHOSE_DOWNLOAD_AREA);
		// }else{
		// StatLogInfoUtils.setStaticLogInfo(mContext,
		// mGameItemBean.getGameCode(), HttpParam.GAMELIST,
		// HttpParam.DOWNLOADCLICK, HttpParam.GAME_TW);
		// AppUtils.download(mContext, mGameItemBean.getAndroidDownload());
		// }
		// break;
		// }
		// }
		// //游戏详情页面的下载事件
		// }
		// });

		groupDetail.addView(containerDetail, LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		groups[0] = groupDetail;

		// 游戏资讯
		ViewGroup groupSummary = new FrameLayout(mContext);
		mGameSummaryAdapter = new GameSummaryAdapter(mContext);
		mGameSummaryAdapter.setGameBean(mGameItemBean);
		listSummary = ViewUtils.createListView(mContext);
		listSummary.setAdapter(mGameSummaryAdapter);
		listSummary.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (parent.getAdapter().getItem(position) != null) {
					GameNewsBean bean = (GameNewsBean) parent.getAdapter()
							.getItem(position);
					IntentUtils.go2Web(mContext, Web.WEB_GO_GAME, bean);
				}

			}
		});
		groupSummary.addView(listSummary, LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		groups[1] = groupSummary;

		// 玩家评论
		ViewGroup groupCommend = new FrameLayout(mContext);
		mGameCommendAdapter = new GameCommendAdapter(mContext);
		listCommend = ViewUtils.createListView(mContext);
//		View margin = ViewUtils.createMargin(mContext);
//		margin.setBackgroundColor(getResources().getColor(E_color.e_efeff4));
		View headerCommend = ViewUtils.createView(mContext,
				E_layout.efun_pd_game_detail_tab_commend);
		avgStar = (TextView) headerCommend.findViewById(E_id.item_avg_star);
		starImages = (RatingBarView) headerCommend.findViewById(E_id.game_commend_ratingbar);
		starLevel5 = (DrawViewUtil) headerCommend.findViewById(E_id.item_star_level_5);
		starLevel4 = (DrawViewUtil) headerCommend.findViewById(E_id.item_star_level_4);
		starLevel3 = (DrawViewUtil) headerCommend.findViewById(E_id.item_star_level_3);
		starLevel2 = (DrawViewUtil) headerCommend.findViewById(E_id.item_star_level_2);
		starLevel1 = (DrawViewUtil) headerCommend.findViewById(E_id.item_star_level_1);
		voteCommend = (TextView) headerCommend.findViewById(E_id.vote_commend);
		
		
		starImages.setStarIVSelectImage(E_drawable.efun_pd_star_yellow_select);
		starImages.setStarIVUnSelectImage(E_drawable.efun_pd_star_grey_unselect);
		starImages.setStarWidth(E_dimens.e_size_50);
		
		starLevel5.setMwidth(getResources().getDimensionPixelSize(E_dimens.e_size_480));
		starLevel5.setMheight(getResources().getDimensionPixelSize(E_dimens.e_size_20));
		
		starLevel4.setMwidth(getResources().getDimensionPixelSize(E_dimens.e_size_480));
		starLevel4.setMheight(getResources().getDimensionPixelSize(E_dimens.e_size_20));
		
		starLevel3.setMwidth(getResources().getDimensionPixelSize(E_dimens.e_size_480));
		starLevel3.setMheight(getResources().getDimensionPixelSize(E_dimens.e_size_20));
		
		starLevel2.setMwidth(getResources().getDimensionPixelSize(E_dimens.e_size_480));
		starLevel2.setMheight(getResources().getDimensionPixelSize(E_dimens.e_size_20));
		
		starLevel1.setMwidth(getResources().getDimensionPixelSize(E_dimens.e_size_480));
		starLevel1.setMheight(getResources().getDimensionPixelSize(E_dimens.e_size_20));
		
		voteCommend.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View paramView) {
				// TODO Auto-generated method stub
				//星级评论
				UserUtils.needLogin(mContext, new OnLoginFinishListener() {
					
					@Override
					public void loginCompleted(boolean completed) {
						// TODO Auto-generated method stub
						IntentUtils.goWithBeanForResult(mContext, GameVoteActivity.class, mGameItemBean, RequestCode.REQ_GAME_COMMEND);
					}
				});
				
//				Intent it = new Intent(mContext, GameVoteActivity.class);
//				mContext.startActivity(it);
			}
		});
//		//測試專用
//		avgStar.setText("4.0");
//		starImages.setStarIVSelectImage(E_drawable.efun_pd_star_yellow_select);
//		starImages.setStarIVUnSelectImage(E_drawable.efun_pd_star_grey_unselect);
//		starImages.setStarWidth(E_dimens.e_size_50);
//		starImages.createdStarBarWidthGrey(4);
//		//获取实际经验的经验条的宽度和高度
//		float width = (float) (0.8*getResources().getDimensionPixelSize(E_dimens.e_size_480));
////		float width = (float) (0.8*(starLevel5.getWidth()));
//		starLevel5.setRightwidth(width);
//		starLevel5.setMwidth(getResources().getDimensionPixelSize(E_dimens.e_size_480));
//		starLevel5.setMheight(getResources().getDimensionPixelSize(E_dimens.e_size_20));
//		starLevel5.invalidate();
//		
//		starLevel4.setRightwidth(width);
//		starLevel4.setMwidth(getResources().getDimensionPixelSize(E_dimens.e_size_480));
//		starLevel4.setMheight(getResources().getDimensionPixelSize(E_dimens.e_size_20));
//		starLevel4.invalidate();
//		
//		starLevel3.setRightwidth(width);
//		starLevel3.setMwidth(getResources().getDimensionPixelSize(E_dimens.e_size_480));
//		starLevel3.setMheight(getResources().getDimensionPixelSize(E_dimens.e_size_20));
//		starLevel3.invalidate();
//		
//		starLevel2.setRightwidth(width);
//		starLevel2.setMwidth(getResources().getDimensionPixelSize(E_dimens.e_size_480));
//		starLevel2.setMheight(getResources().getDimensionPixelSize(E_dimens.e_size_20));
//		starLevel2.invalidate();
//		
//		starLevel1.setRightwidth(width);
//		starLevel1.setMwidth(getResources().getDimensionPixelSize(E_dimens.e_size_480));
//		starLevel1.setMheight(getResources().getDimensionPixelSize(E_dimens.e_size_20));
//		starLevel1.invalidate();
		
//===============================================================================================================================		
		listCommend.addHeaderView(headerCommend);
		listCommend.setAdapter(mGameCommendAdapter);
		listCommend.setPullRefreshEnable(true);
		listCommend.setPullLoadEnable(true);
		listCommend.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				// 玩家评论各个子项点击处理事件

			}
		});
		listCommend.setXListViewListener(new IXListViewListener() {
			
			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				mCurrentPage = 0;
				requestWithoutDialog(new BaseRequestBean[]{ createVotes(mCurrentPage+"")});
			}
			
			@Override
			public void onLoadMore() {
				// TODO Auto-generated method stub
				mCurrentPage++;
				requestWithoutDialog(new BaseRequestBean[]{ createVotes(mCurrentPage+"")});
			}
		});
		
		
		containerDetail = new LinearLayout(mContext);
		containerDetail.setOrientation(LinearLayout.VERTICAL);
		params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		containerDetail.addView(listCommend, params);

//		commendBottom = ViewUtils.createBottom(mContext);
//		iconDetail = (ImageView) commendBottom.findViewById(E_id.item_icon);
//		textDetail = (TextView) commendBottom.findViewById(E_id.item_text);
//		iconDetail.setBackgroundResource(E_drawable.efun_pd_commend_selector);
//		textDetail.setText(E_string.efun_pd_i_wanna_commend);
//		containerDetail.addView(commendBottom);
//		// 评论按钮
//		commendBottom.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				IntentUtils.goWithBeanForResult(mContext,
//						GameCommendActivity.class, mGameItemBean,
//						RequestCode.REQ_GAME_COMMEND);
//				commendBottom.setVisibility(View.INVISIBLE);
//			}
//		});

		groupCommend.addView(containerDetail, LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		groups[2] = groupCommend;
		return groups;
	}

//	private View commendBottom;

	// private View createBottom() {
	// return mLayoutInflater.inflate(E_layout.efun_pd_game_detail_tab_bottom,
	// null);
	// }

	@Override
	public void onResume() {
		super.onResume();
//		if (commendBottom != null) {
//			if (commendBottom.getVisibility() == View.INVISIBLE) {
//				commendBottom.setVisibility(View.VISIBLE);
//			}
//		}
	}


	@Override
	public void onSuccess(int requestType, BaseResponseBean baseResponse) {
		super.onSuccess(requestType, baseResponse);
		if (requestType == IPlatformRequest.REQ_GAME_DETAIL) {
			GameDetailResponse response = (GameDetailResponse) baseResponse;
			final GameDetailBean bean = response.getGameDetailBean();
			mSwitchV.setScreenOrientation(screenOrientation);
			if(!EfunStringUtil.isEmpty(bean.getVideoUrl())){
				mSwitchV.setVideoStatue(true);
			}else{
				mSwitchV.setVideoStatue(false);
			}
			mSwitchV.loadedData(bean.getBiggroup());
			mSwitchV.setOnEfunItemClickListener(new OnEfunItemClickListener() {
				@Override
				public void onItemClick(int position) {
					if(position == 0 && !EfunStringUtil.isEmpty(bean.getVideoUrl())){
						IntentUtils.go2VideoWeb(mContext, Web.WEB_GO_GAME,bean);
					}
				}
			});

			gameIntroDetail.setText(bean.getDescription());// 设置游戏介绍内容
			gameVersionDetail.setText(bean.getNewVersionDesc());//设置游戏版本内容
		} else if (requestType == IPlatformRequest.REQ_GAME_NEWS_LIST) {
			// 设置游戏资讯页面相关内容
			GameNewsResponse response = (GameNewsResponse) baseResponse;
			int newSize = 0;
			for (int i = 0; i < response.getGameNewsList().size(); i++) {
				if (response.getGameNewsList().get(i).getType() == 1) {
					newSize++;
				}
			}
			int strategySize = 0;
			for (int i = 0; i < response.getGameNewsList().size(); i++) {
				if (response.getGameNewsList().get(i).getType() == 2) {
					strategySize++;
				}
			}
			mGameSummaryAdapter.setNewsSize(newSize);
			mGameSummaryAdapter.setStrategysSize(strategySize);
			mGameSummaryAdapter.append(response.getGameNewsList());
		} else if (requestType == IPlatformRequest.REQ_GAME_COMMEND_LIST) {
			// 设置玩家评论页面相关内容
//			GameCommendListResponse response = (GameCommendListResponse) baseResponse;
//			mGameCommendAdapter.append(response.getGameCommendList());
			GameVoteResponse response = (GameVoteResponse) baseResponse;
			GameVoteBean bean = response.getGameCommend();
			if (bean.getCode().equals(HttpParam.RESULT_1000)) {
				if(mCurrentPage == 0){
					avgStar.setText(bean.getmVoteStatisBean().getAvgRating()+"");
					starImages.createdStarBarWidthGrey((new BigDecimal(bean.getmVoteStatisBean().getAvgRating()).setScale(0, BigDecimal.ROUND_HALF_UP).intValue()));
					//获取实际经验的经验条的宽度和高度
					float width = getResources().getDimensionPixelSize(E_dimens.e_size_480);
					starLevel5.setRightwidth(bean.getmVoteStatisBean().getStar5Votes() * width/bean.getmVoteStatisBean().getTotalVotes());
					starLevel5.invalidate();
					
					starLevel4.setRightwidth(bean.getmVoteStatisBean().getStar4Votes() * width/bean.getmVoteStatisBean().getTotalVotes());
					starLevel4.invalidate();
					
					starLevel3.setRightwidth(bean.getmVoteStatisBean().getStar3Votes() * width/bean.getmVoteStatisBean().getTotalVotes());
					starLevel3.setCurColor(E_color.e_f9af2f);
					starLevel3.invalidate();
					
					starLevel2.setRightwidth(bean.getmVoteStatisBean().getStar2Votes() * width/bean.getmVoteStatisBean().getTotalVotes());
					starLevel2.setCurColor(E_color.e_f36c60);
					starLevel2.invalidate();
					
					starLevel1.setRightwidth(bean.getmVoteStatisBean().getStar1Votes() * width/bean.getmVoteStatisBean().getTotalVotes());
					starLevel1.setCurColor(E_color.e_f36c60);
					starLevel1.invalidate();
					Log.e("efun", "voteCommend:"+bean.isCanVote());
					if(bean.isCanVote()){
						voteCommend.setClickable(true);
						voteCommend.setText(E_string.efun_pd_games_write_commend);
					}else{
						voteCommend.setClickable(false);
						voteCommend.setText(E_string.efun_pd_games_write_commend_finish);
					}
					
					mGameCommendAdapter.refresh(bean.getmVoteBeans());
					if(bean.getmVoteBeans() == null || bean.getmVoteBeans().size() == 0){//沒有數據的時候
						listCommend.setPullLoadEnable(false);
						listCommend.stopRefresh();
					}else{
						listCommend.stopRefresh();
					}
				}else if(mCurrentPage > 0){
					mGameCommendAdapter.append(bean.getmVoteBeans());
					listCommend.stopLoadMore();
				}
				
			}
			
			
			
			
		} else if (requestType == IPlatformRequest.REQ_GAME_PRAISE) {
			GamePraiseResponse response = (GamePraiseResponse) baseResponse;
			GamePraiseBean bean = response.getGamePraiseBean();
			if (bean.getCode().equals(HttpParam.RESULT_1000)) {
				zanIcon.setEnabled(false);
				zanIcon.setBackgroundResource(E_drawable.efun_pd_zan_click);
				if (((OnUpdateListener) IPlatApplication.get()
						.getOnEfunListener()) != null) {
					((OnUpdateListener) IPlatApplication.get()
							.getOnEfunListener()).onUpdate();
				}
			} else if (bean.getCode().equals(HttpParam.RESULT_1100)) {
				zanIcon.setEnabled(false);
				zanIcon.setBackgroundResource(E_drawable.efun_pd_zan_click);
			}
			ToastUtils.toast(mContext, bean.getMessage());
		}
	}

	@Override
	public void onClickNotifyText(int requestType) {
		super.onClickNotifyText(requestType);
//		if (requestType == IPlatformRequest.REQ_GAME_COMMEND_LIST) {
//			commendBottom.performClick();
//		}
	}

	/**
	 * 设置需要请求数据
	 */
	@Override
	public boolean needRequestData() {
		return true;
	}

	/**
	 * 请求数据
	 */
	@Override
	public BaseRequestBean[] needRequestDataBean() {

		GameDetailRequest detailRequest = new GameDetailRequest(gameCode, "",
				"", HttpParam.PLATFORM_AREA, HttpParam.PLATFORM);
		detailRequest.setReqType(IPlatformRequest.REQ_GAME_DETAIL);

		GameNewsRequest newsRequest = new GameNewsRequest(gameCode,
				HttpParam.PLATFORM_AREA,HttpParam.GAME_DETAIL_BAHA);
		newsRequest.setReqType(IPlatformRequest.REQ_GAME_NEWS_LIST);

		GameVoteRequest votesRequest = createVotes(mCurrentPage+"");
//		commendListRequest.setReqType(IPlatformRequest.REQ_GAME_COMMEND_LIST);
		return new BaseRequestBean[] { detailRequest, newsRequest, votesRequest };
	}

	@Override
	public void init(Bundle bundle) {

		headerIcon = (ImageView) findViewById(E_id.item_icon);
		// vedioIcon = (ImageView) findViewById(E_id.item_button);//视频
		gameDownIcon = (TextView) findViewById(E_id.item_button);// 游戏下载
		zanIcon = (ImageView) findViewById(E_id.item_detail_zan_icon);// 点赞
		zanIcon.setBackgroundResource(E_drawable.efun_pd_zan_icon);
		gameVersionCode = (TextView) findViewById(E_id.item_count);
		gameSize = (TextView) findViewById(E_id.item_content);
		mCategory = (TextView) findViewById(E_id.item_game_type);
		mPagerTabs = (PagerTab) findViewById(E_id.pager_view_tab);

		// vedioIcon.setBackgroundResource(E_drawable.efun_pd_game_video_selector);

		if (bundle != null) {
			mGameItemBean = (GameItemBean) bundle
					.getSerializable(Const.BEAN_KEY);
			gameTitle = mGameItemBean.getGameName();
			gameVersionCode.setText(getString(E_string.efun_pd_game_version)
					+ mGameItemBean.getVersion());
			gameSize.setText(getString(E_string.efun_pd_game_size)
					+ mGameItemBean.getPackageSize());
			// mCategory.setColor(getResources().getColor(E_color.e_00aaeb));
			mCategory.setText(mGameItemBean.getGameType());
			ImageManager.displayImage(mGameItemBean.getSmallpic(), headerIcon,
					ImageManager.IMAGE_SQUARE);
			gameCode = mGameItemBean.getGameCode();
			if (mGameItemBean.getPic_display().equals("0")) {
				screenOrientation = true;
			}
			// vedioIcon.setOnClickListener(new OnClickListener() {
			// @Override
			// public void onClick(View v) {
			// IntentUtils.go2WebForVideo(mContext,
			// mGameItemBean.getVideoUrl());
			// }
			// });

			mDownloadChoice = PopUtils.createDownLoadAreaChose(
					mContext,
					gameDownIcon,
					mContext.getResources().getStringArray(
							E_array.efun_pd_pop_chose_area_download),
					new OnEfunItemClickListener() {

						@Override
						public void onItemClick(int position) {
							// TODO Auto-generated method stub
							if (position == 0) {
								if (mGameItemBean.getStatus() == AppStatus.UPDATE) {
									StatLogInfoUtils.setStaticLogInfo(mContext,
											mGameItemBean.getGameCode(),
											HttpParam.GAMELIST,
											HttpParam.UPDATECLICK,
											HttpParam.GAME_TW);
								} else {
									StatLogInfoUtils.setStaticLogInfo(mContext,
											mGameItemBean.getGameCode(),
											HttpParam.GAMELIST,
											HttpParam.DOWNLOADCLICK,
											HttpParam.GAME_TW);
								}
								AppUtils.download(mContext,
										mGameItemBean.getAndroidDownload());
							} else {
								if (mGameItemBean.getStatus() == AppStatus.UPDATE) {
									StatLogInfoUtils.setStaticLogInfo(mContext,
											mGameItemBean.getGameCode(),
											HttpParam.GAMELIST,
											HttpParam.UPDATECLICK,
											HttpParam.GAME_HK);
								} else {
									StatLogInfoUtils.setStaticLogInfo(mContext,
											mGameItemBean.getGameCode(),
											HttpParam.GAMELIST,
											HttpParam.DOWNLOADCLICK,
											HttpParam.GAME_HK);
								}
								AppUtils.download(mContext,
										mGameItemBean.getHkAndroidDownloadURL());
							}
						}
					});

			mStartAppChoice = PopUtils.createStartAPPChose(
					mContext,
					gameDownIcon,
					mContext.getResources().getStringArray(
							E_array.efun_pd_pop_chose_area_download),
					new OnEfunItemClickListener() {

						@Override
						public void onItemClick(int position) {
							// TODO Auto-generated method stub
							if (position == 0) {
								StatLogInfoUtils.setStaticLogInfo(mContext,
										mGameItemBean.getGameCode(),
										HttpParam.GAMELIST, HttpParam.STARTAPP,
										HttpParam.GAME_TW);
								AppUtils.startApp(mContext,
										mGameItemBean.getPackageName());
							} else {
								StatLogInfoUtils.setStaticLogInfo(mContext,
										mGameItemBean.getGameCode(),
										HttpParam.GAMELIST, HttpParam.STARTAPP,
										HttpParam.GAME_HK);
								AppUtils.startApp(mContext,
										mGameItemBean.getHkPackageName());
							}
						}
					});

			if (mGameItemBean != null) {
				switch (mGameItemBean.getStatus()) {
				case AppStatus.DOWNLOAD:
					gameDownIcon.setText(E_string.efun_pd_game_download);
					gameDownIcon
							.setBackgroundResource(E_drawable.efun_pd_game_download_icon_bg);
					break;
				case AppStatus.START:
					gameDownIcon.setText(E_string.efun_pd_game_start);
					gameDownIcon
							.setBackgroundResource(E_drawable.efun_pd_game_start_icon_bg);
					break;
				case AppStatus.UPDATE:
					gameDownIcon.setText(E_string.efun_pd_game_update);
					gameDownIcon
							.setBackgroundResource(E_drawable.efun_pd_game_update_icon_bg);
					break;
				}
			} else {
				gameDownIcon.setText(E_string.efun_pd_game_download);
				gameDownIcon
						.setBackgroundResource(E_drawable.efun_pd_game_download_icon_bg);
			}

			gameDownIcon.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (mGameItemBean != null) {
						switch (mGameItemBean.getStatus()) {
						case AppStatus.START:
							if (AppUtils.isAppInstalled(mContext,
									mGameItemBean.getPackageName())
									&& !AppUtils.isAppInstalled(mContext,
											mGameItemBean.getHkPackageName())) {
								StatLogInfoUtils.setStaticLogInfo(mContext,
										mGameItemBean.getGameCode(),
										HttpParam.GAMELIST, HttpParam.STARTAPP,
										HttpParam.GAME_TW);
								AppUtils.startApp(mContext,
										mGameItemBean.getPackageName());
							} else if (!AppUtils.isAppInstalled(mContext,
									mGameItemBean.getPackageName())
									&& AppUtils.isAppInstalled(mContext,
											mGameItemBean.getHkPackageName())) {
								StatLogInfoUtils.setStaticLogInfo(mContext,
										mGameItemBean.getGameCode(),
										HttpParam.GAMELIST, HttpParam.STARTAPP,
										HttpParam.GAME_HK);
								AppUtils.startApp(mContext,
										mGameItemBean.getHkPackageName());
							} else {
								mStartAppChoice
										.showPopWindow(PopWindow.POP_WINDOW_CHOSE_START_APP);
							}
							break;
						case AppStatus.UPDATE:
							if (AppUtils.isAppInstalled(mContext,
									mGameItemBean.getPackageName())
									&& !AppUtils.isAppInstalled(mContext,
											mGameItemBean.getHkPackageName())) {
								StatLogInfoUtils.setStaticLogInfo(mContext,
										mGameItemBean.getGameCode(),
										HttpParam.GAMELIST,
										HttpParam.UPDATECLICK,
										HttpParam.GAME_TW);
								AppUtils.download(mContext,
										mGameItemBean.getAndroidDownload());
							} else if (!AppUtils.isAppInstalled(mContext,
									mGameItemBean.getPackageName())
									&& AppUtils.isAppInstalled(mContext,
											mGameItemBean.getHkPackageName())) {
								StatLogInfoUtils.setStaticLogInfo(mContext,
										mGameItemBean.getGameCode(),
										HttpParam.GAMELIST,
										HttpParam.UPDATECLICK,
										HttpParam.GAME_HK);
								AppUtils.download(mContext,
										mGameItemBean.getHkAndroidDownloadURL());
							} else {
								mDownloadChoice
										.showPopWindow(PopWindow.POP_WINDOW_CHOSE_DOWNLOAD_AREA);
							}
							break;
						case AppStatus.DOWNLOAD:
							if (!EfunStringUtil.isEmpty(mGameItemBean
									.getHkAndroidDownloadURL())) {
								mDownloadChoice
										.showPopWindow(PopWindow.POP_WINDOW_CHOSE_DOWNLOAD_AREA);
							} else {
								StatLogInfoUtils.setStaticLogInfo(mContext,
										mGameItemBean.getGameCode(),
										HttpParam.GAMELIST,
										HttpParam.DOWNLOADCLICK,
										HttpParam.GAME_TW);
								AppUtils.download(mContext,
										mGameItemBean.getAndroidDownload());
							}
							break;
						}
					}
					// 游戏详情页面的下载事件
				}
			});

		}

		mViewPager = (GameVPager) findViewById(E_id.pager_view_v4);

		VPagerAdapter mAdapter = new VPagerAdapter(groups);
		mViewPager.setAdapter(mAdapter);

		mPagerTabs.setTab(E_layout.efun_pd_pager_tab_textview);
		mPagerTabs.setTabSelectedColor(E_color.e_5aa9ff);
		mPagerTabs.setTitles(E_array.efun_pd_game_detail_v_tab);
		mPagerTabs.setLine(E_drawable.efun_pd_blue_line_each);
		mPagerTabs.setPagerAdapter(mViewPager);
		mPagerTabs.setSelectedItem(mViewPager, 0);

		if (UserUtils.isLogin()) {
			zanIcon.setVisibility(View.VISIBLE);
		} else {
			zanIcon.setVisibility(View.GONE);
		}
		zanIcon.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				UserUtils.needLogin(mContext, new OnLoginFinishListener() {
					@Override
					public void loginCompleted(boolean completed) {
						requestWithDialog(createLike(),
								getString(E_string.efun_pd_loading_msg_commend));
					}
				});

			}
		});

	}

	private BaseRequestBean[] createLike() {
		GamePraiseRequest praiseRequest = new GamePraiseRequest(gameCode);
		if (IPlatApplication.get().getUser() != null) {
			praiseRequest.setSign(IPlatApplication.get().getUser().getSign());
			praiseRequest.setTimestamp(IPlatApplication.get().getUser()
					.getTimestamp());
		}
		praiseRequest.setReqType(IPlatformRequest.REQ_GAME_PRAISE);
		return new BaseRequestBean[] { praiseRequest };
	}
	
	private GameVoteRequest createVotes(String curPage) {
		GameVoteRequest voteRequest = new GameVoteRequest();
		if (IPlatApplication.get().getUser() != null) {
			voteRequest.setUid(IPlatApplication.get().getUser().getUid());
			voteRequest.setSign(IPlatApplication.get().getUser().getSign());
			voteRequest.setTimestamp(IPlatApplication.get().getUser()
					.getTimestamp());
		}
		voteRequest.setPlatform(HttpParam.PLATFORM_AREA);
		voteRequest.setVoteType(HttpParam.GAME);
		voteRequest.setEntityid(gameCode);
		voteRequest.setCurrentPage(curPage);
		voteRequest.setPageSize(mPageSize);
		voteRequest.setVersion(HttpParam.PLATFORM);
		voteRequest.setPackageVersion(AppUtils.getAppVersionName(mContext));
		voteRequest.setFromType(HttpParam.APP);
		voteRequest.setLanguage(HttpParam.LANGUAGE);
		
		voteRequest.setReqType(IPlatformRequest.REQ_GAME_COMMEND_LIST);
		return voteRequest;
	}

	@Override
	public int LayoutId() {
		return E_layout.efun_pd_game_detail;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == RequestCode.REQ_GAME_COMMEND
				&& resultCode == ResultCode.RST_GAME_COMMEND) {
			GameVoteItemBean bean = (GameVoteItemBean) data
					.getSerializableExtra(Const.BEAN_KEY);
			mGameCommendAdapter.insert(bean);
			voteCommend.setClickable(false);
			voteCommend.setText(E_string.efun_pd_games_write_commend_finish);
			hasData(IPlatformRequest.REQ_GAME_COMMEND_LIST);
		}
	}
}
