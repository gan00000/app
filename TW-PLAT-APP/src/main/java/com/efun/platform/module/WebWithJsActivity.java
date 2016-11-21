package com.efun.platform.module;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
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
import com.efun.core.tools.EfunJSONUtil;
import com.efun.core.tools.EfunLocalUtil;
import com.efun.core.tools.EfunLogUtil;
import com.efun.core.tools.EfunStringUtil;
import com.efun.facebook.share.activity.EfunFBFunctionActivity;
import com.efun.platform.AndroidScape.E_color;
import com.efun.platform.AndroidScape.E_drawable;
import com.efun.platform.AndroidScape.E_id;
import com.efun.platform.AndroidScape.E_layout;
import com.efun.platform.AndroidScape.E_string;
import com.efun.platform.IPlatApplication;
import com.efun.platform.http.request.bean.BaseRequestBean;
import com.efun.platform.module.base.FixedActivity;
import com.efun.platform.module.bean.ConfigInfoBean;
import com.efun.platform.module.game.bean.AchieveSysItemBean;
import com.efun.platform.module.game.bean.GameItemBean;
import com.efun.platform.module.game.bean.GameNewsBean;
import com.efun.platform.module.summary.bean.BannerItemBean;
import com.efun.platform.module.summary.bean.SummaryItemBean;
import com.efun.platform.module.utils.AppUtils;
import com.efun.platform.module.utils.ToastUtils;
import com.efun.platform.module.utils.UrlUtils;
import com.efun.platform.module.welfare.bean.ActItemBean;
import com.efun.platform.utils.Const;
import com.efun.platform.utils.Const.HttpParam;
import com.efun.platform.utils.Const.JsWithAndroidKey;
import com.efun.platform.utils.Const.Web;
import com.efun.platform.widget.TitleView;

/**
 * Web 页面
 * 
 * @author itxuxxey
 * 
 */
public class WebWithJsActivity extends FixedActivity {
	protected boolean DISABLE_SSL_CHECK_FOR_TESTING = false;
	/**
	 * 启动本页的来源
	 */
	private int mComeFrom;
	/**
	 * url
	 */
	private BaseDataBean mBean;
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

	@SuppressLint("NewApi")
	@Override
	public void init(Bundle bundle) {
		if (bundle != null) {
			mComeFrom = bundle.getInt(Web.WEB_GO_KEY);
			if (bundle.getSerializable(Const.BEAN_KEY) != null) {
				mBean = (BaseDataBean) bundle.getSerializable(Const.BEAN_KEY);
			}
		}
		mWebView = (EfunWebView) findViewById(E_id.websit_webview);
		mloadView = (ProgressBar) findViewById(E_id.websit_progressbar);
		emptyView = (RelativeLayout) findViewById(E_id.empty);
		mNotifyImg = (ImageView) emptyView.findViewById(E_id.center_btn);
		mNotifyText = (TextView) emptyView.findViewById(E_id.center_text);

		// 获取系统的TelephonyManager对象
		tm = (TelephonyManager) mContext
				.getSystemService(Context.TELEPHONY_SERVICE);

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
		if (!EfunLocalUtil.isNetworkAvaiable(this)) {
			mWebView.setVisibility(View.GONE);
			emptyView.setVisibility(View.VISIBLE);
			mNotifyImg.setBackgroundResource(E_drawable.efun_pd_error_network);
			mNotifyText.setText(getResources().getString(
					E_string.efun_pd_network_error));
			mNotifyImg.setVisibility(View.VISIBLE);
			mNotifyText.setVisibility(View.VISIBLE);
			mNotifyImg.setOnClickListener(null);
			return;
		}
		checkPage();

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

		int screenDensity = getResources().getDisplayMetrics().densityDpi;
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

		mWebView.setWebViewClient(new WebViewClient() {
			// 加载错误时调用，一般提示错误信息
			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				// TODO Auto-generated method stub
				Log.d("efun", "errorCode:" + errorCode);
				Log.d("efun", "failingUrl:" + failingUrl);
				if (failingUrl.contains(getResources().getString(
						E_string.efun_pd_url_base))
						|| failingUrl.contains(getResources().getString(
								E_string.efun_pd_url_web_base))) {
					mWebView.setVisibility(View.GONE);
					emptyView.setVisibility(View.VISIBLE);
					mNotifyImg.setVisibility(View.VISIBLE);
					mNotifyText.setVisibility(View.VISIBLE);
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
				Log.d("efun", "weburl:" + url);
//				view.loadUrl(url);
				//Uri uri = Uri.parse(url);
				if (url.startsWith("sms:")) {
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
				}else if(url.contains("line.naver.jp")){
//					Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//					startActivity(intent);
					AppUtils.comeDownloadPageInAndroidWeb(mContext, url);
					return true;
				}else if(url.startsWith("whatsapp://send")){
//					Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//					startActivity(intent);
					url = url.replaceAll("whatsapp://send", "http://m.efuntw.com/whatsappShare.html");
					AppUtils.comeDownloadPageInAndroidWeb(mContext, url);
					return true;
				} else if (url.toLowerCase().startsWith("http") || url.toLowerCase().startsWith("https") || url.toLowerCase().startsWith("file")) {
					view.loadUrl(url);
				} else {
					try {
						EfunLogUtil.logI("uri:" + url);
						Uri uri = Uri.parse(url);
						Intent intent = new Intent(Intent.ACTION_VIEW, uri);
						startActivity(intent);
					} catch (Exception e) {
						Log.d("JSLogs", "Webview Error:" + e.getMessage());
						//view.loadUrl(url);
					}
				}
				return true;
//				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//				startActivity(intent);
//				return true;
			}

		});

		mWebView.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				// TODO Auto-generated method stub
				super.onProgressChanged(view, newProgress);
				mloadView.setProgress(newProgress);
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
		}

	}

	@Override
	public void initTitle(TitleView titleView) {
		switch (mComeFrom) {
		case Web.WEB_GO_GAME_ACHIEVE:
			titleView.setTitleRightStatus(View.GONE);
			break;
		case Web.WEB_GO_PERSON_RECHAR_RECORD:
			titleView.setTitleRightTextRes(E_string.efun_pd_close);
			titleView.setTitleRightTVRes(E_drawable.efun_pd_cons_00a0e9_bg);
			titleView.setTitleRightTextColor(E_color.e_00a0e9);
			titleView.setTitleLeftStatus(View.GONE);
			break;
		case Web.WEB_GO_PERSON_SCATCH:
			titleView.setTitleLeftStatus(View.GONE);
			titleView.setTitleRightTextRes(E_string.efun_pd_close);
			titleView.setTitleRightTVRes(E_drawable.efun_pd_cons_00a0e9_bg);
			titleView.setTitleRightTextColor(E_color.e_00a0e9);
			break;
		case Web.WEB_GO_PERSON_RECHAR:
			titleView.setTitleRightTextRes(E_string.efun_pd_close);
			titleView.setTitleRightTVRes(E_drawable.efun_pd_cons_00a0e9_bg);
			titleView.setTitleRightTextColor(E_color.e_00a0e9);
			titleView.setTitleLeftStatus(View.GONE);
			break;
		case Web.WEB_GO_PERSON_EMAIL_SYS:
			titleView.setTitleRightTextRes(E_string.efun_pd_close);
			titleView.setTitleRightTVRes(E_drawable.efun_pd_cons_00a0e9_bg);
			titleView.setTitleRightTextColor(E_color.e_00a0e9);
			titleView.setTitleLeftStatus(View.GONE);
			break;
		case Web.WEB_GO_EGG://砸蛋
			titleView.setTitleLeftStatus(View.GONE);
			titleView.setTitleRightTextRes(E_string.efun_pd_close);
			titleView.setTitleRightTVRes(E_drawable.efun_pd_cons_00a0e9_bg);
			titleView.setTitleRightTextColor(E_color.e_00a0e9);
			titleView.setTitleCenterRes(E_string.efun_pd_egg, false);
			break;
		case Web.WEB_GO_VIP:
			titleView.setTitleLeftStatus(View.GONE);
			titleView.setTitleRightTextRes(E_string.efun_pd_close);
			titleView.setTitleRightTVRes(E_drawable.efun_pd_cons_00a0e9_bg);
			titleView.setTitleRightTextColor(E_color.e_00a0e9);
			titleView.setTitleCenterRes(E_string.efun_pd_vip, false);
			break;
		default:
			titleView.setTitleRightTextRes(E_string.efun_pd_close);
			titleView.setTitleRightTVRes(E_drawable.efun_pd_cons_00a0e9_bg);
			titleView.setTitleRightTextColor(E_color.e_00a0e9);
			titleView.setTitleLeftStatus(View.GONE);
			break;
		}

	}

	@Override
	public void onClickRight() {
		// TODO Auto-generated method stub
		super.onClickRight();
		switch (mComeFrom) {
		case Web.WEB_GO_GAME_ACHIEVE://成就系统
			mWebView = null;
			break;
		case Web.WEB_GO_PERSON_RECHAR_RECORD://储值记录
			finish();
			mWebView = null;
			break;
		case Web.WEB_GO_PERSON_SCATCH://储值刮刮乐
			finish();
			mWebView = null;
			break;
		case Web.WEB_GO_PERSON_FUNCTIONS://功能块
			finish();
			mWebView = null;
			break;
		case Web.WEB_GO_PERSON_RECHAR://储值
			finish();
			mWebView = null;
			break;
		case Web.WEB_GO_PERSON_EMAIL_SYS://信件系统
			finish();
			mWebView = null;
			break;
		case Web.WEB_GO_EGG://砸蛋
			finish();
			mWebView = null;
			break;
		case Web.WEB_GO_VIP://VIP
			finish();
			mWebView = null;
			break;
		default:
			finish();
			mWebView = null;
			break;
		}
	}

	private void checkPage() {
		String url = "http://www.efuntw.com";
		switch (mComeFrom) {
		case Web.WEB_GO_GAME_ACHIEVE://成就系统
			url = ((AchieveSysItemBean) mBundle
					.getSerializable(Const.BEAN_KEY)).getTaskUrl();
			mWebView.loadUrl(((AchieveSysItemBean) mBundle
					.getSerializable(Const.BEAN_KEY)).getTaskUrl());
			break;
		case Web.WEB_GO_PERSON_RECHAR_RECORD://储值记录
//			mWebView.loadUrl(createRechargeRechordUrl());
			url = ((ConfigInfoBean) mBundle
					.getSerializable(Const.BEAN_KEY)).getUrl();
			mWebView.loadUrl(((ConfigInfoBean) mBundle
					.getSerializable(Const.BEAN_KEY)).getUrl());
			break;
		case Web.WEB_GO_PERSON_SCATCH://储值刮刮乐
//			url = createScatchUrl();
//			mWebView.loadUrl(createScatchUrl());
			url = ((ConfigInfoBean) mBundle
					.getSerializable(Const.BEAN_KEY)).getUrl();
			mWebView.loadUrl(((ConfigInfoBean) mBundle
					.getSerializable(Const.BEAN_KEY)).getUrl());
			break;
		case Web.WEB_GO_PERSON_FUNCTIONS:
			url = ((ConfigInfoBean) mBundle
					.getSerializable(Const.BEAN_KEY)).getUrl();
			mWebView.loadUrl(((ConfigInfoBean) mBundle
					.getSerializable(Const.BEAN_KEY)).getUrl());
			break;
		case Web.WEB_GO_PERSON_RECHAR://储值
			url = ((ConfigInfoBean) mBundle
					.getSerializable(Const.BEAN_KEY)).getUrl();
			mWebView.loadUrl(((ConfigInfoBean) mBundle
					.getSerializable(Const.BEAN_KEY)).getUrl());
			break;
		case Web.WEB_GO_PERSON_EMAIL_SYS://信件系统
			url = ((ConfigInfoBean) mBundle
					.getSerializable(Const.BEAN_KEY)).getUrl();
			mWebView.loadUrl(((ConfigInfoBean) mBundle
					.getSerializable(Const.BEAN_KEY)).getUrl());
			break;
		case Web.WEB_GO_EGG://砸蛋
			url = ((ConfigInfoBean) mBundle
					.getSerializable(Const.BEAN_KEY)).getUrl();
			mWebView.loadUrl(((ConfigInfoBean) mBundle
					.getSerializable(Const.BEAN_KEY)).getUrl());
			break;
		case Web.WEB_GO_VIP://VIP
			url = ((ConfigInfoBean) mBundle
					.getSerializable(Const.BEAN_KEY)).getUrl();
			mWebView.loadUrl(((ConfigInfoBean) mBundle
					.getSerializable(Const.BEAN_KEY)).getUrl());
			break;
		default:
			mWebView.loadUrl(url);
			break;
		}
//		mWebView.loadUrl("http://pili.efuntw.com/event/inviteFriends2/mobile/index.html");
		Log.d("efun", "weburl:" + url);
	}

	/**
	 * 储值刮刮乐
	 * 
	 * @return
	 */
	private String createScatchUrl() {
		// TODO Auto-generated method stub
		String baseUrl = null;
		String url = null;
		baseUrl = IPlatApplication.get().getIPlatUrlByKey(
				UrlUtils.ACTIVITY_PRE_KEY);
		String method = mContext.getResources().getString(
				E_string.efun_pd_method_scratch_url);
		String uid = IPlatApplication.get().getUser().getUid();
		String sign = IPlatApplication.get().getUser().getSign();
		String timeStamp = IPlatApplication.get().getUser().getTimestamp();
		url = checkUrl(baseUrl) + method + "?userId=" + uid + "&sign=" + sign
				+ "&timestamp=" + timeStamp;
		return url;
	}

//	/**
//	 * 储值记录
//	 * 
//	 * @return
//	 */
//	private String createRechargeRechordUrl() {
//		// TODO Auto-generated method stub
//		String baseUrl = null;
//		String url = null;
//		baseUrl = IPlatApplication.get().getIPlatUrlByKey(
//				UrlUtils.PLATFORM_PRE_WEB_KEY);
//		String method = mContext.getResources().getString(
//				E_string.efun_pd_method_recharge_record_url);
//		url = checkUrl(baseUrl) + method;
//		return url;
//	}

	/**
	 * 校验链接是否标准
	 * 
	 * @param url
	 * @return
	 */
	private String checkUrl(String url) {
		if (!url.endsWith("/")) {
			url = url + "/";
		}
		return url;
	}

	@Override
	public ViewGroup[] needShowLoading() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int LayoutId() {
		// TODO Auto-generated method stub
		return E_layout.efun_pd_website_with_js;
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

	/**
	 * 设置js调android时的平台参数
	 * 
	 * @return
	 */
	private HashMap<String, String> jsUserAndPlatformParams() {
		HashMap<String, String> mMap = new HashMap<String, String>();
		mMap.put(JsWithAndroidKey.KEY_USERID, IPlatApplication.get().getUser()
				.getUid());
		mMap.put(JsWithAndroidKey.KEY_SIGN, IPlatApplication.get().getUser()
				.getSign());
		mMap.put(JsWithAndroidKey.KEY_TIMESTAMP, IPlatApplication.get()
				.getUser().getTimestamp());
		mMap.put(JsWithAndroidKey.KEY_LANGUAGE, HttpParam.LANGUAGE);// 语言
		mMap.put(JsWithAndroidKey.KEY_VERSION, HttpParam.PLATFORM);// 系统
		mMap.put(JsWithAndroidKey.KEY_PLATFORM, HttpParam.PLATFORM_AREA);// 区域
		mMap.put(JsWithAndroidKey.KEY_PACKAGEVERSION,
				AppUtils.getAppVersionName(mContext));// 平台版本号
		mMap.put(JsWithAndroidKey.KEY_FROMTYPE, HttpParam.APP);
		return mMap;
	}

	/**
	 * 设置js调android时的设备参数
	 * 
	 * @return
	 */
	private HashMap<String, String> jsUserAndDeviceParams() {
		HashMap<String, String> mMap = new HashMap<String, String>();
		mMap.put(JsWithAndroidKey.KEY_DEVICETYPE, Build.MODEL);// 设备名称
		mMap.put(JsWithAndroidKey.KEY_SYSTEMVERSION,
				EfunLocalUtil.getOsVersion());// os
		mMap.put(JsWithAndroidKey.KEY_MAC,
				EfunLocalUtil.getLocalMacAddress(mContext));// mac
		mMap.put(JsWithAndroidKey.KEY_IMEI,
				EfunLocalUtil.getLocalImeiAddress(mContext));// imei
		mMap.put(JsWithAndroidKey.KEY_IP,
				EfunLocalUtil.getLocalIpAddress(mContext));// ip
		mMap.put(JsWithAndroidKey.KEY_SIMOPERATOR,
				tm.getNetworkOperator());// KEY_SIMOPERATOR
		return mMap;
	}

	// 调用手机短信系统，发送短信
	private void sendMessage(String phone, String message) {

		Uri uri = Uri.parse("smsto:" + phone);
		Intent sendIntent = new Intent(Intent.ACTION_SENDTO, uri);
		sendIntent.putExtra("sms_body", message);
		mContext.startActivity(sendIntent);

	}

}
