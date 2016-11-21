package com.efun.platform.module.welfare;

import android.content.Intent;
import android.view.View;

import com.efun.platform.AndroidScape.E_drawable;
import com.efun.platform.AndroidScape.E_string;
import com.efun.platform.module.PopWindow;
import com.efun.platform.module.base.BaseTabFragment;
import com.efun.platform.module.dimensionalcode.activity.DimensionalCodeActivity;
import com.efun.platform.module.utils.TrackingGoogleUtils;
import com.efun.platform.module.welfare.activity.GiftSelfActivity;
import com.efun.platform.module.welfare.fragment.WelfareEltyFragment;
import com.efun.platform.widget.TitleView;

/**
 * 福利Tab頁面
 * @author itxuxxey
 */
public class WelfareFragment extends BaseTabFragment{
	@Override
	public void initTitle(TitleView titleView) {
		titleView.setTitleCenterRes(E_string.efun_pd_welfare, false);
		titleView.setTitleRightRes(E_drawable.efun_pd_welfare_gift_selector);
		titleView.setTitleLeftRes(E_drawable.efun_pd_pop_code_selector);
	}

	@Override
	public String initContent() {
		return WelfareEltyFragment.class.getName();
	}
	
	@Override
	public void onClickRight() {
		// TODO Auto-generated method stub
		Intent it = new Intent(getActivity(), GiftSelfActivity.class);
		startActivity(it);
	}
	
	@Override
	public void onClickLeft() {
		TrackingGoogleUtils.track(TrackingGoogleUtils.ACTION_APP, TrackingGoogleUtils.NAME_APP_SCAN);
		Intent it = new Intent(getActivity(),DimensionalCodeActivity.class);
		getActivity().startActivity(it);
	}
	
}
