package com.efun.platform.module.summary.bean;

import java.util.ArrayList;

import com.efun.platform.module.BaseDataBean;
/**
 * 首页信息
 * @author itxuxxey
 *
 */
public class SummaryHomeBean extends BaseDataBean{
	private String gameEtag;
	private String newsEtag;
	private String picEtag;
	private ArrayList<SummaryItemBean> mSummaryArray = new ArrayList<SummaryItemBean>();
	private ArrayList<EventGameBean> mGameArray  = new ArrayList<EventGameBean>();
	private ArrayList<BannerItemBean> mBannerArray= new ArrayList<BannerItemBean>();
	private VideoBean videoBean;//20151028添加
	public String getGameEtag() {
		return gameEtag;
	}
	public void setGameEtag(String gameEtag) {
		this.gameEtag = gameEtag;
	}
	public String getNewsEtag() {
		return newsEtag;
	}
	public void setNewsEtag(String newsEtag) {
		this.newsEtag = newsEtag;
	}
	public String getPicEtag() {
		return picEtag;
	}
	public void setPicEtag(String picEtag) {
		this.picEtag = picEtag;
	}
	public ArrayList<SummaryItemBean> getSummaryArray() {
		return mSummaryArray;
	}
	public void setSummaryArray(ArrayList<SummaryItemBean> mSummaryArray) {
		this.mSummaryArray = mSummaryArray;
	}
	public ArrayList<EventGameBean> getGameArray() {
		return mGameArray;
	}
	public void setGameArray(ArrayList<EventGameBean> mGameArray) {
		this.mGameArray = mGameArray;
	}
	public ArrayList<BannerItemBean> getBannerArray() {
		return mBannerArray;
	}
	public void setBannerArray(ArrayList<BannerItemBean> mBannerArray) {
		this.mBannerArray = mBannerArray;
	}
	public VideoBean getVideoBean() {
		return videoBean;
	}
	public void setVideoBean(VideoBean videoBean) {
		this.videoBean = videoBean;
	}
	
}
