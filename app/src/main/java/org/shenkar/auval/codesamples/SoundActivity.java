package org.shenkar.auval.codesamples;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.MotionEvent;
import android.view.View;

import com.mta.sharedutils.AsyncHandler;

/**
 * check out the states diagram here:
 * https://developer.android.com/reference/android/media/MediaPlayer.html
 *
 * @author amir uval
 */
public class SoundActivity extends AppCompatActivity {

    /**
     * I need this static in order to keep one musicPlayer.
     * Or else, every time we came here we'd create a new one (unable to stop the previous)
     * MyMusicRunnable class is static - so it doesn't keep reference to the activity - no memory
     * leak. So I CAN use a static reference to it here.
     */
    private static MyMusicRunnable musicPlayer;
    /**
     * same comments as above
     */
    private static MySFxRunnable soundEffectsUtil;

    FloatingActionButton fab;
    private boolean doorbellStarted = false;
    private View.OnTouchListener touchListener = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (doorbellStarted) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    soundEffectsUtil.play(R.raw.doorbell_end);
                    doorbellStarted = false;
                }
                // if doorbellStarted, we'll get lots of move events.
                // we don't want to proceed any further
                return true;
            }
            if (event.getX() > v.getWidth() * .85f) {
                // right side of the door =~ door bell
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    soundEffectsUtil.play(R.raw.doorbell_start);
                    doorbellStarted = true;
                }
                return true; // returning true will enable the "up" to be sent here too.
            } else if (event.getX() > v.getWidth() * .7f
                    && event.getY() > v.getHeight() * 0.5f
                    && event.getY() < v.getHeight() * 0.7f) {
                // area of the lock
                soundEffectsUtil.play(R.raw.door_lock);

            } else {
                if (event.getY() > v.getHeight() * 0.8f) {
                    soundEffectsUtil.play(R.raw.door_bang);
                } else {
                    soundEffectsUtil.play(R.raw.door_knock);
                }

            }
            return false; // false tells the caller that the event wasn't consumed
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound);

        // enables using svg for various things other than srcCompat:
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        /**
         * Create the musicPlayer only the first time we're entering here
         */
        if (musicPlayer == null) {
            musicPlayer = new MyMusicRunnable(this);
        }

        fab = (FloatingActionButton) findViewById(R.id.fab);
        setFabIcon(musicPlayer.isMusicIsPlaying());
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // notice that the AsyncHandler.post(musicPlayer); call is asynchronous, so I cannot
                // check isMusicIsPlaying() immediately after posing it.
                // it will change some time in the future
                setFabIcon(!musicPlayer.isMusicIsPlaying());
                AsyncHandler.post(musicPlayer);
            }
        });

        if (soundEffectsUtil == null) {
            soundEffectsUtil = new MySFxRunnable(this);
        }

        View door = findViewById(R.id.door);
        door.setOnTouchListener(touchListener);
    }

    private void setFabIcon(boolean isPlaying) {
        if (isPlaying) {
            fab.setImageResource(android.R.drawable.ic_media_pause);
        } else {
            fab.setImageResource(android.R.drawable.ic_media_play);
        }
    }

}
