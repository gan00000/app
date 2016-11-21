package com.efun.platform.http.response.bean;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.efun.platform.module.cs.bean.CsGainServerBean;
import com.efun.platform.module.cs.bean.CsGainServerItemBean;
/**
 * 客服- 提问-选服务器
 * @author Jesse
 *
 */
public class CsGainServersListResponse extends BaseResponseBean {
	private CsGainServerBean response;

	@Override
	public void setValues(Object object) {
		JSONObject jsonObject = (JSONObject) object;
		response = new CsGainServerBean();
		response.setCode(jsonObject.optString("code"));
		response.setMessage(jsonObject.optString("message"));
		ArrayList<CsGainServerItemBean> serverList = new ArrayList<CsGainServerItemBean>();
		if(jsonObject.has("ServerList")){
			JSONArray jsonArray = jsonObject.optJSONArray("ServerList");
			CsGainServerItemBean bean = null;
			for (int i = 0; i < jsonArray.length(); i++) {
				jsonObject = jsonArray.optJSONObject(i);
				bean = new CsGainServerItemBean();
				bean.setGameDomain(jsonObject.optString("GameDomain"));
				bean.setServerCode(jsonObject.optString("ServerCode"));
				bean.setServerName(jsonObject.optString("ServerName"));
				bean.setGameCode(jsonObject.optString("gameCode"));
				bean.setInfo(jsonObject.optString("info"));
				bean.setIsCom(jsonObject.optInt("isCom"));
				bean.setServerPrefix(jsonObject.optString("serverPrefix"));
				bean.setStatus(jsonObject.optInt("status"));
				serverList.add(bean);
			}
		}
		response.setServerList(serverList);
	}

	public CsGainServerBean getCsGainServerBean() {
		return response;
	}
}
