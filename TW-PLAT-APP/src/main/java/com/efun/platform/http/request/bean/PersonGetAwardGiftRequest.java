package com.efun.platform.http.request.bean;

/**
 * 获取完善资料礼包
 * @author itxuxxey
 *
 */
public class PersonGetAwardGiftRequest extends BaseRequestBean{
	private String uid;
	private String platform;
	private String category;
	private String goodsType;
	private String gameCode;
	private String fromType;
	private String version;
	private String packageVersion;
	private String sign;
	private String timestamp;
	private String language;

	public PersonGetAwardGiftRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PersonGetAwardGiftRequest(String uid, String platform,
			String category, String goodsType, String gameCode,
			String fromType, String version, String packageVersion,
			String sign, String timestamp, String language) {
		super();
		this.uid = uid;
		this.platform = platform;
		this.category = category;
		this.goodsType = goodsType;
		this.gameCode = gameCode;
		this.fromType = fromType;
		this.version = version;
		this.packageVersion = packageVersion;
		this.sign = sign;
		this.timestamp = timestamp;
		this.language = language;
	}

	
	
}
