package nthtrung09it.android.hello.animation;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import java.io.IOException;

/**
 * @author Kernel
 * @version 1.0
 * @since 23/09/2017
 */
public class MediaPlayerFragment extends Fragment implements MediaPlayer.OnVideoSizeChangedListener, MediaPlayer.OnPreparedListener {

    public static MediaPlayerFragment newInstance() {

        Bundle args = new Bundle();

        MediaPlayerFragment fragment = new MediaPlayerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static final String VIDEO_URL = "http://www.androidbegin.com/tutorial/AndroidCommercial.3gp";

    public MediaPlayer mMediaPlayer;
    private boolean prepared = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (!prepared) {
            prepared = true;

            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setOnVideoSizeChangedListener(this);
            mMediaPlayer.setOnPreparedListener(this);

            try {
                mMediaPlayer.setDataSource(VIDEO_URL);
                mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mMediaPlayer.prepareAsync();
            } catch (IOException e) {
            }
        }
    }

    private void releaseMediaPlayer() {

        if (mMediaPlayer != null) {
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.stop();
            }
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releaseMediaPlayer();
    }

    @Override
    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
        if (getActivity() instanceof VideoPlayerActivity) {
            VideoPlayerActivity videoPlayerActivity = (VideoPlayerActivity) getActivity();
            videoPlayerActivity.adjustAspectRatio(width, height);
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }
}
