package com.efun.platform.module.summary.bean;

import com.efun.platform.module.BaseDataBean;

/**
 * 活动游戏
 * 
 * @author itxuxxey
 * 
 */
public class EventGameBean extends BaseDataBean {
	private String gameCode;
	private String gameName;
	private String actGameCode;
	private String gameDesc;
	private String gameType;
	private String bigpic;
	private String smallpic;
	private String url;
	private int like;
	private String androidDownload;
	private String hkAndroidDownloadURL;
	private String androidVersion;
	private String packageName;
	private String packageSize;
	private String videoUrl;
	private String version;
	private String pic_display;
	private String hkPackageName;
	private String hkAndroidVersion;
	private String hkIOSgameCode;
	private String fbUrl;
	private String apkDownloadUrl;

	private int status;

	public EventGameBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EventGameBean(String gameCode, String gameName, String actGameCode,
			String gameDesc, String gameType, String bigpic, String smallpic,
			String url, int like, String androidDownload,
			String androidVersion, String packageName, String packageSize,
			String videoUrl, String version, String pic_display,
			String hkAndroidDownloadURL, String hkPackageName,
			String hkAndroidVersion, String hkIOSgameCode,String fbUrl,String apkDownloadUrl) {
		super();
		this.gameCode = gameCode;
		this.gameName = gameName;
		this.actGameCode = actGameCode;
		this.gameDesc = gameDesc;
		this.gameType = gameType;
		this.bigpic = bigpic;
		this.smallpic = smallpic;
		this.url = url;
		this.like = like;
		this.androidDownload = androidDownload;
		this.androidVersion = androidVersion;
		this.packageName = packageName;
		this.packageSize = packageSize;
		this.videoUrl = videoUrl;
		this.version = version;
		this.pic_display = pic_display;
		this.hkAndroidDownloadURL = hkAndroidDownloadURL;
		this.hkPackageName = hkPackageName;
		this.hkAndroidVersion = hkAndroidVersion;
		this.hkIOSgameCode = hkIOSgameCode;
		this.fbUrl = fbUrl;
		this.apkDownloadUrl = apkDownloadUrl;
	}

	public String getPic_display() {
		return pic_display;
	}

	public void setPic_display(String pic_display) {
		this.pic_display = pic_display;
	}

	public String getGameDesc() {
		return gameDesc;
	}

	public void setGameDesc(String gameDesc) {
		this.gameDesc = gameDesc;
	}

	public String getAndroidDownload() {
		return androidDownload;
	}

	public void setAndroidDownload(String androidDownload) {
		this.androidDownload = androidDownload;
	}

	public String getBigpic() {
		return bigpic;
	}

	public void setBigpic(String bigpic) {
		this.bigpic = bigpic;
	}

	public String getGameCode() {
		return gameCode;
	}

	public void setGameCode(String gameCode) {
		this.gameCode = gameCode;
	}

	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	public String getActGameCode() {
		return actGameCode;
	}

	public void setActGameCode(String actGameCode) {
		this.actGameCode = actGameCode;
	}

	public String getGameType() {
		return gameType;
	}

	public void setGameType(String gameType) {
		this.gameType = gameType;
	}

	public String getSmallpic() {
		return smallpic;
	}

	public void setSmallpic(String smallpic) {
		this.smallpic = smallpic;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getLike() {
		return like;
	}

	public void setLike(int like) {
		this.like = like;
	}

	public String getAndroidVersion() {
		return androidVersion;
	}

	public void setAndroidVersion(String androidVersion) {
		this.androidVersion = androidVersion;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getPackageSize() {
		return packageSize;
	}

	public void setPackageSize(String packageSize) {
		this.packageSize = packageSize;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getHkAndroidDownloadURL() {
		return hkAndroidDownloadURL;
	}

	public void setHkAndroidDownloadURL(String hkAndroidDownloadURL) {
		this.hkAndroidDownloadURL = hkAndroidDownloadURL;
	}

	public String getHkPackageName() {
		return hkPackageName;
	}

	public void setHkPackageName(String hkPackageName) {
		this.hkPackageName = hkPackageName;
	}

	public String getHkAndroidVersion() {
		return hkAndroidVersion;
	}

	public void setHkAndroidVersion(String hkAndroidVersion) {
		this.hkAndroidVersion = hkAndroidVersion;
	}

	public String getHkIOSgameCode() {
		return hkIOSgameCode;
	}

	public void setHkIOSgameCode(String hkIOSgameCode) {
		this.hkIOSgameCode = hkIOSgameCode;
	}

	public String getFbUrl() {
		return fbUrl;
	}

	public void setFbUrl(String fbUrl) {
		this.fbUrl = fbUrl;
	}

	public String getApkDownloadUrl() {
		return apkDownloadUrl;
	}

	public void setApkDownloadUrl(String apkDownloadUrl) {
		this.apkDownloadUrl = apkDownloadUrl;
	}

}
