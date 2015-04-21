package com.example.tripplanner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class GoogleSearch extends Activity {
	
	private WebView web;
	final int PICK1 = Menu.FIRST + 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.google_search);
		
		web = (WebView)findViewById(R.id.web);
		web.getSettings().setJavaScriptEnabled(true);
		web.setWebViewClient(new WebViewClient());
		web.loadUrl("http://www.google.com");
	}
	
	// set back key to go to previous web page
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK && web.canGoBack()) {
			web.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.google_search, menu);
		MenuItem item1 = menu.add(0, PICK1, Menu.NONE, "Back");
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		
		switch (id) {
		case PICK1:
			startActivity(new Intent (this, MainActivity.class));
		}

		return super.onOptionsItemSelected(item);
	}
}
