package com.efun.platform.http.response.bean;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.efun.platform.module.account.bean.PhoneAreaBean;
import com.efun.platform.module.account.bean.PhoneAreaResultBean;
import com.efun.platform.module.account.bean.ResultBean;
import com.efun.platform.module.game.bean.GameItemBean;
/**
 * 獲取手機號碼地區
 * @author itxuxxey
 *
 */
public class PhoneAreaCodeResponse extends BaseResponseBean {
	/**
	 * 账号相关处理结果{@link ResultBean}
	 */
	private ArrayList<PhoneAreaBean> mPhoneAreas;
	@Override
	public void setValues(Object object) {
			JSONArray jsonArray= (JSONArray) object;
			PhoneAreaBean bean = null;
			JSONObject jsonObject = null;
			if(jsonArray != null && jsonArray.length() > 0){
				mPhoneAreas = new ArrayList<PhoneAreaBean>();
				for (int i = 0; i < jsonArray.length(); i++) {
					bean = new PhoneAreaBean();
					jsonObject = jsonArray.optJSONObject(i);
					bean.setKey(jsonObject.optString("key"));
					bean.setValue(jsonObject.optString("value"));
					bean.setPattern(jsonObject.optString("pattern"));
					bean.setText(jsonObject.optString("text"));
					mPhoneAreas.add(bean);
				}
			}
	}

	public ArrayList<PhoneAreaBean> getPhoneAreaResultBean() {
		return mPhoneAreas;
	}

}
