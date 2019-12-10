package ztk.com.demo.funphotos.listener;

import android.graphics.RectF;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageView;

import ztk.com.demo.funphotos.utils.ZoomPhotoViewAttacher;

/**
 * Provided default implementation of GestureDetector.OnDoubleTapListener, to be overriden with custom behavior, if needed
 * <p>&nbsp;</p>
 * To be used via {@link ZoomPhotoViewAttacher#setOnDoubleTapListener(android.view.GestureDetector.OnDoubleTapListener)}
 */
public class DefaultOnDoubleTapListener implements GestureDetector.OnDoubleTapListener {

    private ztk.com.demo.funphotos.utils.ZoomPhotoViewAttacher ZoomPhotoViewAttacher;

    /**
     * Default constructor
     *
     * @param ZoomPhotoViewAttacher ZoomPhotoViewAttacher to bind to
     */
    public DefaultOnDoubleTapListener(ZoomPhotoViewAttacher ZoomPhotoViewAttacher) {
        setZoomPhotoViewAttacher(ZoomPhotoViewAttacher);
    }

    /**
     * Allows to change ZoomPhotoViewAttacher within range of single instance
     *
     * @param newZoomPhotoViewAttacher ZoomPhotoViewAttacher to bind to
     */
    public void setZoomPhotoViewAttacher(ZoomPhotoViewAttacher newZoomPhotoViewAttacher) {
        this.ZoomPhotoViewAttacher = newZoomPhotoViewAttacher;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        if (this.ZoomPhotoViewAttacher == null) {
            return false;
        }

        ImageView imageView = ZoomPhotoViewAttacher.getImageView();

        if (null != ZoomPhotoViewAttacher.getOnPhotoTapListener()) {
            final RectF displayRect = ZoomPhotoViewAttacher.getDisplayRect();

            if (null != displayRect) {
                final float x = e.getX(), y = e.getY();

                // Check to see if the user tapped on the photo
                if (displayRect.contains(x, y)) {

                    float xResult = (x - displayRect.left)
                            / displayRect.width();
                    float yResult = (y - displayRect.top)
                            / displayRect.height();

                    ZoomPhotoViewAttacher.getOnPhotoTapListener().onPhotoTap(imageView, xResult, yResult);
                    return true;
                }
            }
        }
        if (null != ZoomPhotoViewAttacher.getOnViewTapListener()) {
            ZoomPhotoViewAttacher.getOnViewTapListener().onViewTap(imageView, e.getX(), e.getY());
        }

        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent ev) {
        if (ZoomPhotoViewAttacher == null) {
            return false;
        }

        try {
            float scale = ZoomPhotoViewAttacher.getScale();
            float x = ev.getX();
            float y = ev.getY();

            if (scale < ZoomPhotoViewAttacher.getMediumScale()) {
                ZoomPhotoViewAttacher.setScale(ZoomPhotoViewAttacher.getMediumScale(), x, y, true);
            } else if (scale >= ZoomPhotoViewAttacher.getMediumScale() && scale < ZoomPhotoViewAttacher.getMaximumScale()) {
                ZoomPhotoViewAttacher.setScale(ZoomPhotoViewAttacher.getMaximumScale(), x, y, true);
            } else {
                ZoomPhotoViewAttacher.setScale(ZoomPhotoViewAttacher.getMinimumScale(), x, y, true);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            // Can sometimes happen when getX() and getY() is called
        }

        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        // Wait for the confirmed onDoubleTap() instead
        return false;
    }

}
