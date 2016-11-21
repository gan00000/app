package com.efun.platform.module.person;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.efun.platform.FrameTabListener;
import com.efun.platform.module.base.BaseTabFragment;
import com.efun.platform.module.person.fragment.PerCenterEltyFragment;
import com.efun.platform.module.utils.UserUtils;
import com.efun.platform.utils.Const.RequestCode;
import com.efun.platform.utils.Const.Tab;
import com.efun.platform.widget.TitleView;

public class PersonalCenterFragment extends BaseTabFragment {
	@Override
	public void initTitle(TitleView titleView) {
		titleView.setVisibility(View.GONE);
		
	}

	@Override
	public String initContent() {
		return PerCenterEltyFragment.class.getName();
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		Log.e("efun", "PersonalCenterFragment:onActivityResult");
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == RequestCode.REQ_SETTING){
			if(UserUtils.isLogin()){
				Log.e("efun", "onactivivyResult:isLogin");
//				getActivity().finish();
			}else{
				Log.e("efun", "onactivivyResult:isnotLogin");
				((FrameTabListener) getActivity()).onTabChange(Tab.TAB_ITEM_TAG_SUMMARY);//跳转到资讯页面
//				getActivity().finish();
			}
		}
	}
}
