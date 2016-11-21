package com.efun.platform.widget.camera;



import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.efun.core.cipher.EfunCipher;
import com.efun.core.res.EfunResConfiguration;
import com.efun.core.tools.EfunLocalUtil;
import com.efun.core.tools.EfunLogUtil;
import com.efun.core.tools.EfunResourceUtil;
import com.efun.core.tools.EfunScreenUtil;
import com.efun.core.tools.EfunStringUtil;
import com.efun.platform.http.dao.impl.IPlatformRequest;
import com.efun.platform.utils.Const.RequestCode;
import com.efun.platform.widget.camera.CameraPreview.cameraCallBack;

public class CameraActivity extends Activity{
	//保存图片本地路径
	public static final String ACCOUNT_DIR = Environment.getExternalStorageDirectory().getPath()
				+ "/efun/";
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
	final boolean mIsKitKat = Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT;

    private CameraPreview mPreview;
	public static final int MEDIA_TYPE_IMAGE = 1;
	public static final int MEDIA_TYPE_VIDEO = 2;
	private FrameLayout preview;
	private ImageButton captureButton;
	private Bitmap newShotPicBitmap;
	private String encodeFile, fileName;
	
	File fileone = null;
	File filetwo = null;
	
	private Uri  pictureFileUri;
	private boolean isCamera = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);  
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
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
		
		Intent it = getIntent();
		if(it != null){
			if(it.getExtras().getString("CAMERA_TYPE").equals("Camera")){
				isCamera = true;
				if(EfunScreenUtil.getInStance(this).isPortrait(this)){
					setContentView(EfunResourceUtil.findLayoutIdByName(this, "epd_camera_preview"));
				}else{
					setContentView(EfunResourceUtil.findLayoutIdByName(this, "epd_camera_preview_land"));
				}
		        mPreview = new CameraPreview(this);
		        preview = (FrameLayout) findViewById(EfunResourceUtil.findViewIdByName(this, "camera_preview"));
		        preview.addView(mPreview);
		        captureButton = (ImageButton) findViewById(EfunResourceUtil.findViewIdByName(this, "button_capture"));
				captureButton.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						mPreview.takePicture(new cameraCallBack() {

							@Override
							public void photoSucess(File pictureFile) {
								// TODO Auto-generated method stub
//								startPhotoZoom(Uri.fromFile(pictureFile), 200);
								try {
									if (mIsKitKat) {
										Log.i("efun", "pictureFile:"+pictureFile);
										mAlbumPicturePath = getPath(getApplicationContext(), Uri.fromFile(pictureFile));
										cropImageUriAfterKikat(Uri.fromFile(new File(mAlbumPicturePath)));
									} else {
										Log.i("efun", "pictureFile1:"+pictureFile);
										cameraCropImageUri(Uri.fromFile(pictureFile));
									}
								} catch (Exception e) {
									
								}
							
							}
						});
					}

				});
			}else if(it.getExtras().getString("CAMERA_TYPE").equals("Gallery")){
				isCamera = false;
				setContentView(EfunResourceUtil.findLayoutIdByName(this, "epd_camera_view"));
				if (mIsKitKat) {
					selectImageUriAfterKikat();
				} else {
					cropImageUri();
				}
			}
		}
		

	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		Log.i("efun", "requestCode:"+requestCode+"/resultCode:"+resultCode+"/data:"+data);
		 if (requestCode == SELECT_A_PICTURE) {
				if (resultCode == RESULT_OK && null != data) {
					//4.4以下的
					Bitmap bitmap = decodeUriAsBitmap(Uri.fromFile(new File(IMGPATH,
							TMP_IMAGE_FILE_NAME)));
					if (bitmap != null) {
//						setResult(bitmap);
						setResult(Uri.fromFile(new File(IMGPATH, TMP_IMAGE_FILE_NAME)));
					}
				} else if (resultCode == RESULT_CANCELED) {
					EfunLogUtil.logI("efun", "取消头像设置");
					finish();
				}
			} else if (requestCode == SELECET_A_PICTURE_AFTER_KIKAT) {
				if (resultCode == RESULT_OK && null != data) {
					//4.4以上的
					mAlbumPicturePath = getPath(getApplicationContext(), data.getData());
					cropImageUriAfterKikat(Uri.fromFile(new File(mAlbumPicturePath)));
				} else if (resultCode == RESULT_CANCELED) {
					EfunLogUtil.logI("efun", "取消头像设置");
					finish();
				}
			} else if (requestCode == SET_ALBUM_PICTURE_KITKAT) {
				Log.i("efun", "4.4以上上的 RESULT_OK");
				Bitmap bitmap = null;
				if(isCamera){
					if (null != data) {  
	                    bitmap = decodeUriAsBitmap(data.getData());  
	                    if (bitmap != null) {
							setResult(data.getData());
						}
	                } 
				}else if (resultCode == RESULT_CANCELED) {//20160725处理取消回调
					EfunLogUtil.logI("efun", "取消头像设置");
					finish();
				}else{
					bitmap = decodeUriAsBitmap(Uri.fromFile(new File(IMGPATH, TMP_IMAGE_FILE_NAME)));
					if (bitmap != null) {
						setResult(Uri.fromFile(new File(IMGPATH,
								TMP_IMAGE_FILE_NAME)));
					}
				}
//				if(bitmap != null){
//					setResult(bitmap);
//				}
				
			} else if (requestCode == TAKE_A_PICTURE) {
				Log.i("efun", "TAKE_A_PICTURE-resultCode:" + resultCode);
				if (resultCode == RESULT_OK) {
					cameraCropImageUri(Uri.fromFile(new File(IMGPATH, IMAGE_FILE_NAME)));
				} else {
					EfunLogUtil.logI("efun", "取消头像设置");
					finish();
				}
			} else if (requestCode == SET_PICTURE) {
				Bitmap bitmap = null;
				if (resultCode == RESULT_OK && null != data) {
					bitmap = decodeUriAsBitmap(pictureFileUri);
//					setResult(bitmap);
					setResult(pictureFileUri);
				} else if (resultCode == RESULT_CANCELED) {
					EfunLogUtil.logI("efun", "取消头像设置");
					finish();
				} else {
					EfunLogUtil.logI("efun", "取消头像失败");
					finish();
				}
			}
	}
	
//	private void setResult(Bitmap bitmap){
////		encodeFile = byte2hex(Bitmap2Bytes(bitmap));
////		fileName = EfunStringUtil.toMd5(bitmap.toString(),
////				false);
//		Intent intent = new Intent();
//		intent.putExtra("dataBitMap", bitmap);
////		intent.putExtra("encodeFile", encodeFile);
////		intent.putExtra("fileName", fileName);
//		setResult(RequestCode.REQ_PIC_PHOTO_CAMERA_RETURN, intent);
//		Log.i("platform", "fileName:"+fileName);
//		finish();
//	}
	
	private void setResult(Uri uri) {
		// encodeFile = byte2hex(Bitmap2Bytes(bitmap));
		// fileName = EfunStringUtil.toMd5(bitmap.toString(),
		// false);
		Intent intent = new Intent();
		intent.putExtra("dataBitMap", uri);
		// intent.putExtra("encodeFile", encodeFile);
		// intent.putExtra("fileName", fileName);
		setResult(RequestCode.REQ_PIC_PHOTO_CAMERA_RETURN, intent);
		Log.i("platform", "fileName:" + fileName);
		finish();
	}
	

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.i("efun", "CameraActivity--onDestroy");
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			CameraActivity.this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}
	
	/** <br>功能简述:裁剪图片方法实现---------------------- 相册
	 * <br>功能详细描述:
	 * <br>注意:
	 */
	private void cropImageUri() {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
		intent.setType("image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 300);
		intent.putExtra("outputY", 300);
		intent.putExtra("scale", true);
		intent.putExtra("return-data", false);
		intent.putExtra(MediaStore.EXTRA_OUTPUT,
				Uri.fromFile(new File(IMGPATH, TMP_IMAGE_FILE_NAME)));
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		intent.putExtra("noFaceDetection", true); // no face detection
		startActivityForResult(intent, SELECT_A_PICTURE);
	}

	
	/**
	 *  <br>功能简述:4.4以上裁剪图片方法实现---------------------- 相册
	 * <br>功能详细描述:
	 * <br>注意:
	 */
	@TargetApi(Build.VERSION_CODES.KITKAT)
	private void selectImageUriAfterKikat() {
//		Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
//		intent.addCategory(Intent.CATEGORY_OPENABLE);
//		intent.setType("image/*");
//		startActivityForResult(intent, SELECET_A_PICTURE_AFTER_KIKAT);
		Intent intent = new Intent("android.intent.action.PICK");  
		intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");  
		startActivityForResult(intent, SELECET_A_PICTURE_AFTER_KIKAT);
	} 
	
	/** 
	 * <br>功能简述:裁剪图片方法实现----------------------相机
	 * <br>功能详细描述:
	 * <br>注意:
	 * @param uri
	 */
	private void cameraCropImageUri(Uri uri) {
		pictureFileUri = uri;
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 300);
		intent.putExtra("outputY", 300);
		intent.putExtra("scale", true);
		intent.putExtra("scaleUpIfNeeded", true);
		//		if (mIsKitKat) {
		//			intent.putExtra("return-data", true);
		//			intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
		//		} else {
		intent.putExtra("return-data", false);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
		//		}
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		intent.putExtra("noFaceDetection", true);
		startActivityForResult(intent, SET_PICTURE);
	}

	/** 
	 * <br>功能简述: 4.4及以上改动版裁剪图片方法实现 --------------------相机
	 * <br>功能详细描述:
	 * <br>注意:
	 * @param uri
	 */
	private void cropImageUriAfterKikat(Uri uri) {
		Log.i("efun", "cropImageUriAfterKikat:"+uri);
		pictureFileUri = uri;
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/jpeg");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 300);
		intent.putExtra("outputY", 300);
		intent.putExtra("scale", true);
		intent.putExtra("scaleUpIfNeeded", true);
		//		intent.putExtra("return-data", true);
		intent.putExtra("return-data", false);
		intent.putExtra(MediaStore.EXTRA_OUTPUT,
				Uri.fromFile(new File(IMGPATH, TMP_IMAGE_FILE_NAME)));
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		intent.putExtra("noFaceDetection", true);
		startActivityForResult(intent, SET_ALBUM_PICTURE_KITKAT);
	}
	
	/** 
	 * <br>功能简述:
	 * <br>功能详细描述:
	 * <br>注意:
	 * @param uri
	 * @return
	 */
	private Bitmap decodeUriAsBitmap(Uri uri) {
		Bitmap bitmap = null;
		try {
			bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		return bitmap;
	}

	/** 
	 * <br>功能简述:4.4及以上获取图片的方法
	 * <br>功能详细描述:
	 * <br>注意:
	 * @param context
	 * @param uri
	 * @return
	 */
	@TargetApi(Build.VERSION_CODES.KITKAT)
	public static String getPath(final Context context, final Uri uri) {

		final boolean isKitKat = Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT;

		// DocumentProvider
		if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
			// ExternalStorageProvider
			if (isExternalStorageDocument(uri)) {
				final String docId = DocumentsContract.getDocumentId(uri);
				final String[] split = docId.split(":");
				final String type = split[0];

				if ("primary".equalsIgnoreCase(type)) {
					return Environment.getExternalStorageDirectory() + "/" + split[1];
				}
			}
			// DownloadsProvider
			else if (isDownloadsDocument(uri)) {

				final String id = DocumentsContract.getDocumentId(uri);
				final Uri contentUri = ContentUris.withAppendedId(
						Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

				return getDataColumn(context, contentUri, null, null);
			}
			// MediaProvider
			else if (isMediaDocument(uri)) {
				final String docId = DocumentsContract.getDocumentId(uri);
				final String[] split = docId.split(":");
				final String type = split[0];

				Uri contentUri = null;
				if ("image".equals(type)) {
					contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
				} else if ("video".equals(type)) {
					contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
				} else if ("audio".equals(type)) {
					contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
				}

				final String selection = "_id=?";
				final String[] selectionArgs = new String[] { split[1] };

				return getDataColumn(context, contentUri, selection, selectionArgs);
			}
		}
		// MediaStore (and general)
		else if ("content".equalsIgnoreCase(uri.getScheme())) {

			// Return the remote address
			if (isGooglePhotosUri(uri))
				return uri.getLastPathSegment();

			return getDataColumn(context, uri, null, null);
		}
		// File
		else if ("file".equalsIgnoreCase(uri.getScheme())) {
			return uri.getPath();
		}

		return null;
	}

	public static String getDataColumn(Context context, Uri uri, String selection,
			String[] selectionArgs) {

		Cursor cursor = null;
		final String column = "_data";
		final String[] projection = { column };

		try {
			cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
					null);
			if (cursor != null && cursor.moveToFirst()) {
				final int index = cursor.getColumnIndexOrThrow(column);
				return cursor.getString(index);
			}
		} finally {
			if (cursor != null)
				cursor.close();
		}
		return null;
	}

	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is ExternalStorageProvider.
	 */
	public static boolean isExternalStorageDocument(Uri uri) {
		return "com.android.externalstorage.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is DownloadsProvider.
	 */
	public static boolean isDownloadsDocument(Uri uri) {
		return "com.android.providers.downloads.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is MediaProvider.
	 */
	public static boolean isMediaDocument(Uri uri) {
		return "com.android.providers.media.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is Google Photos.
	 */
	public static boolean isGooglePhotosUri(Uri uri) {
		return "com.google.android.apps.photos.content".equals(uri.getAuthority());
	}
	
}
