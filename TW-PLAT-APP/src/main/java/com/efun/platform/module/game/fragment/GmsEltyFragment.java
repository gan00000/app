package com.efun.platform.module.game.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.efun.platform.AndroidScape.E_id;
import com.efun.platform.AndroidScape.E_layout;
import com.efun.platform.AndroidScape.E_string;
import com.efun.platform.IPlatApplication;
import com.efun.platform.http.dao.impl.IPlatformRequest;
import com.efun.platform.http.request.bean.BaseRequestBean;
import com.efun.platform.http.request.bean.GameListRequest;
import com.efun.platform.http.response.bean.BaseResponseBean;
import com.efun.platform.http.response.bean.GameListResponse;
import com.efun.platform.module.base.ElasticityFragment;
import com.efun.platform.module.base.impl.OnUpdateListener;
import com.efun.platform.module.game.activity.GameAchieveSysListActivity;
import com.efun.platform.module.game.activity.GameDetailActivity;
import com.efun.platform.module.game.adapter.GameAdapter;
import com.efun.platform.module.game.bean.GameItemBean;
import com.efun.platform.module.utils.IntentUtils;
import com.efun.platform.module.utils.ProcessDatasUtils;
import com.efun.platform.module.utils.TrackingGoogleUtils;
import com.efun.platform.module.utils.TrackingUtils;
import com.efun.platform.module.utils.ViewUtils;
import com.efun.platform.utils.Const.HttpParam;
import com.efun.platform.widget.list.XListView.IXListViewListener;

/**
 * 游戏列表
 * 
 * @author Jesse
 * 
 */
public class GmsEltyFragment extends ElasticityFragment {
	private GameAdapter mAdapter;
	private int mCurrentPage = 1;
	private String mPageSize = "10";

	@Override
	public View[] addHeaderViews() {
		if (IPlatApplication.get().getUser() != null) {
			// 用戶有登錄
			if (ProcessDatasUtils.getGameAchieveSysTextInfo() != null) {
				if (ProcessDatasUtils.getGameAchieveSysTextInfo().isOpen()) {
					View view = ViewUtils.createView(getActivity(),
							E_layout.efun_pd_game_header_view_item);
					return new View[] { view };
				} else {
					return null;
				}
			} else {
				return null;
			}

		} else {
			return null;
		}
	}

	@Override
	public void init(Bundle bundle) {
		mAdapter = new GameAdapter(getActivity());
		if (IPlatApplication.get().getUser() != null) {// 用戶有登錄
			if (ProcessDatasUtils.getGameAchieveSysTextInfo() != null) {
				if (ProcessDatasUtils.getGameAchieveSysTextInfo().isOpen()) {
					mViews[0].setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							TrackingUtils.track(getActivity(), TrackingUtils.ACTION_GAME, TrackingUtils.NAME_GAME_ACHIEVE_SYS);
//							TrackingGoogleUtils.track(TrackingGoogleUtils.ACTION_GAME, TrackingGoogleUtils.NAME_GAME_ACHIEVE_SYS);
							Intent it = new Intent(getActivity(),
									GameAchieveSysListActivity.class);
							getActivity().startActivity(it);
						}
					});
				}
			}
		}
		mListView.setPullLoadEnable(true);
		mListView.setXListViewListener(new IXListViewListener() {
			@Override
			public void onRefresh() {
				requestWithoutDialog(new BaseRequestBean[] { createRequest(0) });
			}

			@Override
			public void onLoadMore() {
				requestWithoutDialog(new BaseRequestBean[] { createRequest(mCurrentPage) });
			}
		});
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(final AdapterView<?> parent,
					final View view, final int position, long id) {
				// 各个游戏子项点击处理事件
				if (parent.getAdapter() != null) {
					final GameItemBean bean = (GameItemBean) parent
							.getAdapter().getItem(position);
					TrackingUtils.track(getActivity(), TrackingUtils.ACTION_GAME, TrackingUtils.NAME_GAME_DETAIL+"_"+bean.getGameCode());
//					TrackingGoogleUtils.track(TrackingGoogleUtils.ACTION_GAME, bean.getGameName()+TrackingGoogleUtils.NAME_GAME_DETAIL);
					IntentUtils.goWithBeanForResult(getActivity(),
							GameDetailActivity.class, bean,
							new OnUpdateListener() {
								@Override
								public void onUpdate() {
									int visiblePosition = mListView
											.getFirstVisiblePosition();
									View view = mListView.getChildAt(position
											- visiblePosition);
									View v = mListView.getAdapter().getView(
											position, view, mListView);
									TextView text = (TextView) v
											.findViewById(E_id.item_count);
									text.setText((bean.getLike() + 1)
											+ getActivity()
													.getString(
															E_string.efun_pd_game_item_zan));
								}
							});
				}
			}
		});
	}

	@Override
	public void onSuccess(int requestType, BaseResponseBean baseResponse) {
		super.onSuccess(requestType, baseResponse);
		if (requestType == IPlatformRequest.REQ_GAME_LIST) {
			GameListResponse bean = (GameListResponse) baseResponse;
			GameListRequest reqBean = (GameListRequest) bean.getRequestBean();
			if (reqBean.getCurrentPage().equals("1")) {
				mAdapter.refresh(bean.getGameList());
				mCurrentPage = 1;
				mListView.stopRefresh();
			} else {
				mAdapter.append(bean.getGameList());
				mCurrentPage++;
				mListView.stopLoadMore();
			}
			if (bean.getPageInfoBean().getPageIndex() == bean.getPageInfoBean()
					.getTotalPage()) {
				mListView.setPullLoadEnable(false);
			} else {
				mListView.setPullLoadEnable(true);
			}
		}
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

	private GameListRequest createRequest(int currentPage) {
		++currentPage;
		GameListRequest request = new GameListRequest();
		request.setReqType(IPlatformRequest.REQ_GAME_LIST);
		request.setCurrentPage("" + currentPage);
		request.setPageSize(mPageSize);
		request.setPlateform(HttpParam.PLATFORM_AREA);
		return request;
	}
}
