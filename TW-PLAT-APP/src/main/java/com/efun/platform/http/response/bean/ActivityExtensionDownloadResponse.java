package com.efun.platform.http.response.bean;

import org.json.JSONObject;

import com.efun.platform.module.welfare.bean.ActExtensionDownloadBean;


public class ActivityExtensionDownloadResponse extends BaseResponseBean{
	private ActExtensionDownloadBean response;
	@Override
	public void setValues(Object object) {
		JSONObject jsonObject = (JSONObject) object;
		response = new ActExtensionDownloadBean();
		response.setCode(jsonObject.optString("code"));
		response.setMessage(jsonObject.optString("message"));
		if(jsonObject.has("params")){
			response.setParams(jsonObject.optString("params"));
		}
	}
	public ActExtensionDownloadBean getActExtensionDownloadBean() {
		return response;
	}
}
