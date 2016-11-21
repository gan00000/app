package com.efun.platform.module.bean;

import java.io.Serializable;
import java.util.ArrayList;

import android.content.Intent;
import android.graphics.drawable.Drawable;
/**
 * 玩家城市
 * @author itxuxxey
 */
public class PlayerCityBean implements Serializable{
	private static final long serialVersionUID = 1L;
	private String key;
	private String value;
	private String text;
      
    public PlayerCityBean(){}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

    
}
