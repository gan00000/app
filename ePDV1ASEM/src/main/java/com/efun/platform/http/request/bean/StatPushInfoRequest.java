package com.efun.platform.http.request.bean;

/**
 * 数据统计
 * @author itxuxxey
 *
 */
public class StatPushInfoRequest extends BaseRequestBean{
	private String pushMessageId;//推送信息ID
	private String lookTime;//查看时间
	private String userId;//玩家UID
	private String version;//ios或android
	private String packageVersion;//应用(游戏)包版本
	private String idfa;//ios设备标识
	private String cfuuid;//ios设备标识
	private String mac;//android设备mac地址
	private String androidid;//android设备androidid
	private String imei;//android设备imei
	
	public StatPushInfoRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public StatPushInfoRequest(String pushMessageId, String lookTime,
			String userId, String version, String packageVersion, 
			String mac, String androidid, String imei) {
		super();
		this.pushMessageId = pushMessageId;
		this.lookTime = lookTime;
		this.userId = userId;
		this.version = version;
		this.packageVersion = packageVersion;
		this.mac = mac;
		this.androidid = androidid;
		this.imei = imei;
	}

	public String getPushMessageId() {
		return pushMessageId;
	}

	public void setPushMessageId(String pushMessageId) {
		this.pushMessageId = pushMessageId;
	}

	public String getLookTime() {
		return lookTime;
	}

	public void setLookTime(String lookTime) {
		this.lookTime = lookTime;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public String getIdfa() {
		return idfa;
	}

	public void setIdfa(String idfa) {
		this.idfa = idfa;
	}

	public String getCfuuid() {
		return cfuuid;
	}

	public void setCfuuid(String cfuuid) {
		this.cfuuid = cfuuid;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getAndroidid() {
		return androidid;
	}

	public void setAndroidid(String androidid) {
		this.androidid = androidid;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}
	
}
