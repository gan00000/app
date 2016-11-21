package com.efun.platform.module.utils;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.efun.platform.AndroidScape.E_anim;

public class AnimUtils {
	/**
	 * 頁面動畫，向左邊加載view2
	 * @param context
	 * @param view1
	 * @param view2
	 */
	public static void actAnimation_go(Context context, View view1, View view2)
	{
		Animation shake1 = AnimationUtils.loadAnimation(context, E_anim.efun_pd_exit_go);
		view1.startAnimation(shake1);
		view1.setVisibility(View.GONE);
		Animation shake2 = AnimationUtils.loadAnimation(context, E_anim.efun_pd_enter_go);
		view2.startAnimation(shake2);
		view2.setVisibility(View.VISIBLE);
	}
	/**
	 * 頁面動畫，向右邊加載view1
	 * @param context
	 * @param view1
	 * @param view2
	 */
	public static void actAnimation_back(Context context, View view1, View view2)
	{
		Animation shake1 = AnimationUtils.loadAnimation(context, E_anim.efun_pd_exit_back);
		view1.startAnimation(shake1);
		view1.setVisibility(View.GONE);
		Animation shake2 = AnimationUtils.loadAnimation(context, E_anim.efun_pd_enter_back);
		view2.startAnimation(shake2);
		view2.setVisibility(View.VISIBLE);
	}
}
