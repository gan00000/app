package com.efun.platform.module.welfare.bean;

import com.efun.platform.module.BaseDataBean;
/**
 * 礼包信息
 * @author Jesse
 *
 */
public class GiftItemBean extends BaseDataBean{
	private String code;
	private String id;
	private String gameCode;//游戏編碼
	private String gameName;//游戲名稱
	private String goodsName;//礼包名称
	private String awardDesc;//礼包描述
	private String awardRule;//获取礼包的规则
	private String activeEndTime;//活动结束时间
	private int total;//礼包数量
	private int hasUse;//已经使用了的礼包数量
	private String usePercent;//已经使用了的礼包百分比
	private String icon;//图标
	private int userHasGot;//用户是否获取过该礼包0:没有 1:有
	private String goodsType;//fresher:新手礼包  unique:专属礼包
	private String goodsTypeName;//礼包类型名称20150907添加
	private String tag;//礼包别名
	public GiftItemBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public GiftItemBean(String id, String gameCode, String gameName,
			String goodsName, String awardDesc, String awardRule,
			String activeEndTime, int total, int hasUse, String usePercent,
			String icon, int userHasGot, String goodsType,String goodsTypeName) {
		super();
		this.id = id;
		this.gameCode = gameCode;
		this.gameName = gameName;
		this.goodsName = goodsName;
		this.awardDesc = awardDesc;
		this.awardRule = awardRule;
		this.activeEndTime = activeEndTime;
		this.total = total;
		this.hasUse = hasUse;
		this.usePercent = usePercent;
		this.icon = icon;
		this.userHasGot = userHasGot;
		this.goodsType = goodsType;
		this.goodsTypeName = goodsTypeName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getGameCode() {
		return gameCode;
	}
	public void setGameCode(String gameCode) {
		this.gameCode = gameCode;
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
	public String getAwardDesc() {
		return awardDesc;
	}
	public void setAwardDesc(String awardDesc) {
		this.awardDesc = awardDesc;
	}
	public String getAwardRule() {
		return awardRule;
	}
	public void setAwardRule(String awardRule) {
		this.awardRule = awardRule;
	}
	public String getActiveEndTime() {
		return activeEndTime;
	}
	public void setActiveEndTime(String activeEndTime) {
		this.activeEndTime = activeEndTime;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getHasUse() {
		return hasUse;
	}
	public void setHasUse(int hasUse) {
		this.hasUse = hasUse;
	}
	public String getUsePercent() {
		return usePercent;
	}
	public void setUsePercent(String usePercent) {
		this.usePercent = usePercent;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public int getUserHasGot() {
		return userHasGot;
	}
	public void setUserHasGot(int userHasGot) {
		this.userHasGot = userHasGot;
	}
	public String getGoodsType() {
		return goodsType;
	}
	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getGoodsTypeName() {
		return goodsTypeName;
	}
	public void setGoodsTypeName(String goodsTypeName) {
		this.goodsTypeName = goodsTypeName;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	
}
