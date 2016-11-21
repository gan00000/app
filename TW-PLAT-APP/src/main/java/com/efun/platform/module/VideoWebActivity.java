package com.efun.platform.module;

import java.lang.reflect.InvocationTargetException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.efun.game.tw.R;
import com.efun.platform.module.game.bean.GameDetailBean;
import com.efun.platform.module.summary.bean.SummaryItemBean;
import com.efun.platform.module.summary.bean.VideoBean;
import com.efun.platform.utils.Const;
import com.efun.platform.utils.Const.Web;

/**
 * Web 页面
 * 
 * @author harvery
 * 
 */

public class VideoWebActivity extends Activity {
	/**
	 * 上一级页面传递过来的数据对象
	 */
	protected Bundle mBundle;
	/**
	 * 启动本页的来源
	 */
	private int mComeFrom;
	/**
	 * url
	 */
	private BaseDataBean mBean;

	private FrameLayout videoview;// 全屏时视频加载view
	private WebView videowebview;
	private ProgressDialog mDialog;
	
	private Boolean islandport = true;//true表示此时是竖屏，false表示此时横屏。
	private View xCustomView;
	private xWebChromeClient xwebchromeclient;
//	private String url = "http://look.appjx.cn/mobile_api.php?mod=news&id=12604";
	private WebChromeClient.CustomViewCallback 	xCustomViewCallback;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉应用标题
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
	            WindowManager.LayoutParams.FLAG_FULLSCREEN);
//		setContentView(R.layout.activity_main);
		LinearLayout body = new LinearLayout(this);
		body.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		body.setBackgroundColor(Color.BLACK);
		body.setOrientation(LinearLayout.VERTICAL);

		videoview = new FrameLayout(this);
		videoview.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		videoview.setVisibility(View.GONE);
		videowebview = new WebView(this);
		videowebview.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		mDialog = new ProgressDialog(this);
		mDialog.setCancelable(true);
		mDialog.setIndeterminate(false);
		mDialog.setMessage("請稍等...");

		body.addView(videoview);
		body.addView(videowebview);

		setContentView(body);
		
		if(getIntent()!=null){
			mBundle = getIntent().getBundleExtra(Const.DATA_KEY);
			if (mBundle != null) {
				mComeFrom = mBundle.getInt(Web.WEB_GO_KEY);
				if(mBundle.getSerializable(Const.BEAN_KEY)!=null){
					mBean = (BaseDataBean) mBundle.getSerializable(Const.BEAN_KEY);
				}
			}
		}
		
		initwidget();
		if(mBundle != null){
			switch (mComeFrom) {
			case Web.WEB_GO_SUMMARY:
				videowebview.loadUrl(((VideoBean)mBundle.getSerializable(Const.BEAN_KEY)).getVideoUrl());
				mDialog.show();
				break;

			case Web.WEB_GO_GAME:
				videowebview.loadUrl(((GameDetailBean)mBundle.getSerializable(Const.BEAN_KEY)).getVideoUrl());
				mDialog.show();
				break;
			case Web.WEB_GO_SUMMARY_LIST:
				videowebview.loadUrl(((SummaryItemBean)mBundle.getSerializable(Const.BEAN_KEY)).getVideoUrl());
				mDialog.show();
				break;
			}
		}
	}

	private void initwidget() {
		// TODO Auto-generated method stub
//		videoview = (FrameLayout) findViewById(R.id.video_view);
//		videowebview = (WebView) findViewById(R.id.video_webview);
		WebSettings ws = videowebview.getSettings();
		/**
		 * setAllowFileAccess 启用或禁止WebView访问文件数据 setBlockNetworkImage 是否显示网络图像
		 * setBuiltInZoomControls 设置是否支持缩放 setCacheMode 设置缓冲的模式
		 * setDefaultFontSize 设置默认的字体大小 setDefaultTextEncodingName 设置在解码时使用的默认编码
		 * setFixedFontFamily 设置固定使用的字体 setJavaSciptEnabled 设置是否支持Javascript
		 * setLayoutAlgorithm 设置布局方式 setLightTouchEnabled 设置用鼠标激活被选项
		 * setSupportZoom 设置是否支持变焦
		 * */
		ws.setBuiltInZoomControls(true);// 隐藏缩放按钮
		ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);// 排版适应屏幕
		ws.setUseWideViewPort(true);// 可任意比例缩放
		ws.setLoadWithOverviewMode(true);// setUseWideViewPort方法设置webview推荐使用的窗口。setLoadWithOverviewMode方法是设置webview加载的页面的模式。
		ws.setSavePassword(true);
		ws.setSaveFormData(true);// 保存表单数据
		ws.setJavaScriptEnabled(true);
//		ws.setGeolocationEnabled(true);// 启用地理定位
//		ws.setGeolocationDatabasePath("/data/data/org.itri.html5webview/databases/");// 设置定位的数据库路径
		ws.setDomStorageEnabled(true);
		xwebchromeclient = new xWebChromeClient();
		videowebview.setWebChromeClient(xwebchromeclient);
		videowebview.setWebViewClient(new xWebViewClientent());
	}

	   @Override
	    public boolean onKeyDown(int keyCode, KeyEvent event) {
	    	if (keyCode == KeyEvent.KEYCODE_BACK) {
	            if (inCustomView()) {
	            	hideCustomView();
	            	return true;
	            }else {
		   			 videowebview.loadUrl("about:blank");
		   			VideoWebActivity.this.finish();
					}
	    	}
	    	return true;
	    }
	   /**
	    * 判断是否是全屏
	    * @return
	    */
	    public boolean inCustomView() {
	 		return (xCustomView != null);
	 	}
	     /**
	      * 全屏时按返加键执行退出全屏方法
	      */
	     public void hideCustomView() {
	    	 xwebchromeclient.onHideCustomView();
	 	}
	/**
	 * 处理Javascript的对话框、网站图标、网站标题以及网页加载进度等
	 * @author
	 */
	public class xWebChromeClient extends WebChromeClient {
		private Bitmap xdefaltvideo;
		private View xprogressvideo;
		@Override
    	//播放网络视频时全屏会被调用的方法
		public void onShowCustomView(View view, WebChromeClient.CustomViewCallback callback)
		{
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); 
	        videowebview.setVisibility(View.GONE);
	        //如果一个视图已经存在，那么立刻终止并新建一个
	        if (xCustomView != null) {
	            callback.onCustomViewHidden();
	            return;
	        }
	        
	        videoview.addView(view);
	        xCustomView = view;
	        xCustomViewCallback = callback;
	        videoview.setVisibility(View.VISIBLE);
		}
		
		@Override
		//视频播放退出全屏会被调用的
		public void onHideCustomView() {
			
			if (xCustomView == null)//不是全屏播放状态
				return;	       
			
			// Hide the custom view.
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); 
			xCustomView.setVisibility(View.GONE);
			
			// Remove the custom view from its container.
			videoview.removeView(xCustomView);
			xCustomView = null;
			videoview.setVisibility(View.GONE);
			xCustomViewCallback.onCustomViewHidden();
			
			videowebview.setVisibility(View.VISIBLE);
			
		}
		//视频加载添加默认图标
		@Override
		public Bitmap getDefaultVideoPoster() {
			if (xdefaltvideo == null) {
				xdefaltvideo = BitmapFactory.decodeResource(
						getResources(), R.drawable.efun_pd_app_name);
		    }
			return xdefaltvideo;
		}
		//视频加载时进程loading
		@Override
		public View getVideoLoadingProgressView() {
			
	        if (xprogressvideo == null) {
	            LayoutInflater inflater = LayoutInflater.from(VideoWebActivity.this);
	            xprogressvideo = inflater.inflate(R.layout.video_loading_progress, null);
	        }
	        return xprogressvideo; 
		}
    	//网页标题
    	 @Override
         public void onReceivedTitle(WebView view, String title) {
            (VideoWebActivity.this).setTitle(title);
         }

//         @Override
//       //当WebView进度改变时更新窗口进度
//         public void onProgressChanged(WebView view, int newProgress) {
//        	 (MainActivity.this).getWindow().setFeatureInt(Window.FEATURE_PROGRESS, newProgress*100);
//         }
    	 
	}

	/**
	 * 处理各种通知、请求等事件
	 * @author
	 */
	public class xWebViewClientent extends WebViewClient {

		 @Override
		    public boolean shouldOverrideUrlLoading(WebView view, String url) {
			 
		    	Log.i("efun", "shouldOverrideUrlLoading: "+url);
		    	mDialog.show();
		        return false;
		    }
		 
		 @Override
		public void onPageFinished(WebView view, String url) {
			// TODO Auto-generated method stub
			super.onPageFinished(view, url);
			mDialog.dismiss();
		}
	}
	/**
	 * 当横竖屏切换时会调用该方法
	 * @author
	 */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
    	Log.i("testwebview", "=====<<<  onConfigurationChanged  >>>=====");
         super.onConfigurationChanged(newConfig);
         
         if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
             Log.i("webview", "   现在是横屏1");
             islandport = false;
            }else if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
             Log.i("webview", "   现在是竖屏1");
             islandport = true;
            }
    }
    
    //
    @Override
    protected void onPause() {
    	// TODO Auto-generated method stub
    	if(videowebview != null){
    		videowebview.onPause();
    	}
    	super.onPause();
    }
    
    @Override
    protected void onResume() {
    	// TODO Auto-generated method stub
    	if(videowebview != null){
    		videowebview.onResume();
    	}
    	super.onResume();
    }
    
    
    
}
