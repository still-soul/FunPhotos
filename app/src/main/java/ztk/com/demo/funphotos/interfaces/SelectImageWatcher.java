package ztk.com.demo.funphotos.interfaces;


import ztk.com.demo.funphotos.bean.Photo;

/**
 * @author zhaotk
 */
public interface SelectImageWatcher{
    /**
     * 选中/取消选中照片回调
     * @param position
     * @param photo
     */
    void imageSelected(int position,Photo photo);
}
