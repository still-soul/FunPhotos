package ztk.com.demo.funphotos.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import androidx.appcompat.widget.AppCompatImageView;
import ztk.com.demo.funphotos.interfaces.ScrollChanged;

/**
 * @author zhaotk
 */
public class SelectImageView extends AppCompatImageView {
    /**
     * 按下时 的X坐标
     */
    private float downX;
    /**
     * 移动时 的X坐标
     */
    private float moveX;
    private ScrollChanged scrollChanged;

    public void setScrollChanged(ScrollChanged scrollChanged) {
        this.scrollChanged = scrollChanged;
    }

    public SelectImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                return true;
            case MotionEvent.ACTION_MOVE:
                moveX = event.getX();
                if ((moveX - downX > 0)) {
                    Log.e( "onTouchEvent: ","向右滑动" );
                    if (scrollChanged != null) {
                        scrollChanged.onScrollRightChanged();
                    }
                }

                if ((downX - moveX > 0)) {
                    Log.e( "onTouchEvent: ","向左滑动" );
                    if (scrollChanged != null) {
                        scrollChanged.onScrollLeftChanged();
                    }
                }
                return true;
            case MotionEvent.ACTION_UP:
              return true;
            default:
                break;

        }
        return super.onTouchEvent(event);
    }
}
