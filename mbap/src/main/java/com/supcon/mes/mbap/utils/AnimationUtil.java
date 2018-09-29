package com.supcon.mes.mbap.utils;

import android.animation.ValueAnimator;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * Created by wangshizhan on 2017/12/28.
 * Email:wangshizhan@supcon.com
 */

public class AnimationUtil {


    public static void startAnimation(View v, int start, int end, AnimationStateListener listener) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end).setDuration(500);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.addUpdateListener(arg0 -> {
            int value = (int) arg0.getAnimatedValue();
            ViewGroup.LayoutParams lp = v.getLayoutParams();
            lp.height = value;
            v.setLayoutParams(lp);

            if (end == value) {
                if (listener != null) {
                    listener.onAnimationFinished();
                }
            }

        });

        animator.start();
    }

    public interface AnimationStateListener {

        void onAnimationFinished();

    }

    public static final int DEFAULT_DURATION = -1;
    public static final int NO_DELAY = 0;


    public static void crossFadeViews(View fadeIn, View fadeOut, int duration) {
        fadeIn(fadeIn, duration);
        fadeOut(fadeOut, duration);
    }

    public static void fadeOut(View fadeOut, int duration) {
        fadeOut(fadeOut, duration, null);
    }

    public static void fadeOut(final View fadeOut, int durationMs,
                               final AnimationCallback callback) {
        fadeOut.setAlpha(1);
        final ViewPropertyAnimatorCompat animator = ViewCompat.animate(fadeOut);
        animator.cancel();
        animator.alpha(0).withLayer().setListener(new ViewPropertyAnimatorListener() {
            @Override
            public void onAnimationStart(View view) {

            }

            @Override
            public void onAnimationEnd(View view) {
                fadeOut.setVisibility(View.GONE);
                if (callback != null) {
                    callback.onAnimationEnd();
                }
            }

            @Override
            public void onAnimationCancel(View view) {
                fadeOut.setVisibility(View.GONE);
                fadeOut.setAlpha(0);
                if (callback != null) {
                    callback.onAnimationCancel();
                }
            }
        });
        if (durationMs != DEFAULT_DURATION) {
            animator.setDuration(durationMs);
        }
        animator.start();
    }

    public static void fadeIn(View fadeIn, int durationMs) {
        fadeIn(fadeIn, durationMs, NO_DELAY, null);
    }

    public static void fadeIn(final View fadeIn, int durationMs, int delay,
                              final AnimationCallback callback) {
        fadeIn.setAlpha(0);
        final ViewPropertyAnimatorCompat animator = ViewCompat.animate(fadeIn);
        animator.cancel();
        animator.setStartDelay(delay);
        animator.alpha(1).withLayer().setListener(new ViewPropertyAnimatorListener() {
            @Override
            public void onAnimationStart(View view) {
                fadeIn.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationCancel(View view) {
                fadeIn.setAlpha(1);
                if (callback != null) {
                    callback.onAnimationCancel();
                }
            }

            @Override
            public void onAnimationEnd(View view) {
                if (callback != null) {
                    callback.onAnimationEnd();
                }
            }
        });
        if (durationMs != DEFAULT_DURATION) {
            animator.setDuration(durationMs);
        }
        animator.start();
    }

    public interface AnimationCallback {
        void onAnimationEnd();

        void onAnimationCancel();
    }

}
