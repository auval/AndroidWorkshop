package org.shenkar.auval.codesamples;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.SparseIntArray;
import android.view.MotionEvent;
import android.view.View;

import com.mta.sharedutils.AsyncHandler;
import com.mta.sharedutils.compat.Compat;

import java.io.IOException;

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

    /**
     * This can be an independent class.
     * It's here for convenience.
     */
    static class MySFxRunnable implements Runnable {
        Context appContext;
        SoundPool soundPool;
        /**
         * like a hash map, but more efficient
         */
        SparseIntArray soundsMap = new SparseIntArray();
        private boolean prepared = false;

        public MySFxRunnable(Context c) {
            // be careful not to leak the activity context.
            // can keep the app context instead.
            appContext = c.getApplicationContext();

            // init this object on a user thread.
            // The rest of the use of this class can be on the UI thread
            AsyncHandler.post(this);
        }

        /**
         * load and init the sound effects, so later I'll be able to play them instantly from the
         * UI thread.
         */
        @Override
        public void run() {

            soundPool = Compat.createSoundPool();

            /**
             * a callback when prepared -- we need to prevent playing before prepared
             */
            soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                @Override
                public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                    prepared = true;
                }
            });

            /**
             * the load() returns a stream id that can be used to play the sound.
             * I use the "R.raw.xyz" integer as key, because it's useless to invent new keys for
             * them
             */
            soundsMap.put(R.raw.door_bang, soundPool.load(appContext, R.raw.door_bang, 1));
            soundsMap.put(R.raw.doorbell_start, soundPool.load(appContext, R.raw.doorbell_start, 1));
            soundsMap.put(R.raw.doorbell_end, soundPool.load(appContext, R.raw.doorbell_end, 1));
            soundsMap.put(R.raw.door_knock, soundPool.load(appContext, R.raw.door_knock, 1));
            soundsMap.put(R.raw.door_lock, soundPool.load(appContext, R.raw.door_lock, 1));


        }

        public void play(int soundKey) {
            if (soundPool == null || !prepared) {
                return;
            }
            soundPool.play(soundsMap.get(soundKey), 1.0f, 1.0f, 1, 0, 1.0f);
        }
    }

    /**
     * This can be an independent class.
     * It's here for convenience.
     */
    static class MyMusicRunnable implements Runnable, MediaPlayer.OnCompletionListener {
        Context appContext;
        MediaPlayer mPlayer;
        boolean musicIsPlaying = false;

        public MyMusicRunnable(Context c) {
            // be careful not to leak the activity context.
            // can keep the app context instead.
            appContext = c.getApplicationContext();
        }

        public boolean isMusicIsPlaying() {
            return musicIsPlaying;
        }

        /**
         * MediaPlayer.OnCompletionListener callback
         *
         * @param mp
         */
        @Override
        public void onCompletion(MediaPlayer mp) {
            // loop back - play again
            if (musicIsPlaying && mPlayer != null) {
                mPlayer.start();
            }
        }

        /**
         * toggles the music player state
         * called asynchronously every time the play/pause button is pressed
         */
        @Override
        public void run() {

            if (musicIsPlaying) {
                mPlayer.stop();
                musicIsPlaying = false;
            } else {
                if (mPlayer == null) {
                    mPlayer = MediaPlayer.create(appContext, R.raw.nobody_nome);
                    mPlayer.start();
                    mPlayer.setOnCompletionListener(this); // MediaPlayer.OnCompletionListener
                } else {
                    try {
                        mPlayer.prepare();
                        mPlayer.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                musicIsPlaying = true;
            }

        }

    }

}
