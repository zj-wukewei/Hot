package com.wkw.hot.ui.web;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.wkw.hot.R;
import com.wkw.hot.base.BaseActivity;
import com.wkw.hot.base.IPresenter;

import butterknife.Bind;

/**
 * Created by wukewei on 16/6/2.
 */
public class WebActivity extends BaseActivity {


    private static final String URL = "url";
    private static final String TITLE = "title";
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.webView)
    WebView webView;
    @Bind(R.id.progress_bar)
    ProgressBar progressBar;


    private String url, title;

    public static void startActivity(Context context, String url, String title) {
        Intent intent = new Intent(context,WebActivity.class);
        intent.putExtra(URL, url);
        intent.putExtra(TITLE, title);
        context.startActivity(intent);
    }

    @Override
    protected IPresenter getPresenter() {
        return null;
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_web;
    }

    @Override
    protected void initEventAndData() {
        url = getIntent().getStringExtra(URL);
        title = getIntent().getStringExtra(TITLE);
        setCommonBackToolBack(toolbar, title);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setAppCacheEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setSupportZoom(true);
        webView.setWebChromeClient(new ChromeClient());
        webView.setWebViewClient(new Client());
        webView.loadUrl(url);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (webView != null) webView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (webView != null) webView.onPause();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (webView.canGoBack()) {
                        webView.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView != null) {
            webView.removeAllViews();
            webView.destroy();
        }
    }

    private class ChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            progressBar.setProgress(newProgress);
            if (newProgress == 100) {
                progressBar.setVisibility(View.GONE);
            } else {
                progressBar.setVisibility(View.VISIBLE);
            }
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
        }
    }
    private class Client extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url!=null) view.loadUrl(url);
            return  true;
        }
    }

}
