package com.efun.platform.utils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.Map;

import android.content.Context;
import android.util.Log;

import com.efun.core.tools.EfunLocalUtil;
import com.efun.core.tools.EfunLogUtil;
import com.efun.core.tools.EfunStringUtil;
import com.efun.platform.IPlatApplication;
import com.efun.platform.http.IPlatController;
import com.efun.platform.http.dao.impl.IPlatformRequest;
import com.efun.platform.http.request.IPlatRequest;
import com.efun.platform.http.request.bean.BaseRequestBean;
import com.efun.platform.http.request.bean.StatLogInfoRequest;
import com.efun.platform.http.request.bean.StatPushInfoRequest;
import com.efun.platform.http.response.bean.BaseResponseBean;
import com.efun.platform.http.response.bean.StatLogInfoResponse;
import com.efun.platform.module.base.impl.OnRequestListener;
import com.efun.platform.module.bean.ButtonControlBean;
import com.efun.platform.module.utils.AppUtils;
import com.efun.platform.utils.Const.HttpParam;

/**
 * 自动登入相关统计
 * 
 * @author itxuxxey
 * 
 */
public class StatLogInfoUtils {

	public static void setStaticLogInfo(Context context, String gameCode,
			String module, String operationType, String packageArea) {
		StatLogInfoRequest request = new StatLogInfoRequest();
		request.setGameCode(gameCode);
		request.setPackageArea(packageArea);
		request.setFromType(HttpParam.APP);
		request.setModule(module);
		request.setOperationType(operationType);
		if (IPlatApplication.get().getUser() != null) {
			request.setUid(IPlatApplication.get().getUser().getUid());
		}
		request.setMac(EfunLocalUtil.getLocalMacAddress(context));
		request.setImei(EfunLocalUtil.getLocalImeiAddress(context));
		request.setIdfa("");
		request.setCfuuid("");
		request.setDeviceType(EfunLocalUtil.getDeviceType());
		request.setSystemVersion(EfunLocalUtil.getOsVersion());
		request.setVersion(HttpParam.PLATFORM);
		// request.setLanguage(HttpParam.LANGUAGE);
		request.setPackageVersion(AppUtils.getAppVersionName(context));
		request.setPlatform(HttpParam.PLATFORM_AREA);
		request.setReqType(IPlatformRequest.REQ_STAT_LOGINFO);
		// request.setSign(EfunStringUtil.toMd5(HttpParam.PLATFORM_APP_KEY+gameCode+operationType+timestamp,
		// false));
		// request.setTimestamp(timestamp+"");

		IPlatController mController = new IPlatController(context);
		IPlatRequest mIRequest = new IPlatRequest(context);
		mIRequest.setRequestBean(request);
		mController.executeAll(null, new IPlatRequest[] { mIRequest },
				new OnRequestListener() {

					@Override
					public void onTimeout(int requestType) {
						// TODO Auto-generated method stub
						Log.e("efun", "statloginfo-->onTimeout");
					}

					@Override
					public void onSuccess(int requestType,
							BaseResponseBean baseResponse) {
						// TODO Auto-generated method stub
						// if(requestType == IPlatformRequest.REQ_STAT_LOGINFO){
						// StatLogInfoResponse response =
						// (StatLogInfoResponse)baseResponse;
						// ButtonControlBean bean = response.getResponse();
						// Log.i("efun",
						// "code:"+bean.getCode()+"/message:"+bean.getMessage());
						// }
						Log.e("efun", "statloginfo-->onSuccess");
					}

					@Override
					public void onNoData(int requestType, String noDataNotify) {
						// TODO Auto-generated method stub
						Log.e("efun", "statloginfo-->onNoData");
					}

					@Override
					public void onFailure(int requestType) {
						// TODO Auto-generated method stub
						Log.e("efun", "statloginfo-->onFailure");
					}
				});
	}

	public static void setStaticPushInfo(Context context, long pushMessageId) {
		long timestamp = System.currentTimeMillis();
		StatPushInfoRequest request1 = new StatPushInfoRequest();
		request1.setPushMessageId(pushMessageId + "");
		request1.setLookTime(timestamp + "");
		if (IPlatApplication.get().getUser() != null) {
			request1.setUserId(IPlatApplication.get().getUser().getUid());
		}
		request1.setVersion(HttpParam.PLATFORM);
		request1.setPackageVersion(AppUtils.getAppVersionName(context));
		request1.setIdfa("");
		request1.setCfuuid("");
		request1.setMac(EfunLocalUtil.getLocalMacAddress(context));
		request1.setAndroidid(EfunLocalUtil.getLocalAndroidId(context));
		request1.setImei(EfunLocalUtil.getLocalImeiAddress(context));
		request1.setReqType(IPlatformRequest.REQ_STAT_PUSHINFO);

		IPlatController mController1 = new IPlatController(context);
		IPlatRequest mIRequest1 = new IPlatRequest(context);
		mIRequest1.setRequestBean(request1);
		mController1.executeAll(null, new IPlatRequest[] { mIRequest1 },
				new OnRequestListener() {

					@Override
					public void onTimeout(int requestType) {
						// TODO Auto-generated method stub
						Log.e("efun", "statpushinfo-->onTimeout");
					}

					@Override
					public void onSuccess(int requestType,
							BaseResponseBean baseResponse) {
						// TODO Auto-generated method stub
						// if(requestType == IPlatformRequest.REQ_STAT_LOGINFO){
						// StatLogInfoResponse response =
						// (StatLogInfoResponse)baseResponse;
						// ButtonControlBean bean = response.getResponse();
						// Log.i("efun",
						// "code:"+bean.getCode()+"/message:"+bean.getMessage());
						// }
						Log.e("efun", "statpushinfo-->onSuccess");
					}

					@Override
					public void onNoData(int requestType, String noDataNotify) {
						// TODO Auto-generated method stub
						Log.e("efun", "statpushinfo-->onNoData");
					}

					@Override
					public void onFailure(int requestType) {
						// TODO Auto-generated method stub
						Log.e("efun", "statpushinfo-->onFailure");
					}
				});
	}

	public static String map2strData(Map<String, String> dataMap,
			String className) throws UnsupportedEncodingException {
		String[] fields = null;
		if (className.equals("StatLogInfoRequest")) {
			fields = new String[] { "gameCode", "packageArea", "fromType",
					"module", "operationType", "uid", "mac", "imei", "idfa",
					"cfuuid", "deviceType", "systemVersion", "version",
					"packageVersion", "platform" };
		} else if (className.equals("StatPushInfoRequest")) {
			fields = new String[] { "pushMessageId", "lookTime", "userId",
					"version", "packageVersion", "idfa", "cfuuid", "mac",
					"androidid", "imei" };
		}
		String data = "";
		if ((dataMap != null) && (!dataMap.isEmpty())) {
			if ((fields != null) && (fields.length > 0)) {
				for (int i = 0; i < fields.length; i++) {
					if (i == fields.length - 1) {
						data = data	+ dataMap.get(fields[i]);
					} else {
						data = data	+ dataMap.get(fields[i]) + "|";
					}
				}
			}
		}
		EfunLogUtil.logI("efun", "data:" + data);
		return data;
	}
}
