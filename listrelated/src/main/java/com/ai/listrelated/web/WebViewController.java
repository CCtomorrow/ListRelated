package com.ai.listrelated.web;

import android.content.Context;
import android.os.Build;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * <b>Project:</b> ListRelated <br>
 * <b>Create Date:</b> 2017/1/19 <br>
 * <b>Author:</b> qy <br>
 * <b>Address:</b> qingyongai@gmail.com <br>
 * <b>Description:</b> 通用的WebView的一些设置，方便直接使用以及扩展 <br>
 */
public class WebViewController {

    protected static final String TAG = WebViewController.class.getSimpleName();

    protected Context mContext;
    protected WebView mWebView;
    protected WebSettings mSettings;
    protected MWebViewClient mWebViewClient;
    protected MWebChromeClient mWebChromeClient;

    public WebViewController(WebView webview) {
        mWebView = webview;
        mContext = mWebView.getContext();
        mSettings = mWebView.getSettings();
    }

    /**
     * 初始化WebView
     */
    public void setupWebView() {
        mSettings.setSaveFormData(false);
        mSettings.setAllowFileAccess(true);
        mSettings.setDatabaseEnabled(true);
        mSettings.setJavaScriptEnabled(true);
        mSettings.setUseWideViewPort(true);
        mSettings.setAppCacheEnabled(true);
        mSettings.setDomStorageEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            mSettings.setDisplayZoomControls(false);
        }
        mSettings.setLoadWithOverviewMode(true);
        mSettings.setPluginState(WebSettings.PluginState.ON);
        // WebView自适应屏幕大小
        mSettings.setDefaultTextEncodingName("UTF-8");
        mSettings.setLoadsImagesAutomatically(true);
        // 设置可以支持缩放
        mSettings.setSupportZoom(true);
        // 设置默认缩放方式尺寸是far
        mSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        // 设置出现缩放工具
        mSettings.setBuiltInZoomControls(true);
        // 设置WebView的缓存模式
        mSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // 不然5.0以后http和https混合的页面会加载不出来
            mSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
    }

    /**
     * 设置ua，需要注意的是我已经把原来的ua追加了，调用这个方法，只需要添加你想要添加的字符串即可
     *
     * @param userAgent ua
     */
    public void setUserAgent(String userAgent) {
        String origin = mSettings.getUserAgentString();
        mSettings.setUserAgentString(origin + " " + userAgent);
        // KLog.i(TAG, "UA------------------>" + mSettings.getUserAgentString());
    }

    /**
     * 设置webViewClient
     *
     * @param webViewClient webViewClient必须继承{@link MWebViewClient}
     */
    public void setWebViewClient(MWebViewClient webViewClient) {
        if (webViewClient != null) {
            mWebViewClient = webViewClient;
            mWebView.setWebViewClient(webViewClient);
        }
    }

    /**
     * 设置webChromeClient
     *
     * @param webChromeClient webChromeClient必须继承{@link MWebChromeClient}
     */
    public void setWebChromeClient(MWebChromeClient webChromeClient) {
        if (webChromeClient != null) {
            mWebChromeClient = webChromeClient;
            mWebView.setWebChromeClient(webChromeClient);
        }
    }

    /**
     * 添加js调用java的接口
     *
     * @param object
     * @param name
     */
    @SuppressWarnings("All")
    public void addJavascriptInterface(Object object, String name) {
        mWebView.addJavascriptInterface(object, name);
    }

}
