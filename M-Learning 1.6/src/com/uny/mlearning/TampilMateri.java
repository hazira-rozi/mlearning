package com.uny.mlearning;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


public class TampilMateri extends Activity {
	private TextView textView_WebJudul;
	private String judul;
	private String url;
	private String zoho = "http://viewer.zoho.com/api/urlview.do?url=";
	WebView web;
	ProgressBar progressBar;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tampilmateri);
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
        	return;
        }
        String value1 = extras.getString("value1");
        String value2 = extras.getString("value2");
        if (value1 != null ) {
        	judul = value1;
        }
        if (value2 != null ) {
        	url = value2;
        }
        
        
        web = (WebView) findViewById(R.id.webview01);
        progressBar = (ProgressBar) findViewById(R.id.progressBar1);
        textView_WebJudul = (TextView) findViewById(R.id.textView_WebJudul);
        textView_WebJudul.setText(judul);
        web.setWebViewClient(new myWebClient());
        web.getSettings().setJavaScriptEnabled(true);
        web.loadUrl(zoho+url);
    }
    
    public class myWebClient extends WebViewClient
    {
    	public void onPageStarted(WebView view, String url, Bitmap favicon) {
    		super.onPageStarted(view, url, favicon);
    	}
    	
    	public boolean shouldOverrideUrlLoading(WebView view, String url) {   		
    		view.loadUrl(url);
    		return true;
    		
    	}
    	
    	public void onPageFinished(WebView view, String url) {
    		super.onPageFinished(view, url);
    		
    		progressBar.setVisibility(View.GONE);
    	}
    }
    
	public boolean onKeyDown(int keyCode, KeyEvent event) 
	{
		if ((keyCode == KeyEvent.KEYCODE_BACK) && web.canGoBack()) {
			web.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	public void onAbout(View v)
    {
    	Toast.makeText (getApplicationContext(),"Tunggu loading untuk melihat materi/tugas." , Toast.LENGTH_LONG).show ();
    }
    
    public void onSearch(View v)
    {
    	startActivity (new Intent(getApplicationContext(), SearchActivity.class));
    }
    
    public void onHome (View v)
    {
    	return2Home(this);
    }
    
    public void return2Home(Context context)
    {
        final Intent intent = new Intent(context, MenuUtama.class);
        intent.setFlags (Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity (intent);
    }
}