package com.efun.platform.module.welfare.bean;

import java.util.ArrayList;

import com.efun.platform.module.BaseDataBean;
/**
 * 福利
 * @author harvery
 *
 */
public class WelfareListBean extends BaseDataBean{
	private String code;
	private String message;
	private ArrayList<WelfareItemBean> mResponse;
	public WelfareListBean() {
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
	public ArrayList<WelfareItemBean> getmResponse() {
		return mResponse;
	}
	public void setmResponse(ArrayList<WelfareItemBean> mResponse) {
		this.mResponse = mResponse;
	}
	
}
