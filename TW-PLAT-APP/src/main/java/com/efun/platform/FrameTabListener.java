package com.efun.platform;
/**
 * Tab 切换监听
 * 当非TAB页面需要切换Tab时，方法被执行
 * @author Jesse
 *
 */
public interface FrameTabListener {
	/**
	 * 切换Tab
	 * @param index Tab下标
	 */
	public void onTabChange(int index);
}
