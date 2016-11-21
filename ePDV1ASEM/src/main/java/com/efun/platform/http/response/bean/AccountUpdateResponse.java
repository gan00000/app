package com.efun.platform.http.response.bean;

import org.json.JSONObject;

import com.efun.platform.module.account.bean.ResultBean;
import com.efun.platform.module.account.bean.User;
import com.efun.platform.utils.Const.HttpParam;
import com.efun.platform.utils.StoreUtil;
/**
 * 账号
 * @author itxuxxey
 *
 */
public class AccountUpdateResponse extends BaseResponseBean {
	private static final long serialVersionUID = 1L;
	/**
	 * 玩家信息 {@link User}
	 */
	private User userInfoBean;
	@Override
	public void setValues(Object object) {
		JSONObject jsonObject = (JSONObject) object;
		userInfoBean = new User();
		userInfoBean.setCode(jsonObject.optString("code"));
		userInfoBean.setMessage(jsonObject.optString("message"));
		if (jsonObject.has("uid")&& userInfoBean.getCode().equals(HttpParam.RESULT_1000)) {
			String accessToken = jsonObject.optString("accessToken");
			long mUid = jsonObject.optLong("uid");
			
			String[] keys = new String[]{"accessToken"};
			String[] values = new String[]{accessToken};
//			boolean flag = Store.changeNotify(getContext(),
//					keys,values,AccountUpdateResponse.class);
			
			boolean flag = StoreUtil.valueChangeNotify(getContext(),
					StoreUtil.Json_file_name, 
					StoreUtil.Json_key_account_update_response, 
					keys, 
					values);
			
			if(flag){
	//			Store.saveResponseByClazz(getContext(), AccountUpdateResponse.class,getResponseJson2String());
				StoreUtil.saveValue(getContext(), 
						StoreUtil.Json_file_name, 
						StoreUtil.Json_key_account_update_response, 
						getResponseJson2String());
				
				keys = new String[]{"accessToken"};
				values = new String[]{accessToken};
//				Store.saveRequestByClazz(getContext(),
//						keys, 
//						values, 
//						AccountUpdateRequest.class);
				StoreUtil.saveValues(getContext(), StoreUtil.Request_file_name_login_update, keys, values);
			}
			
			keys = new String[]{"uid"};
			values = new String[]{mUid+""};
//			flag = Store.changeNotify(getContext(),
//					keys,values,AccountResponse.class);
			flag = StoreUtil.valueChangeNotify(getContext(),
					StoreUtil.Json_file_name,
					StoreUtil.Json_key_account_response,
					keys, 
					values);
			if(!flag){
//				Store.saveResponseByClazz(getContext(), AccountResponse.class,getResponseJson2String());
				StoreUtil.saveValue(getContext(), 
						StoreUtil.Json_file_name,
						StoreUtil.Json_key_account_response,
						getResponseJson2String());
			}
		}
	}
	

	public ResultBean getResultBean() {
		return userInfoBean;
	}

}
