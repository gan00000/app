package com.efun.platform.status;

import com.efun.platform.status.bean.VersionBean;
/**
 * 平台状态
 * @author Jesse
 *
 */
public class IPlatStatus {
	/**
	 * 版本状态
	 */
	private VersionBean version;
	public IPlatStatus() {
		super();
	}
	public IPlatStatus(VersionBean version) {
		super();
		this.version = version;
	}
	public VersionBean getVersion() {
		return version;
	}
	public void setVersion(VersionBean version) {
		this.version = version;
	}
	
	
}
