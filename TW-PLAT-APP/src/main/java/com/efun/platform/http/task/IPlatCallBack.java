package com.efun.platform.http.task;

/**
 * 请求回调
 * 
 * @author Jesse
 */
public interface IPlatCallBack {
	/**
	 * 任务
	 * 
	 * @param command
	 *            {@link IPlatCmd}
	 */
	public void cmdCallBack(IPlatCmd command);
}
