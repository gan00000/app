package com.efun.platform.http.response.bean;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.efun.platform.module.account.bean.PhoneAreaBean;
import com.efun.platform.module.account.bean.PhoneAreaResultBean;
import com.efun.platform.module.account.bean.ResultBean;
import com.efun.platform.module.game.bean.GameItemBean;
import com.efun.platform.module.person.bean.PlatformPointBean;
/**
 * 平台点
 * @author itxuxxey
 *
 */
public class PersonPlatformPointResponse extends BaseResponseBean {
	/**
	 * 账号相关处理结果{@link ResultBean}
	 */
	private PlatformPointBean pointbean;
	@Override
	public void setValues(Object object) {
		JSONObject jsonObject = (JSONObject) object;
		pointbean = new PlatformPointBean();
		pointbean.setCode(jsonObject.optString("code"));
		pointbean.setMessage(jsonObject.optString("message"));
		pointbean.setFreePoint(jsonObject.optString("freePoint"));
		pointbean.setPoint(jsonObject.optString("point"));
		pointbean.setUid(jsonObject.optString("uid"));
		pointbean.setSumPoint(jsonObject.optString("sumPoint"));
	}

	public PlatformPointBean getPlatformPointBean() {
		return pointbean;
	}

}
