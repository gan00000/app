package com.efun.platform.module.base.impl;

import com.efun.platform.module.BaseDataBean;

/**
 * List Item's button Click Listener
 * @author itxuxxey
 *
 */
public interface OnEfunItemAttrsClickListener{
	/**
	 * 点击事件
	 * @param position 下标
	 */
	public void onItemClick(int position,int type,BaseDataBean bean);
}
