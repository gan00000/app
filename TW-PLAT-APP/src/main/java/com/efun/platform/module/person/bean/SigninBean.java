package com.efun.platform.module.person.bean;

import java.io.Serializable;

import com.efun.platform.module.BaseDataBean;
/**
 * 签到奖励
 * @author itxuxxey
 *
 */
public class SigninBean extends BaseDataBean{
	private static final long serialVersionUID = 1L;
	private String code;
	private String message;
	private int points;
	private int exps;
	private int goldValue;
	private int signinDays;
	private boolean isTodayHasSigninGift; 
	private boolean siginDaysGetGameGift;
	public SigninBean() {
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
	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
	}
	public int getExps() {
		return exps;
	}
	public void setExps(int exps) {
		this.exps = exps;
	}
	public int getGoldValue() {
		return goldValue;
	}
	public void setGoldValue(int goldValue) {
		this.goldValue = goldValue;
	}
	public int getSigninDays() {
		return signinDays;
	}
	public void setSigninDays(int signinDays) {
		this.signinDays = signinDays;
	}
	public boolean isTodayHasSigninGift() {
		return isTodayHasSigninGift;
	}
	public void setTodayHasSigninGift(boolean isTodayHasSigninGift) {
		this.isTodayHasSigninGift = isTodayHasSigninGift;
	}
	public boolean isSiginDaysGetGameGift() {
		return siginDaysGetGameGift;
	}
	public void setSiginDaysGetGameGift(boolean siginDaysGetGameGift) {
		this.siginDaysGetGameGift = siginDaysGetGameGift;
	}

	
}
