package com.efun.platform.module.utils;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.text.TextUtils;

import com.efun.core.tools.EfunLogUtil;
import com.efun.core.tools.EfunStringUtil;
import com.efun.platform.AndroidScape.E_string;
import com.efun.platform.module.bean.AppInfoBean;

public class AppUtils {
	/**
	 * 判断是否安装
	 * @param context
	 * @param packageName
	 * @return
	 */
	public static boolean isAppInstalled(Context context,String packageName) {
		try {
			context.getPackageManager().getApplicationInfo(packageName, 0);
			return true;
		} catch (PackageManager.NameNotFoundException e) {
			return false;
		}
	}
	
	/**
	 * 判断APP是否有更新
	 * 
	 * @param context
	 * @param packageName
	 * @param version
	 * @return
	 */
	public static boolean isAppUpdate(Context context, String packageName,String version) {
		if(version.equals("null") || EfunStringUtil.isEmpty(version)){
			return false;
		}
		int curVerCode = getAppVersionCode(context, packageName);
		if (Integer.parseInt(version) > curVerCode && curVerCode!=0) {
			return true;
		}
		return false;
	}
	
	
	/**
	 * 获取Version Code
	 * @param context
	 * @param packageName
	 * @return
	 */
	public static int getAppVersionCode(Context context, String packageName) {
		if (isAppInstalled(context, packageName)) {
			try {
				PackageInfo packInfo = context.getPackageManager().getPackageInfo(packageName, 0);
				return packInfo.versionCode;
			} catch (NameNotFoundException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}

	/**
	 * 获取Version Code
	 * 
	 * @param context
	 * @param packageName
	 * @return
	 */
	public static int getAppVersionCode(Context context) {
		try {
			PackageInfo packInfo = context.getPackageManager().getPackageInfo(getAppPackageName(context), 0);
			return packInfo.versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return 0;
	}
	/**
	 * 获取Version Code
	 * @param context
	 * @param packageName
	 * @return
	 */
	public static String getAppPackageName(Context context) {
		return context.getPackageName();
	}

	/**
	 * 获取Version Name
	 * 
	 * @param context
	 * @param packageName
	 * @return
	 */
	public static String getAppVersionName(Context context) {
		PackageInfo packInfo;
		try {
			packInfo = context.getPackageManager().getPackageInfo(getAppPackageName(context), 0);
			return packInfo.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return "";

	}
	/**
	 * 通过包名获取应用的activity的类名，打开指定包名的应用相关页面
	 * 
	 * @param packageName
	 * @param context
	 */
	@SuppressLint("NewApi")
	public static void download(Context context, String url) {
		if (!isGooglePayInstalled(context)) {
			ToastUtils.toast(context, context.getResources().getString(E_string.efun_pd_uninstalled_google_play));
//			try {
//				comeDownloadPageInAndroidWeb(context, url);
//			} catch (Exception e) {
//			}
			return;
		}
		try {
			startApp(context, "com.android.vending", url);
		} catch (Exception e) {
			return;
		}
	}
	public static boolean isGooglePayInstalled(Context context){
		return isAppInstalled(context,"com.android.vending");
	}
	/**
	 * 启动应用
	 * @param context
	 * @param packageName
	 */
	public static void startApp(Context context,String packageName){
		startApp(context, packageName, null);
	}
	
	public static void startApp(Context context,String packageName,String url){
		EfunLogUtil.logE("download_url:"+url);
		// 获取应用安装包管理类对象
		PackageManager packageManager = context.getPackageManager();
		try {
			packageManager.getPackageInfo(packageName, 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		Intent intent = new Intent(Intent.ACTION_VIEW);
		if(!TextUtils.isEmpty(url)){
			Uri uriObj = Uri.parse(url);
			intent.setData(uriObj);
		}
		
		Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
		resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		resolveIntent.setPackage(packageName);

		List<ResolveInfo> apps = packageManager.queryIntentActivities(
				resolveIntent, 0);
		ResolveInfo ri = null;
		if(apps!=null && apps.size() != 0){
			ri = apps.iterator().next();
		}
		if (ri != null) {
			String className = ri.activityInfo.name;
			intent.addCategory(Intent.CATEGORY_LAUNCHER);
			ComponentName cn = new ComponentName(packageName,className);
			intent.setComponent(cn);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(intent);
		}
	}
	
	private static void startLineApp(Context context,String packageName,String url){
		// 获取应用安装包管理类对象
		PackageManager packageManager = context.getPackageManager();
		try {
			packageManager.getPackageInfo(packageName, 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setPackage(packageName);
		if(!TextUtils.isEmpty(url)){
			Uri uriObj = Uri.parse(url);
			intent.setData(uriObj);
		}
		context.startActivity(intent);
	}

	/**
	 * 启动网页去加载下载页面
	 * 
	 * @param context
	 * @param url
	 */
	public static void comeDownloadPageInAndroidWeb(Context context, String url) {
		Intent intent = new Intent();
		intent.setAction("android.intent.action.VIEW");
		Uri content_url = Uri.parse(url);
		intent.setData(content_url);
		// Log.i("efun", content_url+"");

		try {
			intent.setClassName("com.android.browser",
					"com.android.browser.BrowserActivity");
			// intent.getComponent();
			context.startActivity(intent);
		} catch (Exception e) {
			try {
				intent.setClassName("com.android.chrome",
						"com.google.android.apps.chrome.Main");
				context.startActivity(intent);
			} catch (Exception e1) {
				context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
			}
		}
	}
	
	/**
	 * 通過包名，查找應用的信息
	 * @param context
	 * @param packageName
	 * @return
	 */
	public static AppInfoBean getAppInfoBeanByPackageName(Context context,String packageName){
		PackageInfo packageInfo;  
		ApplicationInfo info = null;
		try {
			packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
			info = context.getPackageManager().getApplicationInfo(packageName, 0);
		} catch (Exception e) {
			return null;
		}
		AppInfoBean appInfo = new AppInfoBean();
		appInfo.setAppLabel((String) info.loadLabel(context.getPackageManager()));
		appInfo.setAppIcon(info.loadIcon(context.getPackageManager()));
		appInfo.setPkgName(packageName);
		appInfo.setVersionCode(packageInfo.versionCode+"");
		return appInfo;
	}
	/**
	 * 打开Line应用
	 * @param context
	 * @param url
	 */
	public static void openLineAPP(Context context,String url){
		if (!isAppInstalled(context,"jp.naver.line.android")) {
//			try {
//				comeDownloadPageInAndroidWeb(context, url);
//			} catch (Exception e) {				
//			}
			ToastUtils.toast(context, E_string.efun_pd_share_line_error);
			return;
		}
		startLineApp(context, "jp.naver.line.android", url);
	}
	
	/**
	 * 判断应用程序是否在运行
	 * @param context
	 * @param packageName
	 * @return
	 */
	public static Object[] isAppRunning(Context context,String packageName){
		ActivityManager am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> list = am.getRunningTasks(100);
		boolean isAppRunning = false;
		String topActivityName = "";
		for (RunningTaskInfo info : list) {
			if (info.topActivity.getPackageName().equals(packageName)||info.baseActivity.getPackageName().equals(packageName)) {
				isAppRunning = true;
				topActivityName = info.topActivity.getClassName();
				break;
			}
		}
		return new Object[]{isAppRunning,topActivityName};
	}	
}
