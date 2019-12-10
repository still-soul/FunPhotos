package ztk.com.demo.funphotos.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.List;

import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import ztk.com.demo.funphotos.R;
import ztk.com.demo.funphotos.bean.Photo;
import ztk.com.demo.funphotos.utils.PublicUtils;
import ztk.com.demo.funphotos.view.ZoomPhotoView;

public class ZoomPhotoViewPagerAdapter extends PagerAdapter {
    private Context context;
    private List<Photo> photos;

    public ZoomPhotoViewPagerAdapter(Context context, List<Photo> list) {
        this.context = context;
        this.photos = list;
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_photo, null);
        ZoomPhotoView photoView = view.findViewById(R.id.photo_view);
        String path = null;
        photoView.setBackgroundColor(ContextCompat.getColor(context, R.color.black));

        if (photos != null) {
            Photo attachment = photos.get(position);
            if (attachment != null) {
                path = attachment.getMediaPath();
            }
        }
        loadImage(photoView, path);
        try {
            container.addView(view, PublicUtils.dip2px(ViewGroup.LayoutParams.MATCH_PARENT),
                    PublicUtils.dip2px(ViewGroup.LayoutParams.MATCH_PARENT));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return photos == null ? 0 : photos.size();

    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    private void loadImage(final ZoomPhotoView iv, final String imagePath) {
        if (TextUtils.isEmpty(imagePath)) {
            return;
        }
        if (!PublicUtils.isGif(imagePath)) {
            Glide.with(context).load(imagePath).into(iv);
            return;
        }

        Glide.with(context).asGif().load(imagePath).into(iv);
    }


}
