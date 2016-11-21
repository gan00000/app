package com.efun.platform.module.account.bean;
/**
 * 用户信息
 * @author itxuxxey
 *
 */
public class User extends ResultBean{
	private long createdTime;
	private long modifiedTime;
	private long uid;
	private String username;
	private String accountName;
	private String rango;
	private String rangoLevel;
	private int memberLevel;
	private int experienceValue;
	private int goldValue;
	private String address;//详细地址
	private String sex;
	private String icon;
	private String areaDesc;
	private int expPercentage;
	private int levelupExp;
	private int currentExp;
	private String isVip;
	private String isAccept;
	private String sign;
	private String privilege;
	private String password;
	private String accessToken;
	private String ip;
	private String loginType;
	private String thirdId;
	private String email;
	private String authed;
	private String auth_code;
	private String gameCode;
	private int gotExp;
	private int gotGold;
	private String isSendGold;
	private String urlParamString;
	private String timestamp;
	private String phone;
	private String bindEmail;
	//20160109
	private String apps;
	private String area;
	private String areaCode;
	private String deleted;
	private String encryptEmail;
	private String from;
	private String id;
	private String isAuthPhone;
	private String isAuthEmail;//个人中心个人资料参数，跟bindEmail一样的功能
	private int isVipShow;
	private String language;
	private int nextGotGold;
	private String packageVersion;
	private String telecomOperator;
	private int vipLikes;
	private boolean vipShow;
	private boolean settedSecurityAnswers;
	private String birthdayStr;
	private String city;
	private String line;
	private boolean isFinished;
	private boolean isGetAward;
	//20160707
	private String realName;
	private String idCard;
	//20160713
	private int signinDays;
	private boolean isSignin;
	private boolean isTodayHasSigninGift;
	private String md5Phone;
	private boolean getSigninGift;
	
	
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	public User(long uid, String username) {
		super();
		this.uid = uid;
		this.username = username;
	}
	
	public int getExperienceValue() {
		return experienceValue;
	}
	public void setExperienceValue(int experienceValue) {
		this.experienceValue = experienceValue;
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
	public String getUid() {
		return uid+"";
	}
	public void setUid(long uid) {
		this.uid = uid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public long getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(long createdTime) {
		this.createdTime = createdTime;
	}
	public long getModifiedTime() {
		return modifiedTime;
	}
	public void setModifiedTime(long modifiedTime) {
		this.modifiedTime = modifiedTime;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getRango() {
		return rango;
	}
	public void setRango(String rango) {
		this.rango = rango;
	}
	public String getRangoLevel() {
		return rangoLevel;
	}
	public void setRangoLevel(String rangoLevel) {
		this.rangoLevel = rangoLevel;
	}
	public int getMemberLevel() {
		return memberLevel;
	}
	public void setMemberLevel(int memberLevel) {
		this.memberLevel = memberLevel;
	}
	public int getGoldValue() {
		return goldValue;
	}
	public void setGoldValue(int goldValue) {
		this.goldValue = goldValue;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getAreaDesc() {
		return areaDesc;
	}
	public void setAreaDesc(String areaDesc) {
		this.areaDesc = areaDesc;
	}
	public int getExpPercentage() {
		return expPercentage;
	}
	public void setExpPercentage(int expPercentage) {
		this.expPercentage = expPercentage;
	}
	public int getLevelupExp() {
		return levelupExp;
	}
	public void setLevelupExp(int levelupExp) {
		this.levelupExp = levelupExp;
	}
	public int getCurrentExp() {
		return currentExp;
	}
	public void setCurrentExp(int currentExp) {
		this.currentExp = currentExp;
	}
	public String getIsVip() {
		return isVip;
	}
	public void setIsVip(String isVip) {
		this.isVip = isVip;
	}
	public String getIsAccept() {
		return isAccept;
	}
	public void setIsAccept(String isAccept) {
		this.isAccept = isAccept;
	}
	public String getPrivilege() {
		return privilege;
	}
	public void setPrivilege(String privilege) {
		this.privilege = privilege;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getLoginType() {
		return loginType;
	}
	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}
	public String getThirdId() {
		return thirdId;
	}
	public void setThirdId(String thirdId) {
		this.thirdId = thirdId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAuthed() {
		return authed;
	}
	public void setAuthed(String authed) {
		this.authed = authed;
	}
	public String getAuth_code() {
		return auth_code;
	}
	public void setAuth_code(String auth_code) {
		this.auth_code = auth_code;
	}
	public String getGameCode() {
		return gameCode;
	}
	public void setGameCode(String gameCode) {
		this.gameCode = gameCode;
	}
	public int getGotGold() {
		return gotGold;
	}
	public void setGotGold(int gotGold) {
		this.gotGold = gotGold;
	}
	public String getIsSendGold() {
		return isSendGold;
	}
	public void setIsSendGold(String isSendGold) {
		this.isSendGold = isSendGold;
	}
	public String getUrlParamString() {
		return urlParamString;
	}
	public void setUrlParamString(String urlParamString) {
		this.urlParamString = urlParamString;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getBindEmail() {
		return bindEmail;
	}
	public void setBindEmail(String bindEmail) {
		this.bindEmail = bindEmail;
	}
	public int getGotExp() {
		return gotExp;
	}
	public void setGotExp(int gotExp) {
		this.gotExp = gotExp;
	}
	public String getApps() {
		return apps;
	}
	public void setApps(String apps) {
		this.apps = apps;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getDeleted() {
		return deleted;
	}
	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}
	public String getEncryptEmail() {
		return encryptEmail;
	}
	public void setEncryptEmail(String encryptEmail) {
		this.encryptEmail = encryptEmail;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIsAuthPhone() {
		return isAuthPhone;
	}
	public void setIsAuthPhone(String isAuthPhone) {
		this.isAuthPhone = isAuthPhone;
	}
	public int getIsVipShow() {
		return isVipShow;
	}
	public void setIsVipShow(int isVipShow) {
		this.isVipShow = isVipShow;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public int getNextGotGold() {
		return nextGotGold;
	}
	public void setNextGotGold(int nextGotGold) {
		this.nextGotGold = nextGotGold;
	}
	public String getPackageVersion() {
		return packageVersion;
	}
	public void setPackageVersion(String packageVersion) {
		this.packageVersion = packageVersion;
	}
	public String getTelecomOperator() {
		return telecomOperator;
	}
	public void setTelecomOperator(String telecomOperator) {
		this.telecomOperator = telecomOperator;
	}
	public int getVipLikes() {
		return vipLikes;
	}
	public void setVipLikes(int vipLikes) {
		this.vipLikes = vipLikes;
	}
	public boolean isVipShow() {
		return vipShow;
	}
	public void setVipShow(boolean vipShow) {
		this.vipShow = vipShow;
	}
	public boolean isSettedSecurityAnswers() {
		return settedSecurityAnswers;
	}
	public void setSettedSecurityAnswers(boolean settedSecurityAnswers) {
		this.settedSecurityAnswers = settedSecurityAnswers;
	}
	public String getBirthday() {
		return birthdayStr;
	}
	public void setBirthday(String birthdayStr) {
		this.birthdayStr = birthdayStr;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getLine() {
		return line;
	}
	public void setLine(String line) {
		this.line = line;
	}
	public boolean isFinished() {
		return isFinished;
	}
	public void setFinished(boolean isFinished) {
		this.isFinished = isFinished;
	}
	public boolean isGetAward() {
		return isGetAward;
	}
	public void setGetAward(boolean isGetAward) {
		this.isGetAward = isGetAward;
	}
	public String getIsAuthEmail() {
		return isAuthEmail;
	}
	public void setIsAuthEmail(String isAuthEmail) {
		this.isAuthEmail = isAuthEmail;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public int getSigninDays() {
		return signinDays;
	}
	public void setSigninDays(int signinDays) {
		this.signinDays = signinDays;
	}
	public boolean isSignin() {
		return isSignin;
	}
	public void setSignin(boolean isSignin) {
		this.isSignin = isSignin;
	}
	public boolean isTodayHasSigninGift() {
		return isTodayHasSigninGift;
	}
	public void setTodayHasSigninGift(boolean isTodayHasSigninGift) {
		this.isTodayHasSigninGift = isTodayHasSigninGift;
	}
	public String getMd5Phone() {
		return md5Phone;
	}
	public void setMd5Phone(String md5Phone) {
		this.md5Phone = md5Phone;
	}
	public boolean isGetSigninGift() {
		return getSigninGift;
	}
	public void setGetSigninGift(boolean getSigninGift) {
		this.getSigninGift = getSigninGift;
	}
	
	
	
}
