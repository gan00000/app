package com.efun.platform.module.bean;

import java.io.Serializable;

import android.content.Intent;
import android.graphics.drawable.Drawable;
/**
 * 手机app详情
 * @author itxuxxey
 */
public class AppInfoBean implements Serializable{
	private static final long serialVersionUID = 1L;
	private String appLabel;    //应用程序标签  
    private Drawable appIcon ;  //应用程序图像  
    private Intent intent ;     //启动应用程序的Intent ，一般是Action为Main和Category为Lancher的Activity  
    private String pkgName ;    //应用程序所对应的包名  
    private String versionCode;
    private String versionName;//版本名 1.1
    private String isUpdate;//是否更新，1代表要更新，0代表不更新
    private String downloadUrl;//下载地址
    private String updateDesc;//版本更新的信息
      
    public AppInfoBean(){}  
      
    public String getAppLabel() {  
        return appLabel;  
    }  
    public void setAppLabel(String appName) {  
        this.appLabel = appName;  
    }  
    public Drawable getAppIcon() {  
        return appIcon;  
    }  
    public void setAppIcon(Drawable appIcon) {  
        this.appIcon = appIcon;  
    }  
    public Intent getIntent() {  
        return intent;  
    }  
    public void setIntent(Intent intent) {  
        this.intent = intent;  
    }  
    public String getPkgName(){  
        return pkgName ;  
    }  
    public void setPkgName(String pkgName){  
        this.pkgName=pkgName ;  
    }

	public String getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public String getIsUpdate() {
		return isUpdate;
	}

	public void setIsUpdate(String isUpdate) {
		this.isUpdate = isUpdate;
	}

	public String getDownloadUrl() {
		return downloadUrl;
	}

	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}

	public String getUpdateDesc() {
		return updateDesc;
	}

	public void setUpdateDesc(String updateDesc) {
		this.updateDesc = updateDesc;
	}  
    
}
