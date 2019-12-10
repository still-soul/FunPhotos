package ztk.com.demo.funphotos.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import ztk.com.demo.funphotos.R;
import ztk.com.demo.funphotos.bean.Photo;
import ztk.com.demo.funphotos.interfaces.OnItemClickListener;
import ztk.com.demo.funphotos.interfaces.ScrollChanged;
import ztk.com.demo.funphotos.interfaces.SelectImageWatcher;
import ztk.com.demo.funphotos.utils.PublicUtils;
import ztk.com.demo.funphotos.view.SelectImageView;

/**
 * @author zhaotk
 */
public class PreviewSmallPhotosAdapter extends  RecyclerView.Adapter<PreviewSmallPhotosAdapter.PhotoListHolder>{
    private List<Photo> photoList = new ArrayList<>();
    private Context mContext;
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;

    }

    public PreviewSmallPhotosAdapter(Context context) {
        this.mContext = context;
    }

    public void setPhotos(List<Photo> photos){
        photoList.clear();
        photoList.addAll(photos);
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public PhotoListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.preview_small_item, parent, false);
        return new PhotoListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PhotoListHolder holder, final int position) {
        final Photo photo = photoList.get(position);
        if (photo == null){
            return;
        }
        if (photo.isSmallCheck()){
            holder.ivChecked.setVisibility(View.VISIBLE);
        }else {
            holder.ivChecked.setVisibility(View.GONE);
        }
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.mipmap.empty)
                .centerCrop()
                .override(PublicUtils.dip2px(60) ,PublicUtils.dip2px(60));
        Glide.with(mContext).load(photo.getMediaPath()).apply(requestOptions).into(holder.ivImage);

        holder.vItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return photoList == null? 0 : photoList.size();
    }

    public class PhotoListHolder extends RecyclerView.ViewHolder {
        View vItem;
        ImageView ivImage,ivChecked;

        public PhotoListHolder(@NonNull View itemView) {
            super(itemView);
            vItem = itemView.findViewById(R.id.item_view);
            ivImage = itemView.findViewById(R.id.iv_image);
            ivChecked = itemView.findViewById(R.id.iv_checked);
        }
    }

}
