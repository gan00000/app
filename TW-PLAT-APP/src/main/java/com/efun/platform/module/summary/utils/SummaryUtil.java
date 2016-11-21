package com.efun.platform.module.summary.utils;

import android.widget.TextView;

import com.efun.platform.AndroidScape.E_color;
import com.efun.platform.AndroidScape.E_drawable;

public class SummaryUtil {
	/**
	 * 修饰资讯头部
	 * @param view
	 * @param type
	 */
	public static void displaySummaryHeader(TextView view,int type){
		switch (type) {
		case 0:
			view.setText("公告");
//			view.setBackgroundResource(E_color.e_83d160);
			view.setBackgroundResource(E_drawable.efun_pd_summary_notices_icon_bg);
			break;
		case 1:
			view.setText("新聞");
//			view.setBackgroundResource(E_color.e_eb4847);
			view.setBackgroundResource(E_drawable.efun_pd_summary_news_icon_bg);
			break;
		case 2:
			view.setText("攻略");
//			view.setBackgroundResource(E_color.e_faca57);
			view.setBackgroundResource(E_drawable.efun_pd_summary_guide_icon_bg);
			break;
		case 4:
			view.setText("活動");
//			view.setBackgroundResource(E_color.e_8058bd);
			view.setBackgroundResource(E_drawable.efun_pd_summary_event_icon_bg);
			break;
		case 7:
			view.setText("視頻");
//			view.setBackgroundResource(E_color.e_8058bd);
			view.setBackgroundResource(E_drawable.efun_pd_summary_video_icon_bg);
			break;
		}
	}
}
