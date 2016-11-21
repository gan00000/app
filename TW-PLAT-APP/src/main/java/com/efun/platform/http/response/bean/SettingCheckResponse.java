package com.efun.platform.http.response.bean;

import org.json.JSONObject;

import com.efun.platform.status.bean.VersionBean;
/**
 * 设置 - 检查版本更新
 * @author Jesse
 *
 */
public class SettingCheckResponse extends BaseResponseBean{
	/**
	 * 版本 {@link VersionBean}
	 */
	private VersionBean response;
	@Override
	public void setValues(Object object) {
		JSONObject jsonObject = (JSONObject) object;
		response = new VersionBean();
		response.setIsUpdate(jsonObject.optString("isUpdate"));
		response.setDownloadUrl(jsonObject.optString("downloadUrl"));
		response.setUpdateDesc(jsonObject.optString("updateDesc"));
		response.setVersion(jsonObject.optString("version"));
		response.setIsforce(jsonObject.optString("isforce"));
		response.setAudited(jsonObject.optString("audited"));
		response.setVersionName(jsonObject.optString("versionName"));
	}
	public VersionBean getVersion() {
		return response;
	}
}
