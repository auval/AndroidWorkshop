package org.shenkar.auval.codesamples;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * This is an original code for animating rotating patterns.
 * Given as a reference material for an Android course
 *
 * @author amir uval
 */
public class MyBitmapAnimatedGraphicsView extends View {

    int height;
    Canvas offScreenCanvas;
    /**
     * the "brush"
     */
    Paint paint;
    int width;
    private Bitmap bitmap;
    private int mDotSpeed = 2000;
    private int mRotationSpeed = 5000;

    public MyBitmapAnimatedGraphicsView(Context context) {
        super(context);
        init();
    }

    public MyBitmapAnimatedGraphicsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyBitmapAnimatedGraphicsView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true); // smooth lines
        paint.setTextSize(24);
        paint.setStrokeWidth(1); // used only when when drawing line (Paint.Style.LINE)
        paint.setStyle(Paint.Style.FILL);
    }

    /**
     * drawing on an off-screen bitmaps allows us to keep the previous drawing visible.
     *
     * @param canvas   the one we created for the off-screen bitmap
     * @param now      we want all drawing to be at sync with each other, using the same value
     * @param rotation we already calculated that, passing the value is usually cheaper than re-calculating it
     */
    protected void drawOffscreen(Canvas canvas, long now, float rotation) {

        // note that you can use underscore ('_') inside int or long literals!
        paint.setColor(0xff_44_ff_44); // green

        int save = canvas.save();

        canvas.rotate(rotation * 360, width / 2, height / 2);

        float dotPlace = calcDotPlace(now);

        // notice the circle has a radius of 12
        canvas.drawCircle(dotPlace * (width - 24) + 12, height / 2, 12, paint);

        paint.setColor(0xff_22_dd_22); // darker green

        // the fill is centered inside the 12 radius circle
        canvas.drawCircle(dotPlace * (width - 24) + 12, height / 2, 10, paint);

        canvas.restoreToCount(save); // we should return the canvas the way we got it == good citizenship


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (bitmap != null) {
            bitmap.recycle();
        }
        offScreenCanvas = new Canvas();
        bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        offScreenCanvas.setBitmap(bitmap);

        height = h;// getHeight();
        width = w;// getWidth();

    }

    /**
     * Note that onDraw need to draw the *entire* view every time it is called.
     * previous draw is long forgotten. (about 16ms ago if we're efficient)
     *
     * @param canvas the surface to draw to
     */
    @Override
    protected void onDraw(Canvas canvas) {
        long now = System.currentTimeMillis();
        float rotation = calcMovement(now);
        int save = canvas.save();
//        canvas.scaleToFitInView(0.9f, 0.9f, width / 2, height / 2);

        drawOffscreen(offScreenCanvas, now, rotation);

        canvas.drawBitmap(bitmap, 0, 0, null);


        canvas.rotate(rotation * 360, width / 2, height / 2);

        paint.setColor(0xff_ff_44_44); // red
        canvas.drawLine(0, height / 2, width, height / 2, paint);

        canvas.restoreToCount(save);


        paint.setColor(0xff_dd_dd_dd); // light grey

        canvas.drawText("Using offscreen Bitmap", 10, 50, paint);

        postInvalidateOnAnimation();

    }

//    /**
//     * force the view to be square. works, but I don't need it eventually.
//     * @param widthMeasureSpec
//     * @param heightMeasureSpec
//     */
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int originalWidth = MeasureSpec.getSize(widthMeasureSpec);
//
//        int minDimension = Math.min(originalWidth, MeasureSpec.getSize(heightMeasureSpec));
//
//        super.onMeasure(
//                MeasureSpec.makeMeasureSpec(minDimension, MeasureSpec.EXACTLY),
//                MeasureSpec.makeMeasureSpec(minDimension, MeasureSpec.EXACTLY));
//    }

    /**
     * I want the red dot to go from one end of the line (0) to the other (w) and back during one cycle
     */
    private float calcDotPlace(long now) {
        // I want the dot to go in one direction for 2 seconds, and then 2 seconds the other way
        int position = (int) (now % (mDotSpeed + mDotSpeed));
        if (position < mDotSpeed) {
            return position / (mDotSpeed * 1f);
        }
        return (mDotSpeed + mDotSpeed - position) / (mDotSpeed * 1f);
    }

    /**
     * @return 0..1
     */
    private float calcMovement(long now) {
        // I want a full  sine cycle every 5 seconds
        // it will be a function of time
        // value 0..1
        float position = (now % mRotationSpeed) / (mRotationSpeed * 1f); // 5000 milliseconds = 5 seconds

        return position;
    }

    public void setAnimationParams(int rotationSpeed, int dotSpeed) {
        this.mRotationSpeed = rotationSpeed;
        this.mDotSpeed = dotSpeed;
    }

}
