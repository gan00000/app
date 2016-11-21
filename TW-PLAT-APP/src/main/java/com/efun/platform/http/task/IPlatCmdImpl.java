package com.efun.platform.http.task;

import com.efun.platform.http.dao.IPlatformDao;
import com.efun.platform.http.request.IPlatRequest;
import com.efun.platform.http.response.IPlatResponse;

/**
 * 任务子类实现类
 * 
 * @author Jesse
 * 
 */
public class IPlatCmdImpl extends IPlatCmd {
	private static final long serialVersionUID = 1L;

	private IPlatRequest mRequest;
	private IPlatResponse mResponse;
	private IPlatformDao dao;

	public IPlatCmdImpl(IPlatRequest request) {
		mRequest = request;
		dao = new IPlatformDao(request.getContext());
	}

	/**
	 * 执行，调用数据处理
	 */
	@Override
	public void execute() throws Exception {
		mResponse = dao.request(mRequest);
	}

	@Override
	public IPlatResponse getResponse() {
		return mResponse;
	}

}
