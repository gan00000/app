package com.efun.platform.http.request;

import android.content.Context;

import com.efun.platform.http.request.bean.BaseRequestBean;
import com.efun.platform.http.task.IPlatCallBack;
import com.efun.platform.http.task.IPlatCmdExecute;
import com.efun.platform.http.task.IPlatCmdImpl;

/**
 * 请求封装类，封装了接口处理
 * 
 * @author Jesse
 * 
 */
public class IPlatRequest {
	
	/**
	 * 上下文 {@link Context}
	 */
	public Context mContext;
	/**
	 * 文本内容 {@link BaseRequestBean}
	 */
	private BaseRequestBean mReqBean;

	public IPlatRequest(Context mContext) {
		this.mContext = mContext;
	}

	public Context getContext() {
		return mContext;
	}

	/**
	 * 设置请求对象
	 * 
	 * @param reqBean
	 *            {@link BaseRequestBean}
	 * @return {@link IPlatRequest}
	 */
	public IPlatRequest setRequestBean(BaseRequestBean reqBean) {
		mReqBean = reqBean;
		return this;
	}

	/**
	 * 获取请求对象
	 * 
	 * @return {@link BaseRequestBean}
	 */
	public BaseRequestBean getRequestBean() {
		return mReqBean;
	}

	/**
	 * 执行回调
	 * 
	 * @param callback
	 *            {@link IPlatCallBack}
	 */
	public void execute(IPlatCallBack callback) {
		IPlatCmdImpl mCmdImpl = new IPlatCmdImpl(this);
		mCmdImpl.setCallback(callback);
		IPlatCmdExecute.getInstance().asynExecute(mCmdImpl);
	}
}
