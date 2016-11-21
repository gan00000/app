package com.efun.platform.module.dimensionalcode.activity;

import java.io.IOException;
import java.util.Vector;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.widget.Toast;

import com.efun.core.tools.EfunResourceUtil;
import com.efun.platform.AndroidScape.E_id;
import com.efun.platform.AndroidScape.E_layout;
import com.efun.platform.AndroidScape.E_string;
import com.efun.platform.module.dimensionalcode.decoding.CaptureActivityHandler;
import com.efun.platform.module.dimensionalcode.decoding.InactivityTimer;
import com.efun.platform.module.dimensionalcode.utils.CameraManager;
import com.efun.platform.module.dimensionalcode.view.ViewfinderView;
import com.efun.platform.module.utils.AppUtils;
import com.efun.platform.module.utils.ToastUtils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

/**
 * Initial the camera 初始化相机对象，用于二维码扫描
 * @author Administrator 
 */
public class DimensionalCodeActivity extends Activity implements Callback {

	private CaptureActivityHandler handler;
	private ViewfinderView viewfinderView;
	private boolean hasSurface;
	private Vector<BarcodeFormat> decodeFormats;
	private String characterSet;
	private InactivityTimer inactivityTimer;
	private MediaPlayer mediaPlayer;
	private boolean playBeep;
	private static final float BEEP_VOLUME = 0.10f;
	private boolean vibrate;
//	private BaseTitleView titleView;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(E_layout.efun_pd_dimensional_code_activity_capture);
		//ViewUtil.addTopView(getApplicationContext(), this, R.string.scan_card);
		CameraManager.init(getApplication());
		viewfinderView = (ViewfinderView) findViewById(E_id.viewfinder_view);
//		titleView = (BaseTitleView) findViewById(EfunResourceUtil.findViewIdByName(this, "efun_platform_dimensional_code_title"));
//		View mButtonBack = titleView.getLeftView();
		
//		mButtonBack.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				DimensionalCodeActivityCapture.this.finish();				
//			}
//		});
		hasSurface = false;
		inactivityTimer = new InactivityTimer(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		SurfaceView surfaceView = (SurfaceView) findViewById(E_id.preview_view);
		SurfaceHolder surfaceHolder = surfaceView.getHolder();
		if (hasSurface) {
			initCamera(surfaceHolder);
		} else {
			surfaceHolder.addCallback(this);
			surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		}
		decodeFormats = null;
		characterSet = null;

		playBeep = true;
		AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
		if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
			playBeep = false;
		}
		initBeepSound();
		vibrate = true;
		
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (handler != null) {
			handler.quitSynchronously();
			handler = null;
		}
		CameraManager.get().closeDriver();
	}

	@Override
	protected void onDestroy() {
		inactivityTimer.shutdown();
		super.onDestroy();
	}
	
	/**
	 * 处理扫描结果
	 * @param result
	 * @param barcode
	 */
	public void handleDecode(Result result, Bitmap barcode) {
		inactivityTimer.onActivity();
		playBeepSoundAndVibrate();
		String resultString = result.getText();
		if (resultString.equals("")) {
			Toast.makeText(DimensionalCodeActivity.this, "Scan failed!", Toast.LENGTH_SHORT).show();
		}else {
//			Intent resultIntent = new Intent();
//			Bundle bundle = new Bundle();
//			bundle.putString("result", resultString);
//			bundle.putParcelable("bitmap", barcode);
//			resultIntent.putExtras(bundle);
//			this.setResult(RESULT_OK, resultIntent);
    	 	Uri uri = Uri.parse(resultString);
			if(uri.toString().contains("market://details")){//market
				AppUtils.download(this, resultString);
			}else if(uri.toString().contains("https://play.google.com/store/apps/details")){//应用商城
				AppUtils.download(this, resultString);
			}else{
	    	    //调用系统自带的
	    	 	Intent intent = new Intent(Intent.ACTION_VIEW);  
		        intent.setAction("android.intent.action.VIEW");
		        intent.setData(uri);
		        try {
					DimensionalCodeActivity.this.startActivity(intent);
				} catch (Exception e) {
					ToastUtils.toast(this, E_string.efun_pd_dimensional_url_error);
				} 
			}   
		}
		DimensionalCodeActivity.this.finish();
	}
	
	private void initCamera(SurfaceHolder surfaceHolder) {
		try {
			CameraManager.get().openDriver(surfaceHolder);
		} catch (IOException ioe) {
			return;
		} catch (RuntimeException e) {
			return;
		}
		if (handler == null) {
			handler = new CaptureActivityHandler(this, decodeFormats,
					characterSet);
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if (!hasSurface) {
			hasSurface = true;
			initCamera(holder);
		}

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		hasSurface = false;

	}

	public ViewfinderView getViewfinderView() {
		return viewfinderView;
	}

	public Handler getHandler() {
		return handler;
	}

	public void drawViewfinder() {
		viewfinderView.drawViewfinder();

	}

	private void initBeepSound() {
		if (playBeep && mediaPlayer == null) {
			// The volume on STREAM_SYSTEM is not adjustable, and users found it
			// too loud,
			// so we now play on the music stream.
			setVolumeControlStream(AudioManager.STREAM_MUSIC);
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.setOnCompletionListener(beepListener);

			AssetFileDescriptor file = getResources().openRawResourceFd(EfunResourceUtil.getResourcesIdByName(this, "raw", "beep"));
			try {
				mediaPlayer.setDataSource(file.getFileDescriptor(),
						file.getStartOffset(), file.getLength());
				file.close();
				mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
				mediaPlayer.prepare();
			} catch (IOException e) {
				mediaPlayer = null;
			}
		}
	}

	private static final long VIBRATE_DURATION = 200L;

	private void playBeepSoundAndVibrate() {
		if (playBeep && mediaPlayer != null) {
			mediaPlayer.start();
		}
		if (vibrate) {
			Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
			vibrator.vibrate(VIBRATE_DURATION);
		}
	}

	/**
	 * When the beep has finished playing, rewind to queue up another one.
	 */
	private final OnCompletionListener beepListener = new OnCompletionListener() {
		public void onCompletion(MediaPlayer mediaPlayer) {
			mediaPlayer.seekTo(0);
		}
	};
	
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			DimensionalCodeActivity.this.finish();
            return true;
		}
		return super.onKeyDown(keyCode, event);
	};

}