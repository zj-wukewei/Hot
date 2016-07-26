package com.wkw.hot.ui.web;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.wkw.common_lib.utils.AndroidUtils;
import com.wkw.hot.R;
import com.wkw.hot.base.BaseActivity;
import com.wkw.hot.base.IPresenter;
import com.wkw.hot.reject.component.AppComponent;
import com.wkw.hot.reject.component.DaggerActivityComponent;
import com.wkw.hot.reject.module.ActivityModule;
import com.wkw.hot.ui.AboutActivity;

import butterknife.Bind;

/**
 * Created by wukewei on 16/6/2.
 */
public class WebActivity extends BaseActivity<WebPresenter> implements WebContract.View {


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
    protected void setupActivityComponent(AppComponent appComponent, ActivityModule activityModule) {
        DaggerActivityComponent.builder()
                .appComponent(appComponent)
                .activityModule(activityModule)
                .build()
                .inject(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_web, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case  R.id.action_other_browser:
                AndroidUtils.openBrowser(mContext, url);
                return true;

            case R.id.action_cory_url:
                AndroidUtils.copyToClipBoard(mContext, webView.getUrl());
                return true;

            case R.id.action_share:
                AndroidUtils.showSystemShareOption(mContext, getString(R.string.share_title), url);
                return true;
        }

        return super.onOptionsItemSelected(item);
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
