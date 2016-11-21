package com.efun.platform.service;

import com.efun.core.cipher.AESCipher;
import com.efun.core.tools.EfunLogUtil;
import com.efun.core.tools.EfunStringUtil;
import com.efun.platform.IPlatApplication;
import com.efun.platform.aidl.StartGame;
import com.efun.platform.utils.Const;
import com.efun.platform.utils.Const.LoginPlatform;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
/**
 * 自动登录支持的AIDL
 * @author itxuxxey
 *
 */
public class StartGameService extends Service{
	
	private IBinder mIBinder = new StartGameBinder();
	private Intent mIntent;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		this.mIntent = intent;
		return mIBinder;
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		onUnbind(mIntent);
		super.onDestroy();
	}
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}
	
	
	public String getPfANameValue(){
		if(IPlatApplication.get() != null){
			if(!EfunStringUtil.isEmpty(IPlatApplication.get().getPfLType())){
				if(LoginPlatform.EFUN.equals(IPlatApplication.get().getPfLType()) && !EfunStringUtil.isEmpty(IPlatApplication.get().getPfAName())){
					String plaName = AESCipher.encrypt(IPlatApplication.get().getPfAName(), Const.AES_ENCRYPT_KEY);
					EfunLogUtil.logD("efun", "plaName:"+plaName);
					return plaName;
				}else{
					return "";//非efun登录
				}
			}else{
				return "";//没有登录
			} 
		}else{
			return "";//应用没开启的情况
		}
	}
	//此处返回md5后的密码给sdk可能造成错误，sdk也会md5
	public String getPfPCodeValue(){
		if(IPlatApplication.get() != null){
			if(!EfunStringUtil.isEmpty(IPlatApplication.get().getPfLType())){
				if(LoginPlatform.EFUN.equals(IPlatApplication.get().getPfLType()) && !EfunStringUtil.isEmpty(IPlatApplication.get().getPfPCode())){
					String pfPCode = AESCipher.encrypt(IPlatApplication.get().getPfPCode(), Const.AES_ENCRYPT_KEY);
					EfunLogUtil.logD("efun", "pfPCode:"+pfPCode);
					return pfPCode;
				}else{
					return "";//非efun登录
				}
			}else{
				return "";//没有登录
			}
		}else{
			return "";//应用没开启的情况
		}
	}
	public String getPfLTypeValue(){
		if(IPlatApplication.get() != null){
			if(!EfunStringUtil.isEmpty(IPlatApplication.get().getPfLType())){
				return IPlatApplication.get().getPfLType();
			}else{
				return "";//没有登录
			}
		}else {
			return "";//应用没开启的情况
		}
	}
	
	private class StartGameBinder extends StartGame.Stub{

		@Override
		public String getPfAName() throws RemoteException {
			// TODO Auto-generated method stub
			return getPfANameValue();
		}

		@Override
		public String getPfPCode() throws RemoteException {
			// TODO Auto-generated method stub
			return getPfPCodeValue();
		}

		@Override
		public String getPfLType() throws RemoteException {
			// TODO Auto-generated method stub
			return getPfLTypeValue();
		}
		
	}

}
