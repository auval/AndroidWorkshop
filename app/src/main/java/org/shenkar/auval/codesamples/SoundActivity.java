package org.shenkar.auval.codesamples;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mta.sharedutils.AsyncHandler;

import java.io.IOException;

/**
 * check out the states diagram here:
 * https://developer.android.com/reference/android/media/MediaPlayer.html
 *
 * @author amir uval
 */
public class SoundActivity extends AppCompatActivity {

    /**
     * I need this static in order to keep one player.
     * Or else, every time we came here we'd create a new one (unable to stop the previous)
     * MyMusicRunnable class is static - so it doesn't keep reference to the activity - no memory
     * leak. So I CAN use a static reference to it here.
     */
    private static MyMusicRunnable player;
    FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /**
         * Create the player only the first time we're entering here
         */
        if (player == null) {
            player = new MyMusicRunnable(this);
        }

        fab = (FloatingActionButton) findViewById(R.id.fab);
        setFabIcon(player.isMusicIsPlaying());
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // notice that the AsyncHandler.post(player); call is asynchronous, so I cannot
                // check isMusicIsPlaying() immediately after posing it.
                // it will change some time in the future
                setFabIcon(!player.isMusicIsPlaying());
                AsyncHandler.post(player);
            }
        });
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
    static class MyMusicRunnable implements Runnable {
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

        @Override
        public void run() {

            if (musicIsPlaying) {
                mPlayer.stop();
                musicIsPlaying = false;
            } else {
                if (mPlayer == null) {
                    mPlayer = MediaPlayer.create(appContext, R.raw.elo);
                    mPlayer.start();
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
