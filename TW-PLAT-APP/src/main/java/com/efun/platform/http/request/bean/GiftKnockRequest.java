package com.efun.platform.http.request.bean;

/**
 * 好康 - 礼包 - 抢礼包
 * @author Jesse
 *
 */
public class GiftKnockRequest extends BaseRequestBean{
	private String uid;
	private String gameCode;
	private String giftType;
	private String platform;
	private String version;
	private String fromType;
	private String sign;
	private String timestamp;
	public GiftKnockRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	public GiftKnockRequest(String gameCode, String giftType,
			String platform,String version,String fromType) {
		super();
		this.gameCode = gameCode;
		this.giftType = giftType;
		this.platform = platform;
		this.version = version;
		this.fromType = fromType;
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
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getGameCode() {
		return gameCode;
	}
	public void setGameCode(String gameCode) {
		this.gameCode = gameCode;
	}
	public String getGiftType() {
		return giftType;
	}
	public void setGiftType(String giftType) {
		this.giftType = giftType;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getFromType() {
		return fromType;
	}
	public void setFromType(String fromType) {
		this.fromType = fromType;
	}

	
}
