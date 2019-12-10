package ztk.com.demo.funphotos.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import ztk.com.demo.funphotos.R;
import ztk.com.demo.funphotos.bean.Photo;
import ztk.com.demo.funphotos.interfaces.ScrollChanged;
import ztk.com.demo.funphotos.interfaces.SelectImageWatcher;
import ztk.com.demo.funphotos.utils.PublicUtils;
import ztk.com.demo.funphotos.view.SelectImageView;

/**
 * @author zhaotk
 */
public class PhotosAdapter extends  RecyclerView.Adapter<PhotosAdapter.PhotoListHolder>{
    private List<Photo> photoList = new ArrayList<>();
    private Context mContext;
    private int mScreenWidth;
    private SelectImageWatcher selectImageWatcher;

    public void setSelectImageWatcher(SelectImageWatcher selectImageWatcher){
        this.selectImageWatcher = selectImageWatcher;

    }

    public PhotosAdapter(Context context) {
        this.mContext = context;
        mScreenWidth = PublicUtils.getScreenWidth(mContext, PublicUtils.dip2px(4)) / 3;
    }

    public void setPhotos(List<Photo> photos){
        photoList.clear();
        photoList.addAll(photos);
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public PhotoListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.photo_item_layout, parent, false);
        return new PhotoListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PhotoListHolder holder, final int position) {
        final Photo photo = photoList.get(position);
        if (photo.isCheck()){
            holder.checkBox.setChecked(true);
        }else {
            holder.checkBox.setChecked(false);
        }
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.mipmap.empty)
                .centerCrop()
                .override(mScreenWidth , mScreenWidth);
        Glide.with(mContext).load(photo.getMediaPath()).apply(requestOptions).into(holder.ivPhoto);
        holder.ivPhoto.setScrollChanged(new ScrollChanged() {
            @Override
            public void onScrollRightChanged() {
                if (!photo.isCheck()){
                    photo.setCheck(true);
                    if (selectImageWatcher != null) {
                        selectImageWatcher.imageSelected(position, photo);
                    }
                }
            }

            @Override
            public void onScrollLeftChanged() {
                if (photo.isCheck()){
                    photo.setCheck(false);
                    if (selectImageWatcher != null) {
                        selectImageWatcher.imageSelected(position, photo);
                    }
                }
            }
        });

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.checkBox.isChecked()){
                    photo.setCheck(true);
                }else {
                    photo.setCheck(false);
                }
                if (selectImageWatcher != null) {
                    selectImageWatcher.imageSelected(position, photo);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return photoList == null? 0 : photoList.size();
    }

    public class PhotoListHolder extends RecyclerView.ViewHolder {
        SelectImageView ivPhoto;
        CheckBox checkBox;

        public PhotoListHolder(@NonNull View itemView) {
            super(itemView);
            ivPhoto = itemView.findViewById(R.id.photo);
            checkBox = itemView.findViewById(R.id.check);
        }
    }

}
