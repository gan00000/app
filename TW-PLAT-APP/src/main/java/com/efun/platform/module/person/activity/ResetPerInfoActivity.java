package com.efun.platform.module.person.activity;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.efun.core.tools.EfunLogUtil;
import com.efun.core.tools.EfunResourceUtil;
import com.efun.core.tools.EfunStringUtil;
import com.efun.platform.AndroidScape.E_color;
import com.efun.platform.AndroidScape.E_drawable;
import com.efun.platform.AndroidScape.E_id;
import com.efun.platform.AndroidScape.E_layout;
import com.efun.platform.AndroidScape.E_string;
import com.efun.platform.IPlatApplication;
import com.efun.platform.http.dao.impl.IPlatformRequest;
import com.efun.platform.http.request.bean.BaseRequestBean;
import com.efun.platform.http.request.bean.GameListOfModuleRequest;
import com.efun.platform.http.request.bean.PersonGetAwardGiftRequest;
import com.efun.platform.http.request.bean.UserUpdateInfoRequest;
import com.efun.platform.http.response.bean.BaseResponseBean;
import com.efun.platform.http.response.bean.GameListOfModuleResponse;
import com.efun.platform.http.response.bean.GiftKnockResponse;
import com.efun.platform.http.response.bean.UserUpdateInfoResponse;
import com.efun.platform.module.PopWindow;
import com.efun.platform.module.account.activity.BindEmailActivity;
import com.efun.platform.module.account.activity.BindPhoneActivityNew;
import com.efun.platform.module.account.bean.User;
import com.efun.platform.module.base.FixedActivity;
import com.efun.platform.module.base.impl.OnEfunItemClickListener;
import com.efun.platform.module.base.impl.OnLoginFinishListener;
import com.efun.platform.module.base.impl.OnUpdateSecondUserListener;
import com.efun.platform.module.base.impl.OnUpdateUserListener;
import com.efun.platform.module.bean.ConfigInfoBean;
import com.efun.platform.module.bean.PlayerAreaBean;
import com.efun.platform.module.bean.PlayerCityBean;
import com.efun.platform.module.person.bean.GiftGameBean;
import com.efun.platform.module.person.bean.GiftGameResultBean;
import com.efun.platform.module.person.bean.UserUpdateBean;
import com.efun.platform.module.utils.AppUtils;
import com.efun.platform.module.utils.IntentUtils;
import com.efun.platform.module.utils.PopUtils;
import com.efun.platform.module.utils.ProcessDatasUtils;
import com.efun.platform.module.utils.ToastUtils;
import com.efun.platform.module.utils.TrackingGoogleUtils;
import com.efun.platform.module.utils.TrackingUtils;
import com.efun.platform.module.utils.UserUtils;
import com.efun.platform.module.utils.ViewUtils;
import com.efun.platform.module.utils.ViewUtils.OnDialogSelect;
import com.efun.platform.module.welfare.bean.GiftKnockBean;
import com.efun.platform.utils.Const;
import com.efun.platform.utils.Const.HttpParam;
import com.efun.platform.utils.Const.RequestCode;
import com.efun.platform.utils.Const.ResultCode;
import com.efun.platform.utils.Const.ToastType;
import com.efun.platform.utils.TimeFormatUtil;
import com.efun.platform.widget.TitleView;
import com.efun.platform.widget.dialog.MyAlertDialog;
import com.efun.platform.widget.dialog.OnWheelChangedListener;
import com.efun.platform.widget.dialog.WheelView;
import com.efun.platform.widget.dialog.adapter.AbstractWheelTextAdapter;
import com.efun.platform.widget.dialog.adapter.ArrayWheelAdapter;

/**
 * 修改玩家信息页面
 * 
 * @author itxuxxey
 * 
 */
public class ResetPerInfoActivity extends FixedActivity {

	private LinearLayout mNormalInfoLayout, mEditInfoLayout;// 正常显示信息状态，编辑信息状态
	private TextView phoneTitle, emailTitle;//手机标题，邮箱标题
//	private LinearLayout mBindPhoneLayout, mBindEmailLayout;// 首次綁定手機信息,郵箱信息
//	private LinearLayout mEditextCityLayout;//编辑城市可选项
	// 顯示狀態的昵稱，line,性別，生日,地區，詳細地址,手機號碼,UID
	private TextView mNormalNick, mNormalLine, mNormalSex, mNormalBirth,
			mNormalArea, mNormalDetailAddress,mNormalUid,mEditUid,mNormalRealName,mNormalIdCard;
	// 未設置狀態
	private TextView mNotSetNick, mNotSetLine, mNotSetSex, mNotSetBirth,
			mNotSetArea, mNotSetDetailAddress,mNotSetRealName,mNotSetIdCard;
	private TextView mNormalPhone, mNormalEmail;
	// 綁定手機，邮箱
	private TextView mBindPhone, mBindEmail;
	//是否已绑定手机或邮箱的状态
	private boolean isBindPhone = false;
	private boolean isBindEmail = false;

	// 編輯：昵稱，line,詳細地址
	private EditText mEditNick, mEidtLine, mEidtDetailAddress,mEditRealName,mEditIDCard;
	// 編輯：生日，地區，城市
	private TextView mEditBirth, mEditArea; 
//	mEditCity;

	// 完善資料領取獎勵按鈕,更改密碼，設置轉點密碼，幫助
	private TextView mGetGiftBtn, mChangePwdBtn;
//	mSetPointPwdBtn,mSetPointPwdHelpBtn;
	//领取奖励按钮layout
	private LinearLayout mGetGiftLayout;
	
	//用戶信息
	private User mUserInfoBean;
	//切換賬號
	private TextView mChangeAccount;
	//性別選擇
	private TextView sexRadio;
//	private RadioButton mMale,mFemale;
	private TextView resetButton;//编辑，保存按钮
	
	//选择的获取完善资料奖励的游戏
	private String awardGameCode;

	private TitleView titleView;
	private boolean isSave = false;
	
	private Calendar calendar = Calendar.getInstance();
	private String birthStr = "";
	
	private ArrayList<GiftGameBean> mGiftGames;//可领奖的游戏数据列表
	private String[] mGiftGamesStr;//可领奖的游戏名称列表
	
	private ArrayList<PlayerAreaBean> mPlayerAreas;//玩家地区数据列表
	private String[] playerAreaNames;//玩家地区名称列表
	private ArrayList<PlayerCityBean> mPlayerCityBeans;//玩家城市数据列表
	private String[] playerCityNames;//玩家城市名称列表
	
	private ConfigInfoBean mSetChangePointBean;
	
	private String cityTxt;
	private PopWindow mSexPopWindow;

	@Override
	public void onClickRight() {
		super.onClickRight();
//		if (isSave) {// 保存按鈕
//			isSave = false;
//			titleView.setTitleRightTextRes(E_string.efun_pd_edit);
//			mNormalInfoLayout.setVisibility(View.VISIBLE);
//			mEditInfoLayout.setVisibility(View.GONE);
//			mUserInfoBean.setUsername(mEditNick.getText().toString());
//			mUserInfoBean.setLine(mEidtLine.getText().toString());
//			mUserInfoBean.setAddress(mEidtDetailAddress.getText().toString());
//			mUserInfoBean.setRealName(mEditRealName.getText().toString());
//			mUserInfoBean.setIdCard(mEditIDCard.getText().toString());
//			phoneTitle.setTextColor(mContext.getResources().getColor(E_color.e_8d8d8d));
//			emailTitle.setTextColor(mContext.getResources().getColor(E_color.e_8d8d8d));
//			requst();
//		} else {// 編輯按鈕
//			isSave = true;
//			phoneTitle.setTextColor(mContext.getResources().getColor(E_color.e_272727));
//			emailTitle.setTextColor(mContext.getResources().getColor(E_color.e_272727));
//			titleView.setTitleRightTextRes(E_string.efun_pd_save);
//			mNormalInfoLayout.setVisibility(View.GONE);
//			mEditInfoLayout.setVisibility(View.VISIBLE);
//			TrackingUtils.track(mContext, TrackingUtils.ACTION_UPDATE_PERSON_INFO, TrackingUtils.NAME_UPDATE_PERSON_INFO_EDIT);
////			TrackingGoogleUtils.track(TrackingGoogleUtils.ACTION_UPDATE_PERSON_INFO, TrackingGoogleUtils.NAME_UPDATE_PERSON_INFO_EDIT);
//		}

	}

	@Override
	public boolean needRequestData() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public BaseRequestBean[] needRequestDataBean() {
		UserUpdateInfoRequest infoRequest = new UserUpdateInfoRequest();
		if (IPlatApplication.get().getUser() != null) {
			infoRequest.setSign(IPlatApplication.get().getUser().getSign());
			infoRequest.setTimestamp(IPlatApplication.get().getUser()
					.getTimestamp());
		}
		infoRequest.setUsername(mUserInfoBean.getUsername());
		infoRequest.setLine(mUserInfoBean.getLine());
		infoRequest.setSex(mUserInfoBean.getSex());
		infoRequest.setBirthday(birthStr);
		infoRequest.setArea(mUserInfoBean.getArea());
		infoRequest.setCity(mUserInfoBean.getCity());
		infoRequest.setAddress(mUserInfoBean.getAddress());
		infoRequest.setUid(IPlatApplication.get().getUser().getUid());
		infoRequest.setPlatform(HttpParam.PLATFORM_AREA);
		infoRequest.setFromType(HttpParam.APP);
		infoRequest.setVersion(HttpParam.PLATFORM);
		infoRequest.setPackageVersion(AppUtils.getAppVersionName(mContext));
		infoRequest.setLanguage(HttpParam.LANGUAGE);
		infoRequest.setRealName(mUserInfoBean.getRealName());
		infoRequest.setIdCard(mUserInfoBean.getIdCard());
		
		infoRequest.setReqType(IPlatformRequest.REQ_USER_UPDATE_INFO);
		return new BaseRequestBean[] { infoRequest };
	}

	@Override
	public void init(Bundle bundle) {
		mUserInfoBean = (User) bundle.get(Const.BEAN_KEY);

		mEditInfoLayout = (LinearLayout) findViewById(E_id.reset_info_edit_layout);
		mNormalInfoLayout = (LinearLayout) findViewById(E_id.reset_info_normal_layout);
//		mBindPhoneLayout = (LinearLayout) findViewById(E_id.reset_info_bind_phone_layout);
//		mBindEmailLayout = (LinearLayout) findViewById(E_id.reset_info_bind_email_layout);
//		mEditextCityLayout = (LinearLayout) findViewById(E_id.reset_info_edit_city_layout);
		mGetGiftLayout = (LinearLayout) findViewById(E_id.reset_info_get_gift_layout);

		mNormalNick = (TextView) findViewById(E_id.reset_info_normal_nick);
		mNormalLine = (TextView) findViewById(E_id.reset_info_normal_line);
		mNormalSex = (TextView) findViewById(E_id.reset_info_normal_sex);
		mNormalBirth = (TextView) findViewById(E_id.reset_info_normal_birthday);
		mNormalArea = (TextView) findViewById(E_id.reset_info_normal_area);
		mNormalDetailAddress = (TextView) findViewById(E_id.reset_info_normal_detail_adress);
		mNormalPhone = (TextView) findViewById(E_id.reset_info_normal_phone);
		mBindPhone = (TextView) findViewById(E_id.reset_info_reset_phone);
		mNormalEmail = (TextView) findViewById(E_id.reset_info_normal_email);
		mBindEmail = (TextView) findViewById(E_id.reset_info_reset_email);
		mNotSetNick = (TextView) findViewById(E_id.reset_info_normal_notset_nick);
		mNotSetLine = (TextView) findViewById(E_id.reset_info_normal_notset_line);
		mNotSetSex = (TextView) findViewById(E_id.reset_info_normal_notset_sex);
		mNotSetBirth = (TextView) findViewById(E_id.reset_info_normal_notset_birth);
		mNotSetArea = (TextView) findViewById(E_id.reset_info_normal_notset_area);
		mNotSetDetailAddress = (TextView) findViewById(E_id.reset_info_normal_notset_detail_adress);
		phoneTitle = (TextView) findViewById(E_id.reset_info_phone_title);
		emailTitle = (TextView) findViewById(E_id.reset_info_email_title);
		mNormalUid = (TextView) findViewById(E_id.reset_info_normal_uid);
		mEditUid = (TextView) findViewById(E_id.reset_info_edit_uid);
		mNormalRealName = (TextView) findViewById(E_id.reset_info_normal_realname);
		mNotSetRealName = (TextView) findViewById(E_id.reset_info_normal_notset_realname);
		mNormalIdCard = (TextView) findViewById(E_id.reset_info_normal_idcard);
		mNotSetIdCard = (TextView) findViewById(E_id.reset_info_normal_notset_idcard);
		resetButton = (TextView) findViewById(E_id.reset_person_info_btn);

		mEditNick = (EditText) findViewById(E_id.reset_info_edit_nick);
		mEidtLine = (EditText) findViewById(E_id.reset_info_edit_line);
		mEidtDetailAddress = (EditText) findViewById(E_id.reset_info_edit_detail_adress);
		mEditBirth = (TextView) findViewById(E_id.reset_info_edit_birth);
		mEditArea = (TextView) findViewById(E_id.reset_info_edit_area);
		mEditRealName = (EditText) findViewById(E_id.reset_info_edit_realname);
		mEditIDCard = (EditText) findViewById(E_id.reset_info_edit_idcard);
//		mEditCity = (TextView) findViewById(E_id.reset_info_edit_city);

		mGetGiftBtn = (TextView) findViewById(E_id.reset_info_get_gift);
		mChangePwdBtn = (TextView) findViewById(E_id.reset_info_change_pwd);
//		mSetPointPwdBtn = (TextView) findViewById(E_id.reset_info_set_change_point_pwd);
//		mSetPointPwdHelpBtn = (TextView) findViewById(E_id.reset_info_set_change_point_pwd_help);
		
		sexRadio = (TextView) findViewById(E_id.sex);
//		mMale = (RadioButton) findViewById(E_id.male);
//		mFemale = (RadioButton) findViewById(E_id.female);

		mChangeAccount = (TextView) findViewById(E_id.change_account);
		
		mSetChangePointBean = ProcessDatasUtils.getPersonTextInfo(ProcessDatasUtils.TYPE_LINK_AREA, ProcessDatasUtils.TYPE_LINK_TURNINT_POINT);
		
		initListeners();
		// 設置已有數據(正常狀態的)
		setNormalInfo();
		// 設置編輯狀態的數據
		setEditInfo();

		initPhoneEmailAndGiftDescInfo();

		mPlayerAreas = IPlatApplication.get().getmPlayerAreas();
		playerAreaNames = ProcessDatasUtils.getAllPlayerArea();
		//获取可获取奖励的游戏列表
		requestWithoutDialog(createGetModuleGameListReqeust());
	}

	private void initListeners() {
		// TODO Auto-generated method stub
		//重綁手機
		mBindPhone.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View paramView) {
				// TODO Auto-generated method stub
				if(isBindPhone){//已绑定，--》重新绑定
//					IntentUtils.goWithBean(mContext, ResetReBindPhoneActivity.class, mUserInfoBean);
					IntentUtils.goWithBeanForResult(mContext, ResetReBindPhoneActivityNew.class, mUserInfoBean, new OnUpdateSecondUserListener() {
						@Override
						public void onUpdate(User userInfo) {
//							IPlatApplication.get().setUser(userInfo);
							mUserInfoBean = IPlatApplication.get().getUser();
							Log.e("yang", "onUpdate.getPhone():"+mUserInfoBean.getPhone());
							initPhoneEmailAndGiftDescInfo();
						}
					});
				}else{//未绑定
					IntentUtils.goWithBeanForResult(mContext, BindPhoneActivityNew.class, mUserInfoBean, new OnUpdateSecondUserListener() {
						@Override
						public void onUpdate(User userInfo) {
							IPlatApplication.get().setUser(userInfo);
							mUserInfoBean = userInfo;
//							checkIsFinishInfo();
							initPhoneEmailAndGiftDescInfo();
						}
					});
				}
			}
		});
		//重綁郵箱
		mBindEmail.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View paramView) {
				// TODO Auto-generated method stub
				if(isBindEmail){//已绑定，--》重新绑定
//					IntentUtils.goWithBean(mContext, ResetReBindEmailActivity.class, mUserInfoBean);
					IntentUtils.goWithBeanForResult(mContext, ResetReBindEmailActivity.class, mUserInfoBean, new OnUpdateUserListener() {
						@Override
						public void onUpdate(User userInfo) {
							IPlatApplication.get().setUser(userInfo);
							mUserInfoBean = userInfo;
							initPhoneEmailAndGiftDescInfo();
						}
					});
				}else{//未绑定
//					IntentUtils.goWithBean(mContext, BindEmailActivity.class, mUserInfoBean);
					IntentUtils.goWithBeanForResult(mContext, BindEmailActivity.class, mUserInfoBean, new OnUpdateUserListener() {
						@Override
						public void onUpdate(User userInfo) {
							IPlatApplication.get().setUser(userInfo);
							mUserInfoBean = userInfo;
							initPhoneEmailAndGiftDescInfo();
						}
					});
				}
			}
		});
//		//首次綁定手機
//		mBindPhoneLayout.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View paramView) {
//				// TODO Auto-generated method stub
//				IntentUtils.goWithBeanForResult(mContext, BindPhoneActivity.class, mUserInfoBean, new OnUpdateUserListener() {
//					@Override
//					public void onUpdate(User userInfo) {
//						IPlatApplication.get().setUser(userInfo);
//						mUserInfoBean = userInfo;
//						initPhoneEmailAndGiftDescInfo();
//					}
//				});
//			}
//		});
//		//首次綁定郵箱
//		mBindEmailLayout.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View paramView) {
//				// TODO Auto-generated method stub
//				IntentUtils.goWithBean(mContext, BindEmailActivity.class, mUserInfoBean);
//			}
//		});
		// 切换账号
		mChangeAccount.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
//				UserUtils.changeUser(mContext, new OnLoginFinishListener() {
//					@Override
//					public void loginCompleted(boolean completed) {
//						if (completed) {
//							// ((OnUpdateUserListener)
//							// IPlatApplication.get().getOnEfunListener()).onUpdate(null);
//							TrackingUtils
//									.track(TrackingUtils.ACTION_UPDATE_PERSON_INFO,
//											TrackingUtils.NAME_UPDATE_PERSON_INFO_CHANGE_ACCOUNT);
//							finish();
//						}
//					}
//				});
				UserUtils.logout(mContext);
				UserUtils.needLoginInTag(mContext, RequestCode.REQ_CHANGE_ACCOUNT, ResultCode.RST_CHANGE_ACCOUNT, new OnLoginFinishListener() {
					
					@Override
					public void loginCompleted(boolean completed) {
						// TODO Auto-generated method stub
							// ((OnUpdateUserListener)
							// IPlatApplication.get().getOnEfunListener()).onUpdate(null);
							TrackingUtils
									.track(mContext,TrackingUtils.ACTION_UPDATE_PERSON_INFO,
											TrackingUtils.NAME_UPDATE_PERSON_INFO_CHANGE_ACCOUNT);
							TrackingGoogleUtils
							.track(TrackingGoogleUtils.ACTION_UPDATE_PERSON_INFO,
									TrackingGoogleUtils.NAME_UPDATE_PERSON_INFO_CHANGE_ACCOUNT);
							finish();
//							if(UserUtils.isLogin()){
//								finish();
//							}else{
////								((FrameTabListener) mContext).onTabChange(Tab.TAB_ITEM_TAG_SUMMARY);//跳转到资讯页面
//								finish();
//							}
				}});
			}
		});
		//領取禮包
		mGetGiftBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View paramView) {
				// TODO Auto-generated method stub
				ViewUtils.createToastCommon(mContext, getString(E_string.efun_pd_reset_person_gift_get_resc), null, mGiftGamesStr, ToastType.CHOSETYPE, new OnEfunItemClickListener() {
					
					@Override
					public void onItemClick(int position) {
						// TODO Auto-generated method stub
						Log.e("efun", "點擊了確認按鈕");
						if(mUserInfoBean != null){
							requestWithDialog(createGetFinishInfoGiftReqeust(awardGameCode), getString(E_string.efun_pd_loading_msg_commend));
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
			}
		});
		//更新密碼
		mChangePwdBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View paramView) {
				// TODO Auto-generated method stub
				TrackingUtils.track(mContext,TrackingUtils.ACTION_UPDATE_PERSON_INFO, TrackingUtils.NAME_UPDATE_PERSON_INFO_CHANGE_PWD);
				TrackingGoogleUtils.track(TrackingGoogleUtils.ACTION_UPDATE_PERSON_INFO, TrackingGoogleUtils.NAME_UPDATE_PERSON_INFO_CHANGE_PWD);
				IntentUtils.goWithBeanForResult(mContext, ResetPasswordActivity.class, null, RequestCode.REQ_RET_PWD);
			}
		});
//		//設置轉點密碼
//		mSetPointPwdBtn.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View paramView) {
//				// TODO Auto-generated method stub
//				if(mSetChangePointBean != null){
//					IntentUtils.go2WithJSWeb(mContext, Web.WEB_GO_PERSON_FUNCTIONS, mSetChangePointBean);
//				}
//			}
//		});
//		//轉點密碼幫助
//		mSetPointPwdHelpBtn.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View paramView) {
//				// TODO Auto-generated method stub
//				ViewUtils.createToastCommon(mContext, getString(E_string.efun_pd_reset_change_point_resc_title), 
//						getString(E_string.efun_pd_reset_change_point_resc_content), null, ToastType.HASCONTENTTYPE, null, null).show();
//			}
//		});
//		//性别选择
//		sexRadio.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//			
//			@Override
//			public void onCheckedChanged(RadioGroup paramRadioGroup, int paramInt) {
//				// TODO Auto-generated method stub
//				if(paramInt==EfunResourceUtil.findViewIdByName(mContext, "male")){
//					mUserInfoBean.setSex(getString(E_string.efun_pd_sex_boy));
//				}else{
//					mUserInfoBean.setSex(getString(E_string.efun_pd_sex_girl));
//				}
//			}
//		});
		
		mSexPopWindow = PopUtils.createPerInfo(mContext, mParentView, new String[]{getString(E_string.efun_pd_sex_boy),getString(E_string.efun_pd_sex_girl)}, new OnEfunItemClickListener() {
			
			@Override
			public void onItemClick(int position) {
				// TODO Auto-generated method stub
				if(position == 0){
					mUserInfoBean.setSex(getString(E_string.efun_pd_sex_boy));
					sexRadio.setText(getString(E_string.efun_pd_sex_boy));
					mNormalSex.setText(getString(E_string.efun_pd_sex_boy));
				}else if(position == 1){
					mUserInfoBean.setSex(getString(E_string.efun_pd_sex_girl));
					sexRadio.setText(getString(E_string.efun_pd_sex_girl));
					mNormalSex.setText(getString(E_string.efun_pd_sex_girl));
				}
			}
		});
		
		sexRadio.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mSexPopWindow.showPopWindow(PopWindow.POP_WINDOW_CHOSE_SEX);
			}
		});
		//生日
		mEditBirth.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View paramView) {
				// TODO Auto-generated method stub
				if(!EfunStringUtil.isEmpty(mUserInfoBean.getBirthday())){
					birthStr = mUserInfoBean.getBirthday();
					calendar = TimeFormatUtil.getCalendarByInintData(birthStr);
				}else if(!birthStr.equals("")){
					calendar = TimeFormatUtil.getCalendarByInintData(birthStr);
				}else{
					calendar.set(1996, 00, 01);//默认值为1996-01-01
				}
				
				DatePickerDialog datePicker = new DatePickerDialog(mContext, EfunResourceUtil.findStyleIdByName(mContext, "dateDialog"),new OnDateSetListener() {
					
					@Override
					public void onDateSet(DatePicker view, int year, int monthOfYear,
							int dayOfMonth) {
						// TODO Auto-generated method stub
						String mothStr = "";
						String dayStr = "";
						if((monthOfYear+1) < 10){
							mothStr = "0"+ (monthOfYear+1);
						}else{
							mothStr = ""+ (monthOfYear+1);
						}
						if(dayOfMonth < 10){
							dayStr = "0"+dayOfMonth;
						}else{
							dayStr = ""+dayOfMonth;
						}
						birthStr = year+"-"+mothStr+"-"+dayStr;
						mEditBirth.setText(birthStr);
						mUserInfoBean.setBirthday(birthStr);
						EfunLogUtil.logD("efun", "birthday:"+birthStr);
					}
				}, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
				datePicker.show();
			}
		});
		
		//地区
		mEditArea.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View paramView) {
				// TODO Auto-generated method stub
				if(playerAreaNames != null){
//					ViewUtils.paramListDialog(playerAreaNames, mContext, new OnDialogSelect() {
//						
//						@Override
//						public void onSelect(int postion) {
//							// TODO Auto-generated method stub
//							mEditArea.setText(mPlayerAreas.get(postion).getText());
//							mPlayerCityBeans = ProcessDatasUtils.getAllCityInfoByPlayArea(mPlayerAreas.get(postion).getKey());
//							playerCityNames = ProcessDatasUtils.getAllCitys(mPlayerAreas.get(postion).getKey());
//							if(mPlayerCityBeans == null || mPlayerCityBeans.size() == 0){
////								mEditextCityLayout.setVisibility(View.INVISIBLE);
////								mEditCity.setClickable(false);
//							}else{
////								mEditextCityLayout.setVisibility(View.VISIBLE);
////								mEditCity.setClickable(true);
////								mEditCity.setText(mPlayerCityBeans.get(0).getText());
//								mUserInfoBean.setCity(mPlayerCityBeans.get(0).getValue());
//							}
//							mUserInfoBean.setArea(mPlayerAreas.get(postion).getValue());
//						}
//					});
					View view = dialogm();
					final MyAlertDialog dialog1 = new MyAlertDialog(ResetPerInfoActivity.this)
					.builder()
					.setTitle("选择地区")
					// .setMsg("再连续登陆15天，就可变身为QQ达人。退出QQ可能会使你现有记录归零，确定退出？")
					// .setEditText("1111111111111")
					.setView(view)
					.setNegativeButton("取消", new OnClickListener() {
						@Override
						public void onClick(View v) {

						}
					});
					dialog1.setPositiveButton("保存", new OnClickListener() {
						@Override
						public void onClick(View v) {
							mEditArea.setText(cityTxt);
//							Toast.makeText(getApplicationContext(), cityTxt, 1).show();
						}
					});
					dialog1.show();
				}
			}
		});
		
		resetButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (isSave) {// 保存按鈕
					isSave = false;
					resetButton.setText(E_string.efun_pd_edit);
					mNormalInfoLayout.setVisibility(View.VISIBLE);
					mEditInfoLayout.setVisibility(View.GONE);
					mUserInfoBean.setUsername(mEditNick.getText().toString());
					mUserInfoBean.setLine(mEidtLine.getText().toString());
					mUserInfoBean.setAddress(mEidtDetailAddress.getText().toString());
					mUserInfoBean.setRealName(mEditRealName.getText().toString());
					mUserInfoBean.setIdCard(mEditIDCard.getText().toString());
					phoneTitle.setTextColor(mContext.getResources().getColor(E_color.e_8d8d8d));
					emailTitle.setTextColor(mContext.getResources().getColor(E_color.e_8d8d8d));
					sexRadio.setTextColor(mContext.getResources().getColor(E_color.e_8c8c8c));
					mEditBirth.setTextColor(mContext.getResources().getColor(E_color.e_8c8c8c));
					mEditArea.setTextColor(mContext.getResources().getColor(E_color.e_8c8c8c));
					requst();
				} else {// 編輯按鈕
					isSave = true;
					mEditNick.setSelection(mEditNick.getText().toString().length());
					phoneTitle.setTextColor(mContext.getResources().getColor(E_color.e_272727));
					emailTitle.setTextColor(mContext.getResources().getColor(E_color.e_272727));
					sexRadio.setTextColor(mContext.getResources().getColor(E_color.e_c0c0c0));
					mEditBirth.setTextColor(mContext.getResources().getColor(E_color.e_c0c0c0));
					mEditArea.setTextColor(mContext.getResources().getColor(E_color.e_c0c0c0));
					resetButton.setText(E_string.efun_pd_save);
					mNormalInfoLayout.setVisibility(View.GONE);
					mEditInfoLayout.setVisibility(View.VISIBLE);
					TrackingUtils.track(mContext, TrackingUtils.ACTION_UPDATE_PERSON_INFO, TrackingUtils.NAME_UPDATE_PERSON_INFO_EDIT);
//					TrackingGoogleUtils.track(TrackingGoogleUtils.ACTION_UPDATE_PERSON_INFO, TrackingGoogleUtils.NAME_UPDATE_PERSON_INFO_EDIT);
				}
			}
		});
		
//		//城市
//		mEditCity.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View paramView) {
//				// TODO Auto-generated method stub
//				if(playerCityNames != null){
//					ViewUtils.paramListDialog(playerCityNames, mContext, new OnDialogSelect() {
//						
//						@Override
//						public void onSelect(int postion) {
//							// TODO Auto-generated method stub
//							mEditCity.setText(mPlayerCityBeans.get(postion).getText());
//							mUserInfoBean.setCity(mPlayerCityBeans.get(postion).getValue());
//						}
//					});
//				}
//			}
//		});
		
	}
	
	/**
	 * 检测是否已完成必填项
	 */
	private void checkIsFinishInfo(){
		if(!mUserInfoBean.getIsAuthPhone().equals("0") && !EfunStringUtil.isEmpty(mUserInfoBean.getBirthday()) && !EfunStringUtil.isEmpty(mUserInfoBean.getArea())){
			mUserInfoBean.setFinished(true);
		}else{
			mUserInfoBean.setFinished(false);
		}
	}
	
	/**
	 * 初始化手机，邮箱，完善资料信息
	 */
	private void initPhoneEmailAndGiftDescInfo(){
		// 手機或者郵箱狀態
		if (mUserInfoBean != null) {
			if (EfunStringUtil.isEmpty(mUserInfoBean.getPhone())
					&& mUserInfoBean.getIsAuthPhone().equals("0")) {// 未綁定手機
//				mNormalPhoneLayout.setVisibility(View.GONE);
//				mBindPhoneLayout.setVisibility(View.VISIBLE);
				mBindPhone.setBackgroundResource(E_drawable.efun_pd_cons_00c6ff_bg);
				mBindPhone.setTextColor(mContext.getResources().getColor(E_color.e_00c6ff));
				mBindPhone.setText(E_string.efun_pd_reset_bind_phone);
				isBindPhone = false;
				
			} else if(!EfunStringUtil.isEmpty(mUserInfoBean.getPhone())
					&& mUserInfoBean.getIsAuthPhone().equals("0")){//存在手机号码，但未绑定
				mNormalPhone.setText("+" + mUserInfoBean.getAreaCode() + " "
						+ mUserInfoBean.getPhone());
				mBindPhone.setBackgroundResource(E_drawable.efun_pd_cons_00c6ff_bg);
				mBindPhone.setTextColor(mContext.getResources().getColor(E_color.e_00c6ff));
				mBindPhone.setText(E_string.efun_pd_reset_bind_phone);
				isBindPhone = false;
			} else{
				mNormalPhone.setText("+" + mUserInfoBean.getAreaCode() + " "
						+ mUserInfoBean.getPhone());
//				mNormalPhoneLayout.setVisibility(View.VISIBLE);
//				mBindPhoneLayout.setVisibility(View.GONE);
				mBindPhone.setBackgroundResource(E_drawable.efun_pd_cons_ff9031_bg);
				mBindPhone.setTextColor(mContext.getResources().getColor(E_color.e_ff9031));
				mBindPhone.setText(E_string.efun_pd_reset_reset);
				isBindPhone = true;
			}
			if (EfunStringUtil.isEmpty(mUserInfoBean.getEmail())
					&& mUserInfoBean.getBindEmail().equals("0")) {// 未綁定郵箱
//				mNormalEmailLayout.setVisibility(View.GONE);
//				mBindEmailLayout.setVisibility(View.VISIBLE);
				mBindEmail.setBackgroundResource(E_drawable.efun_pd_cons_00c6ff_bg);
				mBindEmail.setTextColor(mContext.getResources().getColor(E_color.e_00c6ff));
				mBindEmail.setText(E_string.efun_pd_reset_bind_email);
				isBindEmail = false;
			} else if(!EfunStringUtil.isEmpty(mUserInfoBean.getEmail())
					&& mUserInfoBean.getBindEmail().equals("0")){
				mNormalEmail.setText(mUserInfoBean.getEmail());
				mBindEmail.setBackgroundResource(E_drawable.efun_pd_cons_00c6ff_bg);
				mBindEmail.setTextColor(mContext.getResources().getColor(E_color.e_00c6ff));
				mBindEmail.setText(E_string.efun_pd_reset_bind_email);
				isBindEmail = false;
			} else {
				mNormalEmail.setText(mUserInfoBean.getEmail());
//				mNormalEmailLayout.setVisibility(View.VISIBLE);
				mBindEmail.setBackgroundResource(E_drawable.efun_pd_cons_ff9031_bg);
				mBindEmail.setTextColor(mContext.getResources().getColor(E_color.e_ff9031));
				mBindEmail.setText(E_string.efun_pd_reset_reset);
				isBindEmail = true;
//				mBindEmailLayout.setVisibility(View.GONE);
			}
			birthStr = mUserInfoBean.getBirthday();
			
			if(mUserInfoBean.isFinished() && mUserInfoBean.isGetAward()){//必填项均已完成并已领取礼包
				mGetGiftLayout.setVisibility(View.GONE);
			}else if(!mUserInfoBean.isFinished()){//必填项暂未完成
				mGetGiftLayout.setVisibility(View.VISIBLE);
				mGetGiftBtn.setText(getString(E_string.efun_pd_reset_title_get_gift_edit));
				mGetGiftBtn.setClickable(false);
			}else if(mUserInfoBean.isFinished() && !mUserInfoBean.isGetAward()){//完成，未领取
				mGetGiftLayout.setVisibility(View.VISIBLE);
				mGetGiftBtn.setText(getString(E_string.efun_pd_reset_title_get_gift_normal));
				mGetGiftBtn.setClickable(true);
			}else{
				mGetGiftLayout.setVisibility(View.VISIBLE);
				mGetGiftBtn.setText(getString(E_string.efun_pd_reset_title_get_gift_edit));
				mGetGiftBtn.setClickable(false);
			}
			
		}
	}

	private void setEditInfo() {
		// TODO Auto-generated method stub
		if (mUserInfoBean != null) {
			// 昵稱
			if (!EfunStringUtil.isEmpty(mUserInfoBean.getUsername())) {
				mEditNick.setText(mUserInfoBean.getUsername());
			}
			// Line
			if (!EfunStringUtil.isEmpty(mUserInfoBean.getLine())) {
				mEidtLine.setText(mUserInfoBean.getLine());
			}
			
			//姓名
			if (!EfunStringUtil.isEmpty(mUserInfoBean.getRealName())) {
				mEditRealName.setText(mUserInfoBean.getRealName());
			}
			

			// 生日
			 if (!EfunStringUtil.isEmpty(mUserInfoBean.getBirthday())) {
				 mEditBirth.setText(mUserInfoBean.getBirthday());
				 //如果是VIP用户且存在生日数据时，不可编辑
				 if(mUserInfoBean.getIsVip().equals("0")){
					 mEditBirth.setClickable(false);
				 }else{
					 mEditBirth.setClickable(true);
				 }
				}else{
					mEditBirth.setClickable(true);
				}
			
			 //IdCard
			 if (!EfunStringUtil.isEmpty(mUserInfoBean.getIdCard())) {
					mEditIDCard.setText(mUserInfoBean.getIdCard());
					mEditIDCard.setEnabled(false);
				}
			 
			// 地區
			if (!EfunStringUtil.isEmpty(mUserInfoBean.getArea())) {
				mEditArea.setText(mUserInfoBean.getArea());
			}
			// 城市
			if (!EfunStringUtil.isEmpty(mUserInfoBean.getCity())) {
//				mEditCity.setText(mUserInfoBean.getCity());
				mEditArea.setText(mUserInfoBean.getArea()+"--"+mUserInfoBean.getCity());
			}

			// 詳細地址
			if (!EfunStringUtil.isEmpty(mUserInfoBean.getAddress())) {
				mEidtDetailAddress.setText(mUserInfoBean.getAddress());
			}
			
		}
	}

	private void setNormalInfo() {
		if (mUserInfoBean != null) {
			//UID
			if(!EfunStringUtil.isEmpty(mUserInfoBean.getUid())){
				mNormalUid.setText(mUserInfoBean.getUid());
				mEditUid.setText(mUserInfoBean.getUid());
			}
			// 昵稱
			if (!EfunStringUtil.isEmpty(mUserInfoBean.getUsername())) {
				mNormalNick.setText(mUserInfoBean.getUsername());
				mNotSetNick.setVisibility(View.GONE);
			} else {
				mNotSetNick.setVisibility(View.VISIBLE);
			}
			// Line
			if (!EfunStringUtil.isEmpty(mUserInfoBean.getLine())) {
				mNormalLine.setText(mUserInfoBean.getLine());
				mNotSetLine.setVisibility(View.GONE);
			} else {
				mNotSetLine.setVisibility(View.VISIBLE);
			}
			//姓名
			if(!EfunStringUtil.isEmpty(mUserInfoBean.getRealName())){
				mNormalRealName.setText(mUserInfoBean.getRealName());
				mNotSetRealName.setVisibility(View.GONE);
			}else{
				mNotSetRealName.setVisibility(View.VISIBLE);
			}
			
			// 性別
			mNotSetSex.setVisibility(View.GONE);
			if (mUserInfoBean.getSex().equals(
					getString(E_string.efun_pd_sex_girl))) {
				mNormalSex.setText(getString(E_string.efun_pd_sex_girl));
				sexRadio.setText(getString(E_string.efun_pd_sex_girl));
			} else if(mUserInfoBean.getSex().equals(
					getString(E_string.efun_pd_sex_boy))){
				mNormalSex.setText(getString(E_string.efun_pd_sex_boy));
				sexRadio.setText(getString(E_string.efun_pd_sex_boy));
			}else{
				mNotSetSex.setVisibility(View.VISIBLE);
			}
			
			//身份证号码
			if(!EfunStringUtil.isEmpty(mUserInfoBean.getIdCard())){
				mNormalIdCard.setText(mUserInfoBean.getIdCard());
				mNotSetIdCard.setVisibility(View.GONE);
			}else{
				mNotSetIdCard.setVisibility(View.VISIBLE);
			}
			
			
			// 生日
			if(!EfunStringUtil.isEmpty(mUserInfoBean.getBirthday())){
				mNormalBirth.setText(mUserInfoBean.getBirthday());
				mNotSetBirth.setVisibility(View.GONE);
			} else {
				mNotSetBirth.setVisibility(View.VISIBLE);
			}

			// 地區
			if (!EfunStringUtil.isEmpty(mUserInfoBean.getArea())) {
				if (!EfunStringUtil.isEmpty(mUserInfoBean.getCity())) {
					mNormalArea.setText(mUserInfoBean.getArea() + "--"
							+ mUserInfoBean.getCity());
				}else{
					mNormalArea.setText(mUserInfoBean.getArea());
				}
				mNotSetArea.setVisibility(View.GONE);
			} else {
				mNotSetArea.setVisibility(View.VISIBLE);
			}

			// 詳細地址
			if(!EfunStringUtil.isEmpty(mUserInfoBean.getAddress())){
				mNormalDetailAddress.setText(mUserInfoBean.getAddress());
				mNotSetDetailAddress.setVisibility(View.GONE);
			} else {
				mNormalDetailAddress.setText("");
				mNotSetDetailAddress.setVisibility(View.VISIBLE);
			}

		}
	}

	@Override
	public void onSuccess(int requestType, BaseResponseBean baseResponse) {
		super.onSuccess(requestType, baseResponse);
		if (requestType == IPlatformRequest.REQ_USER_UPDATE_INFO) {
			UserUpdateInfoResponse response = (UserUpdateInfoResponse) baseResponse;
			UserUpdateBean bean = response.getUserUpdateBean();
			if (bean.getCode().equals(HttpParam.RESULT_1000)) {
				ToastUtils.toast(mContext, bean.getMsg());
				if(bean.getUser() != null){
					updateUser(bean.getUser());
					IPlatApplication.get().setUser(mUserInfoBean);
				}else{
					IPlatApplication.get().setUser(mUserInfoBean);
				}
//				checkIsFinishInfo();
				setNormalInfo();
				setEditInfo();
				initPhoneEmailAndGiftDescInfo();
			}else{
				ToastUtils.toast(mContext, bean.getMsg());
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
		
		if (requestType==IPlatformRequest.REQ_USER_GET_GIFT_SERIAL){
			GiftKnockResponse response = (GiftKnockResponse)baseResponse;
			GiftKnockBean bean = response.getGiftKnockBean();
			String code = bean.getCode();
			if(code.equals(HttpParam.RESULT_1000)){//领取成功
				mUserInfoBean.setFinished(true);
				mUserInfoBean.setGetAward(true);
				IPlatApplication.get().setUser(mUserInfoBean);
				initPhoneEmailAndGiftDescInfo();
			}else if(code.equals(HttpParam.RESULT_1011)){//已经领奖
				mUserInfoBean.setFinished(true);
				mUserInfoBean.setGetAward(true);
				IPlatApplication.get().setUser(mUserInfoBean);
				initPhoneEmailAndGiftDescInfo();
			}
			ToastUtils.toast(mContext, bean.getMessage());
		}
	}
	
	private void updateUser(User mUser){
		mUserInfoBean.setUid(Long.valueOf(mUser.getUid()));
		mUserInfoBean.setUsername(mUser.getUsername());
		mUserInfoBean.setLine(mUser.getLine());
		mUserInfoBean.setRealName(mUser.getRealName());
		mUserInfoBean.setSex(mUser.getSex());
		mUserInfoBean.setIdCard(mUser.getIdCard());
		mUserInfoBean.setBirthday(mUser.getBirthday());
		mUserInfoBean.setArea(mUser.getArea());
		mUserInfoBean.setCity(mUser.getCity());
		mUserInfoBean.setAddress(mUser.getAddress());
		mUserInfoBean.setPhone(mUser.getPhone());
		mUserInfoBean.setIsAuthPhone(mUser.getIsAuthPhone());
		mUserInfoBean.setEmail(mUser.getEmail());
		mUserInfoBean.setIsAuthEmail(mUser.getIsAuthEmail());
		mUserInfoBean.setBindEmail(mUser.getIsAuthEmail());
		mUserInfoBean.setFinished(mUser.isFinished());
		mUserInfoBean.setGetAward(mUser.isGetAward());
	}

	@Override
	public ViewGroup[] needShowLoading() {
		return null;
	}

	public void requst() {
		requestWithDialog(needRequestDataBean(),
				getString(E_string.efun_pd_loading_msg_commend));
	}

	@Override
	public int LayoutId() {
		return E_layout.efun_pd_persion_reset_info_new;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Log.e("efun", "ResetPerInfo:requestCode:"+requestCode);
		if(requestCode == RequestCode.REQ_RET_PWD){//修改密碼之后的返回
			if(resultCode == ResultCode.RST_LOGIN_AFTER_RET_PWD){
				finish();
			}else if(!UserUtils.isLogin()){
				finish();
			}
		}
		if(requestCode == RequestCode.REQ_CHANGE_ACCOUNT){//切换账号
			finish();
//			if(UserUtils.isLogin()){
//				finish();
//			}else{
////				((FrameTabListener) mContext).onTabChange(Tab.TAB_ITEM_TAG_SUMMARY);//跳转到资讯页面
//				finish();
//			}
		}
	}

	@Override
	public void initTitle(TitleView titleView) {
		this.titleView = titleView;
		isSave = false;
//		titleView.setTitleRightTextRes(E_string.efun_pd_edit);
		titleView.setTitleRightStatus(View.GONE);
		titleView.setTitleCenterRes(E_string.efun_pd_reset_per_info, false);
		titleView.setTitleLeftRes(E_drawable.efun_pd_back_white_normal);
		titleView.setTitleBarBackgroundRes(E_color.e_7fb9f0);
		titleView.setTitleCenterTextColor(getResources().getColor(E_color.e_ffffff));
	}
	
	public BaseRequestBean[] createGetModuleGameListReqeust(){
		GameListOfModuleRequest request = new GameListOfModuleRequest(
				HttpParam.PLATFORM_AREA, 
				HttpParam.COMPINFOGIFT, 
				HttpParam.PLATFORM, 
				AppUtils.getAppVersionName(this),
				HttpParam.APP, 
				HttpParam.LANGUAGE);
		request.setReqType(IPlatformRequest.REQ_USER_GIFT_GAMES_LIST);
		return new BaseRequestBean[]{request};
	}
	
	public BaseRequestBean[] createGetFinishInfoGiftReqeust(String gameCode){
		PersonGetAwardGiftRequest request = new PersonGetAwardGiftRequest(
				mUserInfoBean.getUid(), 
				HttpParam.PLATFORM_AREA, 
				HttpParam.REWARD_GIFT, 
				HttpParam.COMPINFO, 
				gameCode, 
				HttpParam.APP, 
				HttpParam.PLATFORM, 
				AppUtils.getAppVersionName(this), 
				mUserInfoBean.getSign(), 
				mUserInfoBean.getTimestamp(), 
				HttpParam.LANGUAGE);
		request.setReqType(IPlatformRequest.REQ_USER_GET_GIFT_SERIAL);
		return new BaseRequestBean[]{request};
	}
	
	private String [][] progressCitysDatas(){
		String cities[][] = null;
		if(playerAreaNames == null || playerAreaNames.length == 0){
		}else{
			cities = new String[playerAreaNames.length][];
			for(int i = 0; i < playerAreaNames.length; i++){
				playerCityNames = ProcessDatasUtils.getAllCitys(mPlayerAreas.get(i).getKey());
				cities[i] = playerCityNames;
			}
			return cities;
			
		}
		
		return null;
	} 
	
	private View dialogm() {
		View contentView = LayoutInflater.from(this).inflate(
				E_layout.efun_pd_wheelcity_cities_layout, null);
		final WheelView country = (WheelView) contentView
				.findViewById(E_id.wheelcity_country);
		country.setVisibleItems(3);
		country.setViewAdapter(new CountryAdapter(this));

		final String cities[][] = progressCitysDatas();
		final WheelView city = (WheelView) contentView
				.findViewById(E_id.wheelcity_city);
		city.setVisibleItems(0);

		country.addChangingListener(new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				updateCities(city, cities, newValue);
				mPlayerCityBeans = ProcessDatasUtils.getAllCityInfoByPlayArea(mPlayerAreas.get(country.getCurrentItem()).getKey());
				playerCityNames = ProcessDatasUtils.getAllCitys(mPlayerAreas.get(country.getCurrentItem()).getKey());
				
				
				if(mPlayerCityBeans == null || mPlayerCityBeans.size() == 0){
					cityTxt = playerAreaNames[country.getCurrentItem()];
					mUserInfoBean.setCity("");
				}else{
					cityTxt = playerAreaNames[country.getCurrentItem()]
							+ "--"
							+ playerCityNames[city
							                  .getCurrentItem()];
					mUserInfoBean.setCity(mPlayerCityBeans.get(city.getCurrentItem()).getValue());
				}
				mUserInfoBean.setArea(mPlayerAreas.get(country.getCurrentItem()).getValue());
				
			}
		});

		city.addChangingListener(new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				if(mPlayerCityBeans == null || mPlayerCityBeans.size() == 0){
					
				}else{
					
						cityTxt = playerAreaNames[country.getCurrentItem()]
								+ "--"
								+ playerCityNames[city
								                  .getCurrentItem()];
				
					
				}
			}
		});
		
//		if(EfunStringUtil.isEmpty(mEditArea.getText().toString())){
//			mPlayerCityBeans = ProcessDatasUtils.getAllCityInfoByPlayArea(mPlayerAreas.get(0).getKey());
//			playerCityNames = ProcessDatasUtils.getAllCitys(mPlayerAreas.get(0).getKey());
//			country.setCurrentItem(0);
//			cityTxt = playerAreaNames[country.getCurrentItem()];
//			mUserInfoBean.setArea(mPlayerAreas.get(country.getCurrentItem()).getValue());
//			mUserInfoBean.setCity("");
//			if(city != null){
//				city.setCurrentItem(0);
//				cityTxt = playerAreaNames[country.getCurrentItem()]
//						+ "--"
//						+ playerCityNames[city
//						                  .getCurrentItem()];
//				mUserInfoBean.setCity(mPlayerCityBeans.get(city.getCurrentItem()).getValue());
//			}
//		}else{
//			country.setCurrentItem(0);
//			if(city != null){
//				city.setCurrentItem(0);
//			}
//		}
		country.setCurrentItem(1);
		if(city != null){
			city.setCurrentItem(0);
		}
		
		return contentView;
	}
	
	/**
	 * Updates the city wheel
	 */
	private void updateCities(WheelView city, String cities[][], int index) {
		ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<String>(this,
				cities[index]);
		adapter.setTextSize(18);
		city.setViewAdapter(adapter);
		if (city != null) {
			city.setCurrentItem(0);
		}
	}
	
	/**
	 * Adapter for countries
	 */
	private class CountryAdapter extends AbstractWheelTextAdapter {
		// Countries names
		private String countries[] = playerAreaNames;

		/**
		 * Constructor
		 */
		protected CountryAdapter(Context context) {
			super(context, E_layout.efun_pd_wheelcity_country_layout, NO_RESOURCE);
			setItemTextResource(E_id.wheelcity_country_name);
		}

		@Override
		public View getItem(int index, View cachedView, ViewGroup parent) {
			View view = super.getItem(index, cachedView, parent);
			return view;
		}

		@Override
		public int getItemsCount() {
			return countries.length;
		}

		@Override
		protected CharSequence getItemText(int index) {
			return countries[index];
		}
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(IPlatApplication.get().isUserHasChange()){
			IPlatApplication.get().setUserHasChange(false);
			mUserInfoBean = IPlatApplication.get().getUser();
//			checkIsFinishInfo();
			initPhoneEmailAndGiftDescInfo();
		}
		
	}
	
	
}
