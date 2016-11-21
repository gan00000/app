package com.efun.platform.module.find;

import android.content.Intent;
import android.view.View;

import com.efun.platform.AndroidScape.E_drawable;
import com.efun.platform.AndroidScape.E_string;
import com.efun.platform.module.WebWithJSEltyFragment;
import com.efun.platform.module.base.BaseTabFragment;
import com.efun.platform.widget.TitleView;
/**
 * 发现Tab页面
 * @author itxuxxey
 *
 */
public class FindFragment extends BaseTabFragment{
	
	@Override
	public void initTitle(TitleView titleView) {
//		titleView.setTitleCenterRes(E_string.efun_pd_game, false);
		titleView.setTitleCenterRes(E_string.efun_pd_tab_find, false);
		titleView.setTitleLeftStatus(View.GONE);
		titleView.setTitleRightRes(E_drawable.efun_pd_web_title_refresh_selector);
		
//		titleView.setPopWindowEnable(PopWindow.POP_WINDOW_CODE,null);
//		titleView.setTitleBarBackgroundRes(E_color.e_ffae42);
//		titleView.setTitleCenterTextColor(Color.WHITE);
//		titleView.setVisibility(View.GONE);
	}

	@Override
	public String initContent() {
//		return GmsEltyFragment.class.getName();
		return WebWithJSEltyFragment.class.getName();
	}
	
	@Override
	public void onClickRight() {
		// TODO Auto-generated method stub
		((WebWithJSEltyFragment) mContentFragment).checkPage();
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
	}
}
