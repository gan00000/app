package com.efun.platform.push;

import com.efun.platform.module.BaseDataBean;

public class PushParamsBean extends BaseDataBean{
	private String pushGameCode;
	private String pushGoodsId;
	public PushParamsBean() {
	}

	public String getPushGameCode() {
		return pushGameCode;
	}

	public void setPushGameCode(String pushGameCode) {
		this.pushGameCode = pushGameCode;
	}

	public String getPushGoodsId() {
		return pushGoodsId;
	}

	public void setPushGoodsId(String pushGoodsId) {
		this.pushGoodsId = pushGoodsId;
	}
	
	
	
}
