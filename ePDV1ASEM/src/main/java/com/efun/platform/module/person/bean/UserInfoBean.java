package com.efun.platform.module.person.bean;

import com.efun.platform.module.BaseDataBean;
/**
 * 登陆成功后的玩家信息
 * @author itxuxxey
 *
 */
public class UserInfoBean extends BaseDataBean{
	private static final long serialVersionUID = 1L;
	private String createTime;
	private String modifiedTime;
	private String uid;
	private String username;
	private String accountName;
	private String rango;
	private String rangoLevel;
	private String memberLevel;
	private String experienceValue;
	private String goldValue;
	private String address;
	private String sex;
	private String icon;
	private String areaDesc;
	private String expPercentage;
	private String levelupExp;
	private String currentExp;
	private String isVip;
	private String isAccept;
	public UserInfoBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UserInfoBean(String createTime, String modifiedTime, String uid,
			String username, String accountName, String rango,
			String rangoLevel, String memberLevel, String experienceValue,
			String goldValue, String address, String sex, String icon,
			String areaDesc, String expPercentage, String levelupExp,
			String currentExp, String isVip, String isAccept) {
		super();
		this.createTime = createTime;
		this.modifiedTime = modifiedTime;
		this.uid = uid;
		this.username = username;
		this.accountName = accountName;
		this.rango = rango;
		this.rangoLevel = rangoLevel;
		this.memberLevel = memberLevel;
		this.experienceValue = experienceValue;
		this.goldValue = goldValue;
		this.address = address;
		this.sex = sex;
		this.icon = icon;
		this.areaDesc = areaDesc;
		this.expPercentage = expPercentage;
		this.levelupExp = levelupExp;
		this.currentExp = currentExp;
		this.isVip = isVip;
		this.isAccept = isAccept;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getModifiedTime() {
		return modifiedTime;
	}
	public void setModifiedTime(String modifiedTime) {
		this.modifiedTime = modifiedTime;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getRango() {
		return rango;
	}
	public void setRango(String rango) {
		this.rango = rango;
	}
	public String getRangoLevel() {
		return rangoLevel;
	}
	public void setRangoLevel(String rangoLevel) {
		this.rangoLevel = rangoLevel;
	}
	public String getMemberLevel() {
		return memberLevel;
	}
	public void setMemberLevel(String memberLevel) {
		this.memberLevel = memberLevel;
	}
	public String getExperienceValue() {
		return experienceValue;
	}
	public void setExperienceValue(String experienceValue) {
		this.experienceValue = experienceValue;
	}
	public String getGoldValue() {
		return goldValue;
	}
	public void setGoldValue(String goldValue) {
		this.goldValue = goldValue;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getAreaDesc() {
		return areaDesc;
	}
	public void setAreaDesc(String areaDesc) {
		this.areaDesc = areaDesc;
	}
	public String getExpPercentage() {
		return expPercentage;
	}
	public void setExpPercentage(String expPercentage) {
		this.expPercentage = expPercentage;
	}
	public String getLevelupExp() {
		return levelupExp;
	}
	public void setLevelupExp(String levelupExp) {
		this.levelupExp = levelupExp;
	}
	public String getCurrentExp() {
		return currentExp;
	}
	public void setCurrentExp(String currentExp) {
		this.currentExp = currentExp;
	}
	public String getIsVip() {
		return isVip;
	}
	public void setIsVip(String isVip) {
		this.isVip = isVip;
	}
	public String getIsAccept() {
		return isAccept;
	}
	public void setIsAccept(String isAccept) {
		this.isAccept = isAccept;
	}
}
