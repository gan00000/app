package com.efun.platform.http.request.bean;

/**
 * 游戏 - 特定模块中的游戏列表
 * @author harvery
 *
 */
public class GameListOfModuleRequest extends BaseRequestBean{
	private String platform;
	private String module;
	private String version;
	private String packageVersion;
	private String fromType;
	private String language;
	
	public GameListOfModuleRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GameListOfModuleRequest(String platform, String module,
			String version, String packageVersion, String fromType,
			String language) {
		super();
		this.platform = platform;
		this.module = module;
		this.version = version;
		this.packageVersion = packageVersion;
		this.fromType = fromType;
		this.language = language;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
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
	
	
}