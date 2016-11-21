package com.efun.platform.module.welfare;

import android.content.Intent;
import android.view.View;

import com.efun.platform.AndroidScape.E_drawable;
import com.efun.platform.AndroidScape.E_string;
import com.efun.platform.module.base.BaseTabFragment;
import com.efun.platform.module.dimensionalcode.activity.DimensionalCodeActivity;
import com.efun.platform.module.utils.TrackingGoogleUtils;
import com.efun.platform.module.utils.TrackingUtils;
import com.efun.platform.module.welfare.fragment.HaoKangEltyFragment;
import com.efun.platform.widget.TitleView;

/**
 * 好康Tab頁面
 * @author Jesse
 */
public class HaoKangFragment extends BaseTabFragment{
	@Override
	public void initTitle(TitleView titleView) {
		titleView.setTitleCenterRes(E_string.efun_pd_welfare, false);
		titleView.setTitleRightStatus(View.GONE);
		titleView.setTitleRightStatus(View.GONE);
		titleView.setTitleLeftRes(E_drawable.efun_pd_pop_code_selector);
//		titleView.setPopWindowEnable(PopWindow.POP_WINDOW_CODE,null);
//		titleView.setTitleBarBackgroundRes(E_color.e_fe5a66);
//		titleView.setTitleCenterTextColor(Color.WHITE);
	}

	@Override
	public String initContent() {
		return HaoKangEltyFragment.class.getName();
	}
	
	@Override
	public void onClickLeft() {
		TrackingUtils.track(getContext(),TrackingUtils.ACTION_APP, TrackingUtils.NAME_APP_SCAN);
		TrackingGoogleUtils.track(TrackingGoogleUtils.ACTION_APP, TrackingGoogleUtils.NAME_APP_SCAN);
		Intent it = new Intent(getActivity(),DimensionalCodeActivity.class);
		getActivity().startActivity(it);
	}
}
