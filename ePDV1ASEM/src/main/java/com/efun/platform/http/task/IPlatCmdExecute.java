package com.efun.platform.http.task;

import java.util.HashMap;
import java.util.Map;
/**
 * 处理Command请求
 * @author itxuxxey
 *
 */
public class IPlatCmdExecute {
	private static IPlatCmdExecute instance;
	private static Map<String, IPlatAsyncTask> taskMap;

	public static synchronized IPlatCmdExecute getInstance() {
		if (instance == null) {
			instance = new IPlatCmdExecute();
			taskMap = new HashMap<String, IPlatAsyncTask>();
		}
		return instance;
	}

	/**
	 * 执行操作 （异步执行）
	 * */
	public void asynExecute(IPlatCmd command) {
		if (command != null) {
			IPlatAsyncTask task = new IPlatAsyncTask(command);
			task.execute();
			taskMap.put(command.getCommandId(), task);
		}
	}

}