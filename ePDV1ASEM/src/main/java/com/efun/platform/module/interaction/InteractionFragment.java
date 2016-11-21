package com.efun.platform.module.interaction;

import android.content.Intent;
import android.util.Log;

import com.efun.platform.AndroidScape.E_drawable;
import com.efun.platform.AndroidScape.E_string;
import com.efun.platform.module.WebWithJSEltyFragment;
import com.efun.platform.module.base.BaseTabFragment;
import com.efun.platform.module.utils.IntentUtils;
import com.efun.platform.utils.Const.Web;
import com.efun.platform.widget.TitleView;
/**
 * 交流Tab页面
 * @author itxuxxey
 *
 */
public class InteractionFragment extends BaseTabFragment {
	@Override
	public void initTitle(TitleView titleView) {
		titleView.setTitleCenterRes(E_string.efun_pd_tab_connected, false);
		titleView.setTitleLeftRes(E_drawable.efun_pd_web_title_post_tips_selector);
		titleView.setTitleRightRes(E_drawable.efun_pd_web_title_refresh_selector);
//		titleView.setTitleBarBackgroundRes(E_color.e_00bf8e);
//		titleView.setTitleCenterTextColor(Color.WHITE);
//		titleView.setVisibility(View.GONE);
	}

	@Override
	public String initContent() {
//		return CsEltyFragment.class.getName();
		return WebWithJSEltyFragment.class.getName();
	}
	
	@Override
	public void onClickLeft() {
		// TODO Auto-generated method stub
		//发贴
		IntentUtils.go2WithJSWeb(getActivity(), Web.WEB_GO_CONNECTED_POST_TIPS, null);
	}
	
	@Override
	public void onClickRight() {
		// TODO Auto-generated method stub
		((WebWithJSEltyFragment)mContentFragment).checkPage();
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
	}
}