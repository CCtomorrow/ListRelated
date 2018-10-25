package com.ai.listrelated.sample;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.ai.listrelated.imgchooser.ImgChooser;
import com.ai.listrelated.imgchooser.ImgChooserUtil;
import com.ai.listrelated.sample.defaultimpl.DefaultRefreshActivity;
import com.ai.listrelated.sample.img.ImageSizeActivity;
import com.ai.listrelated.sample.info.DeviceInfoActivity;
import com.ai.listrelated.sample.input.InputActivity;
import com.ai.listrelated.sample.simpleloadmore.SimpleLoadmoreActivity;
import com.ai.listrelated.sample.slide.SlideActivity;
import com.ai.listrelated.sample.web.WebrowserActivity;
import com.ai.listrelated.sample.zoom.ZoomActivity;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private ImgChooser chooser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setTitle(R.string.app_name);
        chooser = new ImgChooser(this);
    }

    public void defaultImpl(View view) {
        startActivity(new Intent(this, DefaultRefreshActivity.class));
    }

    public void goWebrowser(View view) {
        startActivity(new Intent(this, WebrowserActivity.class));
    }

    /**
     * 简单的加载更多的实现
     */
    public void simpleLoadMore(View view) {
        startActivity(new Intent(this, SimpleLoadmoreActivity.class));
    }

    public void imageChoose(View view) {
        chooser.showChooseDialog(false);
    }

    public void imageChooseDocument(View view) {
        chooser.showChooseDialog(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        chooser.onActivityResult(requestCode, resultCode, data);
    }

    public void imageSize(View view) {
        startActivity(new Intent(this, ImageSizeActivity.class));
    }

    public void deviceInfo(View view) {
        startActivity(new Intent(this, DeviceInfoActivity.class));
    }

    public void ZoomView(View view) {
        startActivity(new Intent(this, ZoomActivity.class));
    }

    public void InputLimit(View view) {
        startActivity(new Intent(this, InputActivity.class));
    }

    public void Notification(View view) {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //为了版本兼容  选择V7包下的NotificationCompat进行构造
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        //Ticker是状态栏显示的提示
        //builder.setTicker("简单Notification");
        //第一行内容  通常作为通知栏标题
        builder.setContentTitle("标题");
        //第二行内容 通常是通知正文
        builder.setContentText("通知内容");
        builder.setAutoCancel(true);
        //系统状态栏显示的小图标
        builder.setSmallIcon(R.mipmap.ic_launcher);
        //下拉显示的大图标
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 1, intent, 0);
        //点击跳转的intent
        builder.setContentIntent(pIntent);
        //通知默认的声音 震动 呼吸灯
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);
        Notification notification = builder.build();
        manager.notify(0, notification);
    }

    public void Slidview(View view) {
        startActivity(new Intent(this, SlideActivity.class));
    }

}
