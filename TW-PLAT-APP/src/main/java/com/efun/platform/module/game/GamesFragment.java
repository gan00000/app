package com.efun.platform.module.game;

import android.content.Intent;
import android.view.View;

import com.efun.platform.AndroidScape.E_drawable;
import com.efun.platform.AndroidScape.E_string;
import com.efun.platform.module.base.BaseTabFragment;
import com.efun.platform.module.dimensionalcode.activity.DimensionalCodeActivity;
import com.efun.platform.module.game.fragment.GmsEltyFragment;
import com.efun.platform.module.utils.TrackingGoogleUtils;
import com.efun.platform.module.utils.TrackingUtils;
import com.efun.platform.widget.TitleView;
/**
 * 游戏Tab页面
 * @author Jesse
 *
 */
public class GamesFragment extends BaseTabFragment{
	
	@Override
	public void initTitle(TitleView titleView) {
		titleView.setTitleCenterRes(E_string.efun_pd_game, false);
		titleView.setTitleRightStatus(View.GONE);
		titleView.setTitleRightStatus(View.GONE);
		titleView.setTitleLeftRes(E_drawable.efun_pd_pop_code_selector);
//		titleView.setPopWindowEnable(PopWindow.POP_WINDOW_CODE,null);
//		titleView.setTitleBarBackgroundRes(E_color.e_ffae42);
//		titleView.setTitleCenterTextColor(Color.WHITE);
	}

	@Override
	public String initContent() {
		return GmsEltyFragment.class.getName();
	}
	
	@Override
	public void onClickLeft() {
		TrackingUtils.track(getActivity(),TrackingUtils.ACTION_APP, TrackingUtils.NAME_APP_SCAN);
		TrackingGoogleUtils.track(TrackingGoogleUtils.ACTION_APP, TrackingGoogleUtils.NAME_APP_SCAN);
		Intent it = new Intent(getActivity(),DimensionalCodeActivity.class);
		getActivity().startActivity(it);
	}
}
