package com.efun.platform.http.request.bean;


/**
 * 游戏-游戏评分
 * @author Harvery
 *
 */
public class GameVoteRequest extends BaseRequestBean{
	private String uid;//用户id
	private String platform;
	private String voteType;
	private String entityid;//当前游戏gameCode
	private String currentPage;
	private String pageSize;
	private String version;
	private String packageVersion;
	private String fromType;
	private String language;
	private String sign;
	private String timestamp;
	
	public GameVoteRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public String getVoteType() {
		return voteType;
	}
	public void setVoteType(String voteType) {
		this.voteType = voteType;
	}
	public String getEntityid() {
		return entityid;
	}
	public void setEntityid(String entityid) {
		this.entityid = entityid;
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
	public String getFromType() {
		return fromType;
	}
	public void setFromType(String fromType) {
		this.fromType = fromType;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
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
	
}
