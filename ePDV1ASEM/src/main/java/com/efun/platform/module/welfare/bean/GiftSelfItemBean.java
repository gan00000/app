package com.efun.platform.module.welfare.bean;

import com.efun.platform.module.BaseDataBean;
/**
 * 序列号
 * @author itxuxxey
 *
 */
public class GiftSelfItemBean extends BaseDataBean{
	private String Id;
	private String goodsId;
	private String usePercent;
	private String uid;
	private String serial;
	private String secretCode;
	private String hasUse;
	private String gameCode;
	private String goodsType;
	private long modifiedTime;
	private String gameName;
	private long rewardTime;
	private String goodsName;
	private String activityName;
	private String category;
	
	public GiftSelfItemBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GiftSelfItemBean(String id, String goodsId, String usePercent,
			String uid, String serial, String secretCode, String hasUse,
			String gameCode, String goodsType, long modifiedTime,
			String gameName, long rewardTime, String goodsName,
			String activityName, String category) {
		super();
		Id = id;
		this.goodsId = goodsId;
		this.usePercent = usePercent;
		this.uid = uid;
		this.serial = serial;
		this.secretCode = secretCode;
		this.hasUse = hasUse;
		this.gameCode = gameCode;
		this.goodsType = goodsType;
		this.modifiedTime = modifiedTime;
		this.gameName = gameName;
		this.rewardTime = rewardTime;
		this.goodsName = goodsName;
		this.activityName = activityName;
		this.category = category;
	}

	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public String getUsePercent() {
		return usePercent;
	}
	public void setUsePercent(String usePercent) {
		this.usePercent = usePercent;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getSerial() {
		return serial;
	}
	public void setSerial(String serial) {
		this.serial = serial;
	}
	public String getSecretCode() {
		return secretCode;
	}
	public void setSecretCode(String secretCode) {
		this.secretCode = secretCode;
	}

	public String getHasUse() {
		return hasUse;
	}
	public void setHasUse(String hasUse) {
		this.hasUse = hasUse;
	}
	public String getGameCode() {
		return gameCode;
	}
	public void setGameCode(String gameCode) {
		this.gameCode = gameCode;
	}
	public String getGoodsType() {
		return goodsType;
	}
	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}
	public String getGameName() {
		return gameName;
	}
	public void setGameName(String gameName) {
		this.gameName = gameName;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public long getModifiedTime() {
		return modifiedTime;
	}
	public void setModifiedTime(long modifiedTime) {
		this.modifiedTime = modifiedTime;
	}
	public long getRewardTime() {
		return rewardTime;
	}
	public void setRewardTime(long rewardTime) {
		this.rewardTime = rewardTime;
	}
	
	
}
