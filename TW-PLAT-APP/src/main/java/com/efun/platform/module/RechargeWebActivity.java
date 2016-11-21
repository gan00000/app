package com.efun.platform.module;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;

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

import com.efun.core.tools.EfunLocalUtil;
import com.efun.core.tools.EfunLogUtil;
import com.efun.core.tools.EfunStringUtil;
import com.efun.platform.AndroidScape.E_color;
import com.efun.platform.AndroidScape.E_drawable;
import com.efun.platform.AndroidScape.E_id;
import com.efun.platform.AndroidScape.E_layout;
import com.efun.platform.AndroidScape.E_string;
import com.efun.platform.IPlatApplication;
import com.efun.platform.http.request.bean.BaseRequestBean;
import com.efun.platform.module.base.FixedActivity;
import com.efun.platform.module.utils.AppUtils;
import com.efun.platform.module.utils.UrlUtils;
import com.efun.platform.utils.Const.HttpParam;
import com.efun.platform.utils.Const.Web;
import com.efun.platform.widget.TitleView;

/**
 * Web 页面
 * 
 * @author Jesse
 * 
 */
public class RechargeWebActivity extends FixedActivity {
	protected boolean DISABLE_SSL_CHECK_FOR_TESTING = false;
	/**
	 * 启动本页的来源
	 */
	private int mComeFrom;
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
	
	private ArrayList<String> list;
	
	@Override
	public void onClickRight() {
		switch (mComeFrom) {
		case Web.WEB_GO_PERSON_RECHAR:
			finish();
			break;
		}
	}
	
	@Override
	public void onClickLeft() {
		switch (mComeFrom) {
		case Web.WEB_GO_PERSON_RECHAR:
			break;
		}
	}

	private WebView mWebView;
	private ProgressBar mloadView;

	@SuppressLint("NewApi")
	@Override
	public void init(Bundle bundle) {
		if (bundle != null) {
			mComeFrom = bundle.getInt(Web.WEB_GO_KEY);
		}
		
		mWebView = (WebView) findViewById(E_id.websit_webview);
		mloadView = (ProgressBar)findViewById(E_id.websit_progressbar);
		emptyView = (RelativeLayout)findViewById(E_id.empty);
		mNotifyImg = (ImageView) emptyView.findViewById(E_id.center_btn);
		mNotifyText = (TextView) emptyView.findViewById(E_id.center_text);
		
		// 获取系统的TelephonyManager对象  
		tm = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
		
		list = new ArrayList<String>();
		
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
		settings.setBuiltInZoomControls(true);
		settings.setSupportZoom(true);
		settings.setDomStorageEnabled(true);
		settings.setDefaultFontSize(20);// 设置默认的字体大小
		// 设置自动加载图片
		settings.setLoadsImagesAutomatically(true);
		settings.getDatabaseEnabled();
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

			@SuppressWarnings("deprecation")
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				Log.d("efun", "recharweb:" + url);
				if ((Integer.parseInt(android.os.Build.VERSION.SDK) < 21)) {
					if (list.size() != 0) {
						for (int i = 0; i < list.size(); i++) {
							if (url.equals(list.get(i))) {
								// Log.d("efun",
								// "===========================remove+goBack");
								Log.e("efun", "list.size():" + list.size());
								for (int j = list.size() - 1; j >= i + 1; j--) {
									Log.e("efun", "remove====url");
									list.remove(j);
								}
								mWebView.goBack();
								return true;
							}
						}
					}
					// Log.d("efun", "list.size:"+list.size());
					list.add(url);
				}

				if (url.toLowerCase().startsWith("sms:")) {
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

	}
	
	private String createRechargeUrl() {
		String mName = IPlatApplication.get().getUser().getAccountName();
		if(mName.equals("null") || EfunStringUtil.isEmpty(mName)){
			mName = "";//这里用登录名
		}
		
		String baseUrl = null;
		String url = null;
		baseUrl = IPlatApplication.get().getIPlatUrlByKey(UrlUtils.PAY_PRE_KEY);
		//渠道包名
		String payFrom = "efun";
		String method = mContext.getResources().getString(E_string.efun_pd_method_recharge_url);
		checkMethod(method);
		String payType = mContext.getResources().getString(E_string.efun_pd_recharge_params_paytype);
		long time = System.currentTimeMillis();
		String key = mContext.getResources().getString(E_string.efun_pd_recharge_params_passwordkey);
		
		//toMd5(gameCode + serverCode + efunLevel + creditId + userId + payFrom + time + "ppqiazjaA157JIiidSLiqNViiM");
		String md5Str = EfunStringUtil.toMd5(HttpParam.PLATFORM_CODE+""+""+""+IPlatApplication.get().getUser().getUid()+payFrom+time+key, true);
		
		//http://pay.efun.com/payForward_toPayGW.shtml?gameCode=ka&serverCode=kas1&efunLevel=10&creditId=42030&userId=1&payForm=efun&payType=mobile&time=13840019293&md5Str=JdkwoskdkS12kJJIKK&remark=Majaisjqp-fakwixjj&simOperator=tm.getNetworkOperator()&phoneNumber=&phoneModel=
		//20150303先去掉  +"&phoneNumber="+tm.getLine1Number() 原因：google推荐不支持有这个
		//20150728添加authed,原因，支持转点需要
		url = checkUrl(baseUrl)+method+"?gameCode="+HttpParam.PLATFORM_CODE+"&serverCode="+""
				+"&efunLevel="+""+"&roleId="+""+"&creditId="+"&userId="+IPlatApplication.get().getUser().getUid()
				+"&userName="+mName+"&fromType="+HttpParam.APP+"&payFrom="+payFrom
				+"&payType="+payType+"&time="+time+"&md5Str="+md5Str+"&remark="+"&language="+HttpParam.LANGUAGE+"&simOperator="+tm.getNetworkOperator()
				+"&phoneNumber="+"&version="+HttpParam.PLATFORM+"&platform="+HttpParam.PLATFORM_AREA+"&packageVersion="
				+AppUtils.getAppVersionName(mContext)+"&phoneModel="+Build.MODEL+"&authed="+IPlatApplication.get().getUser().getAuthed()
				+"&sign="+IPlatApplication.get().getUser().getSign()+"&timestamp="+IPlatApplication.get().getUser().getTimestamp();
		
		//url = baseUrl+"?fromType=app&uid="+uid+"&userName="+uname;
		EfunLogUtil.logI("efun", "储值url:"+url);
		Log.d("efun", "rechin:"+url);	
		return url;
	}

	@Override
	public void initTitle(TitleView titleView) {
		switch (mComeFrom) {
		case Web.WEB_GO_PERSON_RECHAR:
			titleView.setTitleRightTextRes(E_string.efun_pd_close);
			titleView.setTitleRightTVRes(E_drawable.efun_pd_cons_00a0e9_bg);
			titleView.setTitleRightTextColor(E_color.e_00a0e9);
			titleView.setTitleLeftStatus(View.GONE);
			break;
		}
	}
	private void checkPage() {
		mWebView.loadUrl(createRechargeUrl());
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

	/**
	 * 校验链接是否标准
	 * @param url 
	 * @return 
	 */
	private String checkUrl(String url) {
		if (!url.endsWith("/")) {
			url = url + "/";
		}
		return url;
	}
	/**
	 * 校验Method是否标准
	 * @param method
	 */
	private void checkMethod(String method) {
		if (!method.endsWith(".shtml")) {
			throw new IllegalAccessError("Method Illeagel");
		}
	}
	
	// 调用手机短信系统，发送短信
		private void sendMessage(String phone, String message) {

			Uri uri = Uri.parse("smsto:" + phone);
			Intent sendIntent = new Intent(Intent.ACTION_SENDTO, uri);
			sendIntent.putExtra("sms_body", message);
			mContext.startActivity(sendIntent);

		}
	
}
