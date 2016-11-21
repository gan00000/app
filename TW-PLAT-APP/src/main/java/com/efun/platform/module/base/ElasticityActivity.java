package com.efun.platform.module.base;

import android.app.Dialog;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.efun.core.tools.EfunLocalUtil;
import com.efun.platform.AndroidScape.E_drawable;
import com.efun.platform.AndroidScape.E_id;
import com.efun.platform.AndroidScape.E_layout;
import com.efun.platform.AndroidScape.E_string;
import com.efun.platform.http.IPlatController;
import com.efun.platform.http.request.IPlatRequest;
import com.efun.platform.http.request.bean.BaseRequestBean;
import com.efun.platform.http.response.bean.BaseResponseBean;
import com.efun.platform.module.base.impl.OnTitleButtonClickListener;
import com.efun.platform.module.utils.ShareUtils;
import com.efun.platform.module.utils.ToastUtils;
import com.efun.platform.module.utils.ViewUtils;
import com.efun.platform.widget.list.XListView;

/**
 * 弹性Activity 继承 {@link FragmentActivity} 并且实现
 * {@link OnTitleButtonClickListener} 接口
 * 
 * @author Jesse
 * 
 */
public abstract class ElasticityActivity extends BaseActivity{
	/**
	 * 弹性LixtView：{@link XListView}
	 */
	public XListView mListView;
	/**
	 * 界面数组
	 */
	public View[] mViews;
	/**
	 * 当ListView没有数据的时候显示的内容 ，参考{@link ListView#setEmptyView(View)}
	 */
	private View emptyView;
	/**
	 * 等待框 {@link ProgressBar}
	 */
	private ProgressBar mLoading;
	/**
	 * 网络异常时显示的图片
	 */
	private ImageView mNotifyImg;
	/**
	 * 网络异常时显示的文本内容
	 */
	public TextView mNotifyText;

	/**
	 * 请求是否有等待框框
	 */
	private boolean requestWithDialogFlag;
	
	private BaseRequestBean[] baseRequestBeans;
	/**
	 * 界面布局ID
	 * 
	 * @return
	 */
	@Override
	public int LayoutId(){
		return E_layout.efun_pd_elasticity_activity;
	}

	@Override
	public void initViews() {
		mListView = (XListView)findViewById(E_id.list);
		emptyView = findViewById(E_id.empty);
		mLoading = (ProgressBar) emptyView.findViewById(E_id.progress);
		mNotifyImg = (ImageView) emptyView.findViewById(E_id.center_btn);
		mNotifyText = (TextView) emptyView.findViewById(E_id.center_text);
		mViews = addHeaderViews();
	}
	
	@Override
	public void init() {
		baseRequestBeans = needRequestDataBean();
		addViews();
		if (needRequestData()) {
			emptyView.setVisibility(View.VISIBLE);
			mNotifyImg.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (EfunLocalUtil.isNetworkAvaiable(mContext)) {
						showLoading();
						requestWithoutDialog();
					} 
				}
			});
			loading();
		} else {
			emptyView.setVisibility(View.GONE);
		}
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
	private void showLoading(){
		mLoading.setVisibility(View.VISIBLE);
		mNotifyText.setVisibility(View.GONE);
		mNotifyImg.setVisibility(View.GONE);
	}
	private void showNotify(){
		mLoading.setVisibility(View.GONE);
		mNotifyText.setVisibility(View.VISIBLE);
		mNotifyImg.setVisibility(View.VISIBLE);
	}
	/**
	 * 判断网络,并模拟等待界面
	 */
	private void loading() {
		if (EfunLocalUtil.isNetworkAvaiable(this)) {
			showLoading();
			requestWithoutDialog();
		} else {
			showNotify();
		}
	}
	
	private void addViews() {
		if (mViews != null) {
			for (int i = 0; i < mViews.length; i++) {
				mListView.addHeaderView(mViews[i]);
			}
		}
		mListView.setAdapter(adapter());
	}
	@Override
	public void onSuccess(int requestType,BaseResponseBean response) {
		if(!requestWithDialogFlag){
			emptyView.setVisibility(View.GONE);
		}
		resetListView();
	}

	private void resetListView(){
		mListView.stopRefresh();
		mListView.stopLoadMore();
	} 
	@Override
	public void onFailure(int requestType) {
		if(requestWithDialogFlag){
			ToastUtils.toast(this, getString(E_string.efun_pd_request_error));
		}else{
			showNotify();
			mNotifyImg.setBackgroundResource(E_drawable.efun_pd_error_request);
			mNotifyText.setText(getString(E_string.efun_pd_request_error));
		}
		resetListView();
	}
	@Override
	public void onTimeout(int requestType) {
		if(requestWithDialogFlag){
			ToastUtils.toast(this, getString(E_string.efun_pd_timeout_error));
		}else{
			showNotify();
			mNotifyImg.setBackgroundResource(E_drawable.efun_pd_error_timeout);
			mNotifyText.setText(getString(E_string.efun_pd_timeout_error));
		}
		resetListView();
	}
	
	@Override
	public void onNoData(int requestType, String noDataNotify) {
		showNotify();
		mNotifyImg.setBackgroundResource(E_drawable.efun_pd_error_empty);
		mNotifyImg.setEnabled(false);
		if(TextUtils.isEmpty(noDataNotify)){
			mNotifyText.setText(getString(E_string.efun_pd_empty_error));
		}else{
			mNotifyText.setText(noDataNotify);
		}
		resetListView();
	}
	
//	/**
//	 * 数据位空是掉用
//	 * @param drawable
//	 */
//	public void setEmpty(){
//		emptyView.setVisibility(View.VISIBLE);
//		mLoading.setVisibility(View.GONE);
//		mNotifyImg.setVisibility(View.VISIBLE);
//		mNotifyText.setVisibility(View.GONE);
//		mListView.setEmptyView(emptyView);
//		mNotifyImg.setBackgroundResource(E_drawable.efun_pd_error_empty);
//		mNotifyImg.setEnabled(false);
//	}
	
	/**
	 * 组成UI的零件，零件类型为View
	 * @return
	 */
	public abstract View[] addHeaderViews();
	
	/**
	 * 组成UI的Adapter
	 * @return
	 */
	public abstract BaseAdapter adapter();

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
