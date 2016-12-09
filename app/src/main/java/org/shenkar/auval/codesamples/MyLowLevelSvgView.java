package org.shenkar.auval.codesamples;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import com.mta.sharedutils.PathParser;

/**
 * Warning: This custom view is a hack. (
 * I had to extract some internal code from Android Support libraries. (PathParser.java)
 *
 * Currently it's only displaying a sleeping moon as an example, but it can be easily extended
 * to use more raw SVG data, and animate any graphics in onDraw()
 *
 * It's much easier to use CompatVectorDrawable (only xml is needed).
 * But CompatVectorDrawable keeps a Bitmap buffer in the size of the destination view.
 * But this custom view converts the SVG to Path object and uses drawPath() instead.
 * So it's much more memory efficient.
 * On the other hand, it's much more CPU heavy to draw path than to draw Bitmap.
 *
 * So it's good only if you need very large svg to display and possibly animate.
 *
 *
 * @author amir uval 2016
 */
public class MyLowLevelSvgView extends View {

    Path moonPath;
    /**
     * the "brush"
     */
    Paint paint;
    float scaleToFitInView;
    private boolean scaleProportionally = true;

    public MyLowLevelSvgView(Context context) {
        super(context);
        init();
    }

    public MyLowLevelSvgView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyLowLevelSvgView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public static float calculateScaleToFit(Path path, int dstWidth, int dstHeight) {
        RectF rectF = new RectF(); // if called often, take this out and reuse the same object
        path.computeBounds(rectF, false); // the value is returned in our rect of floats
        float horScale = (dstWidth) / rectF.width();
        float verScale = (dstHeight) / rectF.height();

        return Math.min(horScale, verScale) * 0.98f; // shrink a 2% more to prevent clipping

    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true); // smooth lines
        paint.setTextSize(24);

        String moon = getResources().getString(R.string.raw_svg_moon);

        moonPath = PathParser.createPathFromPathData(moon);

        setLayerType(LAYER_TYPE_SOFTWARE, paint);
        //  comment: don't setLayerType(LAYER_TYPE_HARDWARE,topRectPaint);
        //  this view looks bad on some targets with hardware acceleration on.

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        /*
         * Our view size is not ready before this callback
         */
        scaleToFitInView = calculateScaleToFit(moonPath, w, h);

    }

    /**
     * @param canvas the surface to draw to
     */
    @Override
    protected void onDraw(Canvas canvas) {

        paint.setColor(0xff_ff_44_44); // red

        /*
         * call this to get the size we want
         */
        canvas.scale(scaleToFitInView, scaleToFitInView);

        /*
         * draw the path
         */
        canvas.drawPath(moonPath, paint);

        // if animating, don't forget this: it requests another call to onDraw()
        // postInvalidateOnAnimation();
    }

}
