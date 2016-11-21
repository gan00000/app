package com.efun.platform.module;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.efun.core.component.EfunWebView;
import com.efun.core.js.PlatNative2JS;
import com.efun.platform.IPlatApplication;
import com.efun.platform.module.account.bean.User;
import com.efun.platform.module.utils.UrlUtils;
import com.efun.platform.utils.Const.HttpParam;
import com.efun.platform.utils.Const.RequestCode;

public class PlatformNative2JS extends PlatNative2JS{
	private String KEFU_callback = "";
	private String KEFU_imageUrl = "";
	private String jsValue="";//接收方js返回的数据
	private Activity mActContext;
	private EfunWebView mWebView;
	private boolean isAddPic = false;
	
	public PlatformNative2JS(Context context, EfunWebView efunWebView) {
		super(context, efunWebView);
		// TODO Auto-generated constructor stub
	}

	public PlatformNative2JS(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	//该方法返回一个object对象，此对象会在webview.addJavaScriptInterface(对象,别名)方法中设置一个别名，javascript端可以通过
	// <别名.方法名> 调用object对象中的方法
	public PlatformNative2JS(Context context,Activity actContext,EfunWebView webView) {
		super(context,webView);
		this.mActContext = actContext;
		this.mWebView = webView;
		Log.i("yang","----------------openForJava--------Looper:" + Looper.myLooper());
		// TODO Auto-generated constructor stub
	}
	
	@JavascriptInterface
	@Override
	public String getSdkInfo(String jsJson) {
		// TODO Auto-generated method stub
		return super.getSdkInfo(jsJson);
	}

	@JavascriptInterface
	public void getSdkImage(String str) {
		isAddPic = true;
		jsValue=str;
//		requestPermissions(CUSTOMER_SERVICE);
		try {
			JSONObject job = new JSONObject(jsValue);
			String key = job.getString("key");
			if (key != null && !key.equals("")) {
				if (key.equals("imageInfo")) {
					String callback = job.getString("callback");
					if (callback != null && !callback.equals("")) {
						KEFU_callback = callback;
						Intent intent = new Intent(
								Intent.ACTION_PICK,
								android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
						mActContext.startActivityForResult(intent, RequestCode.REQ_FRAGMENT_JS_UPLOAD_PICTURE);
						//
					}
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e("yang", "json exception:" + e.getMessage());

		}
	}
	
	//关闭页面
	@JavascriptInterface
	public void closeCurPage(String str){
		if(mActContext != null){
//			IPlatApplication.get().setInteractionWebNeedFresh(true);
			mActContext.finish();
		}
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.e("yang", "webEltyFrg:"+requestCode);
		if (resultCode == Activity.RESULT_OK) {
			Uri uri = data.getData();

			String[] proj = { MediaStore.Images.Media.DATA };

			Cursor actualimagecursor = (mActContext).managedQuery(uri, proj, null, null, null);

			int actual_image_column_index = actualimagecursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

			actualimagecursor.moveToFirst();

			String img_path = actualimagecursor
					.getString(actual_image_column_index);
			Log.i("yang", img_path);

			File file = new File(img_path);
			String name = file.getName();
			String picName, picType;
			if (name.contains(".")) {
				picType = name.substring(name.lastIndexOf(".") + 1);
				picName = name.substring(0, name.lastIndexOf("."));
			} else {
				picType = "png";
				picName = name;
			}

			String pathStr = IPlatApplication.get().getIPlatUrlByKey(UrlUtils.IMG_UPLOAD_PRE_KEY);
			if (!pathStr.endsWith("/")) {
				pathStr = pathStr + "/";
			}
			String path = pathStr + "image_uploadBybytes.shtml?";
//			LoginParameters params = EfunRestarGameCallback.getInstands()
//					.getUser(this);
			StringBuffer stringBuffer = new StringBuffer(path);
			stringBuffer
					.append("&group=")
					.append("pfen")
					.append("&suffix=")
					.append(picType)
					.append("&imgName=")
					.append(picName)
					.append("&typeNo=")
					.append("img001")
					.append("&gameCode=")
					.append(HttpParam.PLATFORM_CODE);
			User mUser = IPlatApplication.get().getUser();
			if(mUser != null){
				stringBuffer.append("&userid=")
				.append(mUser.getUid())
				.append("&timestamp=")
				.append(mUser.getTimestamp())
				.append("&signature=")
				.append(mUser.getSign());
//				.append("&signature="
//						+ EfunStringUtil.toMd5(
//								EfunResourceUtil.findStringByName(this,
//										"efunAppKey")
//										+ mUser.getUid()
//										+ mUser.getTimestamp(), false));
			}
			Log.i("yang", "上传图片地址" + stringBuffer.toString());
			new Thread(new PlatformNative2JS.Run(stringBuffer.toString(),
					file)).start();
		}
	}
	
	private ProgressDialog uploadDialog;
	private Handler handle = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 0) {
				if (uploadDialog == null) {
					uploadDialog = new ProgressDialog(mActContext);
					uploadDialog.setCanceledOnTouchOutside(false);
					uploadDialog.setMessage("上传图片中，请耐心等候...");
				}
				if (!uploadDialog.isShowing()) {
					uploadDialog.show();
				}
			}
			if (msg.what == 1) {
				if (uploadDialog != null) {
					try {
						if (uploadDialog.isShowing())
							uploadDialog.dismiss();
						uploadDialog = null;
					} catch (Exception e) {
						Log.e("yang", "上传图片部分取消dialog失败" + e.getMessage());
					}
				}
				Object ob = msg.obj;
				if (ob != null) {
					String result = ob.toString();
					if (result != null && !result.equals("")) {
						try {
							JSONObject job = new JSONObject(result);
							String code = job.getString("code");
							if (code != null && code.equals("1000")) {
								final String imgurl = job.getString("imgUrl");
								KEFU_imageUrl = "{'imageUrl':'" + imgurl + "'}";
								if (mWebView != null) {
									if (KEFU_callback != null
											&& !KEFU_callback.equals("")) {
										mWebView.post(new Runnable() {

											@Override
											public void run() {
												// TODO Auto-generated method
												// stub
												Log.i("efun", "javascript:"
														+ KEFU_callback + "("
														+ KEFU_imageUrl + ")");
												mWebView.loadUrl("javascript:"
														+ KEFU_callback + "("
														+ KEFU_imageUrl + ")");
											}
										});

									}
								} else {
									Log.e("yang",
											"WebView不存在，请确认EfunBaseJsInterface中的Wv是否指向继承他的之类WebView实例");
								}
							} else {
								Toast.makeText(mActContext,
										job.getString("message"),
										Toast.LENGTH_LONG).show();
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				}
			}
		}
	};
	
	class Run implements Runnable {
		private String path;
		private File file;

		public Run(String path, File file) {
			this.path = path;
			this.file = file;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			Message msg = handle.obtainMessage();
			msg.what = 0;
			handle.sendMessage(msg);
			try {
				URL url = new URL(path);
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				// conn.setReadTimeout(TIME_OUT);
				// conn.setConnectTimeout(TIME_OUT);
				conn.setRequestProperty("accept", "*/*");
				conn.setRequestProperty("connection", "Keep-Alive");
				conn.setRequestProperty("content-type",
						"text/html;charset=utf-8");
				conn.setDoOutput(true);
				conn.setDoInput(true);
				conn.setRequestMethod("POST"); // 请求方式
				{
					// 上传图片
					FileInputStream in = new FileInputStream(file);

					byte[] temp = new byte[in.available()];
					in.read(temp);
					OutputStream out = conn.getOutputStream();
					out.write(temp);
					out.close();
					in.close();
				}
				String result = "";// 请求服务器端的返回结果
				// 获取服务端请求结果
				{
					InputStream inputStream = conn.getInputStream();
					ByteArrayOutputStream arrayOutStream = new ByteArrayOutputStream();
					int len = -1;
					byte[] temp = new byte[1024];
					while ((len = inputStream.read(temp)) != -1) {
						arrayOutStream.write(temp, 0, len);
					}
					result = new String(arrayOutStream.toByteArray(), "utf-8");
					arrayOutStream.close();
					inputStream.close();
					Log.i("yang", result + "");
					if (result != null && !result.equals("")) {
						msg = new Message();
						msg.what = 1;
						msg.obj = result;
						handle.sendMessage(msg);
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
				msg = new Message();
				msg.what = 1;
				msg.obj = null;
				handle.sendMessage(msg);
				Log.e("yang", "上传图片失败:" + e.getMessage());
			} finally {

			}
		}

	}

	public boolean isAddPic() {
		return isAddPic;
	}

	public void setAddPic(boolean isAddPic) {
		this.isAddPic = isAddPic;
	}
	
}
