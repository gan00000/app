package com.efun.platform.module.cs;

import android.graphics.Color;
import android.view.View;

import com.efun.platform.AndroidScape.E_color;
import com.efun.platform.AndroidScape.E_drawable;
import com.efun.platform.AndroidScape.E_string;
import com.efun.platform.module.base.BaseTabFragment;
import com.efun.platform.module.cs.fragment.CsEltyFragment;
import com.efun.platform.widget.TitleView;
/**
 * 客服Tab页面
 * @author Jesse
 *
 */
public class CustomServiceFragment extends BaseTabFragment {
	@Override
	public void initTitle(TitleView titleView) {
		titleView.setTitleCenterRes(E_string.efun_pd_cs, false);
		titleView.setTitleLeftStatus(View.GONE);
		titleView.setTitleRightStatus(View.GONE);
//		titleView.setTitleBarBackgroundRes(E_color.e_00bf8e);
//		titleView.setTitleCenterTextColor(Color.WHITE);
	}

	@Override
	public String initContent() {
		return CsEltyFragment.class.getName();
	}
}