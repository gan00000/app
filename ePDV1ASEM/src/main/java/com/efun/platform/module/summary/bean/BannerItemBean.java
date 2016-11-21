package com.efun.platform.module.summary.bean;

import com.efun.platform.module.BaseDataBean;
/**
 * Banner
 * @author itxuxxey
 *
 */
public class BannerItemBean extends BaseDataBean{
	private String pic;
	private String url;
	private String title;
	
	public BannerItemBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public BannerItemBean(String pic, String url,String title) {
		super();
		this.pic = pic;
		this.url = url;
		this.title = title;
	}

	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
