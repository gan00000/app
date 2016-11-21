package com.efun.platform.http.task;

import android.os.AsyncTask;

/**
 * 异步请求类
 * 
 * @author itxuxxey
 * 
 */
public class IPlatAsyncTask extends AsyncTask<Void, Void, Void> {
	/**
	 * 任务 {@link IPlatCmd}
	 */
	private IPlatCmd command;

	public IPlatAsyncTask(IPlatCmd _command) {
		this.command = _command;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected Void doInBackground(Void... params) {
		// Log.i("EfunCommandAsyncTask", "doInBackground");
		try {
			command.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		if (command.getCallback() != null) {
			command.getCallback().cmdCallBack(command);
		}
	}

}
