package com.efun.platform.module.account.bean;

import com.efun.platform.module.BaseDataBean;
/**
 * 手機號碼地區 
 * @author itxuxxey
 *
 */
public class PhoneAreaBean extends BaseDataBean{
	
	private String key;//手機號碼地區編號(文本：标识)
	private String value;//顯示內容（文本：区号）
	private String pattern;//正式表達式約束
	private String text;//显示内容
	
	public PhoneAreaBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PhoneAreaBean(String key, String value, String pattern) {
		super();
		this.key = key;
		this.value = value;
		this.pattern = pattern;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	

}
