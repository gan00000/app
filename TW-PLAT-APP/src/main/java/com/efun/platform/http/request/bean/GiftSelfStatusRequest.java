package com.efun.platform.http.request.bean;

import com.efun.platform.utils.Const.HttpParam;

/**
 * 礼包序号状态检查
 * @author Jesse
 *
 */
public class GiftSelfStatusRequest extends BaseRequestBean {
	private String fromType;
	private String uid;
	private String type;
	private String handleType;
	private String area;
	private String sign;
	private String timestamp;
	public GiftSelfStatusRequest(String uid,String handleType) {
		super();
		this.fromType = HttpParam.APP;
		this.uid = uid;
		this.area = HttpParam.PLATFORM_AREA;
		this.handleType = handleType;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getHandleType() {
		return handleType;
	}
	public void setHandleType(String handleType) {
		this.handleType = handleType;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}

	
}
