package com.ai.listrelated.util;

import android.text.InputFilter;
import android.text.Spanned;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditCheckUtil implements InputFilter {

    private int mMaxLength;
    /**
     * 匹配出中文的正则表达式
     */
    private Pattern chinesePattern = Pattern.compile("[\u4e00-\u9fa5]{1,2}");
    private Matcher chineseMatcher;

    public EditCheckUtil(int length) {
        mMaxLength = length;
    }

    /**
     * 比如：输入框中现有文字：我在，然后再输入广州时触发下面的事件
     *
     * @param source 输入的数据：广州
     * @param start  输入的数据开始的下标，一直是0
     * @param end    输入数据结束的下标，此处是2
     * @param dest   源数据：我在
     * @param dstart 数据源的长度
     * @param dend   数据源的长度
     * @return 返回
     */
    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        chineseMatcher = chinesePattern.matcher(source.toString());
        if (!chineseMatcher.matches()) {
            return "";
        }
        if (dest.length() >= mMaxLength) {
            // 如果源数据本身就大于等于最大的长度了就不再输入
            return "";
        }
        chineseMatcher = chinesePattern.matcher(dest.toString());
        if (chineseMatcher.matches()) {
            // 考虑下还能输入多少个
            int canInputLength = mMaxLength - dest.length();
            // 如果要输入的数据小于等于能输入的长度，则全部输入
            if (source.length() <= canInputLength) {
                return null;
            } else {
                return source.subSequence(0, canInputLength);
            }
        } else {
            return "";
        }
    }

}