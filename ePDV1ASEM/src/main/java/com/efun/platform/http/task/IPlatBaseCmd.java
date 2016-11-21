package com.efun.platform.http.task;

import java.io.Serializable;

/**
 * 父类任务类
 * 
 * @author itxuxxey
 * 
 */
public class IPlatBaseCmd implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 任务id，可以用于以后取消操作
	 */
	private String commandId;
	/**
	 * 请求接口回调
	 */
	private IPlatCallBack callback;

	public IPlatBaseCmd() {
		this.commandId = null;
		this.callback = null;
	}

	/**
	 * 获取任务ID
	 * 
	 * @return
	 */
	public String getCommandId() {
		return commandId;
	}

	/**
	 * 设置任务ID
	 * 
	 * @param commandId
	 */
	public void setCommandId(String commandId) {
		this.commandId = commandId;
	}

	/**
	 * 获取回调
	 * 
	 * @return {@link IPlatCallBack}
	 */
	public IPlatCallBack getCallback() {
		return callback;
	}

	/**
	 * 设置回调
	 * 
	 * @param callback
	 *            {@link IPlatCallBack}
	 */
	public void setCallback(IPlatCallBack callback) {
		this.callback = callback;
	}

}
