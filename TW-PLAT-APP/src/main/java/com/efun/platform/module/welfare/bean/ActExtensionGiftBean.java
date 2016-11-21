package com.efun.platform.module.welfare.bean;

import com.efun.platform.module.BaseDataBean;
/**
 * 推荐游戏 单个礼包
 * @author Jesse
 * @author itxuxxey 20150617修整
 *
 */
public class ActExtensionGiftBean extends BaseDataBean {
	private String id;//流水ID
	private String gameCode;//游戏代码
	private String goodsName;//礼包名称
	private String awardDesc;//礼包描述
	private String awardRule;//礼包规则
	private String activeStartTime;//礼包有效开始时间
	private String activeEndTime;//礼包有效结束时间
	private String orderno;//排序字段
	private String allCount;//礼包总数量 20150617添加
	private String lastCount;//礼包剩余数量

	public ActExtensionGiftBean() {
		super();
		// TODO Auto-generated constructor stub
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

	public String getActiveStartTime() {
		return activeStartTime;
	}

	public void setActiveStartTime(String activeStartTime) {
		this.activeStartTime = activeStartTime;
	}

	public String getActiveEndTime() {
		return activeEndTime;
	}

	public void setActiveEndTime(String activeEndTime) {
		this.activeEndTime = activeEndTime;
	}

	public String getOrderno() {
		return orderno;
	}

	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}

	public String getAllCount() {
		return allCount;
	}

	public void setAllCount(String allCount) {
		this.allCount = allCount;
	}

	public String getLastCount() {
		return lastCount;
	}

	public void setLastCount(String lastCount) {
		this.lastCount = lastCount;
	}


}
