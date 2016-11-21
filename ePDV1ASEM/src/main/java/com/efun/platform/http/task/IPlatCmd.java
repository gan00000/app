package com.efun.platform.http.task;

import com.efun.platform.http.response.IPlatResponse;

/**
 * 任务类-子类
 * 
 * @author itxuxxey
 * 
 */
public abstract class IPlatCmd extends IPlatBaseCmd {

	private static final long serialVersionUID = 1L;

	/**
	 * 请求后封装的响应类
	 * 
	 * @return
	 */
	public abstract IPlatResponse getResponse();

	/**
	 * 执行操作
	 */
	public abstract void execute() throws Exception;
}
