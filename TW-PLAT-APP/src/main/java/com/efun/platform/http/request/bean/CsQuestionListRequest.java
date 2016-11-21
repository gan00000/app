package com.efun.platform.http.request.bean;

/**
 * 客服-常见问题-问题列表
 * @author lgh
 * 
 */
public class CsQuestionListRequest extends BaseRequestBean {
	private String questionType;
	private String areaDesc;
	private String fromType;
	private String currentPage;
	private String pageSize;
	private String device;
	public CsQuestionListRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CsQuestionListRequest(String questionType, String areaDesc,
			String fromType, String currentPage, String pageSize,String device) {
		super();
		this.questionType = questionType;
		this.areaDesc = areaDesc;
		this.fromType = fromType;
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		this.device = device;
	}
	public String getQuestionType() {
		return questionType;
	}
	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}
	public String getAreaDesc() {
		return areaDesc;
	}
	public void setAreaDesc(String areaDesc) {
		this.areaDesc = areaDesc;
	}
	public String getFromType() {
		return fromType;
	}
	public void setFromType(String fromType) {
		this.fromType = fromType;
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
	public String getDevice() {
		return device;
	}
	public void setDevice(String device) {
		this.device = device;
	}
	
}
