package com.efun.platform.http.request.bean;

/**
 * 新闻资讯- 点赞
 * @author itxuxxey
 *
 */
public class SummaryPraiseRequest extends BaseRequestBean{
	private String uid;
	private String nid;
	private String platform;
	private String sign;
	private String timestamp;
	private String version;
	private String packageVersion;
	private String language;
	private String fromType;
	public SummaryPraiseRequest() {
		super();
	}
	public SummaryPraiseRequest(String uid, String nid, String platform,
			String sign, String timestamp, String version,
			String packageVersion, String language, String fromType) {
		super();
		this.uid = uid;
		this.nid = nid;
		this.platform = platform;
		this.sign = sign;
		this.timestamp = timestamp;
		this.version = version;
		this.packageVersion = packageVersion;
		this.language = language;
		this.fromType = fromType;
	}
	
}
