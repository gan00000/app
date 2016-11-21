package com.efun.platform.http.response.bean;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.efun.platform.module.cs.bean.CsGainGameBean;
import com.efun.platform.module.cs.bean.CsGainGameItemBean;
import com.efun.platform.module.cs.bean.CsGainRoleBean;
import com.efun.platform.module.cs.bean.CsGainRoleItemBean;
/**
 * 客服- 提问-选角色
 * @author Jesse
 *
 */
public class CsGainRoleListResponse extends BaseResponseBean {

	private CsGainRoleBean response;
	@Override
	public void setValues(Object object) {
		JSONObject jsonObject = (JSONObject) object;
		response = new CsGainRoleBean();
		response.setCode(jsonObject.optString("code"));
		response.setMessage(jsonObject.optString("message"));
		ArrayList<CsGainRoleItemBean> roleList = new ArrayList<CsGainRoleItemBean>();
		if(jsonObject.has("list")){
			JSONArray jsonArray = jsonObject.optJSONArray("list");
			CsGainRoleItemBean bean = null;
			for (int i = 0; i < jsonArray.length(); i++) {
				jsonObject = jsonArray.optJSONObject(i);
				bean = new CsGainRoleItemBean();
				bean.setLevel(jsonObject.optInt("level"));
				bean.setName(jsonObject.optString("name"));
				bean.setRoleid(jsonObject.optString("roleid"));
				bean.setSubgame(jsonObject.optString("subgame"));
				roleList.add(bean);
			}
		}
		response.setRoleList(roleList);
	}

	public CsGainRoleBean getCsGainRoleBean() {
		return response;
	}
}
