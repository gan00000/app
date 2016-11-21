package com.efun.platform.utils;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.efun.core.db.EfunDatabase;

import android.content.Context;
import android.text.TextUtils;

public class StoreUtil {
	/**
	 * 标志文件
	 */
	private static final String Flag_file_name = "Flag.db";
	private static final String Flag_key_first_start = "key_first_start";
	private static final String Flag_value_first_start = "value_first_start";

	/**
	 * 存储是否第一次启动
	 * @param context
	 * @return
	 */
	public static boolean firstStart(Context context) {
		String flag = EfunDatabase.getSimpleString(context,
				Flag_file_name, Flag_key_first_start);
		if (TextUtils.isEmpty(flag)) {
			EfunDatabase.saveSimpleInfo(context, Flag_file_name,Flag_key_first_start,Flag_value_first_start);
			return true;
		}
		return false;
	}
	
	/**
	 * 悬浮按钮存储文件
	 * @author itxuxxey
	 *
	 */
	public static class IPlatDatabaseValues{
		public static final String PLATFORM_FILE = "platform.db";
		public static final String UID = "UID";
		public static final String UNAME="UNAME";
		public static final String DATA_KEY_HOME = "DATA_HOME";
		public static final String DATA_KEY_PERSION="DATA_PERSION";
		public static final String DATA_TOTAL_TIME = "TOTAL_TIME";
		public static final String DATA_FIRST_TIME = "FIRST_TIME";
		public static final String DATA_COUNT_TIME = "COUNT_TIME";
		public static final String DATA_REMAIN_TIME = "REMAIN_TIME";
	}
	
	/**
	 * 追踪文件
	 */
	public static final String Track_file_name = "Track.db";
	public static final String Track_key_start_app = "key_start_app";
	public static final String Track_key_start_app_google="key_start_app_google";
	
	
	/**
	 * 追踪文件
	 */
	public static final String Param_file_name = "param.db";
	
	/**
	 * 推送设置的状态
	 */
	public static final String Param_key_push_flag = "key_push_flag";
	
	/**
	 * 登陆用户信息存储文件
	 */
	public static final String Request_file_name_login_request = "request_login_request.db";
	/**
	 * 登陆用户更新后存储文件
	 */
	public static final String Request_file_name_login_update = "request_login_update.db";
	
	/**
	 * 保存Json 格式数据文件
	 */
	public static final String Json_file_name = "json.db";
	public static final String Json_key_account_response = "key_account_response";
	public static final String Json_key_account_update_response = "key_account_update_response";
	
	/**
	 * 用户登录时间追踪文件
	 */
	public static final String Pamam_key_user_login_oldTime = "User_login_oldTime";
	public static final String Pamam_key_user_login_currentTime = "User_login_currentTime";

	
	
	/**
	 * 判断Json对应Key的Value是否有变化
	 * @param context
	 * @param fileName
	 * @param key
	 * @param jsonKeys
	 * @param jsonValues
	 * @return
	 */
	public static boolean valueChangeNotify(Context context,String fileName,String key,String[] jsonKeys,String[] jsonValues){
		String responseJson2String = getValue(context, fileName, key);
		int lenght = jsonKeys.length;
		String[] result = new String[lenght];
		if (!TextUtils.isEmpty(responseJson2String)) {
			try {
				JSONObject jsonObject = new JSONObject(responseJson2String);
				for (int i = 0; i < lenght; i++) {
					if (jsonObject.has(jsonKeys[i])) {
						result[i] = jsonObject.optString(jsonKeys[i], "");
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
				for (int i = 0; i < result.length; i++) {
					result[i] = "";
				}
			}
		} else {
			for (int i = 0; i < result.length; i++) {
				result[i] = "";
			}
		}
		for (int i = 0; i < lenght; i++) {
			if (result[i] != null && !result[i].equals(jsonValues[i])) {
				return true;
			}
		}
		return false;
	}
	

	
	/**
	 * 批量存储数据
	 * @param context
	 * @param fileName
	 * @param key
	 * @param info
	 */
	public static void saveValues(Context context,String fileName, String[] keys,String[] infos){
		for (int i = 0; i < keys.length; i++) {
			saveValue(context, fileName, keys[i], infos[i]);
		}
	}
	/**
	 * 批量获取存储数据
	 * @param context
	 * @param fileName
	 * @param key
	 * @return
	 */
	public static String[] getValues(Context context,String fileName, String[] keys){
		int lenght = keys.length;
		String[] values = new String[lenght];
		for (int i = 0; i < lenght; i++) {
			values[i]=getValue(context, fileName, keys[i]);
			if (TextUtils.isEmpty(values[i])) {
				values[i] = "";
			}
		}
		return values;
	}
	
	/**
	 * 批量获取存储数据
	 * @param context
	 * @param keys
	 * @param clazz
	 * @return
	 */
	public static HashMap<String, String> getValues(Context context,String[] keys, String fileName) {
		int length = keys.length;
		HashMap<String, String> map = new HashMap<String, String>(length);
		for (int i = 0; i < length; i++) {
			//String etagItemValues = EfunDatabase.getSimpleString(context,fileName, keys[i]);
			String value = getValue(context, fileName, keys[i]);
			if (TextUtils.isEmpty(value)|| value.equals("null")) {
				value = "";
			}
			map.put(keys[i], value);
		}
		return map;
	}
	/**
	 * 简单获取存储
	 * @param context
	 * @param fileName
	 * @param key
	 * @return
	 */
	public static String getValue(Context context,String fileName, String key) {
		return EfunDatabase.getSimpleString(context, fileName, key);
	}
	/**
	 * 简单存储数据
	 * @param context
	 * @param fileName
	 * @param key
	 * @param info
	 */
	public static void saveValue(Context context,String fileName, String key,String info){
		EfunDatabase.saveSimpleInfo(context, fileName, key, info);
	}
	/**
	 * 简单获取存储
	 * @param context
	 * @param fileName
	 * @param key
	 * @return
	 */
	public static long getLongValue(Context context,String fileName, String key) {
		return EfunDatabase.getSimpleLong(context, fileName, key);
	}
	/**
	 * 简单存储数据
	 * @param context
	 * @param fileName
	 * @param key
	 * @param info
	 */
	public static void saveValue(Context context,String fileName, String key,long info){
		EfunDatabase.saveSimpleInfo(context, fileName, key, info);
	}
	/**
	 * 通过文件名称清除数据
	 * @param context
	 * @param fileName
	 * @return
	 */
	public static boolean clearValue(Context context, String fileName) {
		return EfunDatabase.getEditor(context, fileName).clear().commit();
	}
	/**
	 * 通过key清除数据
	 * @param context
	 * @param fileName
	 * @return
	 */
	public static boolean clearValueByKey(Context context, String fileName,String key) {
		return EfunDatabase.getEditor(context, fileName).remove(key).commit();
	}
}
