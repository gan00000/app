package com.efun.platform.status.bean;

import com.efun.platform.module.BaseDataBean;
import com.efun.platform.utils.Const.BeanType;

public class VersionBean extends BaseDataBean{
	private String isUpdate;
	private String downloadUrl;
	private String updateDesc;
	private String version;
	private String isforce;
	private String audited;
	private String versionName;
	
	public VersionBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public VersionBean(String isUpdate, String downloadUrl, String updateDesc,
			String version, String isforce,String versionName) {
		super();
		this.isUpdate = isUpdate;
		this.downloadUrl = downloadUrl;
		this.updateDesc = updateDesc;
		this.version = version;
		this.isforce = isforce;
		this.versionName = versionName;
	}

	public String getIsforce() {
		return isforce;
	}

	public void setIsforce(String isforce) {
		this.isforce = isforce;
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
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}

	public String getAudited() {
		return audited;
	}

	public void setAudited(String audited) {
		this.audited = audited;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

}
