package com.efun.platform.service;

import android.app.Dialog;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.view.WindowManager;

import com.efun.core.tools.EfunLogUtil;
import com.efun.platform.FrameTabListener;
import com.efun.platform.IPlatApplication;
import com.efun.platform.AndroidScape.E_string;
import com.efun.platform.http.IPlatController;
import com.efun.platform.http.dao.impl.IPlatformRequest;
import com.efun.platform.http.request.IPlatRequest;
import com.efun.platform.http.request.bean.AccountLoginRequest;
import com.efun.platform.http.request.bean.BaseRequestBean;
import com.efun.platform.http.request.bean.SettingCheckRequest;
import com.efun.platform.http.response.bean.AccountResponse;
import com.efun.platform.http.response.bean.BaseResponseBean;
import com.efun.platform.http.response.bean.SettingCheckResponse;
import com.efun.platform.module.PopWindow;
import com.efun.platform.module.account.bean.ResultBean;
import com.efun.platform.module.account.bean.User;
import com.efun.platform.module.base.impl.OnEfunItemClickListener;
import com.efun.platform.module.base.impl.OnRequestListener;
import com.efun.platform.module.utils.AppUtils;
import com.efun.platform.module.utils.PopUtils;
import com.efun.platform.module.utils.UserUtils;
import com.efun.platform.module.utils.ViewUtils;
import com.efun.platform.status.IPlatStatus;
import com.efun.platform.utils.Const;
import com.efun.platform.utils.Const.HttpParam;
import com.efun.platform.utils.Const.Tab;
import com.efun.platform.utils.Const.ToastType;
import com.efun.platform.utils.StoreUtil;
import com.efun.pushnotification.task.EfunPushManager;

/**
 * 版本更新Server
 * @author Jesse
 *
 */
public class IPlatService extends Service{
	private final IBinder mBinder = new IPlatBinder();
	private IPlatController mController;
	private OnEfunItemClickListener mClickListener;
	@Override
	public void onCreate() {
		super.onCreate();
		request(); 
	}

	 /** 
     * 该类是获得Service对象 
     * @author Administrator 
     * 
     */  
    public class IPlatBinder extends Binder{  
        public IPlatService getService(){  
            return IPlatService.this;  
        }  
    } 
	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}
	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.i("efun", "IPlatService onDestroy");
	}
	private void request(){
		mController = new IPlatController(getApplicationContext());
		mController.executeAll(null, createIPlatRequests(needRequestBean()), new OnRequestListener() {
			@Override
			public void onSuccess(int requestType, BaseResponseBean baseResponse) {
				EfunLogUtil.logE("-------onSuccess---------"+requestType);
				if(requestType==IPlatformRequest.REQ_SETTING_CHECK_VERSION){
					SettingCheckResponse response = (SettingCheckResponse) baseResponse;
					IPlatStatus status = IPlatApplication.get().getIPlatStatus();
					if(status==null){
						status = new IPlatStatus();
					}
					if(response.getVersion().getAudited().equals("0")){
						EfunLogUtil.enableInfo(true);
						EfunLogUtil.enableDebug(true);
					}else{
						EfunLogUtil.enableInfo(false);
						EfunLogUtil.enableDebug(false);
//						EfunLogUtil.enableInfo(true);
//						EfunLogUtil.enableDebug(true);
					}
					if(response.getVersion().getIsUpdate().equals("1")){
						status.setVersion(response.getVersion());
						final String url = response.getVersion().getDownloadUrl();
						boolean isForce = false;
						if(!response.getVersion().getIsforce().equals("0")){
							isForce = true;
							Intent it = new Intent();
							it.setAction(Const.RECEIVER_UPDATEAPP_ACTION);
							it.putExtra("downloadUrl", url);
							getApplicationContext().sendBroadcast(it);
						}else{
							ViewUtils.createToastUpdate(IPlatService.this, 
									new OnEfunItemClickListener() {
								@Override
								public void onItemClick(int position) {
									AppUtils.download(getApplicationContext(), url);
								}
							}, isForce);
						}
					}
					IPlatApplication.get().setIPlatStatus(status);
				}else if(requestType==IPlatformRequest.REQ_ACCOUNT_LOGIN){
					//获取前一次登录时间，和当前登录时间
					long oldTime = StoreUtil.getLongValue(getApplicationContext(), StoreUtil.Param_file_name, StoreUtil.Pamam_key_user_login_oldTime);
					long currentTime = StoreUtil.getLongValue(getApplicationContext(), StoreUtil.Param_file_name, StoreUtil.Pamam_key_user_login_currentTime);
					if(currentTime == 0){
						currentTime = System.currentTimeMillis();
					}
					if(oldTime == 0){
						oldTime = currentTime;
					}
					if(oldTime == currentTime){
						currentTime = System.currentTimeMillis();
					}
					//时间前隔
					long middleTime = currentTime - oldTime;
					EfunLogUtil.logE("middleTime:"+middleTime);
					AccountResponse response = (AccountResponse) baseResponse;
					ResultBean result = response.getResultBean();
					long guiTime = 30*24*60*60*1000L;
					EfunLogUtil.logE("guiTime:"+guiTime);
					if(middleTime <= guiTime){
						EfunLogUtil.logE("-------小于middleTime---------");
						if(result.getCode().equals(HttpParam.RESULT_1000)){
							EfunLogUtil.logE("-------小于middleTime-----登录成功----");
							IPlatApplication.get().setUser((User)result);
							for (int i = 0; i < Integer.MAX_VALUE; i++) {
								if(IPlatApplication.get().getUser()!=null){
//									EfunPushManager.gameLoginSuccessReport(getApplication(), ((User)result).getUid());
									break;
								}
							}
						}else{//1004
							UserUtils.logout(getApplicationContext());
							if(result.getCode().equals(HttpParam.RESULT_1004)){//密码有误
								EfunLogUtil.logE("-------小于middleTime------密码错误---");
								ViewUtils.createToastCommon(IPlatService.this, getString(E_string.efun_pd_login_error_pwd), mClickListener);
							}else{
								EfunLogUtil.logE("-------小于middleTime------其它情况---");
								ViewUtils.createToastCommon(IPlatService.this, getString(E_string.efun_pd_login_error_timeout), mClickListener);
							}
						}
					}else{
						EfunLogUtil.logE("-------大于middleTime---------");
						//1个月之内没登录
//						UserUtils.logout(getApplicationContext());
						UserUtils.logout(getApplicationContext());
						ViewUtils.createToastCommon(IPlatService.this, getString(E_string.efun_pd_login_error_timeout), mClickListener);
					}
					StoreUtil.saveValue(getApplicationContext(), StoreUtil.Param_file_name, StoreUtil.Pamam_key_user_login_oldTime, currentTime);
				}
			}
			
			@Override
			public void onFailure(int requestType) {
				EfunLogUtil.logE("-------onFailure---------");
			}

			@Override
			public void onTimeout(int requestType) {
				EfunLogUtil.logE("-------onTimeout---------");
			}

			@Override
			public void onNoData(int requestType, String noDataNotify) {
				// TODO Auto-generated method stub
				EfunLogUtil.logE("-------onNoData---------");
			}
		});
	}
	private IPlatRequest[] createIPlatRequests(BaseRequestBean[] requests){
		IPlatRequest[] mRequests = new IPlatRequest[requests.length];
		for (int i = 0; i < mRequests.length; i++) {
			mRequests[i] = new IPlatRequest(getApplicationContext()).setRequestBean(requests[i]);
		}
		return mRequests;
	}
	private BaseRequestBean[] needRequestBean(){
		SettingCheckRequest checkRequest = new SettingCheckRequest(getApplicationContext(),
				HttpParam.PLATFORM_CODE);
		checkRequest.setReqType(IPlatformRequest.REQ_SETTING_CHECK_VERSION);
		if(UserUtils.isLogin()){
			AccountLoginRequest loginRequest = UserUtils.createRequest(getApplicationContext());
			return new BaseRequestBean[]{loginRequest,checkRequest};
		}
		return new BaseRequestBean[]{checkRequest};
	}
	
	public void setmClickListener(OnEfunItemClickListener mClickListener) {
		this.mClickListener = mClickListener;
	}
	
}
