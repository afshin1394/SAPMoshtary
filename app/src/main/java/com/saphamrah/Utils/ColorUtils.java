package com.saphamrah.Utils;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.view.View;

public class ColorUtils {


    public static void animateColor( int fromColor, int toColor,int durationInMillis,UpdateTransition updateTransition){


        ValueAnimator anim = new ValueAnimator();
        anim.setIntValues(fromColor, toColor);
        anim.setEvaluator(new ArgbEvaluator());
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                updateTransition.onTransitionDone(valueAnimator);
            }
        });

        anim.setDuration(durationInMillis);
        anim.start();
    }

   public interface UpdateTransition{
        void onTransitionDone(ValueAnimator valueAnimator);
    }
}
