package ztk.com.demo.funphotos.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import ztk.com.demo.funphotos.R;
import ztk.com.demo.funphotos.bean.Photo;
import ztk.com.demo.funphotos.utils.PublicMethod;
import ztk.com.demo.funphotos.view.SelectImageView;

public class PhotosAdapter extends  RecyclerView.Adapter<PhotosAdapter.PhotoListHolder>{
    private List<Photo> photoList = new ArrayList<>();
    private Context mContext;
    private int mScreenWidth;
    private selectImageWatcher selectImageWatcher;

    public interface selectImageWatcher{
        void imageSelected(List<Photo> photoList);
    }

    public void setSelectImageWatcher(selectImageWatcher selectImageWatcher){
        this.selectImageWatcher = selectImageWatcher;

    }

    public PhotosAdapter(Context context) {
        this.mContext = context;
        mScreenWidth = PublicMethod.getScreenWidth(mContext, PublicMethod.dip2px(4)) / 3;
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
    public void onBindViewHolder(@NonNull final PhotoListHolder holder, int position) {
        final Photo photo = photoList.get(position);

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.mipmap.empty)
                .centerCrop()
                .override(mScreenWidth , mScreenWidth);
        Glide.with(mContext).load(photo.getMediaPath()).apply(requestOptions).into(holder.ivPhoto);
        holder.ivPhoto.setScrollChanged(new SelectImageView.ScrollChanged() {
            @Override
            public void onScrollRightChanged() {
                if (!holder.checkBox.isChecked()){
                    holder.checkBox.setChecked(true);
                }
            }

            @Override
            public void onScrollLeftChanged() {
                if (holder.checkBox.isChecked()){
                    holder.checkBox.setChecked(false);

                }
            }
        });

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    photo.setCheck(true);
                }else {
                    photo.setCheck(false);
                }
                if (selectImageWatcher != null){
                    selectImageWatcher.imageSelected(photoList);
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
