package ztk.com.demo.funphotos.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ztk.com.demo.funphotos.bean.Photo;

public class LoadImagesUtils {
    public static List<Photo> getSdCardImages(Context context, boolean isneedShowgif) {
        Uri imageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        ContentResolver resolver = context.getContentResolver();
        String gifContent = isneedShowgif ? " or " + MediaStore.Images.Media.MIME_TYPE + "=?" : "";
        String selection = MediaStore.Images.Media.MIME_TYPE + "=? or " + MediaStore.Images.Media.MIME_TYPE + "=?" + gifContent;
        String[] selectionArgs;
        if (isneedShowgif) {
            selectionArgs = new String[]{"image/jpeg", "image/png", "image/gif"};
        } else {
            selectionArgs = new String[]{"image/jpeg", "image/png"};
        }
        Cursor c = resolver.query(imageUri, null, selection, selectionArgs, MediaStore.Images.Media.DATE_TAKEN + " desc");
        List<Photo> attachments = new ArrayList<>();
        if (c == null) {
            return attachments;
        }
        android.util.Log.d("myLog", "getSdCardImageUrls2  " + System.currentTimeMillis());

        int cWidth = c.getColumnIndex(MediaStore.Images.Media.WIDTH);
        int cPath = c.getColumnIndex(MediaStore.Images.Media.DATA);
        int cDateTime = c.getColumnIndex(MediaStore.Images.Media.DATE_TAKEN);
        int cBucketName = c.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
        int cThumbMagic = c.getColumnIndex(MediaStore.Images.Media.MINI_THUMB_MAGIC);

        while (c.moveToNext()) {
            // 获取图片的路径
            String path = c.getString(cPath);
            if (TextUtils.isEmpty(path)) {
                continue;
            }
            File dF = new File(path);
            if (!(dF.exists() && dF.length() > 0)) {
                continue;
            }    
            //宽
            int width = c.getInt(cWidth);
            if (width > 0) {
                //判断去掉损坏的图片
                Photo bean = new Photo();
                String datetime = c.getString(cDateTime);
                String bucket_name = c.getString(cBucketName);
                String thumb_magic = c.getString(cThumbMagic);
                bean.setMediaName(bucket_name);
                bean.setThumbMagic(thumb_magic);
                bean.setMediaPath(path);
                bean.setTime(datetime);
                bean.setIsGif(path.toLowerCase().endsWith(".gif") ? 1 : 0);
                attachments.add(bean);
            }
        }
        c.close();
        android.util.Log.d("myLog", "getSdCardImageUrls2 end " + System.currentTimeMillis());
        return attachments;
    }
}
