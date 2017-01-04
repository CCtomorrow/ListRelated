package com.ai.listrelated.ui.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orhanobut.logger.Logger;

/**
 * 一个只打印Fragment生命周期的Fragment，以方便随时不用
 */
public abstract class LifecycleFragment extends Fragment {

    protected void logLifeCycle(String msg) {
        Logger.t("Lifecycle").i(msg);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        logLifeCycle("onAttach");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        logLifeCycle("onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        logLifeCycle("onCreateView");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        logLifeCycle("onViewCreated");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        logLifeCycle("onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        logLifeCycle("onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        logLifeCycle("onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        logLifeCycle("onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        logLifeCycle("onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        logLifeCycle("onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        logLifeCycle("onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        logLifeCycle("onDetach");
    }
}
