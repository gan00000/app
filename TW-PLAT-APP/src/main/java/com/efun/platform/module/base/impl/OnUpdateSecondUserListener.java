package com.efun.platform.module.base.impl;

import com.efun.platform.module.account.bean.User;
/**
 * 用户信息更新接口
 * @author Jesse
 *
 */
public interface OnUpdateSecondUserListener extends OnEfunListener{
	/**
	 * 
	 * @param userInfo 用户信息
	 */
	public void onUpdate(User userInfo);
}
