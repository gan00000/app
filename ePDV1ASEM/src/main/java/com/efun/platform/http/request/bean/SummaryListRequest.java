package com.efun.platform.http.request.bean;

import com.efun.platform.utils.Const.HttpParam;

/**
 * 资讯-资讯列表
 * @author itxuxxey
 *
 */
public class SummaryListRequest extends BaseRequestBean{
	private String platform;
	private String currentPage;
	private String pageSize;
	private String type;
	private String gameCode;//當前游戲gameCode 20151102添加
	private String uid;
	private String queryType;//查询与我相关新闻时传：about
	private String version;
	private String packageVersion;
	
	public SummaryListRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public SummaryListRequest( String platform,
			String currentPage, String pageSize,String uid,String type) {
		super();
		this.platform = platform;
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		this.type = type;
		this.uid = uid;
		this.queryType = HttpParam.ABOUT;
	}

	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getGameCode() {
		return gameCode;
	}
	public void setGameCode(String gameCode) {
		this.gameCode = gameCode;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getQueryType() {
		return queryType;
	}

	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getPackageVersion() {
		return packageVersion;
	}

	public void setPackageVersion(String packageVersion) {
		this.packageVersion = packageVersion;
	}
	
}
