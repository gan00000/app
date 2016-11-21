package com.efun.platform.module.base;

import java.util.HashMap;

import android.app.Dialog;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.efun.core.tools.EfunLocalUtil;
import com.efun.platform.AndroidScape.E_drawable;
import com.efun.platform.AndroidScape.E_id;
import com.efun.platform.AndroidScape.E_string;
import com.efun.platform.http.IPlatController;
import com.efun.platform.http.request.IPlatRequest;
import com.efun.platform.http.request.bean.BaseRequestBean;
import com.efun.platform.http.response.bean.BaseResponseBean;
import com.efun.platform.module.utils.ShareUtils;
import com.efun.platform.module.utils.ToastUtils;
import com.efun.platform.module.utils.ViewUtils;

/**
 * 弹性Activity容器(部分内容加载)
 * 
 * @author Jesse
 */
public abstract class FixedActivity extends BaseActivity{
	private HashMap<Integer, AsyncEntry> mAsyncEntryArray = new HashMap<Integer, AsyncEntry>();
	/**
	 * 请求是否有等待框框
	 */
	private boolean requestWithDialogFlag;
	/**
	 * View容器数组，用于存放各个模块的界面
	 */
	private ViewGroup[] needShowLoadingViews;
	/**
	 * 请求对象数组 {@link BaseRequestBean}
	 */
	private BaseRequestBean[] baseRequestBeans;
	
	private final int NOTIFY_TYPE_FAILURE = -1;
	private final int NOTIFY_TYPE_TIMEOUT = 0;
	private final int NOTIFY_TYPE_EMPTY = 1;
	
	@Override
	public void initViews() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void init() {
		if (needRequestData()) {
			baseRequestBeans = needRequestDataBean();
			if (baseRequestBeans != null) {
				needShowLoadingViews = needShowLoading();
				AsyncEntry mAsyncEntry = null;
				for (int i = 0; i < baseRequestBeans.length; i++) {
					if (needShowLoadingViews[i] != null) {
						final int index = i;
						ViewGroup view = needShowLoadingViews[i];
						View loading = ViewUtils.createLoading(mContext);
						mAsyncEntry = new AsyncEntry();
						mAsyncEntry.index = index;
						mAsyncEntry.dialog = loading;
						mAsyncEntry.parentView = view;
						mAsyncEntry.init();
						mAsyncEntryArray.put(baseRequestBeans[i].getReqType(), mAsyncEntry);
						view.addView(loading, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
					}
				}
				requestWithoutDialog();
			}
		}
	}
	
	class AsyncEntry {
		public int index;
		public View dialog;
		public View mEmptyView;
		public ViewGroup parentView;
		public boolean dissDialog;
		public ProgressBar mLoading;
		public ImageView mNotifyImg;
		public TextView mNotifyText;
		public void init(){
			mEmptyView = dialog.findViewById(E_id.empty);
			mLoading = (ProgressBar) dialog.findViewById(E_id.progress);
			mNotifyImg = (ImageView) dialog.findViewById(E_id.center_btn);
			mNotifyText = (TextView) dialog.findViewById(E_id.center_text);
			
			mNotifyImg.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (EfunLocalUtil.isNetworkAvaiable(mContext)) {
						showLoading(AsyncEntry.this);
						requestWithoutDialog(new BaseRequestBean[]{baseRequestBeans[index]});
					} 
				}
			});
		}
	}
	private void removeAsyncDialog(int requestType){
		AsyncEntry mAsyncEntry = mAsyncEntryArray.get(requestType);
		if(mAsyncEntry!=null){
			mAsyncEntry.parentView.removeView(mAsyncEntry.dialog);
			mAsyncEntry.dissDialog = true;
		}
	}
	private void notifyAsyncDialog(int notifyType,int requestType){
		notifyAsyncDialog(notifyType, requestType, null);
	}
	private void notifyAsyncDialog(int notifyType,int requestType,String message){
		AsyncEntry mAsyncEntry = mAsyncEntryArray.get(requestType);
		if(mAsyncEntry!=null && !mAsyncEntry.dissDialog){
			mAsyncEntry.mEmptyView.setVisibility(View.VISIBLE);
			showNotify(mAsyncEntry);
			if(notifyType == NOTIFY_TYPE_FAILURE){
				mAsyncEntry.mNotifyImg.setBackgroundResource(E_drawable.efun_pd_error_request);
				mAsyncEntry.mNotifyText.setText(getString(E_string.efun_pd_request_error));
			}else if(notifyType == NOTIFY_TYPE_TIMEOUT){
				mAsyncEntry.mNotifyImg.setBackgroundResource(E_drawable.efun_pd_error_timeout);
				mAsyncEntry.mNotifyText.setText(getString(E_string.efun_pd_timeout_error));
			}else if(notifyType== NOTIFY_TYPE_EMPTY){
				mAsyncEntry.mNotifyImg.setBackgroundResource(E_drawable.efun_pd_error_empty);
				mAsyncEntry.mNotifyImg.setEnabled(false);
				if(TextUtils.isEmpty(message)){
					mAsyncEntry.mNotifyText.setText(getString(E_string.efun_pd_empty_error));
				}else{
					mAsyncEntry.mNotifyText.setText(message);
				}
			}
		}else if(mAsyncEntry.dissDialog){
			if(notifyType == NOTIFY_TYPE_FAILURE){
				ToastUtils.toast(this, getString(E_string.efun_pd_request_error));
			}else{
				ToastUtils.toast(this, getString(E_string.efun_pd_timeout_error));
			}
		}
	}
	
	private void showLoading(AsyncEntry mAsyncEntry){
		mAsyncEntry.mLoading.setVisibility(View.VISIBLE);
		mAsyncEntry.mNotifyText.setVisibility(View.GONE);
		mAsyncEntry.mNotifyImg.setVisibility(View.GONE);
	}
	private void showNotify(AsyncEntry mAsyncEntry){
		mAsyncEntry.mLoading.setVisibility(View.GONE);
		mAsyncEntry.mNotifyText.setVisibility(View.VISIBLE);
		mAsyncEntry.mNotifyImg.setVisibility(View.VISIBLE);
	}
	
	@Override
	public void onSuccess(int requestType,BaseResponseBean response) {
		if(!requestWithDialogFlag){
			removeAsyncDialog(requestType);
		}
	}

	@Override
	public void onFailure(int requestType) {
		if(requestWithDialogFlag){
			ToastUtils.toast(this, getString(E_string.efun_pd_request_error));
		}else{
			notifyAsyncDialog(NOTIFY_TYPE_FAILURE,requestType);
		}
	}
	@Override
	public void onTimeout(int requestType) {
		if(requestWithDialogFlag){
			ToastUtils.toast(this, getString(E_string.efun_pd_timeout_error));
		}else{
			notifyAsyncDialog(NOTIFY_TYPE_TIMEOUT,requestType);
		}
	}
	
	@Override
	public void onNoData(final int requestType, String noDataNotify) {
		notifyAsyncDialog(NOTIFY_TYPE_EMPTY,requestType,noDataNotify);
		AsyncEntry mAsyncEntry = mAsyncEntryArray.get(requestType);
		if(mAsyncEntry!=null){
			mAsyncEntry.mNotifyText.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					onClickNotifyText(requestType);
				}
			});
		}
	}
	/**
	 * 有数据时，回复ListView
	 * @param requestType
	 */
	public void hasData(int requestType){
		removeAsyncDialog(requestType);
	}
	
	public void onClickNotifyText(int requestType){
		
	}
	
	/**
	 * 不带等待框请求数据
	 */
	public void requestWithoutDialog() {
		requestWithoutDialog(baseRequestBeans);
	}

	/**
	 * 不带等待框请求数据
	 * @param requests
	 */
	public void requestWithoutDialog(BaseRequestBean[] requests) {
		requestWithDialogFlag = false;
		IPlatController mIPlatController = new IPlatController(this,createIPlatRequests(requests), new ActivityStatusObserver());
		mIPlatController.executeAll();
	}
	
	private IPlatRequest[] createIPlatRequests(BaseRequestBean[] requests){
		IPlatRequest[] mRequests = new IPlatRequest[requests.length];
		for (int i = 0; i < mRequests.length; i++) {
			mRequests[i] = new IPlatRequest(this).setRequestBean(requests[i]);
		}
		return mRequests;
	}
	/**
	 * 带等待框请求数据
	 * @param msg 提示语言
	 */
	public void requestWithDialog(String msg) {
		requestWithDialog(baseRequestBeans, msg);
	}
	/**
	 * 带等待框请求数据
	 * @param requests
	 * @param msg 提示语言
	 */
	public void requestWithDialog(final BaseRequestBean[] requests, String msg) {
		requestWithDialogFlag = true;
		IPlatController mIPlatController = new IPlatController(this,new ActivityStatusObserver());
		Dialog dialog = ViewUtils.createLoadingDialog(this, msg);
		mIPlatController.executeAll(dialog,createIPlatRequests(requests));
	}

	/**
	 * View容器数组，用于存放需要请求的等待框控件
	 */
	public abstract ViewGroup[] needShowLoading();
	
	public boolean hasShare;
//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		// TODO Auto-generated method stub
//		super.onActivityResult(requestCode, resultCode, data);
//		if(hasShare){			
//			ShareUtils.shareGoogleJiaOnActivityResult(this, requestCode, resultCode, data);
//		}
//	}
	
}
