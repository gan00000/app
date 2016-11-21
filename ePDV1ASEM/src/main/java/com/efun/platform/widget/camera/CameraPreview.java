package com.efun.platform.widget.camera;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.efun.core.tools.EfunScreenUtil;
/**
 * sufaceView 的预览类，其中SurfaceHolder.CallBack用来监听Surface的变化，
 * 当Surface发生改变的时候自动调用该回调方法
 * 通过调用方SurfaceHolder.addCallBack来绑定该方法
 * @author itxuxxey
 *
 */
public class CameraPreview extends SurfaceView implements
		SurfaceHolder.Callback {
	
	//保存图片本地路径
	public static final String ACCOUNT_DIR = Environment.getExternalStorageDirectory().getPath()
				+ "/epd/";
	public static final String ACCOUNT_MAINTRANCE_ICON_CACHE = "icon_cache/";
	private static final String IMGPATH = ACCOUNT_DIR + ACCOUNT_MAINTRANCE_ICON_CACHE;

	private static final String IMAGE_FILE_NAME = "faceImage.jpeg";
	private static final String TMP_IMAGE_FILE_NAME = "tmp_faceImage.jpeg";

		//常量定义
	public static final int TAKE_A_PICTURE = 10;
	public static final int SELECT_A_PICTURE = 20;
	public static final int SET_PICTURE = 30;
	public static final int SET_ALBUM_PICTURE_KITKAT = 40;
	public static final int SELECET_A_PICTURE_AFTER_KIKAT = 50;
	private String mAlbumPicturePath = null;
	
	//版本比较：是否是4.4及以上版本
	final boolean mIsKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

    private CameraPreview mPreview;
	public static final int MEDIA_TYPE_VIDEO = 2;
	private FrameLayout preview;
	private ImageButton captureButton;
	private Bitmap newShotPicBitmap;
	private String encodeFile, fileName;
	
	File fileone = null;
	File filetwo = null;

	private String TAG = "efun";
	/**
	 * Surface的控制器，用来控制预览等操作
	 */
	private SurfaceHolder mHolder;
	/**
	 * 相机实例
	 */
	private Camera mCamera = null;
	/**
	 * 图片处理
	 */
	public static final int MEDIA_TYPE_IMAGE = 1;
	/**
	 * 预览状态标志
	 */
	private boolean isPreview = false;
	/**
	 * 设置一个固定的最大尺寸
	 */
	private int maxPictureSize = 5000000;
	/**
	 * 是否支持自动聚焦，默认不支持
	 */
	private Boolean isSupportAutoFocus = false;
	/**
	 * 获取当前的context
	 */
	private Context mContext;
	/**
	 * 当前传感器的方向，当方向发生改变的时候能够自动从传感器管理类接受通知的辅助类
	 */
	MyOrientationDetector cameraOrientation;
	/**
	 * 设置最适合当前手机的图片宽度
	 */
	int setFixPictureWidth = 0;
	/**
	 * 设置当前最适合的图片高度
	 */
	int setFixPictureHeight = 0;
	/**
	 * 成功回调
	 * @param context
	 */
	private cameraCallBack callBack;
	
	@SuppressWarnings("deprecation")
	public CameraPreview(Context context) {
		super(context);
		this.mContext = context;
		isSupportAutoFocus = context.getPackageManager().hasSystemFeature(
				PackageManager.FEATURE_CAMERA_AUTOFOCUS);
		mHolder = getHolder();
		//兼容android 3.0以下的API，如果超过3.0则不需要设置该方法
		if(Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB){
			mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		}
		mHolder.addCallback(this);//绑定当前的回调方法
		File directory = new File(ACCOUNT_DIR);
		File imagepath = new File(IMGPATH);
		if (!directory.exists()) {
			Log.i("platform", "directory.mkdir()");
			directory.mkdir();
		}
		if (!imagepath.exists()) {
			Log.i("platform", "imagepath.mkdir()");
			imagepath.mkdir();
		}

		fileone = new File(IMGPATH, IMAGE_FILE_NAME);
		filetwo = new File(IMGPATH, TMP_IMAGE_FILE_NAME);

		try {
			if (!fileone.exists() && !filetwo.exists()) {
				fileone.createNewFile();
				filetwo.createNewFile();
			}
		} catch (Exception e) {
		}
	}
	
	/**
	 * 创建的时候自动调用该方法
	 */
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if (mCamera == null) {
			mCamera = CameraCheck.getCameraInstance(mContext);
		}
		try {
			if(mCamera!=null){
				mCamera.setPreviewDisplay(holder);   
			}
		} catch (IOException e) {
			if (null != mCamera) {
				mCamera.release();
				mCamera = null;
				isPreview=false;
			}
			e.printStackTrace();
		}

	}
	/**
	 * 当surface的大小发生改变的时候自动调用的
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		if (mHolder.getSurface() == null) {
			return;
		}
		try {
			setCameraParms();
			mCamera.setPreviewDisplay(holder);
			mCamera.startPreview();
			reAutoFocus();
		} catch (Exception e) {
			Log.d(TAG, "Error starting camera preview: " + e.getMessage());
		}
	}

	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	private void setCameraParms(){
		Camera.Parameters myParam = mCamera.getParameters();
		
		List<Camera.Size> mSupportedsizeList =myParam.getSupportedPictureSizes();
		if(mSupportedsizeList.size() > 1) {
			Iterator<Camera.Size> itos = mSupportedsizeList.iterator();
			while (itos.hasNext()){
				Camera.Size curSize = itos.next();
				int curSupporSize=curSize.width * curSize.height;
				int fixPictrueSize= setFixPictureWidth  * setFixPictureHeight;
				if( curSupporSize>fixPictrueSize && curSupporSize <= maxPictureSize) {
					setFixPictureWidth  = curSize.width;
					setFixPictureHeight = curSize.height;
				}
			}
		}
		
		myParam.setPictureFormat(PixelFormat.JPEG);  
		if(setFixPictureWidth > 0 && setFixPictureHeight > 0){
			myParam.setPictureSize(setFixPictureWidth, setFixPictureHeight);  // 部分定制手机，无法正常识别该方法。  
		}
	  	mCamera.cancelAutoFocus();// 2如果要实现连续的自动对焦，这一句必须加上 
		myParam.setJpegQuality(100);
		mCamera.setParameters(myParam);
		Log.i("efun", "myParam.getMaxNumDetectedFaces():"+myParam.getMaxNumDetectedFaces());
//		if (myParam.getMaxNumDetectedFaces() > 0){
//		       mCamera.startFaceDetection();
//		}
		if(EfunScreenUtil.getInStance((Activity) mContext).isPortrait((Activity) mContext)){
			mCamera.setDisplayOrientation(90);
		}
	}
	
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		mCamera.stopPreview();
		mCamera.release();
		mCamera = null;
	}

	/**
	 * Call the camera to Auto Focus
	 */
	public void reAutoFocus() {
		if (isSupportAutoFocus) {
			mCamera.autoFocus(new AutoFocusCallback() {
				@Override
				public void onAutoFocus(boolean success, Camera camera) {
				}
			});
		}
	}
	/**
	 * 自动聚焦，然后拍照
	 */
	public void takePicture(cameraCallBack callBack) {
		this.callBack = callBack;
		if (mCamera != null) {
			mCamera.autoFocus(autoFocusCallback);
		}
	}

	private AutoFocusCallback autoFocusCallback = new AutoFocusCallback() {

		public void onAutoFocus(boolean success, Camera camera) {
			// TODO Auto-generated method stub

			if (success) {
				Log.i(TAG, "autoFocusCallback: success...");
				takePhoto();
				camera.cancelAutoFocus();
			} else {
				Log.i(TAG, "autoFocusCallback: fail...");
				if (isSupportAutoFocus) {
					takePhoto();
				}
			}
		}
	};
	/**
	 * 调整照相的方向，设置拍照相片的方向
	 */
	@SuppressWarnings("deprecation")
	private void takePhoto() {
		cameraOrientation = new MyOrientationDetector(mContext);
		if (mCamera != null) {
			int orientation = cameraOrientation.getOrientation();
			Camera.Parameters cameraParameter = mCamera.getParameters();
			cameraParameter.setRotation(90);
			cameraParameter.set("rotation", 90);
			if ((orientation >= 45) && (orientation < 135)) {
				cameraParameter.setRotation(180);
				cameraParameter.set("rotation", 180);
			}
			if ((orientation >= 135) && (orientation < 225)) {
				cameraParameter.setRotation(270);
				cameraParameter.set("rotation", 270);
			}
			if ((orientation >= 225) && (orientation < 315)) {
				cameraParameter.setRotation(0);
				cameraParameter.set("rotation", 0);
			}
			mCamera.setParameters(cameraParameter);
			mCamera.takePicture(shutterCallback, pictureCallback, mPicture);
		}
	}

	private ShutterCallback shutterCallback = new ShutterCallback() {
		@Override
		public void onShutter() {
			// TODO Auto-generated method stub
		}
	};

	private PictureCallback pictureCallback = new PictureCallback() {

		@Override
		public void onPictureTaken(byte[] arg0, Camera arg1) {
			// TODO Auto-generated method stub
		}
	};
	private PictureCallback mPicture = new PictureCallback() {

		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			new SavePictureTask().execute(data);
			mCamera.startPreview();//重新开始预览
		}
	};

	public class SavePictureTask extends AsyncTask<byte[], String, String> {
		@SuppressLint("SimpleDateFormat")
		@Override
		protected String doInBackground(byte[]... params) {
			Log.i("efun", "SavePictureTask");
//			File pictureFile = FileUtil.getOutputMediaFile(MEDIA_TYPE_IMAGE,
//					mContext);
			File pictureFile = new File(IMGPATH, IMAGE_FILE_NAME);
			if (pictureFile == null) {
				Log.i("efun", "没有存储卡");
				return null;
			}
			try {
				FileOutputStream fos = new FileOutputStream(pictureFile);
				fos.write(params[0]);
				fos.flush();
				fos.close();
				Log.i("efun", "SavePictureTask------");
				callBack.photoSucess(pictureFile);
			} catch (FileNotFoundException e) {
				Log.d(TAG, "File not found: " + e.getMessage());
			} catch (IOException e) {
				Log.d(TAG, "Error accessing file: " + e.getMessage());
			}
			
			return null;
		}
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Log.i("efun", "onTouchEvent");
		reAutoFocus();
		return false;
	}
	
	interface cameraCallBack{
		public void photoSucess(File pictureFile);
	}

}
