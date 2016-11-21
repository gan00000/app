package com.efun.platform.http.request.bean;
/**
 * 单个礼包详情
 * @author Jesse
 *
 */
public class GiftItemRequest extends BaseRequestBean{
	private String uid;
	private String goodsId;
	private String fromType;
	private String sign;
	private String timestamp;
	public GiftItemRequest(String uid, String goodsId, String fromType) {
		super();
		this.uid = uid;
		this.goodsId = goodsId;
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
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public String getFromType() {
		return fromType;
	}
	public void setFromType(String fromType) {
		this.fromType = fromType;
	}
	
	
}
