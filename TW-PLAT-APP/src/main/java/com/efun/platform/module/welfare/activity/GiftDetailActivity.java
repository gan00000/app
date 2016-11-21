package com.efun.platform.module.welfare.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.efun.core.tools.EfunResourceUtil;
import com.efun.platform.AndroidScape.E_color;
import com.efun.platform.AndroidScape.E_drawable;
import com.efun.platform.AndroidScape.E_id;
import com.efun.platform.AndroidScape.E_layout;
import com.efun.platform.AndroidScape.E_string;
import com.efun.platform.AndroidScape.E_style;
import com.efun.platform.IPlatApplication;
import com.efun.platform.http.dao.impl.IPlatformRequest;
import com.efun.platform.http.request.bean.BaseRequestBean;
import com.efun.platform.http.request.bean.GiftKnockRequest;
import com.efun.platform.http.request.bean.GiftSelfStatusRequest;
import com.efun.platform.http.response.bean.BaseResponseBean;
import com.efun.platform.http.response.bean.GiftKnockResponse;
import com.efun.platform.http.response.bean.GiftSelfStatusResponse;
import com.efun.platform.image.ImageManager;
import com.efun.platform.module.base.FixedActivity;
import com.efun.platform.module.base.impl.OnLoginFinishListener;
import com.efun.platform.module.utils.AnimUtils;
import com.efun.platform.module.utils.ToastUtils;
import com.efun.platform.module.utils.TrackingGoogleUtils;
import com.efun.platform.module.utils.TrackingUtils;
import com.efun.platform.module.utils.UserUtils;
import com.efun.platform.module.welfare.bean.GiftItemBean;
import com.efun.platform.module.welfare.bean.GiftKnockBean;
import com.efun.platform.module.welfare.bean.GiftSelfStatusBean;
import com.efun.platform.utils.Const;
import com.efun.platform.utils.Const.HttpParam;
import com.efun.platform.widget.TitleView;
/**
 * 礼包详情
 * @author Jesse
 *
 */
public class GiftDetailActivity extends FixedActivity{
	private String titleStr;
	private TitleView mTitleView;

	@Override
	public void onClickRight() {
		UserUtils.needLogin(this, new OnLoginFinishListener() {
			@Override
			public void loginCompleted(boolean completed) {
				TrackingUtils.track(GiftDetailActivity.this,TrackingUtils.ACTION_GIFT_DETAIL, TrackingUtils.NAME_GIFT_SELF_CENTER);
				TrackingGoogleUtils.track(TrackingGoogleUtils.ACTION_GIFT_DETAIL, TrackingGoogleUtils.NAME_GIFT_SELF_CENTER);
				Intent it = new Intent(GiftDetailActivity.this,GiftSelfActivity.class);
				startActivity(it);
			}
		});
	}
	//giftContent, ruleIntro,
	private TextView gameName;
	private ImageView gameHeader;
//	private PagerTab mPagerTabs;
//	private ViewPager mViewPager;
	private View mBottom;
	private ProgressBar mRoundProgress;
	private GiftItemBean mGiftItemBean;
	private TextView titleGift,titleRule,contentGift,contentRule,giftRemain,count;
	
	@Override
	public ViewGroup[] needShowLoading() {
		return null;
	}
	
	@Override
	public boolean needRequestData() {
		return false;
	}

	@Override
	public BaseRequestBean[] needRequestDataBean() {
		GiftKnockRequest knockRequest = new GiftKnockRequest(
				mGiftItemBean.getGameCode(),
				mGiftItemBean.getGoodsType(),
				HttpParam.PLATFORM_AREA,HttpParam.PLATFORM,HttpParam.APP);
		if(IPlatApplication.get().getUser()!=null){
			knockRequest.setSign(IPlatApplication.get().getUser().getSign());
			knockRequest.setTimestamp(IPlatApplication.get().getUser().getTimestamp());
		}
		knockRequest.setReqType(IPlatformRequest.REQ_GIFT_KNOCK);
		return new BaseRequestBean[]{knockRequest};
	}

//	private ViewGroup[] createGroup(){
//		ViewGroup[] groups = new ViewGroup[2];
//		FrameLayout view  = null;
//		for (int i = 0; i < groups.length; i++) {
//			view = (FrameLayout) ViewUtils.createView(mContext, E_layout.efun_pd_welfare_gift_textview);
//			if(i==0){
//				giftContent = (TextView) view.findViewById(E_id.item_text);
//			}else{
//				ruleIntro = (TextView) view.findViewById(E_id.item_text);
//			}
//			groups[i] = view;
//		}
//		return groups;
//	}
	
	@Override
	public void init(Bundle bundle) {
		if(bundle!=null){
			GiftItemBean bean = (GiftItemBean) bundle.getSerializable(Const.BEAN_KEY);
			titleStr = bean.getGoodsName();
			if(titleStr.contains("-")){
				int lastIndex = titleStr.lastIndexOf("-");
				titleStr = titleStr.substring(lastIndex+1, titleStr.length());
				if(TextUtils.isEmpty(titleStr)){
					titleStr = bean.getGoodsType();
				}
			}
		}
		
//		mPagerTabs = (PagerTab) findViewById(E_id.pager_view_tab);
//		mViewPager = (ViewPager) findViewById(E_id.pager_view_v4);
//		VPagerAdapter mAdapter = new VPagerAdapter(createGroup());
//		mViewPager.setAdapter(mAdapter);

//		mPagerTabs.setTab(E_layout.efun_pd_pager_tab_textview);
//		mPagerTabs.setTabSelectedColor(E_color.e_5aa9ff);
//		mPagerTabs.setTitles(E_array.efun_pd_welfare_gift_v_tab);
//		mPagerTabs.setLine(E_drawable.efun_pd_blue_line_each);
//		mPagerTabs.setPagerAdapter(mViewPager);
//		mPagerTabs.setSelectedItem(mViewPager, 0);
		
		titleGift = (TextView) findViewById(E_id.gift_title_gift);
		titleRule = (TextView) findViewById(E_id.gift_title_rule);
		contentGift = (TextView) findViewById(E_id.gift_content_gift);
		contentRule = (TextView) findViewById(E_id.gift_content_rule);
		giftRemain = (TextView) findViewById(E_id.item_gift_remain);
		count = (TextView) findViewById(E_id.item_count);
		
		
		titleGift.setSelected(true);
		titleGift.setTextColor(Color.WHITE);
		titleRule.setSelected(false);
		
		
		titleGift.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				titleGift.setSelected(true);
				titleGift.setTextColor(Color.WHITE);
				titleRule.setSelected(false);
				titleRule.setTextColor(Color.BLACK);
				AnimUtils.actAnimation_go(GiftDetailActivity.this, contentRule, contentGift);
			}
		});
		titleRule.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				titleGift.setSelected(false);
				titleGift.setTextColor(Color.BLACK);
				titleRule.setSelected(true);
				titleRule.setTextColor(Color.WHITE);
				AnimUtils.actAnimation_back(GiftDetailActivity.this, contentGift, contentRule);
			}
		});
		
		
		
		mBottom = findViewById(E_id.layout_1);
		mBottom.findViewById(E_id.item_icon).setVisibility(View.GONE);
		((TextView)mBottom.findViewById(E_id.item_text)).setText(E_string.efun_pd_rob);
		gameHeader = (ImageView) findViewById(E_id.item_icon);
		gameName = (TextView) findViewById(E_id.text_1);
		
		if(bundle!=null){
			mGiftItemBean = (GiftItemBean) bundle.getSerializable(Const.BEAN_KEY);
			ImageManager.displayImage(mGiftItemBean.getIcon(), gameHeader, ImageManager.IMAGE_SQUARE);
			gameName.setText(mGiftItemBean.getGameName());
			
			count.setText((int)((1 - Double.valueOf(mGiftItemBean.getUsePercent()))*100)+"%");
			giftRemain.setText(EfunResourceUtil.findStringByName(mContext, "efun_pd_gift_count")+"："+(mGiftItemBean.getTotal() - mGiftItemBean.getHasUse()));
			contentGift.setText(mGiftItemBean.getAwardDesc());
			contentRule.setText(mGiftItemBean.getAwardRule());
			
//			giftContent.setText(mGiftItemBean.getAwardDesc());
//			ruleIntro.setText(mGiftItemBean.getAwardRule());
			
			mRoundProgress = (ProgressBar) findViewById(E_id.roundProgress);
//			mRoundProgress.setGradientColors(getRoundProgressColors());
//			mRoundProgress.setMaxProgress(mGiftItemBean.getTotal());
			mRoundProgress.setProgress((int) ((1 - Double.valueOf(mGiftItemBean.getUsePercent()))*100));
//			mRoundProgress.onInvalidate();
			
			if(mGiftItemBean.getUserHasGot()==1){
				mBottom.setEnabled(false);
				mBottom.setBackgroundResource(E_color.e_8e8e8e);
				((TextView)mBottom.findViewById(E_id.item_text)).setText(E_string.efun_pd_robed);
			}else{
				mBottom.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						UserUtils.needLogin(mContext,
								new OnLoginFinishListener() {
									@Override
									public void loginCompleted(boolean completed) {
										requestWithDialog(needRequestDataBean(), getString(E_string.efun_pd_loading_msg_commend));
									}
								});
					}
				});
			}
		}
		queryGiftSelfStatus();
	}
	@Override
	public void onSuccess(int requestType, BaseResponseBean baseResponse) {
		super.onSuccess(requestType,baseResponse);
		if (requestType==IPlatformRequest.REQ_GIFT_KNOCK){
			GiftKnockResponse response = (GiftKnockResponse)baseResponse;
			GiftKnockBean bean = response.getGiftKnockBean();
			String code = bean.getCode();
			if(code.equals(HttpParam.RESULT_1001)){
				ToastUtils.toast(mContext, bean.getMessage());
				mBottom.setEnabled(false);
				mBottom.setBackgroundResource(E_color.e_8e8e8e);
				((TextView)mBottom.findViewById(E_id.item_text)).setText(E_string.efun_pd_robed);
			}else if(code.equals(HttpParam.RESULT_1000)){
				mRoundProgress.setProgress((int) ((1 - Double.valueOf(mGiftItemBean.getUsePercent()))*100));
//				mRoundProgress.onInvalidate();
				mBottom.setEnabled(false);
				mBottom.setBackgroundResource(E_color.e_8e8e8e);
				((TextView)mBottom.findViewById(E_id.item_text)).setText(E_string.efun_pd_robed);
//				ToastUtils.toast(mContext, getString(E_string.efun_pd_knock_code)+serial);
				showGradeDialog();
				queryGiftSelfStatus();
			}else{
				ToastUtils.toast(mContext, bean.getMessage());
			}
		}else if(requestType==IPlatformRequest.REQ_GIFT_SELF_STATUS){
			GiftSelfStatusResponse response = (GiftSelfStatusResponse)baseResponse;
			GiftSelfStatusBean mGiftSelfStatusBean = response.getGiftSelfStatusBean();
			if(mGiftSelfStatusBean.getCode().equals(HttpParam.RESULT_1000)){
				IPlatApplication.get().setNewStatusOfGiftSelf(true);
				mTitleView.setTitleRightPointStatus(View.VISIBLE);
			}
		}
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
	@Override
	protected void onResume() {
		super.onResume();
		if(IPlatApplication.get().isNewStatusOfGiftSelf()){
			mTitleView.setTitleRightPointStatus(View.VISIBLE);
		}else{
			mTitleView.setTitleRightPointStatus(View.GONE);
		}
	}
	@Override
	public int LayoutId() {
		return E_layout.efun_pd_welfare_gift;
	}
	
//	private int[] getRoundProgressColors(){
//		return new int[]{
//				E_color.e_fff765,
//				E_color.e_16c6ff,
//				E_color.e_3bc62a
//		};
//	}

	@Override
	public void initTitle(TitleView titleView) {
		mTitleView = titleView;
//		titleView.setTitleLeftRes(E_drawable.efun_pd_back_white_selector);
//		titleView.setTitleRightRes(E_drawable.efun_pd_menu_selector_white);
		titleView.setTitleRightRes(E_drawable.efun_pd_menu_selector);
		titleView.setTitleCenterText(titleStr);
//		titleView.setTitleBarBackgroundRes(E_drawable.efun_pd_welfare_gift_header);
		titleView.setTitleBottomLineStatus(View.GONE);
	}
	
	private void showGradeDialog() {
		final Dialog dialog = new Dialog(mContext, E_style.CS_Dialog);
		// 设置它的ContentView
		dialog.setContentView(E_layout.efun_pd_common_toast_dialog);
		TextView content = (TextView) dialog.findViewById(E_id.dialog_content);
		TextView sureBtn = (TextView) dialog.findViewById(E_id.dialog_btn_determine);
		TextView checkBtn = (TextView) dialog.findViewById(E_id.dialog_btn_check);
		content.setText(E_string.efun_pd_hint_gift_knocked);
		sureBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		checkBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent it = new Intent(GiftDetailActivity.this,GiftSelfActivity.class);
				startActivity(it);
				dialog.dismiss();
			}
		});
		dialog.show();
	}
	
}