package com.efun.platform.module.summary;

import android.content.Intent;
import android.view.View;

import com.efun.platform.AndroidScape.E_drawable;
import com.efun.platform.module.base.BaseTabFragment;
import com.efun.platform.module.dimensionalcode.activity.DimensionalCodeActivity;
import com.efun.platform.module.summary.fragment.SmryEltyFragment;
import com.efun.platform.module.utils.TrackingGoogleUtils;
import com.efun.platform.widget.TitleView;

/**
 * 资讯Tab页面
 * @author itxuxxey
 * 
 */
public class SummaryFragment extends BaseTabFragment {
	@Override
	public void initTitle(TitleView titleView) {
		titleView.setTitleRightStatus(View.GONE);
		titleView.setTitleRightStatus(View.GONE);
		titleView.setTitleLeftRes(E_drawable.efun_pd_pop_code_selector);
//		titleView.setPopWindowEnable(PopWindow.POP_WINDOW_CODE,null);
	}
	
	@Override
	public String initContent() {
		return SmryEltyFragment.class.getName();
	}
	
	@Override
	public void onClickLeft() {
//		TrackingUtils.track(getContext(),TrackingUtils.ACTION_APP, TrackingUtils.NAME_APP_SCAN);
		TrackingGoogleUtils.track(TrackingGoogleUtils.ACTION_APP, TrackingGoogleUtils.NAME_APP_SCAN);
		Intent it = new Intent(getActivity(),DimensionalCodeActivity.class);
		getActivity().startActivity(it);
	}
	
}
