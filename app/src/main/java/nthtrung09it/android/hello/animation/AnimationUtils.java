package nthtrung09it.android.hello.animation;

import android.animation.ValueAnimator;
import android.widget.ProgressBar;

/**
 * @author Kernel
 * @version 1.0
 * @since 23/09/2017
 */
public class AnimationUtils {

    public static void animateProgress(final ProgressBar progressBar) {

        ValueAnimator valueAnimator = ValueAnimator.ofInt(1, 1000);
        valueAnimator.setDuration(1000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int value = (int) valueAnimator.getAnimatedValue();
                progressBar.setProgress(value);
            }
        });
        valueAnimator.start();
    }
}
