package com.efun.platform.module.ads;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.efun.ads.callback.GAListener;

public class GAAdvert implements GAListener {

	private static final String TAG = "efun";
	@Override
	public void GAonReceive(Context context, Intent intent) {
		Log.i(TAG, "GAonReceive...");
	}

}
