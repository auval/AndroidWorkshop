package org.shenkar.auval.codesamples;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;

/**
 * Created by amir on 12/9/16.
 */

public class SpriteAnimationView extends android.support.constraint.ConstraintLayout {

    private static final int NUM_FRAMES = 64;
    Bitmap bitmap;
    int currFrame;
    RectF dst = new RectF();
    Rect[] frames = new Rect[NUM_FRAMES];
    Paint paint = new Paint();

    public SpriteAnimationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // allow to call onDraw
        setWillNotDraw(false);

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.running_grant);
        // setup the rects
        int w = (bitmap.getWidth() - 64) / 12;
        int h = (bitmap.getHeight() - 292) / 6;

        int i = 0; // rect index
        for (int y = 0; y < 6; y++) { // row
            for (int x = 0; x < 12; x++) { // column
                frames[i] = new Rect(x * w, y * h, (x + 1) * w, (y + 1) * h);
                i++;
                if (i >= NUM_FRAMES) {
                    break;
                }
            }
        }

        dst = new RectF(frames[0]);

        paint.setColor(0xff_dd_dd_ff);
        paint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRect(dst, paint);
        canvas.drawBitmap(bitmap, frames[currFrame], dst, null);

        currFrame = (currFrame + 1) % NUM_FRAMES;
        postInvalidateDelayed(100);
        //InvalidateOnAnimation();
    }
}
