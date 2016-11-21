package com.efun.platform.module.base.impl;

import com.efun.platform.http.response.bean.BaseResponseBean;


/**
 * 请求返回
 * 
 * @author itxuxxey
 * 
 */
public interface OnRequestListener{
	/**
	 * 请求成功接口
	 * 
	 * @param responses
	 */
	public void onSuccess(int requestType,BaseResponseBean baseResponse);

	/**
	 * 请求失败接口
	 */
	public void onFailure(int requestType);
	
	/**
	 * 请求超时接口
	 * @param requestType
	 */
	public void onTimeout(int requestType);
	
	/**
	 * 请求无数据
	 */
	public void onNoData(int requestType,String noDataNotify);
}
