package com.efun.platform.http.response.bean;

import org.json.JSONObject;

import com.efun.platform.module.person.bean.UpLoadHeaderImgBean;
import com.efun.platform.module.person.bean.UserUpdateBean;
/**
 * 玩家 - 上传头像
 * @author Jesse
 *
 */
public class UserUpdateHeaderResponse extends BaseResponseBean{
	private static final long serialVersionUID = 1L;
	/**
	 * 上传头像 {@link UserUpdateBean}
	 */
	private UpLoadHeaderImgBean mResponse ;
	@Override
	public void setValues(Object object) {
		JSONObject jsonObject= (JSONObject) object;
		mResponse = new UpLoadHeaderImgBean();
		mResponse.setCode(jsonObject.optString("code"));
		mResponse.setImgUrl(jsonObject.optString("imgUrl"));
		mResponse.setMessage(jsonObject.optString("message"));
	}
	public UpLoadHeaderImgBean getUpLoadHeaderImgBean() {
		return mResponse;
	}
}
