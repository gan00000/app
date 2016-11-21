package com.efun.platform.module.account.view;

import java.net.URLDecoder;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.efun.core.tools.EfunLogUtil;

public class WebDialog extends Dialog {

	// width below which there are no extra margins
	private static final int NO_PADDING_SCREEN_WIDTH = 480;
	// width beyond which we're always using the MIN_SCALE_FACTOR
	private static final int MAX_PADDING_SCREEN_WIDTH = 800;
	// height below which there are no extra margins
	private static final int NO_PADDING_SCREEN_HEIGHT = 800;
	// height beyond which we're always using the MIN_SCALE_FACTOR
	private static final int MAX_PADDING_SCREEN_HEIGHT = 1280;

	// the minimum scaling factor for the web dialog (50% of screen size)
	private static final double MIN_SCALE_FACTOR = 0.5;
	// translucent border around the webview
	private static final int BACKGROUND_GRAY = 0xCC000000;

	public static final int DEFAULT_THEME = android.R.style.Theme_Translucent_NoTitleBar;

	private String url;
	private OnCompleteListener onCompleteListener;
	private WebView webView;
	private ProgressDialog progressDialog;
	// private ImageView crossImageView;
	private FrameLayout contentFrameLayout;
	private boolean isDetached = false;

	/**
	 * Interface that implements a listener to be called when the user's
	 * interaction with the dialog completes, whether because the dialog
	 * finished successfully, or it was cancelled, or an error was encountered.
	 */
	public interface OnCompleteListener {
		/**
		 * Called when the dialog completes.
		 * 
		 * @param values
		 *            on success, contains the values returned by the dialog
		 * @param error
		 *            on an error, contains an exception describing the error
		 */
		void onComplete(Bundle values);
	}

	/**
	 * Constructor which can be used to display a dialog with an
	 * already-constructed URL.
	 * 
	 * @param context
	 *            the context to use to display the dialog
	 * @param url
	 *            the URL of the Web Dialog to display; no validation is done on
	 *            this URL, but it should be a valid URL pointing to a Facebook
	 *            Web Dialog
	 */
	public WebDialog(Context context, String url) {
		this(context, url, DEFAULT_THEME);
	}

	/**
	 * Constructor which can be used to display a dialog with an
	 * already-constructed URL and a custom theme.
	 * 
	 * @param context
	 *            the context to use to display the dialog
	 * @param url
	 *            the URL of the Web Dialog to display; no validation is done on
	 *            this URL, but it should be a valid URL pointing to a Facebook
	 *            Web Dialog
	 * @param theme
	 *            identifier of a theme to pass to the Dialog class
	 */
	public WebDialog(Context context, String url, int theme) {
		super(context, theme);
		this.url = url;
	}

	/**
	 * Constructor which will construct the URL of the Web dialog based on the
	 * specified parameters.
	 * 
	 * @param context
	 *            the context to use to display the dialog
	 * @param action
	 *            the portion of the dialog URL following "dialog/"
	 * @param parameters
	 *            parameters which will be included as part of the URL
	 * @param theme
	 *            identifier of a theme to pass to the Dialog class
	 * @param listener
	 *            the listener to notify, or null if no notification is desired
	 */
	public WebDialog(Context context, String url, int theme, OnCompleteListener listener) {
		super(context, theme);
		this.url = url;
		onCompleteListener = listener;
	}

	/**
	 * Sets the listener which will be notified when the dialog finishes.
	 * 
	 * @param listener
	 *            the listener to notify, or null if no notification is desired
	 */
	public void setOnCompleteListener(OnCompleteListener listener) {
		onCompleteListener = listener;
	}

	/**
	 * Gets the listener which will be notified when the dialog finishes.
	 * 
	 * @return the listener, or null if none has been specified
	 */
	public OnCompleteListener getOnCompleteListener() {
		return onCompleteListener;
	}

	@Override
	public void dismiss() {
		if (webView != null) {
			webView.stopLoading();
		}
		try {
			/*
			 * webView.clearCache(true); webView.clearHistory();
			 * getContext().deleteDatabase("webview.db");
			 * getContext().deleteDatabase("webviewCache.db");
			 */
			CookieSyncManager cookieSyncMngr = CookieSyncManager.createInstance(getContext());
			CookieManager cookieManager = CookieManager.getInstance();
			cookieManager.removeSessionCookie();
			String cookieData = cookieManager.getCookie("http://api.gamer.com.tw/");
			Log.d("efun", "cookieData:" + cookieData);
			// cookieManager.removeAllCookie();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (!isDetached) {
			if (progressDialog.isShowing()) {
				progressDialog.dismiss();
			}
			super.dismiss();
		}
	}

	@Override
	public void onDetachedFromWindow() {
		EfunLogUtil.logD("onDetachedFromWindow");
		isDetached = true;
		super.onDetachedFromWindow();
	}

	@Override
	public void onAttachedToWindow() {
		EfunLogUtil.logD("onAttachedToWindow");
		isDetached = false;
		super.onAttachedToWindow();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		progressDialog = new ProgressDialog(getContext());
		progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		progressDialog.setMessage("loading");
		progressDialog.setOnCancelListener(new OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialogInterface) {
				// sendCancelToListener();
				WebDialog.this.dismiss();
			}
		});
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		contentFrameLayout = new FrameLayout(getContext());

		// First calculate how big the frame layout should be
		// calculateSize();
		// getWindow().setLayout(400, 800);
		// getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
		// ViewGroup.LayoutParams.MATCH_PARENT);
		setSize();
		getWindow().setGravity(Gravity.CENTER);

		// resize the dialog if the soft keyboard comes up
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		setUpWebView();
		setContentView(contentFrameLayout);
	}

	private void setSize() {
		WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		DisplayMetrics metrics = new DisplayMetrics();
		display.getMetrics(metrics);
		// always use the portrait dimensions to do the scaling calculations so
		// we always get a portrait shaped
		// web dialog
		int width = (int) (metrics.widthPixels * 0.85);
		int height = (int) (metrics.heightPixels * 0.85);
		getWindow().setLayout(width, height);
	}

	private void calculateSize() {
		WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		DisplayMetrics metrics = new DisplayMetrics();
		display.getMetrics(metrics);
		// always use the portrait dimensions to do the scaling calculations so
		// we always get a portrait shaped
		// web dialog
		int width = metrics.widthPixels < metrics.heightPixels ? metrics.widthPixels : metrics.heightPixels;
		int height = metrics.widthPixels < metrics.heightPixels ? metrics.heightPixels : metrics.widthPixels;

		int dialogWidth = Math.min(getScaledSize(width, metrics.density, NO_PADDING_SCREEN_WIDTH, MAX_PADDING_SCREEN_WIDTH),
				metrics.widthPixels);
		int dialogHeight = Math.min(getScaledSize(height, metrics.density, NO_PADDING_SCREEN_HEIGHT, MAX_PADDING_SCREEN_HEIGHT),
				metrics.heightPixels);

		getWindow().setLayout(dialogWidth, dialogHeight);
	}

	/**
	 * Returns a scaled size (either width or height) based on the parameters
	 * passed.
	 * 
	 * @param screenSize
	 *            a pixel dimension of the screen (either width or height)
	 * @param density
	 *            density of the screen
	 * @param noPaddingSize
	 *            the size at which there's no padding for the dialog
	 * @param maxPaddingSize
	 *            the size at which to apply maximum padding for the dialog
	 * @return a scaled size.
	 */
	private int getScaledSize(int screenSize, float density, int noPaddingSize, int maxPaddingSize) {
		int scaledSize = (int) ((float) screenSize / density);
		double scaleFactor;
		if (scaledSize <= noPaddingSize) {
			scaleFactor = 1.0;
		} else if (scaledSize >= maxPaddingSize) {
			scaleFactor = MIN_SCALE_FACTOR;
		} else {
			// between the noPadding and maxPadding widths, we take a linear
			// reduction to go from 100%
			// of screen size down to MIN_SCALE_FACTOR
			scaleFactor = MIN_SCALE_FACTOR + ((double) (maxPaddingSize - scaledSize)) / ((double) (maxPaddingSize - noPaddingSize))
					* (1.0 - MIN_SCALE_FACTOR);
		}
		return (int) (screenSize * scaleFactor);
	}

	@SuppressLint("SetJavaScriptEnabled")
	private void setUpWebView() {
		LinearLayout webViewContainer = new LinearLayout(getContext());
		webView = new WebView(getContext());
		webView.setVerticalScrollBarEnabled(false);
		webView.setHorizontalScrollBarEnabled(false);
		webView.setWebViewClient(new DialogWebViewClient());
		webView.getSettings().setJavaScriptEnabled(true);
		webView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
		webView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
		webView.setVisibility(View.INVISIBLE);
		// webView.getSettings().setSavePassword(false);
		// webView.getSettings().setSaveFormData(false);
		webView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		// webView.getSettings().setAppCacheEnabled(false);
		// webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
		// 设置可以有数据库
		// webView.getSettings().setDatabaseEnabled(false);
		// 设置可以使用本地缓存
		// webView.getSettings().setDomStorageEnabled(false);

		// 自适应屏幕
		webView.getSettings().setUseWideViewPort(true);
		webView.getSettings().setLoadWithOverviewMode(true);
		webView.loadUrl(url);

		webViewContainer.addView(webView);
		webViewContainer.setBackgroundColor(BACKGROUND_GRAY);
		contentFrameLayout.addView(webViewContainer);
	}

	private class DialogWebViewClient extends WebViewClient {

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			String decUrl = URLDecoder.decode(url);
			EfunLogUtil.logD("shouldOverrideUrlLoading :" + decUrl);
			String baha_uid = "";
			String code = "";
			if (decUrl.contains("code") && decUrl.contains("bahaencodeid")) {
				if (decUrl.split("[?]").length > 1) {
					String json = decUrl.split("[?]")[1];

					EfunLogUtil.logD("split :" + json);
					try {
						JSONObject obj = new JSONObject(json);
						code = obj.optString("code");
						EfunLogUtil.logD("baha login code :" + code);
						String dataString = obj.optString("data"); //包含baha信息的JSON格式String
						EfunLogUtil.logD("baha login data :" + dataString);
						JSONObject data = new JSONObject(dataString);
						baha_uid = data.optString("bahaencodeid", "");
						EfunLogUtil.logD("baha login uid :" + baha_uid);
					} catch (JSONException e) {
						EfunLogUtil.logE("loginways result is not a jsonObject");
						e.printStackTrace();
					}
				}
				if ("1000".equals(code) && onCompleteListener != null) {
					Bundle bundle = new Bundle();
					bundle.putString("bahaencodeid", baha_uid);
					onCompleteListener.onComplete(bundle);
				}
				WebDialog.this.dismiss();
				return true;
			} else {
				EfunLogUtil.logD("still in baha login process");
			}
			// else if (url.contains("https://user.gamer.com.tw/login.php")) {
			// view.loadUrl("https://user.gamer.com.tw/mobile/MB_login.php");
			// return true;
			// }

			view.loadUrl(url);
			return true;
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			super.onPageStarted(view, url, favicon);
			if (!isDetached) {
				progressDialog.show();
			}
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
			if (!isDetached) {
				progressDialog.dismiss();
			}
			/*
			 * Once web view is fully loaded, set the contentFrameLayout
			 * background to be transparent and make visible the 'x' image.
			 */
			contentFrameLayout.setBackgroundColor(Color.TRANSPARENT);
			webView.setVisibility(View.VISIBLE);
		}

		@Override
		public void onLoadResource(WebView view, String url) {
			// TODO Auto-generated method stub
			super.onLoadResource(view, url);
		}

		@Override
		public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
			super.onReceivedError(view, errorCode, description, failingUrl);
			if (onCompleteListener != null) {
				Bundle bundle = new Bundle();
				bundle.putString("oauth_verifier", "");
				bundle.putString("xoauth_allow", "");
				onCompleteListener.onComplete(bundle);
			}
			WebDialog.this.dismiss();
		}

	}
}
