package com.efun.platform.module.base.impl;

/**
 * 登陆完成接口
 * @author Jesse
 *
 */
public interface OnLoginFinishListener extends OnEfunListener{
	/**
	 * 登陆接口
	 * @param completed true代表登陆过程中 成功
	 */
	public void loginCompleted(boolean completed);
}
