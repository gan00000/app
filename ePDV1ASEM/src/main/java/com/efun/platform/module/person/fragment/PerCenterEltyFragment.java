package com.efun.platform.module.person.fragment;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.efun.core.tools.EfunLogUtil;
import com.efun.core.tools.EfunResourceUtil;
import com.efun.core.tools.EfunStringUtil;
import com.efun.platform.AndroidScape.E_array;
import com.efun.platform.AndroidScape.E_dimens;
import com.efun.platform.AndroidScape.E_drawable;
import com.efun.platform.AndroidScape.E_id;
import com.efun.platform.AndroidScape.E_layout;
import com.efun.platform.AndroidScape.E_string;
import com.efun.platform.IPlatApplication;
import com.efun.platform.http.dao.impl.IPlatformRequest;
import com.efun.platform.http.request.bean.BaseRequestBean;
import com.efun.platform.http.request.bean.PersonRefreshHeadIconRequest;
import com.efun.platform.http.request.bean.UserUpdateHeaderRequest;
import com.efun.platform.http.request.bean.UserUpdateInfoRequest;
import com.efun.platform.http.response.bean.BaseResponseBean;
import com.efun.platform.http.response.bean.PersonRefreshHeadIconResponse;
import com.efun.platform.http.response.bean.UserUpdateHeaderResponse;
import com.efun.platform.http.response.bean.UserUpdateInfoResponse;
import com.efun.platform.image.ImageManager;
import com.efun.platform.image.core.DisplayImageOptions;
import com.efun.platform.module.PopWindow;
import com.efun.platform.module.account.bean.ResultBean;
import com.efun.platform.module.account.bean.User;
import com.efun.platform.module.base.FixedFragment;
import com.efun.platform.module.base.impl.OnEfunEditTextItemClickListener;
import com.efun.platform.module.base.impl.OnEfunItemClickListener;
import com.efun.platform.module.person.bean.UpLoadHeaderImgBean;
import com.efun.platform.module.person.bean.UserUpdateBean;
import com.efun.platform.module.settting.activity.SettingActivity;
import com.efun.platform.module.utils.AppUtils;
import com.efun.platform.module.utils.IntentUtils;
import com.efun.platform.module.utils.PopUtils;
import com.efun.platform.module.utils.ToastUtils;
import com.efun.platform.module.utils.ViewUtils;
import com.efun.platform.utils.Const.HttpParam;
import com.efun.platform.utils.Const.LoginPlatform;
import com.efun.platform.utils.Const.RequestCode;
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
	 * 设置,头像，更改头像,vip标识
	 */
	private ImageView setingBtn, headIcon, changeHeadIcon;
	/**
	 * 妮称,帐号,用户id
	 */
	private TextView nickName, accountName, userId;
	private ImageView changeNickName;
	
	/**
	 * 用户
	 */
	private User mUser;

	private boolean loadFirstFlag = false;// 第一次加載頁面的標識

	private PopWindow mPopHeader;

	private Bitmap newHeaderBitmap;
	private String encodeFile;
	private String fileName;
	private DisplayImageOptions options;
	private String nickNameStr;
	
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
		setingBtn = (ImageView) childView.findViewById(E_id.right_btn);
		headIcon = (ImageView) childView.findViewById(E_id.head_icon);
		changeHeadIcon = (ImageView) childView
				.findViewById(E_id.head_change_icon);
		nickName = (TextView) childView.findViewById(E_id.person_nickName);
		accountName = (TextView) childView
				.findViewById(E_id.person_accountName);
		changeNickName = (ImageView) childView.findViewById(E_id.change_nickName);
		userId = (TextView) childView.findViewById(E_id.person_accountId);
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
		return E_layout.efun_pd_persion_person;
	}

	@Override
	public void onTurn() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSuccess(int requestType, BaseResponseBean baseResponse) {

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
		
		if (requestType == IPlatformRequest.REQ_USER_UPDATE_INFO) {
			UserUpdateInfoResponse response = (UserUpdateInfoResponse) baseResponse;
			UserUpdateBean bean = response.getUserUpdateBean();
			if (bean.getCode().equals(HttpParam.RESULT_1000)) {
				ToastUtils.toast(getContext(), bean.getMsg());
				if(bean.getUser() != null){
					mUser.setUsername(bean.getUser().getUsername());
					IPlatApplication.get().setUser(mUser);
				}else{
					if(!EfunStringUtil.isEmpty(nickNameStr)){
						mUser.setUsername(nickNameStr);
						IPlatApplication.get().setUser(mUser);
					}
				}
				nickName.setText(mUser.getUsername());
			}else{
				ToastUtils.toast(getContext(), bean.getMsg());
			}
		}
		
	}

	@Override
	public void initDatas() {
		// TODO Auto-generated method stub
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
	}

	@Override
	public void initListeners() {

		// 设置
		setingBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				Intent it = new Intent(getActivity(), SettingActivity.class);
//				getActivity().startActivity(it);
				IntentUtils.goWithBeanForResult(getActivity(), SettingActivity.class, mUser, RequestCode.REQ_SETTING);
			}
		});

		// 更改头像
		changeHeadIcon.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mPopHeader.showPopWindow(PopWindow.POP_WINDOW_CHOSE_PIC);
			}
		});
		changeNickName.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ViewUtils.createToastChangeNickName(getActivity(), mUser.getUsername(), new OnEfunEditTextItemClickListener() {

					@Override
					public void onItemClick(int position, String msg) {
						// TODO Auto-generated method stub
						switch (position) {
						case 0://确认
							if(!EfunStringUtil.isEmpty(msg)){
								nickNameStr = msg;
								requestWithDialog(createChangeNickName(msg),
										getString(E_string.efun_pd_loading_msg_commend));
							}
							break;

						case 1://取消
							
							break;
						}
					}
					
				});
			}
		});
	}

	public void displayUserInfo() {
		Log.i("efun", "displayUserInfo");
		childView.setVisibility(View.INVISIBLE);
		mUser = IPlatApplication.get().getUser();
		if (mUser == null) {
		} else {
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
						.setText(IPlatApplication.get().getPfAName());
			} else if (IPlatApplication.get().getPfLType()
					.equals(LoginPlatform.GOOGLE)) {
				accountName
						.setText(getString(E_string.efun_pd_hint_account_by_google));
			} else if (IPlatApplication.get().getPfLType()
					.equals(LoginPlatform.FACEBOOK)) {
				accountName
						.setText(getString(E_string.efun_pd_hint_account_by_facebook));
			} else if (IPlatApplication.get().getPfLType()
					.equals(LoginPlatform.BAHAMUT)) {
				accountName
						.setText(getString(E_string.efun_pd_hint_account_by_bahamut));
			} else if(IPlatApplication.get().getPfLType()
					.equals(LoginPlatform.TWITTER)){
				accountName
				.setText(getString(E_string.efun_pd_hint_account_by_twitter));
			}

			Log.i("yang",
					"mUser.getExperienceValue():" + mUser.getExperienceValue());
			
			// 用戶id
			userId.setText(mUser.getUid());
//			if (!TextUtils.isEmpty(mUser.getIsSendGold())
//					&& mUser.getIsSendGold().equals("1")) {
//				if (mUser.getGotExp() > 0) {
//					ToastUtils.toast(getActivity(), childView, -1, " + "
//							+ mUser.getGotExp()
//							+ getString(E_string.efun_pd_exp),
//							Toast.LENGTH_SHORT, 0, 400);
//				}
//				IPlatApplication.get().getUser().setIsSendGold("0");
//			}
			
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
	
	private BaseRequestBean[] createChangeNickName(String userName) {
		UserUpdateInfoRequest infoRequest = new UserUpdateInfoRequest();
		if (IPlatApplication.get().getUser() != null) {
			infoRequest.setSign(IPlatApplication.get().getUser().getSign());
			infoRequest.setTimestamp(IPlatApplication.get().getUser()
					.getTimestamp());
			infoRequest.setUid(IPlatApplication.get().getUser().getUid());
		}
		infoRequest.setUsername(userName);
		infoRequest.setLine("");
		infoRequest.setSex("");
		infoRequest.setBirthday("");
		infoRequest.setArea("");
		infoRequest.setCity("");
		infoRequest.setAddress("");
		infoRequest.setPlatform(HttpParam.PLATFORM_AREA);
		infoRequest.setFromType(HttpParam.APP);
		infoRequest.setVersion(HttpParam.PLATFORM);
		infoRequest.setPackageVersion(AppUtils.getAppVersionName(getContext()));
		infoRequest.setLanguage(HttpParam.LANGUAGE);
		infoRequest.setRealName("");
		infoRequest.setIdCard("");
		
		infoRequest.setReqType(IPlatformRequest.REQ_USER_UPDATE_INFO);
		return new BaseRequestBean[] { infoRequest };
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
				requestWithDialog(createUpLoadImg(), EfunResourceUtil.findStringByName(getContext(), "efun_pd_upload_data"));
			}
		}
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

}
