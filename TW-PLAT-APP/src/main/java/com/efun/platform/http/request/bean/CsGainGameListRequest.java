package com.efun.platform.http.request.bean;

/**
 * 客服- 提问 -游戏列表
 * @author Jesse
 *
 */
public class CsGainGameListRequest extends BaseRequestBean {

	private String dept;//区域：31表示港台，33表示亚欧
	private String gpId;//固定传1
	private String type;//固定app
	private String pid;//1：表示android，2：表示Ios

	public CsGainGameListRequest() {
		super();
	}

	public CsGainGameListRequest(String dept, String gpId, String type, String pid) {
		super();
		this.dept = dept;
		this.gpId = gpId;
		this.type = type;
		this.pid = pid;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getGpId() {
		return gpId;
	}

	public void setGpId(String gpId) {
		this.gpId = gpId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

}
