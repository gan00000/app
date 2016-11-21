package com.efun.platform.module;

import java.io.Serializable;
/**
 * 数据
 * @author Jesse
 *
 */
public class BaseDataBean implements Serializable{
	private static final long serialVersionUID = 1L;
	private int beanType;
	
	public BaseDataBean(){
		super();
	}
	
	public BaseDataBean(int beanType) {
		super();
		this.beanType = beanType;
	}

	public int getBeanType() {
		return beanType;
	}
	public void setBeanType(int beanType) {
		this.beanType = beanType;
	}
}
