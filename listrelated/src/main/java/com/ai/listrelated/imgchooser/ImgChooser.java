package com.ai.listrelated.imgchooser;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.ai.listrelated.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * <b>Project:</b> ListRelated <br>
 * <b>Create Date:</b> 2017/11/21 <br>
 * <b>@author:</b> qy <br>
 * <b>Address:</b> qingyongai@gmail.com <br>
 * <b>Description:</b> 图片选择器 <br>
 */
public class ImgChooser {

    /**
     * 是从哪里选取图片的，0相机，1相册，2document，这里暂时就两个选项给外面，因为1和2基本一样的
     */
    @ImgChooserUtil.ChooseMode
    private int chooseType;
    private File originFile;
    private String authStr;

    private Activity activity;
    private Fragment fragment;

    private IReceivedChangedImg iReceivedChangedImg;

    public ImgChooser(Activity activity) {
        this.activity = activity;
        authStr = activity.getPackageName() + ImgChooserUtil.AUTH_SUFFIX;
        ImgChooserUtil.deleteTmpImgFile(activity);
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public void setiReceivedChangedImg(IReceivedChangedImg iReceivedChangedImg) {
        this.iReceivedChangedImg = iReceivedChangedImg;
    }

    public void showChooseDialog() {
        showChooseDialog(true);
    }

    /**
     * 展示对话框
     *
     * @param document 第二个item是不是打开document界面，这个和picture界面还有点区别的
     */
    public void showChooseDialog(final boolean document) {
        String[] items = activity.getResources().getStringArray(R.array.image_chooser_item);
        new AlertDialog.Builder(activity)
                .setTitle(R.string.image_chooser_title)
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        chooseType = which;
                        if (!document && which > 0) {
                            chooseType = ImgChooserUtil.CHOOSE_TYPE_PICTURE;
                        }
                        if (Build.VERSION.SDK_INT >= ImgChooserUtil.ANDROID_60) {
                            checkPermissions();
                        } else {
                            handleChoose();
                        }
                    }
                }).create().show();
    }

    private void checkPermissions() {
        handleChoose();
        // FIXME: 2018/5/4 权限自己处理
//        String p;
//        if (handleChoose == 0) {
//            p = Manifest.permission.CAMERA;
//        } else {
//            p = Manifest.permission.WRITE_EXTERNAL_STORAGE;
//        }
//        mPermissionsUtils.setPermissionsListener(new PermissionsListener() {
//            @Override
//            public void onDenied(String[] strings) {
//                TipUtils tipUtils = TipUtils.getTipUtil();
//                tipUtils.showMsg(activity, R.string.linghit_profile_change_permission_text);
//            }
//
//            @Override
//            public void onGranted() {
//                handleChoose();
//            }
//        });
//        mPermissionsUtils.withActivity(activity);
//        Object o = activity;
//        if (fragment != null) {
//            o = fragment;
//        }
//        String tip = activity.getString(R.string.linghit_profile_change_permission_text);
//        mPermissionsUtils.getPermissionsWithTips(o, REQUEST_PERMISSION, new String[]{tip}, p);
    }

    private void handleChoose() {
        switch (chooseType) {
            default:
            case ImgChooserUtil.CHOOSE_TYPE_CAMERA:
                takePicture();
                break;
            case ImgChooserUtil.CHOOSE_TYPE_DOCUMENT:
                selectDocument();
                break;
            case ImgChooserUtil.CHOOSE_TYPE_PICTURE:
                selectPicture();
                break;
        }
    }

    /**
     * 打开系统照相机
     */
    private void takePicture() {
        File captureFile = ImgChooserUtil.getTmpFile(activity);
        originFile = captureFile;
        Intent intent = ImgChooserUtil.createCameraIntent(activity, captureFile);
        activity.startActivityForResult(intent, ImgChooserUtil.REQUEST_PHOTO_CAMERA);
    }

    private void selectDocument() {
        Intent intent = ImgChooserUtil.createDocumentsIntent();
        activity.startActivityForResult(intent, ImgChooserUtil.REQUEST_PHOTO_DOCUMENT);
    }

    /**
     * 从相册获取图片
     */
    private void selectPicture() {
        boolean support = ImgChooserUtil.canDeviceHandleGallery(activity);
        Intent intent;
        int code;
        if (support) {
            intent = ImgChooserUtil.createGalleryIntent();
            code = ImgChooserUtil.REQUEST_PHOTO_PICTURE;
        } else {
            chooseType = ImgChooserUtil.CHOOSE_TYPE_DOCUMENT;
            intent = ImgChooserUtil.createDocumentsIntent();
            code = ImgChooserUtil.REQUEST_PHOTO_DOCUMENT;
        }
        activity.startActivityForResult(intent, code);
    }

    private static boolean isPhoto(Intent data) {
        return data == null || data.getData() == null;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        boolean ok = (requestCode & ImgChooserUtil.REQUEST_IDENTIFICATOR) > 0;
        if (ok && resultCode == Activity.RESULT_OK) {
            if (requestCode == ImgChooserUtil.REQUEST_PHOTO_CAMERA) {
                if (originFile != null && originFile.exists()) {
                    // 去图片裁剪
                    goImgCrop(activity, originFile);
                } else {
                    // 没有得到图片
                    Toast.makeText(activity, "没有得到图片", Toast.LENGTH_SHORT).show();
                }
            } else if (requestCode == ImgChooserUtil.REQUEST_PHOTO_DOCUMENT) {
                onPictureReturnedFromGallery(data, activity);
            } else if (requestCode == ImgChooserUtil.REQUEST_PHOTO_PICTURE) {
                onPictureReturnedFromGallery(data, activity);
            } else if (requestCode == ImgChooserUtil.REQUEST_PHOTO_CUT) {
                // FIXME: 2018/5/4 裁剪好的图片
                Log.i("TAG", cropFile.getAbsolutePath());
            }
        }
    }

    private void onPictureReturnedFromGallery(Intent data, Activity activity) {
        if (data.getData() != null) {
            InputStream pictureInputStream = null;
            try {
                pictureInputStream = activity.getContentResolver().openInputStream(data.getData());
                File file = ImgChooserUtil.getTmpFile(activity);
                ImgChooserUtil.writeToFile(pictureInputStream, file);
                // 裁剪图片
                goImgCrop(activity, file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                // 没有得到图片
                Toast.makeText(activity, "没有得到图片", Toast.LENGTH_SHORT).show();
            } finally {
                try {
                    if (pictureInputStream != null) {
                        pictureInputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            Toast.makeText(activity, "没有得到图片", Toast.LENGTH_SHORT).show();
        }
    }

    private File cropFile;

    private void goImgCrop(Activity activity, File file) {
        cropFile = ImgChooserUtil.getTmpFile(activity);
        // FIXME: 2018/5/4 宽度高度自己调整
        ImgChooserUtil.cropImageUri(activity, ImgChooserUtil.getUriFromFile(activity, file),
                Uri.fromFile(cropFile), 480, 480, ImgChooserUtil.REQUEST_PHOTO_CUT);
    }

    public interface IReceivedChangedImg {
        void onReceivedChangedImg(String path);
    }
}
