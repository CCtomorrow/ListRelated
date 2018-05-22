package com.ai.listrelated.sample.info;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.ai.listrelated.sample.R;

public class DeviceInfoActivity extends AppCompatActivity {

    private TextView vinfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        vinfo = findViewById(R.id.info);
        DeviceInfoModel model = new DeviceInfoModel();
        model.setId(Build.ID);
        model.setDisplay(Build.DISPLAY);
        model.setProduct(Build.PRODUCT);
        model.setDevice(Build.DEVICE);
        model.setBoard(Build.BOARD);
        model.setManufacturer(Build.MANUFACTURER);
        model.setBrand(Build.BRAND);
        model.setModel(Build.MODEL);
        model.setBootloader(Build.BOOTLOADER);
        model.setHardware(Build.HARDWARE);
        model.setSupported_abis(Build.SUPPORTED_ABIS.toString());

        model.setIncremental(Build.VERSION.INCREMENTAL);
        model.setRelease(Build.VERSION.RELEASE);
        model.setBase_os(Build.VERSION.BASE_OS);
        model.setSecurity_patch(Build.VERSION.SECURITY_PATCH);
        model.setSdk_int(Build.VERSION.SDK_INT);

        model.setFingerprint(Build.FINGERPRINT);
        vinfo.setText(model.toString());
    }
}
