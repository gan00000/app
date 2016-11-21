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
public class PhoneAreaTypeResponse extends BaseResponseBean {
	/**
	 * 账号相关处理结果{@link ResultBean}
	 */
	private PhoneAreaResultBean phoneAreaBean;
	private ArrayList<PhoneAreaBean> mPhoneAreas;
	@Override
	public void setValues(Object object) {
		JSONObject jsonObject = (JSONObject) object;
		phoneAreaBean = new PhoneAreaResultBean();
		mPhoneAreas = new ArrayList<PhoneAreaBean>();
		phoneAreaBean.setCode(jsonObject.optString("code"));
		phoneAreaBean.setMessage(jsonObject.optString("message"));
		if(jsonObject.has("result")){
			JSONArray jsonArray= jsonObject.optJSONArray("result");
			PhoneAreaBean bean = null;
			for (int i = 0; i < jsonArray.length(); i++) {
				bean = new PhoneAreaBean();
				jsonObject = jsonArray.optJSONObject(i);
				bean.setKey(jsonObject.optString("key"));
				bean.setValue(jsonObject.optString("value"));
				bean.setPattern(jsonObject.optString("pattern"));
				mPhoneAreas.add(bean);
			}
			phoneAreaBean.setmPhoneAreas(mPhoneAreas);
		}
	}

	public PhoneAreaResultBean getPhoneAreaResultBean() {
		return phoneAreaBean;
	}

}
