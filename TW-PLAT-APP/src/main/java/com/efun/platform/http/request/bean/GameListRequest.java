package com.efun.platform.http.request.bean;

/**
 * 游戏 - 游戏列表
 * @author Jesse
 *
 */
public class GameListRequest extends BaseRequestBean{
	/**
	 * 当前页面
	 */
	private String currentPage;
	/**
	 * 每页数目
	 */
	private String pageSize;
	/**
	 * 平台标志
	 */
	private String plateform;
	
	public GameListRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	public GameListRequest(String currentPage, String pageSize, String plateform) {
		super();
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		this.plateform = plateform;
	}
	public String getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}
	public String getPageSize() {
		return pageSize;
	}
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
	public String getPlateform() {
		return plateform;
	}
	public void setPlateform(String plateform) {
		this.plateform = plateform;
	}
}
