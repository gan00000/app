package com.efun.platform.module.utils;

import android.content.Context;
import android.util.Log;

import com.efun.platform.IPlatApplication;
import com.efun.platform.http.IPlatController;
import com.efun.platform.http.dao.impl.IPlatformRequest;
import com.efun.platform.http.request.IPlatRequest;
import com.efun.platform.http.request.bean.BaseRequestBean;
import com.efun.platform.http.request.bean.ConfigInfoRequest;
import com.efun.platform.http.response.bean.BaseResponseBean;
import com.efun.platform.http.response.bean.PlatformConfigInfosResponse;
import com.efun.platform.http.response.bean.PlayerAreaResponse;
import com.efun.platform.module.base.impl.OnRequestListener;

/**
 * 获取平台相关配置信息
 * @author itxuxxey
 *
 */
public class ConfigInfoUtils {
	private static IPlatController mController;
	
	/**
	 * 获取平台相关配置文件信息
	 */
	public static void initPlatformConfigs(Context context){
		mController = new IPlatController(context);
		mController.executeAll(null, createIPlatRequests(context,needRequestBean()), new OnRequestListener() {
			
			@Override
			public void onTimeout(int requestType) {
				// TODO Auto-generated method stub
				Log.i("efun", "initPlatformConfigs:onTimeout");
			}
			
			@Override
			public void onSuccess(int requestType, BaseResponseBean baseResponse) {
				// TODO Auto-generated method stub
				Log.i("efun", "initPlatformConfigs:onSuccess");
				if(requestType == IPlatformRequest.REQ_TXT_PLATFORM_CONFIG_INFOS){//配置信息
					PlatformConfigInfosResponse response = (PlatformConfigInfosResponse)baseResponse;
					if(response != null){
						IPlatApplication.get().setConfigInfos(response.getResponse());
					}
				}
//				if(requestType == IPlatformRequest.REQ_TXT_PLAYER_AREA){//地区
//					PlayerAreaResponse response = (PlayerAreaResponse)baseResponse;
//					if(response != null){
//						IPlatApplication.get().setmPlayerAreas(response.getPlayerAreaBeans());
//					}
//				}
			}
			
			@Override
			public void onNoData(int requestType, String noDataNotify) {
				// TODO Auto-generated method stub
				Log.i("efun", "initPlatformConfigs:onNoData");
			}
			
			@Override
			public void onFailure(int requestType) {
				// TODO Auto-generated method stub
				Log.i("efun", "initPlatformConfigs:onFailure");
			}
		});
		
	}
	
	private static IPlatRequest[] createIPlatRequests(Context context,BaseRequestBean[] requests){
		IPlatRequest[] mRequests = new IPlatRequest[requests.length];
		for (int i = 0; i < mRequests.length; i++) {
			mRequests[i] = new IPlatRequest(context).setRequestBean(requests[i]);
		}
		return mRequests;
	}
	
	private static BaseRequestBean[] needRequestBean(){
//		ConfigInfoRequest phoneAreaCodeReq = new ConfigInfoRequest();
//		phoneAreaCodeReq.setReqType(IPlatformRequest.REQ_TXT_PHONE_AREA_CODE);
//		ConfigInfoRequest playAreaReq = new ConfigInfoRequest();
//		playAreaReq.setReqType(IPlatformRequest.REQ_TXT_PLAYER_AREA);
		ConfigInfoRequest platformSwitchInfoReq = new ConfigInfoRequest();
		platformSwitchInfoReq.setReqType(IPlatformRequest.REQ_TXT_PLATFORM_CONFIG_INFOS);
		return new BaseRequestBean[]{platformSwitchInfoReq};
	}
	
	
	

}
