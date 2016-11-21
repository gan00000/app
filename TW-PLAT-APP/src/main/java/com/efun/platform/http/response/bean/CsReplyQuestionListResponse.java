package com.efun.platform.http.response.bean;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.efun.platform.module.bean.PageInfoBean;
import com.efun.platform.module.cs.bean.CsReplyQuestionBean;
import com.efun.platform.module.cs.bean.CsReplyQuestionListBean;
/**
 * 客服 - 客服回复- 问题列表
 * @author Jesse
 *
 */
public class CsReplyQuestionListResponse extends BaseResponseBean {
	/**
	 * 问题列表 {@link CsReplyQuestionListBean}
	 */
	private ArrayList<CsReplyQuestionBean> cReplayQuestionBeans = new ArrayList<CsReplyQuestionBean>();
	
	@Override
	public void setValues(Object object) {
		JSONObject jsonObject = (JSONObject) object;
		JSONArray jsonArray = jsonObject.optJSONArray("result");
		createPageInfo(jsonObject);
		for (int i = 0; i < jsonArray.length(); i++) {
			CsReplyQuestionBean bean = new CsReplyQuestionBean();
			jsonObject = jsonArray.optJSONObject(i);
			bean.setTgppid(jsonObject.optString("tgppid"));
			bean.setGameName(jsonObject.optString("gameName"));
			bean.setQuestionTitle(jsonObject.optString("questionsTitle"));
			bean.setHasNew(jsonObject.optString("hasNew"));
			bean.setCreateTime(jsonObject.optString("createTime"));
			bean.setReplyStatus(jsonObject.optString("replyStatus"));
			bean.setScore(jsonObject.optString("score"));
			bean.setLastModifiedTime(jsonObject.optString("lastModifiedTime"));
			bean.setTheQuestions(jsonObject.optString("theQuestions"));
			cReplayQuestionBeans.add(bean);
		}
	}
	public ArrayList<CsReplyQuestionBean> getcReplayQuestionBeans() {
		return cReplayQuestionBeans;
	}
}
