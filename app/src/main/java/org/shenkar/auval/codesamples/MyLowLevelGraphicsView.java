package org.shenkar.auval.codesamples;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Official training:
 * https://developer.android.com/training/custom-views/custom-drawing.html
 *
 *
 */
public class MyLowLevelGraphicsView extends View {

    /**
     * the "brush"
     */
    Paint paint;

    public MyLowLevelGraphicsView(Context context) {
        super(context);
        init();
    }

    public MyLowLevelGraphicsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyLowLevelGraphicsView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true); // smooth lines
        paint.setTextSize(24);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    /**
     *
     * @param canvas the surface to draw to
     */
    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);

        paint.setColor(0xff_ff_44_44); // red

        canvas.drawLine(0, 0, getWidth(), getHeight(), paint);
        canvas.drawLine(0, getHeight(), getWidth(), 0, paint);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, 30, paint);

        paint.setColor(0xff_dd_dd_dd); // light grey

        canvas.drawText("Low Level Graphics",10,50,paint);

    }

}
