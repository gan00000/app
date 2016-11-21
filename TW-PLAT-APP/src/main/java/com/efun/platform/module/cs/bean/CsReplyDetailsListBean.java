package com.efun.platform.module.cs.bean;

import java.util.ArrayList;

import com.efun.platform.module.BaseDataBean;
/**
 * 回答列表
 * @author Jesse
 *
 */
public class CsReplyDetailsListBean extends BaseDataBean {
	private String code;
	private ArrayList<CsReplyDetailsBean> mBeans = new ArrayList<CsReplyDetailsBean>();

	public CsReplyDetailsListBean() {
		super();
	}

	public CsReplyDetailsListBean(String code, ArrayList<CsReplyDetailsBean> mBeans) {
		super();
		this.code = code;
		this.mBeans = mBeans;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public ArrayList<CsReplyDetailsBean> getmBeans() {
		return mBeans;
	}

	public void setmBeans(ArrayList<CsReplyDetailsBean> mBeans) {
		this.mBeans = mBeans;
	}

}
