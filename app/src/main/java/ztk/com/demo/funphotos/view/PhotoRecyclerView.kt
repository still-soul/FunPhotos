package ztk.com.demo.funphotos.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.ViewConfiguration
import androidx.recyclerview.widget.RecyclerView
import ztk.com.demo.funphotos.interfaces.ScrollLeftOrRightListener
import kotlin.math.abs

class PhotoRecyclerView(context: Context, attrs: AttributeSet?) : RecyclerView(context, attrs) {
    /**
     * 按下时 的X坐标
     */
    private var downX: Float = 0f
    private var downY: Float = 0f
    /**
     * 抬起时 的X坐标
     */
    private var moveX: Float = 0f
    private var moveY: Float = 0f
    /**
     * 滑动的最小距离
     */
    private var mTouchSlop: Int = 8
    private var mScrollLeftOrRightListener: ScrollLeftOrRightListener? = null
    private var points = ArrayList<Int>()

    fun setScrollLeftOrRightListener(mScrollLeftOrRightListener: ScrollLeftOrRightListener) {
        this.mScrollLeftOrRightListener = mScrollLeftOrRightListener
    }

    init{
        mTouchSlop = ViewConfiguration.get(context).scaledTouchSlop
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                downX = event.x
                downY = event.y
                points.clear()
            }
            MotionEvent.ACTION_MOVE -> {
                moveX = event.x
                moveY = event.y

                val view = findChildViewUnder(event.x, event.y)
                if (view != null && !points.contains(getChildAdapterPosition(view))) {
                    val position = getChildAdapterPosition(view)
                    Log.e("TAG", "position=$position")
                    points.add(position)
                    Log.e("TAG", "=${ViewConfiguration.get(context).scaledTouchSlop}")
                    if (abs(moveX - downX) > abs(moveY - downY) && abs(moveX - downX) > mTouchSlop) {
                        mScrollLeftOrRightListener?.let {
                            mScrollLeftOrRightListener?.onScrollChanged(position)
                        }
                    }
                }

            }
            MotionEvent.ACTION_UP -> {
                points.clear()
            }
        }
        return super.onTouchEvent(event)
    }

}