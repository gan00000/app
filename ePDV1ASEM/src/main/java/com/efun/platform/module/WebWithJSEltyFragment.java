package com.efun.platform.module;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources.NotFoundException;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.efun.core.component.EfunWebView;
import com.efun.core.js.Native2JS;
import com.efun.core.tools.EfunJSONUtil;
import com.efun.core.tools.EfunLocalUtil;
import com.efun.core.tools.EfunLogUtil;
import com.efun.core.tools.EfunStringUtil;
import com.efun.platform.AndroidScape.E_drawable;
import com.efun.platform.AndroidScape.E_id;
import com.efun.platform.AndroidScape.E_layout;
import com.efun.platform.AndroidScape.E_string;
import com.efun.platform.IPlatApplication;
import com.efun.platform.http.request.bean.BaseRequestBean;
import com.efun.platform.module.VideoWebActivity.xWebChromeClient;
import com.efun.platform.module.base.FixedFragment;
import com.efun.platform.module.bean.ConfigInfoBean;
import com.efun.platform.module.utils.AppUtils;
import com.efun.platform.module.utils.CustomProgressDialog;
import com.efun.platform.module.utils.ProcessDatasUtils;
import com.efun.platform.module.utils.UserUtils;
import com.efun.platform.utils.Const.HttpParam;
import com.efun.platform.utils.Const.JsWithAndroidKey;

/**
 * tab - 网页加载
 * 
 * @author harvery
 * 
 */
public class WebWithJSEltyFragment extends FixedFragment {
	protected AlertDialog dialog;
	protected final String JAVASCRIPTINTERFACE_KEFU = "ESDK";

	protected boolean DISABLE_SSL_CHECK_FOR_TESTING = false;
	/**
	 * 当頁面没有数据的时候显示的内容 ，参考{@link ListView#setEmptyView(View)}
	 */
	private RelativeLayout emptyView;
	/**
	 * 网络异常时显示的图片
	 */
	private ImageView mNotifyImg;
	/**
	 * 网络异常时显示的文本内容
	 */
	public TextView mNotifyText;

	// 获取系统的TelephonyManager对象
	private TelephonyManager tm;

	private EfunWebView mWebView;
	private ProgressBar mloadView;
	private ConfigInfoBean mSummaryBean, mConnectionBean;
	private String tabFlag;
	private Native2JS mJSUsedObject;
	private CustomProgressDialog progressDialog;

	@Override
	public void initViews() {
		// TODO Auto-generated method stub
		mWebView = (EfunWebView) childView.findViewById(E_id.websit_webview);
		mloadView = (ProgressBar) childView
				.findViewById(E_id.websit_progressbar);
		emptyView = (RelativeLayout) childView.findViewById(E_id.empty);
		mNotifyImg = (ImageView) emptyView.findViewById(E_id.center_btn);
		mNotifyText = (TextView) emptyView.findViewById(E_id.center_text);
		progressDialog = (CustomProgressDialog) childView
				.findViewById(E_id.js_custom_progress);
		mSummaryBean = ProcessDatasUtils.getSummaryInfo();
		mConnectionBean = ProcessDatasUtils.getConnectionInfo();
		tabFlag = IPlatApplication.get().getTabFlag();

		// 获取系统的TelephonyManager对象
		tm = (TelephonyManager) getContext().getSystemService(
				Context.TELEPHONY_SERVICE);

		mNotifyImg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View paramView) {
				mNotifyImg
						.setBackgroundResource(E_drawable.efun_pd_error_timeout);
				mNotifyText.setText(E_string.efun_pd_timeout_error);
				emptyView.setVisibility(View.GONE);
				mNotifyImg.setVisibility(View.GONE);
				mNotifyText.setVisibility(View.GONE);
				mWebView.setVisibility(View.VISIBLE);
				checkPage();
			}

		});

		setWebViewSetting();
		if (!EfunLocalUtil.isNetworkAvaiable(getContext())) {
			mWebView.setVisibility(View.GONE);
			emptyView.setVisibility(View.VISIBLE);
			mNotifyImg.setBackgroundResource(E_drawable.efun_pd_error_network);
			String des = "";
			try {
				des = getActivity().getResources().getString(
						E_string.efun_pd_network_error);
			} catch (Exception e) {
				// TODO: handle exception
				 des = "";
			}
			mNotifyText.setText(des);
			mNotifyImg.setVisibility(View.VISIBLE);
			mNotifyText.setVisibility(View.VISIBLE);
			mNotifyImg.setOnClickListener(null);
			return;
		}
//		Log.i("yang", "IPlatApplication.get().isFindWebNeedFresh():"+IPlatApplication.get().isFindWebNeedFresh());
//		Log.i("yang", "IPlatApplication.get().isInteractionWebNeedFresh():"+IPlatApplication.get().isInteractionWebNeedFresh());
//		if(IPlatApplication.get().isFindWebNeedFresh()){
//			if (tabFlag.equals("Games")) {
//				checkPage();
//			}
//		}else if(IPlatApplication.get().isInteractionWebNeedFresh()){
//			if (tabFlag.equals("CustomService")) {
//				checkPage();
//			}
//		}
		checkPage();
	}

	public void checkPage() {
		// TODO Auto-generated method stub
		Log.e("yang", "tabFlag:" + tabFlag);
		if (UserUtils.isLogin()) {
			setWebViewSetting();
			if (!EfunStringUtil.isEmpty(tabFlag)) {
				if (tabFlag.equals("Find")) {
					if (mSummaryBean != null) {
						mWebView.loadUrl(mSummaryBean.getUrl());
						Log.e("yang", "mWebView--url:" + mSummaryBean.getUrl());
//						IPlatApplication.get().setFindWebNeedFresh(false);
					}
				} else if (tabFlag.equals("Interaction")) {
					if (mConnectionBean != null) {
						mWebView.loadUrl(mConnectionBean.getUrl());
						// mWebView.loadUrl("http://m.efunen.com/testTools.html");
						Log.e("yang",
								"mWebView--url:" + mConnectionBean.getUrl());
//						IPlatApplication.get().setInteractionWebNeedFresh(false);
					}
				}
			} else {
				Log.e("yang", "mWebView--url:http://m.efunen.com");
				mWebView.loadUrl("http://m.efunen.com");
			}
			progressDialog.setVisibility(View.VISIBLE);
		}

	}

	@SuppressLint("SetJavaScriptEnabled")
	private void setWebViewSetting() {
		WebSettings settings = mWebView.getSettings();
		settings.setJavaScriptEnabled(true);
		settings.setUseWideViewPort(true);
		settings.setLoadWithOverviewMode(true);
		settings.setBuiltInZoomControls(true);
		settings.setSupportZoom(true);
		settings.setDomStorageEnabled(true);
		settings.setDefaultFontSize(20);// 设置默认的字体大小
		settings.setDisplayZoomControls(false);

		int screenDensity = -1;
		try {
			screenDensity = getActivity().getResources().getDisplayMetrics().densityDpi;
		} catch (Exception e1) {
			screenDensity = -1;
		}
		WebSettings.ZoomDensity zoomDensity = WebSettings.ZoomDensity.MEDIUM;
		switch (screenDensity) {
		case DisplayMetrics.DENSITY_LOW:
			zoomDensity = WebSettings.ZoomDensity.CLOSE;
			break;
		case DisplayMetrics.DENSITY_MEDIUM:
			zoomDensity = WebSettings.ZoomDensity.MEDIUM;
			break;
		case DisplayMetrics.DENSITY_HIGH:
			zoomDensity = WebSettings.ZoomDensity.FAR;
			break;
		}
		settings.setDefaultZoom(zoomDensity);
		// 设置自动加载图片
		settings.setLoadsImagesAutomatically(true);
		// 如果需要用户输入账号密码，必须设置支持手势焦点
		mWebView.requestFocusFromTouch();

		// mWebView.addJavascriptInterface(openForJava(),
		// JAVASCRIPTINTERFACE_KEFU);

		mWebView.setJsObject(openForJava());

		mWebView.setWebViewClient(new WebViewClient() {
			// 加载错误时调用，一般提示错误信息
			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				// TODO Auto-generated method stub
				Log.d("efun", "errorCode:" + errorCode);
				Log.d("efun", "failingUrl:" + failingUrl);
				try {
					if (failingUrl.contains(getResources().getString(
							E_string.efun_pd_url_base))
							|| failingUrl.contains(getResources().getString(
									E_string.efun_pd_url_web_base))) {
						mWebView.setVisibility(View.GONE);
						emptyView.setVisibility(View.VISIBLE);
						mNotifyImg.setVisibility(View.VISIBLE);
						mNotifyText.setVisibility(View.VISIBLE);
					}
				} catch (Exception e) {
					Log.e("efun", "NotFoundException");
				}
			}

			@Override
			public void onReceivedSslError(WebView view,
					SslErrorHandler handler, SslError error) {
				// TODO Auto-generated method stub
				// super.onReceivedSslError(view, handler, error);
				// handler.proceed();
				if (DISABLE_SSL_CHECK_FOR_TESTING) {
					super.onReceivedSslError(view, handler, error);
					handler.cancel();
				} else {
					handler.proceed();
				}
			}

			@Override
			public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
				// TODO Auto-generated method stub
				return super.shouldOverrideKeyEvent(view, event);
			}

			// 用于加载新Webview之前，一般在此加载缓冲区
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				// TODO Auto-generated method stub
				super.onPageStarted(view, url, favicon);
				mloadView.setVisibility(View.VISIBLE);
				progressDialog.setVisibility(View.GONE);
			}

			// 用于加载新Webview之后，一般在此消除缓冲区
			@Override
			public void onPageFinished(WebView view, String url) {
				// TODO Auto-generated method stub
				super.onPageFinished(view, url);
				mloadView.setVisibility(View.GONE);
			}

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				progressDialog.setVisibility(View.VISIBLE);
				Log.d("efun", "weburl:" + url);
				// view.loadUrl(url);
				Uri uri = Uri.parse(url);
				if (uri.toString().startsWith("sms:")) {
					try {
						url = URLDecoder.decode(url, "utf-8");
					} catch (UnsupportedEncodingException e) {
						Log.e("efun", "url编码转换错误" + url);
						return true;
					}
					String[] str = url.split("\\?");
					String[] str1 = str[0].split("\\:");
					String[] str2 = str[1].split("\\=");
					sendMessage(str1[1], str2[1]);
				} else if (uri.toString().contains("line.naver.jp")) {
					// Intent intent = new Intent(Intent.ACTION_VIEW,
					// Uri.parse(url));
					// startActivity(intent);
					AppUtils.comeDownloadPageInAndroidWeb(getContext(), url);
					return true;
				} else if (uri.toString().startsWith("whatsapp://send")) {
					// Intent intent = new Intent(Intent.ACTION_VIEW,
					// Uri.parse(url));
					// startActivity(intent);
					url = url.replaceAll("whatsapp://send",
							"http://m.efuntw.com/whatsappShare.html");
					AppUtils.comeDownloadPageInAndroidWeb(getContext(), url);
					return true;
				} else {
					view.loadUrl(url);
				}

				return true;
				// Intent intent = new Intent(Intent.ACTION_VIEW,
				// Uri.parse(url));
				// startActivity(intent);
				// return true;
			}

		});

		mWebView.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				// TODO Auto-generated method stub
				super.onProgressChanged(view, newProgress);
				mloadView.setProgress(newProgress);
			}

			// 扩展支持alert事件
			@Override
			public boolean onJsAlert(WebView view, String url, String message,
					final JsResult result) {
				Log.i("yang", "onJsAlert");

				showDialog(message, result);

				return true;
			}

			@Override
			public boolean onJsConfirm(WebView view, String url,
					String message, final JsResult result) {
				Log.i("yang", "onJsConfirm");
				showDialog(message, result);

				return true;
			}

			@Override
			public boolean onJsPrompt(WebView view, String url, String message,
					String defaultValue, final JsPromptResult result) {
				Log.i("yang", "onJsPrompt");
				showDialog(message, result);
				return true;
			}
			
			private void showDialog(String message, final JsResult result) {
				try {
					AlertDialog.Builder builder = new AlertDialog.Builder(
							getContext());
					builder.setTitle("Notification")
							.setMessage(message)
							.setNeutralButton("OK",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											// TODO Auto-generated method stub
											result.confirm();
										}
									})
							.setNegativeButton("NO",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											// TODO Auto-generated method stub
											result.cancel();
											dialog.dismiss();
										}
									});
					builder.setCancelable(false);
					// builder.setIcon(R.drawable.ic_launcher);
					if (dialog != null && dialog.isShowing()) {
						try {
							dialog.dismiss();
							dialog = null;
						} catch (Exception e) {
						}
					}
					dialog = builder.create();
					dialog.show();
				} catch (Exception e) {

				}
			}
		});

		// 加入js调原生相关参数
		String pParams = "";
		String dParams = "";
		HashMap<String, String> mMap = null;
		try {
			pParams = EfunJSONUtil.map2jsonString(jsUserAndPlatformParams());
			dParams = EfunJSONUtil.map2jsonString(jsUserAndDeviceParams());
			mMap = new HashMap<String, String>();
			mMap.put("platformInfo", pParams);
			mMap.put("deviceInfo", dParams);

			String paramms = EfunJSONUtil.map2jsonString(mMap);
			EfunLogUtil.logI("efun", "platformInfo：" + pParams);
			EfunLogUtil.logI("efun", "deviceInfo：" + dParams);
			EfunLogUtil.logI("efun", "paramms：" + paramms);

		} catch (JSONException e) {
			EfunLogUtil.logE("js调原生设置时出现异常:" + e.getMessage());
			return;
		}
		if (mMap != null) {
			mWebView.send2JS(mMap);
			// mJSUsedObject.setMap(mMap);
		}

	}

	private Native2JS openForJava() {
		// TODO Auto-generated method stub
		mJSUsedObject = new PlatformNative2JS(getContext(), getActivity(),
				mWebView);

		return mJSUsedObject;
	}

	@Override
	public ViewGroup[] needShowLoading() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean needRequestData() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public BaseRequestBean[] needRequestDataBean() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int LayoutId() {
		// TODO Auto-generated method stub
		return E_layout.efun_pd_fragment_website_with_js;
	}

	@Override
	public void initListeners() {
		// TODO Auto-generated method stub

	}

	/**
	 * 设置js调android时的平台参数
	 * 
	 * @param map
	 * @return
	 */
	private HashMap<String, String> jsUserAndPlatformParams() {
		HashMap<String, String> mMap = new HashMap<String, String>();
		if (IPlatApplication.get().getUser() != null) {
			mMap.put(JsWithAndroidKey.KEY_USERID, IPlatApplication.get()
					.getUser().getUid());
			mMap.put(JsWithAndroidKey.KEY_SIGN, IPlatApplication.get()
					.getUser().getSign());
			mMap.put(JsWithAndroidKey.KEY_TIMESTAMP, IPlatApplication.get()
					.getUser().getTimestamp());
		}
		mMap.put(JsWithAndroidKey.KEY_LANGUAGE, HttpParam.LANGUAGE);// 语言
		mMap.put(JsWithAndroidKey.KEY_VERSION, HttpParam.PLATFORM);// 系统
		mMap.put(JsWithAndroidKey.KEY_PLATFORM, HttpParam.PLATFORM_AREA);// 区域
		mMap.put(JsWithAndroidKey.KEY_PACKAGEVERSION,
				AppUtils.getAppVersionName(getContext()));// 平台版本号
		mMap.put(JsWithAndroidKey.KEY_APPGAMECODE, HttpParam.PLATFORM_CODE);// gameCode
		mMap.put(JsWithAndroidKey.KEY_FROMTYPE, HttpParam.APP);
		return mMap;
	}

	/**
	 * 设置js调android时的设备参数
	 * 
	 * @param map
	 * @return
	 */
	private HashMap<String, String> jsUserAndDeviceParams() {
		HashMap<String, String> mMap = new HashMap<String, String>();
		mMap.put(JsWithAndroidKey.KEY_DEVICETYPE, Build.MODEL);// 设备名称
		mMap.put(JsWithAndroidKey.KEY_SYSTEMVERSION,
				EfunLocalUtil.getOsVersion());// os
		mMap.put(JsWithAndroidKey.KEY_MAC,
				EfunLocalUtil.getLocalMacAddress(getContext()));// mac
		mMap.put(JsWithAndroidKey.KEY_IMEI,
				EfunLocalUtil.getLocalImeiAddress(getContext()));// imei
		mMap.put(JsWithAndroidKey.KEY_IP,
				EfunLocalUtil.getLocalIpAddress(getContext()));// ip
		mMap.put(JsWithAndroidKey.KEY_SIMOPERATOR, tm.getNetworkOperator());// KEY_SIMOPERATOR
		return mMap;
	}

	// 调用手机短信系统，发送短信
	private void sendMessage(String phone, String message) {

		Uri uri = Uri.parse("smsto:" + phone);
		Intent sendIntent = new Intent(Intent.ACTION_SENDTO, uri);
		sendIntent.putExtra("sms_body", message);
		getContext().startActivity(sendIntent);

	}

	public void onResume() {
		Log.i("yang", "webOnResume");
		super.onResume();
		checkPage();
	};

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		Log.e("yang", "11111111111111111webWithJS:");
		if (mJSUsedObject != null) {
			((PlatformNative2JS) mJSUsedObject).onActivityResult(requestCode,
					resultCode, data);
		}
	}
}
