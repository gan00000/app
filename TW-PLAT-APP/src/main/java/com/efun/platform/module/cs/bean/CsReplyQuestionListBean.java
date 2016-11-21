package com.efun.platform.module.cs.bean;

import java.util.ArrayList;

import com.efun.platform.module.BaseDataBean;
/**
 * 回答问题列表
 * @author Jesse
 *
 */
public class CsReplyQuestionListBean extends BaseDataBean {
	private int pageSize;
	private int pageIndex;
	private int total;
	private int totalPage;

	private ArrayList<CsReplyQuestionBean> cReplayQuestionBeans = new ArrayList<CsReplyQuestionBean>();

	public ArrayList<CsReplyQuestionBean> getcReplayQuestionBeans() {
		return cReplayQuestionBeans;
	}

	public void setcReplayQuestionBeans(ArrayList<CsReplyQuestionBean> cReplayQuestionBeans) {
		this.cReplayQuestionBeans = cReplayQuestionBeans;
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
	}
}
