package com.efun.platform.http.response.bean;

import org.json.JSONObject;

import com.efun.platform.module.interaction.bean.CsGetFBUserInfosBean;

public class AccountGetUserFBUidsByUidResponse extends BaseResponseBean {
//	private ArrayList<String> gameCodeList;
	private CsGetFBUserInfosBean response = null;

	@Override
	public void setValues(Object object) {
		JSONObject jsonObject = (JSONObject) object;
		response = new CsGetFBUserInfosBean();
		response.setCode(jsonObject.optString("code"));
		response.setMessage(jsonObject.optString("message"));
		response.setRelation(jsonObject.optString("relation"));
//		if(!TextUtils.isEmpty(relation)){
//			try {
//				gameCodeList = new ArrayList<String>();
//				JSONObject jsonObject2 = new JSONObject(relation);
//				Iterator keys = jsonObject2.keys();
//				while(keys.hasNext()){
//					gameCodeList.add((String) keys.next());				   
//				}
//				if(gameCodeList.size() != 0){
//					
//				}
//				
//				
//			} catch (JSONException e) {
//			}
		}

	public CsGetFBUserInfosBean getResponse() {
		return response;
	}

}
