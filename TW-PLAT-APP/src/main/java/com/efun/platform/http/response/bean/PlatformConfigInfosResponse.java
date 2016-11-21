package com.efun.platform.http.response.bean;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.efun.platform.module.bean.ConfigInfoBean;
import com.efun.platform.module.cs.bean.CsAskBean;
/**
 * 文件 - 平台配置信息
 *
 */
public class PlatformConfigInfosResponse extends BaseResponseBean {
	
	/**
	 * {@link ArrayList<ConfigInfoBean>}
	 */
	private ArrayList<ConfigInfoBean> response;
	
	@Override
	public void setValues(Object object) {
		JSONArray objectArray = (JSONArray) object;
		if(objectArray != null && objectArray.length() > 0){
			response = new ArrayList<ConfigInfoBean>();
			ConfigInfoBean bean = null;
			JSONObject jsonObject = null;
			for(int i = 0 ; i < objectArray.length(); i++){
				bean = new ConfigInfoBean();
				jsonObject = objectArray.optJSONObject(i);
				bean.setId(jsonObject.optString("id"));
				bean.setName(jsonObject.optString("name"));
				bean.setSubName(jsonObject.optString("subName"));
				bean.setIcon(jsonObject.optString("icon"));
				bean.setUrl(jsonObject.optString("url"));
				bean.setOpen(jsonObject.optBoolean("isOpen"));
				bean.setAuditIsOpen(jsonObject.optBoolean("auditIsOpen"));
				JSONArray arrayObjects2 = jsonObject.optJSONArray("subList");
				bean.setSubList(processData(arrayObjects2));
				response.add(bean);
			}
			
		}
		
	}
	
	private ArrayList<ConfigInfoBean> processData(JSONArray arrayObjects){
		ArrayList<ConfigInfoBean> ArrayBean = null;
		if(arrayObjects != null && arrayObjects.length() > 0){
			ArrayBean = new ArrayList<ConfigInfoBean>();
			ConfigInfoBean bean = null;
			JSONObject jsonObject = null;
			for(int i = 0 ; i < arrayObjects.length(); i++){
				bean = new ConfigInfoBean();
				jsonObject = arrayObjects.optJSONObject(i);
				bean.setId(jsonObject.optString("id"));
				bean.setName(jsonObject.optString("name"));
				bean.setSubName(jsonObject.optString("subName"));
				bean.setIcon(jsonObject.optString("icon"));
				bean.setUrl(jsonObject.optString("url"));
				bean.setOpen(jsonObject.optBoolean("isOpen"));
				bean.setAuditIsOpen(jsonObject.optBoolean("auditIsOpen"));
				bean.setVersionName(jsonObject.optString("versionName",""));
				JSONArray arrayObjects2 = jsonObject.optJSONArray("subList");
				bean.setSubList(processData(arrayObjects2));
				ArrayBean.add(bean);
			}
			
		}
		return ArrayBean;
	}

	public ArrayList<ConfigInfoBean> getResponse() {
		return response;
	}
}
