package com.efun.platform.http.request.bean;

import com.efun.platform.utils.Const.HttpParam;

/**
 * 客服 - 客服回复- 问题详情
 * @author Jesse
 *
 */
public class CsReplyStatusRequest extends BaseRequestBean {
	private String fromType;
	private String uid;
	private String platform;
	private String sign;
	private String timestamp;
	public CsReplyStatusRequest(String uid) {
		super();
		this.fromType = HttpParam.APP;
		this.uid = uid;
		this.platform = HttpParam.PLATFORM_AREA;
	}
	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getFromType() {
		return fromType;
	}
	public void setFromType(String fromType) {
		this.fromType = fromType;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}

	
}
