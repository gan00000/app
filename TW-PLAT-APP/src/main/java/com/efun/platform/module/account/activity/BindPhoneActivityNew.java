package com.efun.platform.module.account.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.efun.core.tools.EfunStringUtil;
import com.efun.platform.AndroidScape.E_color;
import com.efun.platform.AndroidScape.E_drawable;
import com.efun.platform.AndroidScape.E_id;
import com.efun.platform.AndroidScape.E_layout;
import com.efun.platform.AndroidScape.E_string;
import com.efun.platform.IPlatApplication;
import com.efun.platform.http.dao.impl.IPlatformRequest;
import com.efun.platform.http.request.bean.BaseRequestBean;
import com.efun.platform.http.request.bean.BindPhoneByCallPhoneRequest;
import com.efun.platform.http.response.bean.BaseResponseBean;
import com.efun.platform.http.response.bean.BindPhoneByCallPhoneResponse;
import com.efun.platform.module.account.bean.BindParamsBean;
import com.efun.platform.module.account.bean.PhoneAreaBean;
import com.efun.platform.module.account.bean.User;
import com.efun.platform.module.base.FixedActivity;
import com.efun.platform.module.base.impl.OnUpdateSecondUserListener;
import com.efun.platform.module.base.impl.OnUpdateUserListener;
import com.efun.platform.module.person.bean.BindPhoneByCallPhoneBean;
import com.efun.platform.module.utils.IntentUtils;
import com.efun.platform.module.utils.ProcessDatasUtils;
import com.efun.platform.module.utils.ToastUtils;
import com.efun.platform.module.utils.TrackingGoogleUtils;
import com.efun.platform.module.utils.ViewUtils;
import com.efun.platform.module.utils.ViewUtils.OnDialogSelect;
import com.efun.platform.utils.Const;
import com.efun.platform.utils.Const.HttpParam;
import com.efun.platform.widget.TitleView;

/**
 * 绑定手机
 * 
 * @author harvery
 * 
 */
public class BindPhoneActivityNew extends FixedActivity {

	private TextView mPhoneArea,mNextButton;
	private LinearLayout mChoiceAreaLayout;
	private EditText mPhone;
	private String mPhoneNum;
	private User mUserInfoBean;
	private String[] mValues;
	private ArrayList<PhoneAreaBean> mPhoneAreas;
	private String key, pattern;// 區號和手機號碼判斷的正則表達式

	@Override
	public boolean needRequestData() {
		return false;
	}

	@Override
	public BaseRequestBean[] needRequestDataBean() {
		return null;
	}

	@Override
	public void init(Bundle bundle) {
		mUserInfoBean = (User) bundle.get(Const.BEAN_KEY);
		mPhone = (EditText) findViewById(E_id.edit_1);
		mChoiceAreaLayout = (LinearLayout) findViewById(E_id.phone_area);
		mPhoneArea = (TextView) findViewById(E_id.phone_area_content);
		mNextButton = (TextView) findViewById(E_id.bind_phone_next_button); 

		mPhoneAreas = IPlatApplication.get().getmPhoneAreas();
		mValues = ProcessDatasUtils.getAllPhoneAreaCode();
		if(mPhoneAreas != null && mValues != null){
			mPhoneArea.setText(mValues[0]);
			key = mPhoneAreas.get(0).getValue();
			pattern = mPhoneAreas.get(0).getPattern();
		}
		
		mChoiceAreaLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View paramView) {
				// TODO Auto-generated method stub
				if (mValues != null) {
					ViewUtils.paramListDialog(mValues, mContext, new OnDialogSelect() {

						@Override
						public void onSelect(int postion) {
							// TODO Auto-generated method stub
							TrackingGoogleUtils.track(TrackingGoogleUtils.ACTION_PHONE_AREA, mValues[postion]);
							mPhoneArea.setText(mValues[postion]);
							key = mPhoneAreas.get(postion).getValue();
							pattern = mPhoneAreas.get(postion).getPattern();
						}
					});
				}
			}
		});
		mNextButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (EfunStringUtil.isEmpty(key)) {
					ToastUtils.toast(mContext, E_string.efun_pd_toast_empty_phone_area);
					return;
				}
				mPhoneNum = mPhone.getText().toString();
				if (TextUtils.isEmpty(mPhoneNum)) {
					ToastUtils.toast(mContext,
							E_string.efun_pd_toast_empty_phone);
					return;
				}
				if (!mPhoneNum.matches(pattern)) {
					ToastUtils.toast(mContext, E_string.efun_pd_toast_error_phone_type);
					return;
				}
				
				requestWithDialog(getCallPhoneNumReq(), "檢測中...");
				
			}
		});

	}

	@Override
	public void onSuccess(int requestType, BaseResponseBean baseResponse) {
		super.onSuccess(requestType, baseResponse);
		if (requestType == IPlatformRequest.REQ_ACCOUNT_BIND_PHONE_BY_CALL_PHONE) {
			BindPhoneByCallPhoneResponse response = (BindPhoneByCallPhoneResponse)baseResponse;
			BindPhoneByCallPhoneBean bean = response.getResponse();
			if (bean.getCode().equals(HttpParam.RESULT_1000)) {
				//跳轉到綁定手機頁面（語音綁定）
				BindParamsBean ParamsBean = new BindParamsBean();
				ParamsBean.setKey(key);
				ParamsBean.setPattern(pattern);
				ParamsBean.setPhoneNum(mPhoneNum);
				ParamsBean.setCallNum(bean.getCall());
				ParamsBean.setReBindStatus(false);
				IntentUtils.goWithBeanForResult(mContext, BindPhoneTwoWayActivity.class, ParamsBean, new OnUpdateUserListener() {
					@Override
					public void onUpdate(User userInfo) {
						// TODO Auto-generated method stub
//						if (userInfo != null) {
//							mUserInfoBean.setAreaCode(key);
//							mUserInfoBean.setPhone(userInfo.getPhone());
//							mUserInfoBean.setIsAuthPhone("1");
//							((OnUpdateUserListener) IPlatApplication.get()
//									.getOnEfunListener()).onUpdate(mUserInfoBean);
//						mUserInfoBean = IPlatApplication.get().getUser();
//						((OnUpdateSecondUserListener) IPlatApplication.get()
//								.getOnEfunListener()).onUpdate(mUserInfoBean);
							finish();
//						}
					}
				});
				
			}else{
				ToastUtils.toast(mContext, bean.getMessage());
			}
		}
		
	}

	@Override
	public ViewGroup[] needShowLoading() {
		return null;
	}

	@Override
	public int LayoutId() {
		return E_layout.efun_pd_bind_phone_edit_phone;
	}


	@Override
	public void initTitle(TitleView titleView) {
		titleView.setTitleLeftRes(E_drawable.efun_pd_back_white_normal);
		titleView.setTitleRightStatus(View.GONE);
		titleView.setTitleCenterRes(E_string.efun_pd_bind_phone, false);
		titleView.setTitleBarBackgroundRes(E_color.e_50c1e9);
		titleView.setTitleCenterTextColor(getResources().getColor(E_color.e_ffffff));
	}
	
	private BaseRequestBean[] getCallPhoneNumReq(){
		BindPhoneByCallPhoneRequest request = new BindPhoneByCallPhoneRequest(
				HttpParam.LANGUAGE, 
				IPlatApplication.get().getUser().getSign(), 
				IPlatApplication.get().getUser().getTimestamp(), 
				key+"-"+mPhoneNum, 
				IPlatApplication.get().getUser().getUid(), 
				HttpParam.PLATFORM_CODE,
				"");
		request.setReqType(IPlatformRequest.REQ_ACCOUNT_BIND_PHONE_BY_CALL_PHONE);
		return new BaseRequestBean[]{request};
	}
	
}
