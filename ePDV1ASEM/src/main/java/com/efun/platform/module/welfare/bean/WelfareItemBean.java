package com.efun.platform.module.welfare.bean;

import com.efun.platform.module.BaseDataBean;
/**
 * 福利
 * @author harvery
 *
 */
public class WelfareItemBean extends BaseDataBean{
	private String content;
	private String icon;
	private String url;
	private String title;
	public WelfareItemBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
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
