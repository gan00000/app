package com.efun.platform.module;

import java.util.HashMap;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
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
import com.efun.facebook.share.activity.EfunFBFunctionActivity;
import com.efun.platform.AndroidScape.E_color;
import com.efun.platform.AndroidScape.E_drawable;
import com.efun.platform.AndroidScape.E_id;
import com.efun.platform.AndroidScape.E_layout;
import com.efun.platform.AndroidScape.E_string;
import com.efun.platform.IPlatApplication;
import com.efun.platform.http.request.bean.BaseRequestBean;
import com.efun.platform.module.base.FixedActivity;
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
 * @author Jesse
 * 
 */
public class WebActivity extends FixedActivity {
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

	@Override
	public void onClickRight() {
		switch (mComeFrom) {
		case Web.WEB_GO_BANNER:
			mWebView = null;
			break;
		case Web.WEB_GO_SUMMARY:
			mWebView = null;
			break;
		case Web.WEB_GO_ACT:
			mWebView = null;
			break;
		case Web.WEB_GO_GAME:
			mWebView = null;
			break;
		default:
			finish();
			mWebView = null;
			break;
		}
	}

	private EfunWebView mWebView;
	private ProgressBar mloadView;

	@SuppressLint("NewApi")
	@Override
	public void init(Bundle bundle) {
		if (bundle != null) {
			mComeFrom = bundle.getInt(Web.WEB_GO_KEY);
			if(bundle.getSerializable(Const.BEAN_KEY)!=null){
				mBean = (BaseDataBean) bundle.getSerializable(Const.BEAN_KEY);
			}
		}
		mWebView = (EfunWebView) findViewById(E_id.websit_webview);
		mloadView = (ProgressBar)findViewById(E_id.websit_progressbar);
		emptyView = (RelativeLayout)findViewById(E_id.empty);
		mNotifyImg = (ImageView) emptyView.findViewById(E_id.center_btn);
		mNotifyText = (TextView) emptyView.findViewById(E_id.center_text);
		
		// 获取系统的TelephonyManager对象
		tm = (TelephonyManager) mContext
						.getSystemService(Context.TELEPHONY_SERVICE);
		
		mNotifyImg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View paramView) {
				mNotifyImg.setBackgroundResource(E_drawable.efun_pd_error_timeout);
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
			mNotifyText.setText(getResources().getString(E_string.efun_pd_network_error));
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
//				handler.proceed();
				if (DISABLE_SSL_CHECK_FOR_TESTING) {
					super.onReceivedSslError(view, handler, error);
					handler.cancel();
				}else{
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
				view.loadUrl(url);
				return true;
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

	private String createEggUrl() {
		String sign = "";
		String timestamp = "";
		if(IPlatApplication.get().getUser()!=null){
			sign = IPlatApplication.get().getUser().getSign();
			timestamp = IPlatApplication.get().getUser().getTimestamp();
		}
		String url = IPlatApplication.get().getIPlatUrlByKey(
				UrlUtils.PLATFORM_PRE_WEB_KEY)
				+ mContext.getString(E_string.efun_pd_method_knock_egg);
		//添加userName,sign,timestamp 20150303
//		url = url + "?uid=" + IPlatApplication.get().getUser().getUid()+"&userName="+IPlatApplication.get().getUser().getUsername()
//				+ "&fromType=" + HttpParam.APP+"&sign="+IPlatApplication.get().getUser().getSign()+"&timestamp="+IPlatApplication.get().getUser().getTimestamp();
		url = url + "?uid=" + IPlatApplication.get().getUser().getUid()
				+ "&fromType=" + HttpParam.APP + "&sign=" +sign+ "&timestamp=" + timestamp;
		EfunLogUtil.logD("eggUrl:"+url);
		return url;
	}

	private String createMemberDescUrl() {
		String url = IPlatApplication.get().getIPlatUrlByKey(
				UrlUtils.PLATFORM_PRE_WEB_KEY)
				+ mContext.getString(E_string.efun_pd_method_member_desc);
		return url;
	}

	private String createAboutUsUrl() {
		String url = IPlatApplication.get().getIPlatUrlByKey(
				UrlUtils.PLATFORM_PRE_WEB_KEY)
				+ mContext.getString(E_string.efun_pd_method_about_us);
		return url;
	}
	
	private String createVipUrl() {
		String sign = "";
		String timestamp = "";
		if(IPlatApplication.get().getUser()!=null){
			sign = IPlatApplication.get().getUser().getSign();
			timestamp = IPlatApplication.get().getUser().getTimestamp();
		}
		String url = IPlatApplication.get().getIPlatUrlByKey(
				UrlUtils.PLATFORM_PRE_WEB_KEY)
				+ mContext.getString(E_string.efun_pd_method_vip);
		
		url = url +"?fromType="+HttpParam.APP+"&uid="+IPlatApplication.get().getUser().getUid()+"&userName="+IPlatApplication.get().getUser().getAccountName()
				+"&sign="+sign+"&timestamp="+timestamp
				+ "&language=" + HttpParam.LANGUAGE + "&gameCode=" + HttpParam.PLATFORM_CODE + "&version=" + HttpParam.PLATFORM 
				+ "&platform=" + HttpParam.PLATFORM_AREA + "&packageVersion=" + AppUtils.getAppVersionName(mContext);
		EfunLogUtil.logD("vipUrl:"+url);
		return url;
	}
	
	
	@Override
	public void initTitle(TitleView titleView) {
		switch (mComeFrom) {
		case Web.WEB_GO_BANNER:
			titleView.setTitleRightStatus(View.GONE);
			break;
		case Web.WEB_GO_GAME:
		case Web.WEB_GO_SUMMARY:
			titleView.setTitleCenterRes(E_string.efun_pd_summary_content, false);
			titleView.setTitleRightRes(E_drawable.efun_pd_share_selector);
			hasShare = true;
			titleView.setPopWindowEnable(PopWindow.POP_WINDOW_SHARE, mBean);
			break;
		case Web.WEB_GO_ACT:
			titleView.setPopWindowEnable(PopWindow.POP_WINDOW_SHARE, mBean);
			titleView.setTitleRightRes(E_drawable.efun_pd_share_selector);
			titleView.setTitleCenterRes(E_string.efun_pd_act_content, false);
			break;
		case Web.WEB_GO_EGG:
			titleView.setTitleLeftStatus(View.GONE);
			titleView.setTitleRightTextRes(E_string.efun_pd_close);
			titleView.setTitleRightTVRes(E_drawable.efun_pd_cons_00a0e9_bg);
			titleView.setTitleRightTextColor(E_color.e_00a0e9);
			titleView.setTitleCenterRes(E_string.efun_pd_egg, false);
			break;
		case Web.WEB_GO_GOLDVALUE:
			titleView.setTitleLeftStatus(View.GONE);
			titleView.setTitleRightTextRes(E_string.efun_pd_close);
			titleView.setTitleRightTVRes(E_drawable.efun_pd_cons_00a0e9_bg);
			titleView.setTitleRightTextColor(E_color.e_00a0e9);
			titleView.setTitleCenterRes(E_string.efun_pd_member_desc, false);
			break;
		case Web.WEB_GO_US:
			titleView.setTitleLeftStatus(View.GONE);
			titleView.setTitleRightTextRes(E_string.efun_pd_close);
			titleView.setTitleRightTVRes(E_drawable.efun_pd_cons_00a0e9_bg);
			titleView.setTitleRightTextColor(E_color.e_00a0e9);
			titleView.setTitleCenterRes(E_string.efun_pd_about_us, false);
			break;
		case Web.WEB_GO_VIP:
			titleView.setTitleLeftStatus(View.GONE);
			titleView.setTitleRightTextRes(E_string.efun_pd_close);
			titleView.setTitleRightTVRes(E_drawable.efun_pd_cons_00a0e9_bg);
			titleView.setTitleRightTextColor(E_color.e_00a0e9);
			titleView.setTitleCenterRes(E_string.efun_pd_vip, false);
			break;
		case Web.WEB_GO_GAME_FB:
			titleView.setTitleRightStatus(View.GONE);
			break;
		case Web.WEB_GO_GAME_ACHIEVE:
			titleView.setTitleRightStatus(View.GONE);
			break;
		default:
			titleView.setTitleRightTextRes(E_string.efun_pd_close);
			titleView.setTitleRightTVRes(E_drawable.efun_pd_cons_00a0e9_bg);
			titleView.setTitleRightTextColor(E_color.e_00a0e9);
			titleView.setTitleLeftStatus(View.GONE);
			break;
				
		}

	}
	private void checkPage() {
		switch (mComeFrom) {
		case Web.WEB_GO_BANNER:
			mWebView.loadUrl(((BannerItemBean)mBundle.getSerializable(Const.BEAN_KEY)).getUrl());
			break;
		case Web.WEB_GO_SUMMARY:
			mWebView.loadUrl(((SummaryItemBean)mBundle.getSerializable(Const.BEAN_KEY)).getIphoneUrl());
			break;
		case Web.WEB_GO_ACT:
			mWebView.loadUrl(((ActItemBean)mBundle.getSerializable(Const.BEAN_KEY)).getActivityUrl());
			break;
		case Web.WEB_GO_GAME:
			mWebView.loadUrl(((GameNewsBean)mBundle.getSerializable(Const.BEAN_KEY)).getIphoneUrl());
			break;
		case Web.WEB_GO_EGG:
			mWebView.loadUrl(createEggUrl());
			break;
		case Web.WEB_GO_GOLDVALUE:
			mWebView.loadUrl(createMemberDescUrl());
			break;	
		case Web.WEB_GO_US:
			mWebView.loadUrl(createAboutUsUrl());
			break;
		case Web.WEB_GO_VIP:
			mWebView.loadUrl(createVipUrl());
			break;
		case Web.WEB_GO_GAME_FB:
			mWebView.loadUrl(((GameItemBean)mBundle.getSerializable(Const.BEAN_KEY)).getFbUrl());
			break;
		case Web.WEB_GO_GAME_ACHIEVE:
			mWebView.loadUrl(((AchieveSysItemBean)mBundle.getSerializable(Const.BEAN_KEY)).getTaskUrl());
			break;
		default:
			mWebView.loadUrl("http://www.efuntw.com");
			break;
		}
	}

	@Override
	public ViewGroup[] needShowLoading() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int LayoutId() {
		// TODO Auto-generated method stub
		return E_layout.efun_pd_website;
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
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == EfunFBFunctionActivity.EXTRA_SHARE_DIALOG_FLAG) {
			if(resultCode == EfunFBFunctionActivity.EXTRA_SUCCESS) {
				ToastUtils.toast(WebActivity.this, E_string.efun_pd_share_success);
			} else {
				ToastUtils.toast(WebActivity.this, E_string.efun_pd_share_failure);
			}
			
		}
	}
	
	/**
	 * 设置js调android时的平台参数
	 * 
	 * @param map
	 * @return
	 */
	private HashMap<String, String> jsUserAndPlatformParams() {
		HashMap<String, String> mMap = null;
		if(IPlatApplication.get().getUser() != null){
			mMap = new HashMap<String, String>();
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
		}
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
				EfunLocalUtil.getLocalMacAddress(mContext));// mac
		mMap.put(JsWithAndroidKey.KEY_IMEI,
				EfunLocalUtil.getLocalImeiAddress(mContext));// imei
		mMap.put(JsWithAndroidKey.KEY_IP,
				EfunLocalUtil.getLocalIpAddress(mContext));// ip
		mMap.put(JsWithAndroidKey.KEY_SIMOPERATOR,
				tm.getNetworkOperator());// KEY_SIMOPERATOR
		return mMap;
	}

}
