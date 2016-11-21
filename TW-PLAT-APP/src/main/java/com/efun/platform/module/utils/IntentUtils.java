package com.efun.platform.module.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;

import com.efun.platform.AndroidScape.E_string;
import com.efun.platform.IPlatApplication;
import com.efun.platform.module.BaseDataBean;
import com.efun.platform.module.RechargeWebActivity;
import com.efun.platform.module.VideoWebActivity;
import com.efun.platform.module.WebActivity;
import com.efun.platform.module.WebWithJsActivity;
import com.efun.platform.module.base.impl.OnEfunListener;
import com.efun.platform.module.game.activity.GameSummaryListActivity;
import com.efun.platform.utils.Const;
import com.efun.platform.utils.Const.Summary;
import com.efun.platform.utils.Const.Web;

/**
 * 跳转帮助类
 * 
 * @author Jesse
 * 
 */
public class IntentUtils {
	/**
	 * 跳转Web页面
	 * 
	 * @param context
	 * @param value
	 */
	public static void go2Web(Context context, int value, BaseDataBean bean) {
		Intent it = new Intent(context, WebActivity.class);
		Bundle bundle = new Bundle();
		bundle.putInt(Web.WEB_GO_KEY, value);
		if(bean!=null){
			bundle.putSerializable(Const.BEAN_KEY, bean);
		}
		it.putExtra(Const.DATA_KEY, bundle);
		context.startActivity(it);
	}
	/**
	 * 跳转Web页面
	 * 
	 * @param context
	 * @param value
	 */
	public static void go2WithJSWeb(Context context, int value, BaseDataBean bean) {
		Intent it = new Intent(context, WebWithJsActivity.class);
		Bundle bundle = new Bundle();
		bundle.putInt(Web.WEB_GO_KEY, value);
		if(bean!=null){
			bundle.putSerializable(Const.BEAN_KEY, bean);
		}
		it.putExtra(Const.DATA_KEY, bundle);
		context.startActivity(it);
	}
	/**
	 * 跳转VideoWebActivity页面
	 * 
	 * @param context
	 * @param value
	 */
	public static void go2VideoWeb(Context context, int value, BaseDataBean bean) {
		Intent it = new Intent(context, VideoWebActivity.class);
		Bundle bundle = new Bundle();
		bundle.putInt(Web.WEB_GO_KEY, value);
		if(bean!=null){
			bundle.putSerializable(Const.BEAN_KEY, bean);
		}
		it.putExtra(Const.DATA_KEY, bundle);
		context.startActivity(it);
	}
	
	/**
	 * 跳转储值Web页面
	 * 
	 * @param context
	 * @param value
	 */
	public static void go2ReChargeWeb(Context context, int value) {
		Intent it = new Intent(context, RechargeWebActivity.class);
		Bundle bundle = new Bundle();
		bundle.putInt(Web.WEB_GO_KEY, value);
		it.putExtra(Const.DATA_KEY, bundle);
		context.startActivity(it);
	}

	/**
	 * 传递 Bean 对象到指定页面
	 * 
	 * @param context
	 * @param clazz
	 * @param bean
	 */
	public static void goWithBean(Context context, Class<? extends FragmentActivity> clazz, BaseDataBean bean) {
		Intent it = new Intent(context, clazz);
		Bundle bundle = new Bundle();
		bundle.putSerializable(Const.BEAN_KEY, bean);
		it.putExtra(Const.DATA_KEY, bundle);
		context.startActivity(it);
	}
	
	/**
	 * 跳转GameSummaryListActivity页面
	 * 
	 * @param context
	 * @param value
	 */
	public static void go2SummaryList(Context context, int value, BaseDataBean bean) {
		Intent it = new Intent(context, GameSummaryListActivity.class);
		Bundle bundle = new Bundle();
		bundle.putInt(Summary.SUMMARY_GO_KEY, value);
		if(bean!=null){
			bundle.putSerializable(Const.BEAN_KEY, bean);
		}
		it.putExtra(Const.DATA_KEY, bundle);
		context.startActivity(it);
	}

	/**
	 * 传递 Bean 对象到指定页面
	 * 
	 * @param context
	 * @param clazz
	 * @param bean
	 */
	public static void goWithBeanForResult(Context context, Class<? extends FragmentActivity> clazz, BaseDataBean bean, OnEfunListener onEfunListener) {
		IPlatApplication.get().setOnEfunListener(onEfunListener);
		Intent it = new Intent(context, clazz);
		Bundle bundle = new Bundle();
		bundle.putSerializable(Const.BEAN_KEY, bean);
		it.putExtra(Const.DATA_KEY, bundle);
		context.startActivity(it);
	}

	/**
	 * 传递 Bean 对象到指定页面(不使用回調監聽)
	 * 
	 * @param context
	 * @param clazz
	 * @param bean
	 */
	public static void goWithBeanForResult(Context context, Class<? extends FragmentActivity> clazz, BaseDataBean bean, int requestCode) {
		Intent it = new Intent(context, clazz);
		Bundle bundle = new Bundle();
		bundle.putSerializable(Const.BEAN_KEY, bean);
		it.putExtra(Const.DATA_KEY, bundle);
		((Activity) context).startActivityForResult(it, requestCode);
	}

	/**
	 * 到指定页面
	 * 
	 * @param context
	 * @param clazz
	 */
	public static void startActivity(Context context, Class<? extends FragmentActivity> clazz) {
		Intent it = new Intent(context, clazz);
		context.startActivity(it);
	}

	public static void startActivityForResult(Context context, Class<? extends FragmentActivity> clazz, int requestCode) {
		Intent it = new Intent(context, clazz);
		((Activity) context).startActivityForResult(it, requestCode);
	}
	
	public static void go2WebForVideo(Context context,String url){
		if(TextUtils.isEmpty(url) || url.equals("null")){
			ToastUtils.toast(context, context.getString(E_string.efun_pd_no_video));
			return;
		}
   	 	Uri uri = Uri.parse(url);   
	    //调用浏览器打开
	    Intent intent = new Intent(Intent.ACTION_VIEW);
	    
        intent.setAction("android.intent.action.VIEW");
        intent.setData(uri);
        //intent.setDataAndType(uri, "video/*");  
        try {
        	context.startActivity(intent);
		} catch (Exception e) {
			ToastUtils.toast(context, context.getString(E_string.efun_pd_error_video_url));
		}
	}
}
