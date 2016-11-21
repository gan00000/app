package com.efun.platform.module.base;

import java.util.Observable;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.efun.platform.http.response.IPlatResponse;
import com.efun.platform.http.response.bean.BaseResponseBean;
import com.efun.platform.module.base.impl.OnRequestListener;
import com.efun.platform.utils.Const;
import com.efun.platform.utils.Const.HttpParam;

/**
 * 基础碎片
 * 
 * @author Jesse
 * 
 */
public abstract class BaseFragment extends Fragment implements OnRequestListener {
	/**
	 * 过滤{@link Fragment} 的
	 * {@link Fragment#onCreateView(LayoutInflater, ViewGroup, Bundle)} 多次创建
	 */
	private boolean isAgain;
	/**
	 * 上一级页面传递过来的数据对象
	 */
	protected Bundle mBundle;

	public BaseFragment() {
		super();
	}
	
	

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (childView == null) {
			if (getArguments() != null) {
				mBundle = getArguments().getBundle(Const.DATA_KEY);
			}
			childView = inflater.inflate(LayoutId(), container, false);
			isAgain = false;
		}
		ViewGroup parentView = (ViewGroup) childView.getParent();
		if (parentView != null) {
			parentView.removeView(childView);
			isAgain = true;
		}
		return childView;
	}

	protected View childView;

	/**
	 * 监听者
	 * 
	 * @author Jesse
	 */
	public class FragmentStatusObserver extends BaseStatusObserver {
		@Override
		public void update(Observable observable, Object data) {
			if (getActivity() == null) {
				return;
			}
			BaseResponseBean bean = ((IPlatResponse) observable).getBaseResponseBean();
			removeCompletedResponse(bean);
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

	/**
	 * 初始化子控件
	 */
	public abstract void initViews();

	/**
	 * 初始化显示数据
	 */
	public abstract void initDatas();
	/**
	 * 初始化事件
	 */
	public abstract void initListeners();
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if (!isAgain) {
			initViews();
			initDatas();
			initListeners();
		}
	}
}
