package com.ai.listrelated.sample.simpleloadmore;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.widget.TextView;

import com.ai.listrelated.sample.R;

/**
 * <b>Project:</b> ListRelated <br>
 * <b>Create Date:</b> 2018/1/11 <br>
 * <b>@author:</b> qy <br>
 * <b>Address:</b> qingyongai@gmail.com <br>
 * <b>Description:</b>  <br>
 */
public class SimpleLoadmoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_load_more);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView tv = (TextView) findViewById(R.id.money);
        toolbar.setTitle("Other");
        String prefix = "商品价格：";
        String origin = "¥ 25.00";
        String dist = "¥ 15.00";
        SpannableStringBuilder sb = new SpannableStringBuilder(prefix);
        append(sb, origin, new StrikethroughSpan(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sb.append("    ");
        append(sb, dist, new ForegroundColorSpan(0xFFFF6900), new AbsoluteSizeSpan(25, true));
        tv.setText(sb);
    }

    /**
     * append span
     */
    public SpannableStringBuilder append(SpannableStringBuilder ssb, CharSequence text, Object... spans) {
        return append(ssb, text, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE, spans);
    }

    /**
     * append span
     *
     * @param ssb   ssb
     * @param text  text
     * @param flag  flag
     * @param spans span
     * @return ssb
     */
    public SpannableStringBuilder append(SpannableStringBuilder ssb, CharSequence text, int flag, Object... spans) {
        int start = ssb.length();
        ssb.append(text);
        for (Object span : spans) {
            ssb.setSpan(span, start, ssb.length(), flag);
        }
        return ssb;
    }

}
