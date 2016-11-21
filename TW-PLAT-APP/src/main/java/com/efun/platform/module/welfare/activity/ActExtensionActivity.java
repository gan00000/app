package com.efun.platform.module.welfare.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.efun.platform.AndroidScape.E_color;
import com.efun.platform.AndroidScape.E_drawable;
import com.efun.platform.AndroidScape.E_id;
import com.efun.platform.AndroidScape.E_layout;
import com.efun.platform.AndroidScape.E_string;
import com.efun.platform.AndroidScape.E_style;
import com.efun.platform.IPlatApplication;
import com.efun.platform.http.dao.impl.IPlatformRequest;
import com.efun.platform.http.request.bean.ActivityExtensionDownloadRequest;
import com.efun.platform.http.request.bean.ActivityExtensionGiftRequest;
import com.efun.platform.http.request.bean.BaseRequestBean;
import com.efun.platform.http.request.bean.GiftSelfStatusRequest;
import com.efun.platform.http.response.bean.ActivityExtensionDownloadResponse;
import com.efun.platform.http.response.bean.ActivityExtensionGiftResponse;
import com.efun.platform.http.response.bean.BaseResponseBean;
import com.efun.platform.http.response.bean.GiftSelfStatusResponse;
import com.efun.platform.image.ImageManager;
import com.efun.platform.module.PopWindow;
import com.efun.platform.module.account.bean.User;
import com.efun.platform.module.base.FixedActivity;
import com.efun.platform.module.base.impl.OnEfunItemClickListener;
import com.efun.platform.module.base.impl.OnLoginFinishListener;
import com.efun.platform.module.game.bean.GameItemBean;
import com.efun.platform.module.utils.AppUtils;
import com.efun.platform.module.utils.PopUtils;
import com.efun.platform.module.utils.ToastUtils;
import com.efun.platform.module.utils.TrackingGoogleUtils;
import com.efun.platform.module.utils.TrackingUtils;
import com.efun.platform.module.utils.UserUtils;
import com.efun.platform.module.utils.ViewUtils;
import com.efun.platform.module.welfare.bean.ActExtensionBean;
import com.efun.platform.module.welfare.bean.ActExtensionDownloadBean;
import com.efun.platform.module.welfare.bean.ActExtensionGiftBean;
import com.efun.platform.module.welfare.bean.ActExtensionGiftGetBean;
import com.efun.platform.module.welfare.bean.ActExtensionTaskBean;
import com.efun.platform.module.welfare.bean.GiftSelfStatusBean;
import com.efun.platform.module.welfare.view.TaskContainer;
import com.efun.platform.utils.Const;
import com.efun.platform.utils.Const.HttpParam;
import com.efun.platform.widget.TitleView;
import com.efun.platform.widget.list.XListView;
/**
 * 领取点卡奖励
 * @author Jesse
 * @author itxuxxey 20150618修整
 */
public class ActExtensionActivity extends FixedActivity{
	/**
	 * 任务未完成
	 */
	private final String GIFT_GET_UNCOMPLETED = "0";
	/**
	 * 任务已领取
	 */
	private final String GIFT_GET_GETTED = "2";
	
	private ActExtensionBean bean = null;
	
	private HeaderViewHolder mHeaderViewHolder;
	private FooterViewHolder mFooterViewHolder;
	private BodyViewHolder mBodyViewHolder;
	private PopWindow mAwardsPopWindow;
	private XListView mListView;
	private View mFooterView;
	private User mUser;
	private LinearLayout childView;
	private TitleView mTitleView;
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
		
		if(bundle!=null){
			bean = (ActExtensionBean) bundle.getSerializable(Const.BEAN_KEY);
		}
		
		childView = (LinearLayout) findViewById(E_id.contaier_linear_1);
		View view = ViewUtils.createView(mContext, E_layout.efun_pd_welfare_act_extension_content);
		mListView = (XListView)findViewById(E_id.list);
		mListView.setPullRefreshEnable(false);
		mListView.setPullLoadEnable(false);
		mListView.addHeaderView(view);
		mListView.setAdapter(null);
		mUser = IPlatApplication.get().getUser();
		initHeader(view);
		initBody(view);
		initFooter();
		initViewData();
		if(bean != null && bean.getGiftsLastCount() == 0){
			mFooterView.setBackgroundResource(E_color.e_8e8e8e);
			mFooterViewHolder.mText.setText(E_string.efun_pd_all_awards_has_get);
			mFooterView.setEnabled(false);
		}
	}
	
	@Override
	public ViewGroup[] needShowLoading() {
		return new ViewGroup[]{(ViewGroup)childView};
	}
	@Override
	public int LayoutId() {
		return E_layout.efun_pd_welfare_act_extension;
	}
	
	
	private BaseRequestBean[] createDownloadRequest(){
		ActivityExtensionDownloadRequest request = new ActivityExtensionDownloadRequest(
				mContext, mGameItemBean.getGameCode(), IPlatApplication.get().getUser().getUid());
		if(IPlatApplication.get().getUser()!=null){
			request.setSign(IPlatApplication.get().getUser().getSign());
			request.setTimestamp(IPlatApplication.get().getUser().getTimestamp());
		}
		request.setReqType(IPlatformRequest.REQ_ACT_EXTENSION_DOWNLOAD);
		return new BaseRequestBean[]{request};
	}
	
	private void queryGiftSelfStatus(){
		if(IPlatApplication.get().getUser()!=null && !IPlatApplication.get().isNewStatusOfGiftSelf()){
			GiftSelfStatusRequest request = new GiftSelfStatusRequest(
					IPlatApplication.get().getUser().getUid(), 
					HttpParam.GIFT_STATUS_QUERY);
			request.setSign(IPlatApplication.get().getUser().getSign());
			request.setTimestamp(IPlatApplication.get().getUser().getTimestamp());
			request.setReqType(IPlatformRequest.REQ_GIFT_SELF_STATUS);
			requestWithoutDialog(new BaseRequestBean[]{request});
		}
	}
	
	private GameItemBean mGameItemBean;
	@Override
	public void onSuccess(int requestType, BaseResponseBean baseResponse) {
		super.onSuccess(requestType,baseResponse);
		if(requestType==IPlatformRequest.REQ_ACT_EXTENSION_DOWNLOAD){
			ActivityExtensionDownloadResponse response = (ActivityExtensionDownloadResponse)baseResponse;
			ActExtensionDownloadBean bean = response.getActExtensionDownloadBean();
			if(bean.getCode().equals(HttpParam.RESULT_1000) && !TextUtils.isEmpty(bean.getParams())){
				String url = null;
				if(isTWGame()){
					url = mGameItemBean.getAndroidDownload();
				}else{
					url = mGameItemBean.getHkAndroidDownloadURL();
				}
				if(url.contains("referrer")){
					url = url + bean.getParams();
				}else{
					url = url + "&referrer=utm_source%3D%2520%26utm_medium%3D%2520" + bean.getParams();
				}
				if(url != null){
					AppUtils.download(mContext, url);
				}
			}else{
				ToastUtils.toast(mContext, bean.getMessage());
			}
		}else if(requestType==IPlatformRequest.REQ_ACT_EXTENSION_GIFT){
			ActivityExtensionGiftResponse response  = (ActivityExtensionGiftResponse)baseResponse;
			ActExtensionGiftGetBean bean = response.getActExtensionGiftGetBean();
			if(bean.getCode().equals(HttpParam.RESULT_1000)){
				mFooterView.performClick();
				mFooterView.setBackgroundResource(E_color.e_8e8e8e);
				mFooterView.setEnabled(false);
				queryGiftSelfStatus();
			}
			ToastUtils.toast(mContext, bean.getMessage(),Toast.LENGTH_LONG);
		}else if(requestType==IPlatformRequest.REQ_GIFT_SELF_STATUS){
			GiftSelfStatusResponse response = (GiftSelfStatusResponse)baseResponse;
			GiftSelfStatusBean mGiftSelfStatusBean = response.getGiftSelfStatusBean();
			if(mGiftSelfStatusBean.getCode().equals(HttpParam.RESULT_1000)){
				IPlatApplication.get().setNewStatusOfGiftSelf(true);
				mTitleView.setTitleRightPointStatus(View.VISIBLE);
			}
		}
	}
	
	private boolean isTWGame(){
		if(bean.getGameCode().equals(bean.getGameBean().getGameCode())){
			return true;
		}else{
			return false;
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		if(IPlatApplication.get().isNewStatusOfGiftSelf()){
			mTitleView.setTitleRightPointStatus(View.VISIBLE);
		}else{
			mTitleView.setTitleRightPointStatus(View.GONE);
		}
	}
	
	/**
	 * 初始化页面头部
	 */
	private void initHeader(View convertView){
		mHeaderViewHolder = new HeaderViewHolder();
		mHeaderViewHolder.mIcon = (ImageView) convertView.findViewById(E_id.item_icon);
		mHeaderViewHolder.mTitle = (TextView) convertView.findViewById(E_id.item_title);
		mHeaderViewHolder.mZanNum = (TextView) convertView.findViewById(E_id.item_zan_counts);
		mHeaderViewHolder.mButton = (TextView) convertView.findViewById(E_id.get_task_icon);
	}
	private void initBody(View convertView){
		mBodyViewHolder = new BodyViewHolder();
		mBodyViewHolder.mRuleIntro= (TextView) convertView.findViewById(E_id.task_rule_text);
		mBodyViewHolder.mTaskLayout=(TaskContainer) convertView.findViewById(E_id.taskContainer);
		mBodyViewHolder.mTaskLayout.setItemLayout(E_layout.efun_pd_welfare_act_extension_task_list_item);
		
	}
	/**
	 * 初始化页面底部
	 */
	private void initFooter(){
		mFooterView = findViewById(E_id.layout_2);
		mFooterViewHolder = new FooterViewHolder();
		mFooterViewHolder.mIcon = (ImageView) mFooterView.findViewById(E_id.item_icon);
		mFooterViewHolder.mText = (TextView) mFooterView.findViewById(E_id.item_text);
		mFooterViewHolder.mIcon.setVisibility(View.GONE);
		mFooterViewHolder.mText.setText(E_string.efun_pd_get_awards);
		mFooterView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				TrackingUtils.track(ActExtensionActivity.this,TrackingUtils.ACTION_EXTENSION, TrackingUtils.NAME_WELFARE_EXTENSION_GET_REWARD);
				TrackingGoogleUtils.track(TrackingGoogleUtils.ACTION_EXTENSION, TrackingGoogleUtils.NAME_WELFARE_EXTENSION_GET_REWARD);
				if(mAwardsPopWindow!=null){
					mAwardsPopWindow.showPopWindow(PopWindow.POP_WINDOW_CHOSE_AWARDS);
				}
			}
		});
	}
	
	private boolean checkAllTask(ArrayList<ActExtensionTaskBean> tasks){
		for(int i = 0 ; i < tasks.size(); i++){
			if(!tasks.get(i).getCurrentState().equals("2")){
				return false;
			}
		}
		return true;
	}
	
	private void initViewData() {
		// TODO Auto-generated method stub
		if(bean != null){
//			if(mActExtensionBean.getGiftsLastCount() == 0){
//				mHeaderViewHolder.mGiftRobbled.setVisibility(View.VISIBLE);
//			}else{
//				mHeaderViewHolder.mGiftRobbled.setVisibility(View.GONE);
//			}
			mGameItemBean = bean.getGameBean();
			ImageManager.displayImage(mGameItemBean.getSmallpic(), mHeaderViewHolder.mIcon,ImageManager.IMAGE_SQUARE);
			mHeaderViewHolder.mTitle.setText(mGameItemBean.getGameName());
			
			mHeaderViewHolder.mZanNum.setText(bean.getGameBean().getLike()+"");
			
			mHeaderViewHolder.mButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					String packageNameStr = null;
					if(isTWGame()){
						packageNameStr = bean.getGameBean().getPackageName();	
					}else{
						packageNameStr = bean.getGameBean().getHkPackageName();
					}
					if(packageNameStr != null){
						if(!bean.getArrayOfTask().get(0).getCurrentState().equals("2")){//下載
							TrackingUtils.track(ActExtensionActivity.this,TrackingUtils.ACTION_EXTENSION, TrackingUtils.NAME_WELFARE_EXTENSION_GAME_DOWNLOAD+"_"+mGameItemBean.getGameCode());
							TrackingGoogleUtils.track(TrackingGoogleUtils.ACTION_EXTENSION, mGameItemBean.getGameName()+TrackingGoogleUtils.NAME_WELFARE_EXTENSION_GAME_DOWNLOAD);
							if(AppUtils.isGooglePayInstalled(mContext)){
								requestWithDialog(createDownloadRequest(), getString(E_string.efun_pd_loading_msg_commend));
							}else{
								ToastUtils.toast(mContext, getString(E_string.efun_pd_uninstalled_google_play));
							}
						}else{//打開游戲
							mHeaderViewHolder.mButton.setText(E_string.efun_pd_continue_task);
							String packageName = null;
							if(isTWGame()){
								packageName = bean.getGameBean().getPackageName();	
							}else{
								packageName = bean.getGameBean().getHkPackageName();
							}
							if(packageName != null){
								AppUtils.startApp(mContext, packageName);
							}
							
						}
					}else{
						TrackingUtils.track(ActExtensionActivity.this,TrackingUtils.ACTION_EXTENSION, TrackingUtils.NAME_WELFARE_EXTENSION_GAME_DOWNLOAD+mGameItemBean.getGameCode());
						TrackingGoogleUtils.track(TrackingGoogleUtils.ACTION_EXTENSION, mGameItemBean.getGameName()+TrackingGoogleUtils.NAME_WELFARE_EXTENSION_GAME_DOWNLOAD);
						if(AppUtils.isGooglePayInstalled(mContext)){
							requestWithDialog(createDownloadRequest(), getString(E_string.efun_pd_loading_msg_commend));
						}else{
							ToastUtils.toast(mContext, getString(E_string.efun_pd_uninstalled_google_play));
						}
					}
				}
			});
			
			if(bean.getCurrentState().equals("2") || bean.getGiftsLastCount() == 0 || checkAllTask(bean.getArrayOfTask())){
				mHeaderViewHolder.mButton.setClickable(false);
			}
			
			if(!checkAllTask(bean.getArrayOfTask()) && bean.getArrayOfTask().get(0).getCurrentState().equals("2")){
				mHeaderViewHolder.mButton.setText(E_string.efun_pd_continue_task);
			}
			
			if(checkAllTask(bean.getArrayOfTask()) && !bean.getCurrentState().equals("2")){
				mHeaderViewHolder.mButton.setBackgroundResource(E_drawable.efun_pd_welfare_grey_round_finish_icon);
				mHeaderViewHolder.mButton.setText(E_string.efun_pd_finish_task);
			}
			
			if(bean.getCurrentState().equals("2")){
				mHeaderViewHolder.mButton.setBackgroundResource(E_drawable.efun_pd_welfare_grey_round_finish_icon);
				mHeaderViewHolder.mButton.setText(E_string.efun_pd_has_get_gift);
			}
			
			mBodyViewHolder.mTaskLayout.loadedData(bean.getArrayOfTask());
			if(mAwardsPopWindow==null){
				if(bean.getArrayOfGift().size()==0){
					ToastUtils.toast(mContext, getString(E_string.efun_pd_toast_empty_gifts));
				}else{
					if(bean.getCurrentState().equals(GIFT_GET_GETTED)){
						mFooterViewHolder.mText.setText(E_string.efun_pd_gift_getted);
						mFooterView.setBackgroundResource(E_color.e_8e8e8e);
						mFooterView.setEnabled(false);
					}
					mFooterView.setVisibility(View.VISIBLE);
					mAwardsPopWindow = PopUtils.createChoseAwards(mContext, 
							bean.getArrayOfGift(),
							mFooterView, new OnEfunItemClickListener() {
								@Override
								public void onItemClick(int position) {
									if(bean.getCurrentState().equals(GIFT_GET_UNCOMPLETED)){
										ViewUtils.createToast(mContext, getString(E_string.efun_pd_gift_limit),E_layout.efun_pd_toast,E_style.DL_Dialog);
									}else{
										requestWithDialog(createRequest(bean.getArrayOfGift().get(position)),
												getString(E_string.efun_pd_loading_msg_commend));
									}
								}
							});
				}
			}
			mBodyViewHolder.mRuleIntro.setText(bean.getContext());
			queryGiftSelfStatus();
		}
	}
	
	/**
	 * 头部
	 */
	private static class HeaderViewHolder {
		public TextView mTitle, mZanNum,mButton;
		public ImageView mIcon;
	}
	private static class BodyViewHolder{
		public TextView mRuleIntro;
		public TaskContainer mTaskLayout;
	}
	/**
	 * 脚部
	 */
	private static class FooterViewHolder {
		public TextView  mText;
		public ImageView mIcon;
	}
	
	private BaseRequestBean[] createRequest(ActExtensionGiftBean gift){
		ActivityExtensionGiftRequest request = new ActivityExtensionGiftRequest(
				HttpParam.PHONE, HttpParam.PLATFORM_AREA, gift.getGameCode(), gift.getId(),"");
		if(IPlatApplication.get().getUser()!=null){
			request.setSign(IPlatApplication.get().getUser().getSign());
			request.setTimestamp(IPlatApplication.get().getUser().getTimestamp());
		}
		request.setReqType(IPlatformRequest.REQ_ACT_EXTENSION_GIFT);
		return new BaseRequestBean[]{request};
	}
		

	@Override
	public void initTitle(TitleView titleView) {
		mTitleView = titleView;
		titleView.setTitleCenterRes(E_string.efun_pd_title_receive_awards, false);
		titleView.setTitleRightRes(E_drawable.efun_pd_menu_selector);
	}
	@Override
	public void onClickRight() {
		UserUtils.needLogin(this, new OnLoginFinishListener() {
			@Override
			public void loginCompleted(boolean completed) {
				TrackingUtils.track(ActExtensionActivity.this,TrackingUtils.ACTION_EXTENSION, TrackingUtils.NAME_GIFT_SELF_CENTER);
				Intent it = new Intent(ActExtensionActivity.this,GiftSelfActivity.class);
				startActivity(it);
			}
		});
	}
}
