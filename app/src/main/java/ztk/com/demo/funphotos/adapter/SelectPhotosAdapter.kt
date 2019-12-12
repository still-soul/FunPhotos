package ztk.com.demo.funphotos.adapter

import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import ztk.com.demo.funphotos.R
import ztk.com.demo.funphotos.bean.Photo
import ztk.com.demo.funphotos.interfaces.ScrollLeftOrRightListener
import ztk.com.demo.funphotos.utils.PublicUtils
import java.util.ArrayList

class SelectPhotosAdapter(private val mContext: Context) : RecyclerView.Adapter<SelectPhotosAdapter.SelectPhotosHolder>() {
    private val photoList = ArrayList<Photo>()
    private val mScreenWidth : Int = PublicUtils.getScreenWidth(mContext, PublicUtils.dip2px(4f)) / 3
    private var mScrollLeftOrRightListener : ScrollLeftOrRightListener? = null

    fun setScrollLeftOrRightListener(mScrollLeftOrRightListener: ScrollLeftOrRightListener){
        this.mScrollLeftOrRightListener = mScrollLeftOrRightListener
    }

    fun setPhotos(photos : List<Photo>){
        photoList.clear()
        photoList.addAll(photos)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectPhotosHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.select_photo_item_layout,parent,false)
        return SelectPhotosHolder(view)
    }

    override fun getItemCount(): Int {
        return photoList.size
    }

    override fun onBindViewHolder(holder: SelectPhotosHolder, position: Int) {
        val photo = photoList[position]
        holder.checkBox.isChecked = photo.isCheck

        val requestOptions = RequestOptions()
                .placeholder(R.mipmap.empty)
                .centerCrop()
                .override(mScreenWidth , mScreenWidth)
        Glide.with(mContext).load(photo.mediaPath).apply(requestOptions).into(holder.ivPhoto)
        holder.checkBox.setOnClickListener {
            mScrollLeftOrRightListener?.let {
                mScrollLeftOrRightListener?.onScrollChanged(position)
            }
        }

    }


    class SelectPhotosHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ivPhoto: ImageView = itemView.findViewById(R.id.photo)
        var checkBox: CheckBox = itemView.findViewById(R.id.check)

    }


}