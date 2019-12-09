package ztk.com.demo.funphotos.utils;

import android.content.Context;
import android.util.Log;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import ztk.com.demo.funphotos.bean.Photo;

public class PhotosUtils {
    private static final String TAG = "loadImages";
    private LoadImageSuccess mLoadImageSuccess;
    private Context mContext;

    public PhotosUtils(Context context) {
        this.mContext = context;
    }

    public void setLoadImageSuccess(LoadImageSuccess loadImageSuccess) {
        this.mLoadImageSuccess = loadImageSuccess;
    }
    public interface LoadImageSuccess {
        void onLoadImageSuccess(List<Photo> attachments);
    }
    public void getData(){
        Observable.create(new ObservableOnSubscribe<List<Photo>>() {
            @Override
            public void subscribe(ObservableEmitter<List<Photo>> emitter) {
                emitter.onNext(LoadImagesUtils.getSdCardImages(mContext, true));
            }
        }).observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Photo>>() {
                    @Override
                    public void accept(List<Photo> attachments) {
                        if (attachments != null && mLoadImageSuccess != null) {
                            mLoadImageSuccess.onLoadImageSuccess(attachments);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Log.e(TAG, throwable.getMessage());
                    }
                });
    }


}
