package com.efun.platform.http;

import android.app.Dialog;
import android.content.Context;

import com.efun.core.tools.EfunLocalUtil;
import com.efun.platform.AndroidScape.E_string;
import com.efun.platform.http.request.IPlatRequest;
import com.efun.platform.http.response.IPlatResponse;
import com.efun.platform.http.task.IPlatCallBack;
import com.efun.platform.http.task.IPlatCmd;
import com.efun.platform.module.base.BaseStatusObserver;
import com.efun.platform.module.base.impl.OnRequestListener;
import com.efun.platform.module.utils.ToastUtils;
import com.efun.platform.utils.Const.HttpParam;

/**
 * 交互控制器 处理所有请求
 * 
 * @author Jesse
 * 
 */
public class IPlatController {
	private Context mContext;
	/**
	 * 请求类 {@link IPlatRequest}
	 */
	private IPlatRequest mRequest;
	/**
	 * 请求类数组 {@link IPlatRequest}
	 */
	private IPlatRequest[] mRequests;
	/**
	 * 响应类 {@link IPlatResponse}
	 */
	private IPlatResponse mResponse;
	/**
	 * 响应类数组 {@link IPlatResponse}
	 */
	private IPlatResponse[] mResponses;
	/**
	 * 监听者 {@link BaseStatusObserver}
	 */
	private BaseStatusObserver mObserver;

	public IPlatController(Context context) {
		this.mContext = context;
	}

	public IPlatController(Context context, BaseStatusObserver observer) {
		this.mContext = context;
		this.mObserver = observer;
	}

	public IPlatController(Context context, IPlatRequest mRequest, BaseStatusObserver observer) {
		this.mContext = context;
		this.mRequest = mRequest;
		this.mObserver = observer;
	}

	public IPlatController(Context context, IPlatRequest[] mRequests, BaseStatusObserver observer) {
		this.mContext = context;
		this.mRequests = mRequests;
		this.mObserver = observer;
	}

	/**
	 * 执行所有请求
	 */
	public void executeAll(final Dialog dialog,IPlatRequest[] requests,final OnRequestListener mListener) {
		if(dialog!=null){
			if(!EfunLocalUtil.isNetworkAvaiable(this.mContext)){
				ToastUtils.toast(mContext, mContext.getString(E_string.efun_pd_network_error));
				return;
			}else{
				dialog.show();
			}
		}
		if(requests!=null){
			mRequests = requests;
		}
		mResponses = new IPlatResponse[mRequests.length];
		for (int i = 0; i < mRequests.length; i++) {
			final int index = i;
			this.mRequests[i].execute(new IPlatCallBack() {
				@Override
				public void cmdCallBack(IPlatCmd command) {
					if(dialog!=null){
						requestCount++;
						if(requestCount==mRequests.length){
							requestCount = 0;
							dialog.dismiss();
						}
					}
					mResponses[index] = command.getResponse();
					if(mListener!=null){
						int code =mResponses[index].getBaseResponseBean().getEfunCode();
						int requestType = mRequests[index].getRequestBean().getReqType();
						if(code==HttpParam.SUCCESS){
							mListener.onSuccess(requestType, mResponses[index].getBaseResponseBean());
						}else if(code==HttpParam.ERROR){
							mListener.onFailure(requestType);
						}
					}else{
						// 设置响应初始状态码
						mResponses[index].setStatus(-1);
						if (mObserver != null) {
							mResponses[index].addObserver(mObserver);
						}
						// 设置 获取的请求类型（状态码），
						// 在这里，状态发生的变化，所以StatusObserver类会启用update方法。
						mResponses[index].setStatus(mRequests[index].getRequestBean().getReqType());
					}
				}
			});
		}
	}
	public void executeAll(){
		executeAll(null,null,null);
	}
	public void executeAll(Dialog dialog){
		executeAll(dialog,null,null);
	}
	public void executeAll(IPlatRequest[] requests){
		executeAll(null,requests,null);
	}
	public void executeAll(Dialog dialog,IPlatRequest[] requests){
		executeAll(dialog,requests,null);
	}
	private int requestCount;

	/**
	 * 执行单个请求
	 */
	public void execute() {
		this.mRequest.execute(new IPlatCallBack() {
			@Override
			public void cmdCallBack(IPlatCmd command) {
				mResponse = command.getResponse();
				// 设置响应初始状态码
				mResponse.setStatus(-1);
				if (mObserver != null) {
					mResponse.addObserver(mObserver);
				}
				// 设置 获取的请求类型（状态码）， 在这里，状态发生的变化，所以StatusObserver类会启用update方法。
				mResponse.setStatus(mRequest.getRequestBean().getReqType());
			}
		});
	}

	/**
	 * 获取请求类
	 * 
	 * @return {@link IPlatRequest}
	 */
	public IPlatRequest getRequest() {
		return mRequest;
	}

	/**
	 * 获取请求类数据
	 * 
	 * @return {@link IPlatRequest}
	 */
	public IPlatRequest[] getRequests() {
		return mRequests;
	}

	/**
	 * 获取响应类
	 * 
	 * @return {@link IPlatResponse}
	 */
	public IPlatResponse getResponse() {
		return mResponse;
	}

}
