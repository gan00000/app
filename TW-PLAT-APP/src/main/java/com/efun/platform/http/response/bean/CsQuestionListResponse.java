package com.efun.platform.http.response.bean;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.efun.platform.module.cs.bean.CsQuestionItemBean;

/**
 * 客服-常见问题-问题列表
 * @author lgh
 * 
 */
public class CsQuestionListResponse extends BaseResponseBean {
	private ArrayList<CsQuestionItemBean> response;
	@Override
	public void setValues(Object object) {
		JSONObject jObject = (JSONObject) object;
		JSONArray jsonArray =jObject.optJSONArray("result");
		createPageInfo(jObject);
		response = new ArrayList<CsQuestionItemBean>();
		CsQuestionItemBean bean = null;
		JSONObject jsonObject = null;
		for (int i = 0; i < jsonArray.length(); i++) {
			jsonObject = jsonArray.optJSONObject(i);
			bean = new CsQuestionItemBean();
			bean.setQuestionsTitle(jsonObject.optString("questionsTitle"));
			bean.setTheQuestions(jsonObject.optString("theQuestions"));
			bean.setCreateTime(jsonObject.optString("createTime"));
			bean.setType(jsonObject.optString("type"));
			response.add(bean);
		}
	}
	public ArrayList<CsQuestionItemBean> getCsQuestionList() {
		return response;
	}
}
