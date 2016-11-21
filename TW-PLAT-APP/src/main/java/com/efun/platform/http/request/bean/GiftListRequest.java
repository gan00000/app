package com.efun.platform.http.request.bean;


/**
 * 好康 - 礼包 - 礼包列表
 * @author Jesse
 * @author itxuxxey 20150617添加
 *
 */
public class GiftListRequest extends BaseRequestBean{
	/**
	 * 平臺標識
	 */
	private String platform;
	/**
	 * 當前请求页码
	 */
	private String currentPage;
	/**
	 * 每页显示的条数
	 */
	private String pageSize;
	/**
	 * 用戶ID
	 */
	private String uid;
	private String sign;
	private String timestamp;
	private String version;//20150617添加
	private String packageVersion;
	private String language;
	private String isUnique;
	public GiftListRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GiftListRequest(String platform, String currentPage,
			String pageSize) {
		super();
		this.platform = platform;
		this.currentPage = currentPage;
		this.pageSize = pageSize;
	}
	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
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

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
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

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getIsUnique() {
		return isUnique;
	}

	public void setIsUnique(String isUnique) {
		this.isUnique = isUnique;
	}
	

}
