package com.efun.platform.module.person.bean;

import com.efun.platform.module.BaseDataBean;
/**
 * 平台点
 * @author itxuxxey
 *
 */
public class PlatformPointBean extends BaseDataBean{
	private static final long serialVersionUID = 1L;
	private String code;
	private String freePoint;
	private String point;
	private String uid;
	private String message;
	private String sumPoint;
	public PlatformPointBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PlatformPointBean(int beanType) {
		super(beanType);
		// TODO Auto-generated constructor stub
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getFreePoint() {
		return freePoint;
	}
	public void setFreePoint(String freePoint) {
		this.freePoint = freePoint;
	}
	public String getPoint() {
		return point;
	}
	public void setPoint(String point) {
		this.point = point;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getSumPoint() {
		return sumPoint;
	}
	public void setSumPoint(String sumPoint) {
		this.sumPoint = sumPoint;
	}
	
	
	
}
