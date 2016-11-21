package com.efun.platform.module.bean;

import java.io.Serializable;
import java.util.ArrayList;

import com.efun.platform.module.BaseDataBean;

import android.content.Intent;
import android.graphics.drawable.Drawable;
/**
 * 平台配置
 * @author itxuxxey
 */
public class ConfigInfoBean extends BaseDataBean{
	private static final long serialVersionUID = 1L;
	private String id;//模块编号：1、我; 4、游戏
    private String name ;  //名称
    private String subName;
    private String icon;
    private String url;
    private boolean isOpen;//开启或者关闭功能
    private boolean auditIsOpen;//是否是送审状态
    private ArrayList<ConfigInfoBean> subList;
      
    public ConfigInfoBean(){}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSubName() {
		return subName;
	}

	public void setSubName(String subName) {
		this.subName = subName;
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

	public boolean isOpen() {
		return isOpen;
	}

	public void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}

	public ArrayList<ConfigInfoBean> getSubList() {
		return subList;
	}

	public void setSubList(ArrayList<ConfigInfoBean> subList) {
		this.subList = subList;
	}

	public boolean isAuditIsOpen() {
		return auditIsOpen;
	}

	public void setAuditIsOpen(boolean auditIsOpen) {
		this.auditIsOpen = auditIsOpen;
	}
    
}
