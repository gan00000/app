package com.efun.platform.module.game.bean;

import java.util.ArrayList;

import com.efun.platform.module.BaseDataBean;
/**
 * 游戏评论(分析) 结果
 * @author harvery
 *
 */
public class GameVoteBean extends BaseDataBean{
	private String code;
	private String message;
	private boolean canVote;
	private GameVoteStatisBean mVoteStatisBean;
	private ArrayList<GameVoteItemBean> mVoteBeans;
	
	public GameVoteBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isCanVote() {
		return canVote;
	}

	public void setCanVote(boolean canVote) {
		this.canVote = canVote;
	}

	public GameVoteStatisBean getmVoteStatisBean() {
		return mVoteStatisBean;
	}

	public void setmVoteStatisBean(GameVoteStatisBean mVoteStatisBean) {
		this.mVoteStatisBean = mVoteStatisBean;
	}

	public ArrayList<GameVoteItemBean> getmVoteBeans() {
		return mVoteBeans;
	}

	public void setmVoteBeans(ArrayList<GameVoteItemBean> mVoteBeans) {
		this.mVoteBeans = mVoteBeans;
	}

	
}
