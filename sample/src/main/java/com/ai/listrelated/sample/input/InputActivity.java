package com.ai.listrelated.sample.input;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.view.View;

import com.ai.listrelated.sample.R;
import com.ai.listrelated.util.EditCheckUtil;

/**
 * <b>Project:</b> ListRelated <br>
 * <b>Create Date:</b> 2018/5/25 <br>
 * <b>@author:</b> qy <br>
 * <b>Address:</b> qingyongai@gmail.com <br>
 * <b>Description:</b>  <br>
 */
public class InputActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        TextInputEditText editText = findViewById(R.id.input);
        editText.setFilters(new InputFilter[]{new EditCheckUtil(2)});
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

            }
        });
    }
}
