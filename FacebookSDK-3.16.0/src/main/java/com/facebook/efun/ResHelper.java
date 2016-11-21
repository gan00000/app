package com.facebook.efun;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

public class ResHelper {

	private static Context context;
	
	

	public static Context getContext() {
		return context;
	}

	public static void setContext(Context context) {
		ResHelper.context = context.getApplicationContext();
	}

	/**
	 * 获取资源
	 * 
	 * @param context
	 * @param packageName
	 * @param resourcesName
	 * @return
	 */
	public static int getResourcesIdByName(Context context, String packageName, String resourcesName) {
		Resources resources = context.getResources();
		int id = resources.getIdentifier(resourcesName, packageName, context.getPackageName());
		if (id == 0) {
			Log.e("EfunResourceUtil", "资源文件读取不到！resourcesName:" + resourcesName);
		}
		return id;
	}

	/**
	 * 获取布局ID
	 * 
	 * @param context
	 * @param resourcesName
	 * @return
	 */
	public static int findLayoutIdByName(Context context, String resourcesName) {
		return getResourcesIdByName(context, "layout", resourcesName);
	}
	/**
	 * 获取 color ID
	 * 
	 * @param context
	 * @param resourcesName
	 * @return
	 */
	public static int findColorIdByName(Context context, String resourcesName) {
		return getResourcesIdByName(context, "color", resourcesName);
	}
	/**
	 * 获取 array ID
	 * 
	 * @param context
	 * @param resourcesName
	 * @return
	 */
	public static int findArrayIdByName(Context context, String resourcesName) {
		return getResourcesIdByName(context, "array", resourcesName);
	}
	/**
	 * 获取String资源ID
	 * 
	 * @param context
	 * @param resourcesName
	 * @return
	 */
	public static int findStringIdByName(Context context, String resourcesName) {
		return getResourcesIdByName(context, "string", resourcesName);
	}
	
	public static int findStringIdByName(String resourcesName) {
		if (context == null) {
			return 0;
		}
		return getResourcesIdByName(context, "string", resourcesName);
	}
	
	public static String findStringByName(String resourcesName) {
		if (context == null) {
			return "";
		}
		return findStringByName(context, resourcesName);
	}

	public static String findStringByName(Context context, String resourcesName) {
		String res = "";
		try {
			res = context.getResources().getString(getResourcesIdByName(context, "string", resourcesName));
		} catch (Exception e) {
			Log.e("efun", "resourcesName:" + resourcesName);
		}
		return res;
	}

	/**
	 * 获取view id资源
	 * 
	 * @param context
	 * @param resourcesName
	 * @return
	 */
	public static int findViewIdByName(Context context, String resourcesName) {
		return getResourcesIdByName(context, "id", resourcesName);
	}

	/**
	 * 获取drawable资源
	 * 
	 * @param context
	 * @param resourcesName
	 * @return
	 */
	public static int findDrawableIdByName(Context context, String resourcesName) {
		return getResourcesIdByName(context, "drawable", resourcesName);
	}

	/**
	 * 获取drawable资源
	 * 
	 * @param context
	 * @param resourcesName
	 * @return
	 */
	public static int findAnimIdByName(Context context, String resourcesName) {
		return getResourcesIdByName(context, "anim", resourcesName);
	}

	public static int findStyleIdByName(Context context, String resourcesName) {
		return getResourcesIdByName(context, "style", resourcesName);
	}

	
	public static int findDimenIdByName(Context context, String resourcesName){
		return getResourcesIdByName(context, "dimen", resourcesName);
	}
	
	/**
	 * 读取Assets中的文本
	 * 
	 * @param ctx
	 * @param filaName
	 * @return
	 */
	public static String getStringFromAssets(Context ctx, String filaName, String format) {
		try {
			InputStream is = ctx.getAssets().open(filaName + "." + format);
			int size = is.available();
			byte[] buffer = new byte[size];
			is.read(buffer);
			is.close();
			return (new String(buffer)).trim();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}	
