package nthtrung09it.android.hello.animation;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Matrix;
import android.graphics.SurfaceTexture;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Surface;
import android.view.TextureView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Kernel
 * @version 1.0
 * @since 23/09/2017
 */
public class VideoPlayerActivity extends AppCompatActivity implements TextureView.SurfaceTextureListener {

    public static final String VIDEO_URL = "http://www.androidbegin.com/tutorial/AndroidCommercial.3gp";
    private static final String TAG_MEDIAPLAYER_FRAGMENT = "MediaPlayerFragment";

    @BindView(R.id.texture_view)
    TextureView mTextureView;

    private int mInitialTextureWidth;
    private int mInitialTextureHeight;

    private int mLastVideoWidth;
    private int mLastVideoHeight;

    private MediaPlayerFragment mMediaPlayerFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        mTextureView.setSurfaceTextureListener(this);

        FragmentManager fm = getSupportFragmentManager();
        mMediaPlayerFragment = (MediaPlayerFragment) fm.findFragmentByTag(TAG_MEDIAPLAYER_FRAGMENT);

        if (mMediaPlayerFragment == null) {
            mMediaPlayerFragment = MediaPlayerFragment.newInstance();
            fm.beginTransaction().add(mMediaPlayerFragment, TAG_MEDIAPLAYER_FRAGMENT).commit();
        }
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int width, int height) {

        mInitialTextureWidth = width;
        mInitialTextureHeight = height;

        Surface surface = new Surface(surfaceTexture);
        mMediaPlayerFragment.mMediaPlayer.setSurface(surface);

        if (mMediaPlayerFragment != null && mMediaPlayerFragment.mMediaPlayer != null) {
            adjustAspectRatio(mInitialTextureWidth, mInitialTextureHeight, mMediaPlayerFragment.mMediaPlayer.getVideoWidth(), mMediaPlayerFragment.mMediaPlayer.getVideoHeight());
        }
    }

    public void adjustAspectRatio(int videoWidth, int videoHeight) {
        adjustAspectRatio(mInitialTextureWidth, mInitialTextureHeight, videoWidth, videoHeight);
    }

    private void adjustAspectRatio(int viewWidth, int viewHeight, int videoWidth, int videoHeight) {
        final double aspectRatio = (double) videoHeight / videoWidth;
        int newWidth, newHeight;

        if (viewHeight > (int) (viewWidth * aspectRatio)) {
            // limited by narrow width; restrict height
            newWidth = viewWidth;
            newHeight = (int) (viewWidth * aspectRatio);
        } else {
            // limited by short height; restrict width
            newWidth = (int) (viewHeight / aspectRatio);
            newHeight = viewHeight;
        }

        final int xoff = (viewWidth - newWidth) / 2;
        final int yoff = (viewHeight - newHeight) / 2;

        final Matrix txform = new Matrix();
        mTextureView.getTransform(txform);
        txform.setScale((float) newWidth / viewWidth, (float) newHeight / viewHeight);
        txform.postTranslate(xoff, yoff);
        mTextureView.setTransform(txform);
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        if (mMediaPlayerFragment != null && mMediaPlayerFragment.mMediaPlayer != null) {
            adjustAspectRatio(mInitialTextureWidth, mInitialTextureHeight, mMediaPlayerFragment.mMediaPlayer.getVideoWidth(), mMediaPlayerFragment.mMediaPlayer.getVideoHeight());
        }
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }

    @OnClick(R.id.btn__full)
    public void showFull() {
        if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}