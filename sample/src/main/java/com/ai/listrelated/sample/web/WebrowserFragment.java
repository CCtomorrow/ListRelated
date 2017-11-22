package com.ai.listrelated.sample.web;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.JsResult;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

import com.ai.listrelated.sample.R;
import com.ai.listrelated.ui.fragment.BaseLazyFragment;
import com.ai.listrelated.web.MWebChromeClient;
import com.ai.listrelated.web.MWebViewClient;
import com.ai.listrelated.web.WebViewController;

/**
 * <b>Project:</b> ListRelated <br>
 * <b>Create Date:</b> 2017/11/21 <br>
 * <b>Author:</b> qy <br>
 * <b>Address:</b> qingyongai@gmail.com <br>
 * <b>Description:</b>  <br>
 */
public class WebrowserFragment extends BaseLazyFragment {

    private WebViewController mController;
    private InputMethodManager mInputMethodManager;

    private Button mButton;
    private EditText mEditText;
    private WebView mWebView;

    @Override
    public View onInflaterRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.web_layout, container, false);
    }

    @Override
    public void onFindViews(View rootview) {
        mButton = (Button) rootview.findViewById(R.id.go);
        mEditText = (EditText) rootview.findViewById(R.id.edit_url);
        mWebView = (WebView) rootview.findViewById(R.id.web_view);
        mController = new WebViewController(mWebView);
        mController.setupWebView();
        mController.setWebViewClient(new MWebViewClient(getActivity()));
        mController.setWebChromeClient(new MWebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }
        });
    }

    @Override
    public void onBindContent() {
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = mEditText.getText().toString().trim();
                if (!TextUtils.isEmpty(s)) {
                    closeInput();
                    mWebView.loadUrl(s);
                }
            }
        });
    }

    protected void closeInput() {
        if (mInputMethodManager == null) {
            mInputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        }
        if (mInputMethodManager == null) {
            return;
        }
        mInputMethodManager.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }
}
