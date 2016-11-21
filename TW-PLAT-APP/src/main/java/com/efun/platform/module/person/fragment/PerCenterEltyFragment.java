package com.efun.platform.module.person.fragment;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.efun.core.tools.EfunLocalUtil;
import com.efun.core.tools.EfunLogUtil;
import com.efun.core.tools.EfunResourceUtil;
import com.efun.core.tools.EfunStringUtil;
import com.efun.platform.AndroidScape.E_array;
import com.efun.platform.AndroidScape.E_color;
import com.efun.platform.AndroidScape.E_dimens;
import com.efun.platform.AndroidScape.E_drawable;
import com.efun.platform.AndroidScape.E_id;
import com.efun.platform.AndroidScape.E_layout;
import com.efun.platform.AndroidScape.E_string;
import com.efun.platform.IPlatApplication;
import com.efun.platform.http.dao.impl.IPlatformRequest;
import com.efun.platform.http.request.bean.BaseRequestBean;
import com.efun.platform.http.request.bean.CheckNewSysEmailRequest;
import com.efun.platform.http.request.bean.GameListOfModuleRequest;
import com.efun.platform.http.request.bean.GiftSelfStatusRequest;
import com.efun.platform.http.request.bean.NewGiftStatusRequest;
import com.efun.platform.http.request.bean.PersonGetSignInGiftRequest;
import com.efun.platform.http.request.bean.PersonPlaformPointRequest;
import com.efun.platform.http.request.bean.PersonRefreshHeadIconRequest;
import com.efun.platform.http.request.bean.PersonSignRequest;
import com.efun.platform.http.request.bean.RechargeControlRequest;
import com.efun.platform.http.request.bean.UserUpdateHeaderRequest;
import com.efun.platform.http.response.bean.BaseResponseBean;
import com.efun.platform.http.response.bean.CheckSysEmailResponse;
import com.efun.platform.http.response.bean.GameListOfModuleResponse;
import com.efun.platform.http.response.bean.GiftSelfStatusResponse;
import com.efun.platform.http.response.bean.NewGiftStatusResponse;
import com.efun.platform.http.response.bean.PersonGetSignInGiftResponse;
import com.efun.platform.http.response.bean.PersonPlatformPointResponse;
import com.efun.platform.http.response.bean.PersonRefreshHeadIconResponse;
import com.efun.platform.http.response.bean.PersonSignResponse;
import com.efun.platform.http.response.bean.RechargeControlResponse;
import com.efun.platform.http.response.bean.UserUpdateHeaderResponse;
import com.efun.platform.image.ImageManager;
import com.efun.platform.image.core.DisplayImageOptions;
import com.efun.platform.module.PopWindow;
import com.efun.platform.module.account.activity.BindPhoneActivityNew;
import com.efun.platform.module.account.bean.ResultBean;
import com.efun.platform.module.account.bean.User;
import com.efun.platform.module.base.FixedFragment;
import com.efun.platform.module.base.impl.OnEfunItemClickListener;
import com.efun.platform.module.bean.ButtonControlBean;
import com.efun.platform.module.bean.ConfigInfoBean;
import com.efun.platform.module.person.activity.ResetPerInfoActivity;
import com.efun.platform.module.person.bean.GiftGameBean;
import com.efun.platform.module.person.bean.GiftGameResultBean;
import com.efun.platform.module.person.bean.NewSysEmailBean;
import com.efun.platform.module.person.bean.PlatformPointBean;
import com.efun.platform.module.person.bean.UpLoadHeaderImgBean;
import com.efun.platform.module.settting.activity.SettingActivity;
import com.efun.platform.module.utils.AppUtils;
import com.efun.platform.module.utils.IntentUtils;
import com.efun.platform.module.utils.PopUtils;
import com.efun.platform.module.utils.ProcessDatasUtils;
import com.efun.platform.module.utils.ToastUtils;
import com.efun.platform.module.utils.TrackingGoogleUtils;
import com.efun.platform.module.utils.TrackingUtils;
import com.efun.platform.module.utils.ViewUtils;
import com.efun.platform.module.utils.ViewUtils.OnDialogSelect;
import com.efun.platform.module.welfare.activity.GiftListActivity;
import com.efun.platform.module.welfare.activity.GiftSelfActivity;
import com.efun.platform.module.welfare.bean.GiftSelfStatusBean;
import com.efun.platform.module.welfare.bean.NewGiftStatusBean;
import com.efun.platform.utils.Const.HttpParam;
import com.efun.platform.utils.Const.LoginPlatform;
import com.efun.platform.utils.Const.RequestCode;
import com.efun.platform.utils.Const.ToastType;
import com.efun.platform.utils.Const.Web;
import com.efun.platform.utils.StoreUtil;
import com.efun.platform.widget.camera.CameraActivity;
import com.efun.platform.widget.scroll.PullScrollView;
//import com.google.android.gms.internal.ex;

/**
 * 个人中心页面
 * 
 * @author harvery 修改于20160104
 * 
 */
public class PerCenterEltyFragment extends FixedFragment implements
		PullScrollView.OnTurnListener {
	/**
	 * 信件系统，设置,头像，更改头像,vip标识
	 */
	private ImageView emailSys, setingBtn, headIcon, changeHeadIcon, vipIcon;
	/**
	 * 更新个人资料,妮称,帐号,用户id,绑定手机文案,签到按钮,签到天数
	 */
	private TextView changePersonInfo, nickName, accountName, userId,
			bindPhoneDec,signInBtn,signInDays;
	/**
	 * 经验等级称号,积分,平台点,储值按钮，储值记录
	 */
	private TextView experName, integral, pfPoint, chargeBtn, chargeRechordBtn;
	/**
	 * 经验等级图标
	 */
	private ImageView experIcon;
	/**
	 * 绑定手机,会员说明
	 */
	private LinearLayout mBindPhoneLayout, mMemberDecLayout;

	/**
	 * 儲值UI布局,拓展功能模块
	 */
	private LinearLayout personChargeLayout, personFunctionLayout;
	
	private TextView mSysEmailRedPoint;
	
	/**
	 * 用户
	 */
	private User mUser;

	private boolean loadFirstFlag = false;// 第一次加載頁面的標識

	private PopWindow mPopHeader;

	private Bitmap newHeaderBitmap;
	private String encodeFile;
	private String fileName;

	private ArrayList<ConfigInfoBean> functionBeans;// 功能区所有功能模块数据
	private ConfigInfoBean mRechargeBean,mRechargeRecodeBean;//储值相关数据，储值记录相关数据
	private ConfigInfoBean mEmailSysBean;
	private ArrayList<ImageView> mRedPointIvs;// 所有红点的图标列表
	private int giftIndex,giftSerialIndex;
//	private View vipLayout,rightLineLayout,emptyLayoutLayout,unVipLayout,unVipRightLineLayout,unVipEmptyLayout;
	private LayoutInflater inflater = null;
	
	private DisplayImageOptions options;
	
	private ArrayList<GiftGameBean> mGiftGames;//可领奖的游戏数据列表
	private String[] mGiftGamesStr;//可领奖的游戏名称列表
	//选择的领取签到奖励的游戏
	private String awardGameCode;
	
	@Override
	public void initViews() {
		loadFirstFlag = true;
		childView.setVisibility(View.INVISIBLE);
		options = new DisplayImageOptions.Builder()
			        .showImageForEmptyUri(EfunResourceUtil.findDrawableIdByName(getContext(), "efun_pd_default_square_icon"))
			        .showImageOnFail(EfunResourceUtil.findDrawableIdByName(getContext(), "efun_pd_default_square_icon"))
			        .showImageOnLoading(EfunResourceUtil.findDrawableIdByName(getContext(), "efun_pd_default_square_icon"))
			        .cacheInMemory(true)
			        .cacheOnDisk(true).build();
		
		emailSys = (ImageView) childView.findViewById(E_id.left_btn);
		setingBtn = (ImageView) childView.findViewById(E_id.right_btn);
		headIcon = (ImageView) childView.findViewById(E_id.head_icon);
		changeHeadIcon = (ImageView) childView
				.findViewById(E_id.head_change_icon);
		vipIcon = (ImageView) childView.findViewById(E_id.person_vip_icon);
		experIcon = (ImageView) childView.findViewById(E_id.person_exper_icon);
		changePersonInfo = (TextView) childView.findViewById(E_id.textView1);
		nickName = (TextView) childView.findViewById(E_id.person_nickName);
		accountName = (TextView) childView
				.findViewById(E_id.person_accountName);
		userId = (TextView) childView.findViewById(E_id.person_accountId);
		bindPhoneDec = (TextView) childView
				.findViewById(E_id.person_bind_phone_dec);
		experName = (TextView) childView.findViewById(E_id.person_exper_name);
		integral = (TextView) childView.findViewById(E_id.item_content);
		pfPoint = (TextView) childView.findViewById(E_id.item_point);
		chargeBtn = (TextView) childView.findViewById(E_id.person_recharge);
		mSysEmailRedPoint = (TextView) childView.findViewById(E_id.sys_email_red_point);
		chargeRechordBtn = (TextView) childView
				.findViewById(E_id.person_recharge_record);
		mBindPhoneLayout = (LinearLayout) childView
				.findViewById(E_id.person_bind_phone);
		mMemberDecLayout = (LinearLayout) childView
				.findViewById(E_id.person_exper_member_dec);
		personChargeLayout = (LinearLayout) childView
				.findViewById(E_id.person_recharge_body);
		personFunctionLayout = (LinearLayout) childView
				.findViewById(E_id.person_body_tuozhan);
		signInBtn = (TextView) childView.findViewById(E_id.person_signin_btn);
		signInDays = (TextView) childView.findViewById(E_id.person_signin_days);
		inflater = LayoutInflater.from(getActivity());
//		functionBeans = removeVIPorNot(ProcessDatasUtils.getPersonFunctions());
		mRechargeBean = ProcessDatasUtils.getPersonTextInfo(ProcessDatasUtils.TYPE_CHARGE_AREA, ProcessDatasUtils.TYPE_CHARGE);
		mRechargeRecodeBean = ProcessDatasUtils.getPersonTextInfo(ProcessDatasUtils.TYPE_CHARGE_AREA, ProcessDatasUtils.TYPE_CHARGE_RECORD);
		mEmailSysBean = ProcessDatasUtils.getPersonTextInfo(ProcessDatasUtils.TYPE_LINK_AREA, ProcessDatasUtils.TYPE_LINK_EMAIL_SYS);
		giftIndex = ProcessDatasUtils.getGiftFunctionIndex();
		giftSerialIndex = ProcessDatasUtils.getGiftSerialFunctionIndex();
		if(ProcessDatasUtils.getPersonTextInfo(ProcessDatasUtils.TYPE_BIND_PHONE_AREA, ProcessDatasUtils.TYPE_PHNOE_DESC) != null){
			bindPhoneDec.setText(ProcessDatasUtils.getPersonTextInfo(ProcessDatasUtils.TYPE_BIND_PHONE_AREA, ProcessDatasUtils.TYPE_PHNOE_DESC).getSubName());
		}
		initListeners();
	}

	@Override
	public void onResume() {
		Log.e("efun", "percenter:onResume");
		super.onResume();
		displayUserInfo();
	}

	@Override
	public int LayoutId() {
		return E_layout.efun_pd_persion_new;
	}

	@Override
	public void onTurn() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSuccess(int requestType, BaseResponseBean baseResponse) {
		// TODO Auto-generated method stub
		if (requestType == IPlatformRequest.REQ_GIFT_SELF_STATUS) {
			GiftSelfStatusResponse response = (GiftSelfStatusResponse) baseResponse;
			GiftSelfStatusBean mGiftSelfStatusBean = response
					.getGiftSelfStatusBean();
			if (mGiftSelfStatusBean.getCode().equals(HttpParam.RESULT_1000)) {
				IPlatApplication.get().setNewStatusOfGiftSelf(true);
				if(mRedPointIvs != null){
					mRedPointIvs.get(giftSerialIndex).setVisibility(View.VISIBLE);
				}
			} else {
				IPlatApplication.get().setNewStatusOfGiftSelf(false);
				if(mRedPointIvs != null){
					mRedPointIvs.get(giftSerialIndex).setVisibility(View.GONE);
				}
			}
		}

		if (requestType == IPlatformRequest.REQ_NEW_GIFT_STATUS) {
			NewGiftStatusResponse response = (NewGiftStatusResponse) baseResponse;
			NewGiftStatusBean mNewGiftStatusBean = response
					.getNewGiftStatusBean();
			if (mNewGiftStatusBean.getCode().equals(HttpParam.RESULT_1000)) {
				String oldTimeStr = StoreUtil.getValue(getActivity(),
						StoreUtil.Param_file_name, IPlatApplication.get()
								.getUser().getUid());
				if (!EfunStringUtil.isEmpty(oldTimeStr)) {
					long oldTime = Long.parseLong(oldTimeStr);
					if (oldTime < mNewGiftStatusBean.getResult()) {
						IPlatApplication.get().setNewGiftStatus(true);
						if(mRedPointIvs != null){
							mRedPointIvs.get(giftIndex).setVisibility(View.VISIBLE);
						}
					}

				}
			}
		}

		if (requestType == IPlatformRequest.REQ_USER_PLATFORM_POINT) {
			PersonPlatformPointResponse response = (PersonPlatformPointResponse) baseResponse;
			PlatformPointBean mPointBean = response.getPlatformPointBean();
			if (mPointBean.getCode().equals(HttpParam.RESULT_0000)) {
				IPlatApplication.get().setSumPoint(mPointBean.getSumPoint());
				pfPoint.setText(mPointBean.getSumPoint());
			}
		}

		if (requestType == IPlatformRequest.REQ_CHARGE_CONTROL) {
			Log.i("efun", "requestType");
			RechargeControlResponse response = (RechargeControlResponse) baseResponse;
			ButtonControlBean bean = response.getButtonControlBean();
			Log.i("efun", "bean" + bean.toString());
			if (bean.getCode().equals("1")) {
				Log.i("efun", "bean.getCode().equals");
				personChargeLayout.setVisibility(View.VISIBLE);
			} else {
				personChargeLayout.setVisibility(View.GONE);
			}
		}

		if (requestType == IPlatformRequest.REQ_USER_UPDATE_HEADER) {
			UserUpdateHeaderResponse response = (UserUpdateHeaderResponse) baseResponse;
			UpLoadHeaderImgBean bean = response.getUpLoadHeaderImgBean();
			EfunLogUtil.logI("efun", "upLoadImg:" + bean.getMessage());
			if (bean.getCode().equals(HttpParam.RESULT_1000)) {
				mUser.setIcon(bean.getImgUrl());
//				ImageManager.displayImage(mUser.getIcon(), headIcon,
//						ImageManager.IMAGE_ROUND_USER,
//						E_dimens.e_corners_radius_150);
				IPlatApplication.get().setUser(mUser);
				requestWithDialog(new BaseRequestBean[]{createRefreshPlayerHeaderIcon()}, getString(E_string.efun_pd_loading_msg_commend));
			}
		}
		
		if(requestType == IPlatformRequest.REQ_USER_UPDATE_HEADER_NEW){
			PersonRefreshHeadIconResponse response = (PersonRefreshHeadIconResponse) baseResponse;
			ResultBean bean = response.getResultBean();
			if(bean.getCode().equals(HttpParam.RESULT_1000)){
				ImageManager.displayImage(mUser.getIcon(), headIcon,
						ImageManager.IMAGE_ROUND_USER,
						E_dimens.e_corners_radius_150);
			}
			ToastUtils.toast(getActivity(), bean.getMessage());
		}
		
		if(requestType == IPlatformRequest.REQ_USER_CHECK_NEW_SYS_EMAIL){
			CheckSysEmailResponse response = (CheckSysEmailResponse) baseResponse;
			NewSysEmailBean bean = response.getResponse();
			if(bean.getCode().equals(HttpParam.RESULT_1000)){
				mSysEmailRedPoint.setVisibility(View.VISIBLE);
				if(bean.getResult() <= 0){
					mSysEmailRedPoint.setVisibility(View.GONE);
				}else{
					mSysEmailRedPoint.setText(bean.getResult()+"");
				}
			}else{
				mSysEmailRedPoint.setVisibility(View.GONE);
			}
		}
		
		if(requestType == IPlatformRequest.REQ_PERSON_SIGN){
			PersonSignResponse response = (PersonSignResponse) baseResponse;
			Toast.makeText(getContext(), ""+response.getResponse().getMessage(), Toast.LENGTH_SHORT).show();
			if(response.getResponse().getCode().equals(HttpParam.RESULT_1000)){
				if(response.getResponse().isTodayHasSigninGift()){//有礼包奖励
					signInBtn.setBackgroundResource(E_drawable.efun_pd_ff4848_rectangle_bg);
					signInBtn.setText(EfunResourceUtil.findStringByName(getContext(), "efun_pd_get_reward"));
					signInDays.setText(response.getResponse().getSigninDays()+"");
					integral.setText((response.getResponse().getGoldValue())+"");
					//刷新數據
					mUser.setSignin(true);
					mUser.setTodayHasSigninGift(true);
					mUser.setGetSigninGift(false);
					mUser.setSigninDays(response.getResponse().getSigninDays());
					mUser.setGoldValue(response.getResponse().getGoldValue());
					mUser.setSiginDaysGetGameGift(response.getResponse().isSiginDaysGetGameGift());
					IPlatApplication.get().setUser(mUser);
				}else{
					signInBtn.setClickable(false);
					signInBtn.setBackgroundResource(E_drawable.efun_pd_a6a6a6_rectangle_bg);
					signInBtn.setText(EfunResourceUtil.findStringByName(getContext(), "efun_pd_has_signined"));
					signInDays.setText(response.getResponse().getSigninDays()+"");
					integral.setText((response.getResponse().getGoldValue())+"");
					//刷新數據
					mUser.setSignin(true);
					mUser.setSigninDays(response.getResponse().getSigninDays());
					mUser.setGoldValue(response.getResponse().getGoldValue());
					mUser.setSiginDaysGetGameGift(response.getResponse().isSiginDaysGetGameGift());
					IPlatApplication.get().setUser(mUser);
				}
				if(signInBtn.isClickable() && mUser.isSignin() && mUser.isTodayHasSigninGift() && !mUser.isGetSigninGift() && mUser.isSiginDaysGetGameGift()){
					//获取可获取奖励的游戏列表
					requestWithoutDialog(createGetModuleGameListReqeust("signin"+mUser.getSigninDays()));
				}
				
			}
			
			
		}
		
		if(requestType == IPlatformRequest.REQ_PERSON_GET_SIGN_REWARD){
			PersonGetSignInGiftResponse response = (PersonGetSignInGiftResponse) baseResponse;
			if(response.getResponse().getCode().equals(HttpParam.RESULT_1000)){
				if(response.getResponse().getIsSerial().equals("1")){//序列號
//					ViewUtils.createCommonToast2(getContext(), EfunResourceUtil.findStringByName(getContext(), "efun_pd_sign_tips_fist")+user.getBaseInfo().getSigninDays()+EfunResourceUtil.findStringByName(getContext(), "efun_pd_sign_tips_second"), response.getData().getSerial());
					ToastUtils.toast(getActivity(), EfunResourceUtil.findStringByName(getContext(), "efun_pd_sign_tips_fist")+response.getResponse().getSerial());
					IPlatApplication.get().setNewStatusOfGiftSelf(true);
					if(mRedPointIvs != null){
						mRedPointIvs.get(giftSerialIndex).setVisibility(View.VISIBLE);
					}
				}else if(response.getResponse().getIsSerial().equals("0")){//積分經驗
					if(response.getResponse().getAddGold() != 0){
						ToastUtils.toast(getActivity(), " + "+response.getResponse().getAddGold()+" "+EfunResourceUtil.findStringByName(getContext(), "efun_pd_credit"));
//						ViewUtils.createCommonToast2(getContext(), EfunResourceUtil.findStringByName(getContext(), "efun_pd_sign_tips_fist")+user.getBaseInfo().getSigninDays()+EfunResourceUtil.findStringByName(getContext(), "efun_pd_sign_tips_third")+response.getData().getAddGold()+EfunResourceUtil.findStringByName(getContext(), "efun_pd_sign_tips_integral"), "");
						integral.setText((mUser.getGoldValue()+response.getResponse().getAddGold())+"");
						mUser.setGoldValue(mUser.getGoldValue()+response.getResponse().getAddGold());
						IPlatApplication.get().setUser(mUser);
					}else{
						ToastUtils.toast(getActivity(), " + "+response.getResponse().getAddExp()+" "+EfunResourceUtil.findStringByName(getContext(), "efun_pd_exp"));
//						ViewUtils.createCommonToast2(getContext(), EfunResourceUtil.findStringByName(getContext(), "efun_pd_sign_tips_fist")+user.getBaseInfo().getSigninDays()+EfunResourceUtil.findStringByName(getContext(), "efun_pd_sign_tips_third")+response.getData().getAddExp()+EfunResourceUtil.findStringByName(getContext(), "efun_pd_sign_tips_exp"), "");
					}
				}else if(response.getResponse().getIsSerial().equals("2")){//平臺點
					ToastUtils.toast(getActivity(), " + "+response.getResponse().getAddPoint()+" "+EfunResourceUtil.findStringByName(getContext(), "efun_pd_person_point"));
//					ViewUtils.createCommonToast2(getContext(), EfunResourceUtil.findStringByName(getContext(), "efun_pd_sign_tips_fist")+user.getBaseInfo().getSigninDays()+EfunResourceUtil.findStringByName(getContext(), "efun_pd_sign_tips_third")+response.getData().getAddPoint()+EfunResourceUtil.findStringByName(getContext(), "efun_pd_sign_tips_point"), "");
					IPlatApplication.get().setSumPoint((response.getResponse().getAddPoint()+Integer.valueOf(pfPoint.getText().toString()))+"");
					pfPoint.setText((response.getResponse().getAddPoint()+Integer.valueOf(pfPoint.getText().toString()))+"");
				}
				signInBtn.setBackgroundResource(E_drawable.efun_pd_a6a6a6_rectangle_bg);
				signInBtn.setText(EfunResourceUtil.findStringByName(getContext(), "efun_pd_sign_has_get_reward"));
				signInBtn.setClickable(false);
				mUser.setGetSigninGift(true);
				IPlatApplication.get().setUser(mUser);
			}else{
				Toast.makeText(getContext(), ""+response.getResponse().getMessage(), Toast.LENGTH_SHORT).show();
			}
		}
		
		if(requestType == IPlatformRequest.REQ_USER_GIFT_GAMES_LIST){
			GameListOfModuleResponse response = (GameListOfModuleResponse)baseResponse;
			GiftGameResultBean mResultBean = response.getmResponse();
			if(mResultBean.getCode().equals(HttpParam.RESULT_1000)){
				mGiftGames = mResultBean.getmGiftGames();
				if(mGiftGames != null && mGiftGames.size() > 0){
					awardGameCode = mGiftGames.get(0).getGameCode();
					mGiftGamesStr = new String[mGiftGames.size()];
					for(int i = 0 ; i < mGiftGames.size() ; i++){
						mGiftGamesStr[i] = mGiftGames.get(i).getGameName();
					}
				}
			}
			
		}
		
		
	}

	@Override
	public void onFailure(int requestType) {
		// TODO Auto-generated method stub
		if (requestType == IPlatformRequest.REQ_USER_PLATFORM_POINT) {
			pfPoint.setText("0");
		}
	}

	@Override
	public void onTimeout(int requestType) {
		// TODO Auto-generated method stub
		if (requestType == IPlatformRequest.REQ_USER_PLATFORM_POINT) {
			pfPoint.setText("0");
		}
	}

	@Override
	public void onNoData(int requestType, String noDataNotify) {
		// TODO Auto-generated method stub
		if (requestType == IPlatformRequest.REQ_USER_PLATFORM_POINT) {
			pfPoint.setText("0");
		}
	}

	@Override
	public void initDatas() {
		// TODO Auto-generated method stub
		pfPoint.setText("0");
		mPopHeader = PopUtils
				.createPerInfo(getActivity(), childView, getResources()
						.getStringArray(E_array.efun_pd_pop_per_info_pic),
						new OnEfunItemClickListener() {
							@Override
							public void onItemClick(int position) {
								if (position == 0) {
									// // 调用系统的拍照功能
									Intent it = new Intent(getActivity(),
											CameraActivity.class);
									it.putExtra("CAMERA_TYPE", "Camera");
									getActivity()
											.startActivityForResult(
													it,
													RequestCode.REQ_USER_INFO_HEAD_PHONE);
								} else {
									Intent it = new Intent(getActivity(),
											CameraActivity.class);
									it.putExtra("CAMERA_TYPE", "Gallery");
									getActivity()
											.startActivityForResult(
													it,
													RequestCode.REQ_USER_INFO_HEAD_THUMB);
								}
							}
						});

		if(!EfunStringUtil.isEmpty(IPlatApplication.get().getSumPoint())){
			pfPoint.setText(IPlatApplication.get().getSumPoint());
		}
	}

	@Override
	public void initListeners() {
		// TODO Auto-generated method stub
		// 信件系统
		emailSys.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				IntentUtils.go2Web(getActivity(), Web.WEB_GO_GOLDVALUE, null);
				if(mEmailSysBean != null){
					IntentUtils.go2WithJSWeb(getActivity(),
							Web.WEB_GO_PERSON_EMAIL_SYS, mEmailSysBean);
				}
//				((FrameTabListener) getActivity()).onTabChange(Tab.TAB_ITEM_TAG_SUMMARY);
			}
		});

		// 设置
		setingBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				TrackingUtils.track(getActivity(),TrackingUtils.ACTION_MYSELF,
						TrackingUtils.NAME_MYSELF_SETTING);
				TrackingGoogleUtils.track(TrackingGoogleUtils.ACTION_MYSELF,
						TrackingGoogleUtils.NAME_MYSELF_SETTING);
				Intent it = new Intent(getActivity(), SettingActivity.class);
				getActivity().startActivity(it);
			}
		});

		// 更改头像
		changeHeadIcon.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// TrackingUtils.track(TrackingUtils.ACTION_MYSELF,
				// TrackingUtils.NAME_UPDATE_PERSON_INFO_HEAD);
				mPopHeader.showPopWindow(PopWindow.POP_WINDOW_CHOSE_PIC);
			}
		});

		// 更新个人资料
		changePersonInfo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				TrackingUtils.track(getActivity(),TrackingUtils.ACTION_MYSELF,
						TrackingUtils.NAME_MYSELF_UPDATEINFO);
				TrackingGoogleUtils.track(TrackingGoogleUtils.ACTION_MYSELF,
						TrackingGoogleUtils.NAME_MYSELF_UPDATEINFO);
//				IntentUtils.goWithBean(getActivity(), ResetPerInfoActivity.class, mUser);
				IntentUtils.goWithBeanForResult(getActivity(), ResetPerInfoActivity.class, mUser, RequestCode.REQ_RESET_PERINFO);
//				IntentUtils.goWithBeanForResult(getActivity(),
//						ResetPerInfoActivity.class, mUser,
//						new OnUpdateUserListener() {
//							@Override
//							public void onUpdate(User userInfo) {
//								IPlatApplication.get().setUser(userInfo);
//								nickName.setText(userInfo.getUsername());
//								ImageManager.displayImage(userInfo.getIcon(),
//										headIcon, ImageManager.IMAGE_ROUND,
//										E_dimens.e_corners_radius_124);
//							}
//						});
			}
		});

		// 绑定手机
		mBindPhoneLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				IntentUtils.goWithBean(getActivity(), BindPhoneActivityNew.class, mUser);
			}
		});

		// 会员说明
		mMemberDecLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				TrackingUtils.track(getActivity(),TrackingUtils.ACTION_MYSELF,
						TrackingUtils.NAME_MYSELF_MEMBERINSTRO);
				TrackingGoogleUtils.track(TrackingGoogleUtils.ACTION_MYSELF,
						TrackingGoogleUtils.NAME_MYSELF_MEMBERINSTRO);
				IntentUtils.go2Web(getActivity(), Web.WEB_GO_GOLDVALUE, null);
			}
		});

		// 储值

		chargeBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(mRechargeBean != null){
					TrackingUtils.track(getActivity(),TrackingUtils.ACTION_MYSELF,
							TrackingUtils.NAME_MYSELF_RECHARGE);
					TrackingGoogleUtils.track(TrackingGoogleUtils.ACTION_MYSELF,
							TrackingGoogleUtils.NAME_MYSELF_RECHARGE);
//				IntentUtils.go2ReChargeWeb(getActivity(),
//						Web.WEB_GO_PERSON_RECHAR);
					IntentUtils.go2WithJSWeb(getActivity(),
							Web.WEB_GO_PERSON_RECHAR, mRechargeBean);
				}
			}
		});
		// 储值记录

		chargeRechordBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				IntentUtils.go2WithJSWeb(getActivity(),
//						Web.WEB_GO_PERSON_RECHAR_RECORD, null);
				if(mRechargeRecodeBean != null){
					IntentUtils.go2WithJSWeb(getActivity(),
							Web.WEB_GO_PERSON_RECHAR_RECORD, mRechargeRecodeBean);
				}
			}
		});
		
		//簽到
		signInBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(signInBtn.isClickable() && !mUser.isSignin()){//簽到
					TrackingUtils.track(getActivity(),TrackingUtils.ACTION_MYSELF,
							TrackingUtils.NAME_MYSELF_SING);
					TrackingGoogleUtils.track(TrackingGoogleUtils.ACTION_MYSELF,
							TrackingGoogleUtils.NAME_MYSELF_SING);
					requestWithoutDialog(createSigninReq());
				}
				if(signInBtn.isClickable() && mUser.isSignin() && mUser.isTodayHasSigninGift() && !mUser.isGetSigninGift()){//領取禮包
					
					if(mUser.isSiginDaysGetGameGift()){
						//游戏礼包
						ViewUtils.createToastCommon(getContext(), getString(E_string.efun_pd_person_sign_gift_get_resc), null, mGiftGamesStr, ToastType.CHOSETYPE, new OnEfunItemClickListener() {
							
							@Override
							public void onItemClick(int position) {
								// TODO Auto-generated method stub
								Log.e("efun", "點擊了確認按鈕");
								if(mUser != null){
									TrackingUtils.track(getActivity(),TrackingUtils.ACTION_MYSELF,
											TrackingUtils.NAME_MYSELF_SING_GIFT+mUser.getSigninDays());
									TrackingGoogleUtils.track(TrackingGoogleUtils.ACTION_MYSELF,
											TrackingGoogleUtils.NAME_MYSELF_SING_GIFT+mUser.getSigninDays());
									requestWithDialog(createGetSignInGiftReq("signin"+mUser.getSigninDays(),awardGameCode),getString(E_string.efun_pd_loading_msg_commend));
								}
							}
						}, new OnDialogSelect() {
							
							@Override
							public void onSelect(int postion) {
								// TODO Auto-generated method stub
								Log.e("efun", "当前选择的游戏GameCode为："+mGiftGames.get(postion).getGameCode());
								awardGameCode = mGiftGames.get(postion).getGameCode();
							}
						}).show();
						
					}else{
						//积分礼包
						TrackingUtils.track(getActivity(),TrackingUtils.ACTION_MYSELF,
								TrackingUtils.NAME_MYSELF_SING_GIFT+mUser.getSigninDays());
						TrackingGoogleUtils.track(TrackingGoogleUtils.ACTION_MYSELF,
								TrackingGoogleUtils.NAME_MYSELF_SING_GIFT+mUser.getSigninDays());
						requestWithDialog(createGetSignInGiftReq("signin"+mUser.getSigninDays(),HttpParam.PLATFORM_CODE),getString(E_string.efun_pd_loading_msg_commend));
					}
					
					
				}
			}
		});
	}

	public void displayUserInfo() {
		Log.i("efun", "displayUserInfo");
		childView.setVisibility(View.INVISIBLE);
		functionBeans = null;
		functionBeans = removeVIPorNot(ProcessDatasUtils.getPersonFunctions());
		personFunctionLayout.removeAllViews();
		initFunctionsView(getActivity());
		if(mRedPointIvs != null){
			if (IPlatApplication.get().isNewStatusOfGiftSelf()) {// 礼包序列号状态
				mRedPointIvs.get(giftIndex).setVisibility(View.VISIBLE);
			} else {
				mRedPointIvs.get(giftIndex).setVisibility(View.GONE);
			}
			if (IPlatApplication.get().isNewGiftStatus()) {// 新礼包状态
				mRedPointIvs.get(giftSerialIndex).setVisibility(View.VISIBLE);
			} else {
				mRedPointIvs.get(giftSerialIndex).setVisibility(View.GONE);
			}
		}
		mUser = IPlatApplication.get().getUser();
		if (mUser == null) {
			// addChargeBtnControl();
			// showDialog();
		} else {
			String oldUid = StoreUtil.getValue(getActivity(),
					StoreUtil.Param_file_name, "oldUid");
			if (oldUid != null) {
				if (!mUser.getUid().equals(oldUid)) {
//					addChargeBtnControl();
					initChargeBtnStatus();
					StoreUtil
							.saveValue(getActivity(),
									StoreUtil.Param_file_name, "oldUid",
									mUser.getUid());
				} else if (loadFirstFlag) {
//					addChargeBtnControl();
					initChargeBtnStatus();
					loadFirstFlag = false;
				}
			} else {
//				addChargeBtnControl();
				initChargeBtnStatus();
				StoreUtil.saveValue(getActivity(), StoreUtil.Param_file_name,
						"oldUid", mUser.getUid());
			}
			// 检查序号中心是否有新信息
			queryGiftSelfStatus();
			checkNewGiftStatus();
			requestPlatformPoints();
			checkNewSysEmail();
			childView.setVisibility(View.VISIBLE);
			if (TextUtils.isEmpty(mUser.getIcon())) {
				if (mUser.getSex().equals(getString(E_string.efun_pd_sex_girl))) {
					headIcon.setImageResource(E_drawable.efun_pd_default_round_user_girl_icon);
				} else {
					headIcon.setImageResource(E_drawable.efun_pd_default_round_user_boy_icon);
				}
			} else {
				ImageManager.displayImage(mUser.getIcon(), headIcon,
						ImageManager.IMAGE_ROUND_USER,
						E_dimens.e_corners_radius_150);
			}

			if (TextUtils.isEmpty(mUser.getUsername())) {
				nickName.setText(E_string.efun_pd_empty_nick);
				mUser.setUsername(getString(E_string.efun_pd_empty_nick));
				IPlatApplication.get().setUser(mUser);
			} else {
				nickName.setText(mUser.getUsername());
			}

			if (IPlatApplication.get().getPfLType().equals(LoginPlatform.EFUN)) {
				accountName
						.setText(getString(E_string.efun_pd_person_member_name)
								+ IPlatApplication.get().getPfAName());
			} else if (IPlatApplication.get().getPfLType()
					.equals(LoginPlatform.GOOGLE)) {
				accountName
						.setText(getString(E_string.efun_pd_person_member_name)
								+ getString(E_string.efun_pd_hint_account_by_google));
			} else if (IPlatApplication.get().getPfLType()
					.equals(LoginPlatform.FACEBOOK)) {
				accountName
						.setText(getString(E_string.efun_pd_person_member_name)
								+ getString(E_string.efun_pd_hint_account_by_facebook));
			} else if (IPlatApplication.get().getPfLType()
					.equals(LoginPlatform.BAHAMUT)) {
				accountName
						.setText(getString(E_string.efun_pd_person_member_name)
								+ getString(E_string.efun_pd_hint_account_by_bahamut));
			}

			Log.i("yang",
					"mUser.getExperienceValue():" + mUser.getExperienceValue());
			//玩家等级名称及图标
			experName.setText(mUser.getRangoLevel());
			
			switch (ProcessDatasUtils.getPlayerLevelByExp(mUser.getExperienceValue())) {
			case 1:
				experIcon.setBackgroundResource(E_drawable.efun_pd_person_level_1_icon);
				break;
			case 2:
				experIcon.setBackgroundResource(E_drawable.efun_pd_person_level_2_icon);
				break;
			case 3:
				experIcon.setBackgroundResource(E_drawable.efun_pd_person_level_3_icon);
				break;
			case 4:
				experIcon.setBackgroundResource(E_drawable.efun_pd_person_level_4_icon);
				break;
			case 5:
				experIcon.setBackgroundResource(E_drawable.efun_pd_person_level_5_icon);
				break;
			case 6:
				experIcon.setBackgroundResource(E_drawable.efun_pd_person_level_6_icon);
				break;
			case 7:
				experIcon.setBackgroundResource(E_drawable.efun_pd_person_level_7_icon);
				break;
			case 8:
				experIcon.setBackgroundResource(E_drawable.efun_pd_person_level_8_icon);
				break;
			case 9:
				experIcon.setBackgroundResource(E_drawable.efun_pd_person_level_9_icon);
				break;
			}
			
			integral.setText(mUser.getGoldValue() + "");// 積分
			signInDays.setText(mUser.getSigninDays()+"");
			// 用戶id
			userId.setText(getString(E_string.efun_pd_person_member_id)
					+ mUser.getUid());
			if (TextUtils.isEmpty(mUser.getPhone()) && !mUser.getIsAuthPhone().equals("1")) {
				bindPhoneDec.setText(ProcessDatasUtils.getPersonTextInfo(ProcessDatasUtils.TYPE_BIND_PHONE_AREA, ProcessDatasUtils.TYPE_PHNOE_DESC).getSubName());
				mBindPhoneLayout.setClickable(true);
			} else {
				bindPhoneDec.setText("+"+mUser.getAreaCode()+" "+mUser.getPhone());
				mBindPhoneLayout.setClickable(false);
			}
			// // Vip item的显示与否
			 if (!(mUser.getIsVip()).equals("0")) {
				 vipIcon.setBackgroundResource(E_drawable.efun_pd_person_isnotvip_icon);
//				 if(vipLayout != null){
//					 vipLayout.setVisibility(View.GONE);
//					 rightLineLayout.setVisibility(View.GONE);
//					 emptyLayoutLayout.setVisibility(View.INVISIBLE);
//					 vipLayout.setClickable(false);
//				 }
//				 if(unVipLayout != null){
//					 unVipLayout.setVisibility(View.VISIBLE);
//					 unVipRightLineLayout.setVisibility(View.VISIBLE);
//					 unVipEmptyLayout.setVisibility(View.GONE);
//					 unVipLayout.setClickable(true);
//				 }
			 } else {
				 vipIcon.setBackgroundResource(E_drawable.efun_pd_person_isvip_cion);
//				 if(vipLayout != null){
//					 vipLayout.setVisibility(View.VISIBLE);
//					 rightLineLayout.setVisibility(View.VISIBLE);
//					 emptyLayoutLayout.setVisibility(View.GONE);
//					 vipLayout.setClickable(true);
//				 }
//				 if(unVipLayout != null){
//					 unVipLayout.setVisibility(View.GONE);
//					 unVipRightLineLayout.setVisibility(View.GONE);
//					 unVipEmptyLayout.setVisibility(View.INVISIBLE);
//					 unVipLayout.setClickable(false);
//				 }
			 }
			if (!TextUtils.isEmpty(mUser.getIsSendGold())
					&& mUser.getIsSendGold().equals("1")) {
//				if (mUser.getGotGold() > 0) {
//					ToastUtils.toast(getActivity(), childView,
//							E_drawable.efun_pd_toast_green_bg,
//							" + " + mUser.getGotGold()
//									+ getString(E_string.efun_pd_credit),
//							Toast.LENGTH_SHORT, 0, 600);
//				}
				if (mUser.getGotExp() > 0) {
					ToastUtils.toast(getActivity(), childView, -1, " + "
							+ mUser.getGotExp()
							+ getString(E_string.efun_pd_exp),
							Toast.LENGTH_SHORT, 0, 400);
				}
				IPlatApplication.get().getUser().setIsSendGold("0");
			}
			
			//是否已签到
			if(mUser.isTodayHasSigninGift()){
				if(!mUser.isSignin()){//没签到
					signInBtn.setClickable(true);
					signInBtn.setBackgroundResource(E_drawable.efun_pd_cons_2cd7d1_bg);
					signInBtn.setText(EfunResourceUtil.findStringByName(getContext(), "efun_pd_person_sign_in"));
				}else if(!mUser.isGetSigninGift()){//没领奖
					signInBtn.setClickable(true);
					signInBtn.setBackgroundResource(E_drawable.efun_pd_ff4848_rectangle_bg);
					signInBtn.setText(EfunResourceUtil.findStringByName(getContext(), "efun_pd_get_reward"));
				}else{//已签到，已领奖
					signInBtn.setClickable(false);
					signInBtn.setBackgroundResource(E_drawable.efun_pd_a6a6a6_rectangle_bg);
					signInBtn.setText(EfunResourceUtil.findStringByName(getContext(), "efun_pd_has_signined"));
				}
			}else{
				if(mUser.isSignin()){//已签到
					signInBtn.setClickable(false);
					signInBtn.setBackgroundResource(E_drawable.efun_pd_a6a6a6_rectangle_bg);
					signInBtn.setText(EfunResourceUtil.findStringByName(getContext(), "efun_pd_has_signined"));
				}else{
					signInBtn.setClickable(true);
					signInBtn.setBackgroundResource(E_drawable.efun_pd_cons_2cd7d1_bg);
					signInBtn.setText(EfunResourceUtil.findStringByName(getContext(), "efun_pd_person_sign_in"));
				}
			}
			
		if(signInBtn.isClickable() && mUser.isSignin() && mUser.isTodayHasSigninGift() && !mUser.isGetSigninGift() && mUser.isSiginDaysGetGameGift()){
				//获取可获取奖励的游戏列表
				requestWithoutDialog(createGetModuleGameListReqeust("signin"+mUser.getSigninDays()));
		}

		}
	}

	/**
	 * 检查序号中心是否有新消息
	 */
	private void queryGiftSelfStatus() {
		if (IPlatApplication.get().getUser() != null) {
			GiftSelfStatusRequest request = new GiftSelfStatusRequest(
					IPlatApplication.get().getUser().getUid(),
					HttpParam.GIFT_STATUS_QUERY);
			request.setSign(IPlatApplication.get().getUser().getSign());
			request.setTimestamp(IPlatApplication.get().getUser()
					.getTimestamp());
			request.setReqType(IPlatformRequest.REQ_GIFT_SELF_STATUS);

			requestWithoutDialog(new BaseRequestBean[] { request });

		}
	}

	/**
	 * 检查有没有新礼包
	 */
	private void checkNewGiftStatus() {
		if (IPlatApplication.get().getUser() != null) {
			NewGiftStatusRequest request = new NewGiftStatusRequest(
					HttpParam.APP, HttpParam.PLATFORM_AREA, HttpParam.PLATFORM,
					AppUtils.getAppVersionName(getActivity()),
					HttpParam.LANGUAGE);
			request.setReqType(IPlatformRequest.REQ_NEW_GIFT_STATUS);

			requestWithoutDialog(new BaseRequestBean[] { request });
		}
	}

	/**
	 * 检查有没有新信件
	 */
	private void checkNewSysEmail() {
		if (IPlatApplication.get().getUser() != null) {
			CheckNewSysEmailRequest request = new CheckNewSysEmailRequest(
					IPlatApplication.get().getUser().getUid(), 
					HttpParam.PLATFORM_AREA, 
					IPlatApplication.get().getUser().getSign(), 
					IPlatApplication.get().getUser().getTimestamp(), 
					HttpParam.APP, 
					HttpParam.LANGUAGE, 
					HttpParam.PLATFORM, 
					AppUtils.getAppVersionName(getActivity()));
			request.setReqType(IPlatformRequest.REQ_USER_CHECK_NEW_SYS_EMAIL);

			requestWithoutDialog(new BaseRequestBean[] { request });
		}
	}
	
	/**
	 * 平台点
	 */
	private void requestPlatformPoints() {
		if (IPlatApplication.get().getUser() != null) {
			PersonPlaformPointRequest request = new PersonPlaformPointRequest(
					IPlatApplication.get().getUser().getUid(), "false",
					IPlatApplication.get().getUser().getSign(),
					IPlatApplication.get().getUser().getTimestamp());
			request.setReqType(IPlatformRequest.REQ_USER_PLATFORM_POINT);

			requestWithoutDialog(new BaseRequestBean[] { request });

		}
	}
	
	/**
	 * 更新玩家头像
	 * @return 
	 */
	private BaseRequestBean createRefreshPlayerHeaderIcon() {
		if (IPlatApplication.get().getUser() != null) {
			PersonRefreshHeadIconRequest request = new PersonRefreshHeadIconRequest(
					IPlatApplication.get().getUser().getUid(), 
					HttpParam.PLATFORM_AREA, 
					IPlatApplication.get().getUser().getSign(), 
					IPlatApplication.get().getUser().getTimestamp(), 
					mUser.getIcon(), 
					HttpParam.APP, 
					HttpParam.LANGUAGE, 
					HttpParam.PLATFORM, 
					AppUtils.getAppVersionName(getActivity()));
			request.setReqType(IPlatformRequest.REQ_USER_UPDATE_HEADER_NEW);
			return request;
		}else{
			return null;
		}
	}
	
	
	/**
	 * 添加储值按钮控制接口
	 */
	private void addChargeBtnControl() {
		// TODO Auto-generated method stub
		requestWithoutDialog(createChargeControlRequest());
	}
	
	/**
	 * 初始化储值按钮状态
	 */
	private void initChargeBtnStatus(){
		if(mRechargeBean != null){//储值按钮 
			if(mRechargeBean.isOpen()){//开
				if (mRechargeBean.getVersionName().contains(EfunLocalUtil.getVersionName(this.getActivity()))) {
					chargeBtn.setVisibility(View.GONE);
				}else{
					chargeBtn.setVisibility(View.VISIBLE);
				}
			}else{
				chargeBtn.setVisibility(View.GONE);
			}
		}
		
		if(mRechargeRecodeBean != null){//储值记录
			if(mRechargeRecodeBean.isOpen()){//开
				if (mRechargeRecodeBean.getVersionName().contains(EfunLocalUtil.getVersionName(this.getActivity()))) {

					chargeRechordBtn.setVisibility(View.GONE);
				}else{

					chargeRechordBtn.setVisibility(View.VISIBLE);
				}
			}else{
				chargeRechordBtn.setVisibility(View.GONE);
			}
		}
		
		if(mRechargeBean != null || mRechargeRecodeBean != null){
			if(mRechargeBean.isOpen() || mRechargeRecodeBean.isOpen()){

				if (mRechargeBean.getVersionName().contains(EfunLocalUtil.getVersionName(this.getActivity())) &&
						mRechargeRecodeBean.getVersionName().contains(EfunLocalUtil.getVersionName(this.getActivity()))) {
					personChargeLayout.setVisibility(View.GONE);
				}else{

					personChargeLayout.setVisibility(View.VISIBLE);
				}
			}else{
				personChargeLayout.setVisibility(View.GONE);
			}
		}else{
			personChargeLayout.setVisibility(View.GONE);
		}
	}

	private BaseRequestBean[] createChargeControlRequest() {
		if (IPlatApplication.get().getUser() != null) {
			TelephonyManager mTelephonyManager = (TelephonyManager) getActivity()
					.getSystemService(Context.TELEPHONY_SERVICE);
			String netFlag = mTelephonyManager.getSubscriberId();
			String payFrom = "efun";

			if (TextUtils.isEmpty(netFlag)) {
				netFlag = "111111111";
			}

			RechargeControlRequest request = new RechargeControlRequest(
					HttpParam.PLATFORM_CODE, HttpParam.CHARGE_FLAG,
					AppUtils.getAppVersionName(getActivity()), payFrom, "",
					netFlag, IPlatApplication.get().getUser().getUid(),
					HttpParam.PLATFORM_AREA,
					AppUtils.getAppVersionName(getActivity()),
					HttpParam.LANGUAGE);
			request.setReqType(IPlatformRequest.REQ_CHARGE_CONTROL);
			return new BaseRequestBean[] { request };
		} else {
			return null;
		}
	}

	private BaseRequestBean[] createUpLoadImg() {
		UserUpdateHeaderRequest headerRequest = new UserUpdateHeaderRequest(
				HttpParam.PLATFORM_CODE, fileName, "jpg",
				getString(E_string.efun_pd_img_upload_platform),
				AppUtils.getAppVersionName(getActivity()), HttpParam.LANGUAGE,
				encodeFile);
		if (IPlatApplication.get().getUser() != null) {
			headerRequest.setSignature(IPlatApplication.get().getUser()
					.getSign());
			headerRequest.setTimestamp(IPlatApplication.get().getUser()
					.getTimestamp());
			headerRequest.setUserid(IPlatApplication.get().getUser().getUid());
		}
		headerRequest.setReqType(IPlatformRequest.REQ_USER_UPDATE_HEADER);
		return new BaseRequestBean[] { headerRequest };
	}
	
	private BaseRequestBean[] createSigninReq(){
		PersonSignRequest request = new PersonSignRequest(
				IPlatApplication.get().getUser().getUid(), 
				"SIGNIN", 
				HttpParam.LANGUAGE, 
				HttpParam.PLATFORM_AREA, 
				IPlatApplication.get().getUser().getSign(), 
				IPlatApplication.get().getUser().getTimestamp(), 
				HttpParam.PLATFORM, 
				AppUtils.getAppVersionName(getActivity()));
		request.setReqType(IPlatformRequest.REQ_PERSON_SIGN);
		return new BaseRequestBean[] { request };
	}
	
	private BaseRequestBean[] createGetSignInGiftReq(String goodsType,String gameCode){
		PersonGetSignInGiftRequest request = new PersonGetSignInGiftRequest(
				IPlatApplication.get().getUser().getUid(), 
				HttpParam.LANGUAGE, 
				gameCode, 
				"reward_gift", 
				goodsType, 
				HttpParam.PLATFORM_AREA, 
				IPlatApplication.get().getUser().getSign(), 
				IPlatApplication.get().getUser().getTimestamp());
		request.setReqType(IPlatformRequest.REQ_PERSON_GET_SIGN_REWARD);
		return new BaseRequestBean[] { request };
		
	}
	
	private BaseRequestBean[] createGetModuleGameListReqeust(String module){
		GameListOfModuleRequest request = new GameListOfModuleRequest(
				HttpParam.PLATFORM_AREA, 
				module, 
				HttpParam.PLATFORM, 
				AppUtils.getAppVersionName(getContext()),
				HttpParam.APP, 
				HttpParam.LANGUAGE);
		request.setReqType(IPlatformRequest.REQ_USER_GIFT_GAMES_LIST);
		return new BaseRequestBean[]{request};
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		Log.e("efun", "PerCenter:requestCode:"+requestCode);
		if (resultCode == RequestCode.REQ_PIC_PHOTO_CAMERA_RETURN) {
			if (data != null && data.getExtras() != null) {
//				newHeaderBitmap = data.getExtras().getParcelable("dataBitMap");
				Uri uri = data.getExtras().getParcelable("dataBitMap");
				newHeaderBitmap = decodeUriAsBitmap(uri);
				if(newHeaderBitmap != null){
					EfunLogUtil.logE("newShotPicBitmap is not null");
					encodeFile = byte2hex(Bitmap2Bytes(newHeaderBitmap));
					fileName = EfunStringUtil.toMd5(newHeaderBitmap.toString(),
							false);
					Log.i("efun", "fileName2:" + fileName);
				}
				requestWithDialog(createUpLoadImg(), "上傳數據中...");
			}
		}
//		if(requestCode == RequestCode.REQ_RESET_PERINFO){
//			if(UserUtils.isLogin()){
//				Log.e("efun", "onactivivyResult:isLogin");
////				getActivity().finish();
//			}else{
//				Log.e("efun", "onactivivyResult:isnotLogin");
//				((FrameTabListener) getActivity()).onTabChange(Tab.TAB_ITEM_TAG_SUMMARY);//跳转到资讯页面
////				getActivity().finish();
//			}
//		}
	}
	
	/**
	 * <br>
	 * 功能简述: <br>
	 * 功能详细描述: <br>
	 * 注意:
	 * 
	 * @param uri
	 * @return
	 */
	private Bitmap decodeUriAsBitmap(Uri uri) {
		Bitmap bitmap = null;
		try {
			bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver()
					.openInputStream(uri));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		return bitmap;
	}

	@Override
	public ViewGroup[] needShowLoading() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean needRequestData() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public BaseRequestBean[] needRequestDataBean() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 二进制转字符串
	 * 
	 * @param b
	 * @return
	 */
	public static String byte2hex(byte[] b) {
		StringBuffer sb = new StringBuffer();
		for (int n = 0; n < b.length; n++) {
			sb.append(String.format("%02x", b[n])); // 占2位不足补零
		}
		return sb.toString();
	}

	/**
	 * 将bitmap转为byte[]
	 * 
	 * @param bm
	 * @return
	 */
	public byte[] Bitmap2Bytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		return baos.toByteArray();
	}

	private void initFunctionsView(Context context) {
		// TODO Auto-generated method stub
		int functionRow = 0;
		int rowYu = 0;
//		boolean hasVipLayout = false;
//		boolean hasUnVipLayout = false;
		if (functionBeans != null) {
			mRedPointIvs = new ArrayList<ImageView>();
			functionRow = functionBeans.size() / 4;
			rowYu = functionBeans.size() % 4;
			Log.e("efun", "functionRow:" + functionRow + "/rowYu:" + rowYu);
			if (rowYu > 0) {// 当存在余数时，则添加多一行
				functionRow = functionRow + 1;
			}
			for (int i = 0; i < functionRow; i++) {
//				hasVipLayout = false;
//				hasUnVipLayout = false;
				LinearLayout rowLayout = new LinearLayout(context);
				LayoutParams params = new LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
				rowLayout.setOrientation(LinearLayout.HORIZONTAL);
				personFunctionLayout.addView(rowLayout, params);

				for (int j = i * 4; j < (i + 1) * 4; j++) {
					View v = inflater.inflate(
							E_layout.efun_pd_persion_tuozhan_item, null);
					v.setLayoutParams(new LayoutParams(0,
							LayoutParams.WRAP_CONTENT, 1.0f));
					rowLayout.addView(v);
					ImageView itemIcon = (ImageView) v
							.findViewById(E_id.item_icon);
					TextView itemTitle = (TextView) v
							.findViewById(E_id.item_title);
					ImageView redPoint = (ImageView) v
							.findViewById(E_id.item_red_point);
					mRedPointIvs.add(redPoint);
					View rightLine = null;
					rightLine = new View(context);
					rightLine.setLayoutParams(new LayoutParams(1,
							LayoutParams.MATCH_PARENT));
					rightLine.setBackgroundResource(E_color.e_cecece);
					rowLayout.addView(rightLine);
					// 没有数据
					if (j >= functionBeans.size()) {
						itemIcon.setVisibility(View.INVISIBLE);
						itemTitle.setVisibility(View.INVISIBLE);
						rightLine.setVisibility(View.INVISIBLE);
						v.setClickable(false);
					} else {
						ImageManager.displayImage(functionBeans.get(j)
								.getIcon(), itemIcon,options);
						itemTitle.setText(functionBeans.get(j).getName());
						v.setOnClickListener(new functionItemOnClickListener(j));
//						if(functionBeans.get(j).getId().equals("1-3-7")){
//							hasVipLayout = true;
//							vipLayout = v;
//							rightLineLayout = rightLine;
//						}
//						if(functionBeans.get(j).getId().equals("1-3-12")){
//							hasUnVipLayout = true;
//							unVipLayout = v;
//							unVipRightLineLayout = rightLine;
//						}
						
					}
//					//当一行中有存在VIP项时
//					if(j == ((i + 1) * 4 -1) && hasVipLayout){
//						View v1 = inflater.inflate(
//								E_layout.efun_pd_persion_tuozhan_item, null);
//						v1.setLayoutParams(new LayoutParams(0,
//								LayoutParams.WRAP_CONTENT, 1.0f));
//						emptyLayoutLayout = v1;
//						rowLayout.addView(v1);
//						emptyLayoutLayout.setVisibility(View.GONE);
//					}
//					//当一行中有存在非VIP项时
//					if(j == ((i + 1) * 4 -1) && hasUnVipLayout){
//						View v2 = inflater.inflate(
//								E_layout.efun_pd_persion_tuozhan_item, null);
//						v2.setLayoutParams(new LayoutParams(0,
//								LayoutParams.WRAP_CONTENT, 1.0f));
//						unVipEmptyLayout = v2;
//						rowLayout.addView(v2);
//						unVipEmptyLayout.setVisibility(View.GONE);
//					}
				}
				// 底部线
				View buttomLine = new View(context);
				buttomLine.setLayoutParams(new LayoutParams(
						LayoutParams.MATCH_PARENT, 1));
				buttomLine.setBackgroundResource(E_color.e_cecece);
				personFunctionLayout.addView(buttomLine);
			}
		} else {
			personFunctionLayout.setVisibility(View.GONE);
		}
	}

	/**
	 * 各拓展功能按钮点击监听
	 */
	class functionItemOnClickListener implements OnClickListener {
		private int index;

		public functionItemOnClickListener(int position) {
			this.index = position;
		}

		@Override
		public void onClick(View paramView) {
			// TODO Auto-generated method stub
			Log.e("efun", "id:" + functionBeans.get(index).getId() + "/name:"
					+ functionBeans.get(index).getName());
			if (functionBeans.get(index).getId().equals("1-3-4")) {// 礼包中心
				TrackingUtils.track(getActivity(),TrackingUtils.ACTION_MYSELF,
						TrackingUtils.NAME_MYSELF_GIFT);
				TrackingGoogleUtils.track(TrackingGoogleUtils.ACTION_MYSELF,
						TrackingGoogleUtils.NAME_MYSELF_GIFT);
				mRedPointIvs.get(giftIndex).setVisibility(View.GONE);
				Intent it = new Intent(getActivity(), GiftListActivity.class);
				startActivity(it);
			} else if (functionBeans.get(index).getId().equals("1-3-3")) {// 序号中心
				TrackingUtils.track(getActivity(),TrackingUtils.ACTION_MYSELF,
						TrackingUtils.NAME_MYSELF_SERIAL);
				TrackingGoogleUtils.track(TrackingGoogleUtils.ACTION_MYSELF,
						TrackingGoogleUtils.NAME_MYSELF_SERIAL);
				Intent it = new Intent(getActivity(), GiftSelfActivity.class);
				startActivity(it);
			} else if (functionBeans.get(index).getId().equals("1-3-1")) {// 砸蛋
				TrackingUtils.track(getActivity(),TrackingUtils.ACTION_MYSELF,
						TrackingUtils.NAME_MYSELF_KNOCK_EGG);
				TrackingGoogleUtils.track(TrackingGoogleUtils.ACTION_MYSELF,
						TrackingGoogleUtils.NAME_MYSELF_KNOCK_EGG);
//				IntentUtils.go2Web(getActivity(), Web.WEB_GO_EGG, null);
				IntentUtils.go2WithJSWeb(getActivity(),
						Web.WEB_GO_EGG, functionBeans.get(index));
			} else if (functionBeans.get(index).getId().equals("1-3-2")) {// 刮刮乐
				IntentUtils.go2WithJSWeb(getActivity(),
						Web.WEB_GO_PERSON_SCATCH, functionBeans.get(index));
			} else if (functionBeans.get(index).getId().equals("1-3-5")) {// 红人馆
				IntentUtils.go2WithJSWeb(getActivity(),
						Web.WEB_GO_PERSON_FUNCTIONS, functionBeans.get(index));
			} else if (functionBeans.get(index).getId().equals("1-3-6")) {// 转帐
				IntentUtils.go2WithJSWeb(getActivity(),
						Web.WEB_GO_PERSON_FUNCTIONS, functionBeans.get(index));
			} else if (functionBeans.get(index).getId().equals("1-3-7")) {// VIP
				TrackingUtils.track(getActivity(),TrackingUtils.ACTION_MYSELF,
						 TrackingUtils.NAME_MYSELF_VIP);
				TrackingGoogleUtils.track(TrackingGoogleUtils.ACTION_MYSELF,
						TrackingGoogleUtils.NAME_MYSELF_VIP);
//						 IntentUtils.go2Web(getActivity(), Web.WEB_GO_VIP, null);
				IntentUtils.go2WithJSWeb(getActivity(),
						Web.WEB_GO_VIP, functionBeans.get(index));
			}else{
				IntentUtils.go2WithJSWeb(getActivity(),
						Web.WEB_GO_PERSON_FUNCTIONS, functionBeans.get(index));
			}
		}
	}
	
	private ArrayList<ConfigInfoBean> removeVIPorNot(ArrayList<ConfigInfoBean> beans){
		ArrayList<ConfigInfoBean> configBeans = null;
		if(beans != null){
			configBeans = new ArrayList<ConfigInfoBean>();
			configBeans.addAll(beans);
			if(IPlatApplication.get().getUser() != null){
				if (!(IPlatApplication.get().getUser().getIsVip()).equals("0")) {//非VIP
					for(int i = 0 ; i < configBeans.size(); i++){
						if(configBeans.get(i).getId().equals("1-3-7")){
							configBeans.remove(i);
							i--;
						}
					}
				}else{
					for(int i = 0 ; i < configBeans.size(); i++){
						if(configBeans.get(i).getId().equals("1-3-12")){
							configBeans.remove(i);
							i--;
						}
					}
				}
			}
		}
		return configBeans;
	}

}
