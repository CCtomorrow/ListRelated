package com.ai.listrelated.adapter.fragment;

import android.os.Bundle;

/**
 * <b>Project:</b> ListRelated <br>
 * <b>Create Date:</b> 2017/1/6 <br>
 * <b>Author:</b> qy <br>
 * <b>Address:</b> qingyongai@gmail.com <br>
 * <b>Description:</b> Fragment携带的信息 <br>
 */
public class TabInFo {

    private final String tag; // tag
    private final Class<?> clss; // 类
    private final Bundle args; // 参数

    public TabInFo(String _tag, Class<?> _class, Bundle _args) {
        tag = _tag;
        clss = _class;
        args = _args;
    }

    public String getTag() {
        return tag;
    }

    public Class<?> getClss() {
        return clss;
    }

    public Bundle getArgs() {
        return args;
    }

}
