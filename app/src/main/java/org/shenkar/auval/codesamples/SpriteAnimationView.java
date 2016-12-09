package org.shenkar.auval.codesamples;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;

/**
 * Created by amir on 12/9/16.
 */

public class SpriteAnimationView extends android.support.constraint.ConstraintLayout {

    private static final int NUM_FRAMES = 64;
    private static final float X_SPEED = 28f;
    Bitmap bgBitmap;
    Character[]  ch;
    RectF dst0 = new RectF();
    RectF dst1 = new RectF();
    Rect[] frames = new Rect[NUM_FRAMES];
    int mCharHeight;
    int mCharWidth;
    int naiveFrameNam;
    Bitmap spritesBitmap;
    Paint topRectPaint = new Paint();
    private int mViewHeight;
    private int mViewWidth;

    public SpriteAnimationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // allow to call onDraw
        setWillNotDraw(false);

        prepareCharacter();

        dst0 = new RectF(frames[0]);
        dst0.offset(10, 10); // like translate for canvas

        topRectPaint.setColor(0xff_dd_dd_ff);
        topRectPaint.setStyle(Paint.Style.STROKE);

        ch = new Character[] {
                // put the smaller (farther) first, because it will draw first
                new Character(0.1f),
                new Character(0.3f),
                new Character(0.5f)
        };


    }

    private void prepareCharacter() {
        spritesBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.running_grant);
        // setup the rects
        mCharWidth = (spritesBitmap.getWidth() - 64) / 12;
        mCharHeight = (spritesBitmap.getHeight() - 292) / 6;

        int i = 0; // rect index
        for (int y = 0; y < 6; y++) { // row
            for (int x = 0; x < 12; x++) { // column
                frames[i] = new Rect(x * mCharWidth, y * mCharHeight, (x + 1) * mCharWidth, (y + 1) * mCharHeight);
                i++;
                if (i >= NUM_FRAMES) {
                    break;
                }
            }
        }
    }

    private void createBackgroundImage(int w, int h) {

        if (bgBitmap != null) {
            bgBitmap.recycle();
        }
        int midW = w / 2;
        int midH = h / 4;
        Canvas offScreenCanvas = new Canvas();
        bgBitmap = Bitmap.createBitmap(w, h / 2, Bitmap.Config.ARGB_4444);
        offScreenCanvas.setBitmap(bgBitmap);
        Paint p = new Paint();
        p.setStyle(Paint.Style.FILL);
        p.setAntiAlias(true);
        LinearGradient shader = new LinearGradient(0, 0, 0, mViewHeight / 2,
                Color.BLUE, Color.RED, Shader.TileMode.CLAMP);
        p.setShader(shader);
        offScreenCanvas.drawRect(0, 0, w, midH, p);
        p.setShader(null);
        p.setColor(ContextCompat.getColor(getContext(), R.color.sunset_yellow));
        offScreenCanvas.drawCircle(midW, midH, 10, p);
        p.setColor(ContextCompat.getColor(getContext(), R.color.grass_green));
        offScreenCanvas.drawRect(0, midH, w, h / 2, p);

        p.setStyle(Paint.Style.STROKE);
        p.setColor(ContextCompat.getColor(getContext(), R.color.horizon_lines));
        for (int i = -30 * w; i <= 30 * w; i += 500) {
            offScreenCanvas.drawLine(midW, midH, i, h, p);
        }
        // horizontal lines
        for (float i = midH; i >= 1; i *= 0.75f) {
            offScreenCanvas.drawLine(0, midH+i, w, midH+i, p);
        }

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        this.mViewWidth = w;
        this.mViewHeight = h;
        createBackgroundImage(w, h);

        for (Character c: ch) {
            c.measure();
        }

        super.onSizeChanged(w, h, oldw, oldh);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRect(dst0, topRectPaint);
        canvas.drawBitmap(spritesBitmap, frames[naiveFrameNam], dst0, null);

        canvas.drawBitmap(bgBitmap, 0, mViewHeight / 2, null);

        for (Character c: ch) {
            c.draw(canvas); // draw the smaller (farther) first
        }

        postInvalidateDelayed(60); // slower = more natural movement
//        postInvalidateOnAnimation(); // too fast
    }

    class Character {
        boolean characterGoesRight = true;
        float mScale;
        float tx1 = 0;
        private float screenEnd;
        private float screenStart;

        public Character(float scale) {
            this.mScale = scale;
        }

        public void measure() {
            screenStart = -mViewWidth * (0.5f / mScale - 0.5f);
            screenEnd = screenStart + mViewWidth / mScale;

        }

        private void draw(Canvas canvas) {

            int save = canvas.save();

            canvas.scale(mScale, mScale, mViewWidth / 2, mViewHeight * 0.75f);  // x,y is the center of the horizon
            canvas.translate(tx1 + mCharWidth / 2, (mViewHeight - mCharHeight));

            // debug - used while calculated the scaled screen edges
//            canvas.drawRect(screenStart - (tx1 + mCharWidth / 2), 0,
//                    screenEnd - (tx1 + mCharWidth / 2), mCharHeight, topRectPaint);

            if (!characterGoesRight) {
                canvas.scale(-1, 1);
            }
            canvas.drawBitmap(spritesBitmap, frames[naiveFrameNam], dst0, null);

            naiveFrameNam = (naiveFrameNam + 1) % NUM_FRAMES;

            // move speed is the same, but scale makes it to move slower
            // (farther seem to move slower)
            tx1 = tx1 + X_SPEED * (characterGoesRight ? 1 : -1);

            if (tx1 + mCharWidth * mScale / 2 >= screenEnd) {
                // reverse when the center of the character hits the right edge of the screen
                characterGoesRight = false;
            } else if (tx1 - mCharWidth * mScale / 2 <= screenStart) {
                characterGoesRight = true;
            }
            canvas.restoreToCount(save);
        }
    }


}
