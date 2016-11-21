package com.efun.platform.http.response.bean;

import java.io.Serializable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.text.TextUtils;

import com.efun.core.tools.EfunLocalUtil;
import com.efun.core.tools.EfunLogUtil;
import com.efun.platform.AndroidScape.E_string;
import com.efun.platform.http.dao.impl.IPlatformRequest;
import com.efun.platform.http.request.bean.BaseRequestBean;
import com.efun.platform.module.bean.PageInfoBean;
import com.efun.platform.utils.Const.HttpParam;
/**
 * 响应类
 * @author Jesse
 *
 */
public abstract class BaseResponseBean implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 上下文
	 */
	private Context context;
	/**
	 * 请求码</b>
	 * 成功{@value HttpParam#SUCCESS}};</b>
	 * 失败{@value HttpParam#ERROR}};</b>
	 * 超时{@value HttpParam#TIMEOUNT}};</b>
	 */
	private int efunCode;
	/**
	 * 页码
	 */
	private PageInfoBean pageInfoBean;
	
	private boolean hasNoData;
	private String noDataNotify;
	/**
	 * 请求响应值
	 */
	private String responseJson2String;
	/**
	 * 请求类
	 */
	private BaseRequestBean requestBean;
	
	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public BaseRequestBean getRequestBean() {
		return requestBean;
	}

	public void setRequestBean(BaseRequestBean requestBean) {
		this.requestBean = requestBean;
	}

	public int getEfunCode() {
		return efunCode;
	}

	public void setEfunCode(int efunCode) {
		this.efunCode = efunCode;
	}

	public String getResponseJson2String() {
		return responseJson2String;
	}

	public void setResponseJson2String(String responseJson2String) {
		this.responseJson2String = responseJson2String;
	}

	public boolean isHasNoData() {
		return hasNoData;
	}
	
	public void setHasNoData(boolean hasNoData) {
		this.hasNoData = hasNoData;
	}

	public String getNoDataNotify() {
		return noDataNotify;
	}
	
	public void setNoDataNotify(String noDataNotify) {
		this.noDataNotify = noDataNotify;
	}

	public PageInfoBean getPageInfoBean() {
		return pageInfoBean;
	}


	/**
	 * 解析列表数据页码
	 * @param jsonObject Http返回的数据
	 * @return {@link PageInfoBean}
	 */
	public PageInfoBean createPageInfo(JSONObject jsonObject){
		pageInfoBean = new PageInfoBean();
		pageInfoBean.setPageIndex(jsonObject.optInt("pageIndex"));
		pageInfoBean.setPageSize(jsonObject.optInt("pageSize"));
		pageInfoBean.setTotal(jsonObject.optInt("total"));
		pageInfoBean.setTotalPage(jsonObject.optInt("totalPage"));
		hasNoData = !pageInfoBean.isHasData();
		if(hasNoData){
			switch (requestBean.getReqType()) {
			case IPlatformRequest.REQ_ACT_LIST:
				this.noDataNotify =context.getString(E_string.efun_pd_no_data_no_activity);
				break;
			case IPlatformRequest.REQ_GIFT_SELF_LIST:
				this.noDataNotify =context.getString(E_string.efun_pd_no_data_no_giftself);
				break;
			case IPlatformRequest.REQ_CS_REPLAY_QUESTION_LIST:
				this.noDataNotify =context.getString(E_string.efun_pd_no_data_no_server_reply);
				break;
			}
		}
		EfunLogUtil.logD(pageInfoBean.toString());
		return pageInfoBean;
	}
	/**
	 * 解析Http数据
	 * @param request 请求参数类 {@link BaseRequestBean}
	 * @param response 响应结果 
	 * @param isArray 响应数据是否是{@link JSONArray}
	 * @return 响应类 {@link BaseResponseBean}
	 */
	public BaseResponseBean pares(BaseRequestBean request,String response,boolean isArray){
		EfunLogUtil.logD(response+"");
		setRequestBean(request);
		if(TextUtils.isEmpty(response)){
			if(EfunLocalUtil.isNetworkAvaiable(getContext())){
				setEfunCode(HttpParam.TIMEOUNT);
			}else{
				setEfunCode(HttpParam.ERROR);
			}
		}else{
			try {
				if(isArray){
					JSONArray jsonArray = new JSONArray(response);
					setValues(jsonArray);
				}else{
					JSONObject jsonObject = new JSONObject(response);
					setResponseJson2String(response);
					setContext(context);
					setValues(jsonObject);
				}
				setEfunCode(HttpParam.SUCCESS);
			} catch (Exception e) {
				e.printStackTrace();
				setEfunCode(HttpParam.ERROR);
				return this;
			}
		}
		return this;
	}
	/**
	 * 解析数据过程
	 * @param object 构造好的 {@link JSONObject} 或者 {@link JSONArray}
	 */
	public abstract void setValues(Object object);

}
