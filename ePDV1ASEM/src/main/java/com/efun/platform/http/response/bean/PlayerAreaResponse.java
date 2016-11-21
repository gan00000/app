package com.efun.platform.http.response.bean;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.efun.platform.module.bean.PlayerAreaBean;
import com.efun.platform.module.bean.PlayerCityBean;
/**
 * 玩家区域
 * @author itxuxxey
 *
 */
public class PlayerAreaResponse extends BaseResponseBean {
	
	private ArrayList<PlayerAreaBean> mPlayerAreas;
	@Override
	public void setValues(Object object) {
			JSONArray jsonArray= (JSONArray) object;
			PlayerAreaBean bean = null;
			JSONObject jsonObject = null;
			if(jsonArray != null && jsonArray.length() > 0){
				mPlayerAreas = new ArrayList<PlayerAreaBean>();
				for (int i = 0; i < jsonArray.length(); i++) {
					bean = new PlayerAreaBean();
					jsonObject = jsonArray.optJSONObject(i);
					bean.setKey(jsonObject.optString("key"));
					bean.setValue(jsonObject.optString("value"));
					bean.setText(jsonObject.optString("text"));
					JSONArray arrayObjects = jsonObject.optJSONArray("citys");
					ArrayList<PlayerCityBean> cityBeans = null;
					if(arrayObjects != null && arrayObjects.length() > 0){
						cityBeans = new ArrayList<PlayerCityBean>();
						PlayerCityBean cityBean = null;
						JSONObject cityObject = null;
						for(int j = 0 ; j < arrayObjects.length(); j++){
							cityBean = new PlayerCityBean();
							cityObject = arrayObjects.optJSONObject(j);
							cityBean.setKey(cityObject.optString("key"));
							cityBean.setValue(cityObject.optString("value"));
							cityBean.setText(cityObject.optString("text"));
							cityBeans.add(cityBean);
						}
					}
					bean.setCitys(cityBeans);
					mPlayerAreas.add(bean);
				}
			}
	}

	public ArrayList<PlayerAreaBean> getPlayerAreaBeans() {
		return mPlayerAreas;
	}
}
