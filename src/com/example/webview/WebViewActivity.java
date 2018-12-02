package com.example.webview;

import android.net.Uri;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class WebViewActivity extends Activity {

	private WebView webview;
	TextView headTitle;
	ImageView ivBack;
	String myTitle = "";

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 去掉窗口标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_webview);

		initHeader("");// 外部网页浏览
		
		WebChromeClient myWebChromeClient = new WebChromeClient() {
			@Override
			public void onShowCustomView(View view, CustomViewCallback callback) {
				// TODO Auto-generated method stub
			}
		};

		try {
			Intent intent = getIntent();
			String Url = intent.getStringExtra("Url");
			if (Url != null) {
				webview = (WebView) findViewById(R.id.webview);

				webview.loadUrl(Url);// "http://cn.bing.com"

				WebSettings webSettings = webview.getSettings();
				webSettings.setJavaScriptEnabled(true);// 设置WebView属性，能够执行Javascript脚本
				webSettings.setAllowFileAccess(true);// 设置可以访问文件
				webSettings.setBuiltInZoomControls(true);// 设置支持缩放
				webSettings.setDomStorageEnabled(true);// DOM储存API

				webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
				webSettings.setPluginState(PluginState.ON);
				webSettings.setLoadWithOverviewMode(true);

				webSettings.setMediaPlaybackRequiresUserGesture(false);

				// webSettings.setRenderPriority(RenderPriority.HIGH);
				// webSettings.setBlockNetworkImage(true);
				// webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
				// webSettings.setAppCacheEnabled(true);
				// webSettings.setLoadsImagesAutomatically(true);

				// 加载需要显示的网页

				// String js =
				// "window.onload = function () {document.getElementById('sb_form_q').value = 111;}";
				// webview.loadUrl("javascript:" + js);//sb_form_q

				// 显示网页中的标题
				// try {
				// WebChromeClient wvcc = new WebChromeClient() {
				// @Override
				// public void onReceivedTitle(WebView view, String title) {
				// super.onReceivedTitle(view, title);
				//
				// if (title.length() > 20) {
				// myTitle = title.substring(0, 20);
				// } else {
				// myTitle = title;
				// }
				// initHeader(myTitle);// 外部网页浏览
				// }
				//
				// };
				// // 设置setWebChromeClient对象
				// webview.setWebChromeClient(wvcc);
				// } catch (Exception ex) {
				// initHeader("");
				// }
				// if (myTitle.length() == 0) {
				// initHeader("");
				// }

				// 设置Web视图
				webview.setWebViewClient(new webViewClient());

				webview.setWebChromeClient(myWebChromeClient);

				webview.setDownloadListener(new MyWebViewDownLoadListener());

			}

		} catch (Exception ex) {
		}
	}

	private class MyWebViewDownLoadListener implements DownloadListener {

		@Override
		public void onDownloadStart(String url, String userAgent,
				String contentDisposition, String mimetype, long contentLength) {
			try {
				Uri uri = Uri.parse(url);
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(intent);
			} catch (Exception ex) {

			}
		}

	}

	public void initHeader(String title) {
		try {
			headTitle = (TextView) findViewById(R.id.headTitle);
			ivBack = (ImageView) findViewById(R.id.ivBack);
			headTitle.setText(title);
			ivBack.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					finish();
				}
			});

		} catch (Exception ex) {

		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	// 设置回退
	// 覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		try {
			if ((keyCode == KeyEvent.KEYCODE_BACK) && webview.canGoBack()) {
				webview.goBack(); // goBack()表示返回WebView的上一页面
				return true;
			}
			finish();// 结束退出程序
		} catch (Exception ex) {

		}
		return false;
	}

	// Web视图
	private class webViewClient extends WebViewClient {
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			try {
				view.loadUrl(url);
			} catch (Exception ex) {
			}
			return true;
		}

		@Override
		public void onReceivedError(WebView view, int errorCode,
				String description, String failingUrl) {
			super.onReceivedError(view, errorCode, description, failingUrl);
			// 这里进行无网络或错误处理，具体可以根据errorCode的值进行判断，做跟详细的处理。
			String errorHtml = "<html><body><h1>NOT FOUND NET!</h1></body></html>";

			view.loadData(errorHtml, "text/html", "UTF-8");
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {

			super.onPageStarted(view, url, favicon);
		}

		@Override
		public void onPageFinished(WebView view, String url) {

			super.onPageFinished(view, url);
		}
	}

	
}
