package com.efun.platform.http.request.bean;

/**
 * 平台点
 * @author itxuxxey
 *
 */
public class PersonPlaformPointRequest extends BaseRequestBean{
	private String userId;
	private String crossdomain;
	private String sign;
	private String timestamp;
	public PersonPlaformPointRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PersonPlaformPointRequest(String userId, String crossdomain,
			String sign, String timestamp) {
		super();
		this.userId = userId;
		this.crossdomain = crossdomain;
		this.sign = sign;
		this.timestamp = timestamp;
	}
	
}
