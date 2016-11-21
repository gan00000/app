package com.efun.platform.module.welfare.bean;

import com.efun.platform.module.BaseDataBean;
/**
 * 单个活动任务
 * @author Jesse
 * @author itxuxxey 20150617修整
 */
public class ActExtensionTaskBean extends BaseDataBean {
	
//	private String condiTions;//此字段在文档中找不到，多余的 20150617
	private String currentState;//任务状态: 0未完成	1完成中	2已完成
	private String descripTion;//任务描述
	private String flag;//是否有效(无用)
	private String rewardCode;//单步任务奖励代码
	private String rewardName;//单步任务奖励名称
	private String rewardNum;//单步任务奖励数
//	private String roderId;//此字段在文档中找不到，多余的 20150617
//	private String statisticalUrl;//此字段在文档中找不到，多余的 20150617
	private String taskCode;//任务代码
	private String taskUrl;//任务URL
	private String taskTitle;//任务标题
	private String specifiedServerCode;//20150617添加		服务器白名单
	private String orderId;//任务排序标识

	public ActExtensionTaskBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public String getTaskUrl() {
		return taskUrl;
	}

	public void setTaskUrl(String taskUrl) {
		this.taskUrl = taskUrl;
	}

	public String getTaskTitle() {
		return taskTitle;
	}

	public void setTaskTitle(String taskTitle) {
		this.taskTitle = taskTitle;
	}
//
//	public String getCondiTions() {
//		return condiTions;
//	}
//
//	public void setCondiTions(String condiTions) {
//		this.condiTions = condiTions;
//	}
	public String getCurrentState() {
		return currentState;
	}

	public void setCurrentState(String currentState) {
		this.currentState = currentState;
	}

	public String getDescripTion() {
		return descripTion;
	}

	public void setDescripTion(String descripTion) {
		this.descripTion = descripTion;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getRewardCode() {
		return rewardCode;
	}

	public void setRewardCode(String rewardCode) {
		this.rewardCode = rewardCode;
	}

	public String getRewardName() {
		return rewardName;
	}

	public void setRewardName(String rewardName) {
		this.rewardName = rewardName;
	}

	public String getRewardNum() {
		return rewardNum;
	}

	public void setRewardNum(String rewardNum) {
		this.rewardNum = rewardNum;
	}
//
//	public String getRoderId() {
//		return roderId;
//	}
//
//	public void setRoderId(String roderId) {
//		this.roderId = roderId;
//	}
//
//	public String getStatisticalUrl() {
//		return statisticalUrl;
//	}
//
//	public void setStatisticalUrl(String statisticalUrl) {
//		this.statisticalUrl = statisticalUrl;
//	}
	public String getTaskCode() {
		return taskCode;
	}

	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}

	public String getSpecifiedServerCode() {
		return specifiedServerCode;
	}

	public void setSpecifiedServerCode(String specifiedServerCode) {
		this.specifiedServerCode = specifiedServerCode;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}


}
