package com.efun.platform.http.response;

import java.util.Observable;

import com.efun.platform.http.response.bean.BaseResponseBean;
/**
 * 响应对象
 * @author Jesse
 *
 */
public class IPlatResponse extends Observable {
	/**
	 * 当前状态码
	 */
	private int status = -1;
	/**
	 * 响应类 {@link BaseResponseBean}
	 */
	private BaseResponseBean mBaseResponseBean;

	public IPlatResponse() {

	}

	public BaseResponseBean getBaseResponseBean() {
		return mBaseResponseBean;
	}

	public IPlatResponse setBaseResponseBean(BaseResponseBean mBaseResponseBean) {
		this.mBaseResponseBean = mBaseResponseBean;
		return this;
	}

	/**
	 * 通知数据变化
	 * 
	 * @param s
	 */
	public void setStatus(int s) {
		status = s;
		setChanged();
		notifyObservers();
	}

	public int getStatus() {
		return status;
	}
}
