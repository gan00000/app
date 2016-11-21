package com.efun.platform.module.person.bean;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * 领取礼包
 * @author itxuxxey
 *
 */
public class GetSignInGiftRewardBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private String code;
	private String message;
	private String isSerial;
	private String serial;
	private int addGold;
	private int addExp;
	private int addPoint;
	private String goldValue;
	private String experienceValue;
	public GetSignInGiftRewardBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getIsSerial() {
		return isSerial;
	}
	public void setIsSerial(String isSerial) {
		this.isSerial = isSerial;
	}
	public String getSerial() {
		return serial;
	}
	public void setSerial(String serial) {
		this.serial = serial;
	}
	public int getAddGold() {
		return addGold;
	}
	public void setAddGold(int addGold) {
		this.addGold = addGold;
	}
	public int getAddExp() {
		return addExp;
	}
	public void setAddExp(int addExp) {
		this.addExp = addExp;
	}
	public int getAddPoint() {
		return addPoint;
	}
	public void setAddPoint(int addPoint) {
		this.addPoint = addPoint;
	}
	public String getGoldValue() {
		return goldValue;
	}
	public void setGoldValue(String goldValue) {
		this.goldValue = goldValue;
	}
	public String getExperienceValue() {
		return experienceValue;
	}
	public void setExperienceValue(String experienceValue) {
		this.experienceValue = experienceValue;
	}
	
}
