package com.efun.platform.module.game.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;

import com.efun.platform.AndroidScape.E_id;
import com.efun.platform.AndroidScape.E_layout;
import com.efun.platform.AndroidScape.E_string;
import com.efun.platform.IPlatApplication;
import com.efun.platform.http.dao.impl.IPlatformRequest;
import com.efun.platform.http.request.bean.BaseRequestBean;
import com.efun.platform.http.request.bean.GameAchieveSysListRequest;
import com.efun.platform.http.response.bean.BaseResponseBean;
import com.efun.platform.http.response.bean.GameAchieveSysListResponse;
import com.efun.platform.module.base.ElasticityActivity;
import com.efun.platform.module.base.impl.OnUpdateListener;
import com.efun.platform.module.bean.ConfigInfoBean;
import com.efun.platform.module.game.adapter.AchieveSysAdapter;
import com.efun.platform.module.game.bean.AchieveSysItemBean;
import com.efun.platform.module.game.bean.GameItemBean;
import com.efun.platform.module.utils.AppUtils;
import com.efun.platform.module.utils.IntentUtils;
import com.efun.platform.module.utils.ProcessDatasUtils;
import com.efun.platform.module.utils.ToastUtils;
import com.efun.platform.module.utils.ViewUtils;
import com.efun.platform.utils.Const.HttpParam;
import com.efun.platform.utils.Const.Web;
import com.efun.platform.widget.TitleView;

/**
 * 成就系統
 * @author itxuxxey
 *
 */
public class GameAchieveSysListActivity extends ElasticityActivity {
	private AchieveSysAdapter mAdapter;
	private ArrayList<AchieveSysItemBean> mAchieveSysBeans;
	private TextView titleHeader;

	@Override
	public View[] addHeaderViews() {
		// TODO Auto-generated method stub efun_pd_game_achieve_header_view_item
		View view = ViewUtils.createView(mContext, E_layout.efun_pd_game_achieve_header_view_item);
		titleHeader = (TextView) view.findViewById(E_id.achieve_header);
		return new View[] { view };
	}

	@Override
	public BaseAdapter adapter() {
		// TODO Auto-generated method stub
		return mAdapter;
	}

	@Override
	public void initTitle(TitleView titleView) {
		// TODO Auto-generated method stub
		titleView.setTitleRightStatus(View.GONE);
		titleView.setTitleCenterRes(E_string.efun_pd_games_title_achieve, false);
	}

	@Override
	public boolean needRequestData() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public BaseRequestBean[] needRequestDataBean() {
		// TODO Auto-generated method stub
		GameAchieveSysListRequest newsRequest = new GameAchieveSysListRequest(
				IPlatApplication.get().getUser().getUid(), 
				HttpParam.PLATFORM_AREA, 
				IPlatApplication.get().getUser().getSign(), 
				IPlatApplication.get().getUser().getTimestamp(), 
				HttpParam.PLATFORM, 
				AppUtils.getAppVersionName(mContext), 
				HttpParam.APP, 
				HttpParam.LANGUAGE);
		newsRequest.setReqType(IPlatformRequest.REQ_GAME_ACHIEVE_SYS);
		return new BaseRequestBean[]{newsRequest};
	}

	@Override
	public void init(Bundle bundle) {
		mAdapter = new AchieveSysAdapter(mContext);
		// TODO Auto-generated method stub
		mListView.setPullLoadEnable(false);
		mListView.setOnItemClickListener(new ItemClickListener());
		mViews[0].setOnClickListener(null);
		//20160112添加
		ConfigInfoBean bean = ProcessDatasUtils.getGameAchieveSysTextInfo();
		if(bean != null){
			titleHeader.setText("\""+bean.getSubName()+"\"");
		}
	}
	
	
	@Override
	public void onSuccess(int requestType, BaseResponseBean baseResponse) {
		super.onSuccess(requestType,baseResponse);
		if (requestType==IPlatformRequest.REQ_GAME_ACHIEVE_SYS) {
			GameAchieveSysListResponse bean = (GameAchieveSysListResponse) baseResponse;
			if(bean.getResponse().getCode().equals(HttpParam.RESULT_1000)){
				if(bean.getResponse().getAchieveSysBeans() != null && bean.getResponse().getAchieveSysBeans().size() > 0){
					mAchieveSysBeans = bean.getResponse().getAchieveSysBeans();
					mAdapter.refresh(bean.getResponse().getAchieveSysBeans());
//					titleHeader.setText("\""+bean.getResponse().getExtendedObj()+"\"");//20160112修改
					mListView.stopRefresh();
				}
			}
		}  
	}
	
	class ItemClickListener implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			if(parent.getAdapter()!=null){
				AchieveSysItemBean bean = (AchieveSysItemBean) parent.getAdapter().getItem(position);
				Log.i("efun", "bean.getGameName():"+bean.getGameName());
				IntentUtils.go2WithJSWeb(mContext, Web.WEB_GO_GAME_ACHIEVE, bean);
			}
		}
	}

}
