package com.efun.platform.http.dao;

import com.efun.platform.http.request.IPlatRequest;
import com.efun.platform.http.response.IPlatResponse;

/**
 * 平台接口
 * 
 * @author itxuxxey
 * 
 */
public interface IPlatformInterface {
	/**
	 * 请求
	 */
	public IPlatResponse request(IPlatRequest r);
}
