package com.efun.platform.http.response.bean;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.efun.platform.module.account.bean.User;
import com.efun.platform.module.bean.ButtonControlBean;
import com.efun.platform.module.interaction.bean.CsAskBean;
import com.efun.platform.module.summary.bean.BannerItemBean;
import com.efun.platform.module.summary.bean.EventGameBean;
import com.efun.platform.module.summary.bean.SummaryHomeBean;
import com.efun.platform.module.summary.bean.SummaryItemBean;
import com.efun.platform.utils.Const.BeanType;
/**
 * 统计数据
 * @author itxuxxey
 *
 */
public class StatLogInfoResponse extends BaseResponseBean{
	private ButtonControlBean response;
	@Override
	public void setValues(Object object) {
		JSONObject jsonObject = (JSONObject) object;
		response = new ButtonControlBean();
		response.setCode(jsonObject.optString("code"));
		response.setMessage(jsonObject.optString("message"));
	}
	public ButtonControlBean getResponse() {
		return response;
	}
}
