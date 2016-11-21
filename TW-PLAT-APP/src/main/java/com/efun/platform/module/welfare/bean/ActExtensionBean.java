package com.efun.platform.module.welfare.bean;

import java.util.ArrayList;

import com.efun.platform.module.BaseDataBean;
import com.efun.platform.module.game.bean.GameItemBean;
/**
 * 推荐游戏
 * @author Jesse
 * @author itxuxxey 20150617修整
 *
 */
public class ActExtensionBean extends BaseDataBean{
//	private String code;//20150617删掉
//	private String activityCode;
//	private String message;
	private String gameCode;
	private String currentState;
	private String context;
	private int giftsAllCount;
	private int giftsLastCount;
	private String startTime;//结束时间 20150617添加
	private String endTime;//开始时间
	private String outLine;//一句話描述
	private String rewarddescription;//禮包描述
	
	
	private ArrayList<ActExtensionGiftBean> mArrayOfGift;
	private ArrayList<ActExtensionTaskBean> mArrayOfTask;
	private GameItemBean mGameBean;
	
	public ActExtensionBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getGameCode() {
		return gameCode;
	}
	public void setGameCode(String gameCode) {
		this.gameCode = gameCode;
	}
	public ArrayList<ActExtensionGiftBean> getArrayOfGift() {
		return mArrayOfGift;
	}
	public void setArrayOfGift(ArrayList<ActExtensionGiftBean> mArrayOfGift) {
		this.mArrayOfGift = mArrayOfGift;
	}
	public ArrayList<ActExtensionTaskBean> getArrayOfTask() {
		return mArrayOfTask;
	}
	public void setArrayOfTask(ArrayList<ActExtensionTaskBean> mArrayOfTask) {
		this.mArrayOfTask = mArrayOfTask;
	}
	public GameItemBean getGameBean() {
		return mGameBean;
	}
	public void setGameBean(GameItemBean mGameBean) {
		this.mGameBean = mGameBean;
	}
	public String getCurrentState() {
		return currentState;
	}
	public void setCurrentState(String currentState) {
		this.currentState = currentState;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public int getGiftsAllCount() {
		return giftsAllCount;
	}
	public void setGiftsAllCount(int giftsAllCount) {
		this.giftsAllCount = giftsAllCount;
	}
	public int getGiftsLastCount() {
		return giftsLastCount;
	}
	public void setGiftsLastCount(int giftsLastCount) {
		this.giftsLastCount = giftsLastCount;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getOutLine() {
		return outLine;
	}
	public void setOutLine(String outLine) {
		this.outLine = outLine;
	}
	public String getRewarddescription() {
		return rewarddescription;
	}
	public void setRewarddescription(String rewarddescription) {
		this.rewarddescription = rewarddescription;
	}
	
}
