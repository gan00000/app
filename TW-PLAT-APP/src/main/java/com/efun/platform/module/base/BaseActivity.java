package com.efun.platform.module.base;

import java.util.Observable;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.efun.platform.AndroidScape.E_id;
import com.efun.platform.http.request.bean.BaseRequestBean;
import com.efun.platform.http.response.IPlatResponse;
import com.efun.platform.http.response.bean.BaseResponseBean;
import com.efun.platform.module.base.impl.OnRequestListener;
import com.efun.platform.module.base.impl.OnTitleButtonClickListener;
import com.efun.platform.utils.Const;
import com.efun.platform.utils.Const.HttpParam;
import com.efun.platform.widget.TitleView;

/**
 * 普通Activity
 * @author Jesse
 *
 */
public abstract class BaseActivity extends FragmentActivity implements OnRequestListener ,OnTitleButtonClickListener {
	/**
	 * 上一级页面传递过来的数据对象
	 */
	protected Bundle mBundle;
	
	protected Context mContext;
	
	protected View mParentView;
	
	
	@Override
	protected void onCreate(Bundle arg0) {
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		super.onCreate(arg0);
		mParentView = View.inflate(this, LayoutId(), null);
//		setContentView(LayoutId());
		setContentView(mParentView);
		mContext = this;
		initViews();
		//google分析
//		TrackingUtils.initService(this);
		if(getIntent()!=null){
			mBundle = getIntent().getBundleExtra(Const.DATA_KEY);
			init(mBundle);
		}
		init();
		if(findViewById(E_id.title)!=null){
			TitleView mTitleView = (TitleView) findViewById(E_id.title);
			mTitleView.setOnTitleButtonClickListener(this);
			initTitle(mTitleView);
		}
	}
	
	/**
	 * 监听者
	 * 
	 * @author Jesse
	 */
	public class ActivityStatusObserver extends BaseStatusObserver {
		@Override
		public void update(Observable observable, Object data) {
			try {
				BaseResponseBean bean = ((IPlatResponse) observable).getBaseResponseBean();
				removeCompletedResponse(bean);
			} catch (Exception e) {
			}
		}
	}

	/**
	 * 移除执行完毕的请求
	 * 
	 * @param response
	 */
	private void removeCompletedResponse(BaseResponseBean response) {
		int requestType =  response.getRequestBean().getReqType();
		if (response.getEfunCode()==HttpParam.SUCCESS) {
			if(response.isHasNoData()){
				onNoData(requestType,response.getNoDataNotify());
			}else{
				onSuccess(requestType,response);
			}
		} else if(response.getEfunCode()==HttpParam.ERROR){
			onFailure(requestType);
		} else if(response.getEfunCode()==HttpParam.TIMEOUNT){
			onTimeout(requestType);
		}
	}
	
	/**
	 * 界面布局ID
	 * 
	 * @return
	 */
	public abstract int LayoutId();

	public abstract void initViews();
	
	public abstract void initTitle(TitleView titleView);
	
	/**
	 * 是否需要请求数据来展现界面
	 * 
	 * @return true表示需要
	 */
	public abstract boolean needRequestData();

	/**
	 * @return {@link BaseRequestBean}
	 */
	public abstract BaseRequestBean[] needRequestDataBean();
	
	/**
	 * 初始化
	 */
	public abstract void init(Bundle bundle);
	
	public abstract void init();

	@Override
	public void onSuccess(int requestType,BaseResponseBean response) {
	}

	@Override
	public void onFailure(int requestType) {
	}
	@Override
	public void onTimeout(int requestType) {
		
	}
	
	@Override
	public void onNoData(int requestType, String noDataNotify) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onClickLeft() {
		finish();
	}

	@Override
	public void onClickRight() {
		// TODO Auto-generated method stub
	}
	@Override
	protected void onDestroy() {
//		TrackingUtils.destory();
		super.onDestroy();
	}
}
