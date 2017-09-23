package nthtrung09it.android.hello.animation;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Kernel
 * @version 1.0
 * @since 23/09/2017
 */
public class SecondActivity extends AppCompatActivity {

    public static Intent getCallingIntent(Context context) {
        Intent intent = new Intent(context, SecondActivity.class);
        return intent;
    }

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.btn__ok)
    Button btnOk;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        ValueAnimator valueAnimator = ValueAnimator.ofInt(1, 1000);
        valueAnimator.setDuration(1000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int value = (int) valueAnimator.getAnimatedValue();
                mProgressBar.setProgress(value);
            }
        });
        valueAnimator.start();
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);


    }

    @OnClick(R.id.btn__ok)
    public void doOk() {
        setResult(RESULT_OK);
        finish();
    }

    @OnClick(R.id.btn__show_video)
    public void showVideo() {
        Intent intent = new Intent(this, VideoPlayerActivity.class);
        startActivity(intent);
    }
}
