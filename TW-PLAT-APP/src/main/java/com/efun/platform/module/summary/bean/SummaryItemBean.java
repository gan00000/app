package com.efun.platform.module.summary.bean;

import com.efun.platform.module.BaseDataBean;
import com.efun.platform.utils.Const.Web;
/**
 * 单条资讯
 * @author Jesse
 *
 */
public class SummaryItemBean extends BaseDataBean{
	private String title;
	private long crtime;
	private int type;
	private String htmlpathurl;
	private String gameCode;
	private String iphoneUrl;
	//20151021添加
	private long id;
	private String subTitle;
	private String videoPic;
	private String videoUrl;
	private String videoTime;
	private int likes;
	private int isLiked;
	public SummaryItemBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public SummaryItemBean(int beanType) {
		super(beanType);
		// TODO Auto-generated constructor stub
	}

	public SummaryItemBean(String title, long crtime, int type,
			String htmlpathurl, String gameCode, String iphoneUrl) {
		super();
		this.title = title;
		this.crtime = crtime;
		this.type = type;
		this.htmlpathurl = htmlpathurl;
		this.gameCode = gameCode;
		this.iphoneUrl = iphoneUrl;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public long getCrtime() {
		return crtime;
	}
	public void setCrtime(long crtime) {
		this.crtime = crtime;
	}
	public String getHtmlpathurl() {
		return Web.WEB_PERFIX+htmlpathurl;
	}
	public void setHtmlpathurl(String htmlpathurl) {
		this.htmlpathurl = htmlpathurl;
	}

	public String getGameCode() {
		return gameCode;
	}

	public void setGameCode(String gameCode) {
		this.gameCode = gameCode;
	}

	public String getIphoneUrl() {
		return iphoneUrl;
	}

	public void setIphoneUrl(String iphoneUrl) {
		this.iphoneUrl = iphoneUrl;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public String getVideoPic() {
		return videoPic;
	}

	public void setVideoPic(String videoPic) {
		this.videoPic = videoPic;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public String getVideoTime() {
		return videoTime;
	}

	public void setVideoTime(String videoTime) {
		this.videoTime = videoTime;
	}

	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}

	public int getIsLiked() {
		return isLiked;
	}

	public void setIsLiked(int isLiked) {
		this.isLiked = isLiked;
	}
	
}
