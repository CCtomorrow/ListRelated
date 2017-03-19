package com.ai.listrelated.web;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.List;

public class MWebViewClient extends WebViewClient {

    public static final String TAG = MWebViewClient.class.getSimpleName();

    private Context mContext;

    public MWebViewClient(Context context) {
        mContext = context;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView webView, String url) {
        return handle(webView, url);
    }

    @TargetApi(21)
    @Override
    public boolean shouldOverrideUrlLoading(WebView webView, WebResourceRequest request) {
        return handle(webView, request.getUrl().toString());
    }

    public boolean handle(WebView webView, String url) {
        // KLog.i(TAG, "shouldOverrideUrlLoading 地址 -------------> " + url);
        // https://play.google.com/store/apps/details?id=org.getlantern.lantern&hl=zh-CN
        if (url.endsWith(".apk")) {
            if (!goSystemBrowser(mContext, url)) {
                return false;
            }
        } else if (!url.startsWith("http")) {
            // 不是http开头的抛出让系统处理先
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            try {
                mContext.startActivity(intent);
            } catch (Exception e) {
                return false;
            }
        } else {
            // 返回false表示继续让此浏览器打开页面
            return false;
        }
        return true;
    }

    /**
     * 用系统的浏览器打开
     *
     * @param context 上下文
     * @param url     地址
     * @return 返回打开是否成功
     */
    public static boolean goSystemBrowser(Context context, String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Intent i = new Intent(intent);
        try {
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.setPackage("com.android.browser");
            context.startActivity(i);
            return true;
        } catch (Exception ignored) {
        }
        try {
            List<ResolveInfo> list = context.getPackageManager().queryIntentActivities(
                    intent, PackageManager.MATCH_DEFAULT_ONLY);
            if (list == null || list.size() <= 0) {
                return false;
            }
            ResolveInfo info = list.get(0);
            intent.setPackage(info.activityInfo.packageName);
            context.startActivity(intent);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
