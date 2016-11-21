package com.efun.platform.module.cs.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.efun.platform.AndroidScape.E_id;
import com.efun.platform.AndroidScape.E_layout;
import com.efun.platform.AndroidScape.E_string;
import com.efun.platform.http.request.bean.BaseRequestBean;
import com.efun.platform.module.base.FixedActivity;
import com.efun.platform.module.cs.bean.CsQuestionItemBean;
import com.efun.platform.utils.Const;
import com.efun.platform.widget.TitleView;
/**
 * 问题详情页面
 * @author Jesse
 *
 */
public class CsQuestionContentActivity extends FixedActivity{
	
	@Override
	public boolean needRequestData() {
		return false;
	}

	@Override
	public BaseRequestBean[] needRequestDataBean() {
		return null;
	}

	@Override
	public void init(Bundle bundle) {
		TextView mTitle = (TextView) findViewById(E_id.item_text);
		TextView mContent= (TextView) findViewById(E_id.item_content);
		if(bundle!=null){
			CsQuestionItemBean bean = (CsQuestionItemBean) bundle.getSerializable(Const.BEAN_KEY);
			mTitle.setText(bean.getQuestionsTitle());
			mContent.setText(bean.getTheQuestions());
		}
		
	}

	@Override
	public ViewGroup[] needShowLoading() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public int LayoutId() {
		// TODO Auto-generated method stub
		return E_layout.efun_pd_cs_question_content;
	}

	@Override
	public void initTitle(TitleView titleView) {
		titleView.setTitleRightStatus(View.GONE);
		titleView.setTitleCenterRes(E_string.efun_pd_question, false);
	}
}
