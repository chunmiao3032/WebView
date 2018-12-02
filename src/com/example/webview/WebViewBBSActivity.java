package com.example.webview;

import android.net.Uri;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings.PluginState;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class WebViewBBSActivity extends Activity {

	private WebView webview;
	TextView headTitle;
	ImageView ivBack;

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ȥ�����ڱ���
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_webview_bbs);

		initHeader("����");

		String Url = "http://fuwu.lcsxjw.com";//"http://fuwu.kashang8.com";//"http://bbs.kashang8.com/";// ������ַ

		webview = (WebView) findViewById(R.id.webview);
		
		webview.loadUrl(Url);// "http://cn.bing.com" 
		 
		WebSettings webSettings = webview.getSettings();
		webSettings.setJavaScriptEnabled(true);// ����WebView���ԣ��ܹ�ִ��Javascript�ű� 
		webSettings.setAllowFileAccess(true);// ���ÿ��Է����ļ� 
		webSettings.setBuiltInZoomControls(true);// ����֧������
		webSettings.setDomStorageEnabled(true);//DOM����API
 
		webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
		webSettings.setPluginState(PluginState.ON);  
		webSettings.setLoadWithOverviewMode(true);
		
		
		// String js =
		// "window.onload = function () {document.getElementById('sb_form_q').value = 111;}";
		// webview.loadUrl("javascript:" + js);//sb_form_q

//		WebChromeClient wvcc = new WebChromeClient() {
//			@Override
//			public void onReceivedTitle(WebView view, String title) {
//				super.onReceivedTitle(view, title);
//				initHeader(title);// �ⲿ��ҳ���
//			}
//
//		};
//		// ����setWebChromeClient����
//		webview.setWebChromeClient(wvcc);
 
		// ����Web��ͼ
		webview.setWebViewClient(new webViewClient());
		
		webview.setWebChromeClient(new WebChromeClient());

		webview.setDownloadListener(new MyWebViewDownLoadListener());
	}
	
	private class MyWebViewDownLoadListener implements DownloadListener {  
		  
        @Override  
        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype,  
                                    long contentLength) {  
            Uri uri = Uri.parse(url);  
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);  
            startActivity(intent);  
        }  
  
    }

	public void initHeader(String title) {
		headTitle = (TextView) findViewById(R.id.headTitle);
		ivBack = (ImageView) findViewById(R.id.ivBack);
		headTitle.setText(title);
		ivBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				finish();
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	// ���û���
	// ����Activity���onKeyDown(int keyCoder,KeyEvent event)����
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// if ((keyCode == KeyEvent.KEYCODE_BACK) && webview.canGoBack()) {
		// webview.goBack(); // goBack()��ʾ����WebView����һҳ��
		// return true;
		// }
		finish();// �����˳�����
		return false;
	}

	// Web��ͼ
	private class webViewClient extends WebViewClient {
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}
	}

}
