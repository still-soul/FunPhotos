package ztk.com.demo.funphotos.activity

import android.Manifest
import android.database.ContentObserver
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tbruyelle.rxpermissions2.RxPermissions
import ztk.com.demo.funphotos.Const.PREVIEW_ID
import ztk.com.demo.funphotos.R
import ztk.com.demo.funphotos.adapter.SelectPhotosAdapter
import ztk.com.demo.funphotos.bean.Photo
import ztk.com.demo.funphotos.interfaces.LoadImageSuccess
import ztk.com.demo.funphotos.interfaces.ScrollLeftOrRightListener
import ztk.com.demo.funphotos.utils.IntentUtils
import ztk.com.demo.funphotos.utils.PhotosUtils
import ztk.com.demo.funphotos.utils.PublicUtils
import ztk.com.demo.funphotos.view.PhotoRecyclerView

class SelectPhotoActivity : AppCompatActivity(),LoadImageSuccess,View.OnClickListener,ScrollLeftOrRightListener {
    var tvPreview : TextView? = null
    var tvSure : TextView? = null
    private val photosUtils = PhotosUtils(this)
    private var selectedPhotos = ArrayList<Photo>()
    private var photos = ArrayList<Photo>()
    private var selected : Int = 0
    private var mPhotoObserver: PhotoObserver? = null
    private var selectPhotosAdapter : SelectPhotosAdapter? = null
    lateinit var recyclerView : PhotoRecyclerView

    private val TAG = "PhotoRecyclerView"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_photo)
        initPermissions()
        addObserver()
        initView()
        setListener()

    }

    private fun initPermissions() {
        val rxPermissions = RxPermissions(this)
        rxPermissions.request(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe { aBoolean ->
                    if (!aBoolean) {
                        IntentUtils.gotoPermissionSetting(this@SelectPhotoActivity)
                    } else {
                        photosUtils.getData()
                    }
                }
    }

    private fun setListener() {
        photosUtils.setLoadImageSuccess(this)
        tvPreview?.setOnClickListener(this)
        recyclerView.setScrollLeftOrRightListener(this)
        selectPhotosAdapter?.setScrollLeftOrRightListener(this)


    }

    private fun addObserver() {
        mPhotoObserver = PhotoObserver(Handler())
        contentResolver.registerContentObserver(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, true, mPhotoObserver!!)
    }

    private fun initView() {
        tvPreview = findViewById(R.id.preview)
        tvSure = findViewById(R.id.sure)
        recyclerView  = findViewById(R.id.photo_recycler)
        val gridLayoutManager = GridLayoutManager(this@SelectPhotoActivity,3)
        recyclerView.layoutManager = gridLayoutManager
        recyclerView.setHasFixedSize(true)
        selectPhotosAdapter = SelectPhotosAdapter(this@SelectPhotoActivity)
        recyclerView.adapter = selectPhotosAdapter

    }

    /**
     * 检测本地相册有没有更新
     */
    internal inner class PhotoObserver(handler: Handler) : ContentObserver(handler){
        override fun onChange(selfChange: Boolean, uri: Uri?) {
            photosUtils.getData()
        }
    }

    override fun onLoadImageSuccess(attachments: MutableList<Photo>?) {
        attachments?.let {
            photos.clear()
            photos.addAll(attachments)
            selectPhotosAdapter?.setPhotos(it)}
    }

    override fun onClick(view: View?) {
        if (view?.id == R.id.preview) {
            PreviewActivity.start(this, selectedPhotos, PREVIEW_ID)
        }
    }
    override fun onScrollChanged(position: Int) {
        photos.let {
            var photo = photos[position]
            photo.let {
                if (photo.isCheck){
                    selectedPhotos.remove(photo)
                    selected--
                    photo.isCheck = false
                }else{
                    selectedPhotos.add(photo)
                    selected++
                    photo.isCheck = true
                }
                selectPhotosAdapter?.notifyItemChanged(position)
                setSureNum()
            }
        }

    }
    /***
     * 设置底部选中状态
     */
    private fun setSureNum() {
        if (selected == 0) {
            tvPreview?.setTextColor(ContextCompat.getColor(this, R.color.CT3))
            tvSure?.setBackgroundResource(R.drawable.shape_rect_grey_radius4)
            tvPreview?.isEnabled = false
        } else {
            tvSure?.setBackgroundResource(R.drawable.record_select_bg_up)
            tvPreview?.setTextColor(ContextCompat.getColor(this, R.color.CT1))
            tvPreview?.isEnabled = true
        }
        val sb = StringBuilder()
                .append("共")
                .append(selected)
                .append("张")

        tvSure?.text = sb
    }

    override fun onDestroy() {
        super.onDestroy()
        mPhotoObserver?.let { contentResolver.unregisterContentObserver(it) }
    }

}
