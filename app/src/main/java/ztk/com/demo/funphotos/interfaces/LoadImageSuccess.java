package ztk.com.demo.funphotos.interfaces;

import java.util.List;

import ztk.com.demo.funphotos.bean.Photo;

/**
 * @author zhaotk
 */
public interface LoadImageSuccess {
    /**
     * 获取相册图片成功回调
     * @param attachments
     */
    void onLoadImageSuccess(List<Photo> attachments);
}
