package com.efun.platform.http.request.bean;
/**
 * 客服 - 客服回复- 请求响应类
 * @author Jesse
 *
 */
public class CsReplyFinishQuestionRequest extends BaseRequestBean {
	private String tgppid;
	private int eva;
	private String fromType;
	private String device;
	private String uid;
	private String sign;
	private String timestamp;
	public CsReplyFinishQuestionRequest(String tgppid, int eva, String fromType, String device) {
		super();
		this.tgppid = tgppid;
		this.eva = eva;
		this.fromType = fromType;
		this.device = device;
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
	public String getTgppid() {
		return tgppid;
	}

	public void setTgppid(String tgppid) {
		this.tgppid = tgppid;
	}

	public int getEva() {
		return eva;
	}

	public void setEva(int eva) {
		this.eva = eva;
	}

	public String getFromType() {
		return fromType;
	}

	public void setFromType(String fromType) {
		this.fromType = fromType;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

}
