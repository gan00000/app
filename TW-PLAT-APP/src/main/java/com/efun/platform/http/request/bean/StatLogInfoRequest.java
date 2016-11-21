package com.efun.platform.http.request.bean;

/**
 * 数据统计
 * 
 * @author itxuxxey
 * 
 */
public class StatLogInfoRequest extends BaseRequestBean {
	private String gameCode;// 游戏编号
	private String packageArea;// hk或tw
	private String fromType;// 数据来源 定值 app或sdk
	private String module;// 模块标识（多个做统计需要用到）,游戏列表gamelist,领取免费点freepoint
	private String operationType;// 操作类型 ,点击下载按钮 downloadClick,启动游戏SDK
									// startApp,使用平台账号登入 loginFromAPP,使用游戏账号登入
									// loginFromSDK,使用更新游戏updateClick
	private String uid;// 玩家UID
	private String mac;// 设备mac地址
	private String imei;// 设备IMEI
	private String idfa;// 设备idfa(ios专有，android只是在这里占个位置)
	private String cfuuid;// 设备cfuuid(ios专有，android只是在这里占个位置)
	private String deviceType;// 设备类型
	private String systemVersion;// 系统版本
	private String version;// ios或android
	private String packageVersion;// 应用(游戏)包版本
	private String platform;// 平台标识：tw
	// private String
	// sign;//签名，MD5(appkey+gameCode+operationType+timestamp),32位，大写
	// private String timestamp;//当前时间戳
	// private String language;//语言标识，zh_HK

	public StatLogInfoRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public StatLogInfoRequest(String gameCode, String packageArea,
			String fromType, String module, String operationType, String uid,
			String mac, String imei, String deviceType, String systemVersion,
			String version, String packageVersion, String platform) {
		super();
		this.gameCode = gameCode;
		this.packageArea = packageArea;
		this.fromType = fromType;
		this.module = module;
		this.operationType = operationType;
		this.uid = uid;
		this.mac = mac;
		this.imei = imei;
		this.deviceType = deviceType;
		this.systemVersion = systemVersion;
		this.version = version;
		this.packageVersion = packageVersion;
		this.platform = platform;
		// this.sign = sign;
		// this.timestamp = timestamp;
		// this.language = language;
	}

	public String getGameCode() {
		return gameCode;
	}

	public void setGameCode(String gameCode) {
		this.gameCode = gameCode;
	}

	public String getPackageArea() {
		return packageArea;
	}

	public void setPackageArea(String packageArea) {
		this.packageArea = packageArea;
	}

	public String getFromType() {
		return fromType;
	}

	public void setFromType(String fromType) {
		this.fromType = fromType;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
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

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getSystemVersion() {
		return systemVersion;
	}

	public void setSystemVersion(String systemVersion) {
		this.systemVersion = systemVersion;
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

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}
	// public String getSign() {
	// return sign;
	// }
	// public void setSign(String sign) {
	// this.sign = sign;
	// }
	// public String getTimestamp() {
	// return timestamp;
	// }
	// public void setTimestamp(String timestamp) {
	// this.timestamp = timestamp;
	// }
	// public String getLanguage() {
	// return language;
	// }
	// public void setLanguage(String language) {
	// this.language = language;
	// }

}
