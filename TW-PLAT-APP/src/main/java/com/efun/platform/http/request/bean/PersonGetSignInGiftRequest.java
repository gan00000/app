package com.efun.platform.http.request.bean;

import com.efun.platform.utils.Const.HttpParam;

/**
 *	获取签到礼包
 *
 */
public class PersonGetSignInGiftRequest extends BaseRequestBean {
	private String uid;
	private String language;
	private String gameCode;
	private String category;
	private String goodsType;
	private String platform;
	private String sign;
	private String timestamp;
	
	
	public PersonGetSignInGiftRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public PersonGetSignInGiftRequest(String uid, String language,
			String gameCode, String category, String goodsType,
			String platform, String sign, String timestamp) {
		super();
		this.uid = uid;
		this.language = language;
		this.gameCode = gameCode;
		this.category = category;
		this.goodsType = goodsType;
		this.platform = platform;
		this.sign = sign;
		this.timestamp = timestamp;
	}
	
	
}
