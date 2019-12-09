package ztk.com.demo.funphotos.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import androidx.appcompat.widget.AppCompatImageView;
import ztk.com.demo.funphotos.utils.PublicMethod;


public class SelectImageView extends AppCompatImageView {
    /**
     * 按下时 的X坐标
     */
    private float downX;
    /**
     * 抬起时 的X坐标
     */
    private float upX;
    private ScrollChanged scrollChanged;

    public void setScrollChanged(ScrollChanged scrollChanged) {
        this.scrollChanged = scrollChanged;
    }
    public interface ScrollChanged {
        void onScrollRightChanged();
        void onScrollLeftChanged();
    }


    public SelectImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //获取当前坐标
        float x = event.getX();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = x;
                return true;
            case MotionEvent.ACTION_MOVE:
                return true;
            case MotionEvent.ACTION_UP:
                upX = x;
                if ((upX - downX > PublicMethod.dip2px( 1))) {
                    Log.e( "onTouchEvent: ","向右滑动" );
                    if (scrollChanged != null) {
                        scrollChanged.onScrollRightChanged();
                    }
                }

                if ((downX - upX > PublicMethod.dip2px( 1))) {
                    Log.e( "onTouchEvent: ","向左滑动" );
                    if (scrollChanged != null) {
                        scrollChanged.onScrollLeftChanged();
                    }
                }

              return true;
            default:
                break;

        }
        return super.onTouchEvent(event);
    }
}
