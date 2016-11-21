package com.efun.platform.module.bean;

import com.efun.platform.module.BaseDataBean;
/**
 * 列表数据的 页码
 * @author Jesse
 *
 */
public class PageInfoBean extends BaseDataBean{
	/**
	 * Page Size
	 */
	private int pageSize;
	/**
	 * 当前页码
	 */
	private int pageIndex;
	/**
	 * 总条数
	 */
	private int total;
	/**
	 * 总页数
	 */
	private int totalPage;
	
	private boolean hasData;
	
	
	public PageInfoBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PageInfoBean(int pageSize, int pageIndex, int total,
			int totalPage) {
		super();
		this.pageSize = pageSize;
		this.pageIndex = pageIndex;
		this.total = total;
		this.totalPage = totalPage;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getTotalPage() {
		return totalPage;
	}
	
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
		if(totalPage!=0){
			setHasData(true);
		}
	}
	
	public boolean isHasData() {
		return hasData;
	}
	public void setHasData(boolean hasData) {
		this.hasData = hasData;
	}
	
	

	@Override
	public String toString() {
		return "pageSize:" + this.pageSize + "|" + "pageIndex:"
				+ this.pageIndex + "|" + "total:" + this.total + "|"
				+ "totalPage:" + this.totalPage + "|";
	}
}
