package com.efun.platform.module.account.bean;

import java.util.ArrayList;

import com.efun.platform.module.BaseDataBean;
/**
 * 账号处理结果
 * @author itxuxxey
 *
 */
public class PhoneAreaResultBean extends BaseDataBean{
	/**
	 * Code - 状态值
	 */
	private String code;
	/**
	 * Message - 返回消息
	 */
	private String message;
	/**
	 * 手機號碼區域數據集
	 */
	private ArrayList<PhoneAreaBean> mPhoneAreas; 
	
	public PhoneAreaResultBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PhoneAreaResultBean(String code, String message,
			ArrayList<PhoneAreaBean> mPhoneAreas) {
		super();
		this.code = code;
		this.message = message;
		this.mPhoneAreas = mPhoneAreas;
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

	public ArrayList<PhoneAreaBean> getmPhoneAreas() {
		return mPhoneAreas;
	}

	public void setmPhoneAreas(ArrayList<PhoneAreaBean> mPhoneAreas) {
		this.mPhoneAreas = mPhoneAreas;
	}

}
