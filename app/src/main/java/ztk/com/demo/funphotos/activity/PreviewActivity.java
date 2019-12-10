package ztk.com.demo.funphotos.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import ztk.com.demo.funphotos.R;
import ztk.com.demo.funphotos.adapter.PreviewSmallPhotosAdapter;
import ztk.com.demo.funphotos.adapter.ZoomPhotoViewPagerAdapter;
import ztk.com.demo.funphotos.bean.Photo;
import ztk.com.demo.funphotos.interfaces.OnItemClickListener;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static ztk.com.demo.funphotos.Const.PHOTOS;

/**
 * @author zhaotk
 */
public class PreviewActivity extends AppCompatActivity implements View.OnClickListener, OnItemClickListener {
    private ImageView ivBack;
    private TextView tvNum;
    private CheckBox checkbox;
    private ViewPager viewPager;
    private RecyclerView recyclerView;
    private ZoomPhotoViewPagerAdapter adapter;
    private List<Photo> photoList;
    private PreviewSmallPhotosAdapter smallPhotosAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        getData();
        initView();
    }

    private void getData() {
        Intent intent = getIntent();
        photoList = intent.getParcelableArrayListExtra(PHOTOS);
    }

    public static void start(Context mContext,List<Photo> photos,int requestId){
        Intent intent = new Intent(mContext,PreviewActivity.class);
        intent.putParcelableArrayListExtra(PHOTOS, (ArrayList<? extends Parcelable>) photos);
        ((Activity)mContext).startActivityForResult(intent, requestId);
    }

    private void initView() {
        ivBack = findViewById(R.id.iv_back);
        tvNum = findViewById(R.id.tv_num);
        checkbox = findViewById(R.id.checkbox);
        viewPager = findViewById(R.id.view_pager);
        recyclerView = findViewById(R.id.recycler_view);

        adapter = new ZoomPhotoViewPagerAdapter(this, photoList);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                setSeleced(arg0);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
        smallPhotosAdapter = new PreviewSmallPhotosAdapter(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(smallPhotosAdapter);
        smallPhotosAdapter.setPhotos(photoList);
        smallPhotosAdapter.setOnItemClickListener(this);

        setSeleced(0);
        ivBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.iv_back){
            finish();
        }
    }

    @Override
    public void onItemClick(int position) {
        setSeleced(position);


    }

    private void setSeleced(int position) {
        viewPager.setCurrentItem(position);
        tvNum.setText(String.format("%s/%s", position + 1, photoList.size()));
        for (int i = 0; i < photoList.size(); i ++){
            if (i == position){
                photoList.get(i).setSmallCheck(true);
            }else {
                photoList.get(i).setSmallCheck(false);
            }
        }
        smallPhotosAdapter.notifyDataSetChanged();
    }
}
