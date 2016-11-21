package com.efun.platform.widget.camera;

import com.efun.core.tools.EfunResourceUtil;
import com.efun.core.tools.EfunStringUtil;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.widget.Toast;

public class CameraCheck {

	public static boolean CheckCamera(Context mContext) {
		if (mContext.getPackageManager().hasSystemFeature(
				PackageManager.FEATURE_CAMERA)) {
			return true;
		} else {
			Toast.makeText(mContext, EfunResourceUtil.findStringByName(mContext, "efun_pf_thumb_error"), Toast.LENGTH_SHORT).show();
			return false;
		}
	}

	/** A safe way to get an instance of the Camera object. */
	public static Camera getCameraInstance(Context mContext) {
		Camera c = null;
		if (CheckCamera(mContext)) {
			try {
				c = Camera.open();
			} catch (Exception e) {
				c=null;
			}
		}
		return c; // returns null if camera is unavailable
	}
}
