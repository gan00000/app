package com.efun.platform.module.person.bean;

import com.efun.platform.module.BaseDataBean;

/**
 * 上传玩家头像
 *  @author itxuxxey
 */

public class UpLoadHeaderImgBean extends BaseDataBean{
	private static final long serialVersionUID = 1L;
	private String code;
	private String imgUrl;
	private String message;
	
	public UpLoadHeaderImgBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UpLoadHeaderImgBean(String code, String imgUrl, String message) {
		super();
		this.code = code;
		this.imgUrl = imgUrl;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
