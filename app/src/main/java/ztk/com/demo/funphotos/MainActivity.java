package ztk.com.demo.funphotos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.functions.Consumer;
import ztk.com.demo.funphotos.adapter.PhotosAdapter;
import ztk.com.demo.funphotos.bean.Photo;
import ztk.com.demo.funphotos.utils.IntentUtils;
import ztk.com.demo.funphotos.utils.PhotosUtils;

import android.Manifest;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;
public class MainActivity extends AppCompatActivity implements PhotosUtils.LoadImageSuccess,
        PhotosAdapter.selectImageWatcher, View.OnClickListener {
    private RxPermissions rxPermissions;
    private PhotosUtils photosUtils;
    private RecyclerView recyclerView;
    private PhotosAdapter photosAdapter;
    private List<Photo> selectedPhotos = new ArrayList<>();
    private int selected;
    private TextView tvPreview,tvSure;
    private PhotoObserver mPhotoObserver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initPermissions();
        mPhotoObserver = new PhotoObserver(new Handler());
        getContentResolver().registerContentObserver(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, true, mPhotoObserver);
        initView();
        setListener();
    }

    private void setListener() {
        photosUtils.setLoadImageSuccess(this);
        photosAdapter.setSelectImageWatcher(this);
        tvPreview.setOnClickListener(this);
    }

    private void initView() {
        tvPreview = findViewById(R.id.preview);
        tvSure = findViewById(R.id.sure);
        recyclerView = findViewById(R.id.photo_recycler);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        photosAdapter = new PhotosAdapter(this);
        recyclerView.setAdapter(photosAdapter);
    }

    private void initPermissions() {
        rxPermissions = new RxPermissions(this);
        photosUtils = new PhotosUtils(this);

        rxPermissions.request(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (!aBoolean) {
                            IntentUtils.gotoPermissionSetting(MainActivity.this);
                        } else {
                            photosUtils.getData();
                        }
                    }
                });
    }

    /**
     * 获取图库图片成功回调
     * @param photos
     */
    @Override
    public void onLoadImageSuccess(List<Photo> photos) {
        Log.e( "onLoadImageSuccess: ",photos.toString() );
        photosAdapter.setPhotos(photos);

    }

    /**
     * 图片选中/取消选中回调
     * @param photoList
     */
    @Override
    public void imageSelected(List<Photo> photoList) {
        selected = 0;
        for (int i = 0; i < photoList.size();i++){
            Photo photo = photoList.get(i);
            if (photo.isCheck()){
                selected++;
                selectedPhotos.add(photo);
            }
        }
        setSureNum();

    }

    @Override
    public void onClick(View view) {

    }

    /***
     * 设置底部选中状态
     */
    private void setSureNum() {
        if (selected == 0) {
            tvPreview.setTextColor(ContextCompat.getColor(this,R.color.CT3));
            tvSure.setBackgroundResource(R.drawable.shape_rect_grey_radius4);
            tvPreview.setEnabled(false);
        } else {
            tvSure.setBackgroundResource(R.drawable.record_select_bg_up );
            tvPreview.setTextColor(ContextCompat.getColor(this,R.color.CT1));
            tvPreview.setEnabled(true);
        }
        StringBuilder sb = new StringBuilder()
                .append("共")
                .append(selected)
                .append("张");

        tvSure.setText(sb);
    }

    /**
     * 检测本地相册有没有更新
     */
    class PhotoObserver extends ContentObserver {


        public PhotoObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange) {

        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            photosUtils.getData();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getContentResolver().unregisterContentObserver(mPhotoObserver);
    }
}
