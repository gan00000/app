package com.efun.platform.module.summary.bean;

import com.efun.platform.module.BaseDataBean;
/**
 * Video
 * @author harvery
 *
 */
public class VideoBean extends BaseDataBean{
	private String title;
	private String videoPic;
	private String videoUrl;
	
	public VideoBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public VideoBean(String title, String videoPic, String videoUrl) {
		super();
		this.title = title;
		this.videoPic = videoPic;
		this.videoUrl = videoUrl;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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
	
	
}
