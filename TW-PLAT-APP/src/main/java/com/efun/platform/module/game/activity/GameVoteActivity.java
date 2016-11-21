package com.efun.platform.module.game.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.EditText;

import com.efun.core.tools.EfunStringUtil;
import com.efun.platform.AndroidScape.E_color;
import com.efun.platform.AndroidScape.E_dimens;
import com.efun.platform.AndroidScape.E_drawable;
import com.efun.platform.AndroidScape.E_id;
import com.efun.platform.AndroidScape.E_layout;
import com.efun.platform.AndroidScape.E_string;
import com.efun.platform.IPlatApplication;
import com.efun.platform.http.dao.impl.IPlatformRequest;
import com.efun.platform.http.request.bean.BaseRequestBean;
import com.efun.platform.http.request.bean.GameVoteScoreRequest;
import com.efun.platform.http.response.bean.BaseResponseBean;
import com.efun.platform.http.response.bean.GameVoteScoreResponse;
import com.efun.platform.module.base.FixedActivity;
import com.efun.platform.module.base.impl.OnEfunItemClickListener;
import com.efun.platform.module.game.bean.GameCommendBean;
import com.efun.platform.module.game.bean.GameItemBean;
import com.efun.platform.module.game.bean.GameVoteItemBean;
import com.efun.platform.module.utils.AppUtils;
import com.efun.platform.module.utils.TextToolUtils;
import com.efun.platform.module.utils.ToastUtils;
import com.efun.platform.module.utils.TrackingUtils;
import com.efun.platform.utils.Const;
import com.efun.platform.utils.Const.HttpParam;
import com.efun.platform.utils.Const.ResultCode;
import com.efun.platform.widget.RatingBarView;
import com.efun.platform.widget.TitleView;

public class GameVoteActivity extends FixedActivity {
	private RatingBarView starImages;//星圖
	private EditText voteEdit;//評論
	private int starCounts;//評星數
	private String voteContent;//評論內容
	private String gameCode;//當前游戲的gameCode

	@Override
	public ViewGroup[] needShowLoading() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int LayoutId() {
		// TODO Auto-generated method stub
		return E_layout.efun_pd_game_vote;
	}

	@Override
	public void initTitle(TitleView titleView) {
		// TODO Auto-generated method stub
		titleView.setTitleCenterRes(E_string.efun_pd_games_write_commend,false);
		titleView.setTitleRightTextRes(E_string.efun_pd_games_vote_send);
		titleView.setTitleRightTextColor(E_color.e_4ca8ff);
	}

	@Override
	public boolean needRequestData() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public BaseRequestBean[] needRequestDataBean() {
		// TODO Auto-generated method stub
		GameVoteScoreRequest request = new GameVoteScoreRequest();
		if (IPlatApplication.get().getUser() != null) {
			request.setUid(IPlatApplication.get().getUser().getUid());
			request.setSign(IPlatApplication.get().getUser().getSign());
			request.setTimestamp(IPlatApplication.get().getUser()
					.getTimestamp());
		}
		
		request.setPlatform(HttpParam.PLATFORM_AREA);
		request.setVoteType(HttpParam.GAME);
		request.setVersion(HttpParam.PLATFORM);
		request.setPackageVersion(AppUtils.getAppVersionName(mContext));
		request.setFromType(HttpParam.APP);
		request.setLanguage(HttpParam.LANGUAGE);
		request.setEntityid(gameCode);
		request.setStar(starCounts+"");
		request.setReview(voteContent);
		request.setReqType(IPlatformRequest.REQ_GAME_COMMEND);
		return new BaseRequestBean[]{request};
	}

	@Override
	public void init(Bundle bundle) {
		// TODO Auto-generated method stub
		starImages = (RatingBarView) findViewById(E_id.game_commend_ratingbar);
		voteEdit = (EditText) findViewById(E_id.edit_vote);
		starCounts = 5;
		
		GameItemBean mGameItemBean  = (GameItemBean) bundle.getSerializable(Const.BEAN_KEY);
		gameCode = mGameItemBean.getGameCode();
		
		
		starImages.setStarIVSelectImage(E_drawable.efun_pd_star_yellow_select);
		starImages.setStarIVUnSelectImage(E_drawable.efun_pd_star_grey_unselect);
		starImages.setStarWidth(E_dimens.e_size_80);
		
		starImages.createdStarBar(new OnEfunItemClickListener() {
			
			@Override
			public void onItemClick(int position) {
				// TODO Auto-generated method stub
				starCounts = position+1;
			}
		});
		
	}
	
	/**
	 * 發送評分信息
	 */
	@Override
	public void onClickRight() {
		// TODO Auto-generated method stub
		super.onClickRight();
		voteContent = voteEdit.getText().toString();
		
		//評論內容不可為空
		if(TextUtils.isEmpty(voteContent) || EfunStringUtil.isEmpty(voteContent.trim())){
			ToastUtils.toast(mContext, getString(E_string.efun_pd_empty_content));
			return;
		}
		//評論內容不可超過512個字節
		if(!TextToolUtils.isStringLimited(voteContent, 1, 512)){
			return;
		}
		TrackingUtils.track(mContext, TrackingUtils.ACTION_GAME, TrackingUtils.NAME_GAME_DETAIL_EVALUATE+"_"+gameCode);
		requestWithDialog(needRequestDataBean(),getString(E_string.efun_pd_loading_msg_commend));
		
	}
	
	@Override
	public void onSuccess(int requestType, BaseResponseBean baseResponse) {
		// TODO Auto-generated method stub
		super.onSuccess(requestType, baseResponse);
		if (requestType==IPlatformRequest.REQ_GAME_COMMEND){
			GameVoteScoreResponse mGameCommendResponse= (GameVoteScoreResponse)baseResponse;
			GameCommendBean bean = mGameCommendResponse.getGameCommend();
			if(bean.getCode().equals(HttpParam.RESULT_1000)){
				Intent it = new Intent();
				GameVoteItemBean itemBean = new GameVoteItemBean();
				itemBean.setCreatedTime(System.currentTimeMillis());
				itemBean.setIcon(IPlatApplication.get().getUser().getIcon());
				itemBean.setNickname(IPlatApplication.get().getUser().getUsername());
				itemBean.setReview(voteContent);
				itemBean.setStar(starCounts);
				it.putExtra(Const.BEAN_KEY, itemBean);
				setResult(ResultCode.RST_GAME_COMMEND, it);
				finish();
			}
		}
	}
	
}
