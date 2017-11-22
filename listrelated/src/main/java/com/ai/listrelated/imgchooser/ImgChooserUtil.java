package com.ai.listrelated.imgchooser;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.IntDef;
import android.support.v4.content.FileProvider;
import android.webkit.MimeTypeMap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * <b>Project:</b> ListRelated <br>
 * <b>Create Date:</b> 2017/11/21 <br>
 * <b>Author:</b> qy <br>
 * <b>Address:</b> qingyongai@gmail.com <br>
 * <b>Description:</b> 图片选择过程中使用的工具类 <br>
 */
public class ImgChooserUtil {

    /**
     * 安卓6.0版本号
     */
    public static final int ANDROID_60 = 23;
    /**
     * 安卓7.0版本号
     */
    public static final int ANDROID_70 = 24;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({CHOOSE_TYPE_CAMERA, CHOOSE_TYPE_DOCUMENT, CHOOSE_TYPE_PICTURE})
    public @interface ChooseMode {
    }

    /**
     * 从相机拍照
     */
    public static final int CHOOSE_TYPE_CAMERA = 0;
    /**
     * 从相册选择
     */
    public static final int CHOOSE_TYPE_PICTURE = 1;
    /**
     * 从Document选择
     */
    public static final int CHOOSE_TYPE_DOCUMENT = 2;
    /**
     * 请求权限
     */
    public static final int REQUEST_PERMISSION = 100;
    /**
     * 876
     */
    public static final int REQUEST_IDENTIFICATOR = 0b1101101100;
    /**
     * 拍照选择图片
     */
    public static final int REQUEST_PHOTO_CAMERA = REQUEST_IDENTIFICATOR + (1 << 11);
    /**
     * 从相册选择图片
     */
    public static final int REQUEST_PHOTO_PICTURE = REQUEST_IDENTIFICATOR + (1 << 12);
    /**
     * 从document选择图片
     */
    public static final int REQUEST_PHOTO_DOCUMENT = REQUEST_IDENTIFICATOR + (1 << 13);
    /**
     * api 24 安卓 7.0 之后的FileProvider的名称，这里以包名为前缀
     */
    public static final String AUTH_SUFFIX = ".image.chooser.file.provider";
    /**
     * 存储图片的临时文件夹，下次启动应用就可以清除了里面的文件，由于API 17以后使用
     * {@link Context#getExternalFilesDir(String)}不需要权限，所以我们可以在这里面
     * 建立子目录用来存储临时图片
     */
    public static final String TMP_DIR = "tmp_img_choose";

    public static Uri getUriFromFile(Context context, File file) {
        Uri uri;
        // 这里用数字，因为有的应用可能还没升级到
        if (Build.VERSION.SDK_INT >= ImgChooserUtil.ANDROID_70) {
            String authStr = context.getPackageName() + AUTH_SUFFIX;
            uri = FileProvider.getUriForFile(context, authStr, file);
        } else {
            uri = Uri.fromFile(file);
        }
        return uri;
    }

    /**
     * 获取临时目录
     */
    private static File getTmpImgDir(Context context) {
        File root = context.getExternalFilesDir(null);
        File tmpDir = new File(root, TMP_DIR);
        return tmpDir;
    }

    /**
     * 删除临时目录，这个可以在每次启动应用的时候调用
     */
    public static void deleteTmpImgFile(Context context) {
        try {
            File tmpDir = getTmpImgDir(context);
            if (tmpDir.exists() && tmpDir.isDirectory()) {
                File[] tmpFiles = tmpDir.listFiles();
                for (File t : tmpFiles) {
                    t.delete();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取一个临时文件，以时间戳命名
     */
    public static File getTmpFile(Context context) {
        File tmpDir = getTmpImgDir(context);
        if (!tmpDir.exists()) {
            tmpDir.mkdirs();
        }
        return new File(tmpDir, System.currentTimeMillis() + ".jpg");
    }

    /**
     * Camera Intent
     */
    public static Intent createCameraIntent(Context context, File file) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            Uri originUri = ImgChooserUtil.getUriFromFile(context, file);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, originUri);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return intent;
    }

    /**
     * Gallery Intent
     */
    public static Intent plainGalleryPickerIntent() {
        return new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
    }

    /**
     * 手机是否支持从Gallery选择图片
     */
    public static boolean canDeviceHandleGallery(Context context) {
        return plainGalleryPickerIntent().resolveActivity(context.getPackageManager()) != null;
    }

    /**
     * Gallery Intent
     */
    public static Intent createGalleryIntent() {
        Intent intent = plainGalleryPickerIntent();
        intent.setType("image/*");
        return intent;
    }

    /**
     * Document Intent
     */
    public static Intent createDocumentsIntent() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        return intent;
    }

    /**
     * 获取MimeType
     */
    private static String getMimeType(Context context, Uri uri) {
        String extension;
        if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            final MimeTypeMap mime = MimeTypeMap.getSingleton();
            extension = mime.getExtensionFromMimeType(context.getContentResolver().getType(uri));
        } else {
            extension = MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(new File(uri.getPath())).toString());

        }
        return extension;
    }

    /**
     * stream write to file
     *
     * @param in
     * @param file
     */
    public static void writeToFile(InputStream in, File file) {
        try {
            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            out.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
